public class Sine implements Argument {
    private final Argument child;
    public Sine(Argument child){
        this.child = child;
    }

    @Override
    public double calculate() {
        return Math.sin(child.calculate()); //child in Radians
    }

    @Override
    public String toString() {
        return "sin" + child.toString();
    }
}
