import java.util.Objects;

public class Point {
    int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Point point)) return false;
        return x == point.x && y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public boolean inBound(int xBound, int yBound) {
        return this.x >= 0 && this.x < xBound &&
                this.y >= 0 && this.y < yBound;
    }
}
