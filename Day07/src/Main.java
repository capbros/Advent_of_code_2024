import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        FlexibleInt tot = new FlexibleInt(0);
        while (input.hasNextLine()) {
            String line = input.nextLine();
            if (line.isEmpty()) break;
            long[] test = Arrays.stream(line.split(":? "))
                    .mapToLong(Long::parseLong).toArray();
            if (test.length <= 1) {
                System.out.println("Invalid line");
                return;
            } else {
                if (test.length == 2) {
                    if (test[1] == test[0]) {
                        tot.add(test[0]);
                    }
                }
                else if (Main.testIsPossible(test, test.length-1, test[0])) {
                    tot.add(test[0]);
                }
            }
        }
        System.out.println("Tot = " + tot);
    }

    public static boolean testIsPossible(long[] test, int pos, FlexibleInt tot) {
        FlexibleInt mulRes = new FlexibleInt(tot);
        mulRes.mul(test[pos]);
        FlexibleInt sumRes = new FlexibleInt(tot);
        sumRes.add(test[pos]);
        if (pos == test.length-1) {
            return mulRes.compareTo(test[0]) == 0 || sumRes.compareTo(test[0]) == 0;
        }
        if (mulRes.compareTo(test[0]) < 0) {
            if (Main.testIsPossible(test, pos+1, mulRes))
                return true;
        }
        if (sumRes.compareTo(test[0]) < 0) {
            return (Main.testIsPossible(test, pos+1, sumRes));
        }
        return false;
    }

    public static boolean testIsPossible(long[] test, int pos, long tot) {
        if (pos == 1) return tot == test[1];
        if (tot % test[pos] == 0) {
            if (testIsPossible(test, pos-1, tot / test[pos])) {
                return true;
            }
        }
        if (tot > test[pos]) {
            return testIsPossible(test, pos - 1, tot - test[pos]);
        }
        return false;
    }
}