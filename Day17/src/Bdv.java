import java.util.Optional;

public class Bdv extends Operation{
    @Override
    public Optional<Integer> execute(int operand) throws IllegalArgumentException {
        Main.B = (Main.A >> comboOperand(operand));
        return Optional.empty();
    }
}
