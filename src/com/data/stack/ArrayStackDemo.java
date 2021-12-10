package com.data.stack;

import java.util.Scanner;

public class ArrayStackDemo {

    public static void main(String[] args) {
        //测试一下ArrayStack 是否正确
        //先创建一个ArrayStack对象->表示栈
        ArrayStack stack = new ArrayStack(4);
        String key = "";
        boolean loop = true; //控制是否退出菜单
        Scanner scanner = new Scanner(System.in);

        while(loop) {
            System.out.println("show: 表示显示栈");
            System.out.println("exit: 退出程序");
            System.out.println("push: 表示添加数据到栈(入栈)");
            System.out.println("pop: 表示从栈取出数据(出栈)");
            System.out.println("请输入你的选择");
            key = scanner.next();
            switch (key) {
                case "show":
                    stack.list();
                    break;
                case "push":
                    System.out.println("请输入一个数");
                    int value = scanner.nextInt();
                    stack.push(value);
                    break;
                case "pop":
                    stack.pop();
                    break;
                case "exit":
                    scanner.close();
                    loop = false;
                    break;
                default:
                    break;
            }
        }

        System.out.println("程序退出~~~");
    }

}

class ArrayStack{
    private int maxSize;//最大容量
    private int top = -1;//栈顶
    private int[] arr;//栈

    public ArrayStack(int maxSize) {
        this.maxSize = maxSize;
        this.arr = new int[maxSize];
    }

    //是否为空
    public boolean isEmpty(){
        return top == -1;
    }

    //是否栈满
    public boolean isFull(){
        return top == maxSize - 1;
    }

    //入栈
    public void push(int value){
        if (isFull()){
            System.out.println("栈已满");
            return;
        }
        top++;
        arr[top] = value;
    }

    //出栈
    public void pop(){
        if (isEmpty()){
            System.out.println("栈已空");
            return;
        }
        System.out.println(arr[top]);
        top--;
    }

    //显示栈的内容
    public void list(){
        if (isEmpty()){
            System.out.println("栈已空");
            return;
        }
        for (int i = top; i >= 0; i--){
            System.out.println(arr[i]);
        }
    }
}
