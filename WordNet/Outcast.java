/* Written by Kyrylo Pervushyn on 29 November 2022. */
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {

    private final WordNet wordNet;

    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        this.wordNet = wordnet;
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        if (nouns.length < 2)
            throw new IllegalArgumentException();
        String result = null;
        int maxSum = 0;
        for (String s : nouns) {
            int sum = 0;
            for (String noun : nouns)
                sum += wordNet.distance(s, noun);
            if (result == null || sum > maxSum) {
                result = s;
                maxSum = sum;
            }
        }
        return result;
    }

    // see test client below
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}