import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Main {
    static class Homework {
        public int deadline;
        public int score;

        public Homework() {
            deadline = 0;
            score = 0;
        }
    }

    static class Task {
        public final static String INPUT_FILE = "in";
        public final static String OUTPUT_FILE = "out";

        int n;
        Homework[] hws;

        private void readInput() {
            try {
                Scanner sc = new Scanner(new File(INPUT_FILE));
                n = sc.nextInt();
                hws = new Homework[n];
                for (int i = 0; i < n; i++) {
                    hws[i] = new Homework();
                    hws[i].deadline = sc.nextInt();
                    hws[i].score = sc.nextInt();
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
            // TODO: Aflati punctajul maxim pe care il puteti obtine
            // planificand optim temele.
        	int max=0;
        	for(int i=0;i<n;i++) {
        		if(hws[i].deadline>max) {
        			max=hws[i].deadline;
        		}
        	}
        	Arrays.sort(hws,new Comparator<Homework>() {

				@Override
				public int compare(Homework t1, Homework t2) {
					if(t1.deadline<t2.deadline) {
						return 1;
					}
					else if(t1.deadline>t2.deadline) {
						return -1;
					}
					else {
						if(t1.score<t2.score) {
							return 1;
						}
						else if(t1.score>t2.score) {
							return -1;
						}
					}
					return 0;
				}
        	});
        	for(Homework t:hws) {
        		System.out.println(t.deadline+"<-dead score->"+t.score);
        	}
        	int score=0, time=0;
        	for(int i=0;i<n;i++) {
        		if(hws[i].deadline>=time||max!=0) {
        			score+=hws[i].score;
        			time++;
        			max--;
        		}
        	}
            return score;
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
