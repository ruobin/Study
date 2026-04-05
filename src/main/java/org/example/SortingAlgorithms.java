package org.example;

import java.util.Arrays;

/**
 * Popular sorting algorithms for LeetCode interview prep.
 *
 * Quick reference — time & space complexity:
 *
 *  Algorithm      | Best       | Average    | Worst      | Space  | Stable?
 *  ---------------|------------|------------|------------|--------|--------
 *  Bubble Sort    | O(n)       | O(n²)      | O(n²)      | O(1)   | Yes
 *  Selection Sort | O(n²)      | O(n²)      | O(n²)      | O(1)   | No
 *  Insertion Sort | O(n)       | O(n²)      | O(n²)      | O(1)   | Yes
 *  Merge Sort     | O(n log n) | O(n log n) | O(n log n) | O(n)   | Yes
 *  Quick Sort     | O(n log n) | O(n log n) | O(n²)      | O(log n)| No
 *  Heap Sort      | O(n log n) | O(n log n) | O(n log n) | O(1)   | No
 *
 * Interview tip: "Which sort would you use?" — the expected answer is almost
 * always merge sort (guaranteed O(n log n), stable) or quicksort (fast in
 * practice). Know heap sort because it proves you understand heaps.
 */
public class SortingAlgorithms {

    // =========================================================================
    // BUBBLE SORT
    // =========================================================================
    // Idea: repeatedly scan the array and "bubble" the largest unsorted element
    //       to the end by swapping adjacent pairs.
    //
    // Visualization (one pass bubbles the max to the right):
    //   [5, 3, 1, 4]
    //    ^--^ swap → [3, 5, 1, 4]
    //       ^--^ swap → [3, 1, 5, 4]
    //          ^--^ swap → [3, 1, 4, 5]  ← 5 is now in its final place
    //
    // When to use: almost never in practice; good for teaching.
    // Optimization: if no swap occurred in a full pass, the array is sorted —
    //               bail early (this gives O(n) best case on already-sorted input).
    // =========================================================================
    static void bubbleSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {           // after i passes, last i elements are sorted
            boolean swapped = false;

            for (int j = 0; j < n - 1 - i; j++) {  // don't re-check the already-sorted tail
                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1);             // push larger element one step right
                    swapped = true;
                }
            }

            if (!swapped) break;  // early exit: array is already sorted
        }
    }

    // =========================================================================
    // SELECTION SORT
    // =========================================================================
    // Idea: divide the array into a sorted left portion and unsorted right portion.
    //       On each pass, find the minimum of the unsorted portion and append it
    //       to the sorted portion with a single swap.
    //
    // Visualization:
    //   [5, 3, 1, 4]  → min=1 at index 2 → swap(0,2) → [1, 3, 5, 4]
    //   [1, 3, 5, 4]  → min=3 at index 1 → swap(1,1) → [1, 3, 5, 4]
    //   [1, 3, 5, 4]  → min=4 at index 3 → swap(2,3) → [1, 3, 4, 5]
    //
    // When to use: minimizes the number of swaps (exactly n-1). Useful when
    //              writes are expensive (e.g., flash memory).
    // =========================================================================
    static void selectionSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {           // boundary of the unsorted region
            int minIdx = i;                          // assume current position holds the min

            for (int j = i + 1; j < n; j++) {       // scan unsorted region for true min
                if (arr[j] < arr[minIdx]) {
                    minIdx = j;
                }
            }

            swap(arr, i, minIdx);                    // place min at the sorted boundary
        }
    }

    // =========================================================================
    // INSERTION SORT
    // =========================================================================
    // Idea: build the sorted portion one element at a time by taking the next
    //       unsorted element and shifting it left until it lands in the right spot.
    //       Just like sorting playing cards in your hand.
    //
    // Visualization:
    //   [5, 3, 1, 4]
    //   take 3: shift 5 right → [5, 5, 1, 4] → insert 3 → [3, 5, 1, 4]
    //   take 1: shift 5,3 right → [3, 3, 5, 4] → [3, 3, 3, 4]... → insert 1 → [1, 3, 5, 4]
    //   take 4: shift 5 right   → [1, 3, 4, 5]
    //
    // When to use: excellent on nearly-sorted data (O(n) best case).
    //              Often used as the base case inside TimSort (Java's Arrays.sort).
    // =========================================================================
    static void insertionSort(int[] arr) {
        int n = arr.length;
        for (int i = 1; i < n; i++) {
            int key = arr[i]; // the element we're about to insert into the sorted portion
            int j = i - 1;

            // Shift elements of the sorted portion that are greater than key one position right.
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j]; // shift right (no swap needed — saves writes)
                j--;
            }

            arr[j + 1] = key; // drop the key into its correct position
        }
    }

    // =========================================================================
    // MERGE SORT
    // =========================================================================
    // Idea: divide-and-conquer.
    //   1. Split the array in half recursively until you have sub-arrays of size 1.
    //   2. Merge pairs of sorted sub-arrays back together in order.
    //
    // Visualization:
    //   [5, 3, 1, 4]
    //   → split → [5, 3] | [1, 4]
    //   → split → [5] [3] | [1] [4]
    //   → merge → [3, 5] | [1, 4]
    //   → merge → [1, 3, 4, 5]
    //
    // When to use: best general-purpose sort — guaranteed O(n log n), stable.
    //              Used in Java's Arrays.sort for objects (TimSort is a hybrid).
    // Trade-off: requires O(n) extra space for the temporary arrays.
    //
    // Interview pattern: recognize that the "merge two sorted arrays" sub-problem
    // (LeetCode 88) is the exact core of merge sort's merge step.
    // =========================================================================
    static void mergeSort(int[] arr, int left, int right) {
        if (left >= right) return; // base case: a single element is already sorted

        int mid = left + (right - left) / 2; // avoids integer overflow vs (left+right)/2

        mergeSort(arr, left, mid);       // sort left half
        mergeSort(arr, mid + 1, right);  // sort right half
        merge(arr, left, mid, right);    // merge the two sorted halves
    }

    private static void merge(int[] arr, int left, int mid, int right) {
        // Copy both halves into temporary arrays.
        int[] leftArr  = Arrays.copyOfRange(arr, left, mid + 1);
        int[] rightArr = Arrays.copyOfRange(arr, mid + 1, right + 1);

        int i = 0, j = 0;       // pointers into leftArr and rightArr
        int k = left;            // pointer into the original arr (write position)

        // Pick the smaller front element from each half and place it back.
        while (i < leftArr.length && j < rightArr.length) {
            if (leftArr[i] <= rightArr[j]) {
                arr[k++] = leftArr[i++];  // <= keeps the sort stable
            } else {
                arr[k++] = rightArr[j++];
            }
        }

        // Drain whichever half still has elements.
        while (i < leftArr.length)  arr[k++] = leftArr[i++];
        while (j < rightArr.length) arr[k++] = rightArr[j++];
    }

    // =========================================================================
    // QUICK SORT
    // =========================================================================
    // Idea: divide-and-conquer around a "pivot".
    //   1. Pick a pivot (we use the last element).
    //   2. Partition: rearrange so everything < pivot is to its left,
    //                 everything >= pivot is to its right.
    //   3. Recursively sort the left and right partitions.
    //
    // Visualization (pivot = 4):
    //   [3, 5, 1, 4]
    //   partition → [3, 1, | 4 | 5]   pivot lands at index 2
    //   recurse left [3,1] → [1,3]
    //   recurse right [5] → [5]
    //   result: [1, 3, 4, 5]
    //
    // When to use: fastest in practice on random data (great cache behavior).
    //              Java's Arrays.sort for primitives uses a dual-pivot quicksort.
    // Watch out: O(n²) worst case if the pivot is always the min or max
    //            (e.g., already-sorted input with a naive last-element pivot).
    //            Fix: use random pivot or median-of-three pivot selection.
    // =========================================================================
    static void quickSort(int[] arr, int low, int high) {
        if (low >= high) return; // base case

        int pivotIdx = partition(arr, low, high); // pivot is now in its final position
        quickSort(arr, low, pivotIdx - 1);         // sort everything to its left
        quickSort(arr, pivotIdx + 1, high);        // sort everything to its right
    }

    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[high]; // choose the last element as pivot (simple choice)
        int i = low - 1;       // i tracks the boundary of elements < pivot

        for (int j = low; j < high; j++) {
            if (arr[j] < pivot) {
                i++;                    // extend the "less than" region
                swap(arr, i, j);        // move this small element into that region
            }
        }

        // Place the pivot right after all elements smaller than it.
        swap(arr, i + 1, high);
        return i + 1; // return pivot's final index
    }

    // =========================================================================
    // HEAP SORT
    // =========================================================================
    // Idea: exploit the max-heap property (parent >= children).
    //   Phase 1 — Build max-heap: turn the array into a valid max-heap in-place.
    //             Start heapifying from the last non-leaf node upward.
    //             After this phase, arr[0] is the largest element.
    //   Phase 2 — Sort: repeatedly swap the heap root (max) with the last
    //             unsorted element, shrink the heap by 1, and re-heapify the root.
    //             Each swap "locks in" the largest remaining element at the end.
    //
    // Visualization (array as a binary tree):
    //   [4, 10, 3, 5, 1]
    //   After build-heap: [10, 5, 3, 4, 1]   ← root is the max
    //   Swap root with last, shrink heap:
    //     [1, 5, 3, 4 | 10]  → heapify → [5, 4, 3, 1 | 10]
    //     [1, 4, 3 | 5, 10]  → heapify → [4, 1, 3 | 5, 10]
    //     [3, 1 | 4, 5, 10]  → heapify → [3, 1 | 4, 5, 10]
    //     [1 | 3, 4, 5, 10]  → done
    //   Result: [1, 3, 4, 5, 10]
    //
    // Array index relationships (0-based):
    //   parent(i)      = (i - 1) / 2
    //   left child(i)  = 2*i + 1
    //   right child(i) = 2*i + 2
    //
    // When to use: when you need O(n log n) worst case AND O(1) space.
    //              In practice slightly slower than quicksort due to poor cache behavior.
    // =========================================================================
    static void heapSort(int[] arr) {
        int n = arr.length;

        // --- Phase 1: build a max-heap in-place ---
        // Last non-leaf node is at index n/2 - 1.
        // Leaves don't need heapifying (they have no children).
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }

        // --- Phase 2: extract elements from the heap one by one ---
        for (int i = n - 1; i > 0; i--) {
            swap(arr, 0, i);   // move current root (max) to its sorted position at the end
            heapify(arr, i, 0); // restore heap property for the reduced heap (size = i)
        }
    }

    /**
     * Sift element at index {@code i} down to restore the max-heap property.
     *
     * @param arr  the array
     * @param n    the current heap size (elements beyond n are already sorted)
     * @param i    the index of the element to sift down
     */
    private static void heapify(int[] arr, int n, int i) {
        int largest = i;           // assume the current node is the largest
        int left    = 2 * i + 1;  // left child index
        int right   = 2 * i + 2; // right child index

        // Check if the left child is larger than the current largest.
        if (left < n && arr[left] > arr[largest]) {
            largest = left;
        }

        // Check if the right child is larger than the current largest.
        if (right < n && arr[right] > arr[largest]) {
            largest = right;
        }

        // If a child is larger, swap and continue sifting down.
        if (largest != i) {
            swap(arr, i, largest);
            heapify(arr, n, largest); // recurse on the subtree that was disturbed
        }
    }

    // =========================================================================
    // Utility
    // =========================================================================
    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i]  = arr[j];
        arr[j]  = tmp;
    }

    // =========================================================================
    // Demo
    // =========================================================================
    public static void main(String[] args) {
        int[] original = {5, 3, 8, 1, 9, 2, 7, 4, 6};

        System.out.println("Original:       " + Arrays.toString(original));

        int[] arr;

        arr = original.clone(); bubbleSort(arr);
        System.out.println("Bubble sort:    " + Arrays.toString(arr));

        arr = original.clone(); selectionSort(arr);
        System.out.println("Selection sort: " + Arrays.toString(arr));

        arr = original.clone(); insertionSort(arr);
        System.out.println("Insertion sort: " + Arrays.toString(arr));

        arr = original.clone(); mergeSort(arr, 0, arr.length - 1);
        System.out.println("Merge sort:     " + Arrays.toString(arr));

        arr = original.clone(); quickSort(arr, 0, arr.length - 1);
        System.out.println("Quick sort:     " + Arrays.toString(arr));

        arr = original.clone(); heapSort(arr);
        System.out.println("Heap sort:      " + Arrays.toString(arr));

        // Expected (all the same): [1, 2, 3, 4, 5, 6, 7, 8, 9]
    }
}
