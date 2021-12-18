package com.data.sort;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 选择排序
 * 1.初始状态：无序区为R[1..n]，有序区为空；
 * 2.第i趟排序(i=1,2,3…n-1)开始时，当前有序区和无序区分别为R[1..i-1]和R(i..n）。该趟排序从当前无序区中-选出关键字最小的记录 R[k]，将它与无序区的第1个记录R交换，使R[1..i]和R[i+1..n)分别变为记录个数增加1个的新有序区和记录个数减少1个的新无序区；
 * 3.n-1趟结束，数组有序化了
 *
 * 最佳情况：T(n) = O(n2)  最差情况：T(n) = O(n2)  平均情况：T(n) = O(n2)
 * 耗时 5秒
 */
public class SelectionSort {

    public static void main(String[] args) {
        int[] arr = new int[80000];
        for (int i = 0; i < arr.length; i++){
            arr[i] = (int) (Math.random() * 80000);
        }
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String s1 = simpleDateFormat.format(date);
        System.out.println("排序前的时间" + s1);

        selectionSort(arr);

        Date date1 = new Date();
        String s2 = simpleDateFormat.format(date1);
        System.out.println("排序后的时间" + s2);
    }

    public static void selectionSort(int[] arr){
        if (arr.length <= 1){
            return;
        }
        for (int i = 0; i < arr.length; i++){
            int mixIndex = i;
            //求出最小数
            for (int j = 0; j < arr.length - 1 - i; j++){
                if (arr[j] < arr[mixIndex]){
                    mixIndex = j;
                }
            }
            int temp = arr[i];
            arr[i] = arr[mixIndex];
            arr[mixIndex] = temp;
        }
    }
}
