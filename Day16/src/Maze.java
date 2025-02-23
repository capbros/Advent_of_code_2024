import utils.generic.Matrix;
import utils.generic.Point;

public class Maze extends Matrix<Character> {
    public Maze(Character[][] values) {
        super(values);
    }

    @Override
    public void print() {
        super.print();
    }

    public Point find(char target) {
        Point pos = new Point();
        while (inBound(pos)) {
            while(inBound(pos)) {
                if (get(pos).equals(target)) return pos;
                pos.add(1,0);
            }
            pos.setX(0);
            pos.add(0,1);
        }
        return null;
    }
}
