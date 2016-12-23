Implementation of the Edmonds-Karp algorithm for maximum flow.
Graph Input
Input will come from standard input and will start with a pair of numbers, n and m, giving the number
of vertices and a number of directed edges in the graph. These two numbers will be followed by m
2
lines, each describing an edge with 3 integer values. The first two values on each line, i j, give the
endpoints of the edge ( i and j in 0 . . . n − 1). The third value gives the non-negative integer weight
of the edge from i to j. The value of n will always be two or greater. Vertex zero is always the source
and vertex one is always the sink. The graph will never contain anti-parallel edges
