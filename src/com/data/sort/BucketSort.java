package com.data.sort;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

/**
 * 桶排序
 * 1.人为设置一个BucketSize，作为每个桶所能放置多少个不同数值（例如当BucketSize==5时，该桶可以存放｛1,2,3,4,5｝这几种数字，但是容量不限，即可以存放100个3）；
 * 2.遍历输入数据，并且把数据一个一个放到对应的桶里去；
 * 3.对每个不是空的桶进行排序，可以使用其它排序方法，也可以递归使用桶排序；
 * 4.从不是空的桶里把排好序的数据拼接起来。
 *
 * 最佳情况：T(n) = O(n+k)   最差情况：T(n) = O(n+k)   平均情况：T(n) = O(n2)　　
 *
 * 设置几个桶，每个桶能放置一定范围的数值，遍历放入数据，对每个桶进行排序，然后从桶中拿出数据拼接
 * （桶排序，更像是，分段排序）
 */
public class BucketSort {

    public static void main(String[] args) {
        int[] arr = new int[80000];
        for (int i = 0; i < arr.length; i++){
            arr[i] = (int) (Math.random() * 80000);
        }
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String s1 = simpleDateFormat.format(date);
        System.out.println("排序前的时间" + s1);

        bucketSort(arr);
        System.out.println("排序后的时间" + Arrays.toString(arr));

        Date date1 = new Date();
        String s2 = simpleDateFormat.format(date1);
        System.out.println("排序后的时间" + s2);
    }

    public static void bucketSort(int[] arr){
        int min= arr[0], max = arr[0];
        //求出最小最大值
        for (int i : arr){
            if (min > i){
                min = i;
            }
            if (max < i){
                max = i;
            }
        }

        //初始化桶
        int bucketNum = (max - min) / arr.length + 1;//桶的个数
        ArrayList<ArrayList<Integer>> bucketArr = new ArrayList<>(bucketNum);
        for (int i = 0; i < bucketNum; i++){
            bucketArr.add(new ArrayList<Integer>());
        }

        //元素放入桶
        for (int i = 0; i < arr.length; i++){
            int num = (arr[i] - min) / arr.length;//值该放入的桶
            bucketArr.get(num).add(arr[i]);
        }

        //桶内元素排序
        for (int i = 0; i < bucketNum; i ++){
            Collections.sort(bucketArr.get(i));
        }

        //赋值给原始数组
        int index = 0;//arr数组索引
        for (int i = 0; i < bucketArr.size(); i++){
            for (int j = 0; j < bucketArr.get(i).size(); j++){
                arr[index++] = bucketArr.get(i).get(j);
            }
        }
    }
}
