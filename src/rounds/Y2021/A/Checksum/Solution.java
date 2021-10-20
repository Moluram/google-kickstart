package rounds.Y2021.A.Checksum;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class Solution {

    private static long solve(int[][] B, int N) {
        int[] R = new int[N];
        int[] C = new int[N];

        LinkedList<Vertex2D> edges = new LinkedList<>();

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (B[i][j] != 0) {
                    edges.add(new Vertex2D(i, j));
                }
            }
        }

        edges.sort((v1, v2) -> Integer.compare(B[v1.x][v1.y], B[v2.x][v2.y]) * -1);

        int setCount = 1;

        int anw = 0;
        while (!edges.isEmpty()) {
            final Vertex2D edge = edges.pop();

            if (R[edge.x] == 0 && C[edge.y] == 0) {
                R[edge.x] = setCount;
                C[edge.y] = setCount++;
                continue;
            }

            if (R[edge.x] == 0 && C[edge.y] != 0) {
                R[edge.x] = C[edge.y];
                continue;
            } else if (R[edge.x] != 0 && C[edge.y] == 0) {
                C[edge.y] = R[edge.x];
                continue;
            }

            if (R[edge.x] == C[edge.y]) {
                anw += B[edge.x][edge.y];
            } else {
                int old = R[edge.x];
                for (int i = 0; i < N; i++) {
                    if (R[i] == old) {
                        R[i] = C[edge.y];
                    }
                    if (C[i] == old) {
                        C[i] = C[edge.y];
                    }
                }
            }
        }

        return anw;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int numCases = sc.nextInt();
        sc.nextLine();
        for (int caseId = 1; caseId <= numCases; caseId++) {
            int N = sc.nextInt();

            int[][] A = new int[N][N];
            int[][] B = new int[N][N];
            int[] R = new int[N];
            int[] C = new int[N];

            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    A[i][j] = sc.nextInt();
                }
            }
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    B[i][j] = sc.nextInt();
                }
            }
            for (int i = 0; i < N; i++) {
                R[i] = sc.nextInt();
            }
            for (int i = 0; i < N; i++) {
                C[i] = sc.nextInt();
            }

            ////

            long ans = solve(B, N);

            /////
            System.out.println("Case #" + caseId + ": " + ans);
        }
    }

    private static class Vertex2D {
        int x;
        int y;

        public Vertex2D(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Vertex2D vertex = (Vertex2D) o;
            return x == vertex.x && y == vertex.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
}
