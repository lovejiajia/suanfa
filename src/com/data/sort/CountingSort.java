package com.data.sort;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * 计数排序：
 * 1.找出待排序的数组中最大和最小的元素；
 * 2.统计数组中每个值为i的元素出现的次数，存入数组C的第i项；
 * 3.对所有的计数累加（从C中的第一个元素开始，每一项和前一项相加）；
 * 4.反向填充目标数组：将每个元素i放在新数组的第C(i)项，每放一个元素就将C(i)减去1。
 *
 * 最佳情况：T(n) = O(n+k)  最差情况：T(n) = O(n+k)  平均情况：T(n) = O(n+k)
 *
 * 获取数组，最小最大值，统计其中数值出现次数，反向填充数组
 */
public class CountingSort {

    public static void main(String[] args) {
        int[] arr = new int[80000];
        for (int i = 0; i < arr.length; i++){
            arr[i] = (int) (Math.random() * 80000);
        }
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String s1 = simpleDateFormat.format(date);
        System.out.println("排序前的时间" + s1);

        countingSrot(arr);

        Date date1 = new Date();
        String s2 = simpleDateFormat.format(date1);
        System.out.println("排序后的时间" + s2);
    }


    public static void countingSrot(int[] arr){
        int min = arr[0],max = arr[0];
        //求出最大最小值
        for (int i : arr){
            if (min > i){
                min = i;
            }
            if (max < i){
                max = i;
            }
        }

        //计数数组
        int[] temp = new int[max - min + 1];
        for (int i : arr){
            temp[i - min] += 1;//计数
        }

        //将计数数组返回给arr
        int index = 0;
        for (int i = 0; i < temp.length; i++){
            for (int j = 0; j < temp[i]; j++){
                arr[index++] = i + min;
            }
        }
    }
}
