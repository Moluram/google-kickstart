package rounds.Y2021.B.TruckDelivery;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class Solution {
    static List<Long> solve(UnGraph cities, int[] c, int[] w) {
        var result = new ArrayList<Q>();

        var cityToW = new HashMap<Integer, List<Q>>();
        for (int i = 0; i < c.length; i++) {
            var qs = new ArrayList<Q>();
            final Q e = new Q(c[i], w[i], i);
            qs.add(e);
            cityToW.merge(e.C, qs, (o, n) -> {
                o.addAll(n);
                return o;
            });
        }

        cities.DFSPreorderR(1, (city, edges) -> {
            if (!cityToW.containsKey(city)) {
                return;
            }

            final List<Q> queries = cityToW.remove(city);
            queries.forEach(q -> {
                q.result = edges.query(1, q.W);

                result.add(q);
            });
        });

        return result.stream()
                .sorted(Comparator.comparingInt(q -> q.order))
                .map(q -> q.result)
                .collect(Collectors.toList());
    }

    static class Q {

        int C;
        int W;
        int order;
        long result = 0;

        public Q(int c, int w, int order) {
            C = c;
            W = w;
            this.order = order;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int numCases = sc.nextInt();
        sc.nextLine();
        for (int caseId = 1; caseId <= numCases; caseId++) {
            int N = sc.nextInt();
            int Q = sc.nextInt();

            var edges = new ArrayList<Edge>();
            for (int i = 0; i < N - 1; i++) {
                int X = sc.nextInt();
                int Y = sc.nextInt();
                int L = sc.nextInt();
                long A = sc.nextLong();

                edges.add(new Edge(A, X, Y, L));
            }

            int[] C = new int[Q];
            int[] W = new int[Q];

            for (int i = 0; i < Q; i++) {
                C[i] = sc.nextInt();
                W[i] = sc.nextInt();
            }

            var cities = new UnGraph(N, edges);
            ////

            var ans = solve(cities, C, W);

            /////
            System.out.print("Case #" + caseId + ": ");
            ans.forEach(i -> System.out.print(i + " "));
            System.out.println();
        }
    }

    static class UnGraph {

        List<Edge>[] adjacencyList;

        public UnGraph(int size, List<Edge> edges) {
            adjacencyList = new List[size + 1];
            for (int i = 0; i <= size; i++) {
                adjacencyList[i] = new ArrayList<>();
            }

            for (Edge edge : edges) {
                adjacencyList[edge.src].add(edge);
                adjacencyList[edge.dest].add(edge);
            }
        }

        void DFSPreorderR(int start, BiConsumer<Integer, SegmentTree> visit) {
            DFSPreorderR(start, new boolean[adjacencyList.length],
                    new SegmentTree(SEGMENT_TREE_SIZE, Solution::gcd), visit);
        }

        private void DFSPreorderR(int current, boolean[] isVisited, SegmentTree edgesStack,
                BiConsumer<Integer, SegmentTree> visit) {
            if (isVisited[current]) {
                return;
            }

            visit.accept(current, edgesStack);

            isVisited[current] = true;

            for (Edge dest : adjacencyList[current]) {
                boolean visited = false;
                if (!isVisited[dest.src]) {
                    if (!visited)  {
                        edgesStack.update(dest.weightL, dest.A);
                        visited = true;
                    }

                    DFSPreorderR(dest.src, isVisited, edgesStack, visit);
                }
                if (!isVisited[dest.dest]) {
                    if (!visited)  {
                        edgesStack.update(dest.weightL, dest.A);
                        visited = true;
                    }

                    DFSPreorderR(dest.dest, isVisited, edgesStack, visit);
                }

                if (visited) {
                    edgesStack.update(dest.weightL, 0);
                }
            }
        }

    }

    static class Edge {

        long A;
        int src, dest, weightL;

        public Edge(long a, int src, int dest, int weightL) {
            A = a;
            this.src = src;
            this.dest = dest;
            this.weightL = weightL;
        }
    }

    static int SEGMENT_TREE_SIZE = (int) Math.pow(10, 5) * 3;

    static class SegmentTree {

        private final BiFunction<Long, Long, Long> merge;
        int size;
        long[] st;

        public SegmentTree(int size, BiFunction<Long, Long, Long> merge) {
            this.merge = merge;
            int x = (int) (Math.ceil(Math.log(size) / Math.log(2)));

            st = new long[2 * (int) Math.pow(2, x) - 1];
            this.size = size;
        }

        public void update(int id, long value) {
            updateR(0, size - 1, 0, id, value);
        }

        private long updateR(int rangeS, int rangeE, int currentI, int nodeToUpdate,
                long value) {
            if (nodeToUpdate < rangeS || nodeToUpdate > rangeE) {
                return st[currentI];
            }

            if (rangeE != rangeS) {
                int mid = calcMid(rangeS, rangeE);

                final long left = updateR(rangeS, mid, 2 * currentI + 1, nodeToUpdate, value);
                final long right = updateR(mid + 1, rangeE, 2 * currentI + 2, nodeToUpdate, value);

                st[currentI] = merge.apply(left, right);
            } else {
                st[currentI] = value;
            }

            return st[currentI];
        }

        public long query(int rangeS, int rangeE) {
            return queryR(0, size - 1, rangeS, rangeE, 0);
        }

        private long queryR(int rangeS, int rangeE, int qRangeS, int qRangeE, int currentI) {
            if (qRangeS <= rangeS && qRangeE >= rangeE) {
                return st[currentI];
            }

            if (rangeS > qRangeE || rangeE < qRangeS) {
                return 0;
            }

            final int mid = calcMid(rangeS, rangeE);
            final long right = queryR(mid + 1, rangeE, qRangeS, qRangeE, currentI * 2 + 2);
            final long left = queryR(rangeS, mid, qRangeS, qRangeE, currentI * 2 + 1);
            return merge.apply(left, right);
        }

        int calcMid(int start, int end) {
            return start + (end - start) / 2;
        }
    }

    static long gcd(long a, long b) {
        if (a == 0)
            return b;

        return gcd(b % a, a);
    }
}
