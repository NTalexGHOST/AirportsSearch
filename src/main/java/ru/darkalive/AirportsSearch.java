package ru.darkalive;

import java.io.*;
import java.util.SortedMap;
import java.util.TreeMap;

public class AirportsSearch {

    private byte columnNum;
    private long searchTime;
    private SortedMap<Long, String> columnInfo;

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
            columnInfo.put(currentFilePointer, line.split(",")[columnNum - 1].replace("\"", "").toLowerCase());
            System.out.println(columnInfo.get(columnInfo.lastKey()));
            currentFilePointer = csvRandomAccessFile.getFilePointer();
        }
        csvRandomAccessFile.close();

        long endTime = System.nanoTime();

        return (endTime - startTime) / 1000000;
    }

    public int startSearch(String queryString) throws IOException {

        int resultsCount = 0;
        long startTime = System.nanoTime();



        long endTime = System.nanoTime();
        searchTime = (endTime - startTime) / 1000000;

        return resultsCount;
    }
}
