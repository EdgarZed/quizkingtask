package com.github.edgarzed.kingtask;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        int size = readInt();
        int[][] matrix = new int[size][size];
        long[][] satMatrix = new long[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[j][i] = readInt();
            }
        }
        Future<?> submit = executorService.submit(() -> computeSATMatrix(matrix, satMatrix));

        /*for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(matrix[j][i] + " ");
            }
            System.out.println();
        }
        System.out.println("***");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(satMatrix[j][i] + " ");
            }
            System.out.println();
        }*/

        int[][] requests = getRequests();
        while (!submit.isDone()) {
            Thread.sleep(1);
        }
        int lastRequestType = 1;
        for (int i = 0; i < requests.length; i++) {
            if (requests[i][0] == 1) {
                if (lastRequestType == 2) {
                    computeSATMatrix(matrix, satMatrix);
                }
                long result = calculateArea(satMatrix, requests[i][1], requests[i][2], requests[i][3], requests[i][4]);
                System.out.println(result);
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

    private static void computeSATMatrix(int[][] matrix, long[][] computedMatrix) {
        for (int x = 0; x < matrix.length; x++) {
            for (int y = 0; y < matrix.length; y++) {
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
}