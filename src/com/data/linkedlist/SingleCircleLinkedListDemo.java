package com.data.linkedlist;

/**
 * Josephu  问题为：设编号为1，2，… n的n个人围坐一圈，约定编号为k（1<=k<=n）的人从1开始报数，数到m 的那个人出列，它的下一位又从1开始报数，
 * 数到m的那个人又出列，依次类推，直到所有人出列为止，由此产生一个出队编号的序列。
 */
public class SingleCircleLinkedListDemo {
    public static void main(String[] args) {
        SingleCircleLinkedList scl = new SingleCircleLinkedList();
        scl.add(5);
        scl.show();
        scl.Josephu(5,1,2);
    }
}


class SingleCircleLinkedList {
    Node1 head = null;//头节点为空

    //添加
    public void add(int nums){
        if(nums <= 0){
            System.out.println("数据错误");
            return;
        }
        Node1 cur = null;//辅助指针
        for (int i = 1; i <= nums; i++){
            Node1 node = new Node1(i);
            if (i == 1){
                head = node;//为头节点赋值
                head.setNext(head);//头节点构成环
                cur = head;//初始化辅助指针
            } else {
                cur.setNext(node);//设置下个节点
                node.setNext(head);//构成环
                cur = node;//节点后移
            }
        }
    }

    //显示
    public void show(){
        if (head == null){
            System.out.println("没有任何数据");
            return;
        }
        Node1 node = head;
        while (true) {
            System.out.println("编号:" + node.getNo());
            if (node.getNext() == head){//最后一个
                break;
            }
            node = node.getNext();//指针后移
        }
    }

    //Josephu

    /**
     *
     * @param N  人数
     * @param K  从第几位数
     * @param M  数多少次
     */
    //数1次会出现bug，所以需要一个辅助节点
    public void Josephu(int N, int K, int M){
        if (N <= K || K < 1 || head == null){
            System.out.println("参数输入有误， 请重新输入");
            return;
        }
        //Node1 node = head;
        //从第K节点开始
        for (int i = 1; i < K; i++){
            head = head.getNext();
        }
        while (N != 1){//N == 1 为最后一个节点
            //数M-1次(出圈出下个节点)
            for (int i = 1; i < M - 1; i++){
                head = head.getNext();
            }
            //出圈
            System.out.println("出圈编号: " + head.getNext().getNo());
            head.setNext(head.getNext().getNext());
            head = head.getNext();//节点后移
            N--;//总人数减1
        }
        System.out.println("最后出圈编号: " + head.getNo());
    }

}

class Node1{
    private int no;
    private Node1 next;

    public Node1(int no) {
        this.no = no;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public Node1 getNext() {
        return next;
    }

    public void setNext(Node1 next) {
        this.next = next;
    }
}