EXTENSIONS=('.png','.jpeg','.jpg','jpe','.gif')

from os.path import join, dirname as d, exists

imgdir=join(d(d(d(d(__file__)))),'doc')
def mknode(handle, node):
    for ext in EXTENSIONS:
        if exists(join(imgdir, handle + ext)):
            return """%s [
    label=<<TABLE BORDER="0"><TR><TD><IMG SRC="%s%s"/></TD><TD>%s</TD></TR></TABLE>>
  ]""" % (handle,handle,ext,"<BR/>".join('%s = "%s"' % entry for entry in node))
    else:
        return '%s [label="%s"]' % (handle, node)

with open(join(d(d(d(__file__))),"test","resources","matrix.data")) as matrix:
    matrix = list(matrix)
    edges = [map(str.strip, line.strip().split(';')[1:])
             for line in matrix if line.startswith('FRIENDS')]
    nodes = dict((edge[0].replace(' ','_'),[('name',edge[0])])for edge in edges)
    nodes.update(
        dict((edge[1].replace(' ','_'),[('name',edge[1])]) for edge in edges))
    for line in matrix:
        if line.startswith('PROPERTY'):
            name, key, value = line.split(';')[1:]
            nodes[name.strip().replace(' ','_')].append(
                (key.strip(),value.strip()))
    for node in nodes.values(): node.append(("interests",[]))
    for line in matrix:
        if line.startswith('INTEREST'):
            name, interest = line.split(';')[1:]
            nodes[name.strip().replace(' ','_')][-1][1].append(interest.strip())

    print """digraph TheMatrix {
  bgcolor="transparent"
  node [
    shape=Mrecord
    fontsize=10
    style=filled
    fillcolor="#AAAAAA"
  ]
  edge [
    arrowhead=none
    label=FRIEND
    fontsize=8
  ]

  %s

  %s    
}""" % ("\n  ".join(mknode(node, nodes[node]) for node in nodes),
        "\n  ".join('%s -> %s' % tuple(node.replace(' ','_') for node in edge)
                    for edge in edges))
