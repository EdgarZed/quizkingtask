package com.github.edgarzed.kingtask;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws Exception {
        int size = readInt();
        int[][] matrix = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = readInt();
            }
        }

        int[][] requests = new int[readInt()][5];
        int lastSumRequest = getLastSumRequestAndFillMatrix(requests);
        long[] sumResults = new long[requests.length];

        for (int i = 0; i < lastSumRequest; i++) {
            if (requests[i][0] == 1) {
                sum(matrix, requests, i, sumResults);
                System.out.println(sumResults[i]);
            } else if (requests[i][0] == 2) {
                modify(matrix, requests[i]);
            }
        }
    }

    private static int getLastSumRequestAndFillMatrix(int[][] requests) throws IOException {
        int lastSumRequest = 0;
        for (int i = 0; i < requests.length; i++) {
            requests[i][0] = readInt();
            if (requests[i][0] == 1) {
                for (int j = 1; j < 5; j++) {
                    requests[i][j] = readInt();
                }
                lastSumRequest = i;
            } else {
                for (int j = 1; j < 4; j++) {
                    requests[i][j] = readInt();
                }
            }
        }
        return lastSumRequest > 0 ? lastSumRequest + 1 : 0;
    }

    private static void sum(int[][] matrix, int[][] requestHistory, int requestNum, long[] sumResults) {
        int x1 = requestHistory[requestNum][2];
        int y1 = requestHistory[requestNum][1];
        int x2 = requestHistory[requestNum][4];
        int y2 = requestHistory[requestNum][3];

        long result = 0;
        for (; x1 <= x2; x1++) {
            result += sumLine(matrix[x1], y1, y2);
        }
        sumResults[requestNum] = result;

    }

    private static long sumLine(int[] line, int y1, int y2) {
        long result = 0;
        for (; y1 <= y2; y1++) {
            result += line[y1];
        }
        return result;
    }

    private static void modify(int[][] matrix, int[] requestParams) {
        int x = requestParams[2];
        int y = requestParams[1];
        int value = requestParams[3];
        matrix[x][y] = value;
    }

    private static int readInt() throws IOException {
        int result = 0;
        boolean digital = false;
        boolean negative = false;
        for (int ch = 0; (ch = System.in.read()) != -1; ) {
            if (ch >= '0' && ch <= '9') {
                digital = true;
                result = result * 10 + ch - '0';
            } else if (ch == '-') {
                negative = true;
            } else if (digital) {
                break;
            }
        }
        return negative ? result * -1 : result;
    }
}