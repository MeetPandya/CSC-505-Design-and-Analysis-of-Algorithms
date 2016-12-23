Write a program named huffman.c (or huffman.cpp, huffman.java or huffman.py, as appropriate). This
program will read a sequence of bytes from standard input until it reaches the end-of-file. Your program
will build a Huffman code based on the frequency of byte values seen in the input.
We want a code that will be able to encode any sequence of bytes (not just the particular byte values
that happened to show up in the input), so your program will create codes for every possible byte
value, plus one more code to represent the end of the encoding. When you build your Huffman code,
assume the following frequencies:
• If character c didn’t occur in the input, it will be given a frequency of 1. So, character c will have
a code, but it will probably be a long code, since we’re giving it such a low frequency.
• If character c occurred f times in the input, it will be given a frequency of f + 1. So, characters
that occur in the input will have a higher frequency than characters that don’t occur.
• An imaginary symbol, EOF will be given a frequency of 1. This will give us a code we can use
to mark the end of file in the compressed representation. Remember, since a Huffman code is a
variable length code, the last code may end part-way through the last byte of a compressed file.
Having a code for EOF gives us a way to mark where the sequence of codewords ends, even if it
doesn’t fall right at the end of the last byte.
Recall that the algorithm for generating Huffman codes uses a priority queue of nodes, so it can easily
choose the two lowest-frequency nodes. For this, you will use your trinary heap from the previous
assignment. You’ll need to modify it some; previously it just had to store key values, but now it will
have to store the node you’re usinging to build your Huffman coding tree (or, more likely, pointers to
nodes).
Once you’ve determined codewords for every value, your program will print out a table of Huffman
codes for each possible byte value, and an extra code for marking the end-of-file. Print your table with
the byte value right-justified in a 3-column field to the left, then a space, then the Huffman code as a
sequence of bits (a string of 1 and 0 characters). For bytes between 33 and 126 (inclusive), assume the
byte represents the ASCII code for a character and print it out as a one-character symbol. For other
byte values, just print a 2-character hexadecimal value for this byte. At the end, print the code for
marking the end-of-file. For this code, just print EOF in the left-hand column
