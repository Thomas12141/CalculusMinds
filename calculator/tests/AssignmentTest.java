import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AssignmentTest {
    @Test
    public void assignmentTest1(){
        Calculator calculator = new Calculator("x=2; x");
        double returned = calculator.solve();
        double expected = 2.0;
        assertEquals(expected, returned);
    }
}
