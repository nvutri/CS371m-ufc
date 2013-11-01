import re
import json

FILE_LOCATION = '../database/FIGHTERS_RECORD'
fo = open(FILE_LOCATION, 'r')
line = fo.readline() # First line is the header of the table.
line = fo.readline()
result = [];
while (line):
	fighter = {}
	fields = line.strip().split('\t')
	fighter["espnId"] = int(fields[0])
	fighter["firstName"] = fields[1]
	fighter["lastName"] = fields[2]
	fighter["wins"] = int(fields[3])
	fighter["submission"] = int(fields[4])
	fighter["ko"] = int(fields[5])
	fighter["losses"] = int(fields[6])
	result.append(fighter)
	line = fo.readline()
print json.dumps(result)
