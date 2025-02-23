import utils.generic.Matrix;
import utils.generic.Point;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    final static int MAXDIM = 150;
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        Character[][] matValues = null;
        int entry = 0;
        while (input.hasNextLine()) {
            String line = input.nextLine();
            if (line.isEmpty()) break;
            if (matValues == null) matValues = new Character[MAXDIM][line.length()];
            matValues[entry] = line.chars().mapToObj(c -> (char) c).toArray(Character[]::new);
            entry++;
        }
        if (matValues == null) return;
        matValues = Arrays.copyOf(matValues, entry);
        Matrix<Character> map = new Matrix<>(matValues);
        Matrix<Integer> visits = new Matrix<>(new Integer[map.ySize()][map.xSize()]);
        visits.init(0);

        int region = 0;
        long totPrice = 0;
        for (int i = 0; i < map.ySize(); i++) {
            for (int j = 0; j < map.xSize(); j++) {
                if (visits.get(j,i) == 0) {
                    region++;
                    totPrice += Utils.price(map, visits, region, j, i);
                }
            }
        }
        System.out.println("Part1: " + totPrice);

        visits.init(0);
        totPrice = 0;
        for (int i = 0; i < map.ySize(); i++) {
            for (int j = 0; j < map.xSize(); j++) {
                if (visits.get(j,i) == 0) {
                    region++;
                    totPrice += Utils.discountPrice(map, visits, region, j, i);
                }
            }
        }
        System.out.println("Part2: " + totPrice);
    }
}
