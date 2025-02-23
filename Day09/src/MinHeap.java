import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class MinHeap<T extends Comparable<T>> {
    List<T> values = new ArrayList<>();

    int parent(int index) {
        return (index - 1) / 2;
    }

    int left(int index) {
        return 2*index + 1;
    }

    int right(int index) {
        return 2*index + 2;
    }

    public void insert(T element) {
        values.add(element);
        int index = values.size()-1;
        int prev = parent(index);
        while (prev >= 0 && values.get(index).compareTo(values.get(prev)) < 0) {
            swap(prev, index);
            index = prev;
            prev = parent(index);
        }
    }

    public T pop() {
        T result = values.getFirst();
        swap(0, values.size()-1);
        values.removeLast();
        heapify(0);
        return result;
    }

    protected void heapify(int index) {
        int l = left(index);
        int r = right(index);
        int min = index;
        if (l < values.size() && values.get(l).compareTo(values.get(min)) < 0) {
            min = l;
        }
        if (r < values.size() && values.get(r).compareTo(values.get(min)) < 0) {
            min = r;
        }
        if (min != index) {
            swap(min, index);
            heapify(min);
        }
    }

    public T min() {
        return values.getFirst();
    }

    public boolean isEmpty() {
        return values.isEmpty();
    }

    public void swap(int i, int j) {
        Collections.swap(values, i, j);
    }

    public boolean isCorrect(int index) {
        if (index >= values.size()) return true;
        int l = left(index);
        int r = right(index);
        if ((l < values.size() && values.get(index).compareTo(values.get(l)) > 0) ||
                (r < values.size() && values.get(index).compareTo(values.get(r)) > 0))
            return false;
        if (l < values.size()) {
            if (!isCorrect(l))
                return false;
        }
        return r < values.size() || isCorrect(r);
    }
}
