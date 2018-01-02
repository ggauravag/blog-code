package com.gaurav;

import org.junit.Assert;
import org.junit.Test;

public class ParallelizedMergeTest {

    @Test
    public void testBinarySearchForRightBoundary() {
        int[] inputArray = {2, 5, 10, 12, 19, 22};
        int element = 230;
        int expectedResult = 6;

        ParallelizedMerge mergeSort = new ParallelizedMerge();
        int actualResult = mergeSort.binarySearch(element, inputArray, 0, inputArray.length - 1);
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testBinarySearchForLeftBoundary() {
        int[] inputArray = {2, 5, 10, 12, 19, 22};
        int element = 1;
        int expectedResult = 0;

        ParallelizedMerge mergeSort = new ParallelizedMerge();
        int actualResult = mergeSort.binarySearch(element, inputArray, 0, inputArray.length - 1);
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testBinarySearch() {
        int[] inputArray = {2, 5, 10, 12, 19, 22, 23};
        int element = 17;
        int expectedResult = 4;

        ParallelizedMerge mergeSort = new ParallelizedMerge();
        int actualResult = mergeSort.binarySearch(element, inputArray, 0, inputArray.length - 1);
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testMerging() {
        int[] inputArray1 = {2, 5, 8, 10};
        int[] inputArray2 = {2, 6, 12};

        int[] result = new int[7];

        ParallelizedMerge merge = new ParallelizedMerge(inputArray1, inputArray2, result, 0, inputArray1.length, 0, inputArray2.length, 0, result.length);
        merge.compute();

        int[] expectedResult = {2, 2, 5, 6, 8, 10, 12};
        Assert.assertArrayEquals(expectedResult, result);
    }

    @Test
    public void testMergingOtherWay() {
        int[] inputArray1 = {2, 5, 8, 10};
        int[] inputArray2 = {3, 6, 12, 14, 16};

        int[] result = new int[9];

        ParallelizedMerge merge = new ParallelizedMerge(inputArray1, inputArray2, result, 0, inputArray1.length, 0, inputArray2.length, 0, result.length);
        merge.compute();

        int[] expectedResult = {2, 3, 5, 6, 8, 10, 12, 14, 16};
        Assert.assertArrayEquals(expectedResult, result);
    }

    @Test
    public void testMergingWhenFirstIsEmpty() {
        int[] inputArray1 = {};
        int[] inputArray2 = {3, 6, 12, 14, 16};

        int[] result = new int[5];

        ParallelizedMerge merge = new ParallelizedMerge(inputArray1, inputArray2, result, 0, inputArray1.length, 0, inputArray2.length, 0, result.length);
        merge.compute();

        int[] expectedResult = {3, 6, 12, 14, 16};
        Assert.assertArrayEquals(expectedResult, result);
    }

    @Test
    public void testMergingWhenSecondIsEmpty() {
        int[] inputArray1 = {3, 6, 12, 14, 16};
        int[] inputArray2 = {};

        int[] result = new int[5];

        ParallelizedMerge merge = new ParallelizedMerge(inputArray1, inputArray2, result, 0, inputArray1.length, 0, inputArray2.length, 0, result.length);
        merge.compute();

        int[] expectedResult = {3, 6, 12, 14, 16};
        Assert.assertArrayEquals(expectedResult, result);
    }

    @Test
    public void testMergingWhenBothAreEqual() {
        int[] inputArray1 = {3, 6, 12, 14, 16};
        int[] inputArray2 = {3, 6, 12, 14, 16};

        int[] result = new int[10];

        ParallelizedMerge merge = new ParallelizedMerge(inputArray1, inputArray2, result, 0, inputArray1.length, 0, inputArray2.length, 0, result.length);
        merge.compute();

        int[] expectedResult = {3, 3, 6, 6, 12, 12, 14, 14, 16, 16};
        Assert.assertArrayEquals(expectedResult, result);
    }

}
