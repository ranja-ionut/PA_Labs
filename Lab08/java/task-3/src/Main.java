import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.ArrayList;

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

		class Edge {
			int x;
			int y;
			
			Edge(int x, int y) {
				this.x = x;
				this.y = y;
			}
		}

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

		private void writeOutput(ArrayList<Edge> result) {
			try {
				PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(
								OUTPUT_FILE)));
				pw.printf("%d\n", result.size());
				for (Edge e : result) {
					pw.printf("%d %d\n", e.x, e.y);
				}
				pw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private void DFSB(int v, ArrayList<Edge> sol) {
			idx[v] = timp;
			low[v] = timp;
			timp++;
			
			for(int u : adj[v]) {
				if(parinte[v] != u) {
					if(idx[u] == 0) {
						parinte[u] = v;
						
						DFSB(u, sol);
						
						low[v] = Math.min(low[v], low[u]);
						
						if(low[u] > idx[v]) {
							sol.add(new Edge(v, u));
						}
					} else {
						low[v] = Math.min(low[v], idx[u]);
					}
				}
			}
		}
		
		private ArrayList<Edge> getResult() {
			// TODO: Gasiti muchiile critice ale grafului neorientat stocat cu liste
			// de adiacenta in adj.
			ArrayList<Edge> sol = new ArrayList<>();
			
			for(int v = 1; v <= n; v++) {
				if(idx[v] == 0) {
					DFSB(v, sol);
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
