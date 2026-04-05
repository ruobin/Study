package org.example;

import java.util.ArrayList;

public class MyStack {

    public ArrayList<Integer> getArr() {
        return arr;
    }

    public void setArr(ArrayList<Integer> arr) {
        this.arr = arr;
    }

    ArrayList<Integer> arr;
    int topIndex = -1;

    public MyStack(int size) {
        arr = new ArrayList<Integer>(size);

    }

    /**
     *
     * @return topIndex
     */
    public int push(int value) {
        arr.add(value);

        return topIndex++;
    }

    public Integer pop() {
        if (topIndex < 0) return null;
        int result = arr.remove(topIndex);
        topIndex--;
        return result;
    }

    static void main() {
        IO.println("My Stack Demonstration");

        MyStack stack = new MyStack(10);
        stack.push(1);
        stack.push(2);
        stack.push(3);
        IO.println("Original array: " + java.util.Arrays.toString(stack.getArr().toArray()));

        stack.pop();
        stack.push(4);
        IO.println("Sorted array: " + java.util.Arrays.toString(stack.getArr().toArray()));
    }
}
