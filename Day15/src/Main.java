import utils.generic.Matrix;
import utils.generic.Point;

import java.io.IOException;
import java.util.*;

public class Main {
    private final static Map<Character, Point> moveMap;
    private final static int YMULTIPLIER = 100;

    static {
        moveMap = new HashMap<>();
        moveMap.put('^', new Point(0, -1));
        moveMap.put('v', new Point(0, 1));
        moveMap.put('>', new Point(1, 0));
        moveMap.put('<', new Point(-1, 0));
    }

    public static void main(String[] args) throws IOException {
        Scanner input = new Scanner(System.in);
        Character[][] charArray = null;
        int index = 0;
        while(input.hasNextLine()) {
            String line = input.nextLine();
            if (line.isEmpty()) break;
            if (charArray == null) charArray = new Character[50][line.length()];
            charArray[index] = line.chars().mapToObj(c -> (char) c).toArray(Character[]::new);
            index++;
        }
        if (charArray == null) {
            System.out.println("Invalid input");
            return;
        }
        charArray = Arrays.copyOf(charArray, index);
        Matrix<Character> map = new Matrix<>(charArray);
        Point start = map.pointOf('@');
        if (start == null) {
            System.out.println("Invalid input");
            return;
        }
        map.set(start, '.');
        Point pos = new Point(start);
        Matrix<Character> bigMap = enlargeMap(map);
        if (bigMap == null) {
            System.out.println("Enlargement error");
            return;
        }
        Point bigPos = new Point(start);
        bigPos.setX(start.getX()*2);
        printMap(bigMap, bigPos);
        while (input.hasNextLine()) {
            String line = input.nextLine();
            if (line.isEmpty()) break;
            for (char c : line.toCharArray()) {
                newMove(map, pos, moveMap.get(c));
                bigMove(bigMap, bigPos, moveMap.get(c));
            }
        }
        printMap(map, pos);
        printMap(bigMap, bigPos);
        System.out.println("Part1: " + sumGPS(map));
        System.out.println("Part2: " + sumGPS(bigMap));

    }

    private static boolean enoughSpace(Matrix<Character> map, Point pos, Point move) {
        pos.add(move);
        while (map.get(pos) != '#') {
            if (map.get(pos) == '.') return true;
            pos.add(move);
        }
        return false;
    }

    private static void newMove(Matrix<Character> map, Point pos, Point move) {
        Point tmp = new Point(pos);
        if (enoughSpace(map, tmp, move)) {
            pos.add(move);
            if (!pos.equals(tmp)) {
                map.set(pos, '.');
                map.set(tmp, 'O');
            }
        }
    }

    private static void printMap(Matrix<Character> map, Point pos) {
        Point tmp = new Point(0,0);
        while (tmp.getY() < map.ySize()) {
            while (tmp.getX() < map.xSize()) {
                if (pos.equals(tmp)) {
                    System.out.print('@');
                } else {
                    System.out.print(map.get(tmp));
                }
                tmp.add(1,0);
            }
            tmp.setX(0);
            tmp.add(0,1);
            System.out.print('\n');
        }
    }

    private static Matrix<Character> duplicateMap(Matrix<Character> map) {
        Character[][] newArray = new Character[map.ySize()][map.xSize()];
        for (int i = 0; i < map.ySize(); i++) {
            for (int j = 0; j < map.xSize(); j++) {
                newArray[i][j] = (char) map.get(j,i);
            }
        }
        return new Matrix<>(newArray);
    }

    private static long sumGPS(Matrix<Character> map) {
        long tot = 0;
        for (int i = 1; i < map.ySize()-1; i++) {
            for (int j = 1; j < map.xSize()-1; j++) {
                if (map.get(j,i).equals('O') || map.get(j,i).equals('[')) {
                    tot += (long) i * YMULTIPLIER + j;
                }
            }
        }
        return tot;
    }

    private static Matrix<Character> enlargeMap(Matrix<Character> map) {
        Matrix<Character> result = new Matrix<>(new Character[map.ySize()][map.xSize()*2]);
        for (int i = 0; i < map.ySize(); i++) {
            for (int j = 0; j < map.xSize(); j++) {
                char[] toAdd = new char[2];
                switch (map.get(j,i)) {
                    case '.' -> toAdd[0] = toAdd[1] = '.';
                    case 'O' -> {
                        toAdd[0] = '[';
                        toAdd[1] = ']';
                    }
                    case '#' -> toAdd[0] = toAdd[1] = '#';
                    case '@' -> {
                        toAdd[0] = '@';
                        toAdd[1] = '.';
                    }
                    default -> {
                        return null;
                    }
                }
                for (int x = 0; x <= 1; x++) {
                    result.set(2*j + x, i, toAdd[x]);
                }
            }
        }
        return result;
    }

    private static List<Point> toMove(Matrix<Character> map, Point pos, Point move) {
        List<Point> res = new ArrayList<>();
        Point tmpPos = new Point(pos);
        tmpPos.add(move);
        if (move.getY() == 0) {
            while (map.get(tmpPos) != '#') {
                if (map.get(tmpPos).equals('.')) return res;
                if (map.get(tmpPos).equals('[')) {
                    res.add(new Point(tmpPos));
                }
                tmpPos.add(move);
            }
            return null;
        }
        Set<Point> presence = new HashSet<>();
        Queue<Point> obstacleQueue = new ArrayDeque<>();
        Point obstacle = new Point(tmpPos);
        switch (map.get(tmpPos)) {
            case '.' -> {
                return res;
            }
            case '#' -> {
                return null;
            }
            case ']' -> {
                obstacle.add(-1,0);
            }
        }
        res.add(obstacle);
        obstacleQueue.add(obstacle);
        presence.add(obstacle);
        while (!obstacleQueue.isEmpty()) {
            obstacle = obstacleQueue.poll();
            presence.remove(obstacle);
            Point[] toCheck = new Point[2];
            toCheck[0] = new Point(obstacle.move(move));
            toCheck[1] = new Point(obstacle.move(move));
            toCheck[1].add(1,0);
            for (Point p : toCheck) {
                if (map.get(p).equals('#')) return null;
                if (!map.get(p).equals('.')) {
                    if (map.get(p).equals(']')) p.add(-1,0);
                    if (!presence.contains(p)) {
                        res.add(p);
                        obstacleQueue.add(p);
                        presence.add(p);
                    }
                }
            }
        }
        return res;
    }

    private static void bigMove(Matrix<Character> map, Point pos, Point move) {
        Point tmp = new Point(pos);
        List<Point> obstacles = toMove(map, pos, move);
        if (obstacles != null) {
            pos.add(move);
            for (Point obst : obstacles) {
                map.set(obst, '.');
                map.set(obst.getX()+1, obst.getY(), '.');
            }
            for (Point obst : obstacles) {
                obst.add(move);
                map.set(obst, '[');
                obst.add(1,0);
                map.set(obst, ']');
            }
        }
    }

}
