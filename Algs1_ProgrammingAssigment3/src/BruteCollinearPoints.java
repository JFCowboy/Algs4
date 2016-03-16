/******************************************************************************
 *   Author:    JFCowboy
 *   Written:      13/03/2016
 *   Last Updated: 13/03/2016
 *  
 *  Compilation:  javac BruteCollinearPoints.java
 *  Execution:    java BruteCollinearPoints
 *  Dependencies: none
 *  
 *  
 *
 ******************************************************************************/
import java.util.ArrayList;
import java.util.Arrays;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;


/**
 * @author JuanFelipe
 *
 */
public class BruteCollinearPoints {
    /**
     * Array of segments whit 4 collinear points. 
     */
    private LineSegment[] segments;
    /**
     *  finds all line segments containing 4 points.
     * @param points 
     * @throws NullPointerException is the <em>points</em> is null or any value 
     * in <em>points</em>
     */
    public BruteCollinearPoints(Point[] points) throws NullPointerException {
        //Validaciones de datos
        if (points == null) {
            throw new NullPointerException("Array is null");
        }
        Point punto;
        int n = points.length;
        for (int i = 0; i < n; i++) {
            punto = points[i];
            if (punto == null) {
                throw new NullPointerException("A value in the array is null");
            }
            for (int j = i + 1; j < n; j++) {
                if (points[j] == null) {
                    throw new NullPointerException("A value in " 
                            + "the array is null");
                }
                if (points[j] == punto) {
                    throw new IllegalArgumentException("Two points " 
                            + "are th same");
                }
            }
        }
        
        //Inicio algoritmo fuerza bruta
        Arrays.sort(points);
        Point p, q, r, s;
        ArrayList<LineSegment> lista = new ArrayList<>();
        double pq, pr, ps;
        
        for (int i = 0; i < n; i++) {
            p = points[i];
            for (int j = i + 1; j < n; j++) {
                q = points[j];
                pq = p.slopeTo(q);
                for (int k = j + 1; k < n; k++) {
                    r = points[k];
                    pr = p.slopeTo(r);
                    if (Double.compare(pq, pr) != 0) {
                        continue;
                    }
                    for (int ii = k + 1; ii < n; ii++) {
                        s = points[ii];
                        ps = p.slopeTo(s);
                        if (Double.compare(pq, ps) != 0) {
                            continue;
                        }
                        lista.add(new LineSegment(p, s));
                    }
                }
            }
        }
        segments = new LineSegment[lista.size()];
        lista.toArray(segments);
    }
    
    /**
     * the number of line segments.
     * @return
     */
    public int numberOfSegments() {
        return segments.length;
    }
    
    /**
     * the line segments.
     * @return
     */
    public LineSegment[] segments() {
        return segments;
    }
    
    /**
     * @param args 
     * @exception Exception 
     */
    public static void main(String[] args) throws Exception {
        //System.setIn(new FileInputStream("collinear/rs1423.txt"));
        //Scanner sc = new Scanner(System.in);
        In sc = new In("collinear/rs1423.txt");
        int n = sc.readInt();
        Point[] puntos = new Point[n];
        for (int i = 0; i < n; i++) {
            puntos[i] = new Point(sc.readInt(), sc.readInt());
        }
        //StdDraw.setScale(-100, 32768);
     // draw the points
        StdDraw.show(0);
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : puntos) {
            p.draw();
        }
        StdDraw.show();
        
        BruteCollinearPoints bcp = new BruteCollinearPoints(puntos);
        System.out.println("Numero de Segmentos: " + bcp.numberOfSegments());
        for (LineSegment ls : bcp.segments) {
            ls.draw();
            System.out.println(ls);
        }
        sc.close();
    }

}
