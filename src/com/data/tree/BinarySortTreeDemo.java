package com.data.tree;

/**
 * 二叉排序树
 */
public class BinarySortTreeDemo {
    public static void main(String[] args) {
        int[] arr = {10,5,7,1,6,4,3,2,14,13,15,11,8,9,12,18,17};
        BinarySortTree binarySortTree = new BinarySortTree();
        //循环的添加结点到二叉排序树
        for(int i = 0; i< arr.length; i++) {
            binarySortTree.add(new SortTree(arr[i]));
        }

        //中序遍历二叉排序树
        System.out.println("中序遍历二叉排序树~");
        binarySortTree.preOrder(); // 1, 3, 5, 7, 9, 10, 12
        binarySortTree.del(11);
        binarySortTree.del(5);
        binarySortTree.preOrder();
    }
}

class BinarySortTree{
    private SortTree root;

    //获取根节点
    public SortTree getRoot(){
        return this.root;
    }

    //添加节点
    public void add(SortTree node){
        if (root == null){
            root = node;
        } else {
            root.add(node);
        }
    }

    //查找节点
    public SortTree search(int data){
        if (root == null){
            return null;
        } else {
          return root.Search(data);
        }
    }

    //查找节点的父节点
    public SortTree searchParent(int data){
        if (root == null){
            return null;
        } else {
            return root.SearchParent(data);
        }
    }

    //删除节点
    public void del(int data){
        if (root == null){
            return;
        } else {
            //先找到节点
            SortTree target = search(data);
            SortTree parent = searchParent(data);//父节点
            boolean leftB = false;//左节点还是右节点,默认为右节点
            if (target == null || parent == null){
                return;
            }
            //是父节点的左节点还是右节点
            if (parent.left != null && parent.left == target){
                leftB = true;
            }
            //如果没有子节点
            if (target.left == null && target.right == null){
                if (leftB){
                    parent.left = null;
                } else {
                    parent.right = null;
                }
            } else if(target.left != null && target.right != null){//左右节点都不为空
                //把右节点的最小值赋值给删除节点
                int minVal = delmix(target.right);
                target.data = minVal;
                /**如果右子节点左右子节点都不为空,让左节点插入到右节点的最左节点
                SortTree tarL = target.left;
                SortTree tarR = target.right;
                SortTree temp = tarR;
                while (temp.left != null){
                    temp = temp.left;
                }
                //删除节点的左子节点，赋值给右子节点的最左节点
                temp.left = tarL;
                if (leftB){
                    parent.left = tarR;
                } else {
                    parent.right = tarR;
                }*/
            } else {//只有一个子节点
                //是左节点还是右节点
                if (target.left != null){
                    if (leftB){
                        parent.left = target.left;
                    } else {
                        parent.right = target.left;
                    }
                } else {
                    if (leftB){
                        parent.left = target.right;
                    } else {
                        parent.right = target.right;
                    }
                }
            }
        }

    }

    //删除最小节点
    public int delmix(SortTree node){
        SortTree temp = node;
        while (temp.left != null){
            temp = temp.left;
        }
        del(temp.data);
        return temp.data;
    }

    //中序遍历
    public void preOrder(){
        if (root == null){
            return;
        } else{
            root.preOrder();
        }
    }

}

class SortTree{
    int data;
    SortTree left;
    SortTree right;

    public SortTree(int data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "SortTree{" +
                "data=" + data +
                '}';
    }

    //添加
    public void add(SortTree node){
        if (this == null){
            return;
        }
        //节点值小于次节点
        if (this.data > node.data){
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

    //查找节点
    public SortTree Search(int data){
        if (this.data == data){
            return this;
        } else if (this.data > data){
            if (this.left == null){
                return null;
            }
            return this.left.Search(data);
        } else {
            if (this.right == null){
                return null;
            }
            return this.right.Search(data);
        }
    }

    //查找节点的父节点
    public SortTree SearchParent(int data){
        if ((this.left != null && this.left.data == data) || (this.right != null && this.right.data == data)){
            return this;
        } else {
            if (this.left != null && this.data > data){
                return this.left.SearchParent(data);
            } else if (this.right != null && this.data < data){
                return this.right.SearchParent(data);
            } else {
                return null;
            }
        }
    }


    //中序遍历
    public void preOrder(){
        if (this.left != null){
            this.left.preOrder();
        }
        System.out.println(this);
        if (this.right != null){
            this.right.preOrder();
        }
    }
}