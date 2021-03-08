import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
	static class Task {
		public final static String INPUT_FILE = "in";
		public final static String OUTPUT_FILE = "out";

		int n, x, y;

		private void readInput() {
			try {
				Scanner sc = new Scanner(new File(INPUT_FILE));
				n = sc.nextInt();
				x = sc.nextInt();
				y = sc.nextInt();
				sc.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private void writeOutput(int answer) {
			try {
				PrintWriter pw = new PrintWriter(new File(OUTPUT_FILE));
				pw.printf("%d\n", answer);
				pw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		private int getAnswer(int n, int x, int y) {
			// TODO: Calculati valoarea de pe pozitia (x, y) din matricea de dimensiune
			// 2^N * 2^N.
			int step = (1<< (n-1));
	        int num = 1;
	        int minL = 1, minC = 1;
	        int maxL = 2 * step, maxC = 2 * step;

	        while (step!=0) {
	            if (minL + step <= x && minC + step <= y) {
	                minL += step;
	                minC += step;
	                num += 3 * step * step;
	            } else if (minL + step <= x && minC + step >= y) {
	                minL += step;
	                maxC -= step;
	                num += 2 * step * step;
	            } else if (minL + step >= x && minC + step <= y) {
	                maxL -= step;
	                minC += step;
	                num += step  * step;
	            } else {
	                maxL -= step;
	                maxC -= step;
	            }

	            step >>= 1;
	        }

			return num;
		}

		public void solve() {
			readInput();
			writeOutput(getAnswer(n, x, y));
		}
	}

	public static void main(String[] args) {
		new Task().solve();
	}
}
