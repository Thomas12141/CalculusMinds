public class Logarithm implements Argument {
    private final Argument child;

    public Logarithm(Argument child){
        this.child = child;
    }

    @Override
    public double calculate() {
        return Math.log(child.calculate());
    }

    @Override
    public String toString() {
        return "log" + child.toString();
    }
}
