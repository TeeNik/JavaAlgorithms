import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;

import java.util.Arrays;

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
        return length(Arrays.asList(new Integer[] {v}), Arrays.asList(new Integer[] {w}));
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        return ancestor(Arrays.asList(new Integer[] {v}), Arrays.asList(new Integer[] {w}));
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        bfdp(v, w);
        return minDist;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        bfdp(v, w);
        return ancestor;
    }

    private void bfdp(Iterable<Integer> v, Iterable<Integer> w) {
        BreadthFirstDirectedPaths bfdpV = new BreadthFirstDirectedPaths(sap, v);
        BreadthFirstDirectedPaths bfdpW = new BreadthFirstDirectedPaths(sap, w);

        minDist = -1;
        ancestor = -1;

        for (int i = 0; i < sap.V(); ++i) {
            if (bfdpV.hasPathTo(i) && bfdpW.hasPathTo(i)) {
                int dist = bfdpV.distTo(i) + bfdpW.distTo(i);
                if (minDist < dist) {
                    minDist = dist;
                    ancestor = i;
                }
            }
        }
    }

    // do unit testing of this class
    public static void main(String[] args) {

    }
}
