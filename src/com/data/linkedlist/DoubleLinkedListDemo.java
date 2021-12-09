package com.data.linkedlist;

public class DoubleLinkedListDemo {
    public static void main(String[] args) {
        DoubleLinkedList dll = new DoubleLinkedList();
        Node2 node1 = new Node2(1);
        Node2 node2 = new Node2(2);
        Node2 node3 = new Node2(3);
        Node2 node4 = new Node2(4);

        dll.add(node1);
        dll.add(node2);
        dll.add(node3);
        dll.add(node4);
        System.out.println("遍历");
        dll.list();
        Node2 node33 = new Node2(33);
        System.out.println("修改");
        dll.updata(node33);
        dll.list();
        System.out.println("删除");
        dll.del(3);
        dll.list();
    }
}

//双向链表
class DoubleLinkedList{
    //初始化头节点
    Node2 head = new Node2(0);

    //添加
    public void add(Node2 node){
        Node2 first = head;
        while (first != null){
            if (first.next == null){
                break;
            }
            first = first.next;
        }
        first.next = node;
        node.pre = first;
    }

    //删除
    public void del(int no){
        Node2 first = head;
        while (first != null){
           if (first.no == no){
               break;
           }
           first = first.next;
        }
        first.pre.next = first.next;
        if (first.next != null){//判断是否是最后一个节点
            first.next.pre = first.pre;
        }
    }

    //修改
    public void updata(Node2 node){
        Node2 first = head;
        while(first != null){
            if(first.no == node.no){
                break;
            }
            first = first.next;
        }
    }

    //遍历
    public void list(){
        Node2 first = head.next;
        while(first != null){
            System.out.println(first);
            first = first.next;
        }
    }
}
class Node2{
    public int no;
    public Node2 pre;//前一个节点
    public Node2 next;//后一个节点

    public Node2(int no) {
        this.no = no;
    }

    @Override
    public String toString() {
        return "Node2{" +
                "no=" + no +
                '}';
    }
}