import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
    static class Task {
        public final static String INPUT_FILE = "in";
        public final static String OUTPUT_FILE = "out";

        private final static int MOD = 1000000007;

        int n;
        String expr;
        private void readInput() {
            try {
                Scanner sc = new Scanner(new File(INPUT_FILE));
                n = sc.nextInt();
                expr = sc.next().trim();
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
            // TODO: Calculati numarul de moduri in care se pot aseza
            // parantezele astfel incat rezultatul expresiei sa fie TRUE.
            // Numarul de moduri se va calcula modulo MOD (1000000007).
        	// Separa operatorii de operanzi
            char[] helper = expr.toCharArray();
            
            // Operatorii si operanzii au cam jumatate din numarul total de caractere
            // asa ca las numarul maxim ca size
            char[] operators = new char[expr.length()];
            char[] operands = new char[expr.length()];
            
            int n_opt = 0, n_opr = 0;
            
            for(int i = 0; i < n; i++){
                if(helper[i] == 'T' || helper[i] == 'F'){
                    operators[n_opt++] = helper[i];
                }
                else{
                    operands[n_opr++] = helper[i];
                }
            }
            
            /*
                Pentru a calcula numarul de parantezari posibile pentru ca expresia sa fie evaluata ca TRUE
                vom folosi 2 matrici in care lucram doar cu ce este deasupra diagonalei principale, asemanator
                cu inmultirea matricilor.
                Fie cele 2 matrici T de la TRUE si F de la FALSE.
                T si F vor avea dimensiunea numarului de operatori.
            */
            
            long[][] T = new long[n_opt][n_opt];
            long[][] F = new long[n_opt][n_opt];
            
            /*
                Cazul de baza ar fi umplerea diagonalei principale cu 0 sau cu 1, in functie de ce tip de 
                operator intalnim. Completarea se face astfel:
                
                T[i][i] = 1 daca avem `T` si 0 daca avem `F`
                F[i][i] = 1 daca avem `F` si 0 daca avem `T`
            */
            
            for(int i = 0; i < n_opt; i++){
                if(operators[i] == 'T'){
                    T[i][i] = 1;
                    F[i][i] = 0;
                }
                else{
                    T[i][i] = 0;
                    F[i][i] = 1;
                }
            }
            
            /*
                Mai departe consideram ca T[i][j] reprezinta numarul de moduri prin care putem pune paranteze,
                astfel incat expresiile de la i la j sa aiba valoare de adevar.
                In mod similar F[i][j] este numarul de parantezari astfel incat expresiile de la i la j sa fie
                false.
                
                De exemplu daca luam expresia de la i la j fiind T F T F si avem operanzii ^ & |, atunci
                T[i][j] va avea valoarea 2 pentru parantezele ((T ^ F) & (T | F))  si (((T ^ F) & T) | F),
                in orice alt mod ar exista variante cu care sa ne dea FALSE, iar F[i][j] va fi 0, deoarece 
                expresia avand valoarea TRUE oricum se pun parantezele - verificat de tabela de adevar, expresia
                nu poate lua valoarea FALSE.
                
                Vom realiza o completare progresiva, calculand T[i][j] si F[i][j] folosind un intermediar k
                intre i si j, variindu-l pe lungimi diferite.
            */
            int k;
            
            for(int lungime = 1; lungime < n_opt; lungime++){
                for(int i = 0, j = lungime; j < n_opt; i++, j++){ // crestem spatiul de evaluare [i; j] dupa lungime
                    for(int aux = 0; aux < lungime; aux++){
                        k = aux + i; // k se va muta stanga->dreapta in [i; j]       
                        switch(operands[k]){ // evaluam expresia
                            case '&':
                                T[i][j] = (int) ((1L * T[i][k] * T[k + 1][j] + T[i][j]) % 1000000007);
                                // & intre expresia de la i la k, si de la k+1 la j
                                // automat, poate da true numai cand ambele sunt true, fiind singura din cele
                                // 4 posibile cu rezultat true
                                
                                F[i][j] = (int) ((((T[i][k] + F[i][k]) * (T[k + 1][j] + F[k + 1][j] * 1L)) 
                                    - 1L *T[i][k] * T[k + 1][j] + F[i][j]) % 1000000007);
                                // scazi din numarul total de expresii, expresia cu rezultat TRUE
                            break;
                                
                            case '|':
                                F[i][j] = (int) ((1L *F[i][k] * F[k + 1][j] + F[i][j]) % 1000000007); 
                                // | intre expresia de la i la k, si de la k+1 la j
                                // automat, poate da false numai cand ambele sunt false, fiind singura expresie din cele 4
                                // posibile cu rezultat fals
                                
                                T[i][j] = (int) ((((T[i][k] + F[i][k]) * (T[k + 1][j] + F[k + 1][j]) * 1L) 
                                    - 1L * F[i][k] * F[k+1][j] + T[i][j]) % 1000000007);
                                // scazi din numarul total de expresii, expresia cu rezultat FALSE
                            break;
                                
                            case '^':
                                T[i][j] = (int) ((1L * F[i][k] * T[k + 1][j] + 1L * T[i][k] * F[k + 1][j] 
                                                 + T[i][j]) % 1000000007);
                                // ^ poate da true numai cand expresiile din stanga si din dreapta sunt pe rand
                                // TRUE si FALSE sau FALSE si TRUE
                                
                                F[i][j] = (int) ((1L * T[i][k] * T[k + 1][j] + 1L * F[i][k] * F[k + 1][j] 
                                                 + F[i][j]) % 1000000007);
                                // asemanator, ^ da FALSE numai cand ambele expresii au aceeasi valoare
                            break;
                        }
                    }
                }
            }
        	
            return (int) (T[0][n_opt-1]);
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
