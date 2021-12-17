package com.data.recursion;

//八皇后问题
/**
 * 思路：主要在check方法上
 *      1.从第一行第一列开始，放置皇后，每行从第一列放置，判断是否有冲突
 *      2.直到放置到最后一行，出现一种解法，打印解法
 *      3.然后开始回溯，回溯到倒数第二行的循环，正确循环已过，循环结束，继续向上层循环回溯
 *      4.直到第二行，开始有不冲突走法，依次出现第二种解法
 *      5.直到第一行第一列4种解法出现完毕，开始从第一行第二列寻找解法
 *      6.依次类推.....
 *
 *     关键点2：判断是否在同一斜线上
 *          Math.abs(n-i) == Math.abs(queen[n] - queen[i])
 *       1.Math.abs()       取非负数
 *       2.行数相减  等于  列数相减  的值， 为同一行
 *          参考： 正方形思考
 */
public class Queen {
    int max = 8;//几个皇后
    int[] queen = new int[max];//存储位置
    static int count;//解法
    static int judgeCount;//判断次数

    public static void main(String[] args) {
        Queen queen = new Queen();
        queen.check(0);
        System.out.println("解法一共有:" + count);
        System.out.println("一共判断:" + judgeCount);
    }

    //
    private void check(int n){
        if (n == max){//已放置最后一个皇后
            print();//打印输出一下
            return;
        }
        //从[0][0]第一行第一列开始，寻找正确解法，找到一个回溯，寻找下个解法，直到[0][7]第一行最后一列
        for(int i = 0; i < max; i++){
            queen[n] = i;
            if (judge(n)){
                check(n + 1);
            }
        }
    }

    //检查是否有冲突
    private boolean judge(int n){
        judgeCount++;
        for (int i = 0; i < n; i++){
            //1.判断是否同一列（queen[i] == queen[n]）
            //2.判断是否同一行（因每次不同行，省略）
            //3.判断是否同一斜线（两个点，行数相减==列数相减，为同一斜线）
            if (queen[i] == queen[n] || Math.abs(n-i) == Math.abs(queen[n] - queen[i])){
                return false;
            }
        }
        return true;
    }

    //打印解法
    private void print(){
        count++;
        for (int a : queen){
            System.out.print(a + " ");
        }
        System.out.println();
    }
}
