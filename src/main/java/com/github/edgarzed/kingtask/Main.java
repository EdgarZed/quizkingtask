package com.github.edgarzed.kingtask;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main {
    private static final BlockingQueue<int[]> QUEUE = new ArrayBlockingQueue<>(50);

    public static void main(String[] args) throws Exception {

        int size = readInt();
        int[][] matrix = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[j][i] = readInt();
            }
        }

        int requestsAmt = readInt();
        if (requestsAmt == 0) {
            return;
        }
        Thread workerThread = new Thread(new LittleWorker(requestsAmt, matrix));
        workerThread.start();

        int[][] requests = new int[requestsAmt][5];
        for (int i = 0; i < requestsAmt; i++) {
            fillRequestData(requests[i]);
            QUEUE.add(requests[i]);
        }

        workerThread.join();
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

    private static int readInt() throws Exception {
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

    private static class LittleWorker implements Runnable {

        private int requestsAmt;
        private int[][] matrix;

        private LittleWorker(int requestsAmt, int[][] matrix) {
            this.requestsAmt = requestsAmt;
            this.matrix = matrix;
        }

        @Override
        public void run() {
            for (int i = 0; i < requestsAmt; i++) {
                try {
                    int[] requestData = QUEUE.take();
                    if (requestData[0] == 1) {
                        System.out.println(sum(requestData));
                    } else if (requestData[0] == 2) {
                        matrix[requestData[1]][requestData[2]] = requestData[3];
                    }
                } catch (InterruptedException e) {
                    break;
                }
            }
        }

        private long sum(int[] requestData) {
            int x1 = requestData[1];
            int y1 = requestData[2];
            int x2 = requestData[3];
            int y2 = requestData[4];

            long result = 0;
            for (; x1 <= x2; x1++) {
                result += sumLine(matrix[x1], y1, y2);
            }
            return result;
        }

        private long sumLine(int[] line, int y1, int y2) {
            long result = 0;
            for (; y1 <= y2; y1++) {
                result += line[y1];
            }
            return result;
        }

    }
}