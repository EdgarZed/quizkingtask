package com.github.edgarzed.kingtask;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws Exception {
        int size = readInt();
        int[][] matrix = new int[size][size];
        long[][] sumMatrix = new long[size][size + 1];
        fillMatrix(matrix);

        int requestsAmt = readInt();
        if (requestsAmt == 0) {
            return;
        }
        int[] request = new int[5];

        for (int i = 0; i < requestsAmt; i++) {
            fillRequestData(request);
            if (request[0] == 1) {
                System.out.println(calculateSum(matrix, sumMatrix, request));
            } else {
                int y = request[1];
                int x = request[2];
                matrix[x][y] = request[3];
                for (int j = y; j < size; j++) {
                    int value = matrix[x][j];
                    if (j > 0) {
                        sumMatrix[x][j] = sumMatrix[x][j - 1] + value;
                    } else {
                        sumMatrix[x][j] = value;
                    }
                }
            }
        }
    }

    private static void fillMatrix(int[][] matrix) throws IOException {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                matrix[i][j] = readInt();
            }
        }
    }

    private static void fillRequestData(int[] request) throws Exception {
        request[0] = readInt();
        if (request[0] == 1) {
            for (int j = 1; j < 5; j++) {
                request[j] = readInt();
            }
        } else {
            for (int j = 1; j < 4; j++) {
                request[j] = readInt();
            }
        }
    }

    private static int readInt() throws IOException {
        int result = 0;
        boolean digital = false;
        boolean negative = false;
        for (int ch; (ch = System.in.read()) != -1; ) {
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

    private static void calculateSumLine(int[][] matrix, long[][] sumMatrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if (j > 0) {
                    sumMatrix[i][j] = sumMatrix[i][j - 1] + matrix[i][j];
                } else {
                    sumMatrix[i][j] = matrix[i][j];
                }
            }
        }
    }

    private static long calculateSum(int[][] matrix, long[][] sumMatrix, int[] request) {
        int y1 = request[1];
        int x1 = request[2];
        int y2 = request[3];
        int x2 = request[4];

        long res = 0;
        for (int i = x1; i <= x2; i++) {
            if (sumMatrix[i][matrix.length] == 0) {
                calculateSumLine(matrix, sumMatrix);
                sumMatrix[i][matrix.length] = 1;
            }
            if (y1 > 0) {
                res += sumMatrix[i][y2] - sumMatrix[i][y1 - 1];
            } else {
                res += sumMatrix[i][y2];
            }
        }
        return res;
    }
}