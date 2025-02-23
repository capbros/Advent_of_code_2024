import utils.generic.Point;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class Main {
    final static Map<Character, Point> numMap = new HashMap<>();
    final static Map<Character, Point> dirMap = new HashMap<>();
    static {
        char val = '1';
        for (int i = 2; i >= 0; i--) {
            for (int j = 0; j < 3; j++) {
                numMap.put(val, new Point(j,i));
                val = (char) (val + 1);
            }
        }
        numMap.put('0', new Point(1,3));
        numMap.put('A', new Point(2,3));
        numMap.put('X', new Point(0,3));
        dirMap.put('A', new Point(2,0));
        dirMap.put('^', new Point(1,0));
        dirMap.put('<', new Point(0,1));
        dirMap.put('v', new Point(1,1));
        dirMap.put('>', new Point(2,1));
        dirMap.put('X', new Point(0,0));
    }
    public static void main(String[] args) throws IOException {
        Scanner scanner = null;
        System.out.println("Example or input?");
        try {
            switch ((char) System.in.read()) {
                case '0' -> scanner = new Scanner(new File("Day21/input_example.txt"));
                case '1' -> scanner = new Scanner(new File("Day21/input.txt"));
                default -> {
                    System.out.println("Invalid input");
                    return;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error when retrieving the file");
        }
        if (scanner == null) return;
        long tot = 0;
        long tot2 = 0;
        Map<Input, Long> prevRes = new HashMap<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.isEmpty()) break;
            tot += bestSeqLen(line) * Long.parseLong(line.replace("A", ""));
            tot2 += bestSeqLen2(line, prevRes) * Long.parseLong(line.replace("A", ""));
        }
        System.out.println("Part1: " + tot);
        System.out.println("Part2: " + tot2);
    }

    public static int bestSeqLen(String target) {
//        Point[] dirPos = new Point[3];
//        for (int i = 0; i < dirPos.length; i++) dirPos[i] = new Point(dirMap.get('A'));
        int tot = 0;
        Point numPos = new Point(numMap.get('A'));
        Point[] pointTarget = target.chars().mapToObj(n -> (char) n).map(numMap::get).toArray(Point[]::new);
        for (Point next : pointTarget) {
            int min = -1;
            for (String line : nextSeq(numPos, next, numMap.get('X'))) {
                int res = getMinLen(1, 0, line,  "");
                if (min == -1 || res < min) min = res;
            }
            tot += min;
            numPos = next;
        }
        return tot;
    }

    public static int getMinLen(int iteration, int index, String source, String line) {
        if (index >= source.length()) {
            index = 0;
            source = line;
            iteration++;
            line = "";
        }
        if (iteration >= 2) {
            int tot = 0;
            Point prev = dirMap.get('A');
            for (Point cur : source.chars().mapToObj(n -> (char) n).map(dirMap::get).toList()) {
                tot += nextSeq(prev, cur, dirMap.get('X')).getFirst().length();
                prev = cur;
            }
            return tot;
        }
        int min = -1;
        Point prev, next;
        if (index == 0) prev = dirMap.get('A');
        else prev = dirMap.get(source.charAt(index-1));
        next = dirMap.get(source.charAt(index));
        for (String toAdd : nextSeq(prev, next, dirMap.get('X'))) {
            int res = getMinLen(iteration, index+1, source, line + toAdd);
            if (min == -1 || res < min) min = res;
        }
        return min;
    }

//    public static long bestSeqLen2(String target, Map<Input, Long> prevRes) {
//        long tot = 0;
//        Point numPos = new Point(numMap.get('A'));
//        Point[] pointTarget = target.chars().mapToObj(n -> (char) n).map(numMap::get).toArray(Point[]::new);
//        for (Point numNext : pointTarget) {
//            String line = bestNextSeq(numPos, numNext, numMap.get('X'));
//            tot += getLen(line, 0, prevRes);
//            numPos = numNext;
//        }
//        return tot;
//    }

    public static long bestSeqLen2(String target, Map<Input, Long> prevRes) {
        //        Point[] dirPos = new Point[3];
        //        for (int i = 0; i < dirPos.length; i++) dirPos[i] = new Point(dirMap.get('A'));
        long tot = 0;
        Point numPos = new Point(numMap.get('A'));
        Point[] pointTarget = target.chars().mapToObj(n -> (char) n).map(numMap::get).toArray(Point[]::new);
        for (Point next : pointTarget) {
            long min = -1;
            for (String line : nextSequence(numPos, next, numMap.get('X'))) {
                long res = getMinLen2(0, line, prevRes);
                if (min == -1 || res < min) min = res;
            }
            tot += min;
            numPos = next;
        }
        return tot;
    }



    public static long getMinLen2(int index, String line, Map<Input, Long> prevRes) {
        if (index >= 25) return line.length();
        Input input = new Input(line, index);
        if (prevRes.containsKey(input)) {
            return prevRes.get(input);
        }
        long tot = 0;
        Point curr = dirMap.get('A');
        for (Point next : line.chars().mapToObj(c -> (char) c).map(dirMap::get).toList()) {
            long min = -1;
            for (String poss : nextSequence(curr, next, dirMap.get('X'))) {
                long res = getMinLen2(index+1, poss, prevRes);
                if (min == -1 || res < min) min = res;
            }
            tot += min;
            curr = next;
        }
        prevRes.put(new Input(line, index), tot);
        return tot;
    }

    public static List<String> nextSeq(Point start, Point end, Point forbidden) {
        List<String> res = new ArrayList<>();
        char xChar, yChar;
        if (end.getX() > start.getX()) xChar = '>';
        else xChar = '<';
        if (end.getY() > start.getY()) yChar = 'v';
        else yChar = '^';
        StringBuilder stringBuilder = new StringBuilder();
        if (!(forbidden.getX() == end.getX() && forbidden.getY() == start.getY())) {
            stringBuilder.append(String.valueOf(xChar).repeat(Math.max(0, Math.abs(end.getX() - start.getX()))));
            stringBuilder.append(String.valueOf(yChar).repeat(Math.max(0, Math.abs(end.getY() - start.getY()))));
            stringBuilder.append('A');
            res.add(stringBuilder.toString());
        }
        stringBuilder = new StringBuilder();
        if (!(forbidden.getY() == end.getY() && forbidden.getX() == start.getX())){
            stringBuilder.append(String.valueOf(yChar).repeat(Math.max(0, Math.abs(end.getY() - start.getY()))));
            stringBuilder.append(String.valueOf(xChar).repeat(Math.max(0, Math.abs(end.getX() - start.getX()))));
            stringBuilder.append('A');
            res.add(stringBuilder.toString());
        }
        return res;
    }

//    public static String bestNextSeq(Point start, Point end, Point forbidden) {
//        char xChar, yChar;
//        if (end.getX() > start.getX()) xChar = '>';
//        else xChar = '<';
//        if (end.getY() > start.getY()) yChar = 'v';
//        else yChar = '^';
//        if (forbidden.getX() == end.getX() && forbidden.getY() == start.getY()) {
//            return String.valueOf(yChar).repeat(Math.max(0, Math.abs(end.getY() - start.getY()))) +
//                    String.valueOf(xChar).repeat(Math.max(0, Math.abs(end.getX() - start.getX()))) +
//                    "A";
//        } else if (forbidden.getY() == end.getY() && forbidden.getX() == start.getX()) {
//            return String.valueOf(xChar).repeat(Math.max(0, Math.abs(end.getX() - start.getX()))) +
//                    String.valueOf(yChar).repeat(Math.max(0, Math.abs(end.getY() - start.getY()))) +
//                    "A";
//        } else if (xChar == '<' || yChar != 'v') {
//            return String.valueOf(xChar).repeat(Math.max(0, Math.abs(end.getX() - start.getX()))) +
//                    String.valueOf(yChar).repeat(Math.max(0, Math.abs(end.getY() - start.getY()))) +
//                    "A";
//        } else {
//            return String.valueOf(yChar).repeat(Math.max(0, Math.abs(end.getY() - start.getY()))) +
//                    String.valueOf(xChar).repeat(Math.max(0, Math.abs(end.getX() - start.getX()))) +
//                    "A";
//        }
//    }

    public static List<String> nextSequence(Point start, Point end, Point forbidden) {
        char xChar, yChar;
        if (end.getX() > start.getX()) xChar = '>';
        else xChar = '<';
        if (end.getY() > start.getY()) yChar = 'v';
        else yChar = '^';
        List<String> res = new ArrayList<>();
        String xFirst = String.valueOf(xChar).repeat(Math.max(0, Math.abs(end.getX() - start.getX()))) +
                String.valueOf(yChar).repeat(Math.max(0, Math.abs(end.getY() - start.getY()))) +
                "A";
        String yFirst = String.valueOf(yChar).repeat(Math.max(0, Math.abs(end.getY() - start.getY()))) +
                String.valueOf(xChar).repeat(Math.max(0, Math.abs(end.getX() - start.getX()))) +
                "A";
        if (!(forbidden.getX() == end.getX() && forbidden.getY() == start.getY())) res.add(xFirst);
        if (!(forbidden.getY() == end.getY() && forbidden.getX() == start.getX())) res.add(yFirst);
        return res;
    }

//    public static long getLen(String line, int index, Map<Input, Long> prevRes) {
//        if (index >= 25) return line.length();
//        Input input = new Input(line, index);
//        if (prevRes.containsKey(input)) {
//            return prevRes.get(input);
//        }
//        long tot = 0;
//        Point curr = dirMap.get('A');
//        for (Point next : line.chars().mapToObj(c -> (char) c).map(dirMap::get).toList()) {
//            tot += getLen(bestNextSeq(curr, next, dirMap.get('X')), index+1, prevRes);
//            curr = next;
//        }
//        prevRes.put(new Input(line, index), tot);
//        return tot;
//    }
}