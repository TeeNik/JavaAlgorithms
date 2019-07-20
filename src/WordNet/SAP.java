import edu.princeton.cs.algs4.*;

import java.util.Arrays;
import java.util.Iterator;

public class SAP {

    private final Digraph sap;
    private int ancestor;
    private int minDist;

    public SAP(Digraph G){
        sap = new Digraph(G);
    }

    public int length(int v, int w){
        bfdp(v, w);
        return minDist;
    }

    private void checkBounds(int v, int w) {
        if (v < 0 || v > sap.V() - 1)
            throw new java.lang.IndexOutOfBoundsException();

        if (w < 0 || w > sap.V() - 1)
            throw new java.lang.IndexOutOfBoundsException();
    }

    private void checkBounds(Iterable<Integer> v, Iterable<Integer> w) {
        Iterator<Integer> iter = v.iterator();
        while (iter.hasNext()) {
            int tmp = iter.next();
            if (tmp < 0 || tmp > sap.V() - 1)
                throw new java.lang.IndexOutOfBoundsException();
        }

        iter = w.iterator();
        while (iter.hasNext()) {
            int tmp = iter.next();
            if (tmp < 0 || tmp > sap.V() - 1)
                throw new java.lang.IndexOutOfBoundsException();
        }
    }

    private void bfdp(int v, int w){
        checkBounds(v, w);
        bfdp(Arrays.asList(new Integer[]{v}), Arrays.asList(new Integer[]{w}));
    }

    private void bfdp(Iterable<Integer> v, Iterable<Integer> w){
        checkBounds(v, w);

        BreadthFirstDirectedPaths bfdpV = new BreadthFirstDirectedPaths(sap, v);
        BreadthFirstDirectedPaths bfdpW = new BreadthFirstDirectedPaths(sap, w);

        minDist = -1;
        ancestor = -1;

        for (int i = 0; i < sap.V(); ++i){
            if(bfdpV.hasPathTo(i) && bfdpW.hasPathTo(i)){
                int newMinDist = bfdpV.distTo(i) + bfdpW.distTo(i);
                if(newMinDist < minDist){
                    minDist = newMinDist;
                    ancestor = i;
                }
            }
        }
    }

    public int ancestor(int v, int w){
        bfdp(v, w);
        return ancestor;
    }

    public int length(Iterable<Integer> v, Iterable<Integer> w){
        bfdp(v, w);
        return minDist;
    }

    public int ancestor(Iterable<Integer> v, Iterable<Integer> w){
        bfdp(v, w);
        return ancestor;
    }

    public static void main(String[] args){
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
