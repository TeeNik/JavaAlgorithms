import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.KosarajuSharirSCC;

import java.util.HashMap;
import java.util.Iterator;

public class WordNet {

    private HashMap<Integer, String> synsets;
    private HashMap<String, Node> nouns;
    private int numSynsets;
    private Digraph hypernym;
    private SAP sap;

    private class Node {
        private int id;
        private Node next;

        public Node(int id){
            this.id = id;
        }
    }

    public WordNet(String synsets, String hypernyms){
        this.synsets = new HashMap<>();
        this.nouns = new HashMap<>();

        getSynsets(synsets);
        getHypernyms(hypernyms);
    }

    private void getSynsets(String path){
        In in = new In(path);

        while (!in.isEmpty()){
            String line = in.readLine();
            String[] fields = line.split(",");
            String[] nounList = fields[1].split(" ");

            for (int i = 0; i < nounList.length; ++i){
                String noun = nounList[i];
                Integer id = Integer.parseInt(fields[0]);
                if(nouns.containsKey(noun)){
                    Node temp = nouns.get(noun);
                    Node first = temp;

                    while (temp.next != null) {
                        temp = temp.next;
                    }

                    temp.next = new Node(id);
                    nouns.put(noun, first);
                } else {
                    nouns.put(noun, new Node(id));
                }
                synsets.put(Integer.parseInt(fields[0]), fields[1]);
                numSynsets++;
            }
        }
    }

    private void getHypernyms(String path){
        hypernym = new Digraph(numSynsets);
        In in = new In(path);
        int roots = 0;
        boolean[] rootCheck = new boolean[numSynsets];

        while (!in.isEmpty()){
            String line = in.readLine();
            String[] fields = line.split(",");
            int length = fields.length;
            if(length == 1){
                roots++;
            }
            Integer id = Integer.parseInt(fields[0]);
            rootCheck[id] = true;
            for (int i = 1; i < length; ++i){
                hypernym.addEdge(id, Integer.parseInt(fields[i]));
            }
        }

        for (int i = 0; i < rootCheck.length; ++i){
            if(!rootCheck[i]) ++roots;
        }

        if(roots != 1) throw new IllegalArgumentException();

        KosarajuSharirSCC testDAG = new KosarajuSharirSCC(hypernym);
        if(testDAG.count() > numSynsets){
            throw new IllegalArgumentException();
        }
        sap = new SAP(hypernym);
    }

         public Iterable<String> nouns(){
        return nouns.keySet();
    }
    public boolean isNoun(String word){
        if (word != null) return nouns.containsKey(word);
        else throw new java.lang.NullPointerException();
    }

    public int distance(String nounA, String nounB){
        
    }

    public String sap(String nounA, String nounB){
        if(!isNoun(nounA) || !isNoun(nounB)){
            throw new IllegalArgumentException();
        }

        Iterable<Integer> itA = getIter(nounA);
        Iterable<Integer> itB = getIter(nounB);
    }

    private Iterable<Integer> getIter(final String noun) {
        return () -> new Iterator<>() {
            Node curr = nouns.get(noun);

            public boolean hasNext() {
                return curr != null;
            }

            public Integer next() {
                Integer val = curr.id;
                curr = curr.next;
                return val;
            }

            public void remove() {
            }
        };
    }

    public static void main(String[] args){

    }
}