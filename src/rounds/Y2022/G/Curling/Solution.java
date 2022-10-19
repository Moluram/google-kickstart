package rounds.Y2022.G.Curling;

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
            int Rs = sc.nextInt();
            int Rh = sc.nextInt();
            sc.nextLine();
            int N = sc.nextInt();
            sc.nextLine();

            ////
            double[] Ndistances = new double[N];

            for (int i = 0; i < N; i++) {
                int Xi = sc.nextInt();
                int Yi = sc.nextInt();
                Ndistances[i] = Math.sqrt((Xi * Xi) + (Yi * Yi));

                sc.nextLine();
            }

            int M = sc.nextInt();
            sc.nextLine();
            double[] Mdistances = new double[M];

            for (int i = 0; i < M; i++) {
                int Zi = sc.nextInt();
                int Wi = sc.nextInt();

                Mdistances[i] = Math.sqrt((Zi * Zi) + (Wi * Wi));

                sc.nextLine();
            }

            Arrays.sort(Ndistances);
            Arrays.sort(Mdistances);

            int Nans = 0;
            int Mans = 0;

            if (N == 0) {
                for (int i = 0; i < M; i++) {
                    if (Mdistances[i] <= Rs + Rh) {
                        Mans++;
                    }
                }
            } else if (M == 0) {
                for (int i = 0; i < N; i++) {
                    if (Ndistances[i] <= Rs + Rh) {
                        Nans++;
                    }
                }
            } else {
                if (Mdistances[0] > Ndistances[0]) {
                    for (int i = 0; i < N; i++) {
                        if (Ndistances[i] < Mdistances[0] && Ndistances[i] <= Rs + Rh) {
                            Nans++;
                        }
                    }
                } else {
                    for (int i = 0; i < M; i++) {
                        if (Mdistances[i] < Ndistances[0] && Mdistances[i] <= Rs + Rh) {
                            Mans++;
                        }
                    }
                }
            }


            /////

            System.out.println("Case #" + caseId + ": " + Nans + " " + Mans);
        }
    }

    private static int solve(int N, int K, int[] B) {
        return 0;
    }
}
