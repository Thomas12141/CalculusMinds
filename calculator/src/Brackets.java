public class Brackets extends AbstractUnaryArgument {

    public Brackets(Argument child){
        this.child = child;
    }
    @Override
    public double calculate() {
        return child.calculate();
    }

    @Override
    public String toString() {
        return "(" + child.toString() + ")";
    }
}
