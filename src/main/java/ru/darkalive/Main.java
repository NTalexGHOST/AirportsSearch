package ru.darkalive;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {

        String queryString;
        int resultsCount;

        while (true) {

            System.out.print("Введите строку: ");
            InputStreamReader queryStreamReader = new InputStreamReader(System.in);
            BufferedReader queryBufferedReader = new BufferedReader(queryStreamReader);

            try {
                queryString = queryBufferedReader.readLine();
            } catch (IOException e) {
                System.out.println("Ошибка!!!\n" + e.getMessage() + "\n");
                continue;
            }
            if (queryString.equals("!quit")) break;

            AirportsSearch airportsSearch = new AirportsSearch(queryString, Byte.parseByte(args[0]));
            try {
                resultsCount = airportsSearch.startSearch();
            } catch (IOException e) {
                System.out.println("Ошибка!!!\n" + e.getMessage() + "\n");
                continue;
            }

            System.out.println("Количество найденных строк - " + resultsCount + "; " +
                    "затраченное на поиск время - " + airportsSearch.getSearchTime() + " мс\n");
        }
    }
}