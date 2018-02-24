package calculations;

import com.google.common.collect.ImmutableList;
import io.Loader;
import io.Writer;
import org.apache.commons.math3.stat.descriptive.rank.Min;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static calculations.CalculationsTest.PATH;
import static org.junit.Assert.*;

public class PeakFinderTest {
    private double[] data = {1, 1.2, 1.1, 1.3, 2.5, 2.9, 3, 6.6, 6.3, 6.4, 5, 4, 3, 3, 3.1, 2, 1, 1.1, 2.7};

    @Test
    public void testFindPeaks() {
        List<double[]> peaks = new PeakFinder().findPeaks(data);
        peaks.forEach(arr -> System.out.println(Arrays.toString(arr)));

        assertEquals(3, peaks.size());

        double[] secondPeak = {1.0, 1.2, 1.1, 1.3, 2.5, 2.9, 3.0, 6.6, 6.3};
        double[] thirdPeak = {6.3, 6.4, 5.0, 4.0, 3.0};
        double[] fourthPeak = {3.0, 3.0, 3.1, 2.0, 1.0};
        List<double[]> expectedPeaks = ImmutableList.of(secondPeak, thirdPeak, fourthPeak);
        for (int i = 0; i < peaks.size(); i++) {
            double[] peak = peaks.get(i);
            for (int j = 0; j < peak.length; j++) {
                assertEquals(expectedPeaks.get(i)[j], peak[j]);
            }
        }

    }


    @Test
    public void testFindPeaksOnMultiplePeaks(){
        double[] multiplePeaks = new Loader().loadArray(PATH + "id012-scl.txt");

        List<double[]> peaks = new PeakFinder().findPeaks(multiplePeaks);
        new Writer().writeListOfArrays(peaks, PATH + "peaks.csv");
    }


    @Test
    public void testInnerLowSclCount(){

        double threshold = new Min().evaluate(data) * 1.1;
        System.out.println(threshold);
        long count = new PeakFinder().innerLowSclCount(data, 1.1,
                1, data.length-1);
        assertEquals(3L, count);
    }


}