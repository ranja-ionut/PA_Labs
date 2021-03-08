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
		
		int index = 0;
		int[] culoare = new int[NMAX];
		
		LinkedList<Integer> S = new LinkedList<>();
		
		ArrayList<ArrayList<Integer>> sol = new ArrayList<>();
		
		@SuppressWarnings("unchecked")
		ArrayList<Integer> adj[] = new ArrayList[NMAX];
		@SuppressWarnings("unchecked")
		ArrayList<Integer> adjt[] = new ArrayList[NMAX];

		private void readInput() {
			try {
				Scanner sc = new Scanner(new BufferedReader(new FileReader(
								INPUT_FILE)));
				n = sc.nextInt();
				m = sc.nextInt();

				for (int i = 1; i <= n; i++) {
					adj[i] = new ArrayList<>();
					adjt[i] = new ArrayList<>();
				}
				for (int i = 1; i <= m; i++) {
					int x, y;
					x = sc.nextInt();
					y = sc.nextInt();
					adj[x].add(y);
					adjt[y].add(x);
				}
				sc.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private void writeOutput(ArrayList<ArrayList<Integer>> result) {
			try {
				PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(
								OUTPUT_FILE)));
				pw.printf("%d\n", result.size());
				for (ArrayList<Integer> ctc : result) {
					for (int nod : ctc) {
						pw.printf("%d ", nod);
					}
					pw.printf("\n");
				}
				pw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		
		private void DFS (int v) {
			culoare[v] = 1;
			
			for(Integer u : adj[v]) {
				if(culoare[u] == 0) {
					DFS(u);
				}
			}
			
			S.push(v);
			culoare[v] = 2;
		}
		
		private void DFST(int v, ArrayList<Integer> helper) {
			culoare[v] = 1;
			helper.add(v);
			
			for(Integer u : adjt[v]) {
				if(culoare[u] == 0) {
					DFST(u, helper);
				}
			}
		
			culoare[v] = 2;
		}

		private ArrayList<ArrayList<Integer>> ctc_kosaraju() {
			// TODO: Gasiti componentele tare conexe ale grafului orientat cu
			// n noduri, stocat in adj. Rezultatul se va returna sub forma
			// unui ArrayList, ale carui elemente sunt componentele tare conexe
			// detectate. Nodurile si componentele tare conexe pot fi puse in orice
			// ordine in arraylist.
			//
			// Atentie: graful transpus este stocat in adjt.
			
			for(int v = 1; v <= n; v++) {
				if(culoare[v] == 0) {
					DFS(v);
				}
			}
			
			for(int i = 1; i <= n; i++) {
				culoare[i] = 0;
			}
			
			int v;
			
			
			while(!S.isEmpty()) {
				v = S.pop();
				if(culoare[v] == 0) {
					ArrayList<Integer> helper = new ArrayList<>();
					DFST(v, helper);
					sol.add(helper);
				}
			}
			
			return sol;
		}

		public void solve() {
			readInput();
			writeOutput(ctc_kosaraju());
		}
	}

	public static void main(String[] args) {
		new Task().solve();
	}
}
