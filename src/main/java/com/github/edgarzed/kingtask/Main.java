package com.github.edgarzed.kingtask;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        int size = readInt();

        int[][] matrix = new int[size][size];
        long[][] computedMatrix = new long[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[j][i] = readInt();
            }
        }

        int lastRequestType = 2;
        int requestNum = readInt();
        for (int i = 0; i < requestNum; i++) {
            int requestType = readInt();
            if (requestType == 1) {
                if (lastRequestType == 2) {
                    computeMatrix(size, matrix, computedMatrix);
                }
                int x1 = readInt();
                int y1 = readInt();
                int x2 = readInt();
                int y2 = readInt();
                System.out.println(calculateArea(computedMatrix, x1, y1, x2, y2));
            } else if (requestType == 2) {
                int x = readInt();
                int y = readInt();
                int value = readInt();
                matrix[y][x] = value;
            }
            lastRequestType = requestType;
        }
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

    private static long calculateArea(long[][] computedMatrix, int x1, int y1, int x2, int y2) {
        long result;
        if (x1 == 0 && y1 == 0) {
            result = computedMatrix[x2][y2];
        } else if (x1 == 0) {
            result = computedMatrix[x2][y1 + y2] - computedMatrix[x2][y1 - 1];
        } else if (y1 == 0) {
            result = computedMatrix[x1 + x2][y2] - computedMatrix[x1 - 1][y2];
        } else {
            result = computedMatrix[x1 - 1][y1 - 1] + computedMatrix[x2][y2] - computedMatrix[x2][y1 - 1] - computedMatrix[x1 - 1][y2];
        }
        return result;
    }

    private static void computeMatrix(int size, int[][] matrix, long[][] computedMatrix) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i > 0 && j > 0) {
                    computedMatrix[i][j] = matrix[i][j] + computedMatrix[i - 1][j] + computedMatrix[i][j - 1] - computedMatrix[i - 1][j - 1];
                } else if (i > 0) {
                    computedMatrix[i][j] = matrix[i][j] + computedMatrix[i - 1][j];
                } else if (j > 0) {
                    computedMatrix[i][j] = matrix[i][j] + computedMatrix[i][j - 1];
                } else {
                    computedMatrix[i][j] = matrix[i][j];
                }
            }
        }
    }
}