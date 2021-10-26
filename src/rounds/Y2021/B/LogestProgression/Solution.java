package rounds.Y2021.B.LogestProgression;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Solution {
    private static int solve(int n, long[] A) {
        int ans = 0;
        int count = 2;
        long prev = A[1] - A[0];
        int errorI = 0;
        long errorValue = 0;
        boolean hasError = false;
        for (int y = 2; y < n; y++) {
            if (A[y] - A[y - 1] == prev) {
                count++;
            } else if (!hasError) {
                hasError = true;
                errorI = y;
                errorValue = A[errorI];
                A[y] = A[y - 1] + prev;
                count++;
            } else {
                ans = Math.max(count, ans);
                y = errorI;
                A[errorI] = errorValue;
                prev = A[errorI] - A[errorI - 1];
                count = 2;
                hasError = false;
            }
        }

        if (hasError) {
            A[errorI] = errorValue;
        }

        ans = Math.max(ans, count);
        count = 2;
        prev = A[n - 2] - A[n - 1];
        errorI = 0;
        errorValue = 0;
        hasError = false;
        for (int y = n - 3; y >= 0; y--) {
            if (A[y] - A[y + 1] == prev) {
                count++;
            } else if (!hasError) {
                hasError = true;
                errorI = y;
                errorValue = A[errorI];
                A[y] = A[y + 1] + prev;
                count++;
            } else {
                ans = Math.max(count, ans);
                y = errorI;
                A[errorI] = errorValue;
                prev = A[errorI] - A[errorI + 1];
                count = 2;
                hasError = false;
            }
        }

        return Math.max(ans, count);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int numCases = sc.nextInt();
        sc.nextLine();
        for (int caseId = 1; caseId <= numCases; caseId++) {
            int N = sc.nextInt();
            long[] A = new long[N];

            for (int i = 0; i < N; i++) {
                A[i] = sc.nextInt();
            }

            ////

            int ans = solve(N, A);

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
