/* Written by Kyrylo Pervushyn on 18 October 2022 */
package DataStructures;

import java.util.Iterator;

public class ArrayStack<T> implements Stack<T> {

    private Object[] data;
    private int capacity;

    public ArrayStack() {
        data = new Object[1];
    }

    @Override
    public void push(T element) {
        /* double length if array is full */
        if (capacity == data.length)
            resize(2 * data.length);
        data[capacity] = element;
        ++capacity;
    }

    @Override
    public T pop() {
        if (capacity == 0) return null;
        Object result = data[capacity - 1];
        --capacity;
        data[capacity] = null;
        /* if less than quarter, half array */
        if (capacity == data.length/4)
            resize(data.length/2);
        return (T)result;
    }

    @Override
    public boolean isEmpty() {
        return capacity == 0;
    }

    @Override
    public int size() {
        return capacity;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {

            private int pos = capacity - 1;

            @Override
            public boolean hasNext() {
                return pos >= 0;
            }

            @Override
            public T next() {
                if (!hasNext())
                    return null;
                return (T)data[pos--];
            }
        };
    }

    private void resize(int new_length) {
        Object[] new_array = new Object[new_length];
        for (int i = 0; i < new_array.length && i < data.length; ++i)
            new_array[i] = data[i];
        data = new_array;
    }
}