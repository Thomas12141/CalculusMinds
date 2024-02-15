public abstract class Argument implements Cloneable {
    public double calculate() {
        return 0;
    }

    @Override
    public Argument clone() {
        try {
            return (Argument) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
