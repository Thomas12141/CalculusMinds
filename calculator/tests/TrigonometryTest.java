import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TrigonometryTest {
    double delta = 10E-5;

    @Test
    public void simpleSineTest(){
        Calculator calculator = new Calculator("sin(1)");
        double returned = calculator.solve();
        double expected = 0.8414709848078965;
        assertEquals(expected, returned, delta);
    }

    @Test
    public void simpleCosineTest(){
        Calculator calculator = new Calculator("1+cos(1*3)");
        double returned = calculator.solve();
        double expected = 0.010007503399554585;
        assertEquals(expected, returned, delta);
    }

    @Test
    public void simpleTanTest(){
        Calculator calculator = new Calculator("1+tan(1)-2");
        double returned = calculator.solve();
        double expected = 0.5574;
        assertEquals(expected, returned, delta);
    }

    @Test
    public void simpleCotTest(){
        Calculator calculator = new Calculator("cot(1)");
        double returned = calculator.solve();
        double expected = 0.64209261593433;
        assertEquals(expected, returned, delta);
    }
}
