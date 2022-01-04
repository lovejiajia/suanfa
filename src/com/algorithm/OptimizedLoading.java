package com.algorithm;

import java.util.Arrays;

/**
 * 贪心算法(没一步选择最优的结果)
 * 贪婪算法(贪心算法)是指在对问题进行求解时，在每一步选择中都采取最好或者最优(即最有利)的选择，从而希望能够导致结果是最好或者最优的算法
 * 贪婪算法所得到的结果不一定是最优的结果(有时候会是最优解)，但是都是相对近似(接近)最优解的结果
 */
public class OptimizedLoading {

    public static int MAXWEIGHT = 30;// 小船的载重量
    public static int AMOUNT = 8;// 古董个数

    /*
     * 装载算法（贪心算法）
     * */
    public static int maxLoading(int[] weight) {
        //计数器，用于记录装载到小船上古董的数量
        int counter = 0;
        // 对weight数组进行排序
        Arrays.sort(weight);
        int temp = 0; // 记录装载到船上的古董重量
        for (int i = 0; i < AMOUNT; i++) {
            temp += weight[i]; // 贪心策略：每次装入最轻者
            if (temp <= MAXWEIGHT) { // 若加入最轻者后还小于载重量，则古董个数+1
                counter++;
            }else{
                //超重，不能装载
                break;
            }
        }
        //返回装载的古董的数量
        return counter;

    }

    public static void main(String[] args) {

        int ANSWER = 0; // 记录已装载的古董个数
        int[] weight = { 4, 10, 7, 11, 3, 5, 14, 2 };
        ANSWER = maxLoading(weight);
        System.out.println("能装入的古董最大数量为: " + ANSWER);
    }

}
