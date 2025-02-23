import utils.generic.Point;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Utils {
    public static Button readButton(String line) {
        Matcher matcher = Pattern.compile("Button .: X\\+(\\d+), Y+\\+(\\d+)").matcher(line);
        if (!matcher.matches()) {
            System.out.println("Input error");
            return null;
        }
        return new Button(Integer.parseInt(matcher.group(1)),
                Integer.parseInt(matcher.group(2)));
    }

    public static Point readPrize(String line) {
        Matcher matcher = Pattern.compile("Prize: X=(\\d+), Y=(\\d+)").matcher(line);
        if (matcher.matches()) {
            return new Point(Integer.parseInt(matcher.group(1)),
                    Integer.parseInt(matcher.group(2)));
        }
        return null;
    }

    public static Optional<Integer> numPushes(int n1, int n2, int tot, int target) throws NotPossibleException{
        int left = n1-n2, right = target - tot*n2;
        if (left==0 && right==0) return Optional.empty();
        if (n1-n2 != 0 && right % left == 0) {
            if (right / left < 0) throw new NotPossibleException("");
            return Optional.of(right/left);
        }
        throw new NotPossibleException("");
    }

    public static Optional<Long> numPushes(int n1, int n2, long tot, long target) throws NotPossibleException {
        int left = n1-n2;
        long right = target-tot*n2;
        if (left == 0 && right == 0) return Optional.empty();
        if (n1-n2 != 0 && right % left == 0) {
            long res = right / left;
            if (res < 0 || res > tot) throw new NotPossibleException("");
            return Optional.of(right/left);
        }
        throw new NotPossibleException("");
    }

    public static long gcd(long a, long b) {
        long r = a % b;
        while (r != 0) {
            a = b;
            b = r;
            r = a % b;
        }
        return b;
    }

    public static long lcm(long a, long b) {
        return a*b / gcd(a,b);
    }

    public static long firstValid(long target, long decrement, long mod) {
        long incr = inverse(decrement, mod);
        target %= mod;
        if (target == 0) return 0;
        long k = 1;
        long curr = (target+incr)%mod;
        while (curr != target) {
            if (curr == 0) return k;
            k++;
            curr = (curr+incr)%mod;
        }
        return -1;
    }

    public static PeriodicFunc getCycle(long target, long decrement, long mod) {
        long incr = inverse(decrement, mod);
        long base = 0;
        long current;
        if (target != 0) {
            base++;
            current = (target + incr) % mod;
            while (current != target && current != 0) {
                current = (current+incr)%mod;
                base++;
            }
            if (current != 0) return null;
        }
        long cycle = 1;
        current = incr;
        while (current != 0) {
            current = (current+incr)%mod;
            cycle++;
        }
        return new PeriodicFunc(cycle, base);
    }

    private static long inverse(long decrement, long mod) {
        long res = mod - decrement;
        while (res < 0) res += mod;
        return res;
    }
}
