package com.data.sparsearray;

import java.io.*;

//稀疏数组(五子棋)
public class sparseArray {
    public static void main(String[] args) throws Exception {

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



        /**
         * 稀疏数组课后联系
         * 要求：
         * 在前面的基础上，将稀疏数组保存到磁盘上，比如 map.data
         * 恢复原来的数组时，读取map.data 进行恢复
         */
        File file = new File("src//com//data//sparsearray//map.data");
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        OutputStreamWriter writer = new OutputStreamWriter(fileOutputStream);

        System.out.println("-----------保存到磁盘上-----------");
        for (int i = 0; i < sparseArray.length; i++) {
            if (i == sparseArray.length-1){
                writer.append(sparseArray[i][0] + "," + sparseArray[i][1] + "," + sparseArray[i][2]);
            }else {
                writer.append(sparseArray[i][0] + "," + sparseArray[i][1] + "," + sparseArray[i][2] + ",");
            }
        }
        writer.close();
        fileOutputStream.close();


        System.out.println("-----------读取map.data文件-----------");
        FileInputStream fileInputStream = new FileInputStream(file);
        InputStreamReader reader = new InputStreamReader(fileInputStream);
        StringBuffer sb = new StringBuffer();
        while (reader.ready()) {
            sb.append((char) reader.read());
        }
        System.out.println(sb.toString());
        reader.close();
        fileInputStream.close();

        System.out.println("-----------恢复为稀疏数组--------");
        String[] s = sb.toString().split(",");
        //创建稀疏数组
        int sparseArray3[][] = new int[s.length/3][3];
        //给稀疏数组赋值
        int a = 0;
        for (String c : s) {
            sparseArray3[a / 3][a % 3] = Integer.parseInt(c);
            a++;
        }
        System.out.println("----------稀疏数组-----------");
        for (int i = 0; i < sum+1;i++){
            System.out.println("第" + i + "行为：" + sparseArray3[i][0] + "      " + sparseArray3[i][1] + "          " + sparseArray3[i][2]);
        }

        System.out.println("-----------恢复为二维数组--------");
        int chessArr3[][] = new int[sparseArray3[0][0]][sparseArray3[0][1]];
        //给二维数组赋值
        for (int i = 1; i <= sparseArray3[0][2]; i++) {
            chessArr3[sparseArray3[i][0]][sparseArray3[i][1]] = sparseArray3[i][2];
        }
        //遍历输出
        for (int[] row : chessArr3) {
            for (int data : row) {
                System.out.printf("%d\t", data);
            }
            System.out.println();
        }

    }
}
