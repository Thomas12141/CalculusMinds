import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MultiplicationTest {

    @Test
    public void SimpeMultiplication(){
        Calculator calculator = new Calculator("2*3");
        double returned = calculator.solve();
        double expected = 6;
        assertEquals(expected, returned);
    }

    @Test
    public void SimpeMultiplication2(){
        Calculator calculator = new Calculator("2*3*2");
        double returned = calculator.solve();
        double expected = 12;
        assertEquals(expected, returned);
    }
}
