public class Logarithm extends AbstractUnaryArgument {

    public Logarithm(Argument child){
        this.child = child;
    }

    @Override
    public double calculate() {
        if(child.calculate()<=0){
            throw new ArithmeticException("This log isn't defined.");
        }
        return Math.log(child.calculate());
    }

    @Override
    public String toString() {
        return "log" + child.toString();
    }
}
