public class Cotangent implements Argument{
    private final Argument child;
    public Cotangent(Argument child){
        this.child = child;
    }

    @Override
    public double calculate() {
        return 1.0 / Math.tan(child.calculate()); //child in Radians
    }

    @Override
    public String toString() {
        return "cot" + child.toString();
    }
}
