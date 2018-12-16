package com.github.edgarzed.kingtask;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {
    private static final BlockingQueue<int[]> QUEUE = new LinkedBlockingQueue<>();

    public static void main(String[] args) throws Exception {
        int size = readInt();
        int[][] matrix = new int[size][size];
        long[][] sumMatrix = new long[size][size];
        fillMatrixAndSum(matrix, sumMatrix);

        int requestsAmt = readInt();
        if (requestsAmt == 0) {
            return;
        }

        Thread workerThread = new Thread(new LittleWorker(requestsAmt, matrix, sumMatrix));
        workerThread.start();

        for (int i = 0; i < requestsAmt; i++) {
            int[] request = new int[5];
            fillRequestData(request);
            QUEUE.add(request);
        }

        workerThread.join();
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
    private static class LittleWorker implements Runnable {

        private int requestsAmt;
        private int[][] matrix;
        private long[][] sumMatrix;

        private LittleWorker(int requestsAmt, int[][] matrix, long[][] sumMatrix) {
            this.requestsAmt = requestsAmt;
            this.matrix = matrix;
            this.sumMatrix = sumMatrix;
        }

        @Override
        public void run() {
            try {
                for (int i = 0; i < requestsAmt; i++) {
                    int[] requestData = QUEUE.take();
                    if (requestData[0] == 1) {
                        System.out.println(calculateSum(sumMatrix, requestData));
                    } else {
                        int y = requestData[1];
                        int x = requestData[2];
                        matrix[x][y] = requestData[3];
                        for (int j = y; j < matrix.length; j++) {
                            int value = matrix[x][j];
                            if (j > 0) {
                                sumMatrix[x][j] = sumMatrix[x][j - 1] + value;
                            } else {
                                sumMatrix[x][j] = value;
                            }
                        }
                    }
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}