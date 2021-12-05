package com.data.queue;

import java.util.Scanner;

//数组模拟环形队列
public class CircleArrayQueueDemo {
    public static void main(String[] args) {
        //测试
        //创建一个队列
        CircleArrayQueue queue = new CircleArrayQueue(5);
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

class CircleArrayQueue {
    private int maxSize;//最大容量
    private int front;//前部
    private int rear;//后部
    private int arr[];//数组储存数据

    //初始化(前部和后部默认为0)
    CircleArrayQueue(int arrSize) {
        maxSize = arrSize;
        arr = new int[maxSize];
    }

    //判断是否为空
    public boolean isEmpty() {
        return front == rear;
    }

    //判断是否已满
    public boolean isFull() {
        return (rear + 1) % maxSize == front;
    }

    //出队列
    public int getQueue() {
        if (isEmpty()) {
            throw new RuntimeException("队列为空");
        } else {
            front = (front + 1) % maxSize;
            return arr[front];
        }
    }

    //入队列
    public void addQueue(int data) {
        if (isFull()) {
            System.out.println("队列已满");
        } else {
            arr[rear] = data;
            rear = (rear + 1) % maxSize;
        }
    }

    //获取队列全部数据
    public void showQueue (){
        if (isEmpty()){
            throw new RuntimeException("队列为空");
        } else {
            for (int i = front; i != rear; i++){
                System.out.println(arr[i]);
            }
        }
    }

    //获取队列首个数据
    public void showFristQueue (){
        if (isEmpty()){
            throw new RuntimeException("队列为空");
        } else {
            System.out.println(arr[front]);
        }
    }
}