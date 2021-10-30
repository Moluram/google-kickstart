package rounds.Y2021.C.RockPaperScissors;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public class Solution {

    public static final Random RANDOM = new Random();

    private static String solve(int X, int W, int E) {
        var memo = (Entry<Double, Integer>[][][]) new Entry[62][62][62];

        for (int a = 0; a <= 60; ++a) {
            for (int b = 0; a + b <= 60; ++b) {
                memo[a][b][60 - (a + b)] = entry(0., -1);
            }
        }

        for (int a = 60; a >= 0; a--) {
            for (int b = 60 - a; b >= 0; b--) {
                for (int c = 60 - b - a - 1; c >= 0; c--) {
                    double p_a = 1. / 3;
                    double p_b = 1. / 3;
                    double p_c = 1. / 3;

                    if (a + b + c != 0) {
                        p_a = (double) b / (a + b + c);
                        p_b = (double) c / (a + b + c);
                        p_c = (double) a / (a + b + c);
                    }

                    var tmp = memo[a + 1][b][c].first + p_a * E + p_b * W;
                    memo[a][b][c] = max(memo[a][b][c], entry(tmp, 0));

                    tmp = memo[a][b + 1][c].first + p_b * E + p_c * W;
                    memo[a][b][c] = max(memo[a][b][c], entry(tmp, 1));

                    tmp = memo[a][b][c + 1].first + p_c * E + p_a * W;
                    memo[a][b][c] = max(memo[a][b][c], entry(tmp, 2));
                }
            }
        }

        String maxS = "";

        int a = 0, b = 0, c = 0;
        String s = "RSP";
        while (a + b + c < 60) {
            final Integer way = memo[a][b][c].second;

            if (way == 0) a++;
            else if (way == 1) b++;
            else c++;

            maxS += s.charAt(way);
        }

        return maxS;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int numCases = sc.nextInt();
        sc.nextLine();
        var X = sc.nextInt();
        for (int caseId = 1; caseId <= numCases; caseId++) {
            var W = sc.nextInt();
            var E = sc.nextInt();
            ////

            var ans = solve(caseId, W, E);

            /////
            System.out.println("Case #" + caseId + ": " + ans);
        }
    }

    private static Entry<Double, Integer> max(
            Entry<Double, Integer> o1,
            Entry<Double, Integer> o2) {
        if (o1 == null) return o2;
        if (o2 == null) return o1;

        return o1.first > o2.first ? o1 : o2;
    }

    private static class Entry<T1, T2> {
        T1 first;
        T2 second;

        public Entry(T1 first, T2 second) {
            this.first = first;
            this.second = second;
        }

        @Override
        public String toString() {
            return "(" + first +
                    ", " + second +
                    ')';
        }
    }

    private static <T1, T2> Entry<T1, T2> entry(T1 t1, T2 t2) {
        return new Entry<>(t1, t2);
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

    private static BigDecimal combinations(int x, int y) {
        return fact(x + y - 2).divide(fact(x - 1).multiply(fact(y - 1)));
    }

    public static BigDecimal fact(int n) {
        BigDecimal r = BigDecimal.valueOf(n);

        if (n == 0) return BigDecimal.ONE;
        if (n <= 2) {
            return r;
        }

        for (int i = n - 1; i > 1; i--) {
            r = r.multiply(BigDecimal.valueOf(i));
        }

        return r;
    }
}
