package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class SortingAlgorithmsTest {

    // -------------------------------------------------------------------------
    // Parameterized test — run the same assertions against every algorithm.
    //
    // Pattern: @MethodSource points to a static method that returns a Stream
    // of "sort functions". Each function is a lambda that wraps one algorithm.
    // JUnit 5 calls the test once per entry, labeling each run by its toString.
    // -------------------------------------------------------------------------
    @FunctionalInterface
    interface SortFn {
        void sort(int[] arr);
    }

    static Stream<SortFn> allSorters() {
        return Stream.of(
            arr -> SortingAlgorithms.bubbleSort(arr),
            arr -> SortingAlgorithms.selectionSort(arr),
            arr -> SortingAlgorithms.insertionSort(arr),
            arr -> SortingAlgorithms.mergeSort(arr, 0, arr.length - 1),
            arr -> SortingAlgorithms.quickSort(arr, 0, arr.length - 1),
            arr -> SortingAlgorithms.heapSort(arr)
        );
    }

    @ParameterizedTest
    @MethodSource("allSorters")
    void sortsRandomArray(SortFn fn) {
        int[] arr      = {5, 3, 8, 1, 9, 2, 7, 4, 6};
        int[] expected = arr.clone();
        Arrays.sort(expected); // use Java's sort as the ground truth

        fn.sort(arr);

        assertArrayEquals(expected, arr);
    }

    @ParameterizedTest
    @MethodSource("allSorters")
    void sortsAlreadySortedArray(SortFn fn) {
        int[] arr = {1, 2, 3, 4, 5};
        fn.sort(arr);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, arr);
    }

    @ParameterizedTest
    @MethodSource("allSorters")
    void sortsReverseSortedArray(SortFn fn) {
        int[] arr = {5, 4, 3, 2, 1};
        fn.sort(arr);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, arr);
    }

    @ParameterizedTest
    @MethodSource("allSorters")
    void handlesDuplicates(SortFn fn) {
        int[] arr = {3, 1, 4, 1, 5, 9, 2, 6, 5};
        int[] expected = arr.clone();
        Arrays.sort(expected);

        fn.sort(arr);

        assertArrayEquals(expected, arr);
    }

    @ParameterizedTest
    @MethodSource("allSorters")
    void handlesSingleElement(SortFn fn) {
        int[] arr = {42};
        fn.sort(arr);
        assertArrayEquals(new int[]{42}, arr);
    }

    // -------------------------------------------------------------------------
    // Plain @Test — for cases that don't fit the parameterized mold.
    // -------------------------------------------------------------------------
    @Test
    void bubbleSortHandlesEmptyArray() {
        // Empty array is a classic edge case — make sure we don't blow up.
        int[] arr = {};
        SortingAlgorithms.bubbleSort(arr);
        assertArrayEquals(new int[]{}, arr);
    }
}
