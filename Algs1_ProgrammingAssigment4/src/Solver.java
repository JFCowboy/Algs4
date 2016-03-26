import java.util.Comparator;
import java.util.TreeMap;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private int nMoves;
    private MinPQ<Board> pq, pqI, pqT;
    private TreeMap<Board, Integer> dist;
    private TreeMap<Board, Boolean> marked;
    private TreeMap<Board, Board> pi;
    private Stack<Board> path;
    
    private class ByDistanceFromInitial implements Comparator<Board> {

        @Override
        public int compare(Board b0, Board b1) {
            int dist0 = dist.get(b0);
            int dist1 = dist.get(b1);
            return dist0 - dist1;
        }
        
    }
    
    private class HashBoard implements Comparator<Board> { 

        @Override
        public int compare(Board o1, Board o2) {
            if (o1.dimension() != o2.dimension()) {
                return o1.dimension() - o2.dimension();
            }
            if (o1.manhattan() != o2.manhattan()) {
                return o1.manhattan() - o2.manhattan();
            }
            if (o1.hamming() != o2.hamming()) {
                return o1.hamming() - o2.hamming();
            }
            if (o1.equals(o2)) {
                return 0;
            }
            Board t1, t2;
            t1 = o1.twin();
            t2 = o2.twin();
            if (t1.dimension() != t2.dimension()) {
                return t1.dimension() - t2.dimension();
            }
            if (t1.manhattan() != t2.manhattan()) {
                return t1.manhattan() - t2.manhattan();
            }
            if (t1.hamming() != t2.hamming()) {
                return t1.hamming() - t2.hamming();
            }
            return 0;
        }
        
    }
    
    /**
     * find a solution to the initial board (using the A* algorithm).
     * @param initial
     */
    public Solver(Board initial) {
        
        if (initial == null) {
            throw new NullPointerException("El tablero inicial no " 
                    + "puede ser vacio");
        }
        nMoves = -1;
        pqI = new MinPQ<Board>(new ByDistanceFromInitial());
        pqT = new MinPQ<Board>(new ByDistanceFromInitial());
        pi = new TreeMap<>(new HashBoard());
        dist = new TreeMap<>(new HashBoard());
        marked = new TreeMap<>(new HashBoard());
        Board aux = initial, twin = initial.twin();
        
        pqI.insert(initial);
        dist.put(initial, 0);
        pqT.insert(twin);
        dist.put(twin, 0);
        
        int idx = 0;
        pq = pqI;
        while (true) {
            if (pq.isEmpty()) {
                if (pq == pqI) {
                    pq = pqT;
                    idx = 1;
                } else {
                    pq = pqI;
                    idx = 0;
                }
                if (pq.isEmpty()) {
                    break;
                }
            }
//            if (idx == 0) {
//                System.out.println("Actual:" + aux);
//                System.out.println("Padre: " + pi.get(aux));
//            }
            aux = pq.delMin();
            if (marked.containsKey(aux)) {
                continue;
            }
//System.out.println("Inidice: " + idx + "\n" + aux + dist.get(aux));
            if (aux.isGoal()) {
                break;
            }
            marked.put(aux, true);
            //System.out.println("HIJOS *******");
            for (Board vec : aux.neighbors()) {
                //System.out.println(vec);
                if (!dist.containsKey(vec) 
                        || dist.get(vec) > dist.get(aux) + vec.manhattan()) {
                    //System.out.println("Entra");
                    dist.put(vec, dist.get(aux) + 1 + vec.manhattan());
                    pi.put(vec, aux);
                    pq.insert(vec);
                }
                /* else {
                    System.out.println("No Entra");
                }*/
            }
            //System.out.println("FIN HIJOS *******");
            
            if (idx == 0) {
                pq = pqT;
            } else {
                pq = pqI;
            }
            idx ^= 1; 
        }
        
        if (idx == 0 && aux != null && aux.isGoal()) {
            path = new Stack<>();
            while (aux != null) {
                path.push(aux);
                aux = pi.get(aux);
            }
            nMoves = path.size() - 1;
        }
    }
    
    /**
     * is the initial board solvable?
     * @return
     */
    public boolean isSolvable() {
        return nMoves != -1;
    }
    
    /**
     * min number of moves to solve initial board; -1 if unsolvable.
     * @return
     */
    public int moves() {
        return nMoves;
    }
    
    /**
     * sequence of boards in a shortest solution; null if unsolvable.
     * @return
     */
    public Iterable<Board> solution() {
        if (nMoves == -1) {
            return null;
        }
        return path; 
    }
    
    /**
     * solve a slider puzzle (given below).
     * @param args
     */
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

        // solve the puzzle
        Solver solver = new Solver(initial);
        StdOut.println("Tablero inicial\n" + initial);
        // print solution to standard output
        if (!solver.isSolvable()) {
            StdOut.println("No solution possible");
        } else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution()) {
                StdOut.println(board);
            }
        }
    }
}