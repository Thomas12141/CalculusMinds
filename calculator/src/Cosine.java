public class Cosine implements Argument {
    private final Argument child;
    public Cosine(Argument child){
        this.child = child;
    }

    @Override
    public double calculate() {
        return Math.cos(child.calculate()); //child in Radians
    }

    @Override
    public String toString() {
        return "cos" + child.toString();
    }
}
