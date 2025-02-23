import utils.generic.Point;

import java.util.Optional;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    final static int MAX = 100 * 2;
    final static long INCREMENT = 10000000000000L;
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        long tot = 0;
        long tot2 = 0;
        while (input.hasNextLine()) {
            String line = input.nextLine();
            if (line.isEmpty()) break;
            Button a = Utils.readButton(line);
            line = input.nextLine();
            Button b = Utils.readButton(line);
            line = input.nextLine();
            Point prize = Utils.readPrize(line);
            if (a == null || b == null || prize == null) {
                System.out.println("Input error");
                return;
            }
            line = input.nextLine();
            boolean valid = false;
            int min = MAX * 3 + 1;
            for (int i = 1; i < MAX && min > 0; i++) {
                int incr = 0;
                try {
                    Optional<Integer> xRes = Utils.numPushes(a.getX(), b.getX(), i, prize.getX());
                    Optional<Integer> yRes = Utils.numPushes(a.getY(), b.getY(), i, prize.getY());
                    if (!(xRes.isPresent() && yRes.isPresent() && !xRes.get().equals(yRes.get()))) {
                        if (xRes.isPresent()) {
                            incr = xRes.get();
                        } else if (yRes.isPresent()){
                            incr = yRes.get();
                        }
                        if (incr < 100 && i - incr < 100) {
                            valid = true;
                            incr = incr * 3 + i - incr;
                            if (incr < min) min = incr;
                        }
                    }
                } catch (NotPossibleException _) {
                }
            }
            if (valid) tot += min;


            long prizeX = prize.getX()+INCREMENT;
            long prizeY = prize.getY()+INCREMENT;
            long den = (long )b.getX()*a.getY() - (long)a.getX()*b.getY();
            long num = prizeY*b.getX() - prizeX*b.getY();
            if (num == 0 && den == 0) {
                long k;
                if (3*b.getX() > a.getX()) {
                    k = Utils.firstValid(prizeX, a.getX(), b.getX());
                    if (k != -1)
                        tot2 += 3*k + (prizeX - k*a.getX())/b.getX();
                } else {
                    k = Utils.firstValid(prizeX, b.getX(), a.getX());
                    if (k != -1)
                        tot2 += k + 3*(prizeX - k*b.getX())/a.getX();
                }
            }
            else if (den != 0 && num%den == 0) {
                long k = num/den;
                if (k > 0 && (prizeX - k*a.getX()) % b.getX() == 0
                    && (prizeY - k*a.getY()) % b.getY() == 0)
                    tot2 += 3*k + (prizeX - k*a.getX()) / b.getX();
            }

        }
        System.out.println("Part1: "+tot);
        System.out.println("Part2: "+tot2);

    }
}