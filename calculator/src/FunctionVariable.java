public class FunctionVariable extends Argument {
    private final String variableString;
    private final int position;
    public FunctionVariable(String variableString, int position){
        this.variableString = variableString;
        if(position<0){
            throw new IllegalArgumentException("Variable position cant be smaller then 0.");
        }
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    @Override
    public double calculate() {
        return Maps.variables.get(variableString);
    }

    @Override
    public String toString() {
        return variableString;
    }
}
