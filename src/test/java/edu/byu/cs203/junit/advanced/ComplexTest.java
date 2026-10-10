package edu.byu.cs203.junit.advanced;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Complex calculator")
class ComplexTest {

    private final Complex calc = new Complex();

    @Nested
    @DisplayName("Factorial")
    class FactorialTest {

        /*
        Covers normal positive inputs, as well as boundary cases of 20 and 0
         */
        @ParameterizedTest(name = "{0}! = {1}")
        @CsvSource({
                "1,1",
                "2,2",
                "5, 120",
                "6, 720",
                "0,1",
                "20, 2432902008176640000",
                "10, 3628800"
        })
        @DisplayName("Factorial within range 0-20")
        void factorial(long num, long expected) {
            assertEquals(expected, calc.factorial(num));
        }

        @Test
        @DisplayName("Negative Throws Execpection For negative Values")
        void negativeFactorial(){
            assertThrows(ArithmeticException.class, () -> calc.factorial(-4));
        }
    }

    @Nested
    @DisplayName("Square Root Tests")
    class SquareRootTest {
        /*
        Covers positive perfect squares, non-perfect squares, and positive fractions less than 1, as well as 0.
         */
        @ParameterizedTest(name = "Square Root of {0} ~ {1}")
        @CsvSource({
                "4,2",
                "16,4",
                "2, 1.4142135623",
                "0.10, 0.31622776601",
                "0.25, 0.50",
                "0,0"
        })
        @DisplayName("Positive Square Roots")
        void squareRoot(double num, double expected) {
            assertEquals(expected, calc.squareRoot(num), 0.0001);
        }

        /*
            Covers negative inputs, NaN, and positive and negative infinity.
         */
        @ParameterizedTest(name = "Square root special cases: {0}")
        @DisplayName("Special Cases for Square Root")
        @CsvSource({
                "-1, NaN",
                "-10000, NaN",
                "NaN, NaN",
                "-Infinity, NaN",
                "Infinity, Infinity"
        })
        void specialCases(double num, double expected){
            assertEquals(expected, calc.squareRoot(num));
        }
    }

    @Nested
    @DisplayName("Sin Tests")
    class sinTests {

        /*
        Covers every quadrant on unit circle, including numbers above 360 and below 0.
         */
        @DisplayName("Standard Sin Tests")
        @ParameterizedTest(name = "Sine of: {0}")
        @CsvSource({
                "30",
                "150",
                "210",
                "330",
                "0",
                "90",
                "180",
                "270",
                "360",
                "-30",
                "-390",
                "390",
                "750"
        })
        void sin(double degrees) {
            double expected = Math.sin(Math.toRadians(degrees));
            assertEquals(expected, calc.sin(degrees), 0.00005);
        }

        /*
        Covers each special case input of NaN, + or - infinity
         */
        @DisplayName("Special Sin Tests")
        @ParameterizedTest(name = "Sine of: {0}")
        @CsvSource({
                "NaN",
                "-Infinity",
                "Infinity"
        })
        void specialSinCases(double degrees){
            assertTrue(Double.isNaN(calc.sin(degrees)));
        }
    }

}