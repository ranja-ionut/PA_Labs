import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class Main {
	static class Task {
		public static final String INPUT_FILE = "in";
		public static final String OUTPUT_FILE = "out";
		public static final int NMAX = 50005;
		public static final int CMAX = 100005;

		int n;
		int m;
		int source;
		int[] dist = new int[NMAX];
		boolean[] selectat = new boolean[NMAX];

		public class Edge implements Comparable<Edge>{
			public int node;
			public int cost;

			Edge(int _node, int _cost) {
				node = _node;
				cost = _cost;
			}

			@Override
			public int compareTo(Edge e) {
				return cost - e.cost;
			}
		}

		@SuppressWarnings("unchecked")
		ArrayList<Edge> adj[] = new ArrayList[NMAX];
		PriorityQueue<Edge> Q = new PriorityQueue<>();

		private void readInput() {
			try {
				Scanner sc = new Scanner(new BufferedReader(new FileReader(
								INPUT_FILE)));
				n = sc.nextInt();
				m = sc.nextInt();
				source = sc.nextInt();

				for (int i = 1; i <= n; i++) {
					adj[i] = new ArrayList<>();
				}
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
				for (int i = 0; i < n; i++) {
					sb.append(result.get(i)).append(' ');
				}
				sb.append('\n');
				bw.write(sb.toString());
				bw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		
		private void Dijkstra() {
			Q.add(new Edge(source, 0));
			
			for(int v = 1; v <=n; v++) {
				dist[v] = CMAX;
				selectat[v] = false;
			}
			
			dist[source] = 0;
			
			int u;
			
			while(!Q.isEmpty()) {
				u = Q.remove().node;
				
				if(!selectat[u]) {
				
					selectat[u] = true;
					
					for(Edge e : adj[u]) {
						if (selectat[e.node] == false) {
							if(dist[e.node] > dist[u] + e.cost) {
								dist[e.node] = dist[u] + e.cost;
								Q.add(new Edge(e.node, dist[e.node]));
							}
						}
					}
				}
			}
		}

		private ArrayList<Integer> getResult() {
			// TODO: Gasiti distantele minime de la nodul source la celelalte noduri
			// folosind Dijkstra pe graful orientat cu n noduri, m arce stocat in adj.
			//	d[node] = costul minim / lungimea minima a unui drum de la source la
			//	nodul node;
			//	d[source] = 0;
			//	d[node] = -1, daca nu se poate ajunge de la source la node.
			// Atentie:
			// O muchie este tinuta ca o pereche (nod adiacent, cost muchie):
			//	adj[x].get(i).node = nodul adiacent lui x,
			//	adj[x].get(i).cost = costul.
			ArrayList<Integer> d = new ArrayList<>();
			
			Dijkstra();
			
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
