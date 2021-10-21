package rounds.Y2020.A.Workout;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.Vector;

public class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int numCases = sc.nextInt();

        for (int caseId = 1; caseId <= numCases; caseId++) {
            int N = sc.nextInt();
            int K = sc.nextInt();

            long[] diffs = new long[N + 1];

            long prev = 0;
            for (int i = 1; i <= N; i++) {
                final long current = sc.nextInt();

                if (i == 1) {
                    prev = current;
                    continue;
                }
                diffs[i - 1] = current - prev;
                prev = current;
            }

            ////

            long dopt = solve(diffs, N, K);

            /////
            System.out.println("Case #" + caseId + ": " + dopt);
        }
    }

    private static long solve(long[] diffs, int n, int k) {
        final TreeMap<Long, List<Difference>> tree = new TreeMap<>();

        for (long diff: diffs) {
            tree.merge(diff, List.of(new Difference(diff)), (o, nn) -> {
                o.get(0).count++;
                return o;
            });
        }

        while (k > 0) {
            List<Difference> value = tree.pollLastEntry().getValue();

            for (Difference v: value) {
                k -= v.count;

                if (k < 0) {
                    return v.current;
                }

                v.k++;
                v.current = (int) Math.ceil(v.diff / (double) v.k);

                tree.merge(v.current, List.of(v), (o, neww) -> {
                    final ArrayList<Difference> vs = new ArrayList<>(o);
                    vs.addAll(neww);
                    return vs;
                });
            }
        }

        return tree.lastKey();
    }

    private static class Difference {
        long diff;
        int k = 1;
        long current;
        int count = 1;

        public Difference(long diff) {
            this.diff = diff;
            this.current = diff;
        }
    }
}

