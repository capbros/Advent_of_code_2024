import java.util.Optional;

public class Bst extends Operation {
    @Override
    public Optional<Integer> execute(int operand) throws IllegalArgumentException {
        Main.B = comboOperand(operand) % 8;
        return Optional.empty();
    }
}
