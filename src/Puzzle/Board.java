package Puzzle;

import edu.princeton.cs.algs4.StdRandom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class Board {

    private final int[][] blocks;
    private int blankI;
    private int blankJ;
    private int n;

    public Board(int[][] blocks)  {
        this.blocks = blocks;
        n = blocks.length;

        for(int i = 0; i < n; ++i){
            for (int j = 0; j < n; ++j){
                if(blocks[i][j] == 0) {
                    blankI = i;
                    blankJ = j;
                }
            }
        }
    }

    public int dimension() {
        return n;
    }

    public int hamming() {
        int hamming = 0;
        for(int i = 0; i < n; ++i){
            for (int j = 0; j < n; ++j){
                int num = i*n + j + 1;
                if(blocks[i][j] != 0 && blocks[i][j] != num) ++hamming;
            }
        }
        return hamming;
    }

    public int manhattan() {
        int manhattan = 0;
        for(int i = 0; i < n; ++i){
            for (int j = 0; j < n; ++j){
                int value = blocks[i][j];
                int di = value / n;
                int dj = (value -1) % n;
                if(i != di || j != dj){
                    manhattan += (Math.abs(i-di) + Math.abs(j-dj));
                }
            }
        }
        return manhattan;
    }

    public boolean isGoal() {
        for(int i = 0; i < n; ++i){
            for (int j = 0; j < n; ++j){
                int num = i*n + j + 1;
                if(blocks[i][j] != num) return false;
            }
        }
        return true;
    }

    public Board twin() {
        int i1 = StdRandom.uniform(n);
        int j1 = StdRandom.uniform(n);
        int i2, j2;
        do{
            i2 = StdRandom.uniform(n);
        } while (i2 != i1);
        do{
            j2 = StdRandom.uniform(n);
        } while (j2 != j1);
        int[][] twin = blocks.clone();
        swap(twin, i1, j1, i2, j2);
        return new Board(twin);

    }

    public boolean equals(Object y)  {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        if (!Arrays.equals(this.blocks, that.blocks)) return false;
        return true;
    }

    public Iterable<Board> neighbors() {
        LinkedList<Board> list = new LinkedList<>();
        int[][] neighbors;
        if(blankI + 1 < n){
            neighbors = blocks.clone();
            swapRight(neighbors);
            list.add(new Board(neighbors));
        }
        else if(blankI > 0){
            neighbors = blocks.clone();
            swapLeft(neighbors);
            list.add(new Board(neighbors));
        }
        if(blankJ > 0){
            neighbors = blocks.clone();
            swapUp(neighbors);
            list.add(new Board(neighbors));
        }
        else if(blankJ + 1 < n){
            neighbors = blocks.clone();
            swapDown(neighbors);
            list.add(new Board(neighbors));
        }
        return list;
    }

    private void swapRight(int[][] array){
        swap(array, blankI, blankJ, blankI+1, blankJ);
    }

    private void swapLeft(int[][] array){
        swap(array, blankI, blankJ, blankI-1, blankJ);
    }

    private void swapUp(int[][] array){
        swap(array, blankI, blankJ, blankI, blankJ-1);
    }

    private void swapDown(int[][] array){
        swap(array, blankI, blankJ, blankI, blankJ+1);
    }

    private void swap(int[][] array, int x1, int y1, int x2, int y2){
        int temp = array[x1][y1];
        array[x1][y1] = array[x2][y2];
        array[x2][y2] = temp;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", blocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    public static void main(String[] args){

    }

}
