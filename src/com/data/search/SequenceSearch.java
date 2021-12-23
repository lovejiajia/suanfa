package com.data.search;

/**
 *顺序查找(线性查找)
 *   顺序查找适合于存储结构为顺序存储或链接存储的线性表。
 */
public class SequenceSearch {


    public static void main(String[] args) {
        int arr[] = { 1, 9, 11, -1, 34, 89 };// 没有顺序的数组
        boolean index = sequence(arr, -11);
        if(!index) {
            System.out.println("没有找到到");
        } else {
            System.out.println("找到");
        }
    }


    public static boolean sequence(int[] arr, int value){
        boolean result = false;
        for (int i : arr){
            if(i == value){
                result = true;
            }
        }
        return result;
    }
}
