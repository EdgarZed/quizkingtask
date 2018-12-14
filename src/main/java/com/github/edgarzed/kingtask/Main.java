package com.github.edgarzed.kingtask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) throws Exception {
//        ExecutorService executorService = Executors.newCachedThreadPool();
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        List<Callable<Long>> todoSum = new ArrayList<>();

        int size = readInt();
        int[][] matrix = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = readInt();
            }
        }
        int requestNum = readInt();
        for (int i = 0; i < requestNum; i++) {
            int requestType = readInt();
            if (requestType == 1) {
                int[] requestParams = new int[4];
                for (int j = 0; j < 4; j++) {
                    requestParams[j] = readInt();
                }
                todoSum.add(new SumTask(matrix, requestParams));
            } else {
                executorService.invokeAll(todoSum);
                todoSum.clear();

                int y = readInt();
                int x = readInt();
                int value = readInt();
                matrix[x][y] = value;
            }
        }
        executorService.invokeAll(todoSum);
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

    private static class SumTask implements Callable<Long> {

        private int[][] matrix;
        private int[] requestParams;

        public SumTask(int[][] matrix, int[] requestParams) {
            this.matrix = matrix;
            this.requestParams = requestParams;
        }

        @Override
        public Long call() throws Exception {
            int x1 = requestParams[1];
            int y1 = requestParams[0];
            int x2 = requestParams[3];
            int y2 = requestParams[2];
            long result = 0;
            for (; x1 <= x2; x1++) {
                result += sumLine(matrix[x1], y1, y2);
            }
            System.out.println(result);
            return result;
        }

        private static long sumLine(int[] line, int y1, int y2) {
            long result = 0;
            for (; y1 <= y2; y1++) {
                result += line[y1];
            }
            return result;
        }

    }
}