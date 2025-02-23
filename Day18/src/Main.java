import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    final static int TOTBYTES = 1024;
//    final static int TOTBYTES = 12;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Set<BytePoint> obstacles = new HashSet<>();
        for (int i = 0; i < TOTBYTES; i++) {
            String line = scanner.nextLine();
            Matcher matcher = Pattern
                    .compile("(\\d+),(\\d+)")
                    .matcher(line);
            if (!matcher.matches()) {
                System.out.println("Invalid input");
                return;
            }
            obstacles.add(new BytePoint(
                    Integer.parseInt(matcher.group(1)),
                    Integer.parseInt(matcher.group(2))));
        }
        System.out.println("\nPart1:\n" + exitDist(obstacles));
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.isEmpty()) {
                System.out.println("End reached");
                return;
            }
            Matcher matcher = Pattern
                    .compile("(\\d+),(\\d+)")
                    .matcher(line);
            if (!matcher.matches()) {
                System.out.println("Invalid input");
                return;
            }
            BytePoint last = new BytePoint(
                    Integer.parseInt(matcher.group(1)),
                    Integer.parseInt(matcher.group(2)));
            obstacles.add(last);
            if (exitDist(obstacles) == -1) {
                System.out.println("Part2:\n" + last);
                break;
            }
        }
    }

    private static int exitDist(Set<BytePoint> obstacles) {
        BytePoint pos = new BytePoint(0,0);
        Set<BytePoint> visited = new HashSet<>();
        Queue<BytePoint> toVisit = new ArrayDeque<>();
        visited.add(pos);
        toVisit.add(pos);
        int dist = 0;
        while (!toVisit.isEmpty()) {
            int size = toVisit.size();
            while (size > 0) {
                pos = toVisit.poll();
                size--;
                assert pos != null;
                if (pos.isExit()) return dist;
                List<BytePoint> added = Arrays.stream(pos.getNeighbours())
                        .filter(p -> p.inBound() && !obstacles.contains(p) && !visited.contains(p)).toList();
                toVisit.addAll(added);
                visited.addAll(added);
            }
            dist++;
        }
        return -1;
    }

}
