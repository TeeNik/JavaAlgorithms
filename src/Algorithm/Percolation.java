package Algorithm;

import Model.Union;
import Reader.UnionReader;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.ArrayList;

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
        opened[row-1][col-1] = true;
        numOfOpen++;

        int index = getSiteIndex(row, col);

        if(row == 1){
            wq.union(index, top);
        }
        if(row == size){
            wq.union(index, bottom);
        }

        if(row > 1 && isOpen(row-1, col)){
            wq.union(index, getSiteIndex(row-1, col));
        }
        if(row < size && isOpen(row+1, col)){
            wq.union(index, getSiteIndex(row+1, col));
        }
        if(col > 1 && isOpen(row, col-1)){
            wq.union(index, getSiteIndex(row, col-1));
        }
        if(col < size && isOpen(row, col+1)){
            wq.union(index, getSiteIndex(row, col+1));
        }
    }

    public boolean isOpen(int row, int col) {
        return opened[row-1][col-1];
    }

    public boolean isFull(int row, int col) {
        return wq.connected(getSiteIndex(row,col), top);
    }

    public int numberOfOpenSites() {
        return numOfOpen;
    }

    public boolean percolates() {
        return wq.connected(top, bottom);
    }

    private int getSiteIndex(int row, int col){
        return (row - 1) * size + col;
    }

    public static void main(String[] args)  {
        UnionReader reader = new UnionReader();
        ArrayList<Union> list = reader.readUnion("data.txt");

        Percolation percolation = new Percolation(3);
        for (Union union : list) {
            percolation.open(union.x, union.y);
        }
        System.out.println("percolates: " + percolation.percolates());
    }

}
