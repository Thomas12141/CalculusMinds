public class Devision implements Argument{
    private Argument left;
    private Argument right;

    public Devision(Argument left, Argument right){
        this.left = left;
        this.right = right;
    }

    @Override
    public double calculate() {
        double divisor = right.calculate();
        if(divisor == 0){
            throw new IllegalArgumentException("Cant divide by 0");
        }
        return left.calculate() / divisor;
    }

    @Override
    public String toString() {
        return left.toString() + "/" + right.toString();
    }
}
