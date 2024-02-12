import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TrigonometryTest {

    @Test
    public void simpleSineTest(){
        Calculator calculator = new Calculator("sin(1)");
        double returned = calculator.solve();
        double expected = 0.8414709848078965;
        assertEquals(expected, returned);
    }

    @Test
    public void simpleCosineTest(){
        Calculator calculator = new Calculator("1+cos(1*3)");
        double returned = calculator.solve();
        double expected = 0.010007503399554585;
        assertEquals(expected, returned);
    }

    @Test
    public void simpleTanTest(){
        Calculator calculator = new Calculator("1+tan(1)-2");
        double returned = calculator.solve();
        double expected = 0.5574077246549023;
        assertEquals(expected, returned);
    }

    @Test
    public void simpleCotTest(){
        Calculator calculator = new Calculator("cot(1)");
        double returned = calculator.solve();
        double expected = 0.6420926159343306;
        assertEquals(expected, returned);
    }
}
