import org.junit.jupiter.api.Test;

import javax.naming.OperationNotSupportedException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AdditionTest {
    @Test
    public void simpleAddition() throws OperationNotSupportedException {
        Calculator calculator = new Calculator("3+2");
        double returned = calculator.solve();
        double expected = 5;
        assertEquals(expected, returned);
    }

    @Test
    public void simpleAddition1() throws OperationNotSupportedException {
        Calculator calculator = new Calculator("3+2+3");
        double returned = calculator.solve();
        double expected = 8;
        assertEquals(expected, returned);
    }

    @Test
    public void simpleAddition3() throws OperationNotSupportedException {
        Calculator calculator = new Calculator("3+-2");
        double returned = calculator.solve();
        double expected = 1;
        assertEquals(expected, returned);
    }

    @Test
    public void simpleAddition4() throws OperationNotSupportedException {
        Calculator calculator = new Calculator("3-+2"); //failed, array angelegt mit nur temp[0] besetzt
        double returned = calculator.solve();
        double expected = 1;
        assertEquals(expected, returned);
    }

    @Test
    public void simpleAddition5() throws OperationNotSupportedException {
        Calculator calculator = new Calculator("3--2"); //failed, ["3", "", "2"] leeres feld im array
        double returned = calculator.solve();
        double expected = 5;
        assertEquals(expected, returned);
    }

}
