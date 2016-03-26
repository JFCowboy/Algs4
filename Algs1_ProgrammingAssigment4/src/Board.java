/******************************************************************************
 *   Author:    JFCowboy
 *   Written:      13/03/2016
 *   Last Updated: 13/03/2016
 *  
 *  Compilation:  javac Board.java
 *  Execution:    java Board
 *  Dependencies: none
 *  
 *  
 *
 ******************************************************************************/

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

/**
 * Immutable data type Board.
 * @author JuanFelipe
 *
 */
public class Board {
    // size of the NxN Board
    private final int N;
    // Hamming Dist of this board
    private final int hammingDist;
    // Manhattan Dist of this board
    private final int manhattanDist;
    // Elements of the Board
    private final int[][] blocks;
    
    
    /**
     * construct a board from an N-by-N array of blocks
     * (where blocks[i][j] = block in row i, column j).
     * 
     * @param blocks elements in the board
     */
    public Board(int[][] blocks) {
        this.N = blocks.length;
        this.blocks = new int[N][N];
        int i = 0;
        for (int[] list : blocks) {
            this.blocks[i++] = list.clone();
        }
            
        this.hammingDist = calcHamming();
        this.manhattanDist = calcManhattan();
    }
    
    /**
     * Sum of Manhattan distances of each piece.
     * @return Sum of manhattan distances.
     */
    private int calcManhattan() {
        int dist = 0, aux;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                aux = distance(blocks[i][j], i, j);
                //System.out.println("<"+i+","+j+">"+blocks[i][j]+"="+aux);
                dist += aux;
            }
        }
        return dist;
    }
    
    /**
     * Distance of piece to real place.
     * @param num piece to verify
     * @param row actual row
     * @param col actual column
     * @return Manhattan distance of actual piece
     */
    private int distance(int num, int row, int col) {
        if (num == 0) {
            return 0;
        }
        int i, j;
        i = (num - 1) / N;
        j = (num - 1) % N;        
        return Math.abs(i - row) + Math.abs(j - col);
    }

    /**
     * Function that calculate the Hamming distance. 
     * @return the number of pieces out of place
     */
    private int calcHamming() {
        int outOfPlace = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (blocks[i][j] != 0 && isOutOfPlace(blocks[i][j], i, j)) {
                    outOfPlace++;
                }
            }
        }
        return outOfPlace;
    }
    
    /**
     * Check if a <em>num</em> is in the correct place.
     * @param num number to check if is in a good place
     * @param row row to verify
     * @param col column to verify
     * @return
     */
    private boolean isOutOfPlace(int num, int row, int col) {
        int good = row * N + col;
        return num != good + 1;
    }

    /**
     * board dimension N.
     * @return
     */
    public int dimension() {
        return N;
    }
    
    /**
     * Number of blocks out of place.
     * @return <em>number</em> of blocks out of place
     */
    public int hamming() {
        return hammingDist;
    }
    
    /**
     * sum of Manhattan distances between blocks and goal.
     * @return
     */
    public int manhattan() {
        return manhattanDist;
    }
    /**
     * is this board the goal board?
     * @return true if this is a goal board false in other case.
     */
    public boolean isGoal() {
        return this.hammingDist == 0;
    }
    
    /**
     * A board that is obtained by exchanging any pair of blocks.
     * @return
     */
    public Board twin() {
        int[][] newBoard = new int[N][N];
        int id = 0;
        for (int[] list : this.blocks) {
            newBoard[id++] = list.clone();
        }
        int x1 = -1, y1 = -1, x2 = -1, y2 = -1, swap;
        for (int i = 0; x2 == -1 && i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (newBoard[i][j] != 0) {
                    if (x1 == -1) {
                        x1 = i;
                        y1 = j;
                    } else {
                        x2 = i;
                        y2 = j;
                        break;
                    }
                }
            }
        }
        swap = newBoard[x1][y1];
        newBoard[x1][y1] = newBoard[x2][y2];
        newBoard[x2][y2] = swap;
        return new Board(newBoard);
    }
    
    
    // does this board equal y?
    public boolean equals(Object y) {
//        if (this == y) {
//            return true;
//        }
        if (y == null) {
            return false;
        }
        if (this.getClass() != y.getClass()) {
            return false;
        }
        Board that = (Board) y;
        boolean same = (this.dimension() == that.dimension());
        
        for (int i = 0; same && i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (this.blocks[i][j] != that.blocks[i][j]) {
                    same = false;
                    break;
                }
            }
        }
        return same;
    }
    
    /**
     * generate and return the neighboring boards.
     * @return all neighboring boards.
     */
    public Iterable<Board> neighbors() {
        return new Iterable<Board>() {
            
            @Override
            public Iterator<Board> iterator() {
                return new NeighborsIterator(N, blocks);
            }
        };
    }
    
    /**
     * Clase que itera y genera los vecinos de un nodo.
     * @author JuanFelipe
     */
    private class NeighborsIterator implements Iterator<Board> {
        private static final int TAM = 4;
        private int N, row, col;
        private int[][] blocks;
        private int[] posX = {-1, 0, 1, 0};
        private int[] posY = {0, 1, 0, -1};
        private Stack<Board>st;
        
        public NeighborsIterator(int n, int[][] blocks) {
            super();
            N = n;
            this.blocks = new int[N][N];
            int id = 0;
            for (int[] list : blocks) {
                this.blocks[id++] = list.clone();
            }
            row = -1;
            for (int i = 0; row == -1 && i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (this.blocks[i][j] == 0) {
                        row = i;
                        col = j;
                        break;
                    }
                }
            }
            st = new Stack<>();
            int x, y;
            for (int i = 0; i < TAM; i++) {
                x = row + posX[i];
                y = col + posY[i];
                if (x >= 0 && x < N && y >= 0 && y < N) {
                    int aux = this.blocks[x][y];
                    this.blocks[x][y] = this.blocks[row][col];
                    this.blocks[row][col] = aux;
                    st.push(new Board(this.blocks));
                    aux = this.blocks[x][y];
                    this.blocks[x][y] = this.blocks[row][col];
                    this.blocks[row][col] = aux;
                }
            }
            
        }

        @Override
        public boolean hasNext() {
            return !st.isEmpty();
        }

        @Override
        public Board next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No hay mas elementos");
            }
            
            return st.pop();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
        
    }
    
    // string representation of this board
    public String toString() {
        
        StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", blocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    
    public static void main(String[] args) { 
        // create initial board from file
        In in;
        if (args.length > 0) {
            in = new In(args[0]);
        } else {
            in = new In("8puzzle/puzzle2x2-06.txt");
        }
        
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                blocks[i][j] = in.readInt();
            }
        }
        Board initial = new Board(blocks);
        
        StdOut.println(initial);
        StdOut.println("TWIN:" + initial.twin());
        StdOut.println("Ini:" + initial);
        StdOut.println("Es el objetivo?" + initial.isGoal());
        StdOut.println("Manhattan:" + initial.manhattan());
        StdOut.println("Hamming:" + initial.hamming());
        StdOut.println("Vecinos:");
        for (Board board : initial.neighbors()) {
            StdOut.println(board);
        }
    }
}
