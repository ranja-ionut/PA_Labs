import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * Generic class to represent a regular pair (C++ style).
 * 
 * Use it for storing the indexes of a grid cell like this <row, col>.
 */
class Pair<T, U> {
    public T first;
    public U second;

    public Pair(T first, U second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public boolean equals(Object other) {
        @SuppressWarnings("unchecked")
		Pair<T, U> p = (Pair<T, U>) other;
        
        return this.first.equals(p.first) && this.second.equals(p.second);
    }

    @Override
    public int hashCode() {
        int result = this.first == null ? 0 : this.first.hashCode();
        result = result * 31 + (this.second == null ? 0 : this.second.hashCode());
        return result;  
    }

    @Override
    public String toString() {
        return "<" + this.first + ", " + this.second + ">";
    }
}

public class Main {
	static int numRows, numCols;
    /**
     * Generic method to print the path to a goal state.
     *
     * Each element in the path is a pair containing a matrix cell's row and column.
     *
     * @param path        The path to a goal state.
     */
    public static <T, U> void printPath(List<Pair<T, U>> path) {
        System.out.println(path.size() - 1);
        for (int i = path.size() - 1; i >= 0; --i) {
            System.out.println(path.get(i).first + " " + path.get(i).second);
        }
    }

    /**
     * Method to implement the A* algorithm.
     *
     * @param M             Encoding of the grid.
     * @param rowPacman     Pacman's starting row.
     * @param colPacman     Pacman's starting column.
     * @param rowFood       Food row.
     * @param colFood       Fool column.
     *
     * @return              The shortest path between Pacman and food, determined with A*.
     *                      If no such path exists, returns an empty path.
     */
    public static List<Pair<Integer, Integer>> astar(List<List<Character>> M,
            						int rowPacman, int colPacman,
            						int rowFood, int colFood) {
    	List<Pair<Integer, Integer>> path = new ArrayList<>();

    	Pair<Integer, Integer> start = new Pair<>(rowPacman, colPacman);
    	Pair<Integer, Integer> goal = new Pair<>(rowFood, colFood);
    	HashMap<Pair<Integer,Integer>, Pair<Integer,Integer>> parent = new HashMap<>();
    	HashMap<Pair<Integer,Integer>, Integer> gscore = new HashMap<>();
    	HashMap<Pair<Integer,Integer>, Integer> fscore = new HashMap<>();
    	
    	PriorityQueue<Pair<Integer, Integer>> open = new PriorityQueue<>(new Comparator<Pair<Integer, Integer>>() {

			@Override
			public int compare(Pair<Integer, Integer> o1, Pair<Integer, Integer> o2) {
				return fscore.get(o1) - fscore.get(o2);
			}
    		
    	});
    	
    	for (int i = 0; i < numRows; i++) {
    		for (int j = 0; j < numCols; j++) {
    			gscore.put(new Pair<Integer, Integer>(i, j), Integer.MAX_VALUE);
    			fscore.put(new Pair<Integer, Integer>(i, j), Integer.MAX_VALUE);
    		}
    	}
    	
    	open.add(start);
    	gscore.put(start, 0);
    	fscore.put(start, gscore.get(start));
    	
    	while (!open.isEmpty()) {
    		Pair<Integer,Integer> current = open.remove();
    		
    		if(current.equals(goal)) {
    			path.add(current);
    			
    			while(parent.get(current) != null) {
    				current = parent.get(current);
    				
    				path.add(current);
    			}
    			
    			return path;
    		}
    		
    		int x = current.first, y = current.second;
    		
    		Pair<Integer, Integer> north = null, south = null, east = null, west = null;
    		
    		if (x < numRows) {
    			north = new Pair<>(x - 1, y);
    		}
    		
    		if (y < numCols) {
    			east = new Pair<>(x, y + 1);
    		}
    		
    		if (x > 0) {
    			south = new Pair<>(x + 1, y);
    		}
    		
    		if (y > 0) {
    			west = new Pair<>(x, y - 1);
    		}
    		
    		ArrayList<Pair<Integer,Integer>> neighbours = new ArrayList<>();
    		neighbours.add(north);
    		neighbours.add(south);
    		neighbours.add(east);
    		neighbours.add(west);
    		
    		for (Pair<Integer, Integer> neighbour : neighbours) {
    			if (neighbour != null) {
    				if (M.get(neighbour.first).get(neighbour.second) != '%') {
	    				int temp_gscore = gscore.get(current) + 1;
	    				
	    				if (temp_gscore < gscore.get(neighbour)) {
	    					parent.put(neighbour, current);
	    					gscore.put(neighbour, temp_gscore);
	    					fscore.put(neighbour, gscore.get(neighbour) + Math.abs(neighbour.first - current.first) + 
	    							Math.abs(neighbour.second - current.second));
	    					
	    					open.add(neighbour);
	    				}
    				}
    			}
    		}
    	}
    	
    	return path;
    }
    public static void main(String[] args) {
        List<List<Character>> M = new ArrayList<>();
        List<Pair<Integer, Integer>> path;
        int rowPacman, colPacman;
        int rowFood, colFood;
        Scanner s = new Scanner(System.in);

        rowPacman = s.nextInt();
        colPacman = s.nextInt();
        rowFood = s.nextInt();
        colFood = s.nextInt();
        numRows = s.nextInt();
        numCols = s.nextInt();

        M = new ArrayList<>();

        for (int i = 0; i < numRows; ++i) {
            List<Character> currentRow = new ArrayList<>();
            String nextRow = s.next();
            for (int j = 0; j < numCols; ++j) {
                currentRow.add(nextRow.charAt(j));
            }
            M.add(currentRow);
        }

        s.close();

        path = astar(M, rowPacman, colPacman, rowFood, colFood);
        printPath(path);
    }
}

