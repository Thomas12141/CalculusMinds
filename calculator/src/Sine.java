public class Sine extends AbstractUnaryArgument {
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
