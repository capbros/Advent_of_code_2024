public class Rule implements Comparable<Rule>{
    final int first;
    final int second;

    public Rule(int first, int second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public String toString() {
        return "Rule{" +
                "first=" + first +
                ", second=" + second +
                '}';
    }

    @Override
    public int compareTo(Rule o) {
        if (this.first < o.first) return -1;
        if (this.first > o.first) return 0;
        return Integer.compare(this.second, o.second);
    }
}
