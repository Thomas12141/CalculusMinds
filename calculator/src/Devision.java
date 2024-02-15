public class Devision extends AbstractBinaryArgument{

    public Devision(Argument left, Argument right){
        this.left = left;
        this.right = right;
    }

    @Override
    public double calculate() {
        double divisor = right.calculate();
        if(divisor == 0){
            throw new ArithmeticException("Cant divide by 0");
        }
        return left.calculate() / divisor;
    }

    @Override
    public String toString() {
        return left.toString() + "/" + right.toString();
    }
}
