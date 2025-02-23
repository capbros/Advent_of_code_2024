import java.util.List;

public class GatewayXor extends Gateway {
    public GatewayXor(String[] input, String output) {
        super(input, output);
        op = "XOR";
    }

    @Override
    public Wire compute() {
        List<Boolean> inputValues = inputWires.stream().map(w -> w.value).toList();
        return new Wire(output, inputValues.getFirst() ^ inputValues.getLast());
    }

    @Override
    public String toString() {
        return super.toString().replaceFirst("OP", op);
    }
}
