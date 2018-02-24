package calculations;


import io.Loader;
import io.Writer;
import org.junit.Test;


import java.util.Arrays;

import static calculations.CalculationsTest.PATH;
import static org.junit.Assert.assertEquals;

public class DetrendTest {
    public static final double DELTA = 1e-15;

    @Test
    public void testSlopeIsCalculated(){
        double[][] dataWithoutTrend = {{0, 1}, {1, 1}, {2, 1}, {3, 1}, {4, 1}};
        double[] noTrendData = new Detrend().calculateLinearSlope(dataWithoutTrend);
        assertEquals(0.0, noTrendData[0], 0.00000001);
        assertEquals(1, noTrendData[1], 0.00000001);

        double[][] dataWithTrend = {{0, 0.5}, {1, 1}, {2, 1.5}, {3, 2}, {4, 2.5}};
        final double[] trendData = new Detrend().calculateLinearSlope(dataWithTrend);
        assertEquals(0.5, trendData[0], 0.00000001);
        assertEquals(0.5, trendData[1], 0.00000001);

    }


    @Test
    public void testRealSlopeIsFeasible(){
        double[][] realData = new Loader().loadMatrix(PATH + "id012.txt");
        final Detrend detrender = new Detrend();
        final double[] slopeAndIntercept = detrender.calculateLinearSlope(realData);
        assertEquals(-2.3847148382730984E-4, slopeAndIntercept[0], DELTA);

        final double[][] detrendedData = detrender.detrend(realData, slopeAndIntercept);

        final double[] noTrend = detrender.calculateLinearSlope(detrendedData);
        assertEquals(0.0, noTrend[0], DELTA);
        new Writer().writeArray(Arrays.stream(detrendedData).mapToDouble(arr -> arr[1]).toArray(), PATH + "detrended012.txt");

    }

    @Test
    public void testDetrendRemovesTrend(){
        double[][] dataWithTrend = {{0, 0.5}, {1, 1}, {2, 1.5}, {3, 2}, {4, 2.5}};


        final Detrend detrender = new Detrend();
        final double[] slopeAndIntercept = detrender.calculateLinearSlope(dataWithTrend);

        assertEquals(0.5, slopeAndIntercept[0], DELTA);

        final double[][] detrendedData = detrender.detrend(dataWithTrend, slopeAndIntercept);

        final double[] noTrend = detrender.calculateLinearSlope(detrendedData);

        assertEquals(0.0, noTrend[0], 0.00000001);
        assertEquals(0.0, noTrend[1], 0.00000001);
    }
}
