import java.util.HashSet;
import java.util.Set;

public class Result {
    Set<String> options;
    long numOptions;

    public Result() {
        options = new HashSet<>();
        numOptions = 0;
    }

    public void add(String option, long incr) {
        options.add(option);
        numOptions += incr;
    }

    public boolean isNew(String option) {
        return !options.contains(option);
    }
}
