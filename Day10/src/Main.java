import java.util.Arrays;
import java.util.Scanner;

import utils.generic.Matrix;


public class Main {
    public static void main(String[] args) {
        final int MAXSIZE = 100;
        Scanner input = new Scanner(System.in);
        Integer[][] mapArray = null;
        int lineN = 0;
        while (true) {
            String line = input.nextLine();
            if (line.isEmpty()) break;
            if (mapArray == null) mapArray = new Integer[MAXSIZE][line.length()];
            mapArray[lineN] = line.chars().mapToObj(Character::getNumericValue).toArray(Integer[]::new);
            lineN++;
        }
        assert mapArray != null;
        mapArray = Arrays.copyOf(mapArray, lineN);
        Matrix<Integer> map = new Matrix<>(mapArray);
//        map.print();

        long tot = 0;
        for (int i = 0; i < map.ySize(); i++) {
            for (int j = 0; j < map.xSize(); j++) {
                if (map.get(j, i) == 0) {
                    tot += Utils.getScore(map, j, i);
                }
            }
        }
        System.out.println("PART 1");
        System.out.println("Tot = " + tot);

        tot = 0;
        System.out.println("PART 2");
        for (int i = 0; i < map.ySize(); i++) {
            for (int j = 0; j < map.xSize(); j++) {
                if (map.get(j,i) == 0) {
                    tot += Utils.getRating(map, j, i);
                }
            }
        }
        System.out.println("Tot = " + tot);
    }
}
