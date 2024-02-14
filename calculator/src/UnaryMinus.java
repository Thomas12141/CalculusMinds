public class UnaryMinus implements Argument{
    private Argument child;

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
