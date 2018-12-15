package com.github.edgarzed.kingtask;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newCachedThreadPool();
        ArrayList<Future<ArrayList<Long>>> futures = new ArrayList<>();
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
        int[][] requests = new int[requestsAmt][5];
        int lastModifyRequest = 0;
        for (int i = 0; i < requestsAmt; i++) {
            fillRequestData(requests[i]);
            if (requests[i][0] == 2) {
                SumTask sumTask = new SumTask(matrix, requests, lastModifyRequest, i);
                Future<ArrayList<Long>> future = executorService.submit(sumTask);
                futures.add(future);
                int[][] modifiedMatrix = new int[size][size];
                //??? requests corners
                for (int j = 0; j < size; j++) {
                    System.arraycopy(matrix[j], 0, modifiedMatrix[j], 0, size);
                }
                modifiedMatrix[requests[i][1]][requests[i][2]] = requests[i][3];
                matrix = modifiedMatrix;
                lastModifyRequest = i+1;
            }
        }
        SumTask sumTask = new SumTask(matrix, requests, lastModifyRequest, requestsAmt);
        Future<ArrayList<Long>> future = executorService.submit(sumTask);
        futures.add(future);
        for (Future<ArrayList<Long>> sumFuture : futures) {
            ArrayList<Long> results = sumFuture.get();
            for (long result : results) {
                System.out.println(result);
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

    private static class SumTask implements Callable<ArrayList<Long>> {

        private int[][] matrix;
        private int[][] requests;
        private int startRequestIndex;
        private int endRequestIndex;

        private SumTask(int[][] matrix, int[][] requests, int startRequestIndex, int endRequestIndex) {
            this.matrix = matrix;
            this.requests = requests;
            this.startRequestIndex = startRequestIndex;
            this.endRequestIndex = endRequestIndex;
        }

        @Override
        public ArrayList<Long> call() {
            ArrayList<Long> results = new ArrayList<>(endRequestIndex-startRequestIndex+1);
            for (int i = startRequestIndex; i < endRequestIndex; i++) {
                results.add(sum(matrix, requests[i]));
            }
            return results;
        }

        private long sum(int[][] matrix, int[] requestData) {
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