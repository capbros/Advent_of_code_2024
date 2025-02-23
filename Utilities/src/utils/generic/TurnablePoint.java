package utils.generic;

public class TurnablePoint extends Point {
    public void turnLeft() {
        this.set(-this.getY(), this.getX());
    }
    public void turnRight() {
        this.set(this.getX(), -this.getX());
    }

    public TurnablePoint(int x, int y) {
        super(x, y);
    }

    public TurnablePoint() {
    }

    public TurnablePoint(Point other) {
        super(other);
    }
}
