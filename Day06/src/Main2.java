import java.util.*;
import java.util.stream.Collectors;

public class Main2 {
    public static void main(String[] args) {
        Coordinates prev = null;
        List<List<Character>> list = new ArrayList<>();
        Scanner input = new Scanner(System.in);
        while (input.hasNextLine()) {
            String line = input.nextLine();
            if (line.isEmpty()) break;
            list.add(new ArrayList<>(line.chars().mapToObj(c -> (char) c).collect(Collectors.toList())));
            if (list.getLast().contains('^')) {
                prev = new Coordinates(list.getLast().indexOf('^'), list.size()-1);
            }
        }
        Map<Character> map = new Map<>(list);
        assert prev != null;
        map.set(prev, 'X');
        Coordinates start = new Coordinates(prev);
        Coordinates direction = new Coordinates(0, -1);
        Coordinates currentPosition = new Coordinates(prev);
        currentPosition.add(direction);

        //System.out.println(map);
        List<Coordinates> possibleObstacles = new ArrayList<>();

        while (currentPosition.inBounds(map)) {
            if (map.get(currentPosition) == '#') {
                direction.turnRight();
                currentPosition.copy(prev);
                currentPosition.add(direction);
            }
            else {
                if (map.get(currentPosition) != 'X') {
                    map.set(currentPosition, 'X');
                    possibleObstacles.add(new Coordinates(currentPosition));
                }
                prev.copy(currentPosition);
                currentPosition.add(direction);
                //System.out.println(map);
            }
        }

        int tot = 0;

        for (Coordinates obstacle : possibleObstacles) {
            List<List<Set<Coordinates>>> values = new ArrayList<>();
            for (int i = 0; i < map.getYBound(); i++) {
                values.add(new ArrayList<>());
                for (int j = 0; j < map.getXBound(); j++) {
                    values.get(i).add(new HashSet<>());
                }
            }
            Map<Set<Coordinates>> visits = new Map<>(values);
            map.set(obstacle, '#');
            prev.copy(start);
            currentPosition.copy(prev);
            direction.set(0, -1);
            currentPosition.add(direction);
            while (currentPosition.inBounds(map)) {
                if (map.get(currentPosition) == '#') {
                    direction.turnRight();
                    currentPosition.copy(prev);
                    currentPosition.add(direction);
                }
                else {
                    if (visits.get(currentPosition).contains(direction)) {
                        tot++;
                        break;
                    }
                    visits.get(currentPosition).add(direction);
                    prev.copy(currentPosition);
                    currentPosition.add(direction);
                    //System.out.println(map);
                }
            }
            map.set(obstacle, '.');
        }

        System.out.println("Tot = " + tot);
    }
}