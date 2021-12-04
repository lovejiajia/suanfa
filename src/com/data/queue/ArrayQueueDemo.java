package com.data.queue;

import java.util.Scanner;

public class ArrayQueueDemo {

    public static void main(String[] args) {
        //测试
        //创建一个队列
        ArrayQueue queue = new ArrayQueue(5);
        char key = ' '; //接收用户输入
        Scanner scanner = new Scanner(System.in);//
        boolean loop = true;

        //输出一个菜单
        while(loop) {
            System.out.println("s(show): 显示队列");
            System.out.println("e(exit): 退出程序");
            System.out.println("a(add): 添加数据到队列");
            System.out.println("g(get): 从队列取出数据");
            System.out.println("h(head): 查看队列头的数据");
            key = scanner.next().charAt(0);//接收一个字符
            switch (key) {
                case 's':
                    queue.showQueue();
                    break;
                case 'a':
                    System.out.println("输出一个数");
                    int value = scanner.nextInt();
                    queue.addQueue(value);
                    break;
                case 'g': //取出数据
                    try {
                        int res = queue.getQueue();
                        System.out.printf("取出的数据是%d\n", res);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'h': //查看队列头的数据
                    try {
                        queue.showFristQueue();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'e': //退出
                    scanner.close();
                    loop = false;
                    break;
                default:
                    break;
            }
        }

        System.out.println("程序退出~~");
    }
}

//使用数组模拟队列
class ArrayQueue {
    private int maxSize;//最大容量
    private int front;//前部
    private int rear;//后部
    private int arr[];//储存数据

    //初始化队列
    ArrayQueue (int arrMaxSize){
        maxSize = arrMaxSize;
        front = -1;
        rear = -1;
        arr = new int[maxSize];
    }

    //判断队列是否已满
    public boolean isFull (){
        return rear == maxSize - 1;
    }
    //判断队列是否为空
    public boolean isEmpty(){
        return rear == front;
    }

    //添加数据(进队列)
    public void addQueue (int data){
        if (isFull()){
            System.out.println("队列已满，请稍后尝试");
        } else {
            rear++;
            arr[rear] = data;
        }
    }

    //获取数据(出队列)
    public int getQueue (){
        if (isEmpty()){
            throw new RuntimeException("队列为空");
        } else {
            front++;
            return arr[front];
        }
    }

    //显示所有数据
    public void showQueue (){
        if (isEmpty()){
            throw new RuntimeException("队列为空");
        } else {
            for (int i = 0; i < arr.length; i++){
                System.out.println(arr[i]);
            }
        }
    }

    //显示头部数据(非出队列)
    public void showFristQueue (){
        if (isEmpty()){
            throw new RuntimeException("队列为空");
        } else {
            System.out.println("第一个数据为" + arr[front+1]);
        }
    }
}