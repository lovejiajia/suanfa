package com.data.tree;

import java.util.Scanner;

/**
 * 红黑树
 * 增加已完成
 * 删除以后完成
 * 左旋转、右旋转，记得改颜色
 */
public class RedBlackTreeDemo {
    public static void main(String[] args) {
        int[] arr = { 10, 11, 7, 8, 9,12,13,5,4,3,2,1,16,18,19,20,21,23,25,27 };
        //创建一个 AVLTree对象
        RedBlackTree tree = new RedBlackTree();
        //添加结点
        for(int i=0; i < arr.length; i++) {
            tree.add(new RedBlackTree.Node5(arr[i]));
        }
        System.out.println("已添加完毕");
        TreeOperation1.show(tree.getRoot());


        RedBlackTree tree1 = new RedBlackTree();
        while(true) {
            Scanner sc = new Scanner(System.in);
            System.out.println("请输入key:");
            String key = sc.next();

            tree1.add(new RedBlackTree.Node5(Integer.parseInt(key)));
            TreeOperation1.show(tree1.getRoot());
        }
    }
}


class RedBlackTree{
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private Node5 root;

    public Node5 getRoot(){
        return root;
    }
    public boolean isRed(Node5 node){
        if (node.color == true){
            return true;
        }
        return false;
    }

    public void setRed(Node5 node){
        node.color = true;
    }

    public void setBlack(Node5 node){
        node.color = false;
    }
    //查找父节点
    public Node5 parentOf(Node5 node){
        if (node != null){
            return node.parent;
        }
        return null;
    }
    //中序遍历
    public void inOrderPrint(Node5 node){
        if (node != null){
            inOrderPrint(node.left);
            System.out.println("key: " + node.key + "    color: " + node.color);
            inOrderPrint(node.right);
        }
    }

    //左旋转(改当前节点的右父节点，右节点的父左节点，右左节点的父节点，父节点的子节点)
    public void leftRotate(Node5 node){
        //把旋转节点的右节点赋值
        Node5 temp = node.right;
       //右左不为空，改右左节点父节点
        if (temp.left != null){
            temp.left.parent = node;
        }
        //父节点不为空
        if (node.parent != null){
            //为父节点的左节点，改父节点的子节点
            if (node.parent.left == node){
                node.parent.left = temp;
            } else {
                node.parent.right = temp;
            }
            //右节点的父节点
            temp.parent = node.parent;
        } else {
            //为空，改为root
            this.root = temp;
            temp.parent = null;
        }
        node.right = temp.left;//改右节点
        temp.left = node;//右节点的左节点
        node.parent = temp;//父节点
    }

    //右旋转（改父左节点，左节点的右父节点，左右节点的父节点，父节点的子节点）
    public void rightRotate(Node5 node){
        Node5 temp = node.left;
        //改左右节点的父节点，
        if (temp.right != null){
            temp.right.parent = node;
        }
        node.left = temp.right;//把node的左节点指向左右节点
        if (node.parent != null){
            if (node.parent.left == node){
                node.parent.left = temp;
            } else {
                node.parent.right = temp;
            }
            temp.parent = node.parent;
        } else {
            this.root = temp;
            temp.parent = null;
        }
        temp.right = node;
        node.parent = temp;
    }

    //修正方法
    /**
     * 插入后修复红黑树平衡的方法
     * |---情景1：红黑树为空树
     * |---情景2：插入节点的key已经存在
     * |---情景3：插入节点的父节点为黑色
     * <p>
     * 情景4 需要咱们去处理
     * |---情景4：插入节点的父节点为红色
     * |---情景4.1：叔叔节点存在，并且为红色（父-叔 双红）
     * |---情景4.2：叔叔节点不存在，或者为黑色，父节点为爷爷节点的左子树
     * |---情景4.2.1：插入节点为其父节点的左子节点（LL情况）
     * |---情景4.2.2：插入节点为其父节点的右子节点（LR情况）
     * |---情景4.3：叔叔节点不存在，或者为黑色，父节点为爷爷节点的右子树
     * |---情景4.3.1：插入节点为其父节点的右子节点（RR情况）
     * |---情景4.3.2：插入节点为其父节点的左子节点（RL情况）
     */
    public void insertFIxUp(Node5 node){
        Node5 parent = parentOf(node);
        Node5 grandpa = parentOf(parent);

        //父节点存在，并为红色，必有爷节点
        if (parent != null && parent.color == true){
            //如果在爷节点的左侧
            if (grandpa.left == parent){
                //叔节点
                Node5 uncle = grandpa.right;
                //叔叔节点存在，并为红,叔叔和父节点变黑，爷节点变红，已爷节点为插入节点进行修复
                if (uncle != null && uncle.color == true){
                    uncle.color = BLACK;
                    parent.color = BLACK;
                    grandpa.color = RED;
                    insertFIxUp(grandpa);
                //叔叔节点不存在，或为黑,
                } else{
                    //插入节点为父节点的左节点，父爷改色，爷右旋
                    if (parent.left == node){
                        parent.color = BLACK;
                        grandpa.color = RED;
                        rightRotate(grandpa);
                    //插入节点为父节点的右节点，爷左旋，子爷改色，爷右旋
                    } else {
                        leftRotate(parent);
                        node.color = BLACK;
                        grandpa.color = RED;
                        rightRotate(grandpa);
                    }
                }
            //如果在爷节点的右侧
            } else {
                //叔节点
                Node5 uncle = grandpa.left;
                //叔叔节点存在，并为红,叔叔和父节点变黑，爷节点变红，已爷节点为插入节点进行修复
                if (uncle != null && uncle.color == true){
                    uncle.color = BLACK;
                    parent.color = BLACK;
                    grandpa.color = RED;
                    insertFIxUp(grandpa);
                    //叔叔节点不存在，或为黑,
                } else{
                    //插入节点为父节点的左节点，父爷改色，爷右旋
                    if (parent.right == node){
                        parent.color = BLACK;
                        grandpa.color = RED;
                        leftRotate(grandpa);
                        //插入节点为父节点的右节点，爷左旋，子爷改色，爷右旋
                    } else {
                        rightRotate(parent);
                        node.color = BLACK;
                        grandpa.color = RED;
                        leftRotate(grandpa);
                    }
                }
            }
        }
        //设置root节点为黑色
        root.setColor(BLACK);
    }
    //添加
    public void add(Node5 node){
        Node5 parent = null;
        Node5 r = root;
        while (r != null){
            parent = r;
            if (r.key > node.key){
                r = r.left;
            } else if (r.key == node.key){
                System.out.println("值已修改");
                return;
            } else {
                r = r.right;
            }
        }
        node.parent = parent;
        if (parent != null){
            if (parent.key > node.key){
                parent.left = node;
            } else {
                parent.right = node;
            }
        } else {
            root = node;
        }
        //执行修复方法
        insertFIxUp(node);
    }
  static class Node5{
        private int key;//权值
        private Node5 left;//左节点
        private Node5 right;//右节点
        private Node5 parent;//父节点
        private boolean color;//颜色

        public Node5(int key) {
            this.key = key;
            this.color = true;
        }

        public Node5() {
        }

        @Override
        public String toString() {
            return "Node5{" +
                    "key=" + key +
                    ", color='" + color + '\'' +
                    '}';
        }

        public int getKey() {
            return key;
        }

        public void setKey(int key) {
            this.key = key;
        }

        public Node5 getLeft() {
            return left;
        }

        public void setLeft(Node5 left) {
            this.left = left;
        }

        public Node5 getRight() {
            return right;
        }

        public void setRight(Node5 right) {
            this.right = right;
        }

        public Node5 getParent() {
            return parent;
        }

        public void setParent(Node5 parent) {
            this.parent = parent;
        }

        public boolean getColor() {
            return color;
        }

        public void setColor(boolean color) {
            this.color = color;
        }

      public boolean isColor() {
            return color;
      }
  }

}
class TreeOperation1 {

    /**
     * ClassName: TestRBTree
     * Description:
     * date: 2020/1/12 16:07
     *
     * @author 巍巍老师
     * @since 1.0.0
     */
      /*
    树的结构示例：
              1
            /   \
          2       3
         / \     / \
        4   5   6   7
    */

    // 用于获得树的层数
    public static int getTreeDepth(RedBlackTree.Node5 root) {
        return root == null ? 0 : (1 + Math.max(getTreeDepth(root.getLeft()), getTreeDepth(root.getRight())));
    }


    private static void writeArray(RedBlackTree.Node5 currNode, int rowIndex, int columnIndex, String[][] res, int treeDepth) {
        // 保证输入的树不为空
        if (currNode == null) return;
        // 先将当前节点保存到二维数组中
        res[rowIndex][columnIndex] = String.valueOf(currNode.getKey() + "-" + (currNode.isColor() ? "R" : "B") + "");

        // 计算当前位于树的第几层
        int currLevel = ((rowIndex + 1) / 2);
        // 若到了最后一层，则返回
        if (currLevel == treeDepth) return;
        // 计算当前行到下一行，每个元素之间的间隔（下一行的列索引与当前元素的列索引之间的间隔）
        int gap = treeDepth - currLevel - 1;

        // 对左儿子进行判断，若有左儿子，则记录相应的"/"与左儿子的值
        if (currNode.getLeft() != null) {
            res[rowIndex + 1][columnIndex - gap] = "/";
            writeArray(currNode.getLeft(), rowIndex + 2, columnIndex - gap * 2, res, treeDepth);
        }

        // 对右儿子进行判断，若有右儿子，则记录相应的"\"与右儿子的值
        if (currNode.getRight() != null) {
            res[rowIndex + 1][columnIndex + gap] = "\\";
            writeArray(currNode.getRight(), rowIndex + 2, columnIndex + gap * 2, res, treeDepth);
        }
    }


    public static void show(RedBlackTree.Node5 root) {
        if (root == null) System.out.println("EMPTY!");
        // 得到树的深度
        int treeDepth = getTreeDepth(root);

        // 最后一行的宽度为2的（n - 1）次方乘3，再加1
        // 作为整个二维数组的宽度
        int arrayHeight = treeDepth * 2 - 1;
        int arrayWidth = (2 << (treeDepth - 2)) * 3 + 1;
        // 用一个字符串数组来存储每个位置应显示的元素
        String[][] res = new String[arrayHeight][arrayWidth];
        // 对数组进行初始化，默认为一个空格
        for (int i = 0; i < arrayHeight; i ++) {
            for (int j = 0; j < arrayWidth; j ++) {
                res[i][j] = " ";
            }
        }

        // 从根节点开始，递归处理整个树
        // res[0][(arrayWidth + 1)/ 2] = (char)(root.val + '0');
        writeArray(root, 0, arrayWidth/ 2, res, treeDepth);

        // 此时，已经将所有需要显示的元素储存到了二维数组中，将其拼接并打印即可
        for (String[] line: res) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < line.length; i ++) {
                sb.append(line[i]);
                if (line[i].length() > 1 && i <= line.length - 1) {
                    i += line[i].length() > 4 ? 2: line[i].length() - 1;
                }
            }
            System.out.println(sb.toString());
        }
    }

}


