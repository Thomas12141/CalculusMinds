public class Tangent extends AbstractUnaryArgument {
    public Tangent(Argument child){
        this.child = child;
    }

    @Override
    public double calculate() {
        if(Math.abs(Math.cos(child.calculate()))<0.0001){
            throw new ArithmeticException("Tan is not defined.");
        }
        return Math.tan(child.calculate()); //child in Radians
    }

    @Override
    public String toString() {
        return "tan" + child.toString();
    }
}
