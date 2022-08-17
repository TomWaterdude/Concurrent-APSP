# Multithreaded-APSP (All Pairs Shortest Path)
A concurrent execution of the Floyd Warshall algorithm.

The objective of this assignment was to introduce a level of concurrency to the Floyd Warshall algorithm for determining the shortest distance between any two nodes using all available paths in a weighted and directed graph. The algorithm is guaranteed to provide the correct results (so long as there is no negative weight cycle within the graph) however, the algorithm has a worst case run time of O(v^3). For graphs or systems with a large number of nodes, this is a costly runtime. 

With the use of thread pools, concurrency can be introduced and tasks can be mapped to available processors on multi processor machines, allowing for more than one distance between pairs to be calculated simultaneously. 

The program generates a random matrix of dimensions 5,000 x 5,000 and then computes the shortest distance between all points from both a sequential and concurrent execusion, and compares the overall runtime between the two executions once both solutions have been solved. 

Along with this README file, there is also a .pdf file that contains a written disection of this problem. Partiioning logic, communication, agglomeration,  and mapping are discussed, along with a discussion on an initially failed design, logic for reimplementation, and final results.  
