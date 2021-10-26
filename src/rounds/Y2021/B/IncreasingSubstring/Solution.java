package rounds.Y2021.B.IncreasingSubstring;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Solution {
    private static List<Integer> solve(int n, String s) {
        List<Integer> result = new ArrayList<>();
        int count = 1;

        result.add(count);

        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) > s.charAt(i - 1)) {
                count++;
            } else {
                count = 1;
            }

            result.add(count);
        }

        return result;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int numCases = sc.nextInt();
        sc.nextLine();
        for (int caseId = 1; caseId <= numCases; caseId++) {
            int N = sc.nextInt();
            sc.nextLine();
            String string = sc.nextLine();

            ////

            List<Integer> ans = solve(N, string);

            /////
            System.out.print("Case #" + caseId + ": ");
            for (int i : ans) {
                System.out.print(i);
                System.out.print(" ");
            }
            System.out.println("");
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
