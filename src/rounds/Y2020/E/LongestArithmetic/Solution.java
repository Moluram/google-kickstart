package rounds.Y2020.E.LongestArithmetic;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Solution {
    private static int solve(int N, long[] A) {
        int maxDist = 2;
        long prevRange = A[1] - A[0];
        int currentDist = 1;

        for (int i = 1; i < N; i++) {
            if (A[i] - A[i - 1] == prevRange) {
                currentDist++;
            } else {
                maxDist = Math.max(currentDist, maxDist);
                prevRange = A[i] - A[i - 1];
                currentDist = 2;
            }
        }

        return Math.max(currentDist, maxDist);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int numCases = sc.nextInt();
        sc.nextLine();
        for (int caseId = 1; caseId <= numCases; caseId++) {
            int N = sc.nextInt();
            long[] A = new long[N];

            for (int i = 0; i < N; i++) {
                A[i] = sc.nextLong();
            }
            ////

            int longest = solve(N, A);

            /////
            System.out.println("Case #" + caseId + ": " + longest);
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
