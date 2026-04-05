package org.example;

import java.util.*;

/**
 * BFS and DFS on an undirected graph represented as an adjacency list.
 *
 * Graph used in the demo:
 *
 *     1 - 2 - 5
 *     |   |
 *     3 - 4
 *         |
 *         6
 */
public class GraphTraversal {

    // Adjacency list: each node maps to the list of its neighbors.
    private final Map<Integer, List<Integer>> graph = new HashMap<>();

    void addEdge(int u, int v) {
        graph.computeIfAbsent(u, k -> new ArrayList<>()).add(v);
        graph.computeIfAbsent(v, k -> new ArrayList<>()).add(u);
    }

    // -------------------------------------------------------------------------
    // BFS  —  use a QUEUE (FIFO).
    // Explores all neighbors of the current node before going deeper.
    // Think of it as spreading outward in "waves" from the start node.
    //
    // Pattern:
    //   1. Mark start as visited, enqueue it.
    //   2. While queue is not empty:
    //        a. Dequeue a node and process it.
    //        b. For each unvisited neighbor: mark visited, enqueue.
    // -------------------------------------------------------------------------
    List<Integer> bfs(int start) {
        List<Integer> order = new ArrayList<>();
        Set<Integer> visited = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>(); // <-- KEY DATA STRUCTURE for BFS

        // Seed the traversal.
        visited.add(start);
        queue.offer(start);

        while (!queue.isEmpty()) {
            int node = queue.poll(); // grab the front (oldest) node
            order.add(node);         // process it

            for (int neighbor : graph.getOrDefault(node, Collections.emptyList())) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);  // mark BEFORE enqueuing to avoid duplicates
                    queue.offer(neighbor);
                }
            }
        }
        return order;
    }

    // -------------------------------------------------------------------------
    // DFS (iterative)  —  use a STACK (LIFO).
    // Dives as deep as possible along one path before backtracking.
    //
    // Pattern:
    //   1. Push start onto the stack.
    //   2. While stack is not empty:
    //        a. Pop a node; skip it if already visited.
    //        b. Mark visited and process it.
    //        c. Push all unvisited neighbors (they will be explored next).
    // -------------------------------------------------------------------------
    List<Integer> dfsIterative(int start) {
        List<Integer> order = new ArrayList<>();
        Set<Integer> visited = new HashSet<>();
        Deque<Integer> stack = new ArrayDeque<>(); // <-- KEY DATA STRUCTURE for DFS

        stack.push(start);

        while (!stack.isEmpty()) {
            int node = stack.pop(); // grab the top (most recent) node

            if (visited.contains(node)) continue; // already explored via another path
            visited.add(node);
            order.add(node); // process it

            for (int neighbor : graph.getOrDefault(node, Collections.emptyList())) {
                if (!visited.contains(neighbor)) {
                    stack.push(neighbor); // push now, visit later
                }
            }
        }
        return order;
    }

    // -------------------------------------------------------------------------
    // DFS (recursive)  —  the call stack IS the stack.
    // Often cleaner to write; same idea as the iterative version.
    //
    // Pattern:
    //   1. Mark current node as visited and process it.
    //   2. For each unvisited neighbor, recurse.
    // -------------------------------------------------------------------------
    List<Integer> dfsRecursive(int start) {
        List<Integer> order = new ArrayList<>();
        Set<Integer> visited = new HashSet<>();
        dfsHelper(start, visited, order);
        return order;
    }

    private void dfsHelper(int node, Set<Integer> visited, List<Integer> order) {
        visited.add(node);
        order.add(node); // process on the way DOWN (pre-order)

        for (int neighbor : graph.getOrDefault(node, Collections.emptyList())) {
            if (!visited.contains(neighbor)) {
                dfsHelper(neighbor, visited, order); // go deep before coming back
            }
        }
        // anything placed HERE runs on the way BACK UP (post-order)
    }

    // -------------------------------------------------------------------------
    // Demo
    // -------------------------------------------------------------------------
    public static void main(String[] args) {
        GraphTraversal g = new GraphTraversal();

        // Build the graph shown in the class comment.
        g.addEdge(1, 2);
        g.addEdge(1, 3);
        g.addEdge(2, 4);
        g.addEdge(2, 5);
        g.addEdge(3, 4);
        g.addEdge(4, 6);

        System.out.println("BFS from 1:            " + g.bfs(1));
        System.out.println("DFS (iterative) from 1:" + g.dfsIterative(1));
        System.out.println("DFS (recursive) from 1:" + g.dfsRecursive(1));

        // Expected BFS  (level by level): 1, 2, 3, 4, 5, 6
        // Expected DFS  (one deep path first): 1, 3, 4, 6, 2, 5  (order depends on neighbor list)
    }
}
