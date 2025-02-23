import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FlexibleInt {
    final long exponent = 6;
    final long mod = (long)Math.pow(10, exponent);
    List<Long> values;

    public FlexibleInt (long value) {
        this.values = new ArrayList<>();
        this.values.add(value);
        this.adjust();
    }

    public FlexibleInt (FlexibleInt flexibleInt) {
        this.values = new ArrayList<>();
        this.values.addAll(flexibleInt.values);
    }

    public void adjust() {
        for (int i = 0; i < values.size()-1; i++) {
            values.set(i+1, values.get(i+1) + values.get(i)/mod);
            values.set(i, values.get(i)%mod);
        }
        long increment = values.getLast()/mod;
        while (increment > 0) {
            values.set(values.size()-1, values.getLast()%mod);
            values.add(increment);
            increment = values.getLast()/mod;
        }
    }

    public void add(long value) {
        this.values.set(0, this.values.getFirst()+value);
        this.adjust();
    }

    public void add(FlexibleInt other) {
        FlexibleInt min, max;
        if (other.values.size() > this.values.size()) {
            min = this;
            max = other;
        }
        else {
            min = other;
            max = this;
        }
        for (int i = 0; i < min.values.size(); i++) {
            this.values.set(i, this.values.get(i) + other.values.get(i));
        }
        if (min == this) {
            for (int i = min.values.size(); i < max.values.size(); i++) {
                long val = other.values.get(i);
                this.values.add(val);
            }
        }
        this.adjust();
    }

    public void sub(FlexibleInt other) {
        for (int i = 0; i < other.values.size(); i++) {
            values.set(i, values.get(i) - other.values.get(i));
        }
    }

    public void mul(long factor) {
        for (int i = 0; i < values.size(); i++) {
            this.values.set(i, this.values.get(i)*factor);
        }
        this.adjust();
    }

    public static FlexibleInt sum(FlexibleInt a, FlexibleInt b) {
        FlexibleInt res = new FlexibleInt(a);
        res.add(b);
        return res;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        boolean leading = true;
        for (long num : this.values.reversed()) {
            if (leading) {
                builder.append(num);
                leading = false;
            } else {
                builder.append(String.format("%0"+exponent+"d", num));
            }
        }
        return builder.toString();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof FlexibleInt that)) return false;
        return Objects.equals(values, that.values);
    }

    @Override
    public int hashCode() {
        return Objects.hash(exponent, mod, values);
    }

    public int compareTo (long val) {
        FlexibleInt other = new FlexibleInt(val);
        if (this.values.size() > other.values.size()) return 1;
        if (this.values.size() < other.values.size()) return -1;
        for (int i = this.values.size()-1; i >= 0; i--) {
            if (this.values.get(i) > other.values.get(i)) return 1;
            if (this.values.get(i) < other.values.get(i)) return -1;
        }
        return 0;
    }
}
