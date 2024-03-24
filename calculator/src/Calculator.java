import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;

public class Calculator {
    private final ArrayList<Argument> arguments = new ArrayList<>();

    public Calculator(String input) throws OperationNotSupportedException {
        input = input.replace(" ","");
        String[] argumentStrings = input.split(";");
        for(String argument : argumentStrings){
            arguments.add(ArgumentFactory.buildArgument(argument));
        }
    }

    public double solve(){
        return arguments.getLast().calculate();
    }
}
