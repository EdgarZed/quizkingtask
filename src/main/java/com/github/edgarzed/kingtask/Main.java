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
        int requestNum = readInt();
        int[][] requestHistory = new int[requestNum][5];
        long[] sumResults = new long[requestNum];
        int lastModifyRequest = 0;
        boolean foundSumResult = false;

        for (int i = 0; i < requestNum; i++) {
            requestHistory[i][0] = readInt();
            if (requestHistory[i][0] == 1) {
                for (int j = 1; j < 5; j++) {
                    requestHistory[i][j] = readInt();
                }
                for (int j = i - 1; j > lastModifyRequest; j--) {
                    if (Arrays.equals(requestHistory[i], requestHistory[j])) {
                        sumResults[i] = sumResults[j];
                        foundSumResult = true;
                        break;
                    }
                }
                if (!foundSumResult) {
                    sumResults[i] = sum(matrix, requestHistory[i]);
                }
                foundSumResult = false;
                System.out.println(sumResults[i]);
            } else {
                for (int j = 1; j < 4; j++) {
                    requestHistory[i][j] = readInt();
                }
                modify(matrix, requestHistory[i]);
                lastModifyRequest = i;
            }
        }
    }

    private static long sum(int[][] matrix, int[] requestParams) {
        int x1 = requestParams[2];
        int y1 = requestParams[1];
        int x2 = requestParams[4];
        int y2 = requestParams[3];
        long result = 0;
        for (; x1 <= x2; x1++) {
            result += sumLine(matrix[x1], y1, y2);
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

    private static boolean contains(int[] modifyParams, int[] sumParams) {
        int x1 = sumParams[2];
        int y1 = sumParams[1];
        int x2 = sumParams[4];
        int y2 = sumParams[3];
        int x = modifyParams[2];
        int y = modifyParams[1];
        return x1 <= x && x <= x2 && y1 <= y && y <= y2;
    }
}