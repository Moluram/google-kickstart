package rounds.Y2020.B.BusRoutes;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int numCases = sc.nextInt();

        for (int caseId = 1; caseId <= numCases; caseId++) {
            int N = sc.nextInt();
            BigInteger D = BigInteger.valueOf(sc.nextLong());

            BigInteger[] busSchedule = new BigInteger[N];

            for (int i = 0; i < N; i++) {
                busSchedule[i] = BigInteger.valueOf(sc.nextLong());
            }
            ////

            int i = N - 1;

            while (i >= 0) {
                final BigInteger currentBusStop = busSchedule[i];

                final BigInteger mod = D.mod(currentBusStop);

                if (mod.equals(BigInteger.ZERO)) {
                    i--;
                    continue;
                }

                D = D.subtract(mod);
                i--;
            }

            /////
            System.out.println("Case #" + caseId + ": " + D);
        }
    }
}
