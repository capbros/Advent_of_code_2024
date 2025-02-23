import java.util.*;
import java.util.stream.Collectors;

public abstract class Gateway {
    private final static int inputN = 2;
    Set<String> input;
    String output;
    Set<Wire> inputWires;
    String op = "OP";


    public Gateway(String[] input, String output) {
        this.input = Arrays.stream(input).collect(Collectors.toSet());
        this.output = output;
        inputWires = new HashSet<>();
    }

    public void addWire(Wire wire) {
        inputWires.add(wire);
    }

    public boolean isReady() {
        return inputWires.size() == inputN;
    }

    public abstract Wire compute();

    @Override
    public String toString() {
        return input.stream().collect(Collectors.joining(" "+op+" "));
    }

    public boolean matches (String expr) {
        return this.toString().equals(expr);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Gateway gateway)) return false;
        return Objects.equals(input, gateway.input) && Objects.equals(op, gateway.op);
    }

    @Override
    public int hashCode() {
        return Objects.hash(input, op);
    }
}

