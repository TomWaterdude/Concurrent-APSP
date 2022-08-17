/*
 * @author Tom Waterman
 * ICS 440 - PA 4
 */

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Driver {
	private static final int I = Integer.MAX_VALUE; // Infinity
    private static final int dim = 5000;
    private static double fill = 0.3;
    private static int maxDistance = 100;
    private static int adjacencyMatrix[][] = new int[dim][dim];
    private static int d[][] = new int[dim][dim];
    private static final int numThreads = 10;

    /*
     * Generate a randomized matrix to use for the algorithm.
     */
    private static void generateMatrix() {
        Random random = new Random();
        for (int i = 0; i < dim; i++)
        {
            for (int j = 0; j < dim; j++)
            {
                if (i != j)
                    adjacencyMatrix[i][j] = I;
            }
        }
        for (int i = 0; i < dim * dim * fill; i++)
        {
            adjacencyMatrix[random.nextInt(dim)][random.nextInt(dim)] =
                random.nextInt(maxDistance + 1);
        }
        
        /*
         * Assign the values of the adjacency matrix to d[][]
         */
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++)
            {
                d[i][j] = adjacencyMatrix[i][j];
                if (i == j)
                {
                    d[i][j] = 0;
                }
            }
        }
    }
    /*
     * Method for comparing two matrices.
     * 
     * @param matrix1 and matrix2 are the two matrices to compare.
     * @return a string message indicating outcome.
     */
	private static void compare(int matrix1[][], int matrix2[][]) {
		for (int i = 0; i < dim; i++) {
			for (int j = 0; j < dim; j++) {
				if (matrix1[i][j] != matrix2[i][j]) {
					System.out.println("Comparison failed");
				}
			}
		}
		System.out.println("Comparison succeeded");
	}
    
	public static void main(String[] args) {
		long start,end;
		int[][] parallelMatrix = new int[dim][dim];
		int[][] sequentMatrix = new int[dim][dim];
		
		generateMatrix();
		
		start = System.nanoTime();
		ExecutorService executor = Executors.newFixedThreadPool(numThreads);
		FloydWarshallParallel fwp = new FloydWarshallParallel();
		for (int k = 0; k < dim; k ++) {
			d = fwp.execute(d, k, executor);
		}
		parallelMatrix = d;
		executor.shutdown();
		end = System.nanoTime();
		System.out.println("time consumed to process in parallel: " + (double) (end - start) / 1000000000);
		
		start = System.nanoTime();
		FloydWarshallSequential fws = new FloydWarshallSequential();
		sequentMatrix = fws.execute(d);
		end = System.nanoTime();
		System.out.println("time consumed to process sequentially: " + (double) (end - start) / 1000000000);
		compare(sequentMatrix, parallelMatrix);
	}

}
