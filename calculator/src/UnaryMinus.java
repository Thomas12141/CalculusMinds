public class UnaryMinus extends AbstractUnaryArgument{

    public UnaryMinus(Argument child){
        this.child = child;
    }

    @Override
    public double calculate() {
        return  - child.calculate();
    }

    @Override
    public String toString() {
        return "-" + child.toString();
    }
}
