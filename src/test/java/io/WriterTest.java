package io;

import calculations.Calculations;
import com.google.common.collect.ImmutableList;
import jdk.nashorn.internal.ir.annotations.Immutable;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static calculations.CalculationsTest.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class WriterTest {
    private static final String PATH = "src/test/resources/";

    @Test
    public void testWriterWrites(){
        double[] data = {1,2,3,4};
        final String filename = PATH + "test-write.txt";

        new Writer().writeArray(data, filename);

        assertArrayEquals(data, new Loader().loadArray(filename));
    }


    @Test
    public void testWriterWritesReal(){

        double[][] data = new Loader().loadMatrix(PATH + "id012.txt");

        Calculations cal = new Calculations();
        final double[] scl = Arrays.stream(data).mapToDouble(arr -> arr[1]).toArray();
        cal.interpolatePeaks(scl);

        new Writer().writeArray(scl, PATH + "id012-scl.txt");
    }

    @Test
    public void testWriteListOfArrays(){
        List<double[]> rows = ImmutableList.of(new double[]{1,2}, new double[]{1,2,3});
        new Writer().writeListOfArrays(rows, PATH + "rows.txt");
    }
}
