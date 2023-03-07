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

        for (int i = 0; i < nouns.length; ++i) {
            int dist = 0;
            for (int j = i + 1; j < nouns.length; ++j) {
                dist += wordnet.distance(nouns[i], nouns[j]);
            }
            if (dist > max) {
                max = dist;
                outcast = nouns[i];
            }
        }
        return outcast;
    }

    // see test client below
    public static void main(String[] args) {

    }
}