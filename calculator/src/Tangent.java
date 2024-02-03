public class Tangent implements Argument {
    private final Argument child;
    public Tangent(Argument child){
        this.child = child;
    }

    @Override
    public double calculate() {
        return Math.tan(child.calculate()); //child in Radians
    }

    @Override
    public String toString() {
        return "tan" + child.toString();
    }
}
