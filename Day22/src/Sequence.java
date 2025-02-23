import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Sequence {
    final int p1, p2, p3, p4;

    public Sequence(List<Integer> values) {
        p1 = values.getFirst();
        p2 = values.get(1);
        p3 = values.get(2);
        p4 = values.get(3);
    }

    public List<Integer> get() {
        List<Integer> res = new ArrayList<>();
        res.add(p1);
        res.add(p2);
        res.add(p3);
        res.add(p4);
        return res;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Sequence sequence)) return false;
        return p1 == sequence.p1 && p2 == sequence.p2 && p3 == sequence.p3 && p4 == sequence.p4;
    }

    @Override
    public int hashCode() {
        return Objects.hash(p1, p2, p3, p4);
    }
}