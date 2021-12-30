package com.data.tree;

/**
 * 平衡二叉树
 */
public class BinaryALVTreeDemo {
    public static void main(String[] args) {
        int[] arr = { 10, 11, 7, 6, 8, 9 };
        //创建一个 AVLTree对象
        AVLTree avlTree = new AVLTree();
        //添加结点
        for(int i=0; i < arr.length; i++) {
            avlTree.add(new Node5(arr[i]));
        }

        //遍历
        System.out.println("中序遍历");
        avlTree.infixOrder();

        System.out.println("在平衡处理~~");
        System.out.println("树的高度=" + avlTree.getRoot().getLength()); //3
        System.out.println("树的左子树高度=" + avlTree.getRoot().leftLength()); // 2
        System.out.println("树的右子树高度=" + avlTree.getRoot().rightLength()); // 2
        System.out.println("当前的根结点=" + avlTree.getRoot());//8
    }
}

class AVLTree {
    private Node5 root;

    //获取root节点
    public Node5 getRoot(){
        return root;
    }
    //添加
    public void add(Node5 node){
        if (root == null){
            root = node;
        } else {
            root.add(node);
        }
    }
    //中序遍历
    public void infixOrder(){
        if (root == null){
            return;
        } else {
            root.infixOrder();
        }
    }
}

class ALVNode{
    int value;
    Node5 left;
    Node5 right;

    public ALVNode(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Node5{" +
                "value=" + value +
                '}';
    }

    public void add(Node5 node){
        if (this.value > node.value){
            if (this.left == null){
                this.left = node;
            } else {
                this.left.add(node);
            }
        } else {
            if (this.right == null){
                this.right = node;
            } else {
                this.right.add(node);
            }
        }
        //添加完毕后，判断是否是平衡二叉树,左比右大
        if (leftLength() - rightLength() > 1){
            //如果左子树的，右子树大于左子树，先左子树左旋转，在root节点右旋
            if (left != null && left.rightLength() > left.leftLength()){
                left.leftRotate();
                rightRotate();
            } else {
                rightRotate();
            }
        } else if (rightLength() - leftLength() > 1){
            if (right != null && right.leftLength() > right.rightLength()){
                right.rightRotate();
                leftRotate();
            } else {
                leftRotate();
            }
        }
    }

    public void infixOrder(){
        if (this.left != null){
            this.left.infixOrder();
        }
        System.out.println(this);
        if (this.right != null){
            this.right.infixOrder();
        }
    }

    //左树高度
    public int leftLength(){
        if (left == null){
            return 0;
        }
        return left.getLength();
    }

    //右树高度
    public int rightLength(){
        if (right == null){
            return 0;
        }
        return right.getLength();
    }

    //返回树的高度
    public int getLength(){
        return Math.max(left == null ? 0 : left.getLength(), right == null ? 0 : right.getLength()) + 1;
    }

    //左旋转
    public void leftRotate(){
        //旋转后的节点顺序
        Node5 newNode = new Node5(value);
        newNode.left = left;
        newNode.right = right.left;
        //修改root节点
        value = right.value;
        left = newNode;
        right = right.right;
    }

    //右旋转
    public void rightRotate(){
        //旋转后的节点顺序
        Node5 newNode = new Node5(value);
        newNode.left = left.right;
        newNode.right = right;
        //修改root节点
        value = left.value;
        right = newNode;
        left = left.left;
    }
}
