import java.util.Arrays;
import java.util.stream.Collectors;

public class GatewayAnd extends Gateway {
    public GatewayAnd(String[] input, String output) {
        super(input, output);
        op = "AND";
    }

    @Override
    public Wire compute() {
        boolean res = inputWires.stream().map(w -> w.value)
                .reduce(true, (x, y) -> x && y, (x, y) -> x&&y );
        return new Wire(output, res);
    }

    @Override
    public String toString() {
        return super.toString().replaceFirst("OP", op);
    }
}
