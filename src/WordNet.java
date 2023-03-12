import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;

import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

public class WordNet {

    private HashMap<Integer, String> synsets;
    private HashMap<String, Set<Integer>> nouns;
    private Digraph hypernyms;
    private SAP sap;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null) {
            throw new IllegalArgumentException();
        }

        this.synsets = new HashMap<>();
        this.nouns = new HashMap<>();

        readSynsets(synsets);
        readHypernyms(hypernyms);

        DirectedCycle cycle = new DirectedCycle(this.hypernyms);
        if (cycle.hasCycle() || !rootedDAG(this.hypernyms)) {
            throw new IllegalArgumentException();
        }

        sap = new SAP(this.hypernyms);
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return nouns.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null) throw new IllegalArgumentException();
        return nouns.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException();
        }

        Iterable<Integer> itA = nouns.get(nounA);
        Iterable<Integer> itB = nouns.get(nounB);

        return sap.length(itA, itB);
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException();
        }

        Iterable<Integer> itA = nouns.get(nounA);
        Iterable<Integer> itB = nouns.get(nounB);

        return synsets.get(sap.ancestor(itA, itB));
    }

    private void readSynsets(String path) {
        In in = new In(path);

        while (!in.isEmpty()) {
            String line = in.readLine();
            String[] fields = line.split(",");
            String[] nounList = fields[1].split(" ");
            Integer id = Integer.parseInt(fields[0]);

            synsets.put(id, fields[1]);

            for (String noun : nounList) {
                Set<Integer> ids = nouns.get(noun);
                if (ids == null) {
                    ids = new TreeSet<>();
                }
                ids.add(id);
                nouns.put(noun, ids);
            }
        }
    }

    private boolean rootedDAG(Digraph g) {
        int roots = 0;
        for (int i = 0; i < g.V(); ++i) {
            if (!g.adj(i).iterator().hasNext()) {
                ++roots;
                if (roots > 1) {
                    return false;
                }
            }
        }
        return roots == 1;
    }

    private void readHypernyms(String path) {
        In in = new In(path);
        this.hypernyms = new Digraph(synsets.size());


        while (!in.isEmpty()) {
            String line = in.readLine();
            String fields[] = line.split(",");
            Integer id = Integer.parseInt(fields[0]);

            for (int i = 1; i < fields.length; ++i) {
                Integer edge = Integer.parseInt(fields[i]);
                hypernyms.addEdge(id, edge);
            }
        }
    }

    // do unit testing of this class
    public static void main(String[] args) {

    }
}
