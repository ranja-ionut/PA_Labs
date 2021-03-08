import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
	static class Task {
		public final static String INPUT_FILE = "in";
		public final static String OUTPUT_FILE = "out";

		double n;

		private void readInput() {
			try {
				Scanner sc = new Scanner(new File(INPUT_FILE));
				n = sc.nextDouble();
				sc.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private void writeOutput(double x) {
			try {
				PrintWriter pw = new PrintWriter(new File(OUTPUT_FILE));
				pw.printf("%.4f\n", x);
				pw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		double computeSqrt() {
			double left, right;
			if(n<1) {
				left=n;
				right=1;
			}
			else {
				left=1;
				right=n;
			}
			double mid=(left+right)/2;
			double eps = 0.0001f;
			while (left <= right) {
			    mid = (left + right) / 2;

			    if (Math.abs(mid * mid - n) < eps) {
	                return mid;
			    }

			    if (mid * mid < n) {
			        left = mid;
			    } else {
			        right = mid;
			    }
			}
			return mid;
		}
		public void solve() {
			readInput();
			writeOutput(computeSqrt());
		}
	}

	public static void main(String[] args) {
		new Task().solve();
	}
}
