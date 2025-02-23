public class Buffer extends Gateway {

    public Buffer(String[] input, String output) {
        super(input, output);
    }

    public Buffer(String wire) {
        super(new String[0], wire);
    }

    @Override
    public Wire compute() {
        return null;
    }

    @Override
    public String toString() {
        return output;
    }
}
