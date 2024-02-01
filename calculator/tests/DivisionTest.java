import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DivisionTest {

    @Test
    public void SimpleDivision(){
        Calculator calculator = new Calculator("2/3");
        double returned = calculator.solve();
        double expected = 2/3.0;
        assertEquals(expected, returned);
    }

    @Test
    public void SimpleDivision2(){
        Calculator calculator = new Calculator("2/3/2");
        double returned = calculator.solve();
        double expected = 2.0/3.0/2.0;
        assertEquals(expected, returned);
    }

    @Test
    public void SimpleDivision3(){
        Calculator calculator = new Calculator("2/0");
        try {
            calculator.solve();
            fail("Division by 0 wasnt recognized");
        }catch (ArithmeticException e){
            assertEquals("Cant divide by 0", e.getMessage());
        }
    }
}
