package com.arka.reportes.application.util;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.function.Function;
public class CsvExporter {

    public static <T> void writeToCsv(PrintWriter writer, List<T> data, String[] headers, Function<T, List<?>> rowMapper) {
        try (CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(headers))) {
            for (T item : data) {
                csvPrinter.printRecord(rowMapper.apply(item));
            }
            csvPrinter.flush();
        } catch (IOException e) {
            throw new RuntimeException("Error al generar CSV: " + e.getMessage(), e);
        }
    }
}
