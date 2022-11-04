package rounds.Y2022.G.HappySubarrays;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class Solution {
        public static BufferedReader br = new BufferedReader(
            new InputStreamReader(System.in));
        public static StringTokenizer st;

        public static String next() {
            while (st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        public static int nextInt() { return Integer.parseInt(next()); }

        public static long nextLong() { return Long.parseLong(next()); }

        public static double nextDouble()
        {
            return Double.parseDouble(next());
        }

        public static String nextLine()
        {
            String str = "";
            try {
                if(st.hasMoreTokens()){
                    str = st.nextToken("\n");
                }
                else{
                    str = br.readLine();
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            return str;
        }
    public static void main(String[] args) {
        int numCases = nextInt();
        for (int caseId = 1; caseId <= numCases; caseId++) {
            int N = nextInt();

            ////

            int[] P = new int[N + 1];
            int[] PP = new int[N + 1];
            for (int i = 1; i <= N; i++) {
                P[i] = P[i - 1] + nextInt();
                PP[i] = PP[i - 1] + P[i];
            }
            int[] nsv = new int[N + 1];
            nsv[0] = N + 1;
            LinkedList<Integer> q = new LinkedList<>();
            q.add(0);
            for(int i=1;i<=N;++i) {
                nsv[i] = N + 1;
                while (!q.isEmpty() && P[i] < P[q.getLast()]) {
                    nsv[q.getLast()] = i;
                    q.pollLast();
                }
                q.addLast(i);
            }

//            for (int i = N; i > 0; i--) {
//                while (!q.isEmpty() && P[q.getLast()] > P[i - 1]) {
//                    q.pollLast();
//                }
//
//                if (q.isEmpty()) {
//                    nsv[i] = N + 1;
//                } else {
//                    nsv[i] = q.getLast();
//                }
//
//                q.addLast(i);
//            }


            int ans = 0;

            for(int i=1;i<=N;++i) {
                int rx = nsv[i - 1] - 1;
                // i ... rx (i-1)
                ans -= P[i - 1] * (rx - i + 1);
                ans += PP[rx] - PP[i - 1];
            }
//            for (int l = 1; l <= N; l++) {
//                int r = nsv[l] - 1;
//                ans += PP[r] - PP[l - 1] - (r - l + 1) * P[l - 1];
//            }

            /////

            System.out.println("Case #" + caseId + ": " + ans);
        }
    }

    private static int solve(int N, int K, int[] B) {
        return 0;
    }
}
