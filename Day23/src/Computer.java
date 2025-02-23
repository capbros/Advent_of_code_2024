import java.util.Objects;

public class Computer implements Comparable<Computer>{
    String name;

    public Computer(String name) {
        this.name = name;
    }

    public Computer(Computer other) {
        name = other.name;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Computer computer)) return false;
        return Objects.equals(name, computer.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name.charAt(0), name.charAt(1));
    }

    @Override
    public int compareTo(Computer o) {
        return name.compareTo(o.name);
    }

    @Override
    public String toString() {
        return name;
    }
}
