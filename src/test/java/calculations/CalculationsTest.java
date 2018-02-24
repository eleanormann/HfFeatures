package calculations;

import io.Loader;
import org.junit.Test;


import java.util.Arrays;

import static org.junit.Assert.assertEquals;


public class CalculationsTest {

    public static final String PATH = "src/test/resources/";

    @Test
    public void testInterpolatePeak() {
        //test creating a sequence of n evenly spaced doubles between lower and upper index
        final Calculations calculations = new Calculations();
        final double[] actual = calculations.interpolatePeak(4, 1, 5);
        final double[] expected = {1, 2, 3, 4};

        assertArrayEquals(expected, actual);

        double[] realSCL = {6.2, 5.36, 5.26, 5.33, 5.87, 6.35, 6.89, 7.08, 7.23};

        final double[] realData =
                calculations.interpolatePeak(realSCL.length, realSCL[0], realSCL[realSCL.length - 1]);

        final double[] expectedData = {6.2, 6.32875, 6.4575, 6.58625, 6.715, 6.84375, 6.9725, 7.10125, 7.23};

        assertArrayEquals(expected, actual);
    }


    @Test
    public void testInterpolatePeaksStops() {
        final Loader loader = new Loader();
        double[][] multiplePeaks = loader.loadMatrix(PATH + "multiple-peaks.txt");
        double[][] expected = loader.loadMatrix(PATH + "smoothed-multiple-peaks.txt");
        //double[] mockedUpdate = {4.2, 4.282, 4.364, 4.446, 4.528, 4.61, 4.692, 4.774, 4.856, 4.938, 5.02};
        Calculations cal = new Calculations();
        final double[] scl = Arrays.stream(multiplePeaks).mapToDouble(arr -> arr[1]).toArray();
        cal.interpolatePeaks(scl);

        assertArrayEquals(Arrays.stream(expected).mapToDouble(arr -> arr[1]).toArray(), scl);

    }


    @Test
    public void updateSubArray() {
        double[] expected = {7.83, 7.83545454545455, 7.84090909090909, 7.84636363636364, 7.85181818181818,
                7.85727272727273, 7.86272727272728, 7.86818181818182, 7.87363636363637, 7.87909090909091,
                7.88454545454546,7.89};
        double[] testArr = {7.83, 7.78, 7.55, 7.34, 6.97, 6.88, 6.91, 6.87, 7.34, 7.36, 7.73, 7.89};
        new Calculations().updateSubArray(testArr, 0, testArr.length-1);
    }


    public static void assertArrayEquals(double[] expected, double[] actual) {
        for (int i = 0; i < actual.length; i++) {
            assertEquals(expected[i], actual[i], 0.000001);
        }
    }

}
