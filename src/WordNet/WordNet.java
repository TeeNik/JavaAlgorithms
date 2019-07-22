
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class WordNet {

    private HashMap<Integer, String> synsets;
    private HashMap<String, Set<Integer>> nouns;
    private Digraph hypernym;
    private SAP sap;


    public WordNet(String synsets, String hypernyms) {
        this.synsets = new HashMap<>();
        this.nouns = new HashMap<>();

        getSynsets(synsets);
        getHypernyms(hypernyms);

        DirectedCycle cycle = new DirectedCycle(hypernym);
        if (cycle.hasCycle() || !rootedDAG(hypernym)) {
            throw new IllegalArgumentException();
        }

        sap = new SAP(hypernym);
    }

    private void getSynsets(String path) {
        In in = new In(path);

        while (!in.isEmpty()){
            String line = in.readLine();
            String[] fields = line.split(",");
            String[] nounList = fields[1].split(" ");
            Integer id = Integer.parseInt(fields[0]);

            synsets.put(id, fields[1]);

            for (String noun : nounList) {
                Set<Integer> ids = nouns.get(noun);
                if (ids == null) {
                    ids = new HashSet<>();
                }
                ids.add(id);
                nouns.put(noun, ids);
            }
        }
    }

    private boolean rootedDAG(Digraph g) {
        int roots = 0;
        for (int i = 0; i < g.V(); i++) {
            if (!g.adj(i).iterator().hasNext()) {
                roots++;
                if (roots > 1) {
                    return false;
                }
            }
        }
        return roots == 1;
    }

    private void getHypernyms(String path) {
        hypernym = new Digraph(synsets.size());
        In in = new In(path);

        while (!in.isEmpty()) {
            String[] line = in.readLine().split(",");
            Integer id = Integer.valueOf(line[0]);
            for (int i = 1; i < line.length; ++i) {
                Integer edge = Integer.valueOf(line[i]);
                hypernym.addEdge(id, edge);
            }
        }
    }

    public Iterable<String> nouns() {
        return nouns.keySet();
    }

    public boolean isNoun(String word) {
        if (word != null) return nouns.containsKey(word);
        else throw new java.lang.IllegalArgumentException();
    }

    public int distance(String nounA, String nounB) {
        if(!isNoun(nounA) || !isNoun(nounB)){
            throw new IllegalArgumentException();
        }
        Iterable<Integer> it0 = nouns.get(nounA);
        Iterable<Integer> it1 = nouns.get(nounB);

        return sap.length(it0, it1);
    }

    public String sap(String nounA, String nounB) {
        if(!isNoun(nounA) || !isNoun(nounB)){
            throw new IllegalArgumentException();
        }
        Iterable<Integer> itA = nouns.get(nounA);
        Iterable<Integer> itB = nouns.get(nounB);

        return synsets.get(sap.ancestor(itA, itB));
    }

    public static void main(String[] args) {
        WordNet wordnet = new WordNet("synsets.txt", "hypernyms.txt");
        StdOut.println("Number of synsets: " + wordnet.synsets.size());
        assert !wordnet.isNoun("zzyzyzz");
        assert wordnet.isNoun("subpart");
        StdOut.println(wordnet.sap("worm", "bird"));
    }
}