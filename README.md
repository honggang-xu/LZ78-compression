# LZ78-compression
LZ78 compression and decompression for given file

LZencode takes inputs from System.in, then encodes the inputs and outputs a file "encoded.txt". It uses the LZ78 algorithm to output pairs, where each pair consists of a phrase number and a mismatched byte, one pair per line of output.
* To use LZencode, type command:

  java LZencode < \<file name>

LZdecode takes a file to decode and a file name for the decoded output The output should exactly match the input to the encoder.
* To use LZdecode, type command:

  java LZdecode \<encoded file> \<file name for decoded output>

LZpack takes the output of LZencode and encode the input as few bits as are needed. It outputs a file "packed.txt".
* To use LZpack, type command:

  java LZpack \<file name>

LZunpack takes the output of LZpack and restore it to match the output of LZencode
* To use LZunpack, type command:

  java LZunpack < \<file name>
