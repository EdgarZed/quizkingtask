package com.github.edgarzed.kingtask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        int size = Integer.parseInt(in.readLine());

        int[][] matrix = new int[size][size];
        long[][] computedMatrix = new long[size][size];


        for (int i = 0; i < size; i++) {
            String[] values = in.readLine().split(" ");
            for (int j = 0; j < values.length; j++) {
                matrix[i][j] = Integer.parseInt(values[j]);
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
        int requestNum = Integer.parseInt(in.readLine());
        for (int i = 0; i < requestNum; i++) {
            String[] requestParams = in.readLine().split(" ");
            int requestType = Integer.parseInt(requestParams[0]);
            if (requestType == 1) {
                if (lastRequestType == 2) {
                    computeMatrix(size, matrix, computedMatrix);
                }
                int x1 = Integer.parseInt(requestParams[2]);
                int y1 = Integer.parseInt(requestParams[1]);
                int x2 = Integer.parseInt(requestParams[4]);
                int y2 = Integer.parseInt(requestParams[3]);
                System.out.println(calculateArea(computedMatrix, x1, y1, x2, y2));
            } else if (requestType == 2) {
                int x = Integer.parseInt(requestParams[2]);
                int y = Integer.parseInt(requestParams[1]);
                int value = Integer.parseInt(requestParams[3]);
                matrix[y][x] = value;
            }
            lastRequestType = requestType;
        }
    }

    /*private static int readInt() throws IOException {
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
    }*/

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