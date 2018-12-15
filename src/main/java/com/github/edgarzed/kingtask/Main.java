package com.github.edgarzed.kingtask;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws Exception {
        int size = readInt();
        int[][] matrix = new int[size][size];
        long[][] sat = new long[size][size];
        fillMatrixAndSAT(matrix, sat);

        int requestsAmt = readInt();
        if (requestsAmt == 0) {
            return;
        }
        int[] request = new int[5];

        for (int i = 0; i < requestsAmt; i++) {
            fillRequestData(request);
            if (request[0] == 1) {
                System.out.println(calculateSum(sat, request));
            } else {
                int x = request[1];
                int y = request[2];
                matrix[x][y] = request[3];
                calcSat(matrix, sat, x, y);
            }
        }
    }

    private static void fillMatrixAndSAT(int[][] matrix, long[][] sat) throws IOException {
        for (int y = 0; y < matrix.length; y++) {
            for (int x = 0; x < matrix.length; x++) {
                matrix[x][y] = readInt();
                calcSatCell(matrix, sat, x, y);
            }
        }
    }

    private static void calcSat(int[][] matrix, long[][] sat, int startX, int startY) {
        for (int y = startY; y < matrix.length; y++) {
            for (int x = startX; x < matrix.length; x++) {
                calcSatCell(matrix, sat, x, y);
            }
        }
    }

    private static void calcSatCell(int[][] matrix, long[][] sat, int x, int y) {
        if (x > 0 && y > 0) {
            sat[x][y] = matrix[x][y] + sat[x - 1][y] + sat[x][y - 1] - sat[x - 1][y - 1];
        } else if (x > 0) {
            sat[x][y] = matrix[x][y] + sat[x - 1][y];
        } else if (y > 0) {
            sat[x][y] = matrix[x][y] + sat[x][y - 1];
        } else {
            sat[x][y] = matrix[x][y];
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

    private static long calculateSum(long[][] satMatrix, int[] request) {
        int x1 = request[1];
        int y1 = request[2];
        int x2 = request[3];
        int y2 = request[4];

        long res = satMatrix[x2][y2];

        if (x1 > 0) {
            res = res - satMatrix[x1 - 1][y2];
        }
        if (y1 > 0) {
            res = res - satMatrix[x2][y1 - 1];
        }
        if (x1 > 0 && y1 > 0) {
            res = res + satMatrix[x1 - 1][y1 - 1];
        }

        return res;
    }
}