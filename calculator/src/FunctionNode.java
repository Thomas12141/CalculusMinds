import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;

public class FunctionNode extends Argument{
    private java.util.function.Function<Double[], Double> functionCall;
    private String functionName;
    private Argument functionTree;
    private ArrayList<String> variables;
    public FunctionNode(String functionName, Argument functionTree, ArrayList<String> variables) throws OperationNotSupportedException {
        this.functionTree = functionTree.clone();
        this.functionName = functionName;
        this.variables = variables;
        functionCall = createFunctionCall(this.functionTree);
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

    private static Function<Double[], Double> createFunctionCall(Argument functionTree) throws OperationNotSupportedException {
        String className = functionTree.getClass().getSimpleName();
        if(functionTree instanceof AbstractBinaryArgument){
            Function<Double[], Double> left = createFunctionCall(((AbstractBinaryArgument) functionTree).getLeft());
            Function<Double[], Double> right = createFunctionCall( ((AbstractBinaryArgument) functionTree).getRight());
            switch (className){
                case ("Plus") : return a-> left.apply(a) + right.apply(a);
                case ("Multiplication") : return a-> left.apply(a) * right.apply(a);
            }

        }else if(functionTree instanceof AbstractUnaryArgument){
            Function<Double[], Double> child = createFunctionCall(((AbstractUnaryArgument) functionTree).getChild());
            switch (className){
                case ("Brackets") : return a-> child.apply(a);
            }
        }
        throw new OperationNotSupportedException("This class must be implemented.");
    }
}
