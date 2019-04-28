package Puzzle;

import java.util.Arrays;

public class Board {

    private final int[][] blocks;
    private int move;

    public Board(int[][] blocks)  {
        this.blocks = blocks;
    }

    public int dimension() {
        return blocks.length;
    }

    public int hamming() {
        int n = blocks.length;
        int hamming = move;
        for(int i = 0; i < n){
            for (int j = 0; j < n; ++j){
                int num = i*n + j + 1;
                if(blocks[i][j] != 0 && blocks[i][j] != num) ++hamming;
            }
        }
        return hamming;
    }

    public int manhattan() {
        int manhattan = move;
        int n = blocks.length;

        for(int i = 0; i < n){
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

    }

    public Board twin() {

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

    }

    public String toString() {

    }

    public static void main(String[] args){

    }

}
