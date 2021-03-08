import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.*;
public class Main {
    static class Obj {
        public int weight;
        public int price;

        public Obj() {
            weight = 0;
            price = 0;
        }
    };

    static class Task {
        public final static String INPUT_FILE = "in";
        public final static String OUTPUT_FILE = "out";

        int n, w;
        Obj[] objs;

        private void readInput() {
            try {
                Scanner sc = new Scanner(new File(INPUT_FILE));
                n = sc.nextInt();
                w = sc.nextInt();
                objs = new Obj[n];
                for (int i = 0; i < n; i++) {
                    objs[i] = new Obj();
                    objs[i].weight = sc.nextInt();
                    objs[i].price = sc.nextInt();
                }
                sc.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        private void writeOutput(double result) {
            try {
                PrintWriter pw = new PrintWriter(new File(OUTPUT_FILE));
                pw.printf("%.4f\n", result);
                pw.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        private double getResult() {
            // TODO: Aflati profitul maxim care se poate obtine cu
            // obiectele date.
        	Arrays.sort(objs, new Comparator<Obj>(){

				@Override
				public int compare(Obj arg0, Obj arg1) {
					double aux0 = (double)(arg0.price) / arg0.weight;
					double aux1 = (double)(arg1.price) / arg1.weight;
					if(aux0>aux1)
						return -1;
					if(aux0<aux1)
						return 1;
					return 0;
				}
        		
        	});
        	System.out.println(n+"<-n W->"+w);
        	for(Obj o:objs) {
        		System.out.println(o.price+"<-p w->"+o.weight);
        	}
			double profit=0;
			for(int i=0;i<n&&w!=0;i++) {
				if(objs[i].weight>w) {
					profit+=((double)(objs[i].price)*w)/objs[i].weight;
					w=0;
				}
				else {
					profit+=objs[i].price;
					w-=objs[i].weight;
				}
			}
            return profit;
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
