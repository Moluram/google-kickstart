package rounds.Y2021.G.Banana;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.Scanner;

public class Solution {
    private static final BigInteger edge = BigInteger.valueOf(10).pow(9);

    public static void main(String[] args) {
        Scanner sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int numCases = sc.nextInt();
        sc.nextLine();
        for (int caseId = 1; caseId <= numCases; caseId++) {
            int N = sc.nextInt();
            int K = sc.nextInt();
            int[] B = new int[N];

            for (int i = 0; i < N; i++) {
                B[i] = sc.nextInt();
            }
            ////

            int ans = solve(N, K, B);

            /////
            System.out.println("Case #" + caseId + ": " + ans );
        }
    }

    private static int solve(int N, int K, int[] B) {
        int[] BestLength = new int[K + 1];

        for (int i = 0; i <= K; i++) {
            BestLength[i] = N + 1;
        }

        int ans = N;
        boolean isFound = false;

        for (int i = N - 1; i >= 0; i--) {
            int currentSum = 0;
            for (int j = i; j >= 0; j--) {
                currentSum += B[j];
                if (currentSum <= K) {
                    if (currentSum == K) {
                        isFound = true;
                        ans = Math.min(ans, i - j + 1);
                        continue;
                    }

                    if (BestLength[K - currentSum] != N + 1) isFound = true;

                    ans = Math.min(ans, i - j + 1 + BestLength[K - currentSum]);
                } else {
                    break;
                }
            }

            currentSum = 0;
            for (int x = i; x < N; x++) {
                currentSum += B[x];

                if (currentSum <= K) {
                    BestLength[currentSum] = Math.min(BestLength[currentSum], x - i + 1);
                } else {
                    break;
                }
            }
        }
        ans  = isFound ? ans : -1;
        return ans;
    }
}
