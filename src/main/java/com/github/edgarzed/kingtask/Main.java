package com.github.edgarzed.kingtask;

import java.io.IOException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws Exception {
        int size = readInt();
        int[][] matrix = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = readInt();
            }
        }
        int requestAmt = readInt();
        int[][] requestHistory = new int[requestAmt][5];
        long[] sumResults = new long[requestAmt];

        for (int i = 0; i < requestAmt; i++) {
            requestHistory[i][0] = readInt();
            if (requestHistory[i][0] == 1) {
                sum(matrix, requestHistory, i, sumResults);
                System.out.println(sumResults[i]);
            } else {
                modify(matrix, requestHistory[i]);
            }
        }
    }

    private static void sum(int[][] matrix, int[][] requestHistory, int requestNum, long[] sumResults) throws IOException {
        for (int j = 1; j < 5; j++) {
            requestHistory[requestNum][j] = readInt();
        }
        int x1 = requestHistory[requestNum][2];
        int y1 = requestHistory[requestNum][1];
        int x2 = requestHistory[requestNum][4];
        int y2 = requestHistory[requestNum][3];

        boolean sumResultExists = isSumResultExists(requestHistory, requestNum, sumResults);
        if (!sumResultExists) {
            long result = 0;
            for (; x1 <= x2; x1++) {
                result += sumLine(matrix[x1], y1, y2);
            }
            sumResults[requestNum] = result;
        }
    }

    private static boolean isSumResultExists(int[][] requestHistory, int requestNum, long[] sumResults) {
        boolean result = false;
        for (int j = requestNum - 1; j > 0; j--) {
            if (requestHistory[j][0] == 1) {
                if (Arrays.equals(requestHistory[requestNum], requestHistory[j])) {
                    sumResults[requestNum] = sumResults[j];
                    result = true;
                    break;
                }
            } else if (isMatrixModified(requestHistory[j], requestHistory[requestNum])) {
                break;
            }
        }
        return result;
    }

    private static long sumLine(int[] line, int y1, int y2) {
        long result = 0;
        for (; y1 <= y2; y1++) {
            result += line[y1];
        }
        return result;
    }

    private static void modify(int[][] matrix, int[] requestParams) throws IOException {
        for (int j = 1; j < 4; j++) {
            requestParams[j] = readInt();
        }
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

    private static boolean isMatrixModified(int[] modifyParams, int[] sumParams) {
        int x1 = sumParams[2];
        int y1 = sumParams[1];
        int x2 = sumParams[4];
        int y2 = sumParams[3];
        int x = modifyParams[2];
        int y = modifyParams[1];
        return x1 <= x && x <= x2 && y1 <= y && y <= y2;
    }
}