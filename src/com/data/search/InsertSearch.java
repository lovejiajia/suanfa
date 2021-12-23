package com.data.search;

import java.util.ArrayList;
import java.util.List;

/**
 * 插值查找
 *
 * 基于二分查找算法，将查找点的选择改进为自适应选择，可以提高查找效率
 *
 * mid=low+(key-a[low])/(a[high]-a[low])*(high-low)，
 */
public class InsertSearch {

    public static void main(String[] args) {
        //int arr[] = { 1, 8, 10, 89,1000,1000, 1234 };
        int arr[] = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 , 11, 12, 13,14,15,16,17,18,19,20 };



        int resIndex = binary(arr, 0, arr.length - 1, 10);
        System.out.println("resIndex=" + resIndex);

        List<Integer> resIndexList = binary1(arr, 0, arr.length - 1, 1000);
        System.out.println("resIndexList=" + resIndexList);
    }

    //二分查找
    public static int binary(int[] arr, int left, int right, int value){
        int mid = left + (value - arr[left]) / (arr[right] - arr[left]) * (right - left);
        if (left >= right){
            return -1;
        }
        if (arr[mid] < value){
            //向右递归
            return binary(arr, mid + 1, right, value);
        } else if (arr[mid] > value){
            //向左递归
            return binary(arr, left, mid - 1, value);
        } else {
            return mid;
        }
    }

    //二分查找（值有多个时）
    public static List<Integer> binary1(int[] arr, int left, int right, int value){
        int mid = left + (value - arr[left]) / (arr[right] - arr[left]) * (right - left);
        if (left >= right){
            return new ArrayList<>();
        }
        if (arr[mid] < value){
            //向右递归
            return binary1(arr, mid + 1, right, value);
        } else if (arr[mid] > value){
            //向左递归
            return binary1(arr, left, mid - 1, value);
        } else {
            /**
             * 值有多个时
             */
            List<Integer> list = new ArrayList<Integer>();
            list.add(mid);

            //向左扫描
            int temp = mid - 1;
            while (temp >= 0 && arr[temp] == value){
                list.add(temp--);
            }
            //向右扫描
            temp = mid + 1;
            while (temp < arr.length && arr[temp] == value){
                list.add(temp++);
            }
            return list;
        }
    }
}
