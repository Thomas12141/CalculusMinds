public class Power implements Argument{
    private Argument left;
    private Argument right;

    public Power(Argument left, Argument right){
        this.left = left;
        this.right = right;
    }

    @Override
    public double calculate() {
        double base = left.calculate();
        double exponent = right.calculate();
        if(base<0&&exponent%1!=0){
            throw new IllegalArgumentException("The result of " + base + " power " + exponent + " is a complex number.");
        }
        return Math.pow(base, exponent);
    }

    @Override
    public String toString() {
        return left.toString() + "^" + right.toString();
    }
}
