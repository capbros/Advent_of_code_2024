import java.util.*;

public class Main {
    final static int numIterations = 2000;
    final static long pruneMask = ~(-1 << 24);
    final static List<Integer> bestSequence = Arrays.stream(new Integer[]{-2, 1, -1, 3}).toList();
    public static void main(String[] args) {
        Scanner scanner = Utils.open_input(22);
        if (scanner == null) return;
        List<List<Integer>> increments = new ArrayList<>();
        List<List<Integer>> prices = new ArrayList<>();
        long tot = 0;
        while (scanner.hasNextLong()) {
            long current = scanner.nextLong();
            increments.add(new ArrayList<>());
            prices.add(new ArrayList<>());
            for (int i = 0; i < numIterations; i++) {
                long next = nextSecretNumber(current);
                increments.getLast().add((int) ((next)%10-(current)%10));
                prices.getLast().add((int) (next%10));
                current = next;
            }
            tot += current;
        }
        System.out.println("Part1: " + tot);
        if (increments.size() != prices.size()) return;
//        Sequence sequence = new Sequence();
        int max = 0;
        Map<Sequence, Integer> sequenceToTot = new HashMap<>();
        for (int x = 0; x < increments.size(); x++) {
            List<Integer> line = increments.get(x);
            Set<Sequence> sequences = new HashSet<>();
            for (int i = 0; i < line.size()-3; i++) {
                Sequence sequence = new Sequence(line.subList(i, i+4));
                if (!sequences.contains(sequence)) {
                    if (sequenceToTot.containsKey(sequence)) {
                        sequenceToTot.replace(sequence, sequenceToTot.get(sequence) + prices.get(x).get(i+3));
                    } else {
                        sequenceToTot.put(sequence, prices.get(x).get(i+3));
                    }
                    sequences.add(sequence);
                }
            }
        }
        for (Sequence sequence : sequenceToTot.keySet()) {
            if (sequenceToTot.get(sequence) > max) max = sequenceToTot.get(sequence);
        }
        System.out.println("Part2: " + max);
    }

    public static long nextSecretNumber(long current) {
        long partial = (current << 6);
        current ^= partial;
        current &= pruneMask;
        partial = (current >> 5);
        current ^= partial;
        current &= pruneMask;
        partial = (current << 11);
        current ^= partial;
        current &= pruneMask;
        return current;
    }

    public static boolean match(int [] currentSequence, int[] sequence) {
        for (int i = 0; i < currentSequence.length; i++) {
            if (currentSequence[i] != sequence[i]) return false;
        }
        return true;
    }

//    public static int firstMatch(List<Integer> mainList, List<Integer> subList) {
//        for (int i = 0; i < )
//    }
}