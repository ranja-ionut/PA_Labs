import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {
	static class Task {
		public static final String INPUT_FILE = "in";
		public static final String OUTPUT_FILE = "out";
		public static final int NMAX = 50005;
		public static final int CMAX = Integer.MAX_VALUE/2;

		int n;
		int m;
		int source;
		int[] dist = new int[NMAX];

		public class Edge {
			public int node;
			public int cost;

			Edge(int _node, int _cost) {
				node = _node;
				cost = _cost;
			}
		}

		@SuppressWarnings("unchecked")
		ArrayList<Edge> adj[] = new ArrayList[NMAX];

		private void readInput() {
			try {
				Scanner sc = new Scanner(new BufferedReader(new FileReader(
								INPUT_FILE)));
				n = sc.nextInt();
				m = sc.nextInt();
				source = sc.nextInt();

				for (int i = 1; i <= n; i++)
					adj[i] = new ArrayList<>();
				for (int i = 1; i <= m; i++) {
					int x, y, w;
					x = sc.nextInt();
					y = sc.nextInt();
					w = sc.nextInt();
					adj[x].add(new Edge(y, w));
				}
				sc.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private void writeOutput(ArrayList<Integer> result) {
			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter(
								OUTPUT_FILE));
				StringBuilder sb = new StringBuilder();
				if (result.size() == 0) {
					sb.append("Ciclu negativ!\n");
				} else {
					for (int i = 0; i < n; i++) {
						sb.append(result.get(i)).append(' ');
					}
					sb.append('\n');
				}
				bw.write(sb.toString());
				bw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		
		private boolean BellmanFord () {
			for(int v = 1; v <= n; v++) {
				dist[v] = CMAX;
			}
			
			dist[source] = 0;
			
			for(int v = 1; v <= n - 1; v++) {
				for(int u = 1; u <= n; u++) {
					if(dist[u] != CMAX) {
						for(Edge e : adj[u]) {
							if(dist[e.node] > dist[u] + e.cost) {
								dist[e.node] = dist[u] + e.cost;
							}
						}
					}
				}
			}
			
			for(int v = 1; v <= n; v++) {
				for(Edge e : adj[v]) {
					if(dist[v] != CMAX && dist[e.node] > dist[v] + e.cost)
						return false;
				}
			}
			
			return true;
		}

		private ArrayList<Integer> getResult() {
			// TODO: Gasiti distantele minime de la nodul source la celelalte noduri
			// folosind BellmanFord pe graful orientat cu n noduri, m arce stocat in
			// adj.
			//	d[node] = costul minim / lungimea minima a unui drum de la source la
			//	nodul node;
			//	d[source] = 0;
			//	d[node] = -1, daca nu se poate ajunge de la source la node.

			// Atentie:
			// O muchie este tinuta ca o pereche (nod adiacent, cost muchie):
			//	adj[x].get(i).node = nodul adiacent lui x,
			//	adj[x].get(i).cost = costul.

			// In cazul in care exista ciclu de cost negativ, returnati un vector gol:
			//	return new ArrayList<Integer>();
			ArrayList<Integer> d = new ArrayList<>();
			
			if(!BellmanFord())
				return d;
			
			for(int i = 1; i <= n; i++) {
				if(dist[i] == CMAX)
					d.add(-1);
				else
					d.add(dist[i]);
			}
			
			return d;
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
