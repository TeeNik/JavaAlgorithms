package Algorithm;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {


    private WeightedQuickUnionUF wq;
    private boolean[][] opened;
    private int numOfOpen;

    private final int top = 0;
    private final int bottom;
    private final int size;

    public Percolation(int N){
        size = N;
        bottom = N*N+1;
        wq = new WeightedQuickUnionUF(N*N+2);
        opened = new boolean[N][N];
    }

    public void open(int row, int col)  {
        opened[row-1][col] = true;
        numOfOpen++;

        int index = getSiteIndex(row, col)

        if(row == 1){
            wq.union(getSiteIndex(r));
        }
    }

    public boolean isOpen(int row, int col) {
        return opened[row][col];
    }

    public boolean isFull(int row, int col) {
        return wq.connected(getSiteIndex(row,col), top);
    }

    public int numberOfOpenSites() {
        return numOfOpen;
    }

    public boolean percolates() {

    }

    private int getSiteIndex(int row, int col){
        return (row - 1) * size + col;
    }

    public static void main(String[] args)  {

    }

}
