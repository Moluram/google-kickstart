package rounds.Y2020.A.Bundling;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.AbstractMap.SimpleEntry;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;
import javax.swing.tree.TreeNode;

public class Solution {

    private static long solve(int n, int k, String[] pips) {
        TrieNode root = new TrieNode();

        for (String st: pips) {
            TrieNode current = root;

            for (char ch: st.toCharArray()) {
                final int level = current.level;

                current = current.children.computeIfAbsent(ch, c -> {
                    final TrieNode trieNode = new TrieNode(c);
                    trieNode.level = level + 1;
                    return trieNode;
                });

                current.count++;
            }
        }

        return count(root, k).getValue();
    }

    // used -> score
    private static Entry<Integer, Long> count(TrieNode node, int K) {
        int used = 0;
        long score = 0;

        for (TrieNode n: node.children.values()) {
            final Entry<Integer, Long> count = count(n, K);
            used += count.getKey();
            score += count.getValue();
        }

        final int left = node.count - used;

        if (left >= K) {
            int num = (int) Math.floor(left / K);

            score += (long) node.level * num;
            used += num * K;
        }

        return new SimpleEntry<>(used, score);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int numCases = sc.nextInt();

        for (int caseId = 1; caseId <= numCases; caseId++) {
            int N = sc.nextInt();
            int K = sc.nextInt();

            String[] pips = new String[N];
            sc.nextLine();
            for (int i = 0; i < N; i++) {
                pips[i] = sc.nextLine();
            }
            ////

            long sumOfScores = solve(N, K, pips);

            /////
            System.out.println("Case #" + caseId + ": " + sumOfScores);
        }
    }

    private static class TrieNode {
        char charR;
        private final HashMap<Character, TrieNode> children = new HashMap<>();
        int count;
        int level;

        public TrieNode() {
        }

        public TrieNode(char charR) {
            this.charR = charR;
        }
    }
}
