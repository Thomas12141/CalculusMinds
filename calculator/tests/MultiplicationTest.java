import org.junit.jupiter.api.Test;

import javax.naming.OperationNotSupportedException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MultiplicationTest {

    @Test
    public void SimpleMultiplication() throws OperationNotSupportedException {
        Calculator calculator = new Calculator("2*3");
        double returned = calculator.solve();
        double expected = 6;
        assertEquals(expected, returned);
    }

    @Test
    public void SimpleMultiplication2() throws OperationNotSupportedException {
        Calculator calculator = new Calculator("2*3*2");
        double returned = calculator.solve();
        double expected = 12;
        assertEquals(expected, returned);
    }
}
