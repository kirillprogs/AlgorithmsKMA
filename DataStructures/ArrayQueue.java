/* Written by Kyrylo Pervushyn on 18 October 2022 */
package DataStructures;

import java.util.Iterator;

public class ArrayQueue<T> implements Queue<T> {

    private Object[] data;
    private int capacity;
    /* Store two indexes: one is where the last
    * element (oldest) is stored, second is one
    * position in front of the first element (newest).
    * Elements of the queue are stored */
    private int read; // where we would read (tail)
    private int write; // where we would write (head)

    public ArrayQueue() {
        data = new Object[1];
        read = 0;
        write = 0;
    }

    @Override
    public void enqueue(T element) {
        if (capacity == data.length)
            resize(2* data.length);
        data[write] = element;
        ++write;
        if (write == data.length)
            write = 0;
        capacity++;
    }

    @Override
    public T dequeue() {
        if (capacity == 0)
            return null;
        Object result = data[read];
        data[read] = null;
        --capacity;
        ++read;
        if (read == data.length)
            read = 0;
        /* Resize array if necessary. Do not resize
        * if capacity is 0 to avoid zero-length array */
        if (capacity > 0 && capacity == data.length / 4)
            resize(data.length / 2);
        return (T) result;
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

            private int cnt = 0;

            @Override
            public boolean hasNext() {
                return cnt < capacity;
            }

            @Override
            public T next() {
                if (!hasNext())
                    return null;
                Object result = data[(cnt + read) % data.length];
                ++cnt;
                return (T)result;
            }
        };
    }

    /* Assume that private method is never called with
    * incorrect argument (new_length < capacity) */
    private void resize(int new_length) {
        Object[] new_array = new Object[new_length];
        for (int i = 0; i < capacity; ++i)
            new_array[i] = data[(read + i) % data.length];
        data = new_array;
        write = capacity;
        read = 0;
    }
}