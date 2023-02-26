import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;

public class Solver {

    private class Move implements Comparable<Move> {

        private Move previous;
        private Board board;
        private int numOfMoves = 0;
        private int manhattan = 0;

        private Move(Board board) {
            this.board = board;
            manhattan = board.manhattan();
        }
        private Move(Board board, Move previous) {
            this.board = board;
            this.previous = previous;
            numOfMoves = previous.numOfMoves + 1;
            manhattan = board.manhattan();
        }

        @Override
        public int compareTo(Move move) {
            return (this.manhattan - move.manhattan) + (this.numOfMoves - move.numOfMoves);
        }
    }

    private Move lastMove;
    private Deque<Board> solution = null;
    private int numOfMoves;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if(initial == null) {
            throw new IllegalArgumentException();
        }

        MinPQ<Move> moves = new MinPQ<>();
        moves.insert(new Move(initial));

        MinPQ<Move> twinMoves = new MinPQ<>();
        twinMoves.insert(new Move(initial.twin()));

        while(true) {
            lastMove = nextMove(moves);
            if (lastMove != null || nextMove(twinMoves) != null) break;
        }

        numOfMoves = lastMove != null ? lastMove.numOfMoves : -1;
        if(numOfMoves >= 0) {
            solution = new LinkedList<>();
            while (lastMove != null) {
                solution.addFirst(lastMove.board);
                lastMove = lastMove.previous;
            }
        }


    }

    private Move nextMove(MinPQ<Move> pq) {
        Move best = pq.delMin();
        if(best.board.isGoal()) {
            return best;
        }
        for (Board neighbor : best.board.neighbors()) {
            if (best.previous == null || !neighbor.equals(best.previous.board)) {
                pq.insert(new Move(neighbor, best));
            }
        }
        return null;
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solution != null;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return numOfMoves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return solution;
    }

    // test client (see below)
    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                tiles[i][j] = in.readInt();
            }
        }

        Board board = new Board(tiles);
        Solver solver = new Solver(board);
        if(!solver.isSolvable()) {
            StdOut.println("No solution possible");
        } else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            StdOut.print(solver.solution);
        }

    }
}
