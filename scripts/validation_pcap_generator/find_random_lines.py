import json
import os

SOURCE_DIR = '[INTELLIGENCE_DIRECTORY]'

UNWANTED_FILES = [
    'Bersonal diary.txt',
    'My Favorite song.txt',
    'The very secret way everything works.txt',
    'Very Important Secret Not To Share.txt',
]

lines = []

for filename in os.listdir(SOURCE_DIR):
    if filename not in UNWANTED_FILES:
        with open(os.path.join(SOURCE_DIR, filename), 'r') as f:
            for line in f:
                if line not in lines:
                    lines.append(line.replace('\n', ''))

print(json.dumps(lines, indent=4))
