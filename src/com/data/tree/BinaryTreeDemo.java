package com.data.tree;

/**
 * 二叉树
 */
public class BinaryTreeDemo {
    public static void main(String[] args) {
        //创建需要的结点
        BinaryTree root = new BinaryTree(1, "宋江");
        BinaryTree binaryTree2 = new BinaryTree(2, "吴用");
        BinaryTree binaryTree3 = new BinaryTree(3, "卢俊义");
        BinaryTree binaryTree4 = new BinaryTree(4, "林冲");
        BinaryTree binaryTree5 = new BinaryTree(5, "关胜");

        //创建二叉树
        root.setLeft(binaryTree2);
        root.setRight(binaryTree3);
        binaryTree2.setLeft(binaryTree4);
        binaryTree2.setRight(binaryTree5);

        root.preOrder();
        root.del(5);
        root.preOrder();
    }

}

class BinaryTree{
    private int no;
    private String name;
    private BinaryTree left;//左子树
    private BinaryTree right;//右子树

    public BinaryTree(int no, String name) {
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

    public BinaryTree getLeft() {
        return left;
    }

    public void setLeft(BinaryTree left) {
        this.left = left;
    }

    public BinaryTree getRight() {
        return right;
    }

    public void setRight(BinaryTree right) {
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
    public BinaryTree preOrderSearch(int no){
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
    public BinaryTree infixOrderSearch(int no){
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
    public BinaryTree postOrderSearch(int no){
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
}
