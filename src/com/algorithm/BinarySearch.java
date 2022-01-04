package com.algorithm;

//非递归，二分查找
public class BinarySearch {

    public static void main(String[] args) {
        //测试
        int[] arr = {1,3, 8, 10, 11, 67, 100};
        int index = binarySearch(arr, 100);
        System.out.println("index=" + index);//
    }

    public static int binarySearch(int[] arr, int i){
        int left = 0;
        int right = arr.length - 1;
        while (left <= right){
            int mid = (left + right) / 2;
            if (arr[mid] == i){
                return mid;
            }
            if (arr[mid] < i){
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1;
    }
}
