package BurrowsWheeler;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

import java.util.LinkedList;

public class MoveToFront {

    private static final int R = 256;

    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {

        LinkedList<Character> aux = new LinkedList<>();
        for (int i = 0; i < R; i++) {
            aux.add((char)i);
        }

        while(!BinaryStdIn.isEmpty()) {
            char c = BinaryStdIn.readChar();
            int toFront = aux.indexOf(c);
            aux.remove(toFront);
            aux.addFirst(c);
            BinaryStdOut.write(toFront, 8);
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
        LinkedList<Character> aux = new LinkedList<>();
        for (int i = 0; i < R; i++) {
            aux.add((char)i);
        }

        while(!BinaryStdIn.isEmpty()) {
            char toFront = BinaryStdIn.readChar();
            char c = aux.remove((int)toFront);
            aux.addFirst(c);
            BinaryStdOut.write(c);
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
