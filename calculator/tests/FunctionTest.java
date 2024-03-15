import org.junit.jupiter.api.Test;

import javax.naming.OperationNotSupportedException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FunctionTest {
    @Test
    void firstTest() throws OperationNotSupportedException {
        Calculator calculator = new Calculator("x=2; f(x) = 2*x");
        double returned = calculator.solve();
        double expected = 4;
        assertEquals(expected, returned);
    }
    @Test
    void secondTest() throws OperationNotSupportedException {
        Calculator calculator = new Calculator("f(x) = 2*x; x=2; f(x)");
        double returned = calculator.solve();
        double expected = 4;
        assertEquals(expected, returned);
    }
}
