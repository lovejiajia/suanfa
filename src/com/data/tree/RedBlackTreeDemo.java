package com.data.tree;

import java.util.Scanner;

/**
 * 红黑树(2022.1.2)
 * 删除已完成
 * 参考:https://blog.csdn.net/weixin_41563161/article/details/104452601?ops_request_misc=&request_id=&biz_id=102&utm_term=%E7%BA%A2%E9%BB%91%E6%A0%91%E5%88%A0%E9%99%A4java&utm_medium=distribute.pc_search_result.none-task-blog-2~all~sobaiduweb~default-3-104452601.nonecase&spm=1018.2226.3001.4187
 *     https://blog.csdn.net/qq_41885278/article/details/104901078?spm=1001.2101.3001.6650.3&utm_medium=distribute.pc_relevant.none-task-blog-2%7Edefault%7EBlogCommendFromBaidu%7Edefault-3.no_search_link&depth_1-utm_source=distribute.pc_relevant.none-task-blog-2%7Edefault%7EBlogCommendFromBaidu%7Edefault-3.no_search_link&utm_relevant_index=3
 *     https://blog.csdn.net/victoryyounger/article/details/112109261?ops_request_misc=&request_id=&biz_id=102&utm_term=%E7%BA%A2%E9%BB%91%E6%A0%91%E5%88%A0%E9%99%A4java&utm_medium=distribute.pc_search_result.none-task-blog-2~all~sobaiduweb~default-4-112109261.nonecase&spm=1018.2226.3001.4187
 * 增加已完成
 *      TreeOperation.java
 *
 * 左旋转、右旋转，记得改颜色
 */
public class RedBlackTreeDemo {
    public static void main(String[] args) {
        RedBlackTree tree1 = new RedBlackTree();
        while(true) {
            Scanner sc = new Scanner(System.in);
            System.out.println("请输入key:");
            String key = sc.next();
            if (Integer.parseInt(key) == 0){
                System.out.println("请输入删除key:");
                key = sc.next();
                tree1.del(Integer.parseInt(key));
                TreeOperation1.show(tree1.getRoot());
                continue;
            }

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
    public Node5 delMix(Node5 node){
        while (node.left != null){
            node = node.left;
        }
        return node;
    }
    private Node5 getKey(int key) {
        Node5 temp = this.root;
        while (temp != null) {
            if (temp.key > key) {
                temp = temp.left;
            } else if (temp.key < key) {
                temp = temp.right;
            } else {
                return temp;
            }
        }
        return null;
    }

    /**删除节点
     *  1.没有节点，直接删除
     *  2.有一个节点，子节点和次节点互换，删除子节点
     *  3.有两个节点，找到后继节点，互换，删除后继节点
     * 总结:删除的节点都没有子节点
     * @param key
     */
    //删除
    public void del(int key){
        Node5 node = getKey(key);
        if (node == null){
            System.out.println("节点不存在");
        }
        if (node == root){
            root = null;
        } else {
            //有两个节点，找到后继节点
            if (node.left != null && node.right != null){
                Node5 temp = delMix(node.right);
                node.key = temp.key;
                //删除节点变为后继节点
                node = temp;
            }
            //有一个节点，或没有节点
            Node5 temp = node.left == null ? node.right : node.left;
            if (temp != null){
                node.key = temp.key;
            } else {
                temp = node;
            }
            //修复红黑树，传入实际删除节点
            deleteLeafFix(temp);
            Node5 parent = parentOf(temp);
            //删除父子节点关系
            if (parent.left == temp) {
                parent.left = null;
            } else {
                parent.right = null;
            }
            temp.parent = null;
        }
    }


    /** 红黑树删除修复(兄弟节点指为黑的兄弟节点，兄弟节点为红，父、兄换色，父旋转，即可得到兄弟节点为黑)
     * 按删除节点为父节点的左右，分为两种情况
     *  按删除节点及删除节点兄弟节点的情况分为以下3种情况
     *  1.可自行调整（删除节点为红）
     *  2.找兄弟节点借(兄弟节点有子节点)
     *      1.RL、LR情况，先换色，在旋转，即可得到RR、LL
     *      2.RR、LL（兄、父、兄左变色，父旋转）
     *  3.兄弟节点不借(兄弟节点没有子节点)
     *          兄弟节点变红，从父节点开始循环
     *              1.父节点有红，红变黑
     *              2.父节点没有红，跟节点的兄弟节点黑减1(跟节点、兄弟节点变色，跟节点旋转)
     * @param node
     */
    private void deleteLeafFix(Node5 node) {
        while (node != root && node.color == BLACK){
            Node5 parent = parentOf(node); //父节点
            //删除节点为父节点的左子节点
            if (parent.left == node){
                Node5 brother = parent.right;
                //兄弟节点为红色，其父、子节点必为黑,把兄弟节点改黑(父兄互换色，父旋转)
                if (brother.color == RED){
                    parent.color = RED;
                    brother.color = BLACK;
                    leftRotate(parent);
                    brother = parent.right;
                }
                //3.兄弟不借，兄弟没有子节点(向上递归，直到跟节点或者红色节点)
                if (brother.left == null && brother.right == null){
                    brother.color = RED;
                    node = parent;
                    //循环到跟节点或者红色节点
                    while (node != root && node.color == BLACK){
                        node = node.parent;
                    }
                    //跟节点，变色，旋转
                    if (node == root){
                        node.color = RED;
                        leftRotate(node);
                    //红色节点，变黑色
                    } else {
                        node.color = BLACK;
                        node = root;
                    }
                //2.兄弟有借,兄弟节点有子节点
                } else {
                    //兄弟节点没有右节点，兄、兄左换色，兄右旋
                    if (brother.right == null){
                        brother.color = RED;
                        brother.left.color = BLACK;
                        rightRotate(brother);
                        brother = parent.right;
                    }
                    brother.color = parent.color;
                    parent.color = BLACK;
                    brother.right.color = BLACK;
                    leftRotate(parent);
                    node = root;
                }
            //删除节点为父节点的右子节点
            }else {
                Node5 brother = parent.left;
                //兄弟节点为红色，其父、子节点必为黑,把兄弟节点改黑(父兄互换色，父旋转)
                if (brother.color == RED){
                    parent.color = RED;
                    brother.color = BLACK;
                    rightRotate(parent);
                    brother = parent.left;
                }
                //3.兄弟无借，兄弟没有子节点
                if (brother.left == null && brother.right == null){
                    brother.color = RED;
                    node = parent;
                    //循环到跟节点或者红色节点
                    while (node != root && node.color == BLACK){
                        node = node.parent;
                    }
                    //跟节点，变色，旋转
                    if (node == root){
                        node.color = RED;
                        rightRotate(node);
                        //红色节点，变黑色
                    } else {
                        node.color = BLACK;
                        node = root;
                    }
                    //2.兄弟有借,兄弟节点有子节点
                } else {
                    //兄弟节点没有右节点，兄、兄左换色，兄右旋
                    if (brother.left == null){
                        brother.color = RED;
                        brother.right.color = BLACK;
                        leftRotate(brother);
                        brother = parent.left;
                    }
                    brother.color = parent.color;
                    parent.color = BLACK;
                    brother.left.color = BLACK;
                    rightRotate(parent);
                    node = root;
                }
            }
        }
        //情况3，补充
        root.color = BLACK;
    }

    /**
     * 1.删除为红，直接删除
     * 2.删除为黑(因交换删除法，实际删除节点，有且只有一个子节点)
     *      1.有子节点，父为黑，子必为红，子节点改黑，替换父节点
     *      2.无子节点
     *          1.兄为红，兄改黑，兄左改红（右），父左旋
     *          2.兄为黑
     *              1.兄有两个节点，必为红(兄改父颜色，父、兄右改黑，父左旋)
     *              2.兄有且只有右节点，必为红(可同1，判断父色，父为红，左旋;父为黑，兄右改黑，父左旋)
     *              3.兄有且只有左节点，必为红(父为黑，兄改黑，兄右旋，父左旋;父为红，父改黑,兄右旋，父左旋)（兄和兄左互换颜色，兄右旋，在经过1）
     *              4.兄无节点，删除，兄改红，向上循环，父为红或跟节点，按情况调整
     * @param
     */
    /*
    //删除修复方法
    private void deleteLeafFix(Node5 node) {
        if (node.color == BLACK){
            Node5 parent = parentOf(node);

            Node5 t = node.left == null ? node.right : node.left;
            //2.1.有子节点，父为黑，子必为红，子节点改黑，替换父节点
            if (t != null){
                t.color = BLACK;
            //2.无子节点
            } else {
                //删除节点是父节点的左子节点
                if (parent.left == node){
                    Node5 brother =parent.right;
                    //1.兄为红，兄改黑，兄左改红（右），父左旋
                    if (brother.color == RED){
                        brother.color = BLACK;
                        brother.left.color = RED;
                        leftRotate(parent);
                    //2.兄为黑
                    } else {
                        //3.兄有且只有左节点，必为红(父为黑，兄改黑，兄右旋，父左旋;父为红，父改黑,兄右旋，父左旋)（兄和兄左互换颜色，兄右旋，在经过1）
                        if (brother.left != null && brother.right == null){
                            brother.color = RED;
                            brother.left.color = BLACK;
                            rightRotate(brother);
                        }
                        //1.兄有两个节点，必为红(兄改父颜色，父、兄右改黑，父左旋)
                        //2.兄有且只有右节点，必为红(可同1，判断父色，父为红，左旋;父为黑，兄右改黑，父左旋)
                        if ((brother.left != null && brother.right != null) || brother.right != null){
                            brother.color = parent.color;
                            parent.color = brother.right.color = BLACK;
                            leftRotate(parent);
                        //4.兄无节点，删除，兄改红，父为删除节点递归
                        }else {

                        }
                    }
                }else {
                    Node5 brother =parent.left;
                }
            }
        }
    }*/




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


