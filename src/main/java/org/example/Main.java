package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    // Restores the max-heap property for the subtree rooted at index i.
    static void heapify(int[] arr, int n, int i) {
        int largest = i;
        // For a node at index i, the children are located at 2*i+1 and 2*i+2.
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        // Find the largest value among the root and its left child.
        if (left < n && arr[left] > arr[largest]) {
            largest = left;
        }

        // Then compare that candidate with the right child.
        if (right < n && arr[right] > arr[largest]) {
            largest = right;
        }

        // If a child is larger than the root, swap and keep pushing the value down.
        if (largest != i) {
            int temp = arr[i];
            arr[i] = arr[largest];
            arr[largest] = temp;

            // The swapped child may now violate the heap property, so fix that subtree too.
            heapify(arr, n, largest);
        }
    }

    // Heap sort works in two phases: build a max heap, then repeatedly extract the max.
    static void heapSort(int[] arr) {
        int n = arr.length;

        // Build the max heap from the last non-leaf node up to the root.
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }

        // Move the current maximum to the end, then rebuild the heap on the remaining prefix.
        for (int i = n - 1; i > 0; i--) {
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            heapify(arr, i, 0);
        }
    }

    public static void main(String[] args) {
        System.out.println("Heap Sort Demonstration");

        int[] arr = {12, 11, 13, 5, 6, 7};
        System.out.println("Original array: " + java.util.Arrays.toString(arr));

        heapSort(arr);

        System.out.println("Sorted array: " + java.util.Arrays.toString(arr));
    }
}
