/*
 * @author Tom Waterman
 * ICS 440 - PA 4
 */
public class FloydWarshallSequential {
	int[][] d = new int[5000][5000];

	public int[][] execute(int[][] d) {
		this.d = d;
		for (int k = 0; k < 5000; k++) {
			for (int i = 0; i < 5000; i++) {
				for (int j = 0; j < 5000; j++) {
					if (d[i][j] > d[i][k] + d[k][j]) {
						d[i][j] = d[i][k] + d[k][j];
					}
				}
			}
			//return d;
		}
		return d;
	}
}
