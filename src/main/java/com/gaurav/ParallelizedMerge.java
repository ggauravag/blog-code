package com.gaurav;

import java.util.concurrent.RecursiveAction;

public class ParallelizedMerge extends RecursiveAction {

    private int[] A;
    private int[] B;
    private int[] C;

    private int startA, endA, startB, endB, startC, endC;

    public ParallelizedMerge() {
    }

    public ParallelizedMerge(int[] a, int[] b, int[] c, int startA, int endA, int startB, int endB, int startC, int endC) {
        A = a;
        B = b;
        C = c;
        this.startA = startA;
        this.endA = endA;
        this.startB = startB;
        this.endB = endB;
        this.startC = startC;
        this.endC = endC;
    }


    /**
     * @param element
     * @param array
     * @param start
     * @param end
     * @return
     */
    public int binarySearch(int element, int[] array, int start, int end) {
        if (start > end) {
            return -1;
        }

        if (start == end) {
            return start;
        }

        int mid = (start + end + 1) / 2;
        while (start <= end) {
            if (mid > end || array[mid] == element) {
                break;
            } else if (array[mid] < element) {
                start = mid + 1;
            } else {
                end = mid - 1;
            }
            mid = (start + end + 1) / 2;
        }
        return mid;
    }

    public int linearSearch(int ele, int[] arr, int start, int end) {
        for (int i = start; i <= end; i++) {
            if (arr[i] >= ele) {
                return i;
            }
        }

        return end + 1;
    }

    @Override
    protected void compute() {
        int sizeA = endA - startA;
        int sizeB = endB - startB;

        int maxSize = sizeA > sizeB ? sizeA : sizeB;

        // Base case, if array is empty
        if (maxSize <= 0) return;

        if (B.length < endB || A.length < endA || endA < startA || endB < startB) {
            throw new IllegalArgumentException("");
        }

        // If A is bigger than B
        if (sizeA >= sizeB) {
            // If second array is empty, then copy all elements of A into C
            if (sizeB == 0) {
                System.arraycopy(A, startA, C, startC, endA - startA);
            } else {
                int r = (startA + endA) >> 1;
                int position = binarySearch(A[r], B, startB, endB - 1);
                int finalPosition = startC + (r - startA) + (position - startB);
                C[finalPosition] = A[r];

                invokeAll(new ParallelizedMerge(A, B, C, startA, r, startB, position, startC, finalPosition),
                        new ParallelizedMerge(A, B, C, r + 1, endA, position, endB, finalPosition + 1, endC));
            }
        } else {
            // If second array is empty, then copy all elements of B into C
            if (sizeA == 0) {
                System.arraycopy(B, startB, C, startC, endB - startB);
            } else {
                int r = (startB + endB) >> 1;
                int position = binarySearch(B[r], A, startA, endA - 1);
                int finalPosition = startC + (r - startB) + (position - startA);
                C[finalPosition] = B[r];

                invokeAll(new ParallelizedMerge(A, B, C, startA, position, startB, r, startC, finalPosition),
                        new ParallelizedMerge(A, B, C, position, endA, r + 1, endB, finalPosition + 1, endC));
            }
        }
    }
}
