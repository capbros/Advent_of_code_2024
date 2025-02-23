import java.util.HashMap;

public class RockMap extends HashMap<Long, Long> {
    public void add(long rock, long amount) {
        if (this.containsKey(rock)) {
            this.put(rock, this.get(rock) + amount);
        } else {
            this.put(rock, amount);
        }
    }
}
