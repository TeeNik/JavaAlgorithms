//package Puzzle;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Deque;
import java.util.Queue;
import java.util.Stack;

public class Solver {

    private class Move implements Comparable<Move> {
        private Move previous;
        private Board board;
        private int numMoves = 0;

        public Move(Board board) {
            this.board = board;
        }

        public Move(Board board, Move previous) {
            this.board = board;
            this.previous = previous;
            this.numMoves = previous.numMoves + 1;
        }

        public int compareTo(Move move) {
            return (this.board.manhattan() - move.board.manhattan()) + (this.numMoves - move.numMoves);
        }
    }

    private Move lastMove;
    private Deque<Board> solution;
    private int numMoves;

    public Solver(Board initial) {

        if(initial == null) throw new IllegalArgumentException();

        solution = new Stack<>();

        MinPQ<Move> moves = new MinPQ<>();
        moves.insert(new Move(initial));

        MinPQ<Move> twinMoves = new MinPQ<>();
        twinMoves.insert(new Move(initial.twin()));

        while(true) {
            lastMove = nextStep(moves);
            if (lastMove != null || nextStep(twinMoves) != null) break;
        }

        numMoves = lastMove != null ? lastMove.numMoves : -1;

        solution = new Deque<Board>();
        while(lastMove != null) {
            solution.addFirst(lastMove.board);
            lastMove = lastMove.previous;
        }

    }

    private Move nextStep(MinPQ<Move> pq){
        Move best = pq.delMin();
        if (best.board.isGoal()) return best;
        for (Board neighbor : best.board.neighbors()) {
            if (best.previous == null || !neighbor.equals(best.previous.board)) {
                pq.insert(new Move(neighbor, best));

            }
        }
        return null;
    }

    public boolean isSolvable() {
        return lastMove != null;
    }

    public int moves() {
        return numMoves;
    }

    public Iterable<Board> solution() {
        if (isSolvable()) return null;
        return solution;
    }

    public static void main(String[] args) {

        int n = StdIn.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = StdIn.readInt();
        Board initial = new Board(blocks);

        Solver solver = new Solver(initial);

        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

}
