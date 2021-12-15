package com.data.stack;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class PolandDome {

    public static void main(String[] args) {
        String s="1+((2+3)*4)-5";
        List<String> list = ConvertToArray(s);
        List<String> list1 = SuffixExpression(list);
        for (String s1 : list1) {
            System.out.print(s1.toString());
        }
        System.out.println("结果为 ： " + calculate(list1));
    }

    // s="1+((2+3)×4)-5";
    //中缀表达式转化为List
    public static List<String> ConvertToArray(String s){
        List<String> list = new ArrayList<>();
        int i = 0;//指针
        String str;//用于多位数拼接
        char c;//存储单个字符
        while(i < s.length()){
            c = s.charAt(i);
            if(c < 48 || c > 57){//非数字
                list.add("" + c);
                i++;
            } else {//如果是数字
                str = "";//重置str
                while (i < s.length() && (c = s.charAt(i)) >=48 && c <= 57){
                    str += c;//拼接数字
                    i++;

                }
                list.add(str);
            }
        }
        return list;
    }

    //ArrayList [1,+,(,(,2,+,3,),*,4,),-,5]  =》 ArrayList [1,2,3,+,4,*,+,5,–]
    //将List形式的中缀表达式转化为后缀表达式
    public static List<String> SuffixExpression(List<String> s){
        Stack<String> s1 = new Stack<String>();//符号栈
        List<String> s2 = new ArrayList<>();//存储结果
        Operator oper = new Operator();
        for (String str : s){
            //如果是数字
            if(str.matches("\\d+")){
                s2.add(str);
            }else if(str.equals("(")){
                s1.push(str);
            }else if (str.equals(")")){
                //循环直到左括号
                while(!s1.peek().equals("(")){
                    s2.add(s1.pop());
                }
                //删除左括号
                s1.pop();
            }else {
                //判断符号优先级
                while (s1.size() != 0 && oper.priority(s1.peek()) >= oper.priority(str)){
                    s2.add(s1.pop());
                }
                s1.push(str);
            }
        }

        //弹出最后符号栈内容
        while (s1.size() != 0){
            s2.add(s1.pop());
        }
        return s2;
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

class Operator {

    private static int ADD = 1;
    private static int SUB = 1;
    private static int MUL = 2;
    private static int DIV = 2;

    public int priority (String oper){
        int result = 0;
        switch (oper){
            case "+":
                result = ADD;
                break;
            case "-":
                result = SUB;
                break;
            case "*":
                result = MUL;
                break;
            case "/":
                result = DIV;
                break;
        }
        return result;
    }
}