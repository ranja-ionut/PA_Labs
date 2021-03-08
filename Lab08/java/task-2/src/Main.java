import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashSet;

public class Main {
	static class Task {
		public static final String INPUT_FILE = "in";
		public static final String OUTPUT_FILE = "out";
		public static final int NMAX = 100005; // 10^5

		int n;
		int m;
		
		int timp = 1;
		int[] idx = new int[NMAX], low = new int[NMAX], parinte = new int[NMAX];

		@SuppressWarnings("unchecked")
		ArrayList<Integer> adj[] = new ArrayList[NMAX];

		private void readInput() {
			try {
				Scanner sc = new Scanner(new BufferedReader(new FileReader(
								INPUT_FILE)));
				n = sc.nextInt();
				m = sc.nextInt();

				for (int i = 1; i <= n; i++)
					adj[i] = new ArrayList<>();
				for (int i = 1; i <= m; i++) {
					int x, y;
					x = sc.nextInt();
					y = sc.nextInt();
					adj[x].add(y);
					adj[y].add(x);
				}
				sc.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private void writeOutput(HashSet<Integer> result) {
			try {
				PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(
								OUTPUT_FILE)));
				for (int node : result) {
					pw.printf("%d ", node);
				}
				pw.printf("\n");
				pw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		
		private void DFSCV(int v, HashSet<Integer> sol) {
			idx[v] = timp;
			low[v] = timp;
			timp++;
			int copii = 0;
			
			for(Integer u : adj[v]) {
				if(parinte[v] != u) {
					if(idx[u] == 0) {
						copii++;
						parinte[u] = v;
							
						DFSCV(u, sol);
							
						low[v] = Math.min(low[v], low[u]);
							
						if(parinte[v] == 0 && copii > 1) {
							sol.add(v);
						}
							
						if(parinte[v] != 0 && low[u] >= idx[v]) {
							sol.add(v);
						}	
					} else {
							low[v] = Math.min(low[v], idx[u]);
					}
				}
			}
		}

		private HashSet<Integer> getResult() {
			HashSet<Integer> sol = new HashSet<>();
			
			for(int v = 1; v <= n; v++) {
				if(idx[v] == 0) {
					DFSCV(v, sol);
				}
			}
			
			return sol;
		}

		public void solve() {
			readInput();
			writeOutput(getResult());
		}
	}

	public static void main(String[] args) {
		new Task().solve();
	}
}
