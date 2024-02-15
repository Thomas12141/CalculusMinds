public class Cotangent extends AbstractUnaryArgument{
    public Cotangent(Argument child){
        this.child = child;
    }

    @Override
    public double calculate() {
        if(Math.abs(Math.sin(child.calculate()))<0.0001){
            throw new ArithmeticException("Tan is not defined.");
        }
        return 1.0 / Math.tan(child.calculate()); //child in Radians
    }

    @Override
    public String toString() {
        return "cot" + child.toString();
    }
}
