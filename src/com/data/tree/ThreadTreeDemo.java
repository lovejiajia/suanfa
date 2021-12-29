package com.data.tree;

/**
 * 线索化二叉树
 */
public class ThreadTreeDemo {
    public static void main(String[] args) {
        //创建需要的结点
        TreadTree root = new TreadTree(1, "宋江");
        TreadTree binaryTree2 = new TreadTree(2, "吴用");
        TreadTree binaryTree3 = new TreadTree(3, "卢俊义");
        TreadTree binaryTree4 = new TreadTree(4, "林冲");
        TreadTree binaryTree5 = new TreadTree(5, "关胜");

        //创建二叉树
        root.setLeft(binaryTree2);
        root.setRight(binaryTree3);
        binaryTree2.setLeft(binaryTree4);
        binaryTree2.setRight(binaryTree5);

        root.preOrder();
        root.ThreadedTree(root);
        System.out.println(binaryTree4.getLeft());
        System.out.println(binaryTree4.getRight());
        root.ThreadedList(root);
    }
}

/**
 * 线索二叉树
 */
class TreadTree{
    private int no;
    private String name;
    private TreadTree left;//前驱节点
    private TreadTree right;//后继节点
    private int LeftType;//左节点类型，默认0，前驱为1
    private int RightType;//右节点类型，默认0，后驱为1
    TreadTree pre = null;

    public TreadTree(int no, String name) {
        this.no = no;
        this.name = name;
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

    public TreadTree getLeft() {
        return left;
    }

    public void setLeft(TreadTree left) {
        this.left = left;
    }

    public TreadTree getRight() {
        return right;
    }

    public void setRight(TreadTree right) {
        this.right = right;
    }

    @Override
    public String toString() {
        return "BinaryTree{" +
                "no=" + no +
                ", name='" + name + '\'' +
                '}';
    }

    //前序遍历
    public void preOrder(){
        System.out.println(this);//先输出自身
        //向左子树遍历
        if (this.left != null){
            this.left.preOrder();
        }
        //向右子树遍历
        if (this.right != null){
            this.right.preOrder();
        }
    }

    //中序遍历
    public void infixOrder(){
        //向左子树遍历
        if (this.left != null){
            this.left.infixOrder();
        }
        System.out.println(this);
        if (this.right != null){
            this.right.infixOrder();
        }
    }

    //后序遍历
    public void postOrder(){
        //向左子树遍历
        if (this.left != null){
            this.left.infixOrder();
        }
        if (this.right != null){
            this.right.infixOrder();
        }
        System.out.println(this);
    }

    //前序遍历查找
    public TreadTree preOrderSearch(int no){
        if (this.no == no){
            return this;
        }
        if (this.left != null){
            this.left.preOrderSearch(no);
        }
        if (this.right != null){
            this.right.preOrderSearch(no);
        }
        return null;
    }

    //中序遍历查找
    public TreadTree infixOrderSearch(int no){
        if (this.left != null){
            this.left.preOrderSearch(no);
        }
        if (this.no == no){
            return this;
        }
        if (this.right != null){
            this.right.preOrderSearch(no);
        }
        return null;
    }

    //后续遍历查找
    public TreadTree postOrderSearch(int no){
        if (this.left != null){
            this.left.preOrderSearch(no);
        }
        if (this.right != null){
            this.right.preOrderSearch(no);
        }
        if (this.no == no){
            return this;
        }
        return null;
    }

    //删除节点
    public void del(int no){
        if (this.left != null){
            if (this.left.no == no){
                this.left = null;
            } else {
                this.left.del(no);
            }
        }
        if (this.right != null){
            if (this.right.no == no){
                this.right = null;
            } else {
                this.right.del(no);
            }
        }
    }

    //中序线索化树
    public void ThreadedTree(TreadTree root){
        if (root == null){
            return;
        }
        //线索化左子树
        ThreadedTree(root.left);
        if (root.left == null){
            root.left = pre;
            root.LeftType = 1;
        }
        //线索化右子树
        if (pre != null && pre.right == null){
            pre.right = root;
            RightType = 1;
        }
        pre = root;
        ThreadedTree(root.right);
    }

    //遍历树
    public void ThreadedList(TreadTree root){
        TreadTree head = root;
        //找到第一个节点
        while (head.LeftType == 0){
            head = head.left;
        }
        //输出第一个节点
        System.out.println(head);
        //先遍历到最左节点
        while (head.right != null){
            //节点后移，输出此节点
            head = head.right;
            System.out.println(head);
        }
    }
}