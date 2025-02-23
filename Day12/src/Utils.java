import utils.generic.Matrix;
import utils.generic.Point;
import utils.generic.TurnablePoint;

import java.util.*;

public abstract class Utils {
    private final static Point[] NEIGHBOURS = {new Point(0,1), new Point(0,-1), new Point(1, 0), new Point(-1, 0)};
    private final static Point[] DIAGONALS = {new Point(1,1), new Point(-1,1), new Point(1,-1), new Point(-1, -1)};
    public static long price(Matrix<Character> map, Matrix<Integer> visits, int region, int x, int y) {
        Queue<Point> queue = new ArrayDeque<>();
        visits.set(x, y, region);
        queue.offer(new Point(x,y));
        char name = map.get(x,y);
        long area = 1;
        long perimeter = 0;
        while (!queue.isEmpty()) {
            Point pos = queue.poll();
            for (Point incr : NEIGHBOURS) {
                Point newPos = pos.move(incr);
                if (map.inBound(newPos) && map.get(newPos) == name) {
                    if (visits.get(newPos) == 0) {
                        visits.set(newPos, region);
                        queue.offer(newPos);
                        area++;
                    }
                } else {
                    perimeter++;
                }
            }
        }
        return area*perimeter;
    }
    public static long discountPrice(Matrix<Character> map, Matrix<Integer> visits, int region, int x, int y) {
        Queue<Point> queue = new ArrayDeque<>();
        visits.set(x, y, region);
        queue.offer(new Point(x,y));
        char name = map.get(x,y);
        long area = 1;
        long vertices = 0;
        while (!queue.isEmpty()) {
            Point pos = queue.poll();
            Boolean[] availableDirs = new Boolean[NEIGHBOURS.length];
            for (int i = 0; i < NEIGHBOURS.length; i++) {
                Point incr = NEIGHBOURS[i];
                Point newPos = pos.move(incr);
                if (map.inBound(newPos) && map.get(newPos) == name) {
                    if (visits.get(newPos) == 0) {
                        visits.set(newPos, region);
                        queue.offer(newPos);
                        area++;
                    }
                    availableDirs[i] = true;
                }
                else availableDirs[i] = false;
            }
            for (int i = 0; i < NEIGHBOURS.length/2; i++) {
                for (int j = NEIGHBOURS.length/2; j < NEIGHBOURS.length; j++) {
                    if (availableDirs[i] && availableDirs[j]) {
                        Point newPos = pos.move(NEIGHBOURS[i]);
                        newPos.add(NEIGHBOURS[j]);
                        if (map.get(newPos) != name) vertices++;
                    }
                }
            }
            int dirs = (int) Arrays.stream(availableDirs).filter(p -> p).count();
            if (dirs <= 2) {
                if (dirs == 0) vertices += 4;
                else if (dirs == 1) vertices += 2;
                else if (!(availableDirs[0] && availableDirs[1])
                    && !(availableDirs[2] && availableDirs[3])) {
                    vertices++;
                }
            }
        }
        return area * vertices;
    }

}
