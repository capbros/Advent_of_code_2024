public class GatewayOr extends Gateway{
    public GatewayOr(String[] input, String output) {
        super(input, output);
        op = "OR";
    }

    @Override
    public Wire compute() {
        return new Wire(output, inputWires.stream()
                .map(w -> w.value).reduce(false,
                        (x, y) -> x || y,
                        (x, y) -> x || y));
    }

    @Override
    public String toString() {
        return super.toString().replaceFirst("OP", op);
    }
}
