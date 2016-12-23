Line-Sweep Algorithm for Line Segment Intersection
You’re going to write a program, sweep.c (or .cpp, .java, .py). This program will implement the
line-sweep algorithm for finding an intersecting pair among a large set of line segments.
Like we did when we were describing the algorithm in class, you can assume no line segments are
perfectly vertical, and that no more than two line segments intersect at exactly the same point.
Input
You’ll read input from standard in. It will start with a positive integer, n. This will be followed by n
lines, each giving four real numbers, the X, Y coordinates of one endpoint of a line segment, then the
X, Y coordinates of the other endpoint.
Output
As output, print a line containing the X and Y coordinates of the point where two line segments
intersect. Round the coordinates to two fractional digits of precision, and put one space between them.
For all the inputs, there should just be one pair of line segments that intersect.
