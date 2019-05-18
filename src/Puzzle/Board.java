package Puzzle;

import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;
import java.util.LinkedList;

public class Board {

    private final int[][] blocks;
    private int blankI;
    private int blankJ;
    private int n;

    public Board(int[][] blocks)  {
        this.blocks = copy(blocks);
        n = this.blocks.length;

        for(int i = 0; i < n; ++i){
            for (int j = 0; j < n; ++j){
                if(this.blocks[i][j] == 0) {
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
                if(value == 0) continue;
                int di = (value - 1)/ n;
                int dj = (value - 1) % n;
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
                if(blocks[i][j] != 0 && blocks[i][j] != num) return false;
            }
        }
        return true;
    }

    public Board twin() {
        for (int row = 0; row < blocks.length; row++)
            for (int col = 0; col < blocks.length - 1; col++)
                if (blocks[row][col] != 0 && blocks[row][col+1] != 0)
                {
                    int[][] clone = copy(blocks);
                    swap(clone, row, col, row, col + 1);
                    return new Board(clone);
                }
        throw new RuntimeException();
    }

    public boolean equals(Object y)  {
        if (y==this) return true;
        if (y==null || !(y instanceof Board) || ((Board)y).blocks.length != blocks.length) return false;
        for (int row = 0; row < blocks.length; row++)
            for (int col = 0; col < blocks.length; col++)
                if (((Board) y).blocks[row][col] != blocks[row][col]) return false;

        return true;
    }

    private int[][] copy(int[][] blocks) {
        int[][] copy = new int[blocks.length][blocks.length];
        for (int row = 0; row < blocks.length; row++)
            for (int col = 0; col < blocks.length; col++)
                copy[row][col] = blocks[row][col];

        return copy;
    }

    public Iterable<Board> neighbors() {
        LinkedList<Board> list = new LinkedList<>();
        int[][] neighbors;
        if(blankI + 1 < n){
            neighbors = copy(blocks);
            swapDown(neighbors);
            list.add(new Board(neighbors));
        }
        if(blankI > 0){
            neighbors = copy(blocks);
            swapUp(neighbors);
            list.add(new Board(neighbors));
        }
        if(blankJ > 0){
            neighbors = copy(blocks);
            swapLeft(neighbors);
            list.add(new Board(neighbors));
        }
        if(blankJ + 1 < n){
            neighbors = copy(blocks);
            swapRight(neighbors);
            list.add(new Board(neighbors));
        }
        return list;
    }

    private void swapRight(int[][] array){
        swap(array, blankI, blankJ, blankI, blankJ+1);
    }

    private void swapLeft(int[][] array){
        swap(array, blankI, blankJ, blankI, blankJ-1);
    }

    private void swapUp(int[][] array){
        swap(array, blankI, blankJ, blankI-1, blankJ);
    }

    private void swapDown(int[][] array){
        swap(array, blankI, blankJ, blankI+1, blankJ);
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
        int[][] g = {{1,0,3},{4,2,5},{7,8,6}};
        Board board = new Board(g);
        System.out.println(board.neighbors());
    }

}
