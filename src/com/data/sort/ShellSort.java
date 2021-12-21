package com.data.sort;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 希尔算法
 * 1.选择一个增量序列t1，t2，…，tk，其中ti>tj，tk=1；
 * 2.按增量序列个数k，对序列进行k 趟排序；
 * 3.每趟排序，根据对应的增量ti，将待排序列分割成若干长度为m 的子序列，分别对各子表进行直接插入排序。仅增量因子为1 时，整个序列作为一个表来处理，表长度即为整个序列的长度。
 *
 * 把数据按组分配，每组用插入排序，
 *
 * 最佳情况：T(n) = O(nlog2 n)  最坏情况：T(n) = O(nlog2 n)  平均情况：T(n) =O(nlog2n)
 *
 * 希尔算法:把数组按length/2分割，每次用第n个元素和第n+gap个元素进行比较，小则向前，比较一圈后，gap/2，
 * 在按用第n个元素和第n+gap个元素进行比较，依次循环，
 * 直到gap为1时，进行最后一次，挨个比较
 * **(length/2)中，2可以替换成其他数字，建议使用2，**
 */
public class ShellSort {

    public static void main(String[] args) {
        int[] arr = new int[8];
        for (int i = 0; i < arr.length; i++){
            arr[i] = (int) (Math.random() * 8);
        }
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String s1 = simpleDateFormat.format(date);
        System.out.println("排序前的时间" + s1);

        shellSort(arr);

        Date date1 = new Date();
        String s2 = simpleDateFormat.format(date1);
        System.out.println("排序后的时间" + s2);
    }

    public static void shellSort(int[] arr){
        int length = arr.length;
        int temp,gap = length / 2;
        while (gap > 0){
            for (int i = gap; i < length; i++){
                temp = arr[i];
                int preIndex = i - gap;
                while (preIndex >= 0 && arr[preIndex] > temp){
                    arr[preIndex + gap] = arr[preIndex];
                    preIndex -= gap;
                }
                arr[preIndex + gap] = temp;
            }
            gap /= 2;
        }
    }


    public static void shell(int[] arr){
        int length = arr.length;
        int temp,gap = length / 2;
        while (gap > 0){
            for (int i = gap; i < length; i++){
                temp = arr[i];
                int preIndex = i - gap;
                while (preIndex >= 0 && arr[preIndex] > temp){
                    arr[preIndex + gap] = arr[preIndex];//和插入排序一样，把大的数值，赋值给后面，小的数值，进入比较区，直到为大数值为止
                    preIndex -= gap;//使之变为负数
                }
                arr[preIndex + gap] = temp;
            }
            gap /= 2;
        }
    }
}
