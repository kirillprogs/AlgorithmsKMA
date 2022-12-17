/* Written by Kyrylo Pervushyn on 29 November 2022. */
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.LinkedList;
import java.util.List;

public class SAP {

    private final Digraph graph;

    private static class Result {
        int ancestor;
        int length;
        Result(int ancestor, int length) {
            this.ancestor = ancestor;
            this.length = length;
        }
    }

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G == null)
            throw new IllegalArgumentException();
        graph = new Digraph(G);
    }

    // length of the shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        List<Integer> listV = new LinkedList<>();
        listV.add(v);
        List<Integer> listW = new LinkedList<>();
        listW.add(w);
        return length(listV, listW);
    }

    // a common ancestor of v and w that participates in the shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        List<Integer> listV = new LinkedList<>();
        listV.add(v);
        List<Integer> listW = new LinkedList<>();
        listW.add(w);
        return ancestor(listV, listW);
    }

    // length of the shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null)
            throw new IllegalArgumentException();
        return dfs(v, w).length;
    }

    // a common ancestor that participates in the shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null)
            throw new IllegalArgumentException();
        return dfs(v, w).ancestor;
    }

    private Result dfs(Iterable<Integer> v, Iterable<Integer> w) {
        boolean[] marked1 = new boolean[graph.V()];
        boolean[] marked2 = new boolean[graph.V()];
        int[] distTo1 = new int[graph.V()];
        int[] distTo2 = new int[graph.V()];
        process(v, marked1, distTo1);
        process(w, marked2, distTo2);
        int length = -1;
        int ancestor = -1;
        for (int i = 0; i < graph.V(); ++i) {
            if (marked1[i] && marked2[i]) {
                int sum = distTo1[i] + distTo2[i];
                if (sum < length || length == -1) {
                    length = sum;
                    ancestor = i;
                }
            }
        }
        return new Result(ancestor, length);
    }

    private void process(Iterable<Integer> v, boolean[] marked, int[] distTo) {
        Queue<Integer> queue = new Queue<>();
        for (Integer vertex : v) {
            if (vertex == null || vertex < 0 || vertex >= graph.V())
                throw new IllegalArgumentException("Vertex out of range!");
            marked[vertex] = true;
            queue.enqueue(vertex);
        }
        while (!queue.isEmpty()) {
            int vertex = queue.dequeue();
            for (int adj : graph.adj(vertex)) {
                if (!marked[adj]) {
                    queue.enqueue(adj);
                    marked[adj] = marked[vertex];
                    distTo[adj] = distTo[vertex] + 1;
                }
            }
        }
    }

    // do unit testing of this class
    public static void main(String[] args) {
        In in = new In();
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length   = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}