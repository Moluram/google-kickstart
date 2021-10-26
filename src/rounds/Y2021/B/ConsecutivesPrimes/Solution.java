package rounds.Y2021.B.ConsecutivesPrimes;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Scanner;

public class Solution {
    private static BigInteger solve(BigInteger Z) {
        BigInteger sqrt = Z.sqrt();

        BigInteger minOne = null;
        BigInteger minTwo = null;
        BigInteger bigOne = null;

        for (BigInteger i = sqrt.add(BigInteger.ONE); i.compareTo(Z) <= 0; i = i.add(BigInteger.ONE)) {
            if (i.isProbablePrime(100))  {
                bigOne = i;
                break;
            }
        }

        for (BigInteger i = sqrt; i.compareTo(Z) <= 0; i = i.subtract(BigInteger.ONE)) {
            if (i.isProbablePrime(100))  {
                if (minOne == null) {
                    minOne = i;
                } else {
                    minTwo = i;
                    break;
                }
            }
        }

        BigInteger m2m1 = minTwo.multiply(minOne);
        BigInteger m1b1 = minOne.multiply(bigOne);

        BigInteger m2m1C = Z.subtract(m2m1);
        BigInteger m1b1C = Z.subtract(m1b1);

        if (m1b1C.compareTo(BigInteger.ZERO) < 0) {
            return m2m1;
        }

        if (m1b1C.compareTo(m2m1C) < 0) {
            return m1b1;
        } else {
            return m2m1;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int numCases = sc.nextInt();
        sc.nextLine();
        for (int caseId = 1; caseId <= numCases; caseId++) {
            BigDecimal Z = sc.nextBigDecimal();
            ////

            BigInteger ans = solve(Z.toBigInteger());

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
