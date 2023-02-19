package ru.darkalive;

import org.apache.fontbox.ttf.BufferedRandomAccessFile;

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
        BufferedRandomAccessFile csvBufferedRandomAccessFile = new BufferedRandomAccessFile("airports.csv", "r", 2048);
        String currentLine; long currentFilePointer = 0L;
        while ((currentLine = csvBufferedRandomAccessFile.readLine()) != null) {
            columnInfo.put(getColumnValue(currentLine), currentFilePointer);
            currentFilePointer = csvBufferedRandomAccessFile.getFilePointer();
        }

        csvBufferedRandomAccessFile.close();
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

        BufferedRandomAccessFile csvBufferedRandomAccessFile = new BufferedRandomAccessFile("airports.csv", "r", 2048);
        PrintWriter output = new PrintWriter(System.out);
        foundValues.forEach(str -> {
            try {
                csvBufferedRandomAccessFile.seek(columnInfo.get(str));
                output.println(str + "[" + csvBufferedRandomAccessFile.readLine() + "]");
            } catch (IOException e) {
                System.out.println("Произошла ошибка во время поиска\r\n" + e.getMessage() + "\r\n");
            }
        } );

        output.flush();
        csvBufferedRandomAccessFile.close();
        long endOutputTime = System.nanoTime();
        System.out.println("\r\nЗатраченное на вывод найденных записей время - " + ((endOutputTime - startOutputTime) / 1000000) + " мс");
        searchTime = (endSearchTime - startSearchTime) / 1000000;

        return foundValues.size();
    }
}
