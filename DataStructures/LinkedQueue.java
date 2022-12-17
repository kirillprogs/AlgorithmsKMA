/* Written by Kyrylo Pervushyn on 18 October 2022 */
package DataStructures;

import java.util.Iterator;

public class LinkedQueue<T> implements Queue<T> {

    private Node<T> head;
    private Node<T> tail;
    private int size;

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
        if (head != null) {
            tail.next = new Node<>(element, null);
            tail = tail.next;
        } else {
            head = new Node<>(element, null);
            tail = head;
        }
        ++size;
    }

    @Override
    public T dequeue() {
        if (head == null)
            return null;
        T result = head.data;
        head = head.next;
        if (tail == head)
            tail = null;
        --size;
        return result;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {

            private Node<T> curr = head;

            @Override
            public boolean hasNext() {
                return curr != null;
            }

            @Override
            public T next() {
                if (!hasNext())
                    return null;
                T result = curr.data;
                curr = curr.next;
                return result;
            }
        };
    }
}