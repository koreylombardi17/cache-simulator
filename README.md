How to compile and run my java application on Windows or Linux Machine from command line

1. Navigate inside the directory named source-code inside cache-simulator from the command line. 

2. To compile use the command:
	javac *java 

3. To run use the command:
	java -Xmx2048m Main <CACHE_SIZE> <ASSOCIATIVITY> <REPLACEMENT_POLICY> <WRITE_BACK_POLICY> <TRACE_FILE>

	Replace <CACHE_SIZE> with a value between 8KB and 128KB, make sure value is a power of 2.  
	Replace <ASSOCIATIVITY> with a value between 1 and 64, make sure value is a power of 2.
	Replace <REPLACEMENT_POLICY> with a 0 to represent LRU, or a 1 to represent FIFO.
	Replace <WRITE_BACK_POLICY> with a 0 to represent write-through, or a 1 to represent write-back.
	Replace <TRACE_FILE> with MINIFE.t or XSBENCH.t.

Example: 
	javac *java
	java -Xmx2048m Main 32768 8 1 1 XSBENCH.t

Make sure the trace files are placed in the same directory as the source code. Trace files are:
	[MINIFE.t](MINIFE.t)
	XSBENCH.t
