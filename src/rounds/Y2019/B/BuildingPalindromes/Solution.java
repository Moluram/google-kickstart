package rounds.Y2019.B.BuildingPalindromes;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;

public class Solution {
    private static int solve(int n, int q, String blocks, int[] l, int[] r) {
        int count = 0;

        final var memo = new int[100][blocks.length()];

        for (int i = 0; i < blocks.length(); i++) {
            final char c = blocks.charAt(i);

            memo[c][i] += 1;

            if (i > 0) {
                for (int cc = 0; cc < 100; cc++) {
                    memo[cc][i] += memo[cc][i - 1];
                }
            }
        }

        for (int i = 0; i < q; i++) {
            if (isPal(blocks, l[i], r[i], memo)) {
                count++;
            }
        }

        return count;
    }

    private static boolean isPal(String s, int i, int y, int[][] chars) {
        if ((y - i + 1) % 2 == 0) {
            for (int[] aChar : chars) {
                final int i1 = i > 1 ? aChar[y - 1] - aChar[i - 2] : aChar[y - 1];
                if (i1 % 2 != 0)
                    return false;
            }
        } else {
            boolean containsOdd = false;

            for (int[] aChar : chars) {
                final int i1 = i > 1 ? aChar[y - 1] - aChar[i - 2] : aChar[y - 1];
                if ((i1) % 2 != 0) {
                    if (!containsOdd) {
                        containsOdd = true;
                    } else {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int numCases = sc.nextInt();
        sc.nextLine();
        for (int caseId = 1; caseId <= numCases; caseId++) {
            int N = sc.nextInt();
            int Q = sc.nextInt();
            sc.nextLine();
            String blocks = sc.nextLine();
            int[] L = new int[Q];
            int[] R = new int[Q];

            for (int i = 0; i < Q; i++) {
                L[i] = sc.nextInt();
                R[i] = sc.nextInt();
            }

            ////

            int ans = solve(N, Q, blocks, L, R);

            /////
            System.out.println("Case #" + caseId + ": " + ans);
        }
    }

    private static class Vertex {
        int x;
        int y;

        public Vertex(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
