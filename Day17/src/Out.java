import java.util.Optional;

public class Out extends Operation{
    @Override
    public Optional<Integer> execute(int operand) throws IllegalArgumentException {
        return Optional.of((int) (comboOperand(operand) % 8));
    }
}
