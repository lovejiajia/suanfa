package com.data.stack;

/**
 * 使用栈实现计算器
 */
public class Calculator {
    public static void main(String[] args) {
        String expression = "7*2*2-5+1-5+3-4";
        ArrayStack1 numStack = new ArrayStack1(10);//数栈
        ArrayStack1 operStack = new ArrayStack1(10);//符号栈
        int num1 = 0;//栈顶两个数
        int num2 = 0;//栈顶两数
        int index = 0;
        int res;
        String KeepNum = "";//拼接多位数
        char ch = ' ';//保存每次扫描的字符

        while (true){//循环扫描入栈
            ch = expression.substring(index, index+1).charAt(0);//依次扫描
            if (operStack.isOper(ch)){//判断是否是运算符
                if (operStack.isEmpty()){//判断符号栈是否为空
                    operStack.push(ch);
                } else {
                    if (operStack.priority(ch) <= operStack.priority(operStack.peek())){//判断符号优先级是否大于栈里的符号
                        num1 = numStack.pop();
                        num2 = numStack.pop();
                        res = numStack.cal(num1, num2, operStack.pop());
                        operStack.push(ch);
                        numStack.push(res);
                    } else {
                        operStack.push(ch);
                    }
                }
            } else {
                KeepNum += ch;
                if (index == expression.length()-1){//判断是否是最后一位数字
                    numStack.push(Integer.valueOf(KeepNum));
                    break;
                }else {
                    if (numStack.isOper(expression.substring(index + 1, index + 2).charAt(0))){//判断后一位是否是字符
                        numStack.push(Integer.valueOf(KeepNum));
                        KeepNum = "";//清空
                    } else {//如果是数字
                        while (!numStack.isOper(expression.substring(index + 1, index + 2).charAt(0))){//循环直到是字符为止
                            index++;
                            ch = expression.substring(index, index+1).charAt(0);
                            KeepNum += ch;
                        }
                        numStack.push(Integer.valueOf(KeepNum));
                        KeepNum = "";//清空
                    }
                }

            }
            if (index == expression.length()-1){//判断是否是最后一位数字
                break;
            }
            index++;
        }

        //扫描结束后
        while (true){
            if (operStack.isEmpty()){//运算栈为空，则结果为数字栈
                res = numStack.pop();
                break;
            }
            num1 = numStack.pop();
            num2 = numStack.pop();
            res = numStack.cal(num1, num2, operStack.pop());
            numStack.push(res);
        }

        System.out.println("最后结果为: " + res);

    }
}
class ArrayStack1 {
    private int maxSize;//最大容量
    private int top = -1;//栈顶
    private int[] arr;//栈

    public ArrayStack1 (int maxSize) {
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

    //非出栈，显示栈顶
    public int peek(){
        if (isEmpty()){
            System.out.println("栈已空");
            return 0;
        }
        return arr[top];
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
    public int pop(){
        int res;
        if (isEmpty()){
            System.out.println("栈已空");
            return 0;
        }
        //System.out.println(arr[top]);
        res = arr[top];
        top--;
        return res;
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

    //符号优先级
    public int priority(int oper){
        if(oper == '*' || oper == '/'){
            return 1;
        } else if (oper == '+' || oper == '-') {
            return 0;
        } else {
            return -1; // 假定目前的表达式只有 +, - , * , /
        }
    }

    //判断是否是运算符
    public boolean isOper(char c){
        return c == '*' || c == '/' || c == '+' || c == '-';
    }

    //运算
    public int cal(int num1, int num2, int oper){
        int res = 0;
        switch (oper){
            case '*':
                res = num1 * num2;
                break;
            case '/':
                res = num2 / num1;
                break;
            case '+':
                res = num1 + num2;
                break;
            case '-':
                res = num2 - num1;
                break;
        }
        return res;
    }
}
