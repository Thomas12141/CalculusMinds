import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MixedOperationsTest {
    @Test
    public void firstTest(){
        Calculator calculator = new Calculator("5+2*3");
        double returned = calculator.solve();
        double expected = 5+2*3;
        assertEquals(expected, returned);
    }

    @Test
    public void secondTest(){
        Calculator calculator = new Calculator("sin1+3");
        double returned = calculator.solve();
        double expected = 3.8414709848078967;
        assertEquals(expected, returned);
    }

    @Test
    public void thirdTest(){
        Calculator calculator = new Calculator("2*sin1+2");
        double returned = calculator.solve();
        double expected = 3.682941969615793;
        assertEquals(expected, returned);
    }
}
