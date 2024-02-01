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
}
