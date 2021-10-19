package rounds.Y2021.A.LShapedPlots;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Solution {
    private static int solve(int[][] points, int R, int C) {
        int[][] psaRR = new int[R][C];
        int[][] psaRL = new int[R][C];
        int[][] psaCU = new int[R][C];
        int[][] psaCD = new int[R][C];

        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                if (j == 0) {
                    psaRR[i][j] = points[i][j];
                    continue;
                }
                if (points[i][j] == 0) continue;
                psaRR[i][j] = points[i][j] + psaRR[i][j - 1];
            }
            for (int j = C - 1; j >= 0; j--) {
                if (j == C - 1) {
                    psaRL[i][j] = points[i][j];
                    continue;
                }
                if (points[i][j] == 0) continue;
                psaRL[i][j] = points[i][j] + psaRL[i][j + 1];
            }
        }

        for (int i = 0; i < C; i++) {
            for (int j = 0; j < R; j++) {
                if (j == 0) {
                    psaCD[j][i] = points[j][i];
                    continue;
                }
                if (points[j][i] == 0) continue;
                psaCD[j][i] = points[j][i] + psaCD[j - 1][i];
            }
            for (int j = R - 1; j >= 0; j--) {
                if (j == R - 1) {
                    psaCU[j][i] = points[j][i];
                    continue;
                }
                if (points[j][i] == 0) continue;
                psaCU[j][i] = points[j][i] + psaCU[j + 1][i];
            }
        }

        int ans = 0;

        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                if (psaRL[i][j] > 1 || psaRR[i][j] > 1) {
                    for (int k = 2; k <= psaCD[i][j]; k++) {
                        if (psaRL[i][j] > 1) {
                            if (psaRL[i][j] >= k * 2) ans++;
                            if (k > 3 && k % 2 == 0 && psaRL[i][j] >= k / 2) ans++;
                        }
                        if (psaRR[i][j] > 1) {
                            if (psaRR[i][j] >= k * 2) ans++;
                            if (k > 3 && k % 2 == 0 && psaRR[i][j] >= k / 2) ans++;
                        }
                    }
                    for (int k = 2; k <= psaCU[i][j]; k++) {
                        if (psaRL[i][j] > 1) {
                            if (psaRL[i][j] >= k * 2) ans++;
                            if (k > 3 && k % 2 == 0 && psaRL[i][j] >= k / 2) ans++;
                        }
                        if (psaRR[i][j] > 1) {
                            if (psaRR[i][j] >= k * 2) ans++;
                            if (k > 3 && k % 2 == 0 && psaRR[i][j] >= k / 2) ans++;
                        }
                    }
                }
            }
        }

        return ans;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int numCases = sc.nextInt();
        sc.nextLine();
        for (int caseId = 1; caseId <= numCases; caseId++) {
            int R = sc.nextInt();
            int C = sc.nextInt();

            int[][] points = new int[R][C];

            for (int i = 0; i < R; i++) {
                for (int j = 0; j < C; j++) {
                    points[i][j] = sc.nextInt();
                }
            }

            ////

            int ans = solve(points, R, C);

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
