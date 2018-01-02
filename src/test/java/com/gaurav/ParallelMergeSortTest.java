package com.gaurav;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class ParallelMergeSortTest {

    static class TestUtils {
        public static void testArrayIsSorted(int[] arr) {
            int i = 0;
            for (; i < arr.length; i++) {
                if (i != 0) {
                    if (arr[i] < arr[i - 1]) {
                        throw new IllegalStateException("Array is not sorted, at " + i + " index");
                    }
                }
            }
        }
    }


    @Test
    public void testMergeSort() {
        int[] inputArray = generateArray(1000);
        int[] testCopy = Arrays.copyOfRange(inputArray, 0, 1000);

        Assert.assertArrayEquals(inputArray, testCopy);

        Arrays.sort(inputArray);
        TestUtils.testArrayIsSorted(inputArray);

        ParallelMergeSort mergeSort = new ParallelMergeSort(testCopy);
        mergeSort.compute();
        TestUtils.testArrayIsSorted(testCopy);

        Assert.assertArrayEquals("Sorted Arrays must match", inputArray, testCopy);
    }

    @Test
    public void testMergeSortOn10M() {
        int testSize = 10000000;
        int NUM_RUNS = 5;
        int[] input = generateArray(testSize);

        long sequentialTime = 0, parallelTime = 0;
        for (int i = 0; i < NUM_RUNS; i++) {
            int[] seqCopy = Arrays.copyOfRange(input, 0, testSize);
            ParallelMergeSort seqMergeSort = new ParallelMergeSort(seqCopy);
            long start = System.currentTimeMillis();
            seqMergeSort.mergeSortSequentially();
            sequentialTime += (System.currentTimeMillis() - start);

            int[] parallelCopy = Arrays.copyOfRange(input, 0, testSize);
            ParallelMergeSort parallelMergeSort = new ParallelMergeSort(parallelCopy);
            start = System.currentTimeMillis();
            parallelMergeSort.compute();
            parallelTime += (System.currentTimeMillis() - start);
        }

        System.out.println(String.format("Number of elements = %d", testSize));
        System.out.println(String.format("Average Sequential Time: %d ms", sequentialTime / NUM_RUNS));
        System.out.println(String.format("Average Parallel Time: %d ms", parallelTime / NUM_RUNS));
    }

    /**
     * Utility method to generate an array of random numbers
     *
     * @param size total size of the array
     * @return array of {@code size} with random values
     */
    private int[] generateArray(int size) {
        int[] randomArray = new int[size];
        for (int i = 0; i < size; i++) {
            randomArray[i] = (int) (Math.random() * Integer.MAX_VALUE);
        }
        return randomArray;
    }
}
