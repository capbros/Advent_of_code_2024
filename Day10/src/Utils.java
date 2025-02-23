import utils.generic.Matrix;
import utils.generic.Point;

import java.util.HashMap;
import java.util.Map;

public abstract class Utils {
    public static int getScore(Matrix<Integer> map, int x, int y) {
        Map<Point, Boolean> visits = new HashMap<>();
        for (int i = 0; i < map.ySize(); i++) {
            for (int j = 0; j < map.xSize(); j++) {
                if (map.get(j,i) == 9) {
                    visits.put(new Point(j,i), false);
                }
            }
        }
        return nextStep(map, new Point(x,y), 0, visits);
    }

    private static int nextStep(Matrix<Integer> map, Point pos, int step, Map<Point, Boolean> visits) {
        int tot = 0;
        if (map.inBound(pos) && map.get(pos) == step) {
            if (step == 9) {
                if (!visits.get(pos)) {
                    visits.put(pos, true);
                    return 1;
                } else return 0;
            }
            else {
                step++;
                tot += nextStep(map, pos.move(1,0), step, visits);
                tot += nextStep(map, pos.move(-1,0), step, visits);
                tot += nextStep(map, pos.move(0,1), step, visits);
                tot += nextStep(map, pos.move(0,-1), step, visits);
            }
        }
        return tot;
    }

    public static int getRating(Matrix<Integer> map, int x, int y){
        return nextStepRepeating(map, new Point(x,y), 0);
    }

    public static int nextStepRepeating(Matrix<Integer> map, Point pos, int step) {
        int tot = 0;
        if (map.inBound(pos) && map.get(pos) == step) {
            if (step == 9) {
                return 1;
            }
            else {
                step++;
                tot += nextStepRepeating(map, pos.move(1,0), step);
                tot += nextStepRepeating(map, pos.move(-1,0), step);
                tot += nextStepRepeating(map, pos.move(0,1), step);
                tot += nextStepRepeating(map, pos.move(0,-1), step);
            }
        }
        return tot;
    }
}
