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
        int requestNum = readInt();
        int[] requestParams = new int[5];
        for (int i = 0; i < requestNum; i++) {
            int requestType = readInt();
            if (requestType == 1) {
                for (int j = 0; j < 4; j++) {
                    requestParams[j] = readInt();
                }
                sum(matrix, requestParams);
            } else {
                for (int j = 0; j < 3; j++) {
                    requestParams[j] = readInt();
                }
                modify(matrix, requestParams);
            }
        }
    }

    private static void sum(int[][] matrix, int[] requestParams) {
        int x1 = requestParams[1];
        int y1 = requestParams[0];
        int x2 = requestParams[3];
        int y2 = requestParams[2];
        long result = 0;
        for (; x1 <= x2; x1++) {
            result += sumLine(matrix[x1], y1, y2);
        }
        System.out.println(result);
    }

    private static long sumLine(int[] line, int y1, int y2) {
        long result = 0;
        for (; y1 <= y2; y1++) {
            result += line[y1];
        }
        return result;
    }

    private static void modify(int[][] matrix, int[] requestParams) {
        int x = requestParams[1];
        int y = requestParams[0];
        int value = requestParams[2];
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