import java.util.Objects;

public class Input {
    String line;
    int index;

    public Input(String line, int index) {
        this.line = line;
        this.index = index;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Input input)) return false;
        return index == input.index && Objects.equals(line, input.line);
    }

    @Override
    public int hashCode() {
        return Objects.hash(line, index);
    }
}
