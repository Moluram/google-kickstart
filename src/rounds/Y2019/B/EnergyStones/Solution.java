package rounds.Y2019.B.EnergyStones;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Solution {

    private static int solve(int N, int[] S, int[] E, int[] L) {
        Integer[] stones = new Integer[N];

        for (int i = 0; i < N; i++) {
            stones[i] = i;
        }

        Arrays.sort(stones, (i, j) -> Integer.compare(S[i] * L[j], L[i] * S[j]));

        return traverse(S, E, L, 0, stones, 0, new HashMap<>());
    }

    private static int traverse(int[] S, int[] E, int[] L, int Tp, Integer[] stones, int i, HashMap<String, Integer> memo) {
        if (i >= stones.length) return 0;

        int stoneI = stones[i];

        final String key = "i "  + stoneI + " Tp " + Tp;

        if (memo.containsKey(key)) {
            return memo.get(key);
        }

        final int notEatenNext = traverse(S, E, L, Tp, stones, i + 1, memo);
        final int eatenNext = traverse(S, E, L, Tp + S[stoneI], stones, i + 1, memo) +
                Math.max(0, E[stoneI] - L[stoneI] * Tp);

        final int result = Math.max(notEatenNext, eatenNext);
        memo.put(key, result);
        return result;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int numCases = sc.nextInt();
        sc.nextLine();
        for (int caseId = 1; caseId <= numCases; caseId++) {
            int N = sc.nextInt();
            int[] S = new int[N];
            int[] E = new int[N];
            int[] L = new int[N];

            for (int i = 0; i < N; i++) {
                S[i] = sc.nextInt();
                E[i] = sc.nextInt();
                L[i] = sc.nextInt();
            }

            ////

            int ans = solve(N, S, E, L);

            /////
            System.out.println("Case #" + caseId + ": " + ans);
        }
    }
}
