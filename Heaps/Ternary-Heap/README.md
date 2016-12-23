Ternary Heap
Problem Statement : A binary heap is a really efficient data structure, partly because you can embed it in an array without
storing or maintaining any of the pointers you’d normally use to build a tree.
Of course, this doesn’t only work for a binary heap. We can build a heap where every node can have
three children instead of just two. This is illustrated below, with each node showing its position in the
array. We still need to pack elements into the array consecutively, with a complete tree except for a
last row that may be partially full from the left.
You’re going to write a program that uses a heap like this. The root node stored in element zero in an
array, its three children will be stored in elements 1 . . . 3, and so on. You’ll need to figure out functions
that map from the index of a node to the index of its parent, and other functions that map from the
index of a node to the index of each of its children. I’ll tell you this is possible and not too difficult.
Your heap will be a min-heap, stored in a resizable array (e.g., a vector in C++ or an ArrayList in
Java). You’ll implement two functions (methods) for manipulating the heap. An insert() function will
add an arbitrary value to the heap, and a removeMin() function will remove the smallest value and
return it.
