package calculations;

public class Calculations {


    public double[] interpolatePeak(int n, double lower, double upper) {
        double[] newPeak = new double[n];
        double increment = (upper - lower) / n;
        newPeak[0] = lower;
        for (int i = 1; i < n; i++) {
            newPeak[i] = newPeak[i - 1] + increment;
        }
        return newPeak;
    }

    public void interpolatePeaks(double[] scl) {
        int start = 0;
        int end = 0;
        int n;

        for (int i = 0; i < scl.length - 1; i++) {
            if (scl[i] > scl[i + 1]) {
                if(start == 0){

                start = i;
                }
            }
            if (scl[i] > scl[start]) {
                if(start != 0){

                end = i;
                }
            }
            n = end - start;
            if (n > 1) {
                scl = updateSubArray(scl, start, end);
                start = 0;
                end = 0;
            }
        }

    }

    protected double[] updateSubArray(double[] scl, int start, int end) {
        double[] subArray = interpolatePeak(end-start, scl[start], scl[end]);
        for (int i = 0; i < subArray.length; i++) {
            scl[start + i] = subArray[i];
        }
        return scl;
    }
}

