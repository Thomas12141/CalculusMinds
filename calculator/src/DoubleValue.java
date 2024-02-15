public class DoubleValue extends Argument{
    private double number;
    public DoubleValue(String number){
        this.number = Double.parseDouble(number);
    }
    @Override
    public double calculate() {
        return number;
    }

    @Override
    public String toString() {
        return Double.toString(number);
    }
}
