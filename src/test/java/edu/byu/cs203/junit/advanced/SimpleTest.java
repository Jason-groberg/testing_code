package edu.byu.cs203.junit.advanced;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;

class SimpleTest {

    private final Simple calc = new Simple();

    /* Cover Positive, Negative, operands and when they mixed
    Covers addition with 0 when it is all positions. */
    @ParameterizedTest(name= "{0} + {1} = {2}")
    @CsvSource({
            "9999, 1, 10000",
            "-2, -3, -5",
            "-2, 3, 1",
            "2, -3, -1",
            "0, 5, 5",
            "5, 0, 5",
            "0, 0, 0"
    })
    @DisplayName("Addition")
     void add(int num1, int num2, int expected) {
        assertEquals(expected, calc.add(num1,num2));
    }

    /*
    over Positive, Negative, operands and when they mixed
    Covers subtraction with 0 when it is all positions.
     */
    @ParameterizedTest(name="{0} - {1} = {2}")
    @CsvSource({
        "9999, 1, 9998",
        "-2, -3, 1",
        "-2, 3, -5",
        "2, -3, 5",
        "0, 5, -5",
        "5, 0, 5",
        "0, 0, 0"
    })
    @DisplayName("Subtraction")
    void subtract(int num1, int num2, int expected) {
        assertEquals(expected, calc.subtract(num1, num2));
    }
    /*
    Covers Positive, Negative, operands and when they mixed
    Covers mulitplication with 0 when it is all positions.
     */
    @ParameterizedTest(name = "{0} * {1} = {2}")
    @CsvSource({
        "1, 1, 1",
        "1, 100, 100",
        "0, 0 , 0",
        "-1, 100, -100",
        "-80, 0, 0",
        "4, 4, 16",
        "2, 10, 20",
        "-2, -2, 4",
        "-3, 8, -24"
    })
    @DisplayName("Multiplication")
    void multiply(int num1, int num2, int expected) {
        assertEquals(expected, calc.multiply(num1, num2));
    }

    /*
    Covers both positive operands, both negative operands,
    mixed signs, zero numerator, zero denominator is tested separately.
    Cover integer floor division when the remainder is not 0.
    */
    @ParameterizedTest(name="{0} / {1} = {2}")
    @CsvSource({
        "10,10,1",
        "20,2,10",
        "-10,5,-2",
        "-22,-11,2",
        "10, -2, -5",
        "0, 10000, 0",
        "0, -1999, 0",
        "10, -5, -2",
        "0, 5, 0",
        "0, -5, 0",
        "7, 2, 3",
    })
    @DisplayName("Integer division")
    void divide(int num1, int num2, int expected) {
        assertEquals(expected, calc.divide(num1, num2));
    }
    @Test
    @DisplayName("Division by zero throws ArithmeticException")
    void divideByZero(){
        assertThrows(ArithmeticException.class, () -> calc.divide(1, 0));
    }

    /*
    Covers positive and negative bases, and zero base.
    Covers negative, positive, and 0 exponents with positive and negative bases.
    */
    @ParameterizedTest(name = "{0}^{1} = {2}")
    @CsvSource({
        "1,1000,1",
        "1,0,1",
        "2000,0,1",
        "2, -1, 1",
        "-40, -1, 1",
        "-2, 2, 4",
        "-20, 1, -20",
        "15,1,15",
        "0, 100,0",
        "1,1,1",
        "0,0,1",
        "-2, 3, -8",
        "-2,2,4",
        "-2,-3,1",
        "-2, 0 ,1"
    })
    @DisplayName("Integer powers")
    void power(int base, int exp, int expected) {
        assertEquals(expected, calc.power(base, exp));
    }
}