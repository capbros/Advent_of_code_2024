import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {
    static Map<Integer, Operation> codeToOp = new HashMap<>();
    static long A, B, C;
    static int opPtr = 0;
    static {
        codeToOp.put(0, new Adv());
        codeToOp.put(1, new Bxl());
        codeToOp.put(2, new Bst());
        codeToOp.put(3, new Jnz());
        codeToOp.put(4, new Bxc());
        codeToOp.put(5, new Out());
        codeToOp.put(6, new Bdv());
        codeToOp.put(7, new Cdv());
    }
    private static final char[] regNames = {'A','B','C'};
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        for (char regName : regNames) {
            String line = scanner.nextLine();
            Matcher matcher = Pattern.compile("Register " + regName +": (\\d+)").matcher(line);
            if (!matcher.matches()) return;
            int res = Integer.parseInt(matcher.group(1));
            switch (regName) {
                case 'A' -> A = res;
                case 'B' -> B = res;
                case 'C' -> C = res;
            }
        }
        long defaultB = B, defaultC = C;
        if (!scanner.nextLine().isEmpty()) return;
        String line = scanner.nextLine();
        if (!line.matches("Program: (\\d,)*\\d")) return;
        int[] program = Arrays.stream(line.replaceAll("Program: ", "").split(","))
                .mapToInt(Integer::parseInt).toArray();
        List<Integer> res = new ArrayList<>();
        while (opPtr >= 0 && opPtr < program.length) {
            try {
                codeToOp.get(program[opPtr]).run(program[opPtr+1]).ifPresent(res::add);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                return;
            }
        }
        System.out.println("Part 1: " +
                res.stream().map(Object::toString).collect(Collectors.joining(",")));
        System.out.println("Part 2: " +
                part2(0, program, program.length-1));
    }

    public static long part2(long a, int[] program, int index) {
        if (index < 0) return (a >> 3);
        for (long lastBits = 0; lastBits < 8; lastBits++) {
            long aNew = a + lastBits;
            long b = lastBits ^ 2;
            long c = (aNew >> b) % 8;
            b ^= c;
            b ^= 7;
            if (b == (program[index])) {
                long res = part2((aNew << 3), program, index-1);
                if (res != -1) return res;
            }
        }
        return -1;
    }
}
