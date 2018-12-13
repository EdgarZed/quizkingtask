package com.github.edgarzed.kingtask;

import java.io.IOException;

public class Main {

    public static final int THRESHOLD = 100;

    public static void main(String[] args) throws IOException {

        int size = readInt();
        int[][] matrix = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = readInt();
            }
        }

        int requestNum = readInt();
        if (requestNum < THRESHOLD) {
            linearProc(matrix, requestNum);
        } else {
            satProc(matrix, requestNum, size);
        }
    }

    private static void linearProc(int[][] matrix, int requestNum) throws IOException {
        int requestType;
        int x1;
        int y1;
        int x2;
        int y2;
        int value;

        for (int i = 0; i < requestNum; i++) {
            requestType = readInt();
            if (requestType == 1) {
                y1 = readInt();
                x1 = readInt();
                y2 = readInt();
                x2 = readInt();
                sum(matrix, x1, y1, x2, y2);
            } else {
                y1 = readInt();
                x1 = readInt();
                value = readInt();
                matrix[x1][y1] = value;
            }
        }
    }

    private static void satProc(int[][] matrix, int requestNum, int size) throws IOException {
        long[][] computedMatrix = new long[size][size];
        int requestType;
        int x1;
        int y1;
        int x2;
        int y2;
        int value;
        int lastRequestType = 2;
        for (int i = 0; i < requestNum; i++) {
            requestType = readInt();
            if (requestType == 1) {
                if (lastRequestType == 2) {
                    computeMatrix(size, matrix, computedMatrix);
                }
                y1 = readInt();
                x1 = readInt();
                y2 = readInt();
                x2 = readInt();
                System.out.println(calculateArea(computedMatrix, x1, y1, x2, y2));
            } else if (requestType == 2) {
                y1 = readInt();
                x1 = readInt();
                value = readInt();
                matrix[x1][y1] = value;
            }
            lastRequestType = requestType;
        }
    }

    private static void sum(int[][] matrix, int x1, int y1, int x2, int y2) {
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

    private static void computeMatrix(int size, int[][] matrix, long[][] computedMatrix) {
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (x > 0 && y > 0) {
                    computedMatrix[x][y] = matrix[x][y] + computedMatrix[x - 1][y] + computedMatrix[x][y - 1] - computedMatrix[x - 1][y - 1];
                } else if (x > 0) {
                    computedMatrix[x][y] = matrix[x][y] + computedMatrix[x - 1][y];
                } else if (y > 0) {
                    computedMatrix[x][y] = matrix[x][y] + computedMatrix[x][y - 1];
                } else {
                    computedMatrix[x][y] = matrix[x][y];
                }
            }
        }
    }

    // A O(1) time function to compute sum
    // of submatrix between (x1, y1) and
    // (x2, y2) using computedMatrix[][] which is
    // built by the computeMatrix function
    static long calculateArea(long[][] computedMatrix, int x1,
                              int y1, int x2, int y2) {

        // result is now sum of elements
        // between (0, 0) and (x2, y2)
        long res = computedMatrix[x2][y2];

        // Remove elements between (0, 0)
        // and (x1-1, y2)
        if (x1 > 0) {
            res = res - computedMatrix[x1 - 1][y2];
        }

        // Remove elements between (0, 0)
        // and (x2, y1-1)
        if (y1 > 0) {
            res = res - computedMatrix[x2][y1 - 1];
        }

        // Add computedMatrix[x1-1][y1-1] as elements
        // between (0, 0) and (x1-1, y1-1)
        // are subtracted twice
        if (x1 > 0 && y1 > 0) {
            res = res + computedMatrix[x1 - 1][y1 - 1];
        }

        return res;
    }
}