package com.gaurav;

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

public class ParallelMergeSort extends RecursiveAction {

    private int[] array;
    private int start, end;
    public static final int THRESHOLD = 1000;

    public ParallelMergeSort(int[] array) {
        this.array = array;
        this.start = 0;
        this.end = array.length;
    }

    /**
     * To merge two sub arrays into the internal final array sequentially
     *
     * @param left
     * @param right
     */
    private void mergeSequentially(int[] left, int[] right) {
        int i = 0, j = 0, k = start;
        while (i < left.length && j < right.length) {
            array[k++] = left[i] < right[j] ? left[i++] : right[j++];
        }

        while (i < left.length) {
            array[k++] = left[i++];
        }

        while (j < right.length) {
            array[k++] = right[j++];
        }
    }

    public void mergeSortSequentially() {
        // Base case
        if (end < 2) {
            return;
        }

        int mid = (start + end) >> 1;
        int[] left = Arrays.copyOfRange(array, 0, mid);
        int[] right = Arrays.copyOfRange(array, mid, end);

        ParallelMergeSort leftRecursion = new ParallelMergeSort(left);
        ParallelMergeSort rightRecursion = new ParallelMergeSort(right);

        leftRecursion.mergeSortSequentially();
        rightRecursion.mergeSortSequentially();

        mergeSequentially(left, right);
    }

    @Override
    protected void compute() {
        // Base case
        if (end < 2) {
            return;
        }

        // To improve performance
        if (end <= THRESHOLD) {
            mergeSortSequentially();
            return;
        }

        int mid = (start + end) >> 1;
        int[] left = Arrays.copyOfRange(array, 0, mid);
        int[] right = Arrays.copyOfRange(array, mid, end);
        invokeAll(new ParallelMergeSort(left), new ParallelMergeSort(right));

        mergeSequentially(left, right);
    }
}
