from pathlib import Path
root = Path('src/main/java')
java_files = list(root.rglob('*.java'))
modified = []
for f in java_files:
    text = f.read_text(encoding='utf-8')
    new = text.replace('(automático)', '').replace('(comentário automático)', '')
    # Also remove double spaces caused by deletion
    new = new.replace('  ', ' ')
    if new != text:
        f.write_text(new, encoding='utf-8')
        modified.append(str(f))
print('Modified files:', len(modified))
for m in modified:
    print(m)
