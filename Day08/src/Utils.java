import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class Utils {
    public static Point getAntinode(Point antenna1, Point antenna2) {
        return new Point(antenna2.x + (antenna2.x-antenna1.x),
                antenna2.y + (antenna2.y - antenna1.y));
    }
    public static Set<Point> getAntinode2(Point antenna1, Point antenna2, int width, int height) {
        Set<Point> result = new HashSet<>();
        int xIncrement = antenna2.x - antenna1.x;
        int yIncrement = antenna2.y - antenna1.y;
        Point newAntinode = new Point(antenna2.x, antenna2.y);
        for (int i = 1; newAntinode.inBound(width, height); i++) {
            result.add(newAntinode);
            newAntinode = new Point(newAntinode.x+xIncrement, newAntinode.y+yIncrement);
        }
        return result;
    }
}
