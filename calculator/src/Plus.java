public class Plus extends AbstractBinaryArgument {
    public Plus(Argument left, Argument right){
        this.left = left;
        this.right = right;
    }

    @Override
    public double calculate() {
        return left.calculate() + right.calculate();
    }

    @Override
    public String toString() {
        return left.toString() + "+" + right.toString();
    }

}
