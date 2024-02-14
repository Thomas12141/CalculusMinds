import java.util.ArrayList;
import java.util.HashMap;

public class FunctionNode extends Argument{
    private java.util.function.Function<Double[], Double> functionCall;
    private String functionName;
    private Argument functionTree;
    private ArrayList<String> variables;
    public FunctionNode(String functionName, Argument functionTree, ArrayList<String> variables){
        this.functionTree = functionTree.clone();
        this.functionName = functionName;
        this.variables = variables;
    }

    @Override
    public String toString() {
        return functionName + "=" + functionTree.toString();
    }
    @Override
    public double calculate() {
        Double[] values = new Double[variables.size()];
        for (int i = 0; i <variables.size(); i++) {
            values[i] = Maps.variables.get(variables.get(i));
        }
        return calculate(values);
    }
    public double calculate(Double[] variables) {
        return functionCall.apply(variables);
    }


}
