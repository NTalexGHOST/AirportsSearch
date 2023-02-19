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

    // Простой самописный аналог String.split()
    private String getColumnValue(String columnLine) {

        for (int i = 0; i < (columnNum - 1); i++)
            columnLine = columnLine.substring(columnLine.indexOf(',') + 1);
        if (columnLine.indexOf(',') != -1) return columnLine.substring(0, columnLine.indexOf(','));
        else return columnLine;
    }
    public long startFileReading() throws IOException {

        long startTime = System.nanoTime();

        columnInfo = new TreeMap<>();
        RandomAccessFile csvRandomAccessFile = new RandomAccessFile("airports.csv", "r");
        FileReader csvFileReader = new FileReader(csvRandomAccessFile.getFD());
        BufferedReader csvBufferedReader = new BufferedReader(csvFileReader);
        String line; long currentFilePointer = 0L;
        while ((line = csvBufferedReader.readLine()) != null) {
            columnInfo.put(getColumnValue(line), currentFilePointer);
            currentFilePointer = csvRandomAccessFile.getFilePointer();
        }

        long endTime = System.nanoTime();

        return (endTime - startTime) / 1000000;
    }

    public int startSearch(String queryString) throws IOException {

        long startSearchTime = System.nanoTime();

        List<String> foundValues = columnInfo.keySet().stream()
                .filter(str -> str.replace("\"", "").toLowerCase()
                        .startsWith(queryString)).collect(Collectors.toList());

        long endSearchTime = System.nanoTime();

        long startOutputTime = System.nanoTime();

        RandomAccessFile csvRandomAccessFile = new RandomAccessFile("airports.csv", "r");
        FileReader csvFileReader = new FileReader(csvRandomAccessFile.getFD());
        BufferedReader csvBufferedReader = new BufferedReader(csvFileReader);
        PrintWriter output = new PrintWriter(System.out);
        foundValues.forEach(str -> {
            try {
                csvRandomAccessFile.seek(columnInfo.get(str));
                output.println(str + "[" + csvBufferedReader.readLine() + "]");
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
