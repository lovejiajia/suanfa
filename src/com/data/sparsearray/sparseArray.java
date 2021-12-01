package com.data.sparsearray;

//稀疏数组(五子棋)
public class sparseArray {
    public static void main(String[] args) {

        //创建二维数组,1为白，2为黑
        int chessArr[][] = new int[10][10];
        //传值
        chessArr[1][1] = 1;
        chessArr[2][2] = 2;
        chessArr[3][3] = 1;
        chessArr[4][4] = 2;
        //遍历输出
        for (int[] row : chessArr) {
            for (int data : row) {
                System.out.printf("%d\t", data);
            }
            System.out.println();
        }


        //转换为稀疏数组储存
        //获取有效数据数量
        int sum = 0;
        for (int[] row : chessArr) {
            for (int data : row) {
                if (data != 0){
                    sum++;
                }
            }
        }
        //创建数组
        int sparseArray[][] = new int[sum+1][3];
        //赋值第一行
        sparseArray[0][0] = 10;
        sparseArray[0][1] = 10;
        sparseArray[0][2] = sum;
        //赋值有效数据
        //判断第几个有效数据
        int count = 1;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++){
                if(chessArr[i][j] != 0){
                    sparseArray[count][0] = i;
                    sparseArray[count][1] = j;
                    sparseArray[count][2] = chessArr[i][j];
                    count++;
                }
            }
        }
        //输出稀疏数组
        System.out.println("----------稀疏数组-----------");
        for (int i = 0; i < sum+1;i++){
            System.out.println("第" + i + "行为：" + sparseArray[i][0] + "      " + sparseArray[i][1] + "          " + sparseArray[i][2]);
        }



        //稀疏数组转化为二维数组
        int chessArr2[][] = new int[sparseArray[0][0]][sparseArray[0][1]];
        for (int i = 1; i <= sparseArray[0][2]; i++) {
            chessArr2[sparseArray[i][0]][sparseArray[i][1]] = sparseArray[i][2];
        }
        //遍历输出
        System.out.println("------------转化为二维数组----------");
        for (int[] row : chessArr) {
            for (int data : row) {
                System.out.printf("%d\t", data);
            }
            System.out.println();
        }
    }
}
