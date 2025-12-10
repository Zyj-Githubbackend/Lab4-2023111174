package org.example;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 二分可能性：用并查集把“同一节点的所有不喜欢者”并到同一组，
 * 同时检查节点 i 是否与其不喜欢者 v 落在同一集合；若同集则无法二分。
 */
public class Solution9 {

    public boolean possibleBipartition(int n, int[][] dislikes) {
        int[] fa = new int[n + 1];
        Arrays.fill(fa, -1); // fa[root] 为负数，表示集合大小的相反数；非根结点存父指针

        // 1. 建图（1..n）
        List<Integer>[] g = new List[n + 1];
        for (int i = 1; i <= n; ++i) g[i] = new ArrayList<>();
        for (int[] p : dislikes) {
            int a = p[0], b = p[1];
            g[a].add(b);
            g[b].add(a);
        }

        // 2. 对每个点 i：把它所有“敌人”合并到同一集合；并校验 i 与任一敌人不在同一集合
        for (int i = 1; i <= n; ++i) {
            if (g[i].isEmpty()) continue;
            int first = g[i].get(0);
            for (int v : g[i]) {
                if (isConnect(i, v, fa)) return false; // i 和敌人同集 → 不可二分
                union(first, v, fa);                    // 敌人之间并到同一集（与 i 相对的那一组）
            }
        }
        return true;
    }

    private void union(int x, int y, int[] fa) {
        x = find(x, fa);
        y = find(y, fa);
        if (x == y) return;
        // 按集合大小合并：fa[root] 越小（更负）代表集合越大，让大的当根
        if (fa[x] > fa[y]) { int t = x; x = y; y = t; }
        fa[x] += fa[y];
        fa[y] = x;
    }

    private boolean isConnect(int x, int y, int[] fa) {
        return find(x, fa) == find(y, fa);
    }

    private int find(int x, int[] fa) {
        if (fa[x] < 0) return x;           // 负数表示根
        return fa[x] = find(fa[x], fa);    // 路径压缩
    }
}
