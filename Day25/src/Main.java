import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = Utils.open_input(25);
        List<Schematic> locks = new ArrayList<>();
        List<Schematic> keys = new ArrayList<>();
        if (scanner == null) return;
        while (scanner.hasNextLine()) {
            Schematic newScheme = new Schematic();
            String line = scanner.nextLine();
            if (line.matches("#+")) locks.add(newScheme);
            else keys.add(newScheme);
            newScheme.init(lineToHeights(line));
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                if (line.isEmpty()) break;
                newScheme.add(lineToHeights(line));
            }
        }

        int yMax = 1 + locks
                .stream()
                .flatMap(x -> x.heights().stream())
                .max(Integer::compareTo)
                .orElseThrow();
        int xMax = locks.getFirst().heights().size();

        List<Map<Integer, Set<Schematic>>> keysByValue = new ArrayList<>();
        for (int i = 0; i < xMax; i++) {
            int index = i;
            Map<Integer, Set<Schematic>> newMap = new HashMap<>();
            for (int j = 1; j < yMax; j++) {
                int maxHeight = j;
                newMap.put(j, keys.stream()
                        .filter(k -> k.heights().get(index) <= maxHeight)
                        .collect(Collectors.toSet()));
            }
            keysByValue.add(newMap);
        }

        long tot = 0;
        for (Schematic lock : locks) {
            Set<Schematic> matches = new HashSet<>(keysByValue.getFirst().get(yMax - lock.heights().getFirst()));
            for (int i = 1; i < xMax; i++) {
                matches.retainAll(keysByValue.get(i).get(yMax - lock.heights().get(i)));
            }
            tot += matches.size();
        }
        System.out.println("Part1:\n" + tot);
    }

    private static List<Integer> lineToHeights(String line) {
        return line.chars()
                .map(x -> (((char) x) == '.') ? 0 : 1)
                .boxed()
                .toList();
    }
}
