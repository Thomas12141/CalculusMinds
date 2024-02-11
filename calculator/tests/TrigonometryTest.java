import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TrigonometryTest {
    double delta = 10E-5;

    @Test
    public void simpleSineTest(){
        Calculator calculator = new Calculator("sin1");
        double returned = calculator.solve();
        double expected = 0.8414709848078965;
        assertEquals(expected, returned, delta);
    }

    @Test
    public void simpleCosineTest(){
        Calculator calculator = new Calculator("cos1");
        double returned = calculator.solve();
        double expected = 0.5403023058681398;
        assertEquals(expected, returned, delta);
    }

    @Test
    public void simpleTanTest(){
        Calculator calculator = new Calculator("tan1");
        double returned = calculator.solve();
        double expected = 1.55740772465;
        assertEquals(expected, returned, delta);
    }

    @Test
    public void simpleCotTest(){
        Calculator calculator = new Calculator("cot1");
        double returned = calculator.solve();
        double expected = 0.64209261593433;
        assertEquals(expected, returned, delta);
    }
}
