import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PowerTest {

    @Test
    public void SimplePower(){
        Calculator calculator = new Calculator("2^3");
        double returned = calculator.solve();
        double expected = 8;
        assertEquals(expected, returned);
    }

    @Test
    public void SimpeMultiplication2(){
        Calculator calculator = new Calculator("2^3^2");
        double returned = calculator.solve();
        double expected = Math.pow(2.0,Math.pow(3.0, 2.0));
        assertEquals(expected, returned);
    }
}
