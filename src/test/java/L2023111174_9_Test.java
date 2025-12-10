import org.example.Solution9;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * 测试类：L2023111174_9_Test
 *更改
 * 一、测试用例设计的总体原则
 * 1) 等价类划分：覆盖「可二分」与「不可二分」两大等价类；在「可二分」中再细分为空图、链式图、偶环、星型图、多连通分量等典型结构；
 *    在「不可二分」中覆盖最小奇环(三角形)与更大奇环。
 * 2) 边界值分析：n=1、n=2、无 dislikes、单条 dislikes、索引在边界(1 与 n)。
 * 3) 典型结构优先：奇环(不可二分)与偶环(可二分)是判定二分图的关键；多连通分量要分别正确处理。
 * 4) 可维护性：每个案例小而清晰，便于快速定位问题；断言只检查核心性质（true/false）。
 */
public class L2023111174_9_Test {

    private final Solution9 s = new Solution9();

    // 小工具：仅为可读性（把字面二维数组“原样”返回）
    private static int[][] E(int[][] edges) { return edges; }

    /**
     * 测试目的：覆盖题目样例1（应可二分）。
     * 用例：n=4, [[1,2],[1,3],[2,4]] → true
     */
    @Test
    public void testSample1_true() {
        int n = 4;
        int[][] dislikes = E(new int[][]{{1,2},{1,3},{2,4}});
        assertTrue(s.possibleBipartition(n, dislikes));
    }

    /**
     * 测试目的：覆盖题目样例2（最小奇环三角形，不可二分）。
     * 用例：n=3, [[1,2],[1,3],[2,3]] → false
     */
    @Test
    public void testSample2_false_triangle() {
        int n = 3;
        int[][] dislikes = E(new int[][]{{1,2},{1,3},{2,3}});
        assertFalse(s.possibleBipartition(n, dislikes));
    }

    /**
     * 测试目的：覆盖题目样例3（长度为5的奇环，不可二分）。
     * 用例：n=5, [[1,2],[2,3],[3,4],[4,5],[1,5]] → false
     */
    @Test
    public void testSample3_false_oddCycle5() {
        int n = 5;
        int[][] dislikes = E(new int[][]{{1,2},{2,3},{3,4},{4,5},{1,5}});
        assertFalse(s.possibleBipartition(n, dislikes));
    }

    /**
     * 测试目的：边界值：无边图（空图）应总是可二分。
     * 用例：n=1, [] → true；n=2, [] → true
     */
    @Test
    public void testEdge_emptyGraph_true() {
        assertTrue(s.possibleBipartition(1, E(new int[][]{})));
        assertTrue(s.possibleBipartition(2, E(new int[][]{})));
    }

    /**
     * 测试目的：单边图（两个节点互相不喜欢）应可二分（各在一组）。
     * 用例：n=2, [[1,2]] → true
     */
    @Test
    public void testSingleEdge_true() {
        int n = 2;
        int[][] dislikes = E(new int[][]{{1,2}});
        assertTrue(s.possibleBipartition(n, dislikes));
    }

    /**
     * 测试目的：偶环应可二分。
     * 用例：n=4, 偶环 1-2-3-4-1 → true
     */
    @Test
    public void testEvenCycle_true() {
        int n = 4;
        int[][] dislikes = E(new int[][]{{1,2},{2,3},{3,4},{4,1}});
        assertTrue(s.possibleBipartition(n, dislikes));
    }

    /**
     * 测试目的：星型结构应可二分（中心一组，叶子另一组）。
     * 用例：n=5, [[1,2],[1,3],[1,4],[1,5]] → true
     */
    @Test
    public void testStar_true() {
        int n = 5;
        int[][] dislikes = E(new int[][]{{1,2},{1,3},{1,4},{1,5}});
        assertTrue(s.possibleBipartition(n, dislikes));
    }

    /**
     * 测试目的：多连通分量，且都可二分 → 全图可二分。
     * 用例：
     *   分量A：1-2-3（链）    分量B：5-6（一条边）
     *   n=6, [[1,2],[2,3],[5,6]] → true
     */
    @Test
    public void testMultipleComponents_allBipartite_true() {
        int n = 6;
        int[][] dislikes = E(new int[][]{{1,2},{2,3},{5,6}});
        assertTrue(s.possibleBipartition(n, dislikes));
    }

    /**
     * 测试目的：含有任一奇环的图均不可二分；这里在三角形基础上再连一条边，仍应为 false。
     * 用例：n=4, [[1,2],[2,3],[1,3],[3,4]] → false
     */
    @Test
    public void testOddCycleWithTail_false() {
        int n = 4;
        int[][] dislikes = E(new int[][]{{1,2},{2,3},{1,3},{3,4}});
        assertFalse(s.possibleBipartition(n, dislikes));
    }

    /**
     * 测试目的：索引接近上界的边界值（使用节点 n），验证实现对 1..n 的索引处理正确。
     * 用例：n=5, [[1,5],[2,4]] → true
     */
    @Test
    public void testIndexBoundary_true() {
        int n = 5;
        int[][] dislikes = E(new int[][]{{1,5},{2,4}});
        assertTrue(s.possibleBipartition(n, dislikes));
    }
}
