package org.example;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * LeetCode 1 — Two Sum
 *
 * Given an array of integers nums and an integer target, return the indices
 * of the two numbers that add up to target. Exactly one solution is guaranteed.
 *
 * Example:  nums = [2, 7, 11, 15], target = 9  →  [0, 1]  (2 + 7 = 9)
 *
 * Solutions covered:
 *
 *   Approach              | Time   | Space | Notes
 *   ----------------------|--------|-------|---------------------------
 *   1. Brute force        | O(n²)  | O(1)  | Check every pair
 *   2. HashMap (2-pass)   | O(n)   | O(n)  | Build map, then look up
 *   3. HashMap (1-pass)   | O(n)   | O(n)  | Look up while building — best
 */
public class TwoSum {

    // -------------------------------------------------------------------------
    // Approach 1 — Brute Force
    // -------------------------------------------------------------------------
    // Check every possible pair (i, j) and return when they sum to target.
    //
    // Why bother knowing this: interviewers expect you to start here before
    // optimizing. Always mention it as your baseline, then improve.
    //
    // Time O(n²) — nested loops.  Space O(1) — no extra data structure.
    // -------------------------------------------------------------------------
    static int[] bruteForce(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {  // j starts at i+1 to avoid using same element twice
                if (nums[i] + nums[j] == target) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{};  // no solution found (problem guarantees this won't happen)
    }

    // -------------------------------------------------------------------------
    // Approach 2 — HashMap, two passes
    // -------------------------------------------------------------------------
    // Pass 1: store each value → index in a map.
    // Pass 2: for each element, check if its complement (target - nums[i])
    //         exists in the map and isn't the element itself.
    //
    // Key insight: instead of searching the array for the complement (O(n)),
    //              a HashMap gives us O(1) lookup.
    //
    // Time O(n).  Space O(n) — the map holds up to n entries.
    // -------------------------------------------------------------------------
    static int[] twoPassHashMap(int[] nums, int target) {
        // Pass 1: build value → index map.
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i], i);
        }

        // Pass 2: look up each element's complement.
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            // map.get(complement) != i  — don't use the same index twice
            // e.g. nums=[3,5], target=6: complement of 3 is 3, but we can't pair index 0 with itself
            if (map.containsKey(complement) && map.get(complement) != i) {
                return new int[]{i, map.get(complement)};
            }
        }
        return new int[]{};
    }

    // -------------------------------------------------------------------------
    // Approach 3 — HashMap, one pass  ← optimal, memorize this one
    // -------------------------------------------------------------------------
    // Build the map and check for the complement at the same time.
    // For each element, ask: "have I already seen the number I need?"
    // If yes → done. If no → store this element and move on.
    //
    // Why this works: if a valid pair (i, j) exists with i < j, then by the
    // time we reach j, i is already in the map, so we find it immediately.
    //
    // Time O(n).  Space O(n).  Only one loop — cleaner than approach 2.
    // -------------------------------------------------------------------------
    static int[] onePassHashMap(int[] nums, int target) {
        Map<Integer, Integer> seen = new HashMap<>(); // value → index of elements seen so far

        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];

            if (seen.containsKey(complement)) {
                // complement was seen earlier — we have our pair
                return new int[]{seen.get(complement), i};
            }

            seen.put(nums[i], i);  // store current element for future lookups
        }
        return new int[]{};
    }

    // -------------------------------------------------------------------------
    // Demo
    // -------------------------------------------------------------------------
    public static void main(String[] args) {
        int[][] testCases = {
            {2, 7, 11, 15},   // target 9  → [0, 1]
            {3, 2, 4},        // target 6  → [1, 2]
            {3, 3},           // target 6  → [0, 1]  (duplicate values)
        };
        int[] targets = {9, 6, 6};

        for (int t = 0; t < testCases.length; t++) {
            int[] nums   = testCases[t];
            int   target = targets[t];
            System.out.printf("nums=%-16s target=%d%n", Arrays.toString(nums), target);
            System.out.println("  brute force:    " + Arrays.toString(bruteForce(nums, target)));
            System.out.println("  two-pass map:   " + Arrays.toString(twoPassHashMap(nums, target)));
            System.out.println("  one-pass map:   " + Arrays.toString(onePassHashMap(nums, target)));
        }
    }
}
