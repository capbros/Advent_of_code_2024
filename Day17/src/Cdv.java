import java.util.Optional;

public class Cdv extends Operation{
    @Override
    public Optional<Integer> execute(int operand) throws IllegalArgumentException {
        Main.C = (Main.A >> comboOperand(operand));
        return Optional.empty();
    }
}
