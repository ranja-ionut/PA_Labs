import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
    static class Task {
        public final static String INPUT_FILE = "in";
        public final static String OUTPUT_FILE = "out";

        int n;
        int[] v;

        private final static int MOD = 1000000007;

        private void readInput() {
            try {
                Scanner sc = new Scanner(new File(INPUT_FILE));
                n = sc.nextInt();
                v = new int[n + 1];
                for (int i = 1; i <= n; i++) {
                    v[i] = sc.nextInt();
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
            // TODO: Aflati numarul de subsiruri (ale sirului stocat in v,
            // indexat de la 1 la n), nevide cu suma numerelor para.
            // Rezultatul se va intoarce modulo MOD (1000000007).
        	int rez1 = 0, rez2 = 0;
        	int kMod = (int) (1e9 + 7);
        	int[] even = new int[n+1];
        	int[] odd = new int[n+1];
        	//primul punct
        	even[0] = 1;
            odd[0] = 0;

            for (int i = 1; i <= n; ++i) {
                if (v[i] % 2 != 0) {
                   odd[i] = even[i] = (int) ((1L * even[i - 1] + odd[i - 1]) % kMod);
                } else {
                    even[i] = (int) ((1L * even[i - 1] * 2) % kMod);
                    odd[i] = (int) ((1L * odd[i - 1] * 2) % kMod);
                }
            }

            rez1 = even[n] - 1;
	        
        	// al treilea punct
        	int pare = 0, impare = 0;
        	for(int i = 1; i <= n; i++) {
        		if(v[i] % 2 == 0)
        			pare++;
        		else
        			impare++;
        	}
        	if(impare == 0)
        		rez = (int) (1L * ((Math.pow(2, pare))) % 1000000007);
        	else
        		rez = (int) (1L * ((Math.pow(2, pare + impare - 1))) % 1000000007);
        	
        	if(true)
        		return rez;
        	else
        		return rez;
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
