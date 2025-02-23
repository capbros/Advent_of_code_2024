import utils.generic.Point;

import java.util.*;

public class PointInQueue extends Point implements Comparable<PointInQueue> {
    final static int ROTATIONCOST = 1000;
    long dist = -1;
    Point dir;
    Set<PointInQueue> prev = new HashSet<>();

    public PointInQueue(int x, int y, long dist, Point dir) {
        super(x, y);
        this.dist = dist;
        this.dir = dir;
    }

    public PointInQueue(long dist, Point dir) {
        this.dist = dist;
        this.dir = dir;
    }

    public PointInQueue(Point other, long dist, Point dir) {
        super(other);
        this.dist = dist;
        this.dir = dir;
    }

    @Override
    public int compareTo(PointInQueue o) {
        return Long.compare(dist, o.dist);
    }

    public List<PointInQueue> getNext() {
        List<PointInQueue> res = new ArrayList<>();
        res.add(new PointInQueue(move(dir), dist+1, dir));
        res.add(new PointInQueue(move(rotate(false)), dist+1+ROTATIONCOST, rotate(false)));
        res.add(new PointInQueue(move(rotate(true)), dist+1+ROTATIONCOST, rotate(true)));
        for (PointInQueue p : res) {
            p.prev.add(this);
        }
        return res;
    }

    protected Point rotate(boolean invert) {
        if (invert) {
            return new Point(dir.getY(), -dir.getX());
        }
        return new Point(-dir.getY(), dir.getX());
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof PointInQueue)) return false;
        if (!super.equals(object)) return false;
        PointInQueue that = (PointInQueue) object;
        return Objects.equals(dir, that.dir);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), dir);
    }
}
