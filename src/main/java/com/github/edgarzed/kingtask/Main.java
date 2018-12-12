package com.github.edgarzed.kingtask;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws Exception {

        int size = readInt();

        int[][] matrix = new int[size][size];
        long[][] computedMatrix = new long[size + 1][size + 1];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[j][i] = readInt();
            }
        }
        computeMatrix(size, matrix, computedMatrix);

        int requestNum = readInt();
        int[] requestParams = new int[4];
        for (int i = 0; i < requestNum; i++) {
            int requestType = readInt();
            if (requestType == 1) {
                int y1 = readInt();
                int x1 = readInt();
                int y2 = readInt();
                int x2 = readInt();
                System.out.println(calculateArea(computedMatrix, x1, y1, x2, y2));
            } else {
                for (int j = 0; j < 3; j++) {
                    requestParams[j] = readInt();
                }
                modify(matrix, requestParams);
                computeMatrix(size, matrix, computedMatrix);
            }
        }
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

    /*s(lowerRow, rightColumn) - s(upperRow - 1, rightColumn) - s(lowerRow, leftColumn - 1) + s(upperRow - 1, leftColumn - 1)
     * (x1,y1) [top-left coordinate] and (x2,y2) [bottom-right coordinate]
     * */
    private static long calculateArea(long[][] computedMatrix, int x1, int y1, int x2, int y2) {
        long A = computedMatrix[y1][x1];
        long B = computedMatrix[y1][x2];
        long C = computedMatrix[y2][x2];
        long D = computedMatrix[y2][x1];

        return A + C - B - D;
    }

    private static void computeMatrix(int size, int[][] matrix, long[][] computedMatrix) {
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                computedMatrix[y + 1][x + 1] = matrix[y][x] +
                        computedMatrix[y + 1][x] +
                        computedMatrix[y][x + 1] -
                        computedMatrix[y][x];
            }
        }
    }
}