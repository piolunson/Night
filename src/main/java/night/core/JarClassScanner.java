/*
 * Decompiled with CFR 0.152.
 */
package night.core;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;
import java.util.stream.Stream;

public final class JarClassScanner {
    private JarClassScanner() {
    }

    public static List<Class<?>> findSubtypesOf(Class<?> baseType, Class<?> anchorClass) {
        ArrayList results = new ArrayList();
        ClassLoader loader = anchorClass.getClassLoader();
        URL location = anchorClass.getProtectionDomain().getCodeSource() != null ? anchorClass.getProtectionDomain().getCodeSource().getLocation() : null;
        List<String> classNames = new ArrayList();
        if (location != null) {
            try {
                File file;
                try {
                    file = new File(location.toURI());
                }
                catch (Throwable throwable) {
                    file = new File(location.getPath());
                }
                if (file.isDirectory()) {
                    classNames = JarClassScanner.scanDirectory(file);
                }
            }
            catch (Throwable file) {
                // empty catch block
            }
            if (classNames.isEmpty()) {
                try (InputStream in = location.openStream();
                     JarInputStream jarInputStream = new JarInputStream(in);){
                    JarEntry entry;
                    while ((entry = jarInputStream.getNextJarEntry()) != null) {
                        String name = entry.getName();
                        if (entry.isDirectory() || !name.endsWith(".class") || name.contains("module-info")) continue;
                        classNames.add(JarClassScanner.toClassName(name));
                    }
                }
                catch (Throwable throwable) {
                    // empty catch block
                }
            }
        }
        for (String string : classNames) {
            int mods;
            Class<?> clazz;
            try {
                clazz = Class.forName(string, false, loader);
            }
            catch (Throwable ignored) {
                continue;
            }
            if (clazz == baseType || !baseType.isAssignableFrom(clazz) || Modifier.isAbstract(mods = clazz.getModifiers()) || Modifier.isInterface(mods)) continue;
            results.add(clazz);
        }
        return results;
    }

    private static List<String> scanJar(File jarFile) {
        ArrayList<String> names = new ArrayList<String>();
        try (JarFile jar = new JarFile(jarFile);){
            Enumeration<JarEntry> entries = jar.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                String name = entry.getName();
                if (entry.isDirectory() || !name.endsWith(".class") || name.contains("module-info")) continue;
                names.add(JarClassScanner.toClassName(name));
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return names;
    }

    private static List<String> scanDirectory(File dir) {
        Path root = dir.toPath();
        try (Stream<Path> walk = Files.walk(root, new FileVisitOption[0])) {
            return walk.filter(p -> p.toString().endsWith(".class") && !p.toString().contains("module-info")).map(p -> JarClassScanner.toClassName(root.relativize(p).toString().replace(File.separatorChar, '/'))).toList();
        }
        catch (IOException ioException) {
            return List.of();
        }
    }

    private static String toClassName(String entryName) {
        return entryName.substring(0, entryName.length() - ".class".length()).replace('/', '.');
    }
}

