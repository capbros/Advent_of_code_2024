import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {
    private final static int SWAPPED = 8;

    public static void main(String[] args) {
        Scanner scanner = Utils.open_input(24);
        if (scanner == null) return;
        List<Wire> wires = new ArrayList<>();
        Map<String, List<Gateway>> wireToGateway = new HashMap<>();
        Map<String, Gateway> outToGateway = new HashMap<>();
        Map<Gateway, Set<Gateway>> fanOut = new HashMap<>();
        Map<Gateway, Set<Gateway>> fanIn = new HashMap<>();
        Set<Gateway> gateways = new HashSet<>();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.isEmpty()) break;
            Wire newWire = readWire(line);
            if (newWire == null) {
                System.out.println("Input error");
                return;
            }
            wires.add(newWire);
        }

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.isEmpty()) break;
            Gateway newGateway = readGateway(line);
            if (newGateway == null) {
                System.out.println("Input error");
                return;
            }
            for (String input : newGateway.input) {
                if (!wireToGateway.containsKey(input)) {
                    wireToGateway.put(input, new ArrayList<>());
                }
                wireToGateway.get(input).add(newGateway);
            }
            outToGateway.put(newGateway.output, newGateway);
            gateways.add(newGateway);
        }

//        if (wireToGateway.values().stream().anyMatch(l -> l.size() > 1))
//            System.out.println("Multiple wiring");

        List<Wire> currentWires = new ArrayList<>(wires);
        List<Wire> nextWires = new ArrayList<>();
        List<Wire> zWires = new ArrayList<>(wires.stream()
                .filter(w -> w.name.startsWith("z")).toList());
        while (!currentWires.isEmpty()) {
            for (Wire wire : currentWires) {
                if (wireToGateway.containsKey(wire.name)) {
                    for (Gateway gateway : wireToGateway.get(wire.name)) {
                        gateway.addWire(wire);
                        if (gateway.isReady()) {
                            nextWires.add(gateway.compute());
                        }
                    }
                    wireToGateway.replace(wire.name, wireToGateway.get(wire.name)
                            .stream().filter(g -> !g.isReady()).toList());
                }
            }
            zWires.addAll(nextWires.stream()
                    .filter(w -> w.name.startsWith("z")).toList());
            currentWires.clear();
            currentWires.addAll(nextWires);
            nextWires.clear();
        }

        zWires.sort(Comparator.comparing(w -> w.name));
        long res = 0;
        long increment = 1;
        for (Wire w : zWires) {
            if (w.value)
                res |= increment;
            increment = (increment<<1);
        }
        System.out.println("Part1:\n\t" + res);

        int index = 0;
        String target = String.format("z%02d", index);
        List<String> expressions = new ArrayList<>();
        while (outToGateway.containsKey(target)) {
            Set<String> current = new HashSet<>(outToGateway.get(target).input);
            String expression = outToGateway.get(target).toString();
            while (current.stream().anyMatch(s -> !(s.startsWith("x") || s.startsWith("y")))) {
                Set<String> toReplace = current.stream()
                        .filter(s -> !(s.startsWith("x") || s.startsWith("y")))
                        .collect(Collectors.toSet());
                current.clear();
                for (String s : toReplace) {
                    expression = expression.replaceAll(s, "("+outToGateway.get(s).toString()+")");
                    current.addAll(outToGateway.get(s).input);
                }
            }
            expressions.add(expression);
            target = String.format("z%02d", ++index);
        }

        for (Gateway g : gateways) {
            fanOut.put(g, new HashSet<>());
            for (Gateway g1: gateways) {
                if (g1.input.contains(g.output)) {
                    fanOut.get(g).add(g1);
                    if (!fanIn.containsKey(g1)) fanIn.put(g1, new HashSet<>());
                    fanIn.get(g1).add(g);
                }
            }
            if (fanOut.get(g).isEmpty()) {
                Gateway buf = new Buffer(g.output);
                fanOut.get(g).add(buf);
                fanIn.put(buf, new HashSet<>());
                fanIn.get(buf).add(g);
            }
        }

        Map<String, Gateway> aliases = new HashMap<>();
        for (int i = 0; i < zWires.size(); i++) {
            String num = String.format("%02d", i);
            String[] input = new String[]{"x"+num, "y"+num};
            Gateway gi = findGateway(input, "AND", gateways);
            if (gi != null)
                aliases.put("g"+num, gi);
            Gateway pi = findGateway(input, "XOR", gateways);
            if (pi != null)
                aliases.put("p"+num, pi);
            Gateway fi = findGateway(input, "OR", gateways);
            if (fi != null)
                aliases.put("f"+num, fi);
        }
        aliases.put("c01", aliases.get("g00"));
        String answer = getSwaps(aliases, fanOut, fanIn, new HashSet<>(), 1, zWires.size()-1, 0);
        System.out.println("Part2 :\n" + Objects.requireNonNullElse(answer, "Not found"));
    }

    private static Wire readWire(String line) {
        Matcher matcher = Pattern.compile("([a-z0-9]+): ([01])").matcher(line);
        if (matcher.matches()) {
            String name = matcher.group(1);
            boolean res = matcher.group(2).equals("1");
            return new Wire(name, res);
        }
        return null;
    }

    private static Gateway readGateway(String line) {
        String wireName = "([a-z0-9]+)";
        Matcher matcher = Pattern.compile(wireName +
                " (XOR|AND|OR) " + wireName + " -> " + wireName).matcher(line);
        if (matcher.matches()) {
            String[] inputs = new String[]{matcher.group(1), matcher.group(3)};
            String output = matcher.group(4);
            switch (matcher.group(2)) {
                case "AND" -> {
                    return new GatewayAnd(inputs, output);
                }
                case "OR" -> {
                    return new GatewayOr(inputs, output);
                }
                case "XOR" -> {
                    return new GatewayXor(inputs, output);
                }
            }
        }
        return null;
    }

    private static Gateway findGateway(String[] input, String OP, Set<Gateway> gateways) {
        for (Gateway g : gateways) {
            if (g.input.containsAll(Arrays.stream(input).toList()) && g.op.equals(OP)) return g;
        }
        return null;
    }

    private static String getSwaps(Map<String, Gateway> aliases, Map<Gateway, Set<Gateway>> fanOut,
                                   Map<Gateway, Set<Gateway>> fanIn, Set<Gateway> swapped, int i, int size, int stage) {
        if (i >= size-1) {
            return checkNetwork(aliases, fanOut, swapped, size);
        }
        if (swapped.size() > SWAPPED) return null;
        String num = String.format("%02d", i);
        String c = "c"+num;
        String g = "g"+num;
        String p = "p"+num;
        String f = "f"+num;
        String pc = "pc"+num;
        String gc = "gc"+num;
        String gp = "gp"+num;
        String first, second, out, op;
        switch (stage) {
            case 0 -> {
                op = "AND";
                first = c;
                second = p;
                out = pc;
            }
            case 1 -> {
                op = "OR";
                first = pc;
                second = g;
                out = String.format("c%02d", i+1);
            }
            case 2 -> {
                op = "OR";
                first = g;
                second = c;
                out = gc;
            }
            case 3 -> {
                op = "AND";
                first = gc;
                second = f;
                out = String.format("c%02d", i+1);
            }
            case 4 -> {
                op = "OR";
                first = g;
                second = p;
                out = gp;
            } case 5 -> {
                op = "AND";
                first = gc;
                second = gp;
                out = String.format("c%02d", i+1);
            }
            default -> {
                return null;
            }
        }
        Set<Gateway> possibilities;
        try {
            possibilities = new HashSet<>(fanOut.get(aliases.get(first)));
            possibilities.retainAll(fanOut.get(aliases.get(second)));
        } catch (NullPointerException e) {
            return null;
        }
        possibilities = possibilities.stream().filter(gate -> gate.op.equals(op)).collect(Collectors.toSet());
        for (Gateway partial : possibilities) {
            aliases.put(out, partial);
            String res = getSwaps(new HashMap<>(aliases), fanOut, fanIn, swapped, i + ((stage%2 == 0) ? 0 : 1), size,
                    (stage % 2 == 0) ? stage + 1 : 0);
            if (res != null) return res;
        }
        if (possibilities.isEmpty()) {
            for (Gateway g1 : fanOut.get(aliases.get(first))) {
                for (Gateway g2 : fanOut.get(aliases.get(second))) {
                    if (g1.op.equals(op)) {
                        Gateway tmp = fanIn.get(g1).stream().filter(x -> !x.equals(aliases.get(first))).findAny().get();
                        Map<Gateway, Set<Gateway>> newFanOut = copyFan(fanOut);
                        swapEntries(newFanOut, tmp, aliases.get(second));
                        Map<Gateway, Set<Gateway>> newFanIn = copyFan(fanIn);
                        newFanIn.get(g1).remove(tmp);
                        newFanIn.get(g1).add(aliases.get(second));
                        newFanIn.get(g2).remove(aliases.get(second));
                        newFanIn.get(g2).add(tmp);
                        aliases.put(out, g1);
                        Set<Gateway> newSwapped = new HashSet<>(swapped);
                        newSwapped.add(tmp);
                        newSwapped.add(aliases.get(second));
                        String res = getSwaps(new HashMap<>(aliases), newFanOut,
                                newFanIn, newSwapped, i + ((stage%2==0) ? 0 : 1), size,
                                (stage%2 == 0) ? stage + 1 : 0);
                        if (res != null) return res;
                    }
                    if (g2.op.equals(op)) {
                        Gateway tmp = fanIn.get(g2).stream().filter(x -> !x.equals(aliases.get(second))).findAny().get();
                        Map<Gateway, Set<Gateway>> newFanOut = copyFan(fanOut);
                        swapEntries(newFanOut, tmp, aliases.get(first));
                        Map<Gateway, Set<Gateway>> newFanIn = copyFan(fanIn);
                        newFanIn.get(g2).remove(tmp);
                        newFanIn.get(g2).add(aliases.get(first));
                        newFanIn.get(g1).remove(aliases.get(first));
                        newFanIn.get(g1).add(tmp);
                        aliases.put(out, g2);
                        Set<Gateway> newSwapped = new HashSet<>(swapped);
                        newSwapped.add(tmp);
                        newSwapped.add(aliases.get(first));
                        String res = getSwaps(new HashMap<>(aliases), newFanOut,
                                newFanIn, newSwapped, i + ((stage%2==0) ? 0 : 1), size,
                                (stage % 2 == 0) ? stage + 1 : 0);
                        if (res != null) return res;
                    }
                }
            }
        }
        if (stage == 0) {
            return getSwaps(aliases, fanOut, fanIn, swapped, i, size, 2);
        } else if (stage == 3) {
            return getSwaps(aliases, fanOut, fanIn, swapped, i, size, 4);
        }
        return null;
    }

    private static String checkNetwork(Map<String, Gateway> aliases, Map<Gateway, Set<Gateway>> fanOut, Set<Gateway> swapped, int size) {
        for (int i = 1; i < size; i++) {
            if (swapped.size() > SWAPPED) return null;
            Set<Gateway> possibilities = new HashSet<>(fanOut.get(aliases.get(String.format("p%02d", i))));
            possibilities.retainAll(fanOut.get(aliases.get(String.format("c%02d", i))));
            Optional<Gateway> out = possibilities.stream().filter(g -> g.op.equals("XOR")).findAny();
            if (out.isEmpty()) return null;
            if (fanOut.get(out.get()).size() != 1 || !fanOut.get(out.get()).iterator().next().output.equals(String.format("z%02d", i))) {
                swapped.add(out.get());
            }
        }
        if (swapped.size() != SWAPPED) return null;
        return swapped.stream().map(g -> g.output).sorted(String::compareTo).collect(Collectors.joining(","));
    }

    private static <K, V> void swapEntries(Map<K, V> map, K key1, K key2) {
        V temp = map.get(key1);
        map.put(key1, map.get(key2));
        map.put(key2, temp);
    }

    private static Map<Gateway, Set<Gateway>> copyFan(Map<Gateway, Set<Gateway>> fan) {
        Map<Gateway, Set<Gateway>> res = new HashMap<>(fan);
        res.replaceAll((_, v) -> new HashSet<>(v));
        return res;
    }
}