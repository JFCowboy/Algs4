/*------------------------------------
 *   Author:       JFCowboy
 *   Written:      10/02/2016
 *   Last Updated: 10/02/2016
 * 
 *   
 * 
 *------------------------------------*/
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * Class to run and collect info about experiment whit Percolation's class.
 */
public class PercolationStats {
    
    private double mean, stddev, conLo, conHi;
    /**
     * perform T independent experiments on an N-by-N grid.
     * */
    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException(" tha values of N and T "
                                                   + "will be grater that 0");
        }
        int x, y, cnt;
        double[] res = new double[T];
        final double mNumber = 1.96;
        Percolation percolation;
        
        for (int i = 0; i < T; i++) {
            percolation = new Percolation(N);
            for (cnt = 0; !percolation.percolates(); cnt++) {
                do {
                    x = StdRandom.uniform(N) + 1;
                    y = StdRandom.uniform(N) + 1;
                } while (percolation.isOpen(x, y));
                percolation.open(x, y);
            }
            res[ i ] = cnt * 1.0 / (N * N);
        }
        mean = StdStats.mean(res);
        stddev = StdStats.stddev(res);
        conLo = mean - (mNumber * stddev) / Math.sqrt(T);
        conHi = mean + (mNumber * stddev) / Math.sqrt(T);
    }
    
    // sample mean of percolation threshold
    public double mean() {
        return mean;
    }                     
    
    // sample standard deviation of percolation threshold
    public double stddev() {
        return stddev;
    }                   
    
    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return conLo;
    }             
    
    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return conHi;
    }

    public static void main(String[] args) {
        
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats pcs = new PercolationStats(N, T);
        
        StdOut.printf("mean\t\t\t= %f\n", pcs.mean());
        StdOut.printf("stddev\t\t\t= %f\n", pcs.stddev());
        StdOut.println("95% condience interval\t= " + pcs.confidenceLo()
                           + "," + pcs.confidenceHi());
        
    }    
}