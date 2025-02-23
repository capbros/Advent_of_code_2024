import java.util.Optional;

public abstract class Operation {
    protected long comboOperand(int operand) throws IllegalArgumentException{
        switch (operand) {
            case 4 -> {
                return Main.A;
            }
            case 5 -> {
                return Main.B;
            }
            case 6 -> {
                return Main.C;
            }
            case 7 -> {
                throw new IllegalArgumentException("Found reserved \"7\" operand");
            }
            default -> {
                if (operand >= 0 && operand <= 3) {
                    return operand;
                } else {
                    throw new IllegalArgumentException("Invalid argument");
                }
            }
        }
    }

    public boolean invalidOperand(int operand) {
        return operand < 0 || operand >= 8;
    }
    public abstract Optional<Integer> execute(int operand) throws IllegalArgumentException;
    public Optional<Integer> run(int operand) throws IllegalArgumentException {
        Main.opPtr += 2;
        return execute(operand);
    }
}
