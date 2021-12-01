package com.data.sparsearray;

import java.io.*;

/**
 * 稀疏数组课后联系
 * 要求：
 * 在前面的基础上，将稀疏数组保存到磁盘上，比如 map.data
 * 恢复原来的数组时，读取map.data 进行恢复
 */
public class sparseArrayTest {
    public static void main(String[] args) throws Exception {
        File file = new File("src//com//data//sparsearray//map.data");

        FileOutputStream fileOutputStream = new FileOutputStream(file);
        OutputStreamWriter writer = new OutputStreamWriter(fileOutputStream, "utf-8");

        writer.append("aaaaaaa");

        writer.flush();
        writer.close();

    }
}
