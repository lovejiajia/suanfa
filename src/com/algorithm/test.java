package com.algorithm;

public class test {
    public static void main(String[] args) {
        int[] prices = {7,5,1,6,9};
        int a = maxProfit(prices);
        System.out.println(a);
    }

    public static int maxProfit(int[] prices) {
        if (prices == null || prices.length < 2)
            return 0;
        int length = prices.length;
        int[][] dp = new int[length][2];
        //初始条件
        dp[0][1] = -prices[0];//第一天利润，-7
        dp[0][0] = 0;
        for (int i = 1; i < length; i++) {
            //递推公式（前一天的利润加今天的价格）
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);//第i+1天交易完后手里没有股票的最大利润（没有股票可以是，已经卖掉，或者没有买）
            dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] - prices[i]);//第i+1天交易完后手里有股票的最大利润（有股票可以是，没有卖(dp[i - 1][1])，或者已经买了(dp[i - 1][0] - prices[i])）
        }
        //最后一天肯定是手里没有股票的时候，利润才会最大，
        //只需要返回dp[length - 1][0]即可
        return dp[length - 1][0];
    }
}
