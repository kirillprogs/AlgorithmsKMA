/* Written by Kyrylo Pervushyn on 18 October 2022 */
package DataStructures;

import java.util.Iterator;

public class Bag<T> implements Iterable<T> {

    private Node<T> head;
    private int size;

    private static class Node<P> {
        P data;
        Node<P> next;
        Node(P o_data, Node<P> o_next) {
            data = o_data;
            next = o_next;
        }
    }

    public void push(T element) {
        head = new Node<>(element, head);
        ++size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

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