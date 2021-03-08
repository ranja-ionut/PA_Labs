import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
	static class Task {
		public final static String INPUT_FILE = "in";
		public final static String OUTPUT_FILE = "out";

		int base;
		int exponent;
		int mod;

		private void readInput() {
			try {
				Scanner sc = new Scanner(new File(INPUT_FILE));
				base = sc.nextInt();
				exponent = sc.nextInt();
				mod = sc.nextInt();
				sc.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private void writeOutput(long result) {
			try {
				PrintWriter pw = new PrintWriter(new File(OUTPUT_FILE));
				pw.printf("%d\n", result);
				pw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private int fastPow(int base, int exponent, int mod) {
			int res = 1;

			while (exponent!=0) {
	            if ((exponent & 1) != 0) {
	                res = (int) ((1L * res * base) % mod);
	            }

	            base = (int) ((1L * base * base) % mod);
			    exponent >>= 1;
			}

			return res;
		}

		public void solve() {
			readInput();
			long result = fastPow(base, exponent, mod);
			writeOutput(result);
		}
	}

	public static void main(String[] args) {
		new Task().solve();
	}
}
