import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Iterator;

public class SAP {

    private final Digraph sap;
    private int ancestor;
    private int minDist;

    public SAP(Digraph G){
        sap = new Digraph(G);
    }

    public int length(int v, int w) {
        bfdp(v, w);
        return minDist;
    }

    private void checkBounds(Integer v, Integer w) {
        if (v == null || v < 0 || v > sap.V() - 1)
            throw new java.lang.IllegalArgumentException();

        if (w == null || w < 0 || w > sap.V() - 1)
            throw new java.lang.IllegalArgumentException();
    }

    private void checkBounds(Iterable<Integer> v, Iterable<Integer> w) {

        if(v == null || w == null) {
            throw new java.lang.IllegalArgumentException();
        }

        Iterator<Integer> iter = v.iterator();
        while (iter.hasNext()) {
            Integer tmp = iter.next();
            if (tmp == null || tmp < 0 || tmp > sap.V() - 1)
                throw new java.lang.IllegalArgumentException();
        }

        iter = w.iterator();
        while (iter.hasNext()) {
            Integer tmp = iter.next();
            if (tmp == null || tmp < 0 || tmp > sap.V() - 1)
                throw new java.lang.IllegalArgumentException();
        }
    }

    private void bfdp(int v, int w) {
        checkBounds(v, w);
        bfdp(Arrays.asList(new Integer[]{v}), Arrays.asList(new Integer[]{w}));
    }

    private void bfdp(Iterable<Integer> v, Iterable<Integer> w) {
        checkBounds(v, w);

        BreadthFirstDirectedPaths bfdpV = new BreadthFirstDirectedPaths(sap, v);
        BreadthFirstDirectedPaths bfdpW = new BreadthFirstDirectedPaths(sap, w);

        minDist = -1;
        ancestor = -1;

        for (int i = 0; i < sap.V(); ++i) {
            if(bfdpV.hasPathTo(i) && bfdpW.hasPathTo(i)){
                int newMinDist = bfdpV.distTo(i) + bfdpW.distTo(i);
                if(minDist < 0 || newMinDist < minDist) {
                    minDist = newMinDist;
                    ancestor = i;
                }
            }
        }
    }

    public int ancestor(int v, int w) {
        bfdp(v, w);
        return ancestor;
    }

    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        bfdp(v, w);
        return minDist;
    }

    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        bfdp(v, w);
        return ancestor;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length   = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}
