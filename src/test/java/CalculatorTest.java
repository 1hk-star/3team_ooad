import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    Calculator c = new Calculator();

    @Test
    void add() {
        assertEquals(7,c.add(5,2));
    }

    @Test
    void subtract() {
        assertEquals(3, c.subtract(5, 2));
        assertEquals(3, c.subtract(5, 2));
        assertEquals(3, c.subtract(5, 2));
    }

    @Test
    void main() {
    }
}