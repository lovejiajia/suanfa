package com.data.tree;

public class RedBlackTreeDemo {
    public static void main(String[] args) {
        int[] arr = { 10, 11, 7, 8, 9,12,13,5,4,3,16 };
        //创建一个 AVLTree对象
        RedBlackTree avlTree = new RedBlackTree();
        //添加结点
        for(int i=0; i < arr.length; i++) {
            avlTree.add(new Node5(arr[i]));
        }

        //遍历
        System.out.println("中序遍历");
        avlTree.infixOrder();

    }
}

class RedBlackTree{
    private Node5 root;

    //获取root节点
    public Node5 getRoot(){
        return root;
    }
    //添加
    public void add(Node5 node){
        /**
         * 1.空树(添加节点，红该黑)
         * 2.已存在(value赋值)
         * 3.父节点为黑，直接插入
         * 4.父节点为红
         */
        if (root == null){
            node.color = false;
            root = node;
        } else if (root.search(node.value) != null){
            root.search(node.value).value = node.value;
        }else{
            root.add(node);
        }
        //添加后判断是否遵守红黑树规则
        /** 父节点为红
         */
        //父节点
        Node5 parent = root.searchParent(node.value);
        if (parent != null){
            //爷爷节点
            Node5 grandpa = root.searchParent(parent.value);
            if (parent.color == true){
                check(node, parent, grandpa);
            }
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
    //是否遵守红黑树规则

    /**
     * 4.父节点为红
     *      1.叔节点存在
     *      2.叔节点不存在，插入节点为爷节点的左节点
     *      3.叔节点不存在，插入节点为爷节点的右节点
     * @param node     插入节点
     * @param parent    父节点
     * @param grandpa   爷节点
     */
    public void check(Node5 node, Node5 parent, Node5 grandpa){
        /**
         * 1.叔节点存在,父树节点该黑，爷节点改红，以爷节点为插入节点进行平衡
         */
        if (grandpa.left != null && grandpa.right != null){
            grandpa.left.color = false;
            grandpa.right.color = false;
            if (root != grandpa){
                grandpa.color = true;
            }
            Node5 parent1 = root.searchParent(grandpa.value);
            if (parent1 != null){
                Node5 grandpa1 = root.searchParent(parent1.value);
                if (parent1 != null && grandpa1 != null){
                    check(grandpa, parent1, grandpa1);
                }
            }
            /**2.叔节点不存在，插入节点为爷节点的左节点
             *      1.插入节点为父节点的左节点，改色，爷节点右旋
             *      2.插入节点为父节点的右节点，父节点左旋，改色，爷节点右旋
             */
        } else if (grandpa.left != null){
            if (parent.left != null && parent.left.value == node.value){
                parent.color = false;
                grandpa.color = true;
                grandpa.rightRotate();
            } else {//?
                parent.leftRotate();
                node.color = false;
                grandpa.color = true;
                grandpa.rightRotate();
            }
            /** 3.叔节点不存在，插入节点为爷节点的右节点
             *      1.插入节点为父节点的右节点，改色，爷节点左旋
             *      2.插入节点为父节点的左节点，父节点右旋，改色，爷节点左旋
             */
        } else if (grandpa.right != null){
            if (parent.right != null && parent.right.value == node.value){
                parent.color = false;
                grandpa.color = true;
                grandpa.leftRotate();
             } else {
                parent.rightRotate();
                node.color = false;
                grandpa.color = true;
                grandpa.leftRotate();
            }
        }
    }
}

class Node5 {
    int value;
    Node5 left;
    Node5 right;
    boolean color;//true红色，false黑色，默认红色

    public Node5(int value) {
        this.value = value;
        this.color = true;
    }

    @Override
    public String toString() {
        return "Node5{" +
                "value=" + value +
                ", color=" + color +
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

    //查找爷爷节点
    public Node5 searchParent(int value){
        if ((this.left != null && this.left.value == value) || (this.right != null && this.right.value == value)){
            return this;
        } else {
            if (this.left != null && this.value > value){
                return this.left.searchParent(value);
            } else if (this.right != null && this.value < value){
                return this.right.searchParent(value);
            } else {
                return null;
            }
        }
    }

    //查找节点
    public Node5 search(int data){
        if (this.value == data){
            return this;
        } else if (this.value > data){
            if (this.left == null){
                return null;
            }
            return this.left.search(data);
        } else {
            if (this.right == null){
                return null;
            }
            return this.right.search(data);
        }
    }
}
