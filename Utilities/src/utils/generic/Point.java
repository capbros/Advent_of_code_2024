package utils.generic;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Point {
    protected int x;
    protected int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point() {
        this.x = 0;
        this.y = 0;
    }

    public Point(Point other) {
        this();
        this.copy(other);
    }

    public void copy(Point other) {
        this.x = other.x;
        this.y = other.y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void set(int x, int y) {
        this.setX(x);
        this.setY(y);
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

    public Point move(int x, int y) {
        return new Point(this.getX()+x, this.getY()+y);
    }
    public Point move(Point incr) {
        return this.move(incr.getX(), incr.getY());
    }

    public void add(int x, int y) {
        this.set(this.getX()+x, this.getY()+y);
    }

    public void add(Point incr) {
        this.add(incr.getX(), incr.getY());
    }

    public List<Point> getNext() {
        List<Point> res = new ArrayList<>();
        res.add(move(1,0));
        res.add(move(-1,0));
        res.add(move(0,1));
        res.add(move(0,-1));
        return res;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public static int dist(Point p1, Point p2) {
        return Math.abs(p1.x - p2.x) + Math.abs(p1.y - p2.y);
    }
}
