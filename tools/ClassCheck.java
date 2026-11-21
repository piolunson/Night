import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Offline bytecode gate for the compiled mod.
 *
 * <p>Two passes over every {@code .class} file in the compiled output:</p>
 * <ol>
 *   <li>structural: the class file must parse and must not declare two fields or two methods with the
 *       same name <em>and</em> descriptor (this is what broke VelocityModule with
 *       {@code ClassFormatError: Duplicate method name "lambda$onPacketReceive$0"});</li>
 *   <li>JVM: every class is handed to a real class loader, so malformed bytecode is rejected by the
 *       JVM itself instead of by the game at runtime.</li>
 * </ol>
 *
 * <p>Classes whose dependencies are not on the supplied classpath are counted as skipped: their
 * structure was still checked in pass one. Exit code is 1 on any problem, 2 on bad usage.</p>
 */
public final class ClassCheck {
    private static final List<String> PROBLEMS = new ArrayList<>();
    private static int parsed;
    private static int defined;
    private static int skipped;

    private ClassCheck() {
    }

    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.err.println("usage: ClassCheck <classes-dir> [classpath]");
            System.exit(2);
        }

        Path classesDir = Path.of(args[0]).toAbsolutePath().normalize();
        if (!Files.isDirectory(classesDir)) {
            System.err.println("not a directory: " + classesDir);
            System.exit(2);
        }

        List<Path> files = new ArrayList<>();
        try (Stream<Path> walk = Files.walk(classesDir)) {
            walk.filter(path -> path.toString().endsWith(".class")).sorted().forEach(files::add);
        }
        if (files.isEmpty()) {
            System.err.println("no .class files under " + classesDir);
            System.exit(1);
        }

        for (Path file : files) {
            checkStructure(classesDir.relativize(file).toString(), file);
        }

        List<URL> urls = new ArrayList<>();
        urls.add(classesDir.toUri().toURL());
        if (args.length > 1 && !args[1].isBlank()) {
            for (String entry : args[1].split(File.pathSeparator)) {
                if (!entry.isBlank()) {
                    urls.add(Path.of(entry).toUri().toURL());
                }
            }
        }

        try (URLClassLoader loader = new URLClassLoader(urls.toArray(URL[]::new), ClassLoader.getPlatformClassLoader())) {
            for (Path file : files) {
                String name = binaryName(classesDir.relativize(file).toString());
                if (name == null) {
                    continue;
                }
                try {
                    Class.forName(name, false, loader);
                    ++defined;
                } catch (ClassFormatError | VerifyError error) {
                    PROBLEMS.add("JVM rejected " + name + " -> " + error.getClass().getSimpleName() + ": " + error.getMessage());
                } catch (NoClassDefFoundError | ClassNotFoundException error) {
                    ++skipped;
                } catch (LinkageError error) {
                    PROBLEMS.add("linkage error in " + name + " -> " + error.getClass().getSimpleName() + ": " + error.getMessage());
                } catch (Throwable error) {
                    PROBLEMS.add("could not define " + name + " -> " + error.getClass().getSimpleName() + ": " + error.getMessage());
                }
            }
        }

        if (!PROBLEMS.isEmpty()) {
            System.out.println("Bytecode verification FAILED (" + PROBLEMS.size() + " problem(s)):");
            for (String problem : PROBLEMS) {
                System.out.println("  - " + problem);
            }
            System.out.println("classes=" + parsed + " definedByJvm=" + defined + " skippedMissingDeps=" + skipped);
            System.exit(1);
        }

        System.out.println("Bytecode verification OK: classes=" + parsed + ", definedByJvm=" + defined + ", skippedMissingDeps=" + skipped);
    }

    private static void checkStructure(String relativePath, Path file) {
        String name = binaryName(relativePath);
        if (name == null) {
            return;
        }
        ++parsed;
        try (DataInputStream in = new DataInputStream(new ByteArrayInputStream(Files.readAllBytes(file)))) {
            if (in.readInt() != 0xCAFEBABE) {
                throw new IOException("bad magic");
            }
            in.readUnsignedShort(); // minor version
            in.readUnsignedShort(); // major version

            int constantPoolCount = in.readUnsignedShort();
            String[] utf8 = new String[constantPoolCount];
            for (int i = 1; i < constantPoolCount; i++) {
                int tag = in.readUnsignedByte();
                switch (tag) {
                    case 1 -> {
                        int length = in.readUnsignedShort();
                        utf8[i] = new String(in.readNBytes(length), StandardCharsets.UTF_8);
                    }
                    case 7, 8, 16, 19, 20 -> in.skipNBytes(2);
                    case 15 -> in.skipNBytes(3);
                    case 3, 4, 9, 10, 11, 12, 17, 18 -> in.skipNBytes(4);
                    case 5, 6 -> {
                        in.skipNBytes(8);
                        i++;
                    }
                    default -> throw new IOException("unknown constant pool tag " + tag);
                }
            }

            in.skipNBytes(6); // access flags, this class, super class
            int interfaces = in.readUnsignedShort();
            in.skipNBytes(2L * interfaces);

            readMembers(in, utf8, name, "field");
            readMembers(in, utf8, name, "method");
        } catch (IOException error) {
            PROBLEMS.add("unreadable class file " + name + " -> " + error.getMessage());
        }
    }

    private static void readMembers(DataInputStream in, String[] utf8, String owner, String kind) throws IOException {
        int count = in.readUnsignedShort();
        Set<String> seen = new HashSet<>();
        for (int i = 0; i < count; i++) {
            in.skipNBytes(2); // access flags
            int nameIndex = in.readUnsignedShort();
            int descriptorIndex = in.readUnsignedShort();
            int attributes = in.readUnsignedShort();
            for (int a = 0; a < attributes; a++) {
                in.skipNBytes(2);
                in.skipNBytes(Integer.toUnsignedLong(in.readInt()));
            }

            String memberName = nameIndex > 0 && nameIndex < utf8.length ? utf8[nameIndex] : null;
            String descriptor = descriptorIndex > 0 && descriptorIndex < utf8.length ? utf8[descriptorIndex] : null;
            if (memberName == null || descriptor == null) {
                PROBLEMS.add("bad " + kind + " name or descriptor reference in " + owner);
                continue;
            }
            if (!seen.add(memberName + descriptor)) {
                PROBLEMS.add("duplicate " + kind + " " + owner + "." + memberName + descriptor);
            }
        }
    }

    private static String binaryName(String relativePath) {
        String path = relativePath.replace(File.separatorChar, '/');
        if (!path.endsWith(".class")) {
            return null;
        }
        String name = path.substring(0, path.length() - ".class".length()).replace('/', '.');
        if (name.endsWith("module-info") || name.endsWith("package-info")) {
            return null;
        }
        return name;
    }
}
