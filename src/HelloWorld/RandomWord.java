import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        int count = 0;
        String champion = "";

        while(!StdIn.isEmpty()){
            String word = StdIn.readString();
            boolean replace = StdRandom.bernoulli(1.0 / (count + 1));
            if(replace){
                champion = word;
            }
            ++count;
        }
        System.out.println(champion);
    }
}
