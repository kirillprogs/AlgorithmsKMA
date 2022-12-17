package DataStructures;

import java.util.Iterator;

public interface Queue<T> extends Iterable<T> {

    void enqueue(T element);

    T dequeue();

    boolean isEmpty();

    int size();

    Iterator<T> iterator();
}