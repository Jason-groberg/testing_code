import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CalculatorTest {

    private Calculator calculator;

    @BeforeEach
    public void setup(){
         calculator = new Calculator();
    }

    @Test
    public void addTest() {
        Assertions.assertEquals(5, calculator.add(2, 3));
        Assertions.assertEquals(9, calculator.add(4, 5));

    }

    @Test
    public void subtractTest() {
        Assertions.assertEquals(3, calculator.subtract(7,4));
        Assertions.assertEquals(12, calculator.subtract(20, 8));
    }

    @Test
    public void powerTest_positiveValuePositivePower(){
        Assertions.assertEquals(16, calculator.power(4,2));
        Assertions.assertEquals(27, calculator.power(3, 3));
    }

    @Test
    public void powerTest_positiveValueZeroPower(){
        Assertions.assertEquals(1, calculator.power(3,0));
    }

    @Test
    public void powerTest_negativeValueNegativePower(){
        Assertions.assertEquals(0.04, calculator.power(-5, -2), .001);
    }

    @Test
    public void powerTest_zeroValueZeroPower(){
        Assertions.assertThrows(ArithmeticException.class, ()-> calculator.power(0,0));
    }

    @Test
    public void powerTest_zeroValuePositivePower(){
        Assertions.assertEquals(0, calculator.power(0,3));
    }

    @Test
    public void powerTest_zeroValueNegativePower(){
        Assertions.assertThrows(ArithmeticException.class, () -> calculator.power(0, -2));
    }

    @Test
    public void powerTest_negativeValueZeroPower(){
        Assertions.assertEquals(1, calculator.power(-2, 0));
    }

    @Test
    public void powerTest_positiveValueNegativePower(){
        Assertions.assertEquals(0.04, calculator.power(5,-2),.001);
        Assertions.assertEquals(0.0625, calculator.power(4, -2),.001);
    }

    @Test
    public void powerTest_negativeValuePositivePower(){
        Assertions.assertEquals(9, calculator.power(-3, 2));
        Assertions.assertEquals(-27, calculator.power(-3, 3));
    }





}