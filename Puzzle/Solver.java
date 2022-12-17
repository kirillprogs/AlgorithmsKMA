/* Written by Kyrylo Pervushyn on 15 November 2022 */
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;
import java.util.Comparator;
import java.util.LinkedList;

public class Solver {

    /* Represents bard */
    private static class Node {
        private final Board board;
        private final Node prev;
        private final int steps;
        private final int priority;
        private Node(Board board, Node prev, int steps) {
            this.board = board;
            this.prev = prev;
            this.steps = steps;
            priority = steps + board.manhattan();
        }
    }

    private final LinkedList<Board> solution;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null)
            throw new IllegalArgumentException();
        boolean solved = false;
        /* Create two queues: one for given layout and other for its twin. */
        MinPQ<Node> queue = new MinPQ<>(Comparator.comparingInt(o -> o.priority));
        MinPQ<Node> twinQueue = new MinPQ<>(Comparator.comparingInt(o -> o.priority));
        /* Insert boards in a corresponding queue. */
        Node node = new Node(initial, null, 0);
        queue.insert(node);
        Node twinNode = new Node(initial.twin(), null, 0);
        twinQueue.insert(twinNode);
        for (;;) { /* Main loop. */
            node = queue.delMin();
            if (node.board.isGoal()) { /* Initial board is solved. */
                solved = true;
                break;
            }
            /* Insert all neighbours in a queue. */
            for (Board board : node.board.neighbors()) {
                Node newNode = new Node(board, node, node.steps + 1);
                if (node.prev == null || !newNode.board.equals(node.prev.board))
                    queue.insert(newNode);
            }
            twinNode = twinQueue.delMin();
            if (twinNode.board.isGoal()) /* Twin was solved: no solution. */
                break;
            /* Insert twins neighbours in twins queue. */
            for (Board board : twinNode.board.neighbors()) {
                Node newNode = new Node(board, twinNode, twinNode.steps + 1);
                if (node.prev == null || !newNode.board.equals(twinNode.prev.board))
                    twinQueue.insert(newNode);
            }
        }
        /* If no solution, <solution> field will be null. */
        solution = solved ? new LinkedList<>() : null;
        if (solved) {
            solution.add(node.board);
            while (node.prev != null) {
                node = node.prev;
                solution.addFirst(node.board);
            }
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solution != null;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!isSolvable())
            return -1;
        return solution.size() - 1;
    }

    // sequence of boards in the shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return this.solution;
    }

    // test client (see below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}