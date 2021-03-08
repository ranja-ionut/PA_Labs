import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Comparator;
import java.awt.Point;

public class Main {
    static class Task {
        public static final String INPUT_FILE = "in";
        public static final String OUTPUT_FILE = "out";
        public static final int NMAX = 100005; // 10^5

        int n;
        int m;
        int[] c, p, d, f;
        int time = 0;

        @SuppressWarnings("unchecked")
        ArrayList<Integer> adj[] = new ArrayList[NMAX];

        private void readInput() {
            try {
                Scanner sc = new Scanner(new BufferedReader(new FileReader(
								INPUT_FILE)));
                n = sc.nextInt();
                m = sc.nextInt();

                c = new int[n + 1];
                p = new int[n + 1];
                d = new int[n + 1];
                f = new int[n + 1];
                
                for (int i = 1; i <= n; i++)
                    adj[i] = new ArrayList<>();
                for (int i = 1; i <= m; i++) {
                    adj[sc.nextInt()].add(sc.nextInt());
                }
                sc.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        private void writeOutput(ArrayList<Integer> result) {
            try {
                PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(
								OUTPUT_FILE)));
                for (int i = 0; i < result.size(); i++) {
                    pw.printf("%d ", result.get(i));
                }
                pw.printf("\n");
                pw.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        
        private void explorare(int u){
            d[u] = time++;
            c[u] = 1;
            for(Integer v : adj[u]){
                if(c[v] == 0){
                    p[v] = u;
                    explorare(v);
                }
            }
            c[u] = 2;
            f[u] = time++;
        }
        
        private void DFS(){
            for(int i = 1; i <= n; i++){
                c[i] = 0;
                p[i] = -1;
            }
            
            for(int i = 1; i <= n; i++){
                if(c[i] == 0){
                    explorare(i);
                }
            }
        }

        private ArrayList<Integer> getResult() {
            DFS();
            
            ArrayList<Integer> topsort = new ArrayList<>();
            
            Point[] topo = new Point[n];
            
            for(int i = 1; i <= n; i++){
                topo[i - 1] = new Point(i, f[i]); 
            }
            
            java.util.Arrays.sort(topo, (a, b) -> ((int)b.getY() - (int)a.getY()));
            
            for(int i = 0; i < n; i++){
                topsort.add((int)topo[i].getX()); 
            }
            
            return topsort;
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