import java.util.regex.Pattern;
public class ArgumentFactory {

    private static String regexDecimal = "^-?\\d*\\.\\d+$";
    private static String regexInteger = "^-?\\d+$";
    private static String regexDouble = regexDecimal + "|" + regexInteger;
    private static Pattern doublePattern = Pattern.compile(regexDouble);
    private static String regexSin = "^sin[0-9]+$";
    private static String regexCos = "^cos[0-9]+$";
    private static String regexTan = "^tan[0-9]+$";
    private static String regexCot = "^cotan[0-9]+$";

    //not finished, must be done iterative through the tree of arguments
    public static Argument buildArgument(String argument){
        if(argument.contains("+")){
            return buildArgumentBinaryOperation("\\+", argument);
        }else if(argument.contains("--")&&!argument.startsWith("-")){
            return buildArgumentBinaryOperation("--", argument);
        }else if(argument.contains("-")&&!argument.startsWith("-")){
            return buildArgumentBinaryOperation("-", argument);
        }else if(argument.contains("*")){
            return buildArgumentBinaryOperation("\\*", argument);
        }else if(argument.contains("/")){
            return buildArgumentBinaryOperation("/", argument);
        }else if(argument.contains("^")){
            return buildArgumentBinaryOperation("\\^", argument);
        }else if(argument.matches(regexSin)){
            return buildArgumentBinaryOperation("sin", argument);
        }else if(argument.matches(regexCos)){
            return buildArgumentBinaryOperation("cos", argument);
        }else if(argument.matches(regexTan)){
            return buildArgumentBinaryOperation("tan", argument);
        }else if(argument.matches(regexCot)){
            return buildArgumentBinaryOperation("cot", argument);
        }else if(argument.contains("log")){
            return buildArgumentBinaryOperation("log", argument);
        }else if(doublePattern.matcher(argument).matches()){
            return new DoubleValue(argument);
        }
        throw new IllegalArgumentException("This argument isn't valid");
    }

    private static Argument buildArgumentBinaryOperation(String operation, String argument){
        Argument left = null, right = null, child = null, result = null;
        String[] temp;

        if(operation.contains("cos") || operation.contains("sin") || operation.contains("tan") || operation.contains("cot") || operation.contains("log")){
            temp = argument.split(operation);
            if (temp.length > 1) {
                child = ArgumentFactory.buildArgument(temp[1].trim()); //Index1 enthält Zahl nach "cos", ...
            }
        } else {
            temp = argument.split(operation);
            left = ArgumentFactory.buildArgument(temp[0]);
            right = ArgumentFactory.buildArgument(temp[1]);
        }

        switch (operation){
            case ("\\+"):
                 result = new Plus(left, right);
                for (int i = 2; i < temp.length; i++) {
                    result = new Plus(result, ArgumentFactory.buildArgument(temp[i]));
                }
                return result;
            case ("--"):
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
                result = new Power(left, right);
                for (int i = 2; i < temp.length; i++) {
                    result = new Power(result, ArgumentFactory.buildArgument(temp[i]));
                }
                return result;
            case ("sin"):
                result = new Sine(child);
                return result;
            case ("cos"):
                result = new Cosine(child);
                return result;
            case ("tan"):
                result = new Tangent(child);
                return result;
            case ("cot"):
                result = new Cotangent(child);
                return result;
            case ("log"):
                result = new Logarithm(child);
                return result;
        }
        throw new IllegalArgumentException("This argument isn't valid");
    }
}
