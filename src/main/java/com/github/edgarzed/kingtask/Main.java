package com.github.edgarzed.kingtask;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws Exception {
        int size = readInt();
        int[][] matrix = new int[size][size];
        long[][] satMatrix = new long[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[j][i] = readInt();
            }
        }

        computeSATMatrix(matrix, satMatrix);

        int requestsAmt = readInt();
        if (requestsAmt == 0) {
            return;
        }
        int[][] requests = new int[requestsAmt][5];
        int[] requestsMeta = getRequestsMetaAndFillMatrix(requests);

        if (requestsMeta[2] == 0 || requestsMeta[2] > 0 && requestsMeta[1] > requestsMeta[2] && (requestsMeta[1] / requestsMeta[2] > 100)) {
            int lastRequestType = 1;
            for (int i = 0; i < requestsMeta[0]; i++) {
                if (requests[i][0] == 1) {
                    if (lastRequestType == 2) {
                        computeSATMatrix(matrix, satMatrix);
                    }
                    long result = calculateArea(satMatrix, requests[i][1], requests[i][2], requests[i][3], requests[i][4]);
                    System.out.println(result);
                } else if (requests[i][0] == 2) {
                    matrix[requests[i][1]][requests[i][2]] = requests[i][3];
                }
                lastRequestType = requests[i][0];
            }
        } else {
            for (int i = 0; i < requestsMeta[0]; i++) {
                if (requests[i][0] == 1) {
                    System.out.println(sum(matrix, requests[i]));
                } else if (requests[i][0] == 2) {
                    matrix[requests[i][1]][requests[i][2]] = requests[i][3];
                }
            }
        }
    }

    private static int[] getRequestsMetaAndFillMatrix(int[][] requests) throws IOException {
        int[] requestsMeta = new int[3];
        int lastSumRequest = 0;
        int sumRequestsAmt = 0;
        int modifyRequestsAmt = 0;
        for (int i = 0; i < requests.length; i++) {
            requests[i][0] = readInt();
            if (requests[i][0] == 1) {
                for (int j = 1; j < 5; j++) {
                    requests[i][j] = readInt();
                }
                lastSumRequest = i;
                sumRequestsAmt++;
            } else {
                for (int j = 1; j < 4; j++) {
                    requests[i][j] = readInt();
                }
                modifyRequestsAmt++;
            }
        }
        requestsMeta[0] = lastSumRequest > 0 ? lastSumRequest + 1 : 1;
        requestsMeta[1] = sumRequestsAmt;
        requestsMeta[2] = modifyRequestsAmt;
        return requestsMeta;
    }

    private static long sum(int[][] matrix, int[] requestHistory) {
        int x1 = requestHistory[1];
        int y1 = requestHistory[2];
        int x2 = requestHistory[3];
        int y2 = requestHistory[4];

        long result = 0;
        for (; x1 <= x2; x1++) {
            result += sumLine(matrix[x1], y1, y2);
        }
        return result;
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

    private static long calculateArea(long[][] computedMatrix, int x1,
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