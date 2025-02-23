import java.util.Optional;

public class Bxc extends Operation {
    @Override
    public Optional<Integer> execute(int operand) throws IllegalArgumentException {
        if (invalidOperand(operand)) throw new IllegalArgumentException("InvalidArgument");
        Main.B ^= Main.C;
        return Optional.empty();
    }
}
