import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BracketsTest {

    @Test
    public void SimpleBrackets(){
        Calculator calculator = new Calculator("(2^3)");
        double returned = calculator.solve();
        double expected = 8;
        assertEquals(expected, returned);
    }

    @Test
    public void SimpleBrackets2(){
        Calculator calculator = new Calculator("((2^3))");
        double returned = calculator.solve();
        double expected = 8;
        assertEquals(expected, returned);
    }

    @Test
    public void SimpleBrackets3(){
        Calculator calculator = new Calculator("3 + (2*2)");
        double returned = calculator.solve();
        double expected = 7;
        assertEquals(expected, returned);
    }

    @Test
    public void SimpleBrackets4(){
        Calculator calculator = new Calculator("((2^3)) + 3");
        double returned = calculator.solve();
        double expected = 11;
        assertEquals(expected, returned);
    }

    @Test
    public void SimpleBrackets5(){
        Calculator calculator = new Calculator("((2^3)) * 3");
        double returned = calculator.solve();
        double expected = 24;
        assertEquals(expected, returned);
    }

    @Test
    public void SimpleBrackets6(){
        Calculator calculator = new Calculator("3 * ((2^3))");
        double returned = calculator.solve();
        double expected = 24;
        assertEquals(expected, returned);
    }

    @Test
    public void SimpleBrackets7(){
        Calculator calculator = new Calculator("3 * ((2*3))");
        double returned = calculator.solve();
        double expected = 18;
        assertEquals(expected, returned);
    }

    @Test
    public void SimpleBrackets8(){
        Calculator calculator = new Calculator("3 * ((2*3)) + (3*2)");
        double returned = calculator.solve();
        double expected = 24;
        assertEquals(expected, returned);
    }

    @Test
    public void SimpleBrackets9(){
        Calculator calculator = new Calculator("3 * (((2*3))) * (3*2)");
        double returned = calculator.solve();
        double expected = 108;
        assertEquals(expected, returned);
    }
}
