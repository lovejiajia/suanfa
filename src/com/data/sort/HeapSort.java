package com.data.sort;


import java.util.Arrays;
/**
 * 堆排序（应用到树结构，最小堆，最大堆）
 * 1.将无需序列构建成一个堆，根据升序降序需求选择大顶堆或小顶堆;
 * 2.将堆顶元素与末尾元素交换，将最大元素"沉"到数组末端;
 * 3.重新调整结构，使其满足堆定义，然后继续交换堆顶元素与当前末尾元素，反复执行调整+交换步骤，直到整个序列有序。
 */
public class HeapSort {
    public static void main(String []args){
        int []arr = {7,6,5,4,3,2,1};
        System.out.println("排序前："+Arrays.toString(arr));
        heapSort(arr);
        System.out.println("排序前："+Arrays.toString(arr));
    }

    public static void heapSort(int[] arr){
        int temp = 0;
        //先构建最大堆,从最左叶子节点
        for (int i = arr.length / 2 - 1; i >= 0; i--){
            adjustHeap(arr, i, arr.length);
        }
        //交换最大值
        for (int i = arr.length - 1; i > 0; i--){
            temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;
            //交换之后，重新排序最大堆
            adjustHeap(arr, 0, i);
        }
    }

    /**
     * @param arr     数组
     * @param i       从那个叶子节点
     * @param lenght  数组长度
     */
    public static void adjustHeap(int[] arr, int i, int lenght){
        int temp = arr[i];//保存叶子节点
        //执行一次，判断是否有叶子节点
        for (int j = i * 2 + 1; j < lenght; j = j * 2 + 1){
            //右节点大于左节点，索引指向右节点
            if (j + 1 < lenght && arr[j] < arr[j + 1]){
                j++;
            }
            //子节点大于叶子节点，交换位置
            if (arr[j] > temp){
                arr[i] = arr[j];
                i = j;//存储最小值应放的索引
            } else {
                break;
            }
        }
        //
        arr[i] = temp;
    }
}