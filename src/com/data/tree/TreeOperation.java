package com.data.tree;


import java.util.Scanner;

/**
 * ClassName: TreeOperation1
 * Description:
 * date: 2020/1/12 16:46
 *
 * @author 巍巍老师
 * @since 1.0.0
 */
public class TreeOperation {

    /**
     * ClassName: TestRBTree
     * Description:
     * date: 2020/1/12 16:07
     *
     * @author 巍巍老师
     * @since 1.0.0
     */
    public static void main(String[] args) {
        RBTree<String, Object> rbt = new RBTree();
        //测试输入：ijkgefhdabc
        while(true) {
            Scanner sc = new Scanner(System.in);
            System.out.println("请输入key:");
            String key = sc.next();

            rbt.insert(key, null);
            TreeOperation.show(rbt.getRoot());
        }
    }
      /*
    树的结构示例：
              1
            /   \
          2       3
         / \     / \
        4   5   6   7
    */

    // 用于获得树的层数
    public static int getTreeDepth(RBTree.RBNode root) {
        return root == null ? 0 : (1 + Math.max(getTreeDepth(root.getLeft()), getTreeDepth(root.getRight())));
    }


    private static void writeArray(RBTree.RBNode currNode, int rowIndex, int columnIndex, String[][] res, int treeDepth) {
        // 保证输入的树不为空
        if (currNode == null) return;
        // 先将当前节点保存到二维数组中
        res[rowIndex][columnIndex] = String.valueOf(currNode.getKey() /*+ "-" + (currNode.isColor() ? "R" : "B") + ""*/);

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


    public static void show(RBTree.RBNode root) {
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


/**
 * ClassName: RBTree
 * Description:
 * date: 2020/1/12 11:07
 * <p>
 * ①创建RBTree，定义颜色
 * ②创建RBNode
 * ③辅助方法定义：parentOf(node)，isRed(node)，setRed(node)，setBlack(node)，inOrderPrint(RBNode tree)
 * ④左旋方法定义：leftRotate(node)
 * ⑤右旋方法定义：rightRotate(node)
 * ⑥公开插入接口方法定义：insert(K key, V value);
 * ⑦内部插入接口方法定义：insert(RBNode node);
 * ⑧修正插入导致红黑树失衡的方法定义：insertFIxUp(RBNode node);
 * ⑨测试红黑树正确性
 *
 * @author 巍巍老师
 * @since 1.0.0
 */
class RBTree<K extends Comparable<K>, V> {
    //定义颜色常量
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    //红黑树的树根
    private RBNode root;

    public RBNode getRoot() {
        return root;
    }

    /**
     * 公开的插入接口
     *
     * @param key   键
     * @param value 值
     */
    public void insert(K key, V value) {
        RBNode node = new RBNode();
        node.setKey(key);
        node.setValue(value);
        node.setColor(RED);
        insert(node);
    }

    /**
     * 内部插入接口定义
     */
    private void insert(RBNode node) {
        //1.找到插入的位置
        RBNode parent = null;
        RBNode x = this.root;
        while (x != null) {
            parent = x;

            //a > b 则返回 1，否则返回 -1 ，相等返回0
            int cmp = node.key.compareTo(parent.key);

            if (cmp < 0) {
                x = x.left;
            } else if (cmp == 0) {
                parent.setValue(node.value);
                return;
            } else {
                x = x.right;
            }
        }

        node.parent = parent;

        if (parent != null) {
            if (node.key.compareTo(parent.key) < 0) {
                parent.left = node;
            } else {
                parent.right = node;
            }
        } else {
            this.root = node;
        }

        //插入之后需要进行修复红黑树，让红黑树再次平衡。
        insertFixUp(node);
    }


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
    private void insertFixUp(RBNode node) {
        RBNode parent = parentOf(node);
        RBNode gparent = parentOf(parent);
        //存在父节点且父节点为红色
        if (parent != null && isRed(parent)) {
            //父节点是红色的，那么一定存在爷爷节点

            //父节点为爷爷节点的左子树
            if (parent == gparent.left) {
                RBNode uncle = gparent.right;
                //4.1：叔叔节点存在，并且为红色（父-叔 双红）
                //将父和叔染色为黑色，再将爷爷染红，并将爷爷设置为当前节点，进入下一次循环判断
                if (uncle != null && isRed(uncle)) {
                    setBlack(parent);
                    setBlack(uncle);
                    setRed(gparent);
                    insertFixUp(gparent);
                    return;
                }

                //叔叔节点不存在，或者为黑色，父节点为爷爷节点的左子树
                if (uncle == null || isBlack(uncle)) {
                    //插入节点为其父节点的右子节点（LR情况）=>
                    //左旋（父节点），当前节点设置为父节点，进入下一次循环
                    if (node == parent.right) {
                        leftRotate(parent);
                        insertFixUp(parent);
                        return;
                    }

                    //插入节点为其父节点的左子节点（LL情况）=>
                    //变色（父节点变黑，爷爷节点变红），右旋爷爷节点
                    if (node == parent.left) {
                        setBlack(parent);
                        setRed(gparent);
                        rightRotate(gparent);
                    }
                }

            } else {//父节点为爷爷节点的右子树
                RBNode uncle = gparent.left;
                //4.1：叔叔节点存在，并且为红色（父-叔 双红）
                //将父和叔染色为黑色，再将爷爷染红，并将爷爷设置为当前节点，进入下一次循环判断
                if (uncle != null && isRed(uncle)) {
                    setBlack(parent);
                    setBlack(uncle);
                    setRed(gparent);
                    insertFixUp(gparent);
                    return;
                }

                //叔叔节点不存在，或者为黑色，父节点为爷爷节点的右子树
                if (uncle == null || isBlack(uncle)) {
                    //插入节点为其父节点的左子节点（RL情况）
                    //右旋（父节点）得到RR情况，当前节点设置为父节点，进入下一次循环
                    if (node == parent.left) {
                        rightRotate(parent);
                        insertFixUp(parent);
                        return;
                    }

                    //插入节点为其父节点的右子节点（RR情况）=>
                    //变色（父节点变黑，爷爷节点变红），右旋爷爷节点
                    if (node == parent.right) {
                        setBlack(parent);
                        setRed(gparent);
                        leftRotate(gparent);
                    }
                }

            }
        }

        setBlack(this.root);
    }


    /**
     * 左旋方法
     * 左旋示意图：左旋x节点
     * p                   p
     * |                   |
     * x                   y
     * / \         ---->   / \
     * lx  y               x   ry
     * / \             / \
     * ly  ry          lx  ly
     * <p>
     * 左旋做了几件事？
     * 1.将y的左子节点赋值给x的右边，并且把x设置为y的左子节点的父节点
     * 2.将x的父节点（非空时）指向y，更新y的父节点为x的父节点
     * 3.将y的左子节点指向x，更新x的父节点为y
     */
    private void leftRotate(RBNode x) {
        RBNode y = x.right;
        //将y的左子节点赋值给x的右边
        x.right = y.left;
        //并且把x设置为y的左子节点的父节点
        if (y.left != null) {
            y.left.parent = x;
        }

        //将x的父节点（非空时）指向y
        if (x.parent != null) {
            //如果x是parent左子树，则把y安放到parent的左边
            if (x.parent.left == x) {
                x.parent.left = y;
            } else {//否则把y安放到parent的右边
                x.parent.right = y;
            }
            //更新y的父节点为x的父节点
            y.parent = x.parent;
        } else {
            this.root = y;
            this.root.parent = null;
        }

        y.left = x;
        x.parent = y;
    }


    /**
     * 右旋方法
     * 右旋示意图：右旋y节点
     * <p>
     * p                       p
     * |                       |
     * y                       x
     * / \          ---->      / \
     * x   ry                  lx  y
     * / \                         / \
     * lx  ly                      ly  ry
     * <p>
     * 右旋都做了几件事？
     * 1.将x的右子节点 赋值 给了 y 的左子节点，并且更新x的右子节点的父节点为 y
     * 2.将y的父节点（不为空时）指向x，更新x的父节点为y的父节点
     * 3.将x的右子节点指向y，更新y的父节点为x
     */
    private void rightRotate(RBNode y) {
        //1.将x的右子节点赋值给y的左子节点，并将y赋值给x右子节点的父节点（x右子节点非空时）
        RBNode x = y.left;
        y.left = x.right;
        if (x.right != null) {
            x.right.parent = y;
        }

        //2.将y的父节点p（非空时）赋值给x的父节点，同时更新p的子节点为x（左或右）
        x.parent = y.parent;

        if (y.parent != null) {
            if (y.parent.left == y) {
                y.parent.left = x;
            } else {
                y.parent.right = x;
            }
        } else {
            this.root = x;
            this.root.parent = null;
        }

        //3.将x的右子节点赋值为y，将y的父节点设置为x
        x.right = y;
        y.parent = x;
    }


    /**
     * 获取当前节点的父节点
     */
    private RBNode parentOf(RBNode node) {
        if (node != null) {
            return node.parent;
        }
        return null;
    }

    /**
     * node节点是否为红色
     *
     * @return boolean true 表示是红色  false 表示不是红色
     */
    private boolean isRed(RBNode node) {
        if (node != null) {
            return node.isColor() == RED;
        }
        return false;
    }

    /**
     * 设置节点为红色
     */
    private void setRed(RBNode node) {
        if (node != null) {
            node.setColor(RED);
        }
    }

    /**
     * 设置节点为黑色
     */
    private void setBlack(RBNode node) {
        if (node != null) {
            node.setColor(BLACK);
        }
    }

    /**
     * 中序打印，可以将二叉查找树有顺序的打印出来
     */
    public void inOrderPrint() {
        if (this.root != null) {
            inOrderPrint(this.root);
        }
    }

    private void inOrderPrint(RBNode node) {
        if (node != null) {
            inOrderPrint(node.left);
            System.out.println("key -> " + node.key + ", value -> " + node.value);
            inOrderPrint(node.right);
        }
    }


    /**
     * node节点是否为黑色
     *
     * @return boolean true 表示是黑色  false 表示不是黑色
     */
    private boolean isBlack(RBNode node) {
        if (node != null) {
            return node.isColor() == BLACK;
        }
        return false;
    }


    /**
     * 红黑树Node
     */
    static class RBNode<K extends Comparable<K>, V> {
        //颜色
        private boolean color;
        //左子节点
        private RBNode left;
        //右子节点
        private RBNode right;
        //父节点
        private RBNode parent;
        //key
        private K key;
        //value
        private V value;

        public RBNode(boolean color, RBNode left, RBNode right, RBNode parent, K key, V value) {
            this.color = color;
            this.left = left;
            this.right = right;
            this.parent = parent;
            this.key = key;
            this.value = value;
        }

        public RBNode() {
        }

        public boolean isColor() {
            return color;
        }

        public void setColor(boolean color) {
            this.color = color;
        }

        public RBNode getLeft() {
            return left;
        }

        public void setLeft(RBNode left) {
            this.left = left;
        }

        public RBNode getRight() {
            return right;
        }

        public void setRight(RBNode right) {
            this.right = right;
        }

        public RBNode getParent() {
            return parent;
        }

        public void setParent(RBNode parent) {
            this.parent = parent;
        }

        public K getKey() {
            return key;
        }

        public void setKey(K key) {
            this.key = key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }
    }


    /*****************************************************************************
     * Print Method
     *****************************************************************************/


    public void padding(String ch, int n) {
        int i;
        for (i = 0; i < n; i++)
            System.out.printf(ch);

    }

    void print_node(RBNode root, int level) {
        if (root == null) {
            padding("\t", level);
            System.out.println("NIL");

        } else {
            print_node(root.right, level + 1);
            padding("\t", level);
            if (root.color == BLACK) {
                System.out.printf(root.key + "(" + (root.isColor() ? "红" : "黑") + ")" + "\n");
            } else
                System.out.printf(root.key + "(" + (root.isColor() ? "红" : "黑") + ")" + "\n");
            print_node(root.left, level + 1);
        }
    }

    void print_tree() {
        print_node(this.root, 0);
        System.out.printf("-------------------------------------------\n");
    }
}

