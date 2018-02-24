package calculations;

import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;
import org.apache.commons.math3.stat.descriptive.rank.Max;
import org.apache.commons.math3.stat.descriptive.rank.Min;

import java.util.*;

public class PeakFinder {

    public List<double[]> findPeaks(double[] data) {
        List<double[]> peaks = new ArrayList<>();
        double stdev = new StandardDeviation().evaluate(data);
        int start = 0;
        for (int i = 1; i < data.length - 1; i++) {

            if (data[i] < data[i - 1] && data[i] <= data[i + 1]) {

                double[] peak = Arrays.copyOfRange(data, start, i + 1);
                //run some checks
                if (isNotMidPeakDip(peak, stdev)) {
                    peaks.add(peak);
                    start = i;
                }
            }
        }
        return peaks;
    }

    protected boolean isNotMidPeakDip(double[] peak, double stdev) {
        return stdevLessThanRange(peak, stdev) &&
                startAndEndWithinOneStdev(peak, stdev);
    }

    private boolean startAndEndWithinOneStdev(double[] peak, double stdev) {
        return stdev > Math.abs(peak[0] - peak[peak.length - 1]);
    }

    private boolean stdevLessThanRange(double[] peak, double stdev) {
        return stdev < (new Max().evaluate(peak) - new Min().evaluate(peak));
    }

    private boolean mostLowestValuesAtStartAndEnd(double[] peak, double stdev) {
        final double min = new Min().evaluate(peak);
        double lowSclThreshold = min * 1.1;
        int startOfRise = -1;
        int startOfTail = -1;
        for (int i = 0; i < peak.length; i++) {
            if (peak[i] > lowSclThreshold) {

                if (startOfRise == -1) {
                    startOfRise = i;
                }
                //if it is a mid peak dip, reset start of tail
                if(startOfTail != -1){
                    startOfTail = -1;
                }
            }
            if(peak[i]<= lowSclThreshold){
                if(startOfRise != -1 && startOfTail == -1){
                    startOfTail = i;
                }
            }
        }
        if(startOfTail==-1){
            startOfTail = peak.length-1;
        }
        int totalOuterScl = startOfRise + (peak.length - startOfTail);
        long totalInnerScl = innerLowSclCount(peak, lowSclThreshold, startOfRise, startOfTail);

        return false;
    }

    protected long innerLowSclCount(double[] peak, double lowSclThreshold, int startOfRise, int startOfTail) {
         return Arrays.stream(Arrays.copyOfRange(peak, startOfRise, startOfTail)).filter(scl -> scl <= lowSclThreshold).count();
    }



    protected double[] getChangesByTimepoint(double[] data){
        double[] changes = new double[data.length] ;
        changes[0] = 0;
        for (int i = 1; i < data.length ; i++) {
            changes[i] = data[i] - data [i-1];
        }
        return changes;
    }

}
