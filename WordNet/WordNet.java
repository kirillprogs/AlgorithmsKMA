/* Written by Kyrylo Pervushyn on 29 November 2022. */
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

public class WordNet {

    private TreeMap<String, List<Integer>> map;
    private ArrayList<String> list;
    private final SAP sap;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null)
            throw new IllegalArgumentException("Input is null!");
        Digraph graph = buildGraph(hypernyms);
        DirectedCycle cycle = new DirectedCycle(graph);
        if (cycle.hasCycle())
            throw new IllegalArgumentException("Is not a DAG!");
        buildSynsets(synsets);
        sap = new SAP(graph);
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return map.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null)
            throw new IllegalArgumentException();
        return map.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (!isNoun(nounA))
            throw new IllegalArgumentException("No such noun: "+nounA);
        if (!isNoun(nounB))
            throw new IllegalArgumentException("No such noun: "+nounB);
        return sap.length(map.get(nounA), map.get(nounB));
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in the shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (!isNoun(nounA))
            throw new IllegalArgumentException("No such noun: "+nounA);
        if (!isNoun(nounB))
            throw new IllegalArgumentException("No such noun: "+nounB);
        int ancestor = sap.ancestor(map.get(nounA), map.get(nounB));
        if (ancestor == -1)
            return null;
        return list.get(ancestor);
    }

    private void buildSynsets(String synsets) {
        In reader = new In(synsets);
        map = new TreeMap<>();
        list = new ArrayList<>();
        String string = reader.readLine();
        int count = 0;
        while (string != null) {
            String[] parts = string.split(",");
            int vertex = Integer.parseInt(parts[0]);
            if (vertex != count)
                throw new IllegalArgumentException("Incorrect vertex number: "+vertex);
            list.add(count, parts[1]);
            String[] array = parts[1].split("\\s");
            for (String s : array) {
                if (!map.containsKey(s))
                    map.put(s, new LinkedList<>());
                map.get(s).add(count);
            }
            string = reader.readLine();
            count++;
        }
        reader.close();
    }

    private Digraph buildGraph(String hypernyms) {
        In reader = new In(hypernyms);
        TreeMap<Integer, List<Integer>> treeMap = new TreeMap<>();
        String string = reader.readLine();
        int count = 0;
        while (string != null) {
            String[] array = string.split(",");
            int vertex = Integer.parseInt(array[0]);
            if (vertex != count)
                throw new IllegalArgumentException("Incorrect vertex number: "+vertex);
            List<Integer> vertList = new LinkedList<>();
            for (int i = 1; i < array.length; ++i)
                vertList.add(Integer.parseInt(array[i]));
            treeMap.put(vertex, vertList);
            string = reader.readLine();
            count++;
        }
        reader.close();
        Digraph digraph = new Digraph(count);
        for (int v = 0; v < count; ++v)
            for (int w : treeMap.get(v))
                digraph.addEdge(v, w);
        return digraph;
    }

    // do unit testing of this class
    public static void main(String[] args) {
        String synsets = StdIn.readString();
        String hypernyms = StdIn.readString();
        WordNet wordNet = new WordNet(synsets, hypernyms);
        System.out.println("ready!");
        for (;;) {
            String fst = StdIn.readString();
            if (fst.equals("::"))
                break;
            String snd = StdIn.readString();
            if (snd.equals("::"))
                break;
            if (!wordNet.isNoun(fst)) {
                System.out.println("First is not a noun!");
                continue;
            }
            if (!wordNet.isNoun(snd)) {
                System.out.println("Second is not a noun!");
                continue;
            }
            System.out.println("SAP: "+wordNet.sap(fst, snd));
            System.out.println("Distance: "+wordNet.distance(fst, snd));
        }
    }
}