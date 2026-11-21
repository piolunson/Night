#!/usr/bin/env python3
"""Repair duplicate local declarations left by the CFR decompiler.

For every "variable X is already defined in method M" error the later
declaration (and every use of it inside its enclosing block) is renamed to a
fresh name. Renaming stays inside the enclosing block, so earlier uses of the
first declaration are untouched.
"""
import re
import subprocess
import sys
import os

ROOT = os.path.dirname(os.path.abspath(__file__))
os.chdir(ROOT)


def compile_project():
    env = dict(os.environ)
    env['JAVA_HOME'] = os.path.expanduser('~/toolchain/jdk25')
    env['PATH'] = env['JAVA_HOME'] + '/bin:' + env['PATH']
    with open('build.log', 'w', encoding='utf-8') as log:
        subprocess.run(['./gradlew', 'compileJava', '--no-daemon', '--console=plain'],
                       stdout=log, stderr=subprocess.STDOUT, env=env, timeout=280)


def parse_errors():
    if not os.path.exists('build.log'):
        return []
    log = open('build.log', encoding='utf-8', errors='replace').read().splitlines()
    errs = []
    for line in log:
        m = re.match(r'\s*(/.*?\.java):(\d+): error: variable (\w+) is already defined', line)
        if m:
            errs.append((m.group(1), int(m.group(2)), m.group(3)))
    return errs


def enclosing_block(text, pos):
    """Return (start, end) of the block that encloses pos."""
    depth = 0
    i = pos
    start = 0
    while i >= 0:
        ch = text[i]
        if ch == '}':
            depth += 1
        elif ch == '{':
            if depth == 0:
                start = i
                break
            depth -= 1
        i -= 1
    depth = 0
    j = start
    while j < len(text):
        ch = text[j]
        if ch == '{':
            depth += 1
        elif ch == '}':
            depth -= 1
            if depth == 0:
                return start, j + 1
        j += 1
    return start, len(text)


def fresh_name(text, base):
    n = 2
    while re.search(r'\b%s_%d\b' % (re.escape(base), n), text):
        n += 1
    return '%s_%d' % (base, n)


def repair(per_file_limit=40):
    errs = parse_errors()
    if not errs:
        return 0
    by_file = {}
    for f, line, var in errs:
        by_file.setdefault(f, []).append((line, var))
    changed = 0
    for f, items in by_file.items():
        text = open(f, encoding='utf-8').read()
        lines = text.splitlines(keepends=True)
        # byte offset of the start of each line
        offsets = []
        acc = 0
        for l in lines:
            offsets.append(acc)
            acc += len(l)
        # handle from the bottom up so offsets stay valid
        items.sort(reverse=True)
        used = set()
        for line, var in items[:per_file_limit]:
            if (line, var) in used:
                continue
            used.add((line, var))
            line_start = offsets[line - 1]
            decl = re.search(r'\b%s\b\s*=' % re.escape(var), text[line_start:line_start + 400])
            if not decl:
                continue
            dpos = line_start + decl.start()
            bstart, bend = enclosing_block(text, dpos)
            new = fresh_name(text, var)
            segment = text[dpos:bend]
            renamed = re.sub(r'(?<!\.)\b%s\b' % re.escape(var), new, segment)
            if renamed == segment:
                continue
            text = text[:dpos] + renamed + text[bend:]
            changed += 1
        open(f, 'w', encoding='utf-8').write(text)
    return changed


def main():
    rounds = int(sys.argv[1]) if len(sys.argv) > 1 else 4
    for i in range(rounds):
        compile_project()
        n = repair()
        print('round %d: renamed %d declarations' % (i + 1, n), flush=True)
        if n == 0:
            break


if __name__ == '__main__':
    main()
