public class SimpleCoverageFunctions {

    int addTwoNum(int a, int b) {
        return a + b;
    }

    int returnLargest(int a, int b) {
        int result = b;
        if (a > b) {
            result = a;
        }

        return result;
    }

    int doWeirdStuff(int a, int b, int c) {
        int result = a;
        if (a < b) {
            result = a * b;
        } else if (b < c) {
            result = c / 4;
        } else {
            result = b;
        }

        return result;
    }

}