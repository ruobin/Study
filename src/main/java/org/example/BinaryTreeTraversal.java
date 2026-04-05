package org.example;

import java.util.*;

/**
 * BFS and DFS on a binary tree.
 *
 * Tree used in the demo:
 *
 *            1
 *           / \
 *          2   3
 *         / \   \
 *        4   5   6
 *               /
 *              7
 *
 * BFS (level-order):  1, 2, 3, 4, 5, 6, 7
 * DFS pre-order:      1, 2, 4, 5, 3, 6, 7
 * DFS in-order:       4, 2, 5, 1, 3, 7, 6
 * DFS post-order:     4, 5, 2, 7, 6, 3, 1
 */
public class BinaryTreeTraversal {

    static class Node {
        int val;
        Node left, right;
        Node(int val) { this.val = val; }
    }

    // -------------------------------------------------------------------------
    // BFS  —  use a QUEUE (FIFO).
    // Visits nodes level by level, left to right. Also called "level-order".
    //
    // Pattern:
    //   1. Enqueue root.
    //   2. While queue is not empty:
    //        a. Dequeue a node and process it.
    //        b. Enqueue its left child (if any), then its right child (if any).
    // -------------------------------------------------------------------------
    List<Integer> bfs(Node root) {
        List<Integer> order = new ArrayList<>();
        if (root == null) return order;

        Queue<Node> queue = new LinkedList<>(); // <-- KEY DATA STRUCTURE for BFS
        queue.offer(root);

        while (!queue.isEmpty()) {
            Node node = queue.poll(); // grab the front (oldest) node
            order.add(node.val);      // process it

            if (node.left  != null) queue.offer(node.left);   // left before right
            if (node.right != null) queue.offer(node.right);  // preserves level order
        }
        return order;
    }

    // -------------------------------------------------------------------------
    // BFS — level-by-level (grouped).
    // Same idea as above, but each level is collected into its own sub-list.
    // Useful when you need to know which level a node is on.
    //
    // Trick: snapshot queue.size() before processing a level so you know
    // exactly how many nodes belong to the current level.
    // -------------------------------------------------------------------------
    List<List<Integer>> bfsLevels(Node root) {
        List<List<Integer>> levels = new ArrayList<>();
        if (root == null) return levels;

        Queue<Node> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int levelSize = queue.size(); // nodes remaining on the CURRENT level
            List<Integer> level = new ArrayList<>();

            for (int i = 0; i < levelSize; i++) {
                Node node = queue.poll();
                level.add(node.val);
                if (node.left  != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
            levels.add(level);
        }
        return levels;
    }

    // -------------------------------------------------------------------------
    // DFS — recursive (three orderings).
    //
    // The only difference between pre / in / post order is WHERE you process
    // the current node relative to the recursive calls:
    //
    //   Pre-order  (root, left, right) — process BEFORE children
    //     Good for: copying a tree, serialization, printing directory trees.
    //
    //   In-order   (left, root, right) — process BETWEEN children
    //     Good for: getting sorted output from a BST.
    //
    //   Post-order (left, right, root) — process AFTER children
    //     Good for: deleting a tree, computing subtree sizes.
    // -------------------------------------------------------------------------
    List<Integer> dfsPreOrder(Node root) {
        List<Integer> order = new ArrayList<>();
        preOrder(root, order);
        return order;
    }

    private void preOrder(Node node, List<Integer> order) {
        if (node == null) return;
        order.add(node.val);    // 1. process ROOT  <-- pre
        preOrder(node.left,  order); // 2. go left
        preOrder(node.right, order); // 3. go right
    }

    List<Integer> dfsInOrder(Node root) {
        List<Integer> order = new ArrayList<>();
        inOrder(root, order);
        return order;
    }

    private void inOrder(Node node, List<Integer> order) {
        if (node == null) return;
        inOrder(node.left,  order); // 1. go left
        order.add(node.val);        // 2. process ROOT  <-- in
        inOrder(node.right, order); // 3. go right
    }

    List<Integer> dfsPostOrder(Node root) {
        List<Integer> order = new ArrayList<>();
        postOrder(root, order);
        return order;
    }

    private void postOrder(Node node, List<Integer> order) {
        if (node == null) return;
        postOrder(node.left,  order); // 1. go left
        postOrder(node.right, order); // 2. go right
        order.add(node.val);          // 3. process ROOT  <-- post
    }

    // -------------------------------------------------------------------------
    // DFS — iterative pre-order  —  use a STACK (LIFO).
    // Push right child first so left is processed first (LIFO reverses order).
    //
    // Pattern:
    //   1. Push root.
    //   2. While stack is not empty:
    //        a. Pop, process.
    //        b. Push right child, then left child.
    // -------------------------------------------------------------------------
    List<Integer> dfsIterativePreOrder(Node root) {
        List<Integer> order = new ArrayList<>();
        if (root == null) return order;

        Deque<Node> stack = new ArrayDeque<>(); // <-- KEY DATA STRUCTURE for DFS
        stack.push(root);

        while (!stack.isEmpty()) {
            Node node = stack.pop();
            order.add(node.val); // process on pop

            // Push right FIRST so left is popped (and processed) first.
            if (node.right != null) stack.push(node.right);
            if (node.left  != null) stack.push(node.left);
        }
        return order;
    }

    // -------------------------------------------------------------------------
    // Demo
    // -------------------------------------------------------------------------
    public static void main(String[] args) {
        BinaryTreeTraversal t = new BinaryTreeTraversal();

        // Build the tree shown in the class comment.
        Node root  = new Node(1);
        root.left  = new Node(2);
        root.right = new Node(3);
        root.left.left   = new Node(4);
        root.left.right  = new Node(5);
        root.right.right = new Node(6);
        root.right.right.left = new Node(7);

        System.out.println("BFS (level-order):        " + t.bfs(root));
        System.out.println("BFS (grouped by level):   " + t.bfsLevels(root));
        System.out.println("DFS pre-order (recursive):" + t.dfsPreOrder(root));
        System.out.println("DFS in-order  (recursive):" + t.dfsInOrder(root));
        System.out.println("DFS post-order(recursive):" + t.dfsPostOrder(root));
        System.out.println("DFS pre-order (iterative):" + t.dfsIterativePreOrder(root));

        // Expected:
        // BFS level-order:   [1, 2, 3, 4, 5, 6, 7]
        // BFS grouped:       [[1], [2, 3], [4, 5, 6], [7]]
        // DFS pre-order:     [1, 2, 4, 5, 3, 6, 7]
        // DFS in-order:      [4, 2, 5, 1, 3, 7, 6]
        // DFS post-order:    [4, 5, 2, 7, 6, 3, 1]
    }
}
