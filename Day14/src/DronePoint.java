import utils.generic.Point;

public class DronePoint extends Point {
    private final int width;
    private final int height;
    private final Point move;

    public DronePoint(int x, int y, Point move) {
        super(x, y);
        width = 101;
        height = 103;
        this.move = move;
    }

    public DronePoint(DronePoint other) {
        super(other);
        width = other.width;
        height = other.height;
        move = other.move;
    }

    public DronePoint(int x, int y, int width, int height, Point move) {
        super(x, y);
        this.width = width;
        this.height = height;
        this.move = move;
    }

    @Override
    public String toString() {
        return "DronePoint{" + super.toString() +
                ", width=" + width +
                ", height=" + height +
                ", move=" + move +
                '}';
    }

    @Override
    public void add(Point incr) {
        super.add(incr);
        this.x %= width;
        while (this.x < 0) this.x += width;
        this.y %= height;
        while (this.y < 0) this.y += height;
    }

    public void step() {
        this.add(this.move);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
