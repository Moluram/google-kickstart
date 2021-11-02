package rounds.Y2021.C.BinaryOperator;

import static java.lang.Character.isDigit;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public class Solution {
    private static Map<Pair<BigInteger, BigInteger>, BigInteger> memo =
            new HashMap<>();

    private static List<Integer> solve(int N, String[] E) {
        var result = new ArrayList<Integer>();

        final HashMap<Integer, BigInteger> map = new HashMap<>();

        for (int i = 0; i < N; i++) {
            BigInteger a = rec(E[i], 0, E[i].length() - 1);
            map.put(i, a);
        }

        int count = 1;

        Map<BigInteger, Integer> m = new HashMap<>();

        for (int i = 0; i < N; i++) {
            final BigInteger key = map.get(i);

            if (m.containsKey(key)) {
                result.add(m.get(key));
            } else {
                result.add(count);
                m.put(key, count++);
            }
        }

        return result;
    }

    private static BigInteger rec(String s, int l, int r) {
        if (s.charAt(l) == '(') {
            int balance = 1;
            int i = l + 1;

            while (balance > 0) {
                if (s.charAt(i) == '(') {
                    balance++;
                }
                if (s.charAt(i) == ')') {
                    balance--;
                }
                i++;
            }

            BigInteger left = rec(s, l + 1, i - 2);

            if (i >= r) return left;

            BigInteger right = rec(s,  i + 1, r);

            return merge(left, right, s.charAt(i));
        }

        int a = l;

        while (a < r && isDigit(s.charAt(a))) a++;

        if (a >= r) return new BigInteger(s.substring(l, a + 1));

        final BigInteger bigInteger = new BigInteger(s.substring(l, a));

        BigInteger b = rec(s,a + 1, r);

        return merge(bigInteger, b, s.charAt(a));
    }

    private static BigInteger merge(BigInteger left, BigInteger right, char ope) {
        if (ope == '+') {
            return left.add(right);
        }

        if (ope == '*') {
            return left.multiply(right);
        }

        var pair = pair(left, right);

        if (memo.containsKey(pair)) return memo.get(pair);

        final double v = 1.e200 + (1.e300 - 1.e200) * new Random().nextDouble();
        memo.put(pair, BigDecimal.valueOf(v).toBigInteger());
        return memo.get(pair);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int numCases = sc.nextInt();
        sc.nextLine();
        for (int caseId = 1; caseId <= numCases; caseId++) {
            memo = new HashMap<>();
            var N = sc.nextInt();
            String[] E = new String[N];

            sc.nextLine();
            for (int i = 0; i < N; i++) {
                E[i] = sc.nextLine();
            }
            ////

            var ans = solve(N, E);

            /////
            System.out.print("Case #" + caseId + ": ");
            ans.forEach(a -> System.out.print(a + " "));
            System.out.println();
        }
    }


    private static Pair<Double, Integer> max(
            Pair<Double, Integer> o1,
            Pair<Double, Integer> o2) {
        if (o1 == null) return o2;
        if (o2 == null) return o1;

        return o1.first > o2.first ? o1 : o2;
    }

    private static class Pair<T1, T2> {
        T1 first;
        T2 second;

        public Pair(T1 first, T2 second) {
            this.first = first;
            this.second = second;
        }

        @Override
        public String toString() {
            return "(" + first +
                    ", " + second +
                    ')';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Pair<?, ?> pair = (Pair<?, ?>) o;
            return Objects.equals(first, pair.first) && Objects.equals(second,
                    pair.second);
        }

        @Override
        public int hashCode() {
            return Objects.hash(first, second);
        }
    }

    private static <T1, T2> Pair<T1, T2> pair(T1 t1, T2 t2) {
        return new Pair<>(t1, t2);
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
