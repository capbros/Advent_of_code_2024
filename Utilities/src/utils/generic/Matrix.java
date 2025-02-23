package utils.generic;

import java.lang.reflect.Array;
import java.util.Arrays;

public class Matrix<T> {
    private final static int sizeMod = 50;
    protected T[][] values;
    private int last = 0;

    public Matrix() {
        this.values = null;
    }

    public void addLine(T[] line) throws ClassCastException{
        if (this.values == null) {
            this.values = (T[][]) Array.newInstance(line.getClass(), sizeMod);
        }
        if (last >= values.length) {
            this.values = Arrays.copyOf(values, values.length+sizeMod);
        }
        this.values[last] = line;
        last++;
    }

    public void resize() {
        this.values = Arrays.copyOf(values, last);
    }

    public Matrix(T[][] values) {
        this.values = values;
    }

    public void init(T value) {
        for (int i = 0; i < this.ySize(); i++) {
            for (int j = 0; j < this.xSize(); j++) {
                this.set(j,i,value);
            }
        }
    }

    public T get(Point point) {
        return values[point.getY()][point.getX()];
    }

    public T get(int x, int y) {
        return values[y][x];
    }

    public void set(Point point, T value) {
        values[point.getY()][point.getX()] = value;
    }

    public void set(int x, int y, T value) {
        values[y][x] = value;
    }

    public int xSize() {
        try {
            return values[0].length;
        } catch (IndexOutOfBoundsException e) {
            return 0;
        }
    }

    public int ySize() {
        return values.length;
    }

    public void print() {
        for (int i = 0; i < this.ySize(); i++) {
            for (int j = 0; j < this.xSize(); j++) {
                System.out.printf(this.get(j, i).toString());
            }
            System.out.printf("%n");
        }
    }

    public boolean inBound(Point point) {
        return point.getX() >= 0 && point.getX() < this.xSize() &&
                point.getY() >= 0 && point.getY() < this.ySize();
    }

    public Point pointOf(T val) {
        for (int i = 0; i < ySize(); i++) {
            for (int j = 0; j < xSize(); j++) {
                if (get(j,i).equals(val)) {
                    return new Point(j,i);
                }
            }
        }
        return null;
    }

    public Point find(T target) {
        for (int i = 0; i < ySize(); i++) {
            for (int j = 0; j < xSize(); j++) {
                if (get(j,i).equals(target)) return new Point(j,i);
            }
        }
        return null;
    }
}
