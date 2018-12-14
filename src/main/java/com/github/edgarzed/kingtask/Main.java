package com.github.edgarzed.kingtask;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        int size = readInt();
        int[][] matrix = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[j][i] = readInt();
            }
        }
        long[][] computedMatrix = computeSATMatrix(matrix);

        /*for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(matrix[j][i] + " ");
            }
            System.out.println();
        }
        System.out.println("***");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(computedMatrix[j][i] + " ");
            }
            System.out.println();
        }*/

        int[][] requests = getRequests();
        int lastRequestType = 1;
        for (int i = 0; i < requests.length; i++) {
            if (requests[i][0] == 1) {
                if (lastRequestType == 2) {
                    computedMatrix = computeSATMatrix(matrix);
                }
                System.out.println(calculateArea(computedMatrix, requests[i][1], requests[i][2], requests[i][3], requests[i][4]));
            } else {
                matrix[requests[i][2]][requests[i][1]] = requests[i][3];
            }
            lastRequestType = requests[i][0];
        }
    }

    private static int[][] getRequests() throws IOException {
        int[][] requests = new int[readInt()][5];
        for (int i = 0; i < requests.length; i++) {
            requests[i][0] = readInt();
            if (requests[i][0] == 1) {
                for (int j = 1; j < 5; j++) {
                    requests[i][j] = readInt();
                }
            } else {
                for (int j = 1; j < 4; j++) {
                    requests[i][j] = readInt();
                }
            }
        }
        return requests;
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

    static long calculateArea(long[][] computedMatrix, int x1,
                              int y1, int x2, int y2) {

        long res = computedMatrix[x2][y2];

        if (x1 > 0) {
            res = res - computedMatrix[x1 - 1][y2];
        }
        if (y1 > 0) {
            res = res - computedMatrix[x2][y1 - 1];
        }
        if (x1 > 0 && y1 > 0) {
            res = res + computedMatrix[x1 - 1][y1 - 1];
        }

        return res;
    }

    private static long[][] computeSATMatrix(int[][] matrix) {
        int rowSize = matrix.length;
        int colSize = matrix[0].length;
        long[][] result = new long[rowSize][colSize];
        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < colSize; j++) {
                result[i][j] = getSubmatrixSum(i, j, matrix);
            }
        }
        return result;
    }

    private static long getSubmatrixSum(int row, int col, int[][] matrix) {
        long sum = 0;
        for (int i = 0; i <= row; i++) {
            for (int j = 0; j <= col; j++) {
                sum += matrix[i][j];
            }
        }
        return sum;
    }
}