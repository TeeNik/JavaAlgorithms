package Union.AlgorIthm;//package Union.Algorithm;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private int experimentsCount;
    private double[] results;
    private double mean;
    private double stddev;

    public PercolationStats(int N, int T){
        if(N <= 0 || T <= 0){
            throw new IllegalArgumentException();
        }
        experimentsCount = T;
        results = new double[T];
        for (int i = 0; i < T; ++i){
            Percolation percolation = new Percolation(N);
            while (!percolation.percolates()){
                int a = StdRandom.uniform(1, N+1);
                int b = StdRandom.uniform(1, N+1);
                if(!percolation.isOpen(a, b)){
                    percolation.open(a,b);
                }
            }
            results[i] = (double)percolation.numberOfOpenSites()/(N*N);
        }
        stddev = StdStats.stddev(results);
        mean = StdStats.mean(results);
    }

    public double confidenceLo() {
        return mean - ((1.96 * stddev) / Math.sqrt(experimentsCount));
    }

    public double confidenceHi() {
        return mean + ((1.96 * stddev) / Math.sqrt(experimentsCount));
    }

    public double mean(){
        return mean;
    }

    public double stddev(){
        return stddev;
    }

    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(N, T);

        String confidence = ps.confidenceLo() + ", " + ps.confidenceHi();
        StdOut.println("mean                    = " + ps.mean());
        StdOut.println("stddev                  = " + ps.stddev());
        StdOut.println("95% confidence interval = " + confidence);
    }

}
