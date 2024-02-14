import java.util.ArrayList;
import java.util.Stack;
import java.util.regex.Pattern;

public class ArgumentFactory {
    private static String regexDecimal = "^-?\\d*\\.\\d+$";
    private static String regexInteger = "^-?\\d+$";
    private static String regexDouble = regexDecimal + "|" + regexInteger;
    private static Pattern doublePattern = Pattern.compile(regexDouble);
    private static ArrayList<ArrayList<Character>> operators;

    /**
     * @author Thomas Fidorin
     * @author Elvira Wied
     * @param argument The string representation of a mathematical argument.
     * @return A tree of instances representing the mathematical argument passed to this method.
     */
    public static Argument buildArgument(String argument){
        initOperators();

        if(!doublePattern.matcher(argument).matches()){ //In case of brackets.
            return buildArgumentBracketContained(argument);
        }else if(doublePattern.matcher(argument).matches()){ //A number, it is always a leaf.
            return new DoubleValue(argument);
        }
        throw new IllegalArgumentException("This argument isn't valid");
    }

    /**
     * Processes argument depending on its mathematical operators.
     * Checks if there are addition or multiplication outside any bracket, then it splits on this operation.
     */
    private static Argument buildArgumentBracketContained(String argument){
        ArrayList<int[]> positions = getBracketsPosition(argument);

        //Getting the bracket positions in the argument, so we won't split in any bracket. An example (2+3)-> "(2" + "3)", is wrong. But (2+3) + (3*2) -> "(2+3)" + "(3*2)"
        for (ArrayList<Character> operatorList : operators){
            if(operatorList.getFirst().equals('^')){
                //process exponentiation
                return processExponentiation(argument, positions);
            } else {
                //process addition, subtraction, multiplication and division
                return processArithmeticOperators(argument, positions, operatorList);
            }
        }
        //process trigonometric functions and logarithms
        int openParenthesisIndex = argument.indexOf("(");
        if((openParenthesisIndex >= 3 && Character.isLetter(argument.charAt(openParenthesisIndex-1)))) {
            return processTrigonAndLog(argument, openParenthesisIndex);
        }
        //No operation to split on.
        if(!positions.isEmpty()){
            return new Brackets(ArgumentFactory.buildArgument(argument.substring(argument.indexOf("(")+1 , argument.lastIndexOf(")"))));
        }
        /*if(argument.contains("cos") || argument.contains("sin") || argument.contains("tan") || argument.contains("cot") || argument.contains("log")) {
            Argument child = ArgumentFactory.buildArgument(argument.substring(3));

            switch (argument.substring(0, 3)) {
                case ("sin"): return new Sine(child);
                case ("cos"): return new Cosine(child);
                case ("tan"): return new Tangent(child);
                case ("cot"): return new Cotangent(child);
                case ("log"): return new Logarithm(child);
            }
        }*/
        throw new IllegalArgumentException("This argument isn't valid");
    }

    /**
     * Processes any exponentiation operators contained in the argument
     */
    private static Argument processExponentiation(String argument, ArrayList<int[]> positions){
        int positionArrayIterator = 0; //The position to check in the positions list.

        for (int i = 0; i < argument.length(); i++) {
            //checking if operator is part of bracket, when yes, skipping it.
            if (positionArrayIterator < positions.size() && i == positions.get(positionArrayIterator)[0]) {
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
        return null;
    }

    /**
     * Processes any trigonometric functions and logarithms contained in the argument
     */
    private static Argument processTrigonAndLog(String argument, int openParenthesisIndex){
        String functionName = argument.substring(argument.indexOf("(")-3, openParenthesisIndex);
        Argument child = ArgumentFactory.buildArgument(argument.substring(openParenthesisIndex+1, argument.indexOf(")")));

        switch (functionName) {
            case ("sin"): return new Sine(child);
            case ("cos"): return new Cosine(child);
            case ("tan"): return new Tangent(child);
            case ("cot"): return new Cotangent(child);
            case ("log"): return new Logarithm(child);
        }
        return null;
    }

    /**
     * Processes any addition-, subtraction-, multiplication- or division operators contained in the argument
     */
    private static Argument processArithmeticOperators(String argument, ArrayList<int[]> positions, ArrayList<Character> operatorList){
        int positionArrayIterator = positions.size()-1; //The position to check in the positions list.

        for (int i = argument.length()-1; i >=0 ; i--) { //Iterating to check any occurrence of the actual operation outside any brackets then splitting on it.
            if (positionArrayIterator>=0 && i == positions.get(positionArrayIterator)[1]) { //checking if it is part of bracket, when yes, skipping it.
                i = positions.get(positionArrayIterator)[0] - 1;
                if(i < 0){
                    break;
                }
                positionArrayIterator--;
            }
            if (operatorList.contains(argument.charAt(i))&&(i==0||!operatorList.contains(argument.charAt(i-1)))) {
                if(argument.charAt(i)=='+'&&i==0){
                    return buildArgument(argument.substring(1));
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
        return null;
    }

    /**
     * Finds the positions of brackets contained in the argument
     * @return A list of two-dimensional arrays representing the start- and end positions of any outer brackets.
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

    /**
     * Creates a list containing lists of arithmetic operators.
     */
    private static void initOperators(){
        operators = new ArrayList<>();

        operators.add(new ArrayList<>());
        operators.get(0).add('+');
        operators.get(0).add('-');
        operators.add(new ArrayList<>());
        operators.get(1).add('*');
        operators.get(1).add('/');
        operators.add(new ArrayList<>());
        operators.get(2).add('^');
    }
}
