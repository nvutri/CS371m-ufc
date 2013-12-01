import re
import json

FILE_LOCATION = '../database/FIGHTER_NAMES'
fo = open(FILE_LOCATION, 'r')
line = fo.readline()
fighters = json.loads(line)
for fighter in fighters:
	print fighter[0]
