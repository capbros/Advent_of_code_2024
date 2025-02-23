import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws NoSuchElementException{
        Scanner scanner = Utils.open_input(23);
        if (scanner == null) return;
        Set<Connection> connections = new HashSet<>();
        Map<Computer, Set<Computer>> computerConnections= new HashMap<>();
        while (scanner.hasNextLine()) {
            Connection current = new Connection(scanner.nextLine());
            connections.add(current);
            for (Computer c : current.participants) {
                if (!computerConnections.containsKey(c))
                    computerConnections.put(c, new HashSet<>());
                computerConnections.get(c).add(current.participants.stream()
                        .filter(x -> !x.equals(c)).findFirst().orElseThrow());
            }
        }
        Set<Connection> connectionsWithT = connections.stream()
                .filter(x -> {
                    for (Computer c : x.participants) {
                        if (c.name.startsWith("t")) return true;
                    }
                    return false;
                }).collect(Collectors.toSet());
        Set<Connection> connectionsToIgnore = new HashSet<>();
        long tot = 0;
        for (Connection connection : connectionsWithT) {
            List<Computer> computers = connection.participants.stream().toList();
            for (Computer c1 : computerConnections.get(computers.getFirst())) {
                if (!c1.equals(computers.getLast()) &&
                        computerConnections.get(computers.getLast()).contains(c1) &&
                        !connectionsToIgnore.contains(new Connection(computers.getFirst(), c1)) &&
                        !connectionsToIgnore.contains(new Connection(computers.getLast(), c1))
                ) tot++;
            }
            connectionsToIgnore.add(connection);
        }
        connectionsToIgnore.clear();
        System.out.println("Part1: " + tot);
        System.out.println("Part2: " + getMaxLan(computerConnections).stream()
                .map(Object::toString).sorted().collect(Collectors.joining(",")));
    }

    private static Set<Computer> getMaxLan(Map<Computer, Set<Computer>> computerConnections) {
        Set<Computer> res = new HashSet<>();
        bronKerbosch(new HashSet<>(), computerConnections.keySet(), new HashSet<>(), computerConnections, res);
        return res;
    }

    private static <T>void bronKerbosch (Set<T> R, Set<T> P, Set<T> X, Map<T, Set<T>> graph, Set<T> maxClique) {
        if (P.isEmpty() && X.isEmpty()) {
            if (R.size() > maxClique.size()) {
                maxClique.clear();
                maxClique.addAll(R);
            }
            return;
        }
        T pivot = P.stream().findFirst().orElse(null);
        if (pivot != null) {
            Set<T> pivotNext = graph.get(pivot);
            for (T v : new HashSet<>(P)) {
                if (!pivotNext.contains(v)) {
                    Set<T> newR = new HashSet<>(R);
                    newR.add(v);
                    Set<T> newP = new HashSet<>(P);
                    newP.retainAll(graph.get(v));
                    Set<T> newX = new HashSet<>(X);
                    newX.retainAll(graph.get(v));
                    bronKerbosch(newR, newP, newX, graph, maxClique);
                    P.remove(v);
                    X.add(v);
                }
            }
        }
    }
}