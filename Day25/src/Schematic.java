import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Schematic {
    private List<Integer> heights = new ArrayList<>();

    public void init(List<Integer> input) {
        heights = new ArrayList<>(input);
    }

    public void add(List<Integer> input) throws IllegalArgumentException {
        if (input.size() != heights.size())
            throw new IllegalArgumentException("Input list and heights parameter have different sizes");
        for (int i = 0; i < heights.size(); i++) {
            heights.set(i, heights.get(i)+input.get(i));
        }
    }

    public List<Integer> heights() {
        return heights;
    }

    public Schematic getMatch(int tot) {
        Schematic res = new Schematic();
        res.init(heights.stream().map(x -> tot - x).toList());
        return res;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Schematic schematic)) return false;
        return Objects.equals(heights, schematic.heights);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(heights);
    }

    @Override
    public String toString() {
        return "Schematic{" +
                "heights=" + heights +
                '}';
    }
}
