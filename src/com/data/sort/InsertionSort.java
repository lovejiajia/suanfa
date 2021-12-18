package com.data.sort;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 插入排序
 * 1.从第一个元素开始，该元素可以认为已经被排序；
 * 2.取出下一个元素，在已经排序的元素序列中从后向前扫描；
 * 3.如果该元素（已排序）大于新元素，将该元素移到下一位置；
 * 4.重复步骤3，直到找到已排序的元素小于或者等于新元素的位置；
 * 5.将新元素插入到该位置后；
 * 6.重复步骤2~5。
 *
 * 从第一个元素开始，拿后一位元素跟前一位元素进行比较，小则前一位元素后退，继续跟前一位比较，条件不成立，插入到当前位置
 *
 * 最佳情况：T(n) = O(n)   最坏情况：T(n) = O(n2)   平均情况：T(n) = O(n2)
 * 耗时 2 秒
 */
public class InsertionSort {

    public static void main(String[] args) {
        int[] arr = new int[80000];
        for (int i = 0; i < arr.length; i++){
            arr[i] = (int) (Math.random() * 80000);
        }
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String s1 = simpleDateFormat.format(date);
        System.out.println("排序前的时间" + s1);

        insertionSort(arr);

        Date date1 = new Date();
        String s2 = simpleDateFormat.format(date1);
        System.out.println("排序后的时间" + s2);
    }


    public static void insertionSort(int[] arr){
        if (arr.length <=1){
            return;
        }
        for (int i = 0; i < arr.length - 1; i++){
            int current = arr[i + 1];
            int preIndex = i;
            while (preIndex >= 0 && current < arr[preIndex]){
                arr[preIndex + 1] = arr[preIndex];
                preIndex--;
            }
            arr[preIndex + 1] = current;
        }
    }
}
