package utils.datastructures;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class Heap<T extends Comparable<T>> {
    protected Comparator<T> rule;
    protected List<T> values = new ArrayList<>();

    protected int parent(int index) {
        return (index - 1) / 2;
    }

    protected int left(int index) {
        return 2*index + 1;
    }

    protected int right(int index) {
        return 2*index + 2;
    }

    protected void swap(int i, int j) {
        Collections.swap(values, i, j);
    }

    protected int size() {
        return values.size();
    }

    public boolean isEmpty() {
        return size()==0;
    }

    protected void downFix(int index) {
        int l = left(index);
        int r = right(index);
        int min = index;
        if (l < values.size() && rule.compare(values.get(l), values.get(min)) < 0) {
            min = l;
        }
        if (r < values.size() && rule.compare(values.get(r), values.get(min)) < 0) {
            min = r;
        }
        if (min != index) {
            swap(min, index);
            downFix(min);
        }
    }
    protected void upFix(int index) {
        int prev = parent(index);
        while (prev >= 0 && rule.compare(values.get(index), values.get(prev)) < 0) {
            swap(prev, index);
            index = prev;
            prev = parent(index);
        }
    }
    public void insert(T el) {
        values.add(el);
        upFix(values.size()-1);
    }
    public T pop() {
        T res = values.getFirst();
        swap(0, values.size()-1);
        values.removeLast();
        downFix(0);
        return res;
    }

    public T first() {
        return values.getFirst();
    }

    public T remove(int index) {
        T res = values.get(index);
        swap(index, values.size()-1);
        values.removeLast();
        T tmp = values.get(index);
        upFix(index);
        if (values.get(index) == tmp) downFix(index);
        return res;
    }

    protected int indexOf(T target) {
        return values.indexOf(target);
    }

    public T remove(T target) {
        return remove(indexOf(target));
    }

    public void update(int index, T newValue) {
        values.set(index, newValue);
        upFix(index);
        if (values.get(index) == newValue) {
            downFix(index);
        }
    }

    public void update(T newValue) {
        update(indexOf(newValue), newValue);
    }
}
