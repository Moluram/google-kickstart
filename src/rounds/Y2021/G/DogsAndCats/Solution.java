package rounds.Y2021.G.DogsAndCats;

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
            int D = sc.nextInt();
            BigInteger C = BigInteger.valueOf(sc.nextInt());
            BigInteger M = BigInteger.valueOf(sc.nextInt());

            BigInteger edge = BigInteger.valueOf(10).pow(5);

            sc.nextLine();
            String order = sc.nextLine();
            ////

            boolean anw = true;
            boolean skip = false;

            for (int i = 0; i < N; i++) {
                final char dogOrCat = order.charAt(i);

                if (dogOrCat == 'D') {
                    D--;

                    if (skip || D < 0) {
                        anw = false;
                        break;
                    }

                    if (C.compareTo(edge) < 0) {
                        C = C.add(M);
                    }
                }

                if (dogOrCat == 'C') {
                    C = C.subtract(BigInteger.ONE);

                    if (C.compareTo(BigInteger.ZERO) < 0) {
                        skip = true;
                    }
                }
            }

            /////
            System.out.println("Case #" + caseId + ": " + (anw ? "YES" : "NO"));
        }
    }
}
