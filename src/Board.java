import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class Board {
    private int tiles[][];
    private int size;
    private int blankI;
    private int blankJ;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this.tiles = tiles;
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
        int h = 0;
        int n = 1;
        for (int[] line : tiles) {
            for (int tile : line) {
                if(tile == n && tile != 0) {
                    ++h;
                }
                ++n;
            }
        }
        return h;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int m = 0;
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++ j) {
                if (i == blankI && j == blankJ) {
                    continue;
                }
                int value = tiles[i][j] - 1;
                int desiredI = value / size;
                int desiredJ = value % size;
                m += (Math.abs(i - desiredI) + Math.abs(j - desiredJ));
            }
        }
        return m;
    }

    // is this board the goal board?
    public boolean isGoal() {
        int n = 1;
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++ j) {
                if(!(tiles[i][j] == n || n == size * size))
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
            int[][] newTiles = copyTiles();
            swap(newTiles, blankI, blankJ, blankI - 1, blankJ);
            neighborsList.add(new Board(newTiles));
        }
        if (blankI < size - 1) {
            int[][] newTiles = copyTiles();
            swap(newTiles, blankI, blankJ, blankI + 1, blankJ);
            neighborsList.add(new Board(newTiles));
        }
        if (blankJ > 0) {
            int[][] newTiles = copyTiles();
            swap(newTiles, blankI, blankJ, blankI, blankJ - 1);
            neighborsList.add(new Board(newTiles));
        }
        if (blankJ < size -1) {
            int[][] newTiles = copyTiles();
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
                    int[][] newTiles = copyTiles();
                    swap(newTiles, i, j, i, j + 1);
                    return new Board(newTiles);
                }
            }
        }
        throw new RuntimeException();
    }

    private int[][] copyTiles() {
        int[][] newTiles = new int[size][size];
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                newTiles[i][j] = tiles[i][j];
            }
        }
        return tiles;
    }

    private void swap(int[][] array, int x1, int y1, int x2, int y2) {
        int temp = array[x1][y1];
        array[x1][y1] = array[x2][y2];
        array[x2][y2] = temp;
    }

    // unit testing (not graded)
    public static void main(String[] args) {

    }
}
