import utils.generic.Point;

import java.util.ArrayList;
import java.util.List;

public class BytePoint extends Point {
    final static int edge = 70;
//    final static int edge = 6;
    public BytePoint(int x, int y) {
        super(x, y);
    }

    public BytePoint() {
    }

    public BytePoint(Point other) {
        super(other);
    }

    public boolean inBound() {
        return this.x >= 0 && this.y >= 0 &&
                this.x <= edge && this.y <= edge;
    }

    public boolean isExit() {
        return this.x == edge && this.y == edge;
    }

    public BytePoint[] getNeighbours() {
        return new BytePoint[] {
                new BytePoint(move(-1,0)),
                new BytePoint(move(1,0)),
                new BytePoint(move(0,-1)),
                new BytePoint(move(0,1))
        };
    }

    @Override
    public String toString() {
        return x + "," + y;
    }
}
