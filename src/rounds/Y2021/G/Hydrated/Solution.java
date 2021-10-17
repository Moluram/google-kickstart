package rounds.Y2021.G.Hydrated;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Solution {

    private static final BigInteger edge = BigInteger.valueOf(10).pow(9);

    public static void main(String[] args) {
        Scanner sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int numCases = sc.nextInt();
        sc.nextLine();
        for (int caseId = 1; caseId <= numCases; caseId++) {
            int K = sc.nextInt();

            List<BigInteger> xs = new ArrayList<>();
            List<BigInteger> ys = new ArrayList<>();

            for (int i = 0; i < K; i++) {
                xs.add(BigInteger.valueOf(sc.nextLong()));
                ys.add(BigInteger.valueOf(sc.nextLong()));
                xs.add(BigInteger.valueOf(sc.nextLong()));
                ys.add(BigInteger.valueOf(sc.nextLong()));
            }

            ////

            xs.sort(BigInteger::compareTo);
            ys.sort(BigInteger::compareTo);

            BigInteger x = xs.get(xs.size() / 2 - 1);
            BigInteger y = ys.get(ys.size() / 2 - 1);

            /////
            System.out.println("Case #" + caseId + ": " + x + " " + y);
        }
    }
}
