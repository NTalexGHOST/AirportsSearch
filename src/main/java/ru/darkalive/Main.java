package ru.darkalive;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {

        String queryString;
        int resultsCount;
        byte columnNum;
        try {
            columnNum = Byte.parseByte(args[0]);
            if (columnNum < 1 || columnNum > 14) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            System.out.println("Ошибка во введенном аргументе");
            return;
        }
        AirportsSearch airportsSearch = new AirportsSearch(columnNum);
        try {
            System.out.println("Затраченное на обработку файла время - " + airportsSearch.startFileReading() + " мс\r\n");
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла, пожалуйста, перезапустите приложение\r\n" + e.getMessage());
            return;
        }

        while (true) {

            System.out.print("Введите строку: ");
            InputStreamReader queryStreamReader = new InputStreamReader(System.in);
            BufferedReader queryBufferedReader = new BufferedReader(queryStreamReader);

            try {
                queryString = queryBufferedReader.readLine().toLowerCase();
            } catch (IOException e) {
                System.out.println("Ошибка во введенном значении\r\n" + e.getMessage() + "\r\n");
                continue;
            }
            if (queryString.equals("!quit")) break;

            try {
                resultsCount = airportsSearch.startSearch(queryString);
            } catch (IOException e) {
                System.out.println("Произошла ошибка во время поиска\r\n" + e.getMessage() + "\r\n");
                continue;
            }

            System.out.println("Количество найденных строк - " + resultsCount + "; " +
                    "затраченное на поиск время - " + airportsSearch.getSearchTime() + " мс\r\n");
        }
    }
}