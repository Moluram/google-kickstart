package rounds.Y2020.A.Allocation;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int numCases = sc.nextInt();

        for (int caseId = 1; caseId <= numCases; caseId++) {
            int numHouses = sc.nextInt();
            int budget = sc.nextInt();

            int[] housesPrices = new int[numHouses];

            for (int i = 0; i < numHouses; i++) {
                housesPrices[i] = sc.nextInt();
            }
            ////
            Arrays.sort(housesPrices);

            int numOfBoughtHouses = 0;

            for (int i = 0; i < numHouses; i++) {
                if (budget >= housesPrices[i]) {
                    budget -= housesPrices[i];
                    numOfBoughtHouses++;
                } else {
                    break;
                }
            }

            /////
            System.out.println("Case #" + caseId + ": " + numOfBoughtHouses);
        }
    }
}
