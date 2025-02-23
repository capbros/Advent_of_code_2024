import utils.generic.Matrix;
import utils.generic.Point;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    final static int STEPS = 100;
    public static void main(String[] args) {
        List<DronePoint> droneList = new ArrayList<>();
        Scanner input = new Scanner(System.in);
        while (input.hasNextLine()) {
            String line = input.nextLine();
            if (line.isEmpty()) break;
            Matcher matcher = Pattern.compile("p=(\\d+),(\\d+)\\s+v=(-?\\d+),(-?\\d+)").matcher(line);
            if (matcher.find()) {
                droneList.add(new DronePoint(Integer.parseInt(matcher.group(1)),
                        Integer.parseInt(matcher.group(2)),
//                        11, 7,
                        new Point(Integer.parseInt(matcher.group(3)),
                                Integer.parseInt(matcher.group(4)))));
            } else {
                System.out.println("Input error");
                return;
            }
        }
        if (droneList.isEmpty()) return;
        List<DronePoint> droneList2 = droneList.stream().map(DronePoint::new).toList();
        for (DronePoint drone: droneList) {
            for (int i = 0; i < STEPS; i++) {
                drone.step();
            }
        }
        System.out.println("Part1: "+getSafetyFactor(droneList));

        Matrix<Integer> map = new Matrix<>(new Integer[droneList2.getFirst().getHeight()][droneList2.getFirst().getWidth()]);
        map.init(0);
        for (DronePoint drone : droneList2) {
            map.set(drone, map.get(drone)+1);
        }
        printMap(map);

        int times = 0;
        int minTimes = 0;
        Matrix<Integer> minMap = mapCopy(map);
        int minfactor = getSafetyFactor(droneList2);
        while (true) {
            times++;
            for (DronePoint drone: droneList2) {
                map.set(drone, map.get(drone)-1);
                drone.step();
                map.set(drone, map.get(drone)+1);
            }
            int factor = getSafetyFactor(droneList2);
            if (factor < minfactor) {
                minfactor = factor;
                minMap = mapCopy(map);
                minTimes = times;
            }
            if (times % 1000 == 0) {
                printMap(minMap);
                System.out.println("It: "+minTimes);
                if (!input.nextLine().isEmpty()) break;
            }
        }
    }

    public static void printMap(Matrix<Integer> map) {
        for (int i = 0; i < map.ySize(); i++) {
            for (int j = 0; j < map.xSize(); j++) {
                if (map.get(j,i) > 0) {
                    System.out.printf("%2d", map.get(j,i));
                } else {
                    System.out.print("..");
                }
            }
            System.out.printf("%n");
        }
        for (int i = 0; i < map.xSize(); i++) {
            System.out.print("--");
        }
        System.out.println();
    }

    public static Matrix<Integer> mapCopy(Matrix<Integer> map) {
        Matrix<Integer> res = new Matrix<>(new Integer[map.ySize()][map.xSize()]);
        for (int i = 0; i < res.ySize(); i++) {
            for (int j = 0; j < res.xSize(); j++) {
                res.set(j,i, map.get(j,i));
            }
        }
        return res;
    }

    public static int getSafetyFactor(List<DronePoint> droneList) {
        int[] sums = {0, 0, 0, 0};
        for (DronePoint drone: droneList) {
            int quad = 0;
            if (drone.getX() != drone.getWidth()/2 && drone.getY() != drone.getHeight()/2) {
                if (drone.getX() > drone.getWidth()/2) quad += 2;
                if (drone.getY() > drone.getHeight()/2) quad++;
                sums[quad]++;
            }
        }
        int res = Arrays.stream(sums).reduce(1, (partial, n) -> partial * n);
        return res;
    }
}
