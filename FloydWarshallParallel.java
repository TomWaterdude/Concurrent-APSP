/*
 * @author Tom Waterman
 * 
 */

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class FloydWarshallParallel {
	private int [][] d = new int [5000][5000];
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int[][] execute(int[][] d, int k, ExecutorService exec) {
		/*
		 * Passing in variables from Driver class
		 */
		this.d = d;
		ExecutorService executor = exec;
		int currentK = k;
		
		/*
		 * Create a list to hold all future references, so that all threads can be joined at the end.
		 * Pass the work to the private class that can view the matrix values, which updates distances when
		 * necessary.
		 */
		List<Future<Object>> futureList = new LinkedList<Future<Object>>();
		for (int i = 0; i < 5000; i ++) {
			Callable<Object> callable = new FloydTask(i, currentK);
			Future future = executor.submit(callable);
			futureList.add(future);
			
		}
		/*
		 * future.get() on all futures ensures that all threads have completed the work before exiting 
		 * execute(), and beginning next iteration of k from Driver method
		 */
		for (Future f : futureList) {
			try {
				f.get();
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
		
		return d;
	}
	
	
	/*
	 * Private task to do some work to matrix.
	 * @return a generic Object of null value, no data needs to be collected,
	 * but threads from thread pool will want to be joined, so get() will 
	 * be called. get() is used by a future, so a generic object must be returned 
	 * for this level of control.
	 */
	private class FloydTask implements Callable<Object>{
		int i, k;
		
		private FloydTask(int i, int k) {
			this.i = i;
			this.k = k;
			//this.j = j;
		}
		@Override
		public Object call() throws Exception {
			for (int j = 0; j < 5000; j ++) {
				if (d[i][j] > d[i][k] + d[k][j]) { 
					d[i][j] = d[i][k] + d[k][j];
				}
			}
			return null;
		}
		
	}
}
