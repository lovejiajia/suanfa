package com.algorithm.LeetCode;

import java.util.Arrays;

/**
 * 旋转数组
 *      给你一个数组，将数组中的元素向右轮转 k 个位置，其中 k 是非负数。
 */
public class Solution {

    public static void main(String[] args) {
        int[] nums = {1,2,3,4,5,6};
        System.out.println(Arrays.toString(rotate1(nums, 3)));
        rotate3(nums, 4);
        System.out.println(Arrays.toString(nums));
    }

    //1.使用临时数组
    public static int[] rotate1(int[] nums, int k){
        int[] temp = new int[nums.length];
        for (int i = 0; i < nums.length; i++){
            temp[(i + k) % nums.length] = nums[i];
        }
        return temp;
    }

    //2.多次反转(先整个反转，在前后反转)
    public static void rotate2(int[] nums, int k){
        if (k < 1 || nums.length < 2 ){
            return;
        }
        if (k > nums.length){
            k = k % nums.length;
        }
        reverse(nums, 0, nums.length - 1);
        reverse(nums, 0,k - 1);
        reverse(nums, k, nums.length - 1);
    }
    public static void reverse(int[] nums, int start, int end){
        while (start < end){
            int temp = nums[start];
            nums[start++] = nums[end];
            nums[end--] = temp;
        }
    }

    //3.环形旋转
    public static void rotate3(int[] nums, int k) {
        int hold = nums[0];//保存上个下标值
        int index = 0;//下标
        int length = nums.length;
        boolean[] visited = new boolean[length];//是否已被访问
        for (int i = 0; i < length; i++) {
            index = (index + k) % length;
            if (visited[index]) {
                //如果访问过，再次访问的话，会出现原地打转的现象，
                //不能再访问当前元素了，我们直接从他的下一个元素开始
                index = (index + 1) % length;
                hold = nums[index];
                i--;
            } else {
                //把当前值保存在下一个位置，保存之前要把下一个位置的
                //值给记录下来
                visited[index] = true;
                int temp = nums[index];
                nums[index] = hold;
                hold = temp;
            }
        }
    }
}
