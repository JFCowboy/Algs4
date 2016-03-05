/*------------------------------------
 *   Author:       JFCowboy
 *   Written:      10/02/2016
 *   Last Updated: 11/02/2016
 * 
 *   Update: correct the backwash Problem
 * 
 *------------------------------------*/
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;


/**
 * Clase usada para el punto 1. 
 * @author JuanFelipe
 * 
 */
public class Percolation {
	private int N; 	
	private int source; 		
  	private boolean percola;
  	/**
  	 * candidate is used to fix the problem of backwash.
  	 */
  	private boolean[] candidate;
  	private boolean[][] board;
  	private WeightedQuickUnionUF disSet;
  	/**
  	 * POS_X is a auxiliar variable to navigate in the board in the X axis.
  	 */
  	private static final int[] POS_X = {-1, 0, 1, 0};
  	/**
  	 * POS_Y is a auxiliar variable to navigate in the board i the Y axis.
  	 */
  	private static final int[] POS_Y = {0, 1, 0, -1};
    
    
	/**
	 * Create N-by-N grid, with all sites blocked, and initializes a 
	 * WeightedQuickUnionUF whit N*N+2 sites.
	 * 
	 * @param N the size of the board
	 * @throws IllegalArgumentException if <tt>N &lt; 0</tt>
	 */
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException(" The size " 
            		+ "of N will be greater than 0");
        }
        this.N = N;
        source = N * N;
        candidate = new boolean[ N * N + 1 ];
        disSet = new WeightedQuickUnionUF(N * N + 1);
        board = new boolean[N][N];
        percola = false;
    }
   
    // return a equivalent index of x and y in the UF
    private int getIndex(int x, int y) {
        return x * N + y;
    }
    
    // Validate that x and y are a valid index
    private void validate(int x, int y) {
        validate(x);
        validate(y);
    }
    
    private void validate(int x) {
        if (x <= 0 || x > N) {
            throw new IndexOutOfBoundsException(" Index out of bounds ");
        }
    }
    
    /**
     * open site (row i, column j) if it is not open already.
     * 
     * @param i the row index
     * @param j the column index
     */
    public void open(int i, int j) {      
        validate(i, j);
        i--;
        j--;
        if (board[i][j]) {
            return;
        }
        
        int x, y, pi1, pi2, auxIdx, idxNow = getIndex(i, j);
        board[i][j] = true;
        pi1 = disSet.find(idxNow);
        
        for (int k = 0; k < POS_X.length; k++) {
            x = i + POS_X[k];
            y = j + POS_Y[k];
            auxIdx = getIndex(x, y);
            if (y < 0 || y >= N) {
                continue;
            }
            if (x < 0) {
                disSet.union(source, pi1);
                pi1 = disSet.find(idxNow);
            }
            if (x >= N) {
                //disSet.union(target, idxNow);
                candidate[ pi1 ] = true;
            }
            if (x >= 0 && x < N && board[x][y]) {
                pi2 = disSet.find(auxIdx);
                if (candidate[ pi1 ] || candidate[ pi2 ]) {
                    candidate[ pi1 ] = true;
                    candidate[ pi2 ] = true;
                }
                
                disSet.union(auxIdx, idxNow);
                pi1 = disSet.find(idxNow);
            }
            
            if (candidate[ disSet.find(pi1) ] 
                   && disSet.connected(pi1, source)) {
                percola = true;
            }
        }
    }
    
    /**
     * is site (row i, column j) open?
     * 
     * @param i the row index
     * @param j the column index
     * @return true if board is Open
     */
    public boolean isOpen(int i, int j) {    
        validate(i, j);
        i--;
        j--;
        return board[i][j];
    }
    
    /**
     * is site (row i, column j) full?
     * 
     * @param i the row index
     * @param j the column index
     * @return true if the pos(i,j) in the board is open and is connected whit 
     * the top level
     */
    public boolean isFull(int i, int j) {   
        validate(i, j);
        i--;
        j--;
        int idx = getIndex(i, j);
        return (board[i][j] && disSet.connected(source, idx) );
    }
    
    /**
     * does the system percolate?
     */
    public boolean percolates() {             
        //return disSet.connected(source, target);
        return percola;
    }

    public static void main(String[] args) {  // test client (optional)
        //Scanner in = new Scanner( System.in );
        Percolation pc;
        int x, y, n = StdIn.readInt();
        
        pc = new Percolation(n);
        while (StdIn.hasNextLine()) {
            x = StdIn.readInt();
            y = StdIn.readInt();
            if (x == -1 && y == -1) {
                break;
            }
            pc.open(x, y);
        }
        
        while (StdIn.hasNextLine()) {
            x = StdIn.readInt();
            y = StdIn.readInt();
            if (x == -1 && y == -1) {
                break;
            }
            StdOut.println("Open? " + pc.isOpen(x, y));
            StdOut.println("Full? " + pc.isFull(x, y));
            StdOut.println("Perclora? " + pc.percolates());
        }
    }
}