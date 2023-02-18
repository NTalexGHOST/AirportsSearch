package ru.darkalive;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {

        String queryString;
        int resultsCount;
        AirportsSearch airportsSearch = new AirportsSearch(Byte.parseByte(args[0]));
        try {
            System.out.println("Затраченное время на обработку файла - " + airportsSearch.startFileReading() + " мс");
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла, пожалуйста, перезапустите приложение\n" + e.getMessage() + "\n");
            return;
        }

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

            try {
                resultsCount = airportsSearch.startSearch(queryString);
            } catch (IOException e) {
                System.out.println("Ошибка!!!\n" + e.getMessage() + "\n");
                continue;
            }

            System.out.println("Количество найденных строк - " + resultsCount + "; " +
                    "затраченное на поиск время - " + airportsSearch.getSearchTime() + " мс\n");
        }
    }
}