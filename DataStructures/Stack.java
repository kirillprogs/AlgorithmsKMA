package DataStructures;

import java.util.Iterator;

public interface Stack<T> extends Iterable<T> {

    void push(T element);

    T pop();

    boolean isEmpty();

    int size();

    Iterator<T> iterator();
}