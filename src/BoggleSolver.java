import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdOut;

public class BoggleSolver {
    private Trie dict;

    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {
        this.dict = new Trie();
        for (String word : dictionary) {
            this.dict.add(word);
        }
    }

    private SET<String> dfs(BoggleBoard board, int i, int j) {
        boolean[][] marked = new boolean[board.rows()][board.cols()];
        return dfs(board, i, j, "" + board.getLetter(i, j), marked);
    }

    private SET<String> dfs(BoggleBoard g, int i, int j, String prefix,
                            boolean[][] marked) {
        marked[i][j] = true;
        SET<String> words = new SET<String>();

        if (prefix.charAt(prefix.length()-1) == 'Q') {
            prefix += 'U';
        }

        if (dict.containsWord(prefix) && prefix.length() > 2) {
            words.add(prefix);
        }

        for (int a = -1; a <= 1; ++a) {
            for (int b = -1; b <= 1; ++b) {
                if (a == 0 && b == 0) continue;

                int in = i + a;
                int jn = j + b;

                if (in >= 0 && in < g.rows() && jn >= 0 && jn < g.cols() && !marked[in][jn]) {
                    char letter = g.getLetter(in, jn);
                    String word = prefix + letter;
                    if (dict.containsPrefix(word)) {
                        words = words.union(dfs(g, in, jn, word, marked));
                    }
                }

            }
        }

        marked[i][j] = false;

        return words;
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        SET<String> words = new SET<String>();

        for (int i = 0; i < board.rows(); ++i) {
            for (int j = 0; j < board.cols(); ++j) {
                words = words.union(dfs(board, i, j));
            }
        }

        return words;
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        if (!dict.containsWord(word)) return 0;
        int len = word.length();
        if      (len <  3) return 0;
        else if (len <  5) return 1;
        else if (len == 5) return 2;
        else if (len == 6) return 3;
        else if (len == 7) return 5;
        return 11;
    }

    /**
     * args[0] - dictionary
     * args[1] - board
     */
    public static void main(String args[]) {
        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard(args[1]);
        int score = 0;
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
    }
}

