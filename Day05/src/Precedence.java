import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Precedence {
    int value;
    List<Integer> nextList = new ArrayList<>();

    public Precedence(Rule rule) {
        value = rule.first;
        addRule(rule);
    }

    public void addRule(Rule rule) {
        nextList.add(rule.second);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Precedence that)) return false;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
