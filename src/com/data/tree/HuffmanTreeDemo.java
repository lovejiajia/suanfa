package com.data.tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HuffmanTreeDemo {
    public static void main(String[] args) {
        int arr[] = { 13, 7, 8, 3, 29, 6, 1 };
        Node root = huffman(arr);

        preOrder(root);
    }

    //编写一个前序遍历的方法
    public static void preOrder(Node root) {
        if(root != null) {
            root.preOrder();
        }else{
            System.out.println("是空树，不能遍历~~");
        }
    }

    //创建赫夫曼树
    public static Node huffman(int[] arr){
        //先将每个值转化为一个节点
        List<Node> list = new ArrayList<Node>();
        for (int i : arr){
            list.add(new Node(i));
        }
        //循环
        while (list.size() > 1){
            //排序
            Collections.sort(list);
            //获取最小两个值
            Node left = list.get(0);
            Node right = list.get(1);
            //构成一个新节点
            Node parent = new Node(left.value + right.value);
            parent.left = left;
            parent.right = right;
            //添加节点，删除最小两个节点
            list.add(parent);
            list.remove(left);
            list.remove(right);
        }
        //返回root节点
        return list.get(0);
    }
}

class Node implements Comparable<Node>{
    int value;
    Node left;
    Node right;

    //前序遍历
    public void preOrder(){
        System.out.println(this);
        if (this.left != null){
            this.left.preOrder();
        }
        if (this.right != null){
            this.right.preOrder();
        }
    }

    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                '}';
    }

    public Node(int value) {
        this.value = value;
    }

    @Override
    public int compareTo(Node o) {
        //从小到大排序
        return this.value - o.value;
    }
}
