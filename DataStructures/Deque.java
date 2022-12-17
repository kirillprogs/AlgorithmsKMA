/* Written by Kyrylo Pervushyn on 18 October 2022 */
package DataStructures;

import java.util.Iterator;

public class Deque<T> implements Iterable<T> {

    Node<T> head;
    Node<T> tail;
    int size;

    /* Implementation uses doubly-linked list.
    * Then all operations run in O(1) time. */
    private static class Node<P> {
        P data;
        Node<P> prev, next;
        Node(P element) { data = element; }
    }

    public void addFront(T element) {
        if (head == null) {
            head = new Node<>(element);
            tail = head;
        } else {
            head.prev = new Node<>(element);
            head.prev.next = head;
            head = head.prev;
        }
        ++size;
    }

    public void addBack(T element) {
        if (tail == null) {
            tail = new Node<>(element);
            head = tail;
        } else {
            tail.next = new Node<>(element);
            tail.next.prev = tail;
            tail = tail.next;
        }
        ++size;
    }

    public T removeFront() {
        if (size == 0)
            return null;
        T result = head.data;
        head = head.next;
        if (head != null)
            head.prev = null;
        else
            tail = null;
        --size;
        return result;
    }

    public T removeBack() {
        if (size == 0)
            return null;
        T result = tail.data;
        tail = tail.prev;
        if (tail != null)
            tail.next = null;
        else
            head = null;
        --size;
        return result;
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