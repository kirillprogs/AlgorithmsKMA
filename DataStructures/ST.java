import java.util.ArrayList;
import java.util.Iterator;

/* Written by Kyrylo Pervushyn on */
public class ST <Key extends Comparable<Key>, Value> {

    private class Node {
        private final Key key;
        private Value value;
        private Node(Key key, Value val) {
            this.key = key;
            this.value = val;
        }
    }

    private final ArrayList<Node> list;

    public ST() {
        this.list = new ArrayList<>();
    }

    /* Puts a value. Neither value, nor key can
    * be <null> (otherwise nothing is done) */
    public void put(Key key, Value val) {
        if (key == null || val == null)
            return;
        int rank = rank(key);
        if (rank < list.size()) {
            /* If <key> present, rewrite with new <value> */
            if (list.get(rank).key.compareTo(key) == 0) {
                list.get(rank).value = val;
                return;
            }
        }
        list.add(rank, new Node(key, val));
    }

    public Value get(Key key) {
        if (key == null)
            return null;
        int rank = rank(key);
        if (rank == list.size())
            return null;
        if (list.get(rank).key.compareTo(key) > 0)
            return null;
        return list.get(rank).value;
    }

    public void delete(Key key) {
        if (key == null)
            return;
        int rank = rank(key);
        if (rank == list.size())
            return;
        if (list.get(rank).key.compareTo(key) > 0)
            return;
        list.remove(rank);
    }

    public boolean contains(Key key) {
        return get(key) != null;
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public int size() {
        return list.size();
    }

    public Key min() {
        if (list.isEmpty())
            return null;
        return list.get(0).key;
    }

    public Key max() {
        if (list.isEmpty())
            return null;
        return list.get(list.size() - 1).key;
    }

    /* Maximum key less or equal than <key> */
    public Key floor(Key key) {
        if (key == null)
            return null;
        if (list.isEmpty())
            return null;
        int rank = rank(key);
        if (rank == 0) { // all are greater
            if (list.get(0).key.compareTo(key) == 0)
                return list.get(0).key;
            return null;
        }
        if (rank == list.size()) // all are less
            return list.get(rank - 1).key;
        if (list.get(rank).key.compareTo(key) == 0)
            return list.get(rank).key;
        return list.get(rank - 1).key;
    }

    /* Minimum key greater or equal than <key> */
    public Key ceiling(Key key) {
        if (key == null)
            return null;
        if (list.isEmpty())
            return null;
        int rank = rank(key);
        if (rank == 0) // all are greater
            return list.get(0).key;
        if (rank == list.size()) // all are less
            return null;
        return list.get(rank).key;
    }

    /* Number of keys less than <key>. */
    private int rank(Key key) {
        int lo = 0, hi = list.size() - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            int cmp = key.compareTo(list.get(mid).key);
            if (cmp < 0)
                hi = mid - 1;
            else if (cmp > 0)
                lo = mid + 1;
            else
                return mid;
        }
        return lo;
    }

    /* Returns <k>-th smallest key */
    public Key select(int k) {
        if (list.size() < k || k <= 0)
            return null;
        return list.get(k - 1).key;
    }

    public void deleteMin() {
        if (list.isEmpty())
            return;
        delete(min());
    }

    public void deleteMax() {
        if (list.isEmpty())
            return;
        delete(max());
    }

    /* Returns number of elements in range <lo>..<hi>
    * Including left, without right bound */
    public int size(Key lo, Key hi) {
        if (lo == null || hi == null)
            return -1;
        int rankLo = rank(lo);
        int rankHi = rank(hi);
        if (rankHi < rankLo)
            return -1;
        if (rankLo == 0 && rankHi == 0)
            return 0;
        return rankHi - rankLo;
    }

    /* Has function <iterator> which
    * is implemented as lambda */
    Iterable<Key> keys() {
        return () -> new Iterator<>() {
            final Iterator<Node> iterator = list.iterator();
            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }
            @Override
            public Key next() {
                return iterator.next().key;
            }
        };
    }

    Iterable<Key> keys(Key lo, Key hi) {
        return () -> new Iterator<>() {
            int pos = 0;
            @Override
            public boolean hasNext() {
                /* Go ip until meet <pos> with <key>
                * in necessary constraints */
                while (pos < list.size() && !(list.get(pos).key.compareTo(lo) >= 0
                        && list.get(pos).key.compareTo(hi) <= 0)) {
                    pos++;
                }
                return pos != list.size();
            }
            @Override
            public Key next() {
                if (!hasNext())
                    return null;
                return list.get(pos++).key;
            }
        };
    }

    void printAll() {
        for (Node node : list)
            System.out.println("Key " + node.key + "\tvalue " + node.value);
    }
}