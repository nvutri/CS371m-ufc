import re
FIGHTER = 'fighters'
REGEX = 'id\/(\d+)\/(\w+)-(\w+)'
fo = open(FIGHTER, 'r')
line = fo.readline()
while (line):
	infos = re.findall(REGEX, line)
	for info in infos:
		print "%s\t%s\t%s" % info
	line = fo.readline()
