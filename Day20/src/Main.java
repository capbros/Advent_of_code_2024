import utils.generic.Matrix;
import utils.generic.Point;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class Main {
    private final static int threshold = 100;

    public static void main(String[] args) throws IOException {
        Scanner scanner = null;
        System.out.println("Example or input?");
        try {
            switch ((char) System.in.read()) {
                case '0' -> {
                    scanner = new Scanner(new File("Day20/input_example.txt"));
                }
                case '1' -> {
                    scanner = new Scanner(new File("Day20/input.txt"));
                }
                default -> {
                    System.out.println("Invalid input");
                    return;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error when retrieving the file");
        }
        if (scanner == null) return;
        Matrix<Character> track = new Matrix<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.isEmpty()) break;
            track.addLine(line.chars().mapToObj(x -> (char) x).toArray(Character[]::new));
        }
        track.resize();
        track.print();
        Point start = track.find('S');
        Point end = track.find('E');
        int originalDist = getDist(track, start, end);
//        System.out.println("Current distance: " + getDist(track, start, end));
//        Map<Integer, Integer> savingsToNum = new HashMap<>();
//        for (int i = 1; i < track.ySize()-1; i++) {
//            for (int j = 1; j < track.xSize()-1; j++) {
//                if (track.get(j,i) == '#') {
//                    track.set(j,i,'.');
//                    int saved = originalDist - getDist(track, start, end);
//                    if (saved > 0) {
//                        if (savingsToNum.containsKey(saved)) {
//                            savingsToNum.replace(saved,
//                                    savingsToNum.get(saved)+1);
//                        } else {
//                            savingsToNum.put(saved, 1);
//                        }
//                    }
//                    track.set(j,i,'#');
//                }
//            }
//        }
//        savingsToNum.keySet().stream().sorted().forEach(saved -> {
//            System.out.println(savingsToNum.get(saved) + " cheats save " + saved + " picoseconds");
//        });
//        System.out.println("Part1 : " + savingsToNum.keySet().stream().filter(saved -> saved >= 100)
//                .mapToInt(savingsToNum::get)
//                .sum());
        int tot = 0;
//        for (int i = 1; i < track.ySize()-1; i++) {
//            for (int j = 1; j < track.xSize()-1; j++) {
//                if (track.get(j,i) == '#') {
//                    track.set(j,i,'.');
//                    int saved = originalDist - getDist(track, start, end);
//                    if (saved >= 100) tot++;
//                    track.set(j,i,'#');
//                }
//            }
//        }
        List<Point> validPos = getTrack(track, start, end);
        if (validPos == null) return;
//        Map<Point, Integer> pointToPos = new HashMap<>();
//        for (int i = 0; i < validPos.size(); i++) {
//            pointToPos.put(validPos.get(i), i);
//        }
        tot = getNumCheats(validPos, 2);
        System.out.println("Part1: " + tot);
        tot = getNumCheats(validPos, 20);
        System.out.println("Part1: " + tot);

    }

    private static int getNumCheats(List<Point> validPos, int cheatDuration) {
        int tot = 0;
        for (int i = 0; i < validPos.size()-Main.threshold; i++) {
            Point cur = validPos.get(i);
            for (int j = validPos.size()-1; j > Main.threshold + i; j--) {
                Point comp = validPos.get(j);
                if (absDist(cur, comp) <= cheatDuration && j-i-absDist(cur,comp) >= Main.threshold) {
                    tot++;
                }
            }
        }
        return tot;
    }

    private static int getDist(Matrix<Character> track, Point start, Point end) {
        Queue<Point> toVisit = new ArrayDeque<>();
        Set<Point> visited = new HashSet<>();
        Point pos = new Point(start);
        toVisit.add(pos);
        visited.add(pos);
        int dist = 0;
        while(!toVisit.isEmpty()) {
            int size = toVisit.size();
            while (size > 0) {
                pos = toVisit.poll();
                assert pos != null;
                if (pos.equals(end)) return dist;
                List<Point> validNext = pos.getNext().stream()
                        .filter(p -> track.get(p) != '#')
                        .filter(p -> !visited.contains(p))
                        .toList();
                toVisit.addAll(validNext);
                visited.addAll(validNext);
                size--;
            }
            dist++;
        }

        return -1;
    }

    private static List<Point> getTrack(Matrix<Character> track, Point start, Point end) {
        List<Point> res = new ArrayList<>();
        Queue<Point> toVisit = new ArrayDeque<>();
        Set<Point> visited = new HashSet<>();
        Point pos = new Point(start);
        toVisit.add(pos);
        visited.add(pos);
        while(!toVisit.isEmpty()) {
            pos = toVisit.poll();
            assert pos != null;
            res.add(pos);
            if (pos.equals(end)) return res;
            List<Point> validNext = pos.getNext().stream()
                    .filter(p -> track.get(p) != '#')
                    .filter(p -> !visited.contains(p))
                    .toList();
            toVisit.addAll(validNext);
            visited.addAll(validNext);
        }
        return null;
    }

    private static int absDist(Point p1, Point p2) {
        return Math.abs(p1.getX() - p2.getX()) + Math.abs(p1.getY() - p2.getY());
    }
}