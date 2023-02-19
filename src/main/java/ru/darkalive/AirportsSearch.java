package ru.darkalive;

import java.io.*;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class AirportsSearch {

    private byte columnNum;
    private long searchTime;
    private SortedMap<String, Long> columnInfo;

    public AirportsSearch(byte columnNum) { this.columnNum = columnNum; }
    public long getSearchTime() {
        return searchTime;
    }

    public long startFileReading() throws IOException {

        long startTime = System.nanoTime();

        columnInfo = new TreeMap<>();
        RandomAccessFile csvRandomAccessFile = new RandomAccessFile("airports.csv", "r");
        String line; long currentFilePointer = 0L;
        while ((line = csvRandomAccessFile.readLine()) != null) {
            columnInfo.put(line.split(",")[columnNum - 1], currentFilePointer);
            currentFilePointer = csvRandomAccessFile.getFilePointer();
        }
        csvRandomAccessFile.close();

        long endTime = System.nanoTime();

        return (endTime - startTime) / 1000000;
    }

    public int startSearch(String queryString) throws IOException {

        long startSearchTime = System.nanoTime();

        List<String> foundValues = columnInfo.keySet().stream()
                .filter(str -> str.replace("\"", "")
                        .toLowerCase().startsWith(queryString)).collect(Collectors.toList());

        long endSearchTime = System.nanoTime();

        long startOutputTime = System.nanoTime();

        RandomAccessFile csvRandomAccessFile = new RandomAccessFile("airports.csv", "r");
        PrintWriter output = new PrintWriter(System.out);
        foundValues.forEach(str -> {
            try {
                csvRandomAccessFile.seek(columnInfo.get(str));
                output.println(str + "[" + csvRandomAccessFile.readLine() + "]");
            } catch (IOException e) {
                System.out.println("Произошла ошибка во время поиска\r\n" + e.getMessage() + "\r\n");
            }
        } );

        output.flush();
        csvRandomAccessFile.close();
        long endOutputTime = System.nanoTime();
        System.out.println("\r\nЗатраченное на вывод найденных записей время - " + ((endOutputTime - startOutputTime) / 1000000) + " мс");
        searchTime = (endSearchTime - startSearchTime) / 1000000;

        return foundValues.size();
    }
}
