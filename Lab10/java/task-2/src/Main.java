import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class Main {
    static class Task {
        public static final String INPUT_FILE = "in";
        public static final String OUTPUT_FILE = "out";
        public static final int NMAX = 200005;

        int n;
        int m;

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
        int[] dist = new int[NMAX], parent = new int[NMAX];

        private void readInput() {
            try {
                Scanner sc = new Scanner(new BufferedReader(new FileReader(
                                INPUT_FILE)));
                n = sc.nextInt();
                m = sc.nextInt();

                for (int i = 1; i <= n; i++)
                    adj[i] = new ArrayList<>();
                for (int i = 1; i <= m; i++) {
                    int x, y, w;
                    x = sc.nextInt();
                    y = sc.nextInt();
                    w = sc.nextInt();
                    adj[x].add(new Edge(y, w));
                    adj[y].add(new Edge(x, w));
                }
                sc.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        private void writeOutput(int result) {
            try {
                PrintWriter pw = new PrintWriter(new File(OUTPUT_FILE));
                pw.printf("%d\n", result);
                pw.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        private int getResult() {
            /*
            TODO: Calculati costul minim al unui arbore de acoperire
            folosind algoritmul lui Prim.
            */
        	
        	 int cost = 0;
             ArrayList<Boolean> selected = new ArrayList<>();
             PriorityQueue<Edge> h = new PriorityQueue<>(n, new Comparator<Edge>() {

     			@Override
     			public int compare(Edge e1, Edge e2) {
     				return e1.cost - e2.cost;
     			}
             	
             });

             for (int i = 0; i <= n; i++) {
                 dist[i] = Integer.MAX_VALUE;
                 selected.add(false);
             }
             
             dist[1] = 0;
             
             h.add(new Edge(1, 0));
             
             while (!h.isEmpty()) {
                 Edge u = h.poll();
                 selected.set(u.node, true);
                 
                 for (int i = 1; i <= adj[u.node].size(); i++) {
                     Edge v = adj[u.node].get(i - 1);
                     
                     if (dist[v.node] > v.cost && !selected.get(v.node)) {
                         dist[v.node] = v.cost;
                         h.add(v);
                     }
                 }
             }
             
             for (int i = 0; i <= n; i++) {
                 if (dist[i] != Integer.MAX_VALUE)
                     cost += dist[i];
             }
             
             return cost;
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
