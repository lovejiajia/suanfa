package com.data.sort;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * 基数排序
 * 1.取得数组中的最大数，并取得位数；
 * 2.arr为原始数组，从最低位开始取每个位组成radix数组；
 * 3.对radix进行计数排序（利用计数排序适用于小范围数的特点）
 *
 * 最佳情况：T(n) = O(n * k)   最差情况：T(n) = O(n * k)   平均情况：T(n) = O(n * k)
 *
 * 求出最大值，最大值为几位数，创建10个桶，从个位数开始，进入对应的桶，循环到最大值的位数
 */
public class RadixSort {

    public static void main(String[] args) {
        int[] arr = new int[80000];
        for (int i = 0; i < arr.length; i++){
            arr[i] = (int) (Math.random() * 80000);
        }
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String s1 = simpleDateFormat.format(date);
        System.out.println("排序前的时间" + s1);

        radixSort(arr);


        Date date1 = new Date();
        String s2 = simpleDateFormat.format(date1);
        System.out.println("排序后的时间" + s2);
    }

    public static void radixSort(int[] arr){
        int max = 0;
        //获取最大值
        for (int i : arr){
            if (max < i){
                max = i;
            }
        }

        //最大值为几位数
        int maxNum = (max + "").length();

        for (int i = 0, n = 1; i < maxNum; i++, n *= 10){
            //创建10个桶，可以装arr.length个值
            int[][] bucket = new int[10][arr.length];
            int[] bucketNum = new int[10];//记录每个桶的值的数量
            //遍历arr数组
            for (int j = 0; j < arr.length; j++){
                int num = arr[j] / n % 10;//求相应位数
                bucket[num][bucketNum[num]++] = arr[j];//赋值给相应的桶
            }
            //赋值给arr数组
            int index = 0;//arr索引
            for (int j = 0; j < 10; j++){
                for (int l = 0; l < bucketNum[j]; l++){
                    arr[index++] = bucket[j][l];
                }
            }
        }
    }
}
