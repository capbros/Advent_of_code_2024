import java.util.List;

public class Map<T> {
    List<List<T>> values;

    public Map (List<List<T>> values) {
        this.values = values;
    }

    T get(Coordinates point) {
        return this.values.get(point.y).get(point.x);
    }

    void set(Coordinates point, T element) {
        this.values.get(point.y).set(point.x, element);
    }

    int getXBound() {
        try {
            return this.values.getFirst().size();
        } catch (IndexOutOfBoundsException e) {
            return 0;
        }
    }

    int getYBound() {
        return this.values.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (List<T> list : values) {
            for (T el : list) {
                sb.append(el.toString());
            }
            sb.append('\n');
        }
        return sb.toString();
    }
}
