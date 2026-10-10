package edu.byu.cs203.junit.advanced;
import static java.lang.System.exit;

public class Complex {

    static final double PRECISION = 0.00001;
    static final double LN_10 = 2.3025851;
    static final int DEPTH = 4;
    Complex() {
    }

    /**
     * Returns the factorial of numbers 0 - 20.
     * <p>
     * There is one bug in this method.
     *
     * @param number A number 0 - 20
     * @return The factorial of the parameter given.
     */
    /*
    Bug fixed: The i switch to type long,
    Change: i now starts at number - 1 and multiplies factorial by i, decrementing 1 each time instead of incrementing i.
     */
    public long factorial(long number) {
        if (number < 0) {
            throw new ArithmeticException("Cannot calculate the factorial of a negative number");
        }
        if (number == 1 || number == 0){
            return 1;
        }

        long factorial = number;
        for (long i = number-1; i >= 1; i--) {
            factorial *= i;
        }

        return factorial;
    }

    /**
     * Returns the square root of a number.
     * <p>
     * Special cases:
     * <ul>
     * <li>If the number is less than 0 or is NaN (not a number), then {@link Double#NaN} is returned.
     * <li>If the number is positive infinity, then {@link Double#POSITIVE_INFINITY} is returned.
     * <li>If the number is 0, then the parameter itself gets returned (0 as a double in Java can be + or -).
     * </ul>
     * <p>
     * There is one edge case bug in this method.
     *
     * @param number Any number of the type double.
     * @return The square root of the number parameter, with the special cases noted
     * above.
     */
    /*
    Bug Fix: Function now returns the correct value for Special cases like Positive Infinity, NaN and the correctly signed 0.
    Before the function would loop forever on 0.
     */
    public double squareRoot(double number) {
        if (number < 0 || Double.isNaN(number)) {
            return Double.NaN;
        }

        if (Double.POSITIVE_INFINITY == number) {
            return Double.POSITIVE_INFINITY;
        }

        if(number == 0){
            return number;
        }

        double t;
        double squareRoot = number / 2;
        do {
            t = squareRoot;
            squareRoot = (t + (number / t)) / 2;
        } while ((t - squareRoot) != 0);

        return squareRoot;
    }


    /**
     * Returns the sine of an angle in degrees to an accuracy of .00005
     * For more information on the approximation used, visit <a href="https://mathonweb.com/help_ebook/appendix.htm#algorithms">...</a>
     * <p>
     *
     * Special cases:
     * <ul>
     * <li>If the angle is less than 0 or greater than 360, 360 is added or subtracted to find a symmetric angle within
     *      the correct range.
     * <li>If the angle is positive infinity, negative infinity, or NaN then {@link Double#NaN} is returned.
     * </ul>
     * <p>
     * There are two known bugs in this method.  You will find them if you test all the equivalency classes.
     *
     *
     * @param angle The angle in degrees
     * @return The sine of the value given
     */
    /*
    Bug Fix 1 : Changed angle = 360 to +=360, so if the angle is negative is correcectly finds the equivalent angle.
    Buf Fix 2 : Comparison of if quandrant was in 3 or 4 was incorrectly include 2 with a quadrant >= 2, fixed to quadrant > 2.
     */
    public double sin(double angle){
        if(Double.isNaN(angle) || angle == Double.POSITIVE_INFINITY || angle == Double.NEGATIVE_INFINITY){
            return Double.NaN;
        }

        //If the angle is not between 0 and 360 degrees, add or subtract 360 until it falls in the correct range.
        while(angle > 360){
            angle -= 360;
        }
        while (angle < 0){
            angle += 360;
        }

        /*Determine which quadrant the angle is in
            Quadrant one: between 0 and 90 degrees
            Quadrant two: between 91 and 180
            Quadrant three: between 181 and 270
            Quadrant four: between 271 and 360
        */
        int quadrant = 0;
        if(angle <= 360){
            quadrant = 4;
        }
        if(angle <= 270){
            quadrant = 3;
        }
        if(angle <= 180){
            quadrant = 2;
        }
        if(angle <= 90){
            quadrant = 1;
        }

        if(quadrant == 0){
            //This should not happen
            System.out.println("Unable to determine quadrant. Exiting.");
            exit(-1);
        }

        //Find the symmetric angle in the first quadrant to be able to use Taylor Series Approximation
        switch (quadrant){

            case 2 -> { //If angle is in quadrant 2, the equivalent angle in quadrant 1 = 180 - angle
                angle = 180 - angle;
            }
            case 3 -> { //If angle is in quadrant 3, the equivalent angle in quadrant 1 = angle - 180
                angle -= 180;
            }
            case 4 -> { //If angle is in quadrant 4, the equivalent angle in quadrant 1 = 360 - angle
                angle = 360 - angle;
            }
            default -> {
                break;
            }
        }

        double answer = -1;

        //Convert angle to radians and use Taylor's Series of sine to approximate
        angle = Math.toRadians(angle);
        answer = sinTaylorSeries(angle);

        //If the original angle was in quadrant 3 or 4,
        // multiply the answer by -1 to adjust for conversion to the symmetric angle in quadrant 1
        if(quadrant > 2){
            answer = -answer;
        }

        return answer;
    }

    /**
     * You DO NOT need to test this method. It is a helper method for
     * {@link Complex#sin(double) sin()}.
     * <p>
     * There are no known bugs in this method.
     *
     * @param x The number to be calculated in the series
     * @return Series calculation of x.
     */
    private double sinTaylorSeries(double x){
        double answer = 0;
        for(int i = 0; i <= DEPTH; i++){
            long n = (2 * i) + 1;
            long factorial = factorial(n);
            answer += ((Math.pow(-1, i)) / factorial) * Math.pow(x, n);
        }

        return answer;
    }
}

