package Percolation;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private WeightedQuickUnionUF union;
    private int size;
    private boolean[] open;
    private int numOfOpen = 0;
    private final int firstSiteIndex;
    private final int lastSiteIndex;
    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }

        union = new WeightedQuickUnionUF(n * n + 2);
        size = n;
        open = new boolean[n * n];
        firstSiteIndex = 0;
        lastSiteIndex = n * n + 1;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validate(row, col);

        if (open[(row - 1) * size + (col - 1)]) {
            return;
        }

        final int index = getIndex(row, col);
        open[(row - 1) * size + (col - 1)] = true;
        ++numOfOpen;

        if (row == 1) {
            union.union(index, firstSiteIndex);
        }
        if (row == size)
        {
            union.union(index, lastSiteIndex);
        }

        if (row > 1 && isOpen(row - 1, col))
        {
            union.union(index, getIndex(row - 1, col));
        }
        if (row < size && isOpen(row + 1, col))
        {
            union.union(index, getIndex(row + 1, col));
        }
        if (col > 1 && isOpen(row, col - 1))
        {
            union.union(index, getIndex(row, col - 1));
        }
        if (col < size && isOpen(row, col + 1))
        {
            union.union(index, getIndex(row, col + 1));
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row, col);
        return open[(row - 1) * size + (col - 1)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validate(row, col);
        return union.find(getIndex(row, col)) == union.find(firstSiteIndex);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numOfOpen;
    }

    // does the system percolate?
    public boolean percolates() {
        return union.find(firstSiteIndex) == union.find(lastSiteIndex);
    }

    // test client (optional)
    public static void main(String[] args)
    {
    }

    private int getIndex(int row, int col)
    {
        return (row - 1)  * size + col;
    }
    private void validate(int row, int col)
    {
        if (row <= 0 || row > size || col <= 0 || col > size) {
            throw new IllegalArgumentException();
        }
    }
}
