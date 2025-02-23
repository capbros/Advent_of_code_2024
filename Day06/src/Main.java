import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Coordinates start = null;
        List<List<Character>> list = new ArrayList<>();
        Scanner input = new Scanner(System.in);
        while (input.hasNextLine()) {
            String line = input.nextLine();
            if (line.isEmpty()) break;
            list.add(new ArrayList<>(line.chars().mapToObj(c -> (char) c).collect(Collectors.toList())));
            if (list.getLast().contains('^')) {
                start = new Coordinates(list.getLast().indexOf('^'), list.size()-1);
            }
        }
        Map<Character> map = new Map<>(list);
        int tot = 1;
        map.set(start, 'X');
        Coordinates direction = new Coordinates(0, -1);
        Coordinates currentPosition = new Coordinates(start);
        currentPosition.add(direction);

        System.out.println(map);

        while (currentPosition.inBounds(map)) {
            if (map.get(currentPosition) == '#') {
                direction.turnRight();
                currentPosition.copy(start);
                currentPosition.add(direction);
            }
            else {
                if (map.get(currentPosition) != 'X') {
                    map.set(currentPosition, 'X');
                    tot++;
                }
                start.copy(currentPosition);
                currentPosition.add(direction);
                //System.out.println(map);
            }
        }

        System.out.println("Tot = " + tot);
    }
}