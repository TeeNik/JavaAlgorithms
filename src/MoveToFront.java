import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class MoveToFront {

    private static final int R = 256;

    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {
        char[] aux = new char[R];
        for (int i = 0; i < R; ++i) {
            aux[i] = (char) i;
        }

        String in = BinaryStdIn.readString();
        for (int i = 0; i < in.length(); ++i) {
            move(aux, in.charAt(i), true);
        }

        BinaryStdOut.close();
    }

    private static void move(char[] aux, char c, boolean printChar) {
        char before = aux[0];
        for (int i = 0; i < R; ++i) {
            if (aux[i] == c) {
                aux[0] = c;
                aux[i] = before;

                if (printChar) {
                    BinaryStdOut.write((char)i);
                }
            }

            char temp = aux[i];
            aux[i] = before;
            before = temp;
        }
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
        char[] aux = new char[R];
        for (int i = 0; i < R; ++i) {
            aux[i] = (char) i;
        }

        while (!BinaryStdIn.isEmpty()) {
            int pos = BinaryStdIn.readInt(8);
            BinaryStdOut.write(aux[pos]);
            move(aux, aux[pos], false);
        }
        BinaryStdOut.close();
    }

    // if args[0] is "-", apply move-to-front encoding
    // if args[0] is "+", apply move-to-front decoding
    public static void main(String[] args) {
        if (args[0].equals("-")) MoveToFront.encode();
        if (args[0].equals("+")) MoveToFront.decode();
    }
}
