import org.example.Solution9;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * 这是一个【错误示范】的测试类。
 *
 * 错误类型分析：
 * 1. 逻辑反转错误：把本该是 false 的情况断言为 true（如奇环）。
 * 2. 算法定义误解：认为不连通图不可二分（实际上只要各分量可二分即可）。
 * 3. 边界输入错误：输入了超出 N 范围的节点编号，导致程序崩溃（IndexOutOfBounds/NullPointer）。
 * 4. 冗余/无效测试：测试了无关紧要的变量或没有实际断言。
 */
public class L2023111174_9_Test1 {

    private final Solution9 s = new Solution9();

    // 辅助方法
    private static int[][] E(int[][] edges) { return edges; }

    /**
     * 【错误 1：断言与事实相反】
     * 这是一个三角形（最小奇环 1-2-3-1）。
     * 根据二分图定义，奇环不可二分，结果应为 false。
     * 但这里断言为 true，因此该测试会 FAIL。
     */
    @Test
    public void testTriangle_Error_ShouldBeFalse() {
        int n = 3;
        int[][] dislikes = E(new int[][]{{1,2},{2,3},{1,3}});
        // 错误：三角形无法二分，这里却断言它可以
        assertTrue("Triangle should satisfy bipartite?", s.possibleBipartition(n, dislikes));
    }

    /**
     * 【错误 2：对不连通图的误解】
     * 这是一个包含两个独立边的图：1-2 和 3-4。
     * 这种图是可以二分的（1,3一组，2,4一组）。
     * 这里错误地认为“不连通就返回 false”，导致测试 FAIL。
     */
    @Test
    public void testDisconnected_Error_ShouldBeTrue() {
        int n = 4;
        int[][] dislikes = E(new int[][]{{1,2},{3,4}});
        // 错误：由于误解题意，认为不连通就是 false
        assertFalse("Disconnected bipartite components should be true", s.possibleBipartition(n, dislikes));
    }

    /**
     * 【错误 3：输入数据越界导致 Crash】
     * n=3，但边集中出现了节点 4。
     * Solution9 中初始化数组大小为 n+1 (即大小为4，索引0-3)。
     * 当处理到节点 4 时，访问 g[4] 会抛出 ArrayIndexOutOfBoundsException。
     * 这是一个由“坏的测试数据”引发的 Error。
     */
    @Test
    public void testOutOfBounds_Error_Crash() {
        int n = 3;
        // 错误：节点 4 超出了 n=3 的范围
        int[][] dislikes = E(new int[][]{{1, 4}});
        assertTrue(s.possibleBipartition(n, dislikes));
    }

    /**
     * 【错误 4：对空图的逻辑混乱】
     * 空图（没有不喜欢关系）应该默认为 true（任何分法都成立）。
     * 这里错误断言为 false。
     */
    @Test
    public void testEmpty_Error_ShouldBeTrue() {
        int n = 5;
        int[][] dislikes = E(new int[][]{});
        // 错误：空图是可二分的
        assertFalse(s.possibleBipartition(n, dislikes));
    }
}