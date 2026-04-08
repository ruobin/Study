package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BinaryTreeTraversalTest {

    // -------------------------------------------------------------------------
    // @BeforeEach — runs before every test to give each test a fresh tree.
    //
    // Tree shape:
    //        1
    //       / \
    //      2   3
    //     / \
    //    4   5
    // -------------------------------------------------------------------------
    BinaryTreeTraversal t;
    BinaryTreeTraversal.Node root;

    @BeforeEach
    void setUp() {
        t = new BinaryTreeTraversal();
        root            = new BinaryTreeTraversal.Node(1);
        root.left       = new BinaryTreeTraversal.Node(2);
        root.right      = new BinaryTreeTraversal.Node(3);
        root.left.left  = new BinaryTreeTraversal.Node(4);
        root.left.right = new BinaryTreeTraversal.Node(5);
    }

    @Test
    void bfsVisitsLevelByLevel() {
        // BFS must visit nodes top-down, left-to-right.
        assertEquals(List.of(1, 2, 3, 4, 5), t.bfs(root));
    }

    @Test
    void bfsLevelsGroupsCorrectly() {
        assertEquals(List.of(
            List.of(1),
            List.of(2, 3),
            List.of(4, 5)
        ), t.bfsLevels(root));
    }

    @Test
    void dfsPreOrderRootLeftRight() {
        assertEquals(List.of(1, 2, 4, 5, 3), t.dfsPreOrder(root));
    }

    @Test
    void dfsInOrderLeftRootRight() {
        // On a BST this would give sorted output.
        assertEquals(List.of(4, 2, 5, 1, 3), t.dfsInOrder(root));
    }

    @Test
    void dfsPostOrderLeftRightRoot() {
        assertEquals(List.of(4, 5, 2, 3, 1), t.dfsPostOrder(root));
    }

    @Test
    void iterativePreOrderMatchesRecursive() {
        // The iterative and recursive pre-order must always agree.
        assertEquals(t.dfsPreOrder(root), t.dfsIterativePreOrder(root));
    }

    @Test
    void nullRootReturnsEmptyList() {
        // Guard: all traversals must handle an empty tree without throwing.
        assertTrue(t.bfs(null).isEmpty());
        assertTrue(t.dfsPreOrder(null).isEmpty());
        assertTrue(t.dfsInOrder(null).isEmpty());
        assertTrue(t.dfsPostOrder(null).isEmpty());
    }
}
