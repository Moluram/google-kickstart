package rounds.Y2021.A.RabbitHouse;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.TreeSet;

public class Solution {

    private static long solve(int[][] points, int R, int C) {
        if (R == 1 && 1 == C) {
            return 0;
        }

        long ans = 0;

        boolean[][] been = new boolean[R][C];
        TreeMap<Integer, List<Vertex>> stack = new TreeMap<>();

        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                final int key = points[i][j];
                final Vertex e = new Vertex(i, j);

                addE(stack, key, e);
            }
        }

        while (!stack.isEmpty()) {
            final List<Vertex> v = stack.pollLastEntry().getValue();

            if (v.isEmpty()) {
                continue;
            }

            for (Vertex n : v) {
                if (been[n.x][n.y]) {
                    continue;
                }

                been[n.x][n.y] = true;

                final int xM = n.x - 1;
                final int xP = n.x + 1;
                final int YM = n.y - 1;
                final int YP = n.y + 1;

                final int newValue = points[n.x][n.y] - 1;

                if (n.x > 0 && !been[xM][n.y] && newValue > points[xM][n.y]) {
                    ans += newValue - points[xM][n.y];
                    points[xM][n.y] = newValue;
                    addE(stack, newValue, new Vertex(xM, n.y));
                }

                if (n.x < R - 1 && !been[xP][n.y] && newValue > points[xP][n.y]) {
                    ans += newValue - points[xP][n.y];
                    points[xP][n.y] = newValue;
                    addE(stack, newValue, new Vertex(xP, n.y));
                }

                if (n.y > 0 && !been[n.x][YM] && newValue > points[n.x][YM]) {
                    ans += newValue - points[n.x][YM];
                    points[n.x][YM] = newValue;
                    addE(stack, newValue, new Vertex(n.x, YM));
                }

                if (n.y < C - 1 && !been[n.x][YP] && newValue > points[n.x][YP]) {
                    ans += newValue - points[n.x][YP];
                    points[n.x][YP] = newValue;
                    addE(stack, newValue, new Vertex(n.x, YP));
                }
            }
        }

        return ans;
    }

    private static void addE(TreeMap<Integer, List<Vertex>> stack, int key, Vertex e) {
        if (stack.containsKey(key)) {
            stack.get(key).add(e);
        } else {
            final ArrayList value = new ArrayList<>();
            value.add(e);
            stack.put(key, value);
        }
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

            long ans = solve(points, R, C);

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

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Vertex vertex = (Vertex) o;
            return x == vertex.x && y == vertex.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
}
