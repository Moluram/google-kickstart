package rounds.Y2020.B.BikeTour;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int numCases = sc.nextInt();

        for (int caseId = 1; caseId <= numCases; caseId++) {
            int N = sc.nextInt();

            int[] heights = new int[N];

            for (int i = 0; i < N; i++) {
                heights[i] = sc.nextInt();
            }
            ////
            int numOfHeights = 0;

            for (int i = 1; i < N - 1; i++) {
                if (heights[i] > heights[i + 1] && heights[i] > heights[i - 1]) {
                    numOfHeights++;
                    i++;
                }
            }

            /////
            System.out.println("Case #" + caseId + ": " + numOfHeights);
        }
    }
}
