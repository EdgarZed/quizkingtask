package com.github.edgarzed.kingtask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int size = Integer.parseInt(in.readLine());
        String[] matrix = new String[size];
        for (int i = 0; i < size; i++) {
            matrix[i] = in.readLine();
        }
        int requestNum = Integer.parseInt(in.readLine());
        for (int i = 0; i < requestNum; i++) {
            String[] requestParams = in.readLine().split(" ");
            if (requestParams.length == 5) {
                sum(matrix, requestParams);
            } else {
                modify(matrix, requestParams);
            }
        }
    }

    private static void sum(String[] matrix, String[] requestParams) {
        int x1 = Integer.parseInt(requestParams[2]);
        int y1 = Integer.parseInt(requestParams[1]);
        int x2 = Integer.parseInt(requestParams[4]);
        int y2 = Integer.parseInt(requestParams[3]);

        long result = 0;
        for (; x1 <= x2; x1++) {
            result += sumLine(matrix[x1].split(" "), y1, y2);
        }
        System.out.println(result);
    }

    private static long sumLine(String[] line, int y1, int y2) {
        long result = 0;
        for (; y1 <= y2; y1++) {
            result += Integer.parseInt(line[y1]);
        }
        return result;
    }

    private static void modify(String[] matrix, String[] requestParams) {
        int x = Integer.parseInt(requestParams[2]);
        int y = Integer.parseInt(requestParams[1]);
        String value = requestParams[3];
        String[] newLine = matrix[x].split(" ");
        newLine[y] = value;
        matrix[x] = String.join(" ", newLine);
    }
}