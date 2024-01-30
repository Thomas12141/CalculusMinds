import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AdditionTest {
    @Test
    public void simpleAddition(){
        Calculator calculator = new Calculator("3+2");
        double returned = calculator.solve();
        double expected = 5;
        assertEquals(returned, expected);
    }
}
