import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AdditionTest {
    @Test
    public void simpleAddition(){
        Calculator calculator = new Calculator("3+2");
        double returned = calculator.solve();
        double expected = 5;
        assertEquals(expected, returned);
    }

    @Test
    public void simpleAddition1(){
        Calculator calculator = new Calculator("3+2+3");
        double returned = calculator.solve();
        double expected = 8;
        assertEquals(expected, returned);
    }

    @Test
    public void simpleAddition3(){
        Calculator calculator = new Calculator("3+-2");
        double returned = calculator.solve();
        double expected = 1;
        assertEquals(expected, returned);
    }
}
