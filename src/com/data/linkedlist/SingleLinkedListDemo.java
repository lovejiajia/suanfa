package com.data.linkedlist;

import java.util.Stack;

public class SingleLinkedListDemo{
    public static void main(String[] args) {

        //测试添加
        SingleLinkedList sll = new SingleLinkedList();
        Node node1 = new Node(1, "宋江", "及时雨");
        Node node2 = new Node(2, "卢俊义", "玉麒麟");
        Node node3 = new Node(3, "吴用", "智多星");
        Node node4 = new Node(4, "林冲", "豹子头");
        sll.add(node1);
        sll.add(node3);
        sll.add(node2);
        sll.add(node4);
        System.out.println("测试添加");
        sll.list();
        System.out.println("测试反向输出,不改变结构");
        sll.reverseListByStack(sll.getNode());
        System.out.println("测试反向输出,不改变结构(递归)");//递归传入值不能有空节点
        sll.reverseListByRecursion(sll.getNode().getNext());
        System.out.println("测试倒数第几位");
        Node indexNode = sll.getByIndex(sll.getNode(), 1);
        System.out.println("倒数第一个为: " + indexNode);



        //测试按顺序
        SingleLinkedList sll1 = new SingleLinkedList();
        sll1.addByNo(node1);
        sll1.addByNo(node2);
        sll1.addByNo(node3);
        sll1.addByNo(node4);
        sll1.addByNo(node1);
        System.out.println("测试按顺序");
        sll1.list();

        //测试反转
        System.out.println("测试反转");
        sll1.reverLinkedList();
        sll1.list();

        //测试修改，删除,长度
        SingleLinkedList sll2 = new SingleLinkedList();
        Node node22 = new Node(2, "卢俊义", "修改");
        sll2.addByNo(node1);
        sll2.addByNo(node2);
        sll2.addByNo(node3);
        sll2.addByNo(node4);
        System.out.println("测试修改");
        sll2.updataNode(node22);
        sll2.list();
        System.out.println("测试删除");
        sll2.delect(2);
        sll2.list();
        System.out.println("测试长度: " + sll2.getLenth(node1));



    }
}

class SingleLinkedList {
    //初始化头节点
    private Node head = new Node(0, "", "");

    public Node getNode(){
        return head;
    }

    //添加节点
    public void add(Node node){
        //头节点不动
        Node first = head;
        while (true){
            if(first.getNext() == null){
                break;
            }
            first = first.getNext();//节点后移
        }
        first.setNext(node);
    }

    //添加节点到指定位置(编号存在，则添加失败)
    public void addByNo(Node node){
        Node first = head;
        boolean flag = false;//判断节点是否存在
        while (true){
            if (first.getNext() == null){
                break;
            }
            if (first.getNext().getNo() > node.getNo()){//找到节点
                break;
            } else if (first.getNext().getNo() == node.getNo()){
                flag = true;//节点存在
                break;
            }
            first = first.getNext();//节点后移
        }
        if (flag){
            System.out.println("节点已存在");
        } else {
            node.setNext(first.getNext());
            first.setNext(node);
        }
    }

    //更新节点内容
    public void updataNode(Node node){
        Node first = head;
        while(true){
            if (first.getNext() == null){
                break;
            }
            if (first.getNext().getNo() == node.getNo()){
                node.setNext(first.getNext().getNext());
                first.setNext(node);
                System.out.println("找到节点，已修改");
                break;
            }
            first = first.getNext();
        }
    }

    //删除指定节点
    public void delect(int no){
        Node first = head;
        while(true){
            if (first.getNext() == null){
                break;
            }
            if (first.getNext().getNo() == no){
                first.setNext(first.getNext().getNext());
                System.out.println("找到节点，已删除");
                break;
            }
            first = first.getNext();
        }
    }

    //遍历节点
    public void list(){
        Node first = head;
        while (true){
            if(first.getNext() == null){
                System.out.println("链表为空，或已遍历完毕");
                break;
            } else {
                System.out.println(first.getNext().toString());
                first = first.getNext();
            }
        }
    }


    //反转链表
    public void reverLinkedList(){
        Node first = head.getNext();
        Node next = null;
        Node reverList = new Node(0, "", "");
        while (first != null){
            next = first.getNext();
            first.setNext(reverList.getNext());
            reverList.setNext(first);
            first = next;
        }
        head = reverList;
    }

    //获取节点个数(不计算头节点)
    public static int getLenth(Node node){
        Node first = node.getNext();
        int length = 0;
        while (first != null){
            length++;
            first = first.getNext();
        }
        return length;
    }

    //查找单链表中的倒数第k个结点
    public Node getByIndex(Node node, int index){
        if (node.getNext() == null){
            System.out.println("链表为空");
        }
        int size = getLenth(node);
        System.out.println("长度为: " + size);
        if (index <= 0 || index > size){
            System.out.println("参数不对");
        }
        Node res = node.getNext();
        for (int i = 0; i < size - index; i++){
            res = res.getNext();
        }
        return res;
    }

    //从尾到头打印单链表(方法1:使用栈)
    public void reverseListByStack(Node node){
        Stack<Node> stack = new Stack();
        Node next = node.getNext();
        //入栈
        while (next != null){
            stack.push(next);
            next = next.getNext();
        }
        //出栈
        while (!stack.isEmpty()){
            System.out.println(stack.pop());
        }
    }

    //从尾到头打印单链表(方法2:使用递归)
    public void reverseListByRecursion(Node node){
        if (node != null){
            if(node.getNext() != null){
                reverseListByRecursion(node.getNext());
            }
            System.out.println(node);
        }
    }
}

class Node{
    private int no;//编号
    private String name;
    private String nickname;
    private Node next;//下个节点

    public Node(int no, String name, String nickname) {
        this.no = no;
        this.name = name;
        this.nickname = nickname;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return "Node{" +
                "no=" + no +
                ", name='" + name + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}