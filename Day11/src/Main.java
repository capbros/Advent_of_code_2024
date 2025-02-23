import java.util.*;

public class Main {
    final static int BLINKS = 25;
    final static int BLINKS2 = 75;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        List<Long> rocks = new ArrayList<>(
                Arrays.stream(input.nextLine().split(" "))
                        .map(Long::parseLong).toList()
        );
        List<Long> newRocks = new ArrayList<>();
        List<Long> prevRocks = new ArrayList<>(rocks);
        for (int blinks = 0; blinks < BLINKS; blinks++) {
            for (long num : prevRocks) {
                newRocks.addAll(Utils.updateRock(num));
            }
            prevRocks.clear();
            List<Long> tmp = newRocks;
            newRocks = prevRocks;
            prevRocks = tmp;
        }
        System.out.println("PART 1\n" +
                "\tAnswer = "+prevRocks.size());

        RockMap rockMap = new RockMap();
        for (long rock : rocks) {
            rockMap.put(rock, 1L);
        }
        RockMap rockMapUpdate = new RockMap();
        for (int blinks = 0; blinks < BLINKS2; blinks++) {
            for (long rock : rockMap.keySet()) {
                for (long newRock : Utils.updateRock(rock)) {
                    rockMapUpdate.add(newRock, rockMap.get(rock));
                }
            }
            RockMap tmp = rockMap;
            rockMap.clear();
            rockMap = rockMapUpdate;
            rockMapUpdate = tmp;
        }
//        long tot = Utils.numRocks(rocks, 0);
        long tot = rockMap.keySet().stream().mapToLong(rockMap::get).sum();
        System.out.println("PART 2\n" +
                "\tAnswer = "+ tot);
    }
}
