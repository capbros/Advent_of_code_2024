import java.util.*;

public class Main {
    public static void main(String[] args) {
        Set<Point> antinodes = new HashSet<>();
        Map<Character, List<Point>> antennas= new HashMap<>();
        Scanner input = new Scanner(System.in);
        int height = 0;
        int width = 0;
        while (input.hasNextLine()) {
            String line = input.nextLine();
            if (line.isEmpty()) break;
            for (int i = 0; i < line.length(); i++) {
                if (line.charAt(i) != '.') {
                    if (!antennas.containsKey(line.charAt(i))) {
                        antennas.put(line.charAt(i), new ArrayList<>());
                    }
                    antennas.get(line.charAt(i)).add(new Point(i, height));
                }
            }
            height++;
            if (width == 0) width = line.length();
        }
        for (char c : antennas.keySet()) {
            List<Point> antennaList = antennas.get(c);
            for (int i = 0; i < antennaList.size()-1; i++) {
                for (int j = i+1; j < antennaList.size(); j++) {
                    Point newAntinode = Utils.getAntinode(antennaList.get(i),antennaList.get(j));
                    if (newAntinode.inBound(width, height))
                        antinodes.add(newAntinode);
                    newAntinode = Utils.getAntinode(antennaList.get(j),antennaList.get(i));
                    if (newAntinode.inBound(width, height))
                        antinodes.add(newAntinode);
                }
            }

        }
        System.out.println("Tot = " + antinodes.size());
    }
}
