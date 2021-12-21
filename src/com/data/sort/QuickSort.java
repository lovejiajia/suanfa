package com.data.sort;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 快速排序
 * 1.从数列中挑出一个元素，称为 “基准”（pivot）；
 * 2.重新排序数列，所有元素比基准值小的摆放在基准前面，所有元素比基准值大的摆在基准的后面（相同的数可以到任一边）。在这个分区退出之后，该基准就处于数列的中间位置。这个称为分区（partition）操作；
 * 3.递归地（recursive）把小于基准值元素的子数列和大于基准值元素的子数列排序。
 *
 * 最佳情况：T(n) = O(nlogn)   最差情况：T(n) = O(n2)   平均情况：T(n) = O(nlogn)　
 *
 * 步骤：
 * 1.选出一个基准数，小于基准数的放左边，大于放右边
 * 2.左边在选出基准数，右边同样选出基准数，依次类推
 */
public class QuickSort {

    public static void main(String[] args) {
        int[] arr = new int[8];
        for (int i = 0; i < arr.length; i++){
            arr[i] = (int) (Math.random() * 8);
        }
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String s1 = simpleDateFormat.format(date);
        System.out.println("排序前的时间" + s1);

        quickSort(arr, 0 , 7);

        Date date1 = new Date();
        String s2 = simpleDateFormat.format(date1);
        System.out.println("排序后的时间" + s2);
    }



    public static void quickSort(int[] arr, int left, int right){
        if (left > right){
            return;
        }
        int t,i = left,j = right;
        int temp = arr[left];//基准位
        while (i < j){
            while (temp <= arr[j] && i < j){
                j--;
            }
            while (temp >= arr[i] && i < j){
                i++;
            }
            if (i < j){
                t = arr[i];
                arr[i] = arr[j];
                arr[j] = t;
            }
        }
        arr[left] = arr[i];
        arr[i] = temp;

        quickSort(arr, left , j - 1);
        quickSort(arr, j + 1 , right);
    }
}
