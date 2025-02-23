import java.util.Optional;

public class Jnz extends Operation {
    @Override
    public Optional<Integer> execute(int operand) throws IllegalArgumentException {
        if (invalidOperand(operand)) throw new IllegalArgumentException("Invalid operand");
        if (Main.A != 0) Main.opPtr = operand;
        else Main.opPtr += 2;
        return Optional.empty();
    }

    @Override
    public Optional<Integer> run(int operand) throws IllegalArgumentException {
        return execute(operand);
    }
}
