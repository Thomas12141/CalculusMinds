public class Variable extends Argument {
    private final String variableString;
    public Variable(String variableString){
        this.variableString = variableString;
    }

    @Override
    public double calculate() {
        return Maps.variables.get(variableString); //child in Radians
    }

    @Override
    public String toString() {
        return variableString;
    }
}
