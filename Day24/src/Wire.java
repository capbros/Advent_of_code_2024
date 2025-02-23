import java.util.Objects;

public class Wire {
    final String name;
    final boolean value;

    public Wire(String name, boolean value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Wire wire)) return false;
        return Objects.equals(name, wire.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
