import java.util.regex.Pattern;
public class ArgumentFactory {

    private static String regexDecimal = "^-?\\d*\\.\\d+$";
    private static String regexInteger = "^-?\\d+$";
    private static String regexDouble = regexDecimal + "|" + regexInteger;
    private static Pattern doublePattern = Pattern.compile(regexDouble);

    //not finished, must be done iterative through the tree of arguments
    public static Argument buildArgument(String argument){
        if(argument.contains("+")){
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
