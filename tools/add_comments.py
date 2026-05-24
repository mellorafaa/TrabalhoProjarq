import re
from pathlib import Path

root = Path('src/main/java')
java_files = list(root.rglob('*.java'))

method_regex = re.compile(r'^(\s*)(public|protected|private)\s+([\w<>, \[\]]+)\s+(\w+)\s*\(([^)]*)\)\s*\{\s*$')

modified = []

for f in java_files:
    text = f.read_text(encoding='utf-8')
    lines = text.splitlines()
    # find package line
    pkg_idx = None
    for i, line in enumerate(lines):
        if line.startswith('package '):
            pkg_idx = i
            break
    if pkg_idx is None:
        continue
    class_name = f.stem
    # decide header comment
    header_comment = f'// Classe {class_name}: responsabilidade principal inferida pelo nome (comentário automático)'
    # insert header if not present already right after package
    insert_header = True
    if pkg_idx + 1 < len(lines) and lines[pkg_idx+1].strip().startswith('// Classe '):
        insert_header = False
    if insert_header:
        lines.insert(pkg_idx+1, header_comment)
    # insert method comments
    i = 0
    while i < len(lines):
        m = method_regex.match(lines[i])
        if m:
            indent, vis, rettype, mname, params = m.groups()
            # check previous non-empty line for comment
            prev = i-1
            while prev >= 0 and lines[prev].strip() == '':
                prev -= 1
            already_comment = False
            if prev >= 0 and lines[prev].strip().startswith('//'):
                already_comment = True
            if not already_comment:
                method_comment = f"{indent}// Método {mname}: {vis} {mname} — descrição breve (automático)"
                lines.insert(i, method_comment)
                i += 1
        i += 1
    new_text = '\n'.join(lines) + '\n'
    if new_text != text:
        f.write_text(new_text, encoding='utf-8')
        modified.append(str(f))

print('Modified files:', len(modified))
for m in modified:
    print(m)
