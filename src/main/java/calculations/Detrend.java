package calculations;

import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.apache.commons.math3.stat.regression.UpdatingMultipleLinearRegression;

import java.util.Arrays;

public class Detrend {


    public double[] calculateLinearSlope(double[][] data) {
        SimpleRegression regression = new SimpleRegression();
        regression.addData(data);
        regression.regress();
        double slope = regression.getSlope();
        double intercept = regression.getIntercept();
        return new double[]{slope, intercept};
    }

    public double[][] detrend(double [][] data, double[] slopeAndIntercept) {

        for (int i = 0; i < data.length; i++) {
            data[i][1] -= slopeAndIntercept[1] + slopeAndIntercept[0] * i;
        }
        return data;
    }

    public void detrend(double[][] v) {
        if (v.length == 0)
            return;

        SimpleRegression regression = new SimpleRegression();

        for (int i = 0; i < v.length; ++i) {
            double y[] = v[i];

            regression.clear();
            for (int j = 0; j < y.length; ++j) {
                regression.addData(j, y[j]);
            }

            double slope = regression.getSlope();
            double intercept = regression.getIntercept();

            for (int j = 0; j < y.length; ++j) {
                y[j] -= intercept + slope * j;
            }
        }
    }
}
