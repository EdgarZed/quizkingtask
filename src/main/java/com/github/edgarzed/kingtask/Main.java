package com.github.edgarzed.kingtask;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws Exception {

        int size = readInt();

        int[][] matrix = new int[size][size];
        long[][] computedMatrix = new long[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[j][i] = readInt();
            }
        }

        /*computeMatrix(size, matrix, computedMatrix);

        for (int i = 0; i < size; i++) {
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
            } else if (requestType == 2){
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

    /*s(lowerRow, rightColumn) - s(upperRow - 1, rightColumn) - s(lowerRow, leftColumn - 1) + s(upperRow - 1, leftColumn - 1)
     * (x1,y1) [top-left coordinate] and (x2,y2) [bottom-right coordinate]
     * */
    private static long calculateArea(long[][] computedMatrix, int x1, int y1, int x2, int y2) {
        long a = computedMatrix[x1][y1];
        long b = computedMatrix[x2-x1][y2];
        long c = computedMatrix[x2][y2-y1];
        long d = computedMatrix[x2][y2];

        return d+a-b-c;
    }

    private static void computeMatrix(int size, int[][] matrix, long[][] computedMatrix) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                /*computedMatrix[i + 1][j + 1] = matrix[i][j] +
                        computedMatrix[i + 1][j] +
                        computedMatrix[i][j + 1] -
                        computedMatrix[i][j];*/
                /*computedMatrix[i][j] = matrix[i][j] +
                        computedMatrix[i - 1][j] +
                        computedMatrix[i][j - 1] -
                        computedMatrix[i-1][j-1];*/
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