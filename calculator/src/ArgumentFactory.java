import java.util.regex.Pattern;
public class ArgumentFactory {

    private static String regexDecimal = "^-?\\d*\\.\\d+$";
    private static String regexInteger = "^-?\\d+$";
    private static String regexDouble = regexDecimal + "|" + regexInteger;
    private static Pattern doublePattern = Pattern.compile(regexDouble);

    //not finished, must be done iterative through the tree of arguments
    public static Argument buildArgument(String argument){
        if(argument.contains("+")){
            String[] temp = argument.split("\\+");
            Argument left = ArgumentFactory.buildArgument(temp[0]);
            Argument right = ArgumentFactory.buildArgument(temp[1]);
            Argument result = new Plus(left, right);
            for (int i = 2; i < temp.length; i++) {
                result = new Plus(result, ArgumentFactory.buildArgument(temp[i]));
            }
            return result;
        }else if(argument.contains("-")){
            String[] temp = argument.split("-");
            Argument left = ArgumentFactory.buildArgument(temp[0]);
            Argument right = ArgumentFactory.buildArgument(temp[1]);
            return new Minus(left, right);
        }else if(argument.contains("*")){
            String[] temp = argument.split("\\*");
            Argument left = ArgumentFactory.buildArgument(temp[0]);
            Argument right = ArgumentFactory.buildArgument(temp[1]);
            Argument result = new Multiplication(left, right);
            for (int i = 2; i < temp.length; i++) {
                result = new Multiplication(result, ArgumentFactory.buildArgument(temp[i]));
            }
            return result;
        }else if(argument.contains("/")){
            String[] temp = argument.split("/");
            Argument left = ArgumentFactory.buildArgument(temp[0]);
            Argument right = ArgumentFactory.buildArgument(temp[1]);
            return new Devision(left, right);
        }else if(argument.contains("^")){
            String[] temp = argument.split("\\^");
            Argument left = ArgumentFactory.buildArgument(temp[0]);
            Argument right = ArgumentFactory.buildArgument(temp[1]);
            return new Power(left, right);
        }else if(doublePattern.matcher(argument).matches()){
            return new DoubleValue(argument);
        }
        throw new IllegalArgumentException("This argument insnt valid");
    }
}
