import java.util.Optional;

public class Bxl extends Operation {
    @Override
    public Optional<Integer> execute(int operand) throws IllegalArgumentException {
        if (invalidOperand(operand)) throw new IllegalArgumentException("Invalid Argument");
        Main.B ^= operand;
        return Optional.empty();
    }
}
