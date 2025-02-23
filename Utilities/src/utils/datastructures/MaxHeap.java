package utils.datastructures;

import java.util.Comparator;

public class MaxHeap<T extends Comparable<T>> extends Heap<T> {
    public MaxHeap() {
        this.rule = Comparator.reverseOrder();
    }
}
