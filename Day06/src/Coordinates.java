import java.util.Objects;

public class Coordinates {
    int x;
    int y;

    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Coordinates (Coordinates other) {
        this(other.x, other.y);
    }

    public void copy (Coordinates other) {
        this.x = other.x;
        this.y = other.y;
    }

    public void add(Coordinates other) {
        this.x += other.x;
        this.y += other.y;
    }

    public void set(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void turnRight() {
        int tmp = this.x;
        this.x = -this.y;
        this.y = tmp;
    }

    public boolean inBounds(Map<?> map) {
        if (x >= 0 && x < map.getXBound() &&
            y >= 0 && y < map.getYBound()) return true;
        return false;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Coordinates that)) return false;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
