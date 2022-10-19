package rounds.Y2022.G.Walktober;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int numCases = sc.nextInt();
        sc.nextLine();
        for (int caseId = 1; caseId <= numCases; caseId++) {
            int M = sc.nextInt();
            int N = sc.nextInt();
            int P = sc.nextInt();
            sc.nextLine();

            ////

            int[] personDay = new int[N];
            int[] bestPerDay = new int[N];
            for (int i = 0; i < N; i++) bestPerDay[i] = 0;

            for (int person = 0; person < M; person++) {
                if (person == (P - 1)) {
                    for (int i = 0; i < N; i++) {
                        personDay[i] = sc.nextInt();
                    }
                } else {
                    for (int i = 0; i < N; i++) {
                        int currentSteps = sc.nextInt();

                        if (bestPerDay[i] < currentSteps) {
                            bestPerDay[i] = currentSteps;
                        }
                    }
                }
                sc.nextLine();
            }

            int ans = 0;
            for (int i = 0; i < N; i++) {
                if (bestPerDay[i] > personDay[i]) {
                    ans += bestPerDay[i] - personDay[i];
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
