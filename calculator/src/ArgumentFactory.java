import java.util.regex.Pattern;
public class ArgumentFactory {

    private static String regexDecimal = "^-?\\d*\\.\\d+$";
    private static String regexInteger = "^-?\\d+$";
    private static String regexDouble = regexDecimal + "|" + regexInteger;
    private static Pattern doublePattern = Pattern.compile(regexDouble);

    //not finished, must be done iterative through the tree of arguments
    public static Argument buildArgument(String argument){
        if(argument.contains("(")){
            return buildArgumentBracketContained(argument, 0, argument.length()-1);
        }else if(argument.contains("+")){
            return buildArgumentBinaryOperation("\\+", argument);
        }else if(argument.contains("-")&&!argument.startsWith("-")){
            return buildArgumentBinaryOperation("-", argument);
        }else if(argument.contains("*")){
            return buildArgumentBinaryOperation("\\*", argument);
        }else if(argument.contains("/")){
            return buildArgumentBinaryOperation("/", argument);
        }else if(argument.contains("^")){
            return buildArgumentBinaryOperation("\\^", argument);
        }else if(doublePattern.matcher(argument).matches()){
            return new DoubleValue(argument);
        }
        throw new IllegalArgumentException("This argument insnt valid");
    }
    private static Argument buildArgumentBracketContained(String argument, int start, int end){
        int from = argument.indexOf("(", start + 1);
        int to = argument.lastIndexOf(")", end - 1);
        if(from == -1 && to == -1){
            return new Brackets(ArgumentFactory.buildArgument(argument.substring(start + 1, end)));
        }

        if(from == -1 || to == -1){
            throw new IllegalArgumentException("Brackets arent valid.");
        }

        if(from != start + 1 || to != end - 1){
            int pos = 0;
            String operation = null;
            if(argument.contains("+") && argument.indexOf("+")<from){
                pos = argument.indexOf("+");
                operation = "+";
            }else if(argument.contains("+") && argument.lastIndexOf("+")>to){
                pos = argument.indexOf("+");
                operation = "+";
            }else if(argument.contains("*") && argument.indexOf("*")<from){
                pos = argument.indexOf("*");
                operation = "*";
            }else if(argument.contains("*") && argument.lastIndexOf("*")>to){
                pos = argument.indexOf("*");
                operation = "*";
            }
            if(operation.equals("+")){
                Argument left = ArgumentFactory.buildArgument(argument.substring(0, pos));
                Argument right = ArgumentFactory.buildArgument(argument.substring(pos + 1));
                return new Plus(left, right);
            }else if(operation.equals("*")){
                Argument left = ArgumentFactory.buildArgument(argument.substring(0, pos));
                Argument right = ArgumentFactory.buildArgument(argument.substring(pos + 1));
                return new Multiplication(left, right);
            }
        }
        return new Brackets(ArgumentFactory.buildArgument(argument.substring(from , to + 1)));
    }
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
}
