package com.data.sort;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 归并排序
 * 1.把长度为n的输入序列分成两个长度为n/2的子序列；
 * 2.对这两个子序列分别采用归并排序；
 * 3.将两个排序好的子序列合并成一个最终的排序序列。
 *
 * 最佳情况：T(n) = O(n)  最差情况：T(n) = O(nlogn)  平均情况：T(n) = O(nlogn)
 *
 * 把数组递归分为左右两组，跟二叉树相似，先排序最底下左右两组，然后依次向上，步骤演示(代码演示见括号)
 * 数组[1,0,2,4,3,6,8,7]
 * 1.分割数组
 * [1,0,2,4][3,6,8,7]
 * [1,0][2,4][3,6][8,7]
 * 2.比较
 * [0,1][2,4][3,6][7,8] (先[0,1]比较，并归到上层[1,0,2,4],进入[2,4]比较，然后并归到[1,0,2,4]比较，依次类推)
 * [0,1,2,4][3,6,7,8]
 */
public class MergeSort {

    public static void main(String[] args) {
        int[] arr = new int[80000];
        for (int i = 0; i < arr.length; i++){
            arr[i] = (int) (Math.random() * 80000);
        }
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String s1 = simpleDateFormat.format(date);
        System.out.println("排序前的时间" + s1);

        int[] temp = new int[80000];

        mergeSort(arr, 0, arr.length - 1, temp);

        Date date1 = new Date();
        String s2 = simpleDateFormat.format(date1);
        System.out.println("排序后的时间" + s2);
    }


    //分+合
    public static void mergeSort(int[] arr, int left, int right, int[] temp){
        //确保左右两边同时分解
        if (left < right){
            //中间索引
            int mid = (left + right) / 2;
            //向左递归进行分解
            mergeSort(arr, left, mid, temp);
            //向右递归进行分解
            mergeSort(arr, mid+1, right, temp);
            //合并
            merge(arr,left, mid, right, temp);
        }
    }

    //合并
    /**
     * @param arr   原始数组
     * @param left  左边索引
     * @param mid   中间索引
     * @param right 右边索引
     * @param temp  辅助数组
     * 步骤:
     *    1.左右两边数组，按规则填充进temp辅助数组中
     *    2.左、右两边数组按规则填充后，剩下数组填充进temp数组
     *    3.把temp数组的值，返回给arr原始数组
     */
    public static void merge(int[] arr, int left,int mid, int right, int[] temp){
        int i = left;//左边数组初始位置
        int j = mid + 1;//右边数组初始位置
        int t = 0;//temp数组索引
        while (i <= mid && j <= right){
            if (arr[i] <= arr[j]){
                temp[t] = arr[i];
                t++;
                i++;
            } else {
                temp[t] = arr[j];
                t++;
                j++;
            }
        }

        while (i <= mid){
            temp[t] = arr[i];
            t++;
            i++;
        }

        while (j <= right){
            temp[t] = arr[j];
            t++;
            j++;
        }

        /**
         * 关键点，从arr索引最左侧开始赋值，到最右侧索引
         */
        t = 0;//初始化temp索引
        while (left <= right){
            arr[left] = temp[t];
            t++;
            left++;
        }
    }
}
