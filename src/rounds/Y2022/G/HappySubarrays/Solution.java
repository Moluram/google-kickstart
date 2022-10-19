package rounds.Y2022.G.HappySubarrays;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Scanner;

public class Solution {

    public static void main(String[] args) {
        Scanner sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int numCases = sc.nextInt();
        sc.nextLine();
        for (int caseId = 1; caseId <= numCases; caseId++) {
            int N = sc.nextInt();
            sc.nextLine();

            ////
            int[] arr = new int[N];
            int[] pre = new int[N];
            int[] calc = new int[N];

            for (int i = 0; i < N; i++) {
                arr[i] = sc.nextInt();
                if (i > 0) pre[i] = arr[i] + pre[i - 1];
                else pre[i] = arr[i];
                calc[i] = 0;
            }

            int ans = 0;

            for (int i = 0; i < N; i++) {
                for (int y = 0; y < N - i; y++) {
                    if (calc[y] >= 0) {
                        if (y == 0) {
                            calc[y] = pre[y + i];
                        } else {
                            calc[y] = pre[y + i] - pre[y - 1];
                        }

                        if (calc[y] > 0) {
                            ans += calc[y];
                        }
                    }
                }
            }

            /////

            System.out.println("Case #" + caseId + ": " + ans);
        }
    }

    private static int solve(int N, int K, int[] B) {
        return 0;
    }
}
