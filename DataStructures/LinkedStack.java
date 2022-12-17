/* Written by Kyrylo Pervushyn on 18 October 2022 */
package DataStructures;

import java.util.Iterator;

public class LinkedStack<T> implements Stack<T> {

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

    @Override
    public void push(T element) {
        head = new Node<>(element, head);
        ++size;
    }

    @Override
    public T pop() {
        if (head == null)
            return null;
        T value = head.data;
        head = head.next;
        --size;
        return value;
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