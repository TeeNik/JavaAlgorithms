import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;

import java.util.Arrays;
import java.util.Iterator;

public class SAP {
    private final Digraph sap;
    private int ancestor;
    private int minDist;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        this.sap = new Digraph(G);
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        bfdp(v, w);
        return minDist;
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        bfdp(v, w);
        return ancestor;
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        validateVertices(v);
        validateVertices(w);

        if (!v.iterator().hasNext() || !w.iterator().hasNext()) {
            return -1;
        }

        bfdp(v, w);
        return minDist;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        validateVertices(v);
        validateVertices(w);
        if (!v.iterator().hasNext() || !w.iterator().hasNext()) {
            return -1;
        }
        bfdp(v, w);
        return ancestor;
    }

    private void bfdp(Integer v, Integer w) {
        checkBounds(v, w);
        bfdp(Arrays.asList(new Integer[] {v}), Arrays.asList(new Integer[] {w}));

    }

    private void bfdp(Iterable<Integer> v, Iterable<Integer> w) {
        checkBounds(v, w);

        BreadthFirstDirectedPaths bfdpV = new BreadthFirstDirectedPaths(sap, v);
        BreadthFirstDirectedPaths bfdpW = new BreadthFirstDirectedPaths(sap, w);

        minDist = -1;
        ancestor = -1;

        for (int i = 0; i < sap.V(); ++i) {
            if (bfdpV.hasPathTo(i) && bfdpW.hasPathTo(i)) {
                int dist = bfdpV.distTo(i) + bfdpW.distTo(i);
                if (minDist < 0 || dist < minDist) {
                    minDist = dist;
                    ancestor = i;
                }
            }
        }
    }

    private void checkBounds(Integer v, Integer w) {
        if (v == null || v < 0 || v > sap.V() - 1) {
            throw new IllegalArgumentException();
        }

        if (w == null || w < 0 || w > sap.V() - 1) {
            throw new IllegalArgumentException();
        }
    }

    private void checkBounds(Iterable<Integer> v, Iterable<Integer> w) {
        if ( v == null || w == null ) {
            throw new IllegalArgumentException();
        }

        Iterator<Integer> iter = v.iterator();
        while (iter.hasNext()) {
            Integer tmp = iter.next();
            if (tmp == null || tmp < 0 || tmp > sap.V() - 1) {
                throw new IllegalArgumentException();
            }
        }

        iter = w.iterator();
        while (iter.hasNext()) {
            Integer tmp = iter.next();
            if (tmp == null || tmp < 0 || tmp > sap.V() - 1) {
                throw new IllegalArgumentException();
            }
        }
    }

    // throw an IllegalArgumentException unless {@code 0 <= v < V}
    private void validateVertex(int v) {
        if (v < 0 || v >= sap.V())
            throw new IllegalArgumentException();
    }

    // throw an IllegalArgumentException unless {@code 0 <= v < V}
    private void validateVertices(Iterable<Integer> vertices) {
        if (vertices == null) {
            throw new IllegalArgumentException();
        }
        for (Integer v : vertices) {
            if (v == null || v < 0 || v >= sap.V()) {
                throw new IllegalArgumentException();
            }
        }
    }

    // do unit testing of this class
    public static void main(String[] args) {

    }
}
