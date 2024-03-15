import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;

public class FunctionNode extends Argument{
    private final Function<Double[], Double> functionCall;
    private final String functionName;
    private final Argument functionTree;
    private final ArrayList<String> variables;
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
                case ("Minus") : return a-> left.apply(a) - right.apply(a);
                case ("Division") : return a-> left.apply(a) / right.apply(a);
                case ("Power") : return a-> Math.pow(left.apply(a), right.apply(a));
            }

        }else if(functionTree instanceof AbstractUnaryArgument){
            Function<Double[], Double> child = createFunctionCall(((AbstractUnaryArgument) functionTree).getChild());
            switch (className){
                case ("UnaryMinus") : return a-> -child.apply(a);
                case ("Brackets") : return a-> child.apply(a);
                case ("Cotangent") : return a-> 1.0 / Math.tan(child.apply(a));
                case ("Tangent") : return a-> Math.tan(child.apply(a));
                case ("Sine") : return a-> Math.sin(child.apply(a));
                case ("Logarithm") : return a-> Math.log(child.apply(a));
                case ("Cosine") : return a-> Math.cos(child.apply(a));
            }
        }else{
            switch (functionTree){
                case FunctionNode f : return a-> f.calculate(a);
                case DoubleValue d : return a-> d.calculate();
                case Variable v : return a-> v.calculate();
                case FunctionVariable fv : return a-> a[fv.getPosition()];
                default:
                    throw new OperationNotSupportedException("This class must be implemented.");
            }
        }
        throw new OperationNotSupportedException("This class must be implemented.");
    }
}
