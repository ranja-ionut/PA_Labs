import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.LinkedList;

public class Main {
	static class Task {
		public static final String INPUT_FILE = "in";
		public static final String OUTPUT_FILE = "out";
		public static final int NMAX = 1005;

		int n;
		int m;
		
		int parent[];
		boolean visited[];
		
		@SuppressWarnings("unchecked")
		ArrayList<Integer> adj[] = new ArrayList[NMAX];
		int C[][];

		private void readInput() {
			try {
				Scanner sc = new Scanner(new BufferedReader(new FileReader(
								INPUT_FILE)));
				n = sc.nextInt();
				m = sc.nextInt();
				
				parent = new int[n + 1];
				visited = new boolean[n + 1];

				C = new int[n + 1][n + 1];
				for (int i = 1; i <= n; i++) {
					adj[i] = new ArrayList<>();
				}
				for (int i = 1; i <= m; i++) {
					int x, y, z;
					x = sc.nextInt();
					y = sc.nextInt();
					z = sc.nextInt();
					adj[x].add(y);
					adj[y].add(x);
					C[x][y] += z;
				}
				sc.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private void writeOutput(int result) {
			try {
				PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(
								OUTPUT_FILE)));
				pw.printf("%d\n", result);
				pw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		
		boolean BFS() {
	        LinkedList<Integer> queue = new LinkedList<Integer>();
	        queue.add(1);
	        
	        visited[1] = true;
	        parent[1] = -1;
	        
	        for(int i = 2; i <= n; i++) {
	        	visited[i] = false;
	        }
	        
	        while (!queue.isEmpty()) {
	            int u = queue.remove();
	            
	            for (int v : adj[u]) {
	                if (visited[v] == false && C[u][v] > 0) {
	                    queue.add(v);
	                    parent[v] = u;
	                    visited[v] = true;
	                }
	            }
	        }
	        
	        if (visited[n] == true) {
	        	return true;
	        } else {
	        	return false;
	        }
	    }

		private int getResult() {
			// TODO: Calculati fluxul maxim pe graful orientat dat.
			// Sursa este nodul 1.
			// Destinatia este nodul n.
			//
			// In adj este stocat graful neorientat obtinut dupa ce se elimina orientarea
			// arcelor, iar in C sunt stocate capacitatile arcelor.
			// De exemplu, un arc (x, y) de capacitate z va fi tinut astfel:
			// C[x][y] = z, adj[x] contine y, adj[y] contine x.
			int flow = 0;
			
			int u, v;
			
			while(BFS()) {
				int currFlow = Integer.MAX_VALUE;
				
				for(v = n; v != 1; v = parent[v]) {
					u = parent[v];
					
					currFlow = Math.min(currFlow, C[u][v]);
				}
				
				flow += currFlow;
				
				for(v = n; v != 1; v = parent[v]) {
					u = parent[v];
					
					C[u][v] -= currFlow;
					C[v][u] += currFlow;
				}
			}
			
			return flow;
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
