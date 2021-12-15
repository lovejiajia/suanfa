package com.data.stack;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Poland {
    public static void main(String[] args) {
        String s="1+((2+3)*4)-5";
        List<String> list = SuffixExpression(s);
        for (String s1 : list) {
            System.out.print(s1.toString());
        }
        System.out.println("结果为 ： " + calculate(list));
    }

    // s="1+((2+3)×4)-5";
    //将字符串转化为后缀表达式
    public static List<String> SuffixExpression(String s){
        Operator oper = new Operator();
        String res = "";//存储单个字符String状态
        int i = 0;//String指针
        char c;//存储单个字符
        Stack<String> stack = new Stack<String>();//符号栈
        List<String> list = new ArrayList<String>();
        while (i < s.length()){
            c = s.charAt(i);
            res = "" + c;//转化为String类型
            if(c > 47 && c < 58){//为数字
                res = "";//清空
                while (i < s.length() && (c = s.charAt(i)) > 47 && c < 58){
                    res += c;//拼接
                    i++;
                }
                list.add(res);
                continue;//i++操作已执行，直接进入下次扫描
            }else if (res.equals("(")){
                stack.push(res);
            }else if (res.equals(")")){
                while (!stack.peek().equals("(")){
                    list.add(stack.pop());
                }
                stack.pop();//删除左括号
            } else {
                while(stack.size() != 0 && oper.priority(stack.peek()) >= oper.priority(res)){//栈里的符号优先级大于等于扫描到的符号
                    list.add(stack.pop());
                }
                stack.push(res);
            }
            i++;
        }
        while (stack.size() != 0){
            list.add(stack.pop());
        }

        return list;
    }

    //将list形式的后缀表达式，计算结果
    public static int calculate(List<String> s){
        Stack<String> stack = new Stack<String>();
        for (String str : s){
            //为数字，入栈
            if (str.matches("\\d+")){
                stack.push(str);
            } else {
                int num2 = Integer.parseInt(stack.pop());
                int num1 = Integer.parseInt(stack.pop());
                int res = 0;
                if (str.equals("+")){
                    res = num1 + num2;
                } else if (str.equals("-")){
                    res = num1 - num2;
                } else if (str.equals("*")){
                    res = num1 * num2;
                } else if (str.equals("/")){
                    res = num1 / num2;
                }
                stack.push("" + res);
            }

        }
        return Integer.parseInt(stack.pop());
    }
}
