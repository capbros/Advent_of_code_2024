package utils.datastructures;

import java.util.Comparator;

public class MinHeap<T extends Comparable<T>> extends Heap<T>{
    public MinHeap() {
        this.rule = Comparator.naturalOrder();
    }
}
