package ru.darkalive;

import java.io.*;

public class AirportsSearch {

    private String queryString;
    private byte columnNum;
    private long searchTime;

    public AirportsSearch(String queryString, byte columnNum) {

        this.queryString = queryString.toLowerCase();
        this.columnNum = columnNum;
    }

    public long getSearchTime() {
        return searchTime;
    }

    public int startSearch() throws IOException {

        int resultsCount = 0;
        long startTime = System.nanoTime();

        FileReader csvFileReader = new FileReader("airports.csv");
        BufferedReader csvBufferedReader = new BufferedReader(csvFileReader);
        String parsedLine;
        while ((parsedLine = csvBufferedReader.readLine()) != null) {
            String foundValue = parsedLine.split(",")[columnNum - 1].replace("\"", "").toLowerCase();
            if (foundValue.startsWith(queryString)) {
                System.out.println(foundValue + "[" + parsedLine + "]");
                resultsCount++;
            }
        }

        long endTime = System.nanoTime();
        searchTime = (endTime - startTime) / 1000000;

        return resultsCount;
    }
}
