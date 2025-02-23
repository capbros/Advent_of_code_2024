import java.util.*;

public class Main {
    public static void main(String[] args) {
        Runtime runtime = Runtime.getRuntime();
        Scanner scanner = new Scanner(System.in);
        Set<String> available = new HashSet<>();
        String inputPattern = "[rgbuw]+";
        String towel = scanner.findInLine(inputPattern);
        while(towel != null) {
            available.add(towel);
            towel = scanner.findInLine(inputPattern);
        }
        long memUsed = runtime.maxMemory() - runtime.freeMemory();
        scanner.skip("\n+");
        int totPossible = 0;
        long totOptions = 0;
        Map<String, Boolean> prevResults = new HashMap<>();
        while (scanner.hasNextLine()) {
            String pattern = scanner.nextLine();
            if (pattern.isEmpty()) break;
            if (isPossible(prevResults, available, pattern)) totPossible++;
            Map<String, Result> prevMatches = new HashMap<>();
            totOptions += numMatches(prevMatches, available, pattern);
        }
        System.out.println("Part1: " + totPossible);
        System.out.println("Part2: " + totOptions);
        memUsed = runtime.maxMemory() - runtime.freeMemory() - memUsed;
        System.out.println("Used memory: " + (memUsed / (1024 * 1024)));
    }

    private static boolean isPossible(Map<String, Boolean> prevResults, Set<String> available, String pattern) {
        if (prevResults.containsKey(pattern)) {
            return prevResults.get(pattern);
        }
        if (pattern.isEmpty()) return true;
        for (String towel : available) {
            if (pattern.startsWith(towel)) {
                if (isPossible(prevResults, available, pattern.substring(towel.length()))) {
                    prevResults.put(pattern, true);
                    return true;
                }
            }
        }
        prevResults.put(pattern, false);
        return false;
    }

    private static long numMatches(Map<String, Result> prevMatches, Set<String> available, String pattern) {
        if (pattern.isEmpty()) return 1;
        for (String towel : available) {
            if (pattern.startsWith(towel)) {
                if (!prevMatches.containsKey(pattern) || prevMatches.get(pattern).isNew(towel)) {
                    long res = numMatches(prevMatches, available, pattern.substring(towel.length()));
                    if (!prevMatches.containsKey(pattern)) {
                        prevMatches.put(pattern, new Result());
                    }
                    prevMatches.get(pattern).add(towel, res);
                }
            }
        }
        if (prevMatches.containsKey(pattern)) return prevMatches.get(pattern).numOptions;
        else return 0;
    }
}
