/******************************************************************************
 *   Author:       JFCowboy
 *   Written:      14/03/2016
 *   Last Updated: 14/03/2016
 *  
 *  Compilation:  javac FastCollinearPoints.java
 *  Execution:    java FastCollinearPoints
 *  Dependencies: none
 *  
 *  
 *
 ******************************************************************************/

import java.util.ArrayList;
import java.util.Arrays;
import edu.princeton.cs.algs4.StdDraw;

/**
 * 
 * @author JuanFelipe
 *
 */
public class FastCollinearPoints {
    /**
     * Array of segments with 4 or more collinear points. 
     */
    private LineSegment[] segments;
    
    /**
     * finds all line segments containing 4 or more points.
     * @param points set of points.
     */
   public FastCollinearPoints(Point[] points) {
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
       
       //Begin FastCollinear Algorithm
       Arrays.sort(points);
       Point[] sortPoints;
       double prevSlope, actSlope;
       int cnt;
       boolean begin = true;
       ArrayList<LineSegment> sol = new ArrayList<>();
       
       for (int i = 0; i < n; i++) {
           sortPoints = points.clone();
           Arrays.sort(sortPoints, points[i].slopeOrder());
//           System.out.println("********** " + points[i] + "*******");
           prevSlope = points[i].slopeTo(sortPoints[0]);
//           System.out.println(points[0] + " Solpe: " 
//                   + points[i].slopeTo(sortPoints[0]));
           cnt = 0;
           for (int j = 1; j < n; j++) {
//               System.out.println(sortPoints[j] + " Solpe: " 
//                       + points[i].slopeTo(sortPoints[j]));
               actSlope = points[i].slopeTo(sortPoints[j]);
               if (prevSlope == actSlope) {
                   cnt++;
               } else {
                   if (begin && cnt >= 3) {
                       sol.add(new LineSegment(points[i], sortPoints[j - 1]));
                   }
                   if (points[i].compareTo(sortPoints[j]) < 0) {
                       begin = true;
                   } else {
                       begin = false;
                   }
                   cnt = 1;
               }
               prevSlope = actSlope;
           }
           if (begin && cnt >= 3) {
               sol.add(new LineSegment(points[i], sortPoints[n - 1]));
//               System.out.println("agregado:" + sol.get(sol.size()-1));
           }
       }
       
       segments = new LineSegment[sol.size()];
       sol.toArray(segments);
   }
   /**
    * the number of line segments.
    * @return
    */
   public int numberOfSegments(){
       return segments.length;
   }

   /**
    * the line segments.
    * @return 
    */
   public LineSegment[] segments() {
       return segments;
   }
   
   public static void main(String args[]) throws Exception {
    // read the N points from a file
       //System.setIn(new FileInputStream("collinear/input6.txt"));
       In in = new In("collinear/rs1423.txt");
       int N = in.readInt();
       Point[] points = new Point[N];
       for (int i = 0; i < N; i++) {
           int x = in.readInt();
           int y = in.readInt();
           points[i] = new Point(x, y);
       }

       // draw the points
       StdDraw.show(0);
       StdDraw.setXscale(0, 32768);
       StdDraw.setYscale(0, 32768);
       for (Point p : points) {
           p.draw();           
       }
       StdDraw.show();

       // print and draw the line segments
       FastCollinearPoints collinear = new FastCollinearPoints(points);
       System.out.println("Puntos Colineales, Numero de Segmentos:" 
                       + collinear.numberOfSegments());
       for (LineSegment segment : collinear.segments()) {
           segment.draw();
           StdOut.println(segment);
       }
   }
}
