package Algorithm;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private int experimentsCount;
    private double[] results;
    private Percolation percolation;

    public PercolationStats(int N, int T){
        if(N <= 0 || T <= 0){
            throw new IllegalArgumentException();
        }
        experimentsCount = T;
        results = new double[T];
        for (int i = 0; i < T; ++i){
            percolation = new Percolation(N);
            int opened = 0;
            while (!percolation.percolates()){
                int a = StdRandom.uniform(1, N+1);
                int b = StdRandom.uniform(1, N+1);
                if(!percolation.isOpen(a, b)){
                    percolation.open(a,b);
                    ++opened;
                }
                results[i] = (double)opened/(N*N);
                //System.out.println(results[i]);
            }
        }
    }

    public double confidenceLo() {
        return mean() - ((1.96 * stddev()) / Math.sqrt(experimentsCount));
    }

    public double confidenceHi() {
        return mean() + ((1.96 * stddev()) / Math.sqrt(experimentsCount));
    }

    public double mean(){
        return StdStats.mean(results);
    }

    public double stddev(){
        return  StdStats.stddev(results);
    }



    public static void main(String[] args) {
        int N = 2;//Integer.parseInt(args[0]);
        int T = 10000;//Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(N, T);

        String confidence = ps.confidenceLo() + ", " + ps.confidenceHi();
        StdOut.println("mean                    = " + ps.mean());
        StdOut.println("stddev                  = " + ps.stddev());
        StdOut.println("95% confidence interval = " + confidence);
    }

}
