import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class SimpleCoverageFunctionsTest {
    SimpleCoverageFunctions testClass;

    @BeforeEach
    public void setUp() throws Exception {
        testClass = new SimpleCoverageFunctions();
    }

    @Test
    public void addTwoNumTest(){
        int sum = testClass.addTwoNum(1, 2);
        assertEquals(3, sum);
    }

    @Test
    void returnLargest() {
        assertEquals(7, testClass.returnLargest(7, 5));
        assertEquals(4, testClass.returnLargest(0,4 ));
    }

    @Test
    void doWeirdStuff() {
        assertEquals(2, testClass.doWeirdStuff(10, 2, 2));
        assertEquals(0, testClass.doWeirdStuff(-1, 0, 10));
        assertEquals(4, testClass.doWeirdStuff(10, 4,16));
    }
}