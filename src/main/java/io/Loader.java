package io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Loader {


    public double[][] loadMatrix(String filename) {
        return parseToDoubleMatrix(loadTextFile(filename));
    }

    public double[] loadArray(String filename){
        return parseToDoubleArray(loadTextFile(filename));
    }

    private List<String> loadTextFile(String filename) {
        try (Stream<String> text = Files.lines(Paths.get(filename))) {

            return text.collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private double[] parseToDoubleArray(List<String> data) {
        double[] scl = new double[data.size()];
        for (int i = 0; i < data.size(); i++) {
            scl[i] = Double.parseDouble(data.get(i));
        }
        return scl;
    }

    private double[][] parseToDoubleMatrix(List<String> data) {
        List<String> sclString = data.stream()
                .map(s -> s.split("\t"))
                .map(arr -> arr.length > 1 ? arr[1] : arr[0])
                .collect(Collectors.toList());
        double[][] scl = new double[data.size()][2];
        for (int i = 0; i < data.size(); i++) {
            scl[i][0] = i;
            scl[i][1] = Double.parseDouble(sclString.get(i));
        }
        return scl;
    }
}
