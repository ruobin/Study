package org.example;

public class MyHeap {

//    int[] arr = {12, 11, 13, 5, 6, 7};
    int[] arr;

    public MyHeap(int[] arr) {
        this.arr = arr;
    }


    public static void heapify(int[] arr, int rootIndex, int n) {
        if (rootIndex * 2 + 1 >= n) return;

        int root = arr[rootIndex];
        int largest = root;
        int left = arr[rootIndex * 2 + 1];


        if (left > root) {
            arr[rootIndex * 2 + 1] = root;
            arr[rootIndex] = left;
            largest = left;
            heapify(arr, rootIndex * 2 + 1, n);
        }

        if (rootIndex * 2 + 2 >= n) return;
        int right = arr[rootIndex * 2 + 2];
        if (right > largest) {
            arr[rootIndex * 2 + 2] = largest;
            arr[rootIndex] = right;
            largest = right;
            heapify(arr, rootIndex * 2 + 2, n);
        }

    }

    static void heapSort(int[] arr) {

        /**
         *              2
         *            /  \
         *          1     3
         *        /  \   /
         *       5    6 7
         */
        // get last non-leaf index
        int i = arr.length/2 - 1;
        for (int m = i; m >= 0; m-- ){
            heapify(arr, m, arr.length);
        }
        int maxValue = arr[0];

        for (int j = arr.length - 1; j > 0; j--) {
            int tmp = arr[j];
            arr[j] = arr[0];
            arr[0] = tmp;
            heapify(arr, 0, j);
        }



    }

    static void main() {
        IO.println("Heap Sort Demonstration");

        int[] arr = {2, 1, 3, 5, 6, 7};
        IO.println("Original array: " + java.util.Arrays.toString(arr));

        heapSort(arr);

        IO.println("Sorted array: " + java.util.Arrays.toString(arr));


        int[] arr2 = {12, 11, 13, 15, 6, 7, 10, 9, 8, 5, 1, 16};
        IO.println("Original array: " + java.util.Arrays.toString(arr2));

        heapSort(arr2);

        IO.println("Sorted array: " + java.util.Arrays.toString(arr2));
    }
}
