public class Derivator {
    public Argument derivation(Argument argument){
        switch (argument){
            case Plus plus -> {
                return derivation((Plus) argument);
            }
            case Minus minus -> {
                return derivation((Minus) argument);
            }
            case Sine sine -> {
                return derivation((Sine) argument);
            }
            case Brackets brackets -> {
                return derivation((Brackets) argument);
            }
            case Cotangent cotangent -> {
                return derivation((Cotangent) argument);
            }
            default -> throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    public Devision derivation(Cotangent argument){// y = cot(x) -> y' = -1/((sin(x))^2)
        if(new Sine(argument.child.clone()).calculate()==0){
            throw new ArithmeticException("Division by zero.");
        }
        return new Devision(new DoubleValue("-1"), new Power(new Sine(argument.child.clone()), new DoubleValue("2")));
    }

    public Plus derivation(Plus argument){
        Argument leftDerivative = derivation(argument.getLeft());
        Argument rightDerivative = derivation(argument.getRight());
        return new Plus(leftDerivative, rightDerivative);
    }

    public Minus derivation(Minus argument){
        Argument leftDerivative = derivation(argument.getLeft());
        Argument rightDerivative = derivation(argument.getRight());
        return new Minus(leftDerivative, rightDerivative);
    }

    public Multiplication derivation(Sine argument){//Chain rule, y = f(g(x))-> y' = f'(g(x))*g'(x)
        Argument child = derivation(argument.getChild());
        return new Multiplication(new Cosine(argument.getChild().clone()), derivation(child));
    }

    public Brackets derivation(Brackets argument){
        return new Brackets(derivation(argument.getChild()));
    }
}
