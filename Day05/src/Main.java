import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        List<Rule> ruleList = new ArrayList<>();
        String line;
        while (input.hasNextLine()) {
            line = input.nextLine();
            if (line.isEmpty() || !(line.matches("\\d+\\|\\d+"))) break;
            String[] ruleNumbers = line.split("[|$]");
            ruleList.add(new Rule(Integer.parseInt(ruleNumbers[0]),
                    Integer.parseInt(ruleNumbers[1])));
        }
        if (ruleList.isEmpty()) return;
        Map<Integer, List<Integer>> precedenceMap = new HashMap<>();
        for (Rule rule : ruleList) {
            if (!precedenceMap.containsKey(rule.first)) {
                precedenceMap.put(rule.first, new ArrayList<>());
            }
            precedenceMap.get(rule.first).add(rule.second);
        }
        int tot = 0;
        while (input.hasNextLine()) {
            line = input.nextLine();
            if (line.isEmpty() || !line.matches("\\d+(,\\d+)*")) break;
            Map<Integer, Integer> pageToPosition = new HashMap<>();
            String[] pagesArray = line.split(",");
            for (int i = 0; i < pagesArray.length; i++) {
                pageToPosition.put(Integer.parseInt(pagesArray[i]), i);
            }
            if (!Main.checkPrecedence(precedenceMap, pageToPosition)) {
                tot += Main.newMiddle(precedenceMap, pageToPosition);
            }
        }
        System.out.printf("Tot = %d%n", tot);
    }

    public static boolean checkPrecedence(Map<Integer, List<Integer>> precedenceMap, Map<Integer, Integer>pageToPosition) {
        for (int page : pageToPosition.keySet()) {
            if (precedenceMap.containsKey(page)) {
                for (int nextPage : precedenceMap.get(page)) {
                    if (pageToPosition.containsKey(nextPage) && pageToPosition.get(page) > pageToPosition.get(nextPage)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static int newMiddle(Map<Integer, List<Integer>> precedenceMap, Map<Integer, Integer> pageToPosition) {
        Set<Integer> pages = pageToPosition.keySet();
        Map<Integer, Set<Integer>> relevantPrecedences = new HashMap<>();
        for (int page : pages) {
            relevantPrecedences.put(page, new HashSet<>());
            if (precedenceMap.containsKey(page)) {
                relevantPrecedences.get(page).addAll(precedenceMap.get(page).stream().filter(pages::contains).collect(Collectors.toSet()));
            }
        }
        for (int page : pages) {
            if (relevantPrecedences.containsKey(page)) {
                Deque<Integer> toCheck = new ArrayDeque<>(relevantPrecedences.get(page));
                while (!toCheck.isEmpty()) {
                    int current = toCheck.removeFirst();
                    if (relevantPrecedences.containsKey(current)) {
                        toCheck.addAll(relevantPrecedences.get(current).stream()
                                .filter(p -> !relevantPrecedences.get(page).contains(p)).toList());
                        relevantPrecedences.get(page).addAll(relevantPrecedences.get(current));
                    }
                }
            }
        }
        List<Integer> newUpdate = new ArrayList<>(pages);
        newUpdate.sort((p1, p2) -> {
            if (relevantPrecedences.containsKey(p1) && relevantPrecedences.get(p1).contains(p2)) return -1;
            if (relevantPrecedences.containsKey(p2) && relevantPrecedences.get(p2).contains(p1)) return 1;
            return 0;
        });
        return newUpdate.get(newUpdate.size()/2);
    }
}
