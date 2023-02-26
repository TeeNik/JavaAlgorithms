package Puzzle;

import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;

public class Board {
    private int tiles[][];
    private int size;
    private int blankI;
    private int blankJ;

    private int manhattan = 0;
    private int hamming = 0;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this.tiles = copy(tiles);
        size = tiles.length;

        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                if (tiles[i][j] == 0) {
                    blankI = i;
                    blankJ = j;
                    break;
                }
            }
        }

        calculateHamming();
        calculateManhattan();
    }

    // string representation of this board
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(size + "\n");
        for (int[] line : tiles) {
            for (int tile : line) {
                str.append(" " + tile);
            }
            str.append("\n");
        }
        return str.toString();
    }

    // board dimension n
    public int dimension() {
        return size;
    }

    // number of tiles out of place
    public int hamming() {
        return hamming;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        return manhattan;
    }

    private void calculateHamming() {
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++ j) {
                int n = i * size + j + 1;
                if(tiles[i][j] != n && tiles[i][j] != 0)
                {
                    ++hamming;
                }
            }
        }
    }

    private void calculateManhattan() {
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++ j) {
                if (i == blankI && j == blankJ) {
                    continue;
                }
                int value = tiles[i][j] - 1;
                int desiredI = value / size;
                int desiredJ = value % size;
                manhattan += (Math.abs(i - desiredI) + Math.abs(j - desiredJ));
            }
        }
    }

    // is this board the goal board?
    public boolean isGoal() {
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++ j) {
                int n = i * size + j + 1;
                if(tiles[i][j] != n && tiles[i][j] != 0)
                {
                    return false;
                }
            }
        }
        return true;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (this == y) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;

        Board that = (Board) y;
        if(size != that.size) return false;

        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                if (tiles[i][j] != that.tiles[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        ArrayList<Board> neighborsList = new ArrayList<>();
        if (blankI > 0) {
            int[][] newTiles = copy(tiles);
            swap(newTiles, blankI, blankJ, blankI - 1, blankJ);
            neighborsList.add(new Board(newTiles));
        }
        if (blankI < size - 1) {
            int[][] newTiles = copy(tiles);
            swap(newTiles, blankI, blankJ, blankI + 1, blankJ);
            neighborsList.add(new Board(newTiles));
        }
        if (blankJ > 0) {
            int[][] newTiles = copy(tiles);
            swap(newTiles, blankI, blankJ, blankI, blankJ - 1);
            neighborsList.add(new Board(newTiles));
        }
        if (blankJ < size -1) {
            int[][] newTiles = copy(tiles);
            swap(newTiles, blankI, blankJ, blankI, blankJ + 1);
            neighborsList.add(new Board(newTiles));
        }
        return neighborsList;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size - 1; ++j) {
                if (tiles[i][j] != 0 && tiles[i][j + 1] != 0) {
                    int[][] newTiles = copy(tiles);
                    swap(newTiles, i, j, i, j + 1);
                    return new Board(newTiles);
                }
            }
        }
        throw new RuntimeException();
    }

    private int[][] copy(int[][] tiles) {
        int[][] newTiles = new int[tiles.length][tiles.length];
        for (int i = 0; i < tiles.length; ++i) {
            for (int j = 0; j < tiles.length; ++j) {
                newTiles[i][j] = tiles[i][j];
            }
        }
        return newTiles;
    }

    private void swap(int[][] array, int x1, int y1, int x2, int y2) {
        int temp = array[x1][y1];
        array[x1][y1] = array[x2][y2];
        array[x2][y2] = temp;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        int[][] tiles = {{1,0,8},{2,4,6},{3,5,7}};
        Board board = new Board(tiles);
        for (Board b : board.neighbors()) {
            StdOut.println(b);
        }
    }
}
