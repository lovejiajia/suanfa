package com.algorithm;

import java.util.Arrays;

/**
 * KMP算法（由暴力匹配算法，优化得来）
 * KMP是一个解决模式串在文本串是否出现过，如果出现过，最早出现的位置的经典算法
 * 具体思想，在查找字符串中，找到前缀，后缀重复出现的值(最大共有元素),匹配时，出现失配，获取对应字符的共有数，再从当前位置和共有数的位置进行比较;
 */
public class KMP {

    public static void main(String[] args) {
        String str1 = "BBC ABCDAB ABCDABCDABDE";
        String str2 = "ABCDABD";
        //String str2 = "BBC";

        int[] next = getNext1("ABCDABD"); //[0, 1, 2, 0]
        System.out.println("next=" + Arrays.toString(next));

        int index = kmp(str1, str2);
        System.out.println("index=" + index); // 15了


    }

    public static int kmp(String st1, String st2){
        int[] next = getNext1(st2);
        int i = 0, j = 0;
        while (i < st1.length() && j < st2.length()){
            if (j == -1 || st1.charAt(i) == st2.charAt(j)){
                i++;
                j++;
            } else {
                j = next[j];
            }
        }
        if (j == st2.length()){
            return i - j;
        } else {
            return -1;
        }
    }

    //KMP获取最大公共元素
    public static int[] getNext(String arr){
        int[] next = new int[arr.length()];
        next[0] = 0;
        for (int i = 1, j = 0; i < arr.length(); i++){
            while (j > 0 && arr.charAt(i) != arr.charAt(j)){
                j = next[j - 1];
            }
            if (arr.charAt(i) == arr.charAt(j)){
                j++;
            }
            next[i] = j;
        }
        return next;
    }

    //优化KMP获取最大公共元素
    public static int[] getNext1(String arr){
        int[] next = new int[arr.length() + 1];
        next[0] = -1;
        int i = 0, j = -1;
        while (i < arr.length()){
            if (j == -1 || arr.charAt(i) == arr.charAt(j)){
                next[++i] = ++j;
            } else {
                j = next[j];
            }
        }
        return next;
    }
}
