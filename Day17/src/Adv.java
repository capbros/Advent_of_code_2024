import java.util.Optional;

public class Adv extends Operation {
    @Override
    public Optional<Integer> execute(int operand) throws IllegalArgumentException {
        Main.A = (Main.A >> comboOperand(operand));
        return Optional.empty();
    }
}
