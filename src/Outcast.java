public class Outcast {

    private final WordNet wordnet;

    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        this.wordnet = wordnet;
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        String outcast = null;
        int max = 0;

        for (String nounA : nouns) {
            int dist = 0;
            for (String nounB : nouns) {
                if (!nounA.equals(nounB)) {
                    dist += wordnet.distance(nounA, nounB);
                }
            }
            if (dist > max) {
                max = dist;
                outcast = nounA;
            }
        }

        return outcast;
    }

    // see test client below
    public static void main(String[] args) {

    }
}