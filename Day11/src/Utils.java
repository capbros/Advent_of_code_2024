import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class Utils {
    final static int MULTIPLIER = 2024;

    public static List<Long> updateRock(long num) {
        List<Long> res = new ArrayList<>();
        if (num == 0L) {
            res.add(1L);
        } else if (Long.toString(num).length() % 2 == 0) {
            String numString = Long.toString(num);
            res.add(Long.parseLong(numString.substring(0, numString.length()/2)));
            res.add(Long.parseLong(numString.substring(numString.length()/2)));
        } else {
            res.add(num * MULTIPLIER);
        }
        return res;
    }

    public static long numRocks(List<Long> rocks, int time) {
        if (time >= Main.BLINKS2) return rocks.size();
        long tot = 0;
        for (long num : rocks) {
            List<Long> newRocks = new ArrayList<>();
            newRocks.add(num);
            for (int i = 0; i < Main.BLINKS; i++) {
                newRocks = newRocks.stream().flatMap(n -> updateRock(n).stream()).toList();
            }
            tot += numRocks(newRocks, time+Main.BLINKS);
        }
        return tot;
    }
}
