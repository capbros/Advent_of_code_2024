import utils.datastructures.MinHeap;
import utils.generic.Matrix;
import utils.generic.Point;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Character[][] mapValues = null;
        int height = 0;
        while (input.hasNextLine()) {
            String line = input.nextLine();
            if (line.isEmpty()) break;
            if (mapValues == null) mapValues = new Character[150][line.length()];
            mapValues[height] = line.chars().mapToObj(c -> (char) c).toArray(Character[]::new);
            height++;
        }
        if (mapValues == null) {
            System.out.println("Invalid input");
            return;
        }
        mapValues = Arrays.copyOf(mapValues, height);
        Maze maze = new Maze(mapValues);
        maze.print();

        System.out.println("Part1: " + minDist(maze));
        System.out.println("Part2: " + numSeats(maze));
    }

    public static long minDist(Maze maze) {
        Point start = maze.find('S');
        Point end = maze.find('E');
        MinHeap<PointInQueue> pointQueue = new MinHeap<>();
        Map<Point, Long> reachablePoints = new HashMap<>();
        Set<Point> addedPoints = new HashSet<>();
        pointQueue.insert(new PointInQueue(start,0, new Point(1,0)));
        reachablePoints.put(pointQueue.first(), pointQueue.first().dist);
        while(!pointQueue.isEmpty()) {
            PointInQueue lastAdded = pointQueue.pop();
            reachablePoints.remove(lastAdded);
            addedPoints.add(lastAdded);
            if (end.equals(lastAdded)) {
                return lastAdded.dist;
            }
            for (PointInQueue p : lastAdded.getNext()) {
                if (!addedPoints.contains(p) && !maze.get(p).equals('#')) {
                    if (reachablePoints.containsKey(p)) {
                        if (p.dist < reachablePoints.get(p)) {
                            reachablePoints.replace(p, p.dist);
                            pointQueue.update(p);
                        }
                    } else {
                        pointQueue.insert(p);
                        reachablePoints.put(p, p.dist);
                    }
                }
            }
        }
        return -1;
    }

    public static long numSeats(Maze maze) {
        Map<PointInQueue, PointInQueue> map = getAddedSet(maze);
        PointInQueue end = map.values().stream().filter(x -> maze.find('E').equals(x)).findAny().get();
        List<PointInQueue> toCheck = new ArrayList<>();
        toCheck.add(end);
        Set<Point> seenSet = new HashSet<>();
        seenSet.add(new Point(end));
        while (!toCheck.isEmpty()) {
            PointInQueue last = toCheck.removeLast();
            toCheck.addAll(map.get(last).prev.stream().filter(x -> !seenSet.contains((Point) x)).toList());
            seenSet.addAll(map.get(last).prev.stream().map(Point::new).toList());
        }
        for (Point p : seenSet) {
            maze.set(p, 'O');
        }
        maze.print();
        return seenSet.size();
    }

    public static Map<PointInQueue, PointInQueue> getAddedSet(Maze maze) {
        Point start = maze.find('S');
        Point end = maze.find('E');
        MinHeap<PointInQueue> pointQueue = new MinHeap<>();
        Map<PointInQueue, PointInQueue> reachablePoints = new HashMap<>();
        Map<PointInQueue, PointInQueue> res = new HashMap<>();
        pointQueue.insert(new PointInQueue(start,0, new Point(1,0)));
        reachablePoints.put(pointQueue.first(), pointQueue.first());
        while(!pointQueue.isEmpty()) {
            PointInQueue lastAdded = pointQueue.pop();
            reachablePoints.remove(lastAdded);
            res.put(lastAdded, lastAdded);
            if (end.equals(lastAdded)) {
                return res;
            }
            for (PointInQueue p : lastAdded.getNext()) {
                if (!res.containsKey(p) && !maze.get(p).equals('#')) {
                    if (reachablePoints.containsKey(p)) {
                        if (p.dist < reachablePoints.get(p).dist) {
                            reachablePoints.replace(p, p);
                            pointQueue.update(p);
                        } else if (p.dist == reachablePoints.get(p).dist) {
                            reachablePoints.get(p).prev.addAll(p.prev);
                        }
                    } else {
                        pointQueue.insert(p);
                        reachablePoints.put(p, p);
                    }
                } else if (res.containsKey(p) && res.get(p).dist == p.dist) {
                    res.get(p).prev.addAll(p.prev);
                }
            }
        }
        return res;
    }
}
