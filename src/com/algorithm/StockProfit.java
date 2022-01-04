package com.algorithm;

/**
 * 动态规划(股票利润)
 * 动态规划(Dynamic Programming)算法的核心思想是：将大问题划分为小问题进行解决，从而一步步获取最优解的处理算法
 * 动态规划算法与分治算法类似，其基本思想也是将待求解问题分解成若干个子问题，先求解子问题，然后从这些子问题的解得到原问题的解。
 * 与分治法不同的是，适合于用动态规划求解的问题，经分解得到子问题往往不是互相独立的。 ( 即下一个子阶段的求解是建立在上一个子阶段的解的基础上，进行进一步的求解 )
 * 动态规划可以通过填表的方式来逐步推进，得到最优解.
 */
public class StockProfit {

    public static void main(String[] args) {
        int[] prices = {7,5,1,6,9,3,8,2,9};
        int a = maxProfit(prices);
        System.out.println(a);
    }

    public static int maxProfit(int[] prices){
        if (prices == null || prices.length < 2){
            return 0;
        }
        int length = prices.length;
        int[][] dp = new int[length][2];
        dp[0][0] = 0;//没有股票利润
        dp[0][1] = -prices[0];//持有股票利润
        for (int i = 1; i < length; i++){
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
            dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] - prices[i]);
        }
        return dp[length - 1][0];
    }
}
