import java.util.ArrayList;
import java.util.Stack;
import java.util.regex.Pattern;
public class ArgumentFactory {

    private static String regexDecimal = "^-?\\d*\\.\\d+$";
    private static String regexInteger = "^-?\\d+$";
    private static String regexDouble = regexDecimal + "|" + regexInteger;
    private static Pattern doublePattern = Pattern.compile(regexDouble);

    /**
     * @author Thomas Fidorin
     * @author Elvira Wied
     * @param argument The string representation of a mathematical argument.
     * @return A tree of instances representing the mathematical argument passed to this method.
     */
    public static Argument buildArgument(String argument){
        if(argument.contains("(")){//In case of brackets.
            return buildArgumentBracketContained(argument);
        }else if(argument.contains("+")){//The first operation in the tree, it will be the last one to be made by the calculation.
            return buildArgumentBinaryOperation("\\+", argument);
        }else if(argument.contains("-")&&!argument.startsWith("-")){//The second operation in the tree, it will be the one before the last one to be made by the calculation.
            return buildArgumentBinaryOperation("-", argument);
        }else if(argument.contains("*")){//The third operation in the tree, it will be the two before the last one to be made by the calculation.
            return buildArgumentBinaryOperation("\\*", argument);
        }else if(argument.contains("/")){//The fourth operation in the tree, it will be the three before the last one to be made by the calculation.
            return buildArgumentBinaryOperation("/", argument);
        }else if(argument.contains("^")){//The fifth operation in the tree, it will be the four before the last one to be made by the calculation.
            return buildArgumentBinaryOperation("\\^", argument);
        }else if(doublePattern.matcher(argument).matches()){//A number, it is always a leaf.
            return new DoubleValue(argument);
        }
        throw new IllegalArgumentException("This argument insnt valid");
    }
    /*This Method checks first, if there are addition or multiplication outside any bracket, then it splits on this operation.
     */
    private static Argument buildArgumentBracketContained(String argument){
        ArrayList<int[]> positions = getBracketsPosition(argument);//Getting the bracket positions in the argument, so we won't split in any bracket. An example (2+3)-> "(2" + "3)", is wrong. But
                                                                   //(2+3) + (3*2) -> "(2+3)" + "(3*2)"
        int positionArrayIterator = 0;//The position to check in the positions list.
        for (int i = 0; i < argument.length(); i++) {//Iterating to check any occurrence of + outside any brackets then splitting on it.
            if (positionArrayIterator<positions.size() && i == positions.get(positionArrayIterator)[0]) {//checking if it is part of bracket, when yes, skipping it.
                i = positions.get(positionArrayIterator)[1] + 1;
                if(i>=argument.length()){
                    break;
                }
                positionArrayIterator++;
            }
            if(argument.charAt(i)=='+'){
                Argument left = ArgumentFactory.buildArgument(argument.substring(0, i));
                Argument right = ArgumentFactory.buildArgument(argument.substring(i + 1));
                return new Plus(left, right);
            }
        }
        positionArrayIterator = 0;
        for (int i = 0; i < argument.length(); i++) {//Iterating to check any occurrence of * outside any brackets then splitting on it.
            if (positionArrayIterator<positions.size() && i == positions.get(positionArrayIterator)[0]) {//checking if it is part of bracket, when yes, skipping it.
                i = positions.get(positionArrayIterator)[1] + 1;
                if(i>=argument.length()){
                    break;
                }
                positionArrayIterator++;
            }
            if(argument.charAt(i)=='*'){
                Argument left = ArgumentFactory.buildArgument(argument.substring(0, i));
                Argument right = ArgumentFactory.buildArgument(argument.substring(i + 1));
                return new Multiplication(left, right);
            }
        }
        return new Brackets(ArgumentFactory.buildArgument(argument.substring(argument.indexOf("(")+1 , argument.lastIndexOf(")"))));//No operation to split on.
    }
    /* Given an argument and operation, the method splits on this operation the argument, this method is only for binary operation given the argument in between.
     */
    private static Argument buildArgumentBinaryOperation(String operation, String argument){
        String[] temp = argument.split(operation);
        Argument left = ArgumentFactory.buildArgument(temp[0]);
        Argument right = ArgumentFactory.buildArgument(temp[1]);
        Argument result;
        switch (operation){
            case ("\\+"):
                 result = new Plus(left, right);
                for (int i = 2; i < temp.length; i++) {
                    result = new Plus(result, ArgumentFactory.buildArgument(temp[i]));
                }
                return result;
            case ("-"):
                result = new Minus(left, right);
                for (int i = 2; i < temp.length; i++) {
                    result = new Minus(result, ArgumentFactory.buildArgument(temp[i]));
                }
                return result;
            case ("\\*"):
                result = new Multiplication(left, right);
                for (int i = 2; i < temp.length; i++) {
                    result = new Multiplication(result, ArgumentFactory.buildArgument(temp[i]));
                }
                return result;
            case ("/"):
                result = new Devision(left, right);
                for (int i = 2; i < temp.length; i++) {
                    result = new Devision(result, ArgumentFactory.buildArgument(temp[i]));
                }
                return result;
            case ("\\^"):
                result = new Power(ArgumentFactory.buildArgument(temp[temp.length-2]), ArgumentFactory.buildArgument(temp[temp.length-1]));
                for (int i = temp.length-3; i >= 0; i--) {
                    result = new Power(ArgumentFactory.buildArgument(temp[i]), result);
                }
                return result;
        }
        throw new IllegalArgumentException("This argument insnt valid");
    }
    /* A method giving back a list with two-dimensional arrays representing the start and end of any outer brackets.
     */
    private static ArrayList<int[]> getBracketsPosition(String argument){
        ArrayList<int[]> result = new ArrayList<>();
        Stack<Integer> stack = new Stack<>();
        int from = 0;
        int to = 0;
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
}
