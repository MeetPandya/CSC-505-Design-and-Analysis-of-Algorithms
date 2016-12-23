You’re going to write a program, allpairs.c (or .cpp, .java, .py). This program will use the
Floyd-Warshall algorithm to solve the all-pairs shortest paths problem.
We’ll be working with a graph that’s defined by a collection of words, each word representing a vertex.
Words consist of lower-case letters, and there’s an edge between two words if the they have the same
length and differ at just one character position. The weight of the edge is how far apart the differing
letters are in alphabetic order. So, for example, the words “help” and “yelp” would be connected by an
edge of weight 17 (since ’y’ is 17 characters away from ’h’ in the alphabet). There wouldn’t be an edge
between “ram” and “arm”; although they contain the same letters, they differ in the first character
position and in the second.
For this problem, you can use a dense graph representation if you want. It won’t hurt the storage cost
or running time, since the resulting shortest path matrix will be a dense representation.
Your program will read a list of words and compute the shortest path matrix. To help prove you
computed the whole matrix, you’ll print a simple statistic based on this matrix. For each word, wi
,
compute the number of other words you can reach starting from wi and traversing edges of the graph
(this should require looking at a row of the shortest path matrix). Compute the average number of
reachable words, averaged over all words in the list. Print this value rounded to two fractional digits.
Then, your program will respond to a sequence of queries. Each querry will give a pair of words from
the list. In response, your program will print out the length of the shortest path from the first word to
the second one, followed by the sequence of words on one such shortest path. If there isn’t a path, it
will print the two words, followed by “ not reachable”. See the sample execution below for an example.
For each query, there may be multiple equally short paths. Your program can print out any one of
them.
If you want, you can use the O(V^2) technique we developed in class for recovering a shortest path. Or,
if you’d like 5 points of extra credit, you can implement a linear-time technique instead. To do this,
as you fill in the table, keep a record of the value, k that achieves the shortest path between each pair
of vertices. Then, when you need to recover a shortest path from some i to j, you can first check to
see if there’s a direct edge. If not, you can lookup the vertex k that the path goes through. Then,
recursively recover a shortest path from i to k and then from k to j.
