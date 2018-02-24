package io;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Writer {

    public void writeArray(double[] data, String filename) {
        String dataColumn = buildString(data, "\n");
        writeString(dataColumn, filename);
    }

    private String buildString(double[] data, String separator) {
        StringBuilder sb = new StringBuilder();
        for (double d : data) {
            sb.append(d);
            sb.append(separator);
        }
        return sb.toString().trim();
    }


    public void writeListOfArrays(List<double[]> data, String filename) {
        StringBuilder sb = new StringBuilder();
        String dataRows = data.stream().map(arr ->
            buildString(arr, ",")).collect(Collectors.joining("\n"));
        writeString(dataRows, filename);
    }

    private void writeString(String data, String filename) {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filename))) {
            writer.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
