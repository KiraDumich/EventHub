package util;

import java.util.ArrayList;
import java.util.List;

public class DataContainer<T> {
    private final List<T> list;
    private final int maxSize;

    public DataContainer(int maxSize) {
        this.maxSize = maxSize;
        this.list = new ArrayList<>();
    }

    public synchronized void add(T item) throws Exception {
        if (list.size() >= maxSize) {
            throw new Exception("Container full");
        }
        list.add(item);
    }

    public synchronized boolean remove(T item) {
        return list.remove(item);
    }

    public synchronized List<T> snapshot() {
        return new ArrayList<>(list);
    }

    public synchronized int size() {
        return list.size();
    }
}
