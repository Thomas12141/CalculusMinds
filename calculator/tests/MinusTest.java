import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MinusTest {

    @Test
    public void SimpleMinus(){
        Calculator calculator = new Calculator("2-3");
        double returned = calculator.solve();
        double expected = -1;
        assertEquals(expected, returned);
    }

    @Test
    public void SimpleMinus2(){
        Calculator calculator = new Calculator("5-2-3");
        double returned = calculator.solve();
        double expected = 0;
        assertEquals(expected, returned);
    }

    @Test
    public void SimpleMinus3(){
        Calculator calculator = new Calculator("-3");
        double returned = calculator.solve();
        double expected = -3;
        assertEquals(expected, returned);
    }
}
