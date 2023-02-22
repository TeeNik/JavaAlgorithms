import edu.princeton.cs.algs4.StdOut;

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
                if (i == blankI && j == blankJ) {
                    continue;
                }
                int value = tiles[i][j] - 1;
                int desiredI = value / size;
                int desiredJ = value % size;
                m += (Math.abs(i - desiredI) + Math.abs(j - desiredJ));
            }
        }
    }

    // does this board equal y?
    public boolean equals(Object y) {
        return false;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        return null;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        return null;
    }

    // unit testing (not graded)
    public static void main(String[] args) {

    }
}
