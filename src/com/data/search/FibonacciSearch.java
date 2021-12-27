package com.data.search;

import java.util.Arrays;
import java.util.List;

public class FibonacciSearch {


    public static void main(String[] args) {
        //int arr[] = { 1, 8, 10, 89,1000,1000, 1234 };
        int arr[] = { 50,51,52,53,54,55 };



        int resIndex = fibonacciSearch(arr, 0, arr.length - 1, 51);
        System.out.println("resIndex=" + resIndex);


    }

    //斐波那契数列
    public static int[] fibonacci(){
        int[] f = new int[20];
        f[0] = 1;
        f[1] = 1;
        for (int i = 2; i < f.length; i++){
            f[i] = f[i - 1] + f[i - 2];
        }
        return f;
    }

    public static int fibonacciSearch(int[] arr, int left, int right, int value){
        int[] f = fibonacci();
        int k = 0;
        //获取斐波纳契数列下标
        while (arr[right] > f[k]){
            k++;
        }
        //f[k]的值可能大于arr[]的长度
        int[] temp = Arrays.copyOf(arr, f[k]);
        //temp尾部为0的值，赋值为arr的最大值
        for (int i = right + 1; i < temp.length; i++){
            temp[i] = arr[right];
        }
        //循环
        while (left <= right){
            //黄金分割数，-1，数组从0开始
            int mid = left + f[k - 1] - 1;
            //在数组右侧
            if(temp[mid] < value){
                left = mid + 1;
                k -= 2;//由于黄金比例为0.618，所以防止越界-2
            } else if(temp[mid] > value){//在数组左侧
                right = mid -1;
                k -= 1;
            } else {
                //取小的值，因有为尾部赋值
                if (mid <= right){
                    return mid;
                } else {
                    return right;
                }
            }
        }
        return -1;
    }
}
