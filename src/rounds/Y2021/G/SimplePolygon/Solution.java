package rounds.Y2021.G.SimplePolygon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Solution {
    private static List<Vertex> solve(int N, int A) {
        if (A < N - 2) {
            return Collections.emptyList();
        }

        final LinkedList<Vertex> vertex = new LinkedList<>();

        vertex.addLast(new Vertex(0, 0));
        vertex.addLast(new Vertex(0, A - N + 3));
        vertex.addLast(new Vertex(1, 1));

        if (N == 3) {
            return vertex;
        }

        vertex.addFirst(new Vertex(1, 0));

        N -= 4;

        int x = 2;

        boolean up = true;

        while (N > 0) {
            N--;
            int y = up ? 1 : 0;
            up = !up;

            vertex.addLast(new Vertex(x, y + 1));

            if (N == 0) {
                break;
            }

            N--;
            vertex.addFirst(new Vertex(x++, y));
        }

        while (true) {
            final Vertex vertex1 = vertex.getFirst();
            if (vertex1.x == 0 && vertex1.y == 0) break;
            vertex.addLast(vertex1);
            vertex.removeFirst();
        }

        return vertex;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int numCases = sc.nextInt();
        sc.nextLine();
        for (int caseId = 1; caseId <= numCases; caseId++) {
            int N = sc.nextInt();
            int A = sc.nextInt();

            ////

            List<Vertex> ans = solve(N, A);

            /////
            System.out.println("Case #" + caseId + ": " + (!ans.isEmpty() ? "POSSIBLE" : "IMPOSSIBLE") );

            ans.forEach(v -> {
                System.out.print(v.x);
                System.out.print(" ");
                System.out.println(v.y);
            });
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
