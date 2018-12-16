package com.github.edgarzed.kingtask;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws Exception {
        int size = readInt();
        int[][] matrix = new int[size][size];
        long[][] sumMatrix = new long[size][size];
        fillMatrixAndSum(matrix, sumMatrix);

        int requestsAmt = readInt();
        if (requestsAmt == 0) {
            return;
        }
        int[][] requests = new int[requestsAmt][5];
        int lastSumRequest = getLastSumRequestAndFillRequests(requests);

        for (int i = 0; i < lastSumRequest; i++) {
            if (requests[i][0] == 1) {
                System.out.println(calculateSum(sumMatrix, requests[i]));
            } else {
                int y = requests[i][1];
                int x = requests[i][2];
                matrix[x][y] = requests[i][3];
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

    private static int getLastSumRequestAndFillRequests(int[][] requests) throws IOException {
        int lastSumRequest = 0;
        for (int i = 0; i < requests.length; i++) {
            requests[i][0] = readInt();
            if (requests[i][0] == 1) {
                for (int j = 1; j < 5; j++) {
                    requests[i][j] = readInt();
                }
                lastSumRequest = i;
            } else {
                for (int j = 1; j < 4; j++) {
                    requests[i][j] = readInt();
                }
            }
        }
        return lastSumRequest > 0 ? lastSumRequest + 1 : 1;
    }
    private static void fillMatrixAndSum(int[][] matrix, long[][] sumMatrix) throws IOException {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                int value = readInt();
                matrix[i][j] = value;
                if (j > 0) {
                    sumMatrix[i][j] = sumMatrix[i][j - 1] + value;
                } else {
                    sumMatrix[i][j] = value;
                }
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

    private static long calculateSum(long[][] sumMatrix, int[] request) {
        int y1 = request[1];
        int x1 = request[2];
        int y2 = request[3];
        int x2 = request[4];

        long res = 0;
        for (int i = x1; i <= x2; i++) {
            if (y1 > 0) {
                res += sumMatrix[i][y2] - sumMatrix[i][y1 - 1];
            } else {
                res += sumMatrix[i][y2];
            }
        }
        return res;
    }
}