package rounds.Y2021.A.KGoodness;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int T = sc.nextInt();

        for (int caseId = 1; caseId <= T; caseId++) {
            int N = sc.nextInt();
            int K = sc.nextInt();

            final String S = sc.next();
            ////

            int numberOfOperations = getNumberOfOperations(N, K, S);

            /////
            System.out.println("Case #" + caseId + ": " + numberOfOperations);
        }
    }

    private static int getNumberOfOperations(int N, int K, String S) {
        int currentK = 0;
        int numberOfOperations = 0;

        for (int i = 0; i < N / 2; i++) {
            if (S.charAt(i) != S.charAt(N - i - 1)) currentK++;
        }

        if (currentK > K) {
            numberOfOperations = currentK - K;
        } else {
            numberOfOperations = K - currentK;
        }

        return numberOfOperations;
    }
}
