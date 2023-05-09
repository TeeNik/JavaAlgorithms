import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.TrieSET;

public class BoggleSolver {

    private TrieSET trie;

    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {
        trie = new TrieSET();
        for (String s : dictionary) {
            trie.add(s);
        }
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        int row = board.rows();
        int col = board.cols();
        SET<String> result = new SET<String>();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                String word = "";
                boolean marked[][] = new boolean[row][col];
                dfs(marked, i, j, word, result, board);
            }
        }
        return result;
    }

    private void dfs(boolean[][] marked, int i, int j, String word, SET<String> result, BoggleBoard board) {

        if (marked[i][j]) {
            return;
        }

        char ch = board.getLetter(i, j);
        String prefix = ch == 'Q' ? word + "QU" : word + ch;

        if (prefix.length() > 2 && trie.contains(prefix)) {
            result.add(prefix);
        }

        if (!hasPrefix(prefix)) {
            return;
        }

        marked[i][j] = true;

        for (int a = -1; a <= 1; ++a) {
            for (int b = -1; b <= 1; ++b) {
                if (a == 0 && b == 0) {
                    continue;
                }

                int in = i + a;
                int jn = j + b;

                if (in >= 0 && in < board.rows() && jn >= 0 && jn < board.cols() && !marked[in][jn]) {
                    dfs(marked, in, jn, prefix, result, board);
                }

            }
        }

        marked[i][j] = false;
    }

    private boolean hasPrefix(String prefix)
    {
        for (String s : trie.keysWithPrefix(prefix)) {
            return true;
        }
        return false;
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        if (trie.contains(word)) {
            final int length = word.length();
            if (length < 3)
                return 0;
            else if (length < 5)
                return 1;
            else if (length == 5)
                return 2;
            else if (length == 6)
                return 3;
            else if (length == 7)
                return 5;
            else
                return 11;
        }
        return 0;
    }
}
