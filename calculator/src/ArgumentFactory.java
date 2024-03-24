import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;
import java.util.regex.Pattern;
public class ArgumentFactory {

    private final static String regexDecimal = "^-?\\d*\\.\\d+$";
    private final static String regexInteger = "^-?\\d+$";
    private final static String regexDouble = regexDecimal + "|" + regexInteger;
    private final static Pattern doublePattern = Pattern.compile(regexDouble);
    private final static Pattern variablePattern = Pattern.compile("^[a-zA-Z]$");

    private final static Pattern functionPattern = Pattern.compile("^[a-zA-Z]+'*\\([a-zA-Z](,[a-zA-Z])*\\)$");

    private static ArrayList<ArrayList<Character>> operations;

    private static ArrayList<String> functionVariables;

    /**
     * @author Thomas Fidorin
     * @author Elvira Wied
     * @param argument The string representation of a mathematical argument.
     * @return A tree of instances representing the mathematical argument passed to this method.
     */
    public static Argument buildArgument(String argument) throws OperationNotSupportedException {
        init();
        if(functionPattern.matcher(argument).matches()){
            return Maps.functions.get(argument.substring(0, argument.indexOf("(")));
        } else if(argument.contains("=")){
            return buildArgumentAssignment(argument);
        }else if(doublePattern.matcher(argument).matches()){//A number, it is always a leaf.
            return new DoubleValue(argument);
        }else if(variablePattern.matcher(argument).matches()){
            if(functionVariables==null) return new Variable(argument);
            int position = -1;
            for (int i = 0; i<functionVariables.size(); i++){
                if(functionVariables.get(i).equals(argument)){
                    position = i;
                }
            }
            return new FunctionVariable(argument, position);
        }else {
            return buildArgumentBracketContained(argument);
        }
    }

    /*This Method checks first, if there are addition or multiplication outside any bracket, then it splits on this operation.
     */
    private static Argument buildArgumentBracketContained(String argument) throws OperationNotSupportedException {
        ArrayList<int[]> positions = getBracketsPosition(argument);//Getting the bracket positions in the argument, so we won't split in any bracket. An example (2+3)-> "(2" + "3)", is wrong. But
                                                                   //(2+3) + (3*2) -> "(2+3)" + "(3*2)"
        for (ArrayList<Character> c : operations){
            if(c.getFirst().equals('^')){
                int positionArrayIterator = 0;//The position to check in the positions list.
                for (int i = 0; i < argument.length(); i++) {//Iterating to check any occurrence of the actual operation outside any brackets then splitting on it.
                    if (positionArrayIterator < positions.size() && i == positions.get(positionArrayIterator)[0]) {//checking if it is part of bracket, when yes, skipping it.
                        i = positions.get(positionArrayIterator)[1] + 1;
                        if (i > argument.length()-1) {
                            break;
                        }
                        positionArrayIterator++;
                    }
                    if (argument.charAt(i)=='^'){
                        Argument left = ArgumentFactory.buildArgument(argument.substring(0, i));
                        Argument right = ArgumentFactory.buildArgument(argument.substring(i + 1));
                        return new Power(left, right);
                    }
                }
            }
            int positionArrayIterator = positions.size()-1;//The position to check in the positions list.
            for (int i = argument.length()-1; i >=0 ; i--) {//Iterating to check any occurrence of the actual operation outside any brackets then splitting on it.
                if (positionArrayIterator>=0 && i == positions.get(positionArrayIterator)[1]) {//checking if it is part of bracket, when yes, skipping it.
                    i = positions.get(positionArrayIterator)[0] - 1;
                    if(i < 0){
                        break;
                    }
                    positionArrayIterator--;
                }
                if (c.contains(argument.charAt(i))&&(i==0||!c.contains(argument.charAt(i-1)))) {
                    if(argument.charAt(i)=='+'&&i==0){
                        return buildArgument(argument.substring(1));
                    }
                    if(i==0&&argument.charAt(i)=='-'){
                        return new UnaryMinus(ArgumentFactory.buildArgument(argument.substring(i + 1)));
                    }
                    Argument left = ArgumentFactory.buildArgument(argument.substring(0, i));
                    Argument right = ArgumentFactory.buildArgument(argument.substring(i + 1));
                    switch (argument.charAt(i)){
                        case ('+'): return new Plus(left, right);
                        case ('-'): return new Minus(left, right);
                        case ('*'): return new Multiplication(left, right);
                        case ('/'): return new Devision(left, right);
                    }
                }
            }
        }
        int openParenthesisIndex = argument.indexOf("(");
        if((openParenthesisIndex >= 3 && Character.isLetter(argument.charAt(openParenthesisIndex-1)))) {
            String functionName = argument.substring(argument.indexOf("(")-3, openParenthesisIndex);

            Argument child = ArgumentFactory.buildArgument(argument.substring(openParenthesisIndex+1, argument.indexOf(")")));

            switch (functionName) {
                case ("sin"): return new Sine(child);
                case ("cos"): return new Cosine(child);
                case ("tan"): return new Tangent(child);
                case ("cot"): return new Cotangent(child);
                case ("log"): return new Logarithm(child);
            }
        }
        if(!positions.isEmpty()){
            return new Brackets(ArgumentFactory.buildArgument(argument.substring(argument.indexOf("(")+1 , argument.lastIndexOf(")"))));//No operation to split on.
        }
        if(argument.contains("cos") || argument.contains("sin") || argument.contains("tan") || argument.contains("cot") || argument.contains("log")) {
            Argument child = ArgumentFactory.buildArgument(argument.substring(3));
            switch (argument.substring(0, 3)) {
                case ("sin"):
                    return new Sine(child);
                case ("cos"):
                    return new Cosine(child);
                case ("tan"):
                    return new Tangent(child);
                case ("cot"):
                    return new Cotangent(child);
                case ("log"):
                    return new Logarithm(child);
            }
        }
        throw new IllegalArgumentException("This argument isn't valid");
    }

    /* A method giving back a list with two-dimensional arrays representing the start and end of any outer brackets.
     */
    private static ArrayList<int[]> getBracketsPosition(String argument){
        ArrayList<int[]> result = new ArrayList<>();
        Stack<Integer> stack = new Stack<>();
        int from, to;
        for (int i = 0; i < argument.length(); i++) {
            char c = argument.charAt(i);
            if(c == ')'){
                from = stack.pop();
                to = i;
                result.add(new int[]{from, to});
            } else if (c == '(') {
                stack.add(i);
            }
        }
        for (int i = 0; i < result.size()-1; i++) {
            int[] current = result.get(i);
            int[] next = result.get(i+1);
            while (current[0]>next[0]&&current[1]<next[1]){
                result.remove(i);
                if(result.size()==1){
                    break;
                }
                current = result.get(i);
                next = result.get(i+1);
            }
        }
        return result;
    }

    private static void init(){
        operations = new ArrayList<>();
        operations.add(new ArrayList<>());
        operations.getFirst().add('+');
        operations.getFirst().add('-');
        operations.add(new ArrayList<>());
        operations.get(1).add('*');
        operations.get(1).add('/');
        operations.add(new ArrayList<>());
        operations.get(2).add('^');
    }

    private static Argument buildArgumentAssignment(String assignment) throws OperationNotSupportedException {
        String toSaveTo = assignment.substring(0, assignment.indexOf("="));
        String expression = assignment.substring(assignment.indexOf("=")+1);
        if(functionPattern.matcher(toSaveTo).matches()){
            functionVariables = buildVariableList(toSaveTo.substring(toSaveTo.indexOf("(")));
            Argument expressionArgument = buildArgument(expression);
            String functionName = toSaveTo.substring(0, toSaveTo.indexOf("("));
            FunctionNode functionNode = new FunctionNode(functionName, expressionArgument, functionVariables);
            Maps.functions.put(functionName, functionNode);
            functionVariables = null;
            return expressionArgument;
        }
        else if(variablePattern.matcher(toSaveTo).matches()){
            Argument expressionArgument = buildArgument(expression);
            Maps.variables.put(toSaveTo, expressionArgument.calculate());
            return expressionArgument;
        }
        throw new IllegalArgumentException("This assignment isn't valid.");
    }

    private static ArrayList<String> buildVariableList(String variables){
        variables = variables.substring(1, variables.length()-1);
        String[] variablesArr = variables.split(",");
        return new ArrayList<>(Arrays.asList(variablesArr));
    }
}
