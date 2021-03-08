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
		public static final int NMAX = 100005; // 10^5

		int n;
		int m;

		@SuppressWarnings("unchecked")
		ArrayList<Integer> adj[] = new ArrayList[NMAX];

		int source;

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
					int x, y;
					x = sc.nextInt();
					y = sc.nextInt();
					adj[x].add(y);
					adj[y].add(x);
				}
				sc.close();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		private void writeOutput(int[] result) {
			try {
				PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(
								OUTPUT_FILE)));
				for (int i = 1; i <= n; i++) {
					pw.printf("%d%c", result[i], i == n ? '\n' : ' ');
				}
				pw.close();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		private int[] getResult() {
			int d[] = new int[n + 1];
            int c[] = new int[n + 1];
            int p[] = new int[n + 1];
			for (int i = 0; i <= n; i++){
                d[i] = -1;
                c[i] = 0;
                p[i] = -1;
            }
            d[source] = 0;
            c[source] = 0;
            
            LinkedList<Integer> queue = new LinkedList<>();
            queue.addLast(source);
            
            while(queue.size() > 0){
                int u = queue.peek();
                
                for(Integer v : adj[u]){
                    if(c[v] == 0){
                        d[v] = d[u] + 1;
                        p[v] = u;
                        c[v] = 1;
                        queue.addLast(v);
                    }
                }
                
                c[u] = 2;
                queue.removeFirst();
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