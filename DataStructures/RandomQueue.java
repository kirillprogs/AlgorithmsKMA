/* Written by Kyrylo Pervushyn on 18 October 2022 */
package DataStructures;

import java.util.Iterator;
import java.util.Random;

public class RandomQueue<T> implements Queue<T> {

    private Node<T> head;
    private int size;
    private final Random rand;

    /* All operations take O(1) except for dequeue(),
    * which takes O(size) time in the worst case and
    * O(1) time in the best case. */
    public RandomQueue() {
        rand = new Random(System.currentTimeMillis());
    }

    private static class Node<P> {
        P data;
        Node<P> next;
        Node(P o_data, Node<P> o_next) {
            data = o_data;
            next = o_next;
        }
    }

    @Override
    public void enqueue(T element) {
        /* Inserts element in the beginning of the list */
        head = new Node<>(element, head);
        ++size;
    }

    @Override
    public T dequeue() {
        if (size == 0)
            return null;
        int position = rand.nextInt(size);
        T result;
        if (position == 0) {
            result = head.data;
            head = head.next;
        }
        else {
            Node<T> tmp = head;
            /* moves to the previous element */
            /* before the one that we remove */
            for (int i = 0; i < position - 1; ++i)
                tmp = tmp.next;
            result = tmp.next.data;
            tmp.next = tmp.next.next;
        }
        --size;
        return result;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {

            Node<T> tmp = head;

            @Override
            public boolean hasNext() {
                return tmp != null;
            }

            @Override
            public T next() {
                if (!hasNext())
                    return null;
                T result = tmp.data;
                tmp = tmp.next;
                return result;
            }
        };
    }
}