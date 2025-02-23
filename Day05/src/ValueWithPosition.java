import com.sun.jdi.Value;

public class ValueWithPosition implements Comparable<ValueWithPosition>{
    int value;
    int position;

    public ValueWithPosition(int value, int position) {
        this.value = value;
        this.position = position;
    }

    @Override
    public int compareTo(ValueWithPosition o) {
        if (value < o.value) return -1;
        if (value > o.value) return 1;
        return Integer.compare(position, o.position);
    }
}
