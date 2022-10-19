package rounds.Y2022.G.CuteLittleButterfly;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int numCases = sc.nextInt();
        sc.nextLine();
        for (int caseId = 1; caseId <= numCases; caseId++) {
            int N = sc.nextInt();
            int E = sc.nextInt();
            sc.nextLine();

            ////

            Map<String, Flower> flowers = new HashMap<>();

            for (int i = 0; i < N; i++) {
                int x = sc.nextInt();
                int y = sc.nextInt();
                int e = sc.nextInt();

                flowers.put("x: " + x + " y: " + y, new Flower(x, y, e));
            }


            int ans = moveButterfly(N, E, 0, 599, flowers, true);

            /////

            System.out.println("Case #" + caseId + ": " + ans);
        }
    }

    private static class Flower {
        int x = 0;
        int y = 0;
        int energy = 0;

        public Flower(int x, int y, int energy) {
            this.x = x;
            this.y = y;
            this.energy = energy;
        }
    }

    private static int moveButterfly(int N, int E, int x, int y, Map<String, Flower> flowers, boolean faceRight) {
        if (flowers.isEmpty()) return 0;

        int max = 0;

        final var entries = new HashSet<>(flowers.entrySet());

        for (Map.Entry<String, Flower> entry: entries) {
            final var flower = entry.getValue();
            final var flowerName = entry.getKey();

            if (flower.y > y) continue;
            flowers.remove(flowerName);

            int nextFlowerEnergy = moveButterfly(N, E, flower.x, flower.y, flowers, flower.x == x ? faceRight : flower.x > x)
                + flower.energy;

            if (flower.x < x && faceRight) nextFlowerEnergy -= E;
            if (flower.x > x && !faceRight) nextFlowerEnergy -= E;

            max = Math.max(max, nextFlowerEnergy);

            flowers.put(flowerName, flower);
        }

        return max;
    }
}
