package rounds.Y2020.A.Plates;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int numCases = sc.nextInt();

        for (int caseId = 1; caseId <= numCases; caseId++) {
            int N = sc.nextInt();
            int K = sc.nextInt();
            int P = sc.nextInt();

            int[][] plates = new int[N][K];

            for (int i = 0; i < N; i++) {
                for (int y = 0; y < K; y++) {
                    plates[i][y] = sc.nextInt();
                    if (y != 0) {
                        plates[i][y] += plates[i][y - 1];
                    }
                }
            }

            ////

            int[][] memo = new int[N][P];

            int maxTotalSum = calcSum(plates, N, Math.min(P, K), 0, P, memo);

            /////
            System.out.println("Case #" + caseId + ": " + maxTotalSum);
        }
    }

    private static int calcSum(int[][] plates, int N, int length, int i, int pleft, int[][] memo) {
        if (pleft == 0) return 0;
        if (i >= N) return 0;
        if (memo[i][pleft - 1] != 0) return memo[i][pleft - 1];

        length = Math.min(pleft, length);

        int maxTotalSum = 0;

        for (int k = i; k < N; k++) {
            for (int l = 0; l < length; l++) {
                int currentSum = plates[k][l];
                currentSum += calcSum(plates, N, length, k + 1, pleft - l - 1, memo);

                if (currentSum > maxTotalSum) maxTotalSum = currentSum;
            }
        }

        memo[i][pleft - 1] = maxTotalSum;

        return maxTotalSum;
    }
}
