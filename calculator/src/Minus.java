public class Minus implements Argument{
    private Argument left;
    private Argument right;

    public Minus(Argument left, Argument right){
        this.left = left;
        this.right = right;
    }

    @Override
    public double calculate() {
        return left.calculate() - right.calculate();
    }

    @Override
    public String toString() {
        return left.toString() + "-" + right.toString();
    }
}
