package com.data.tree;

import java.util.*;

/**
 * 赫夫曼编码解码
 */
public class HuffmanCodeDemo {
    public static void main(String[] args) {
        String content = "i like like like java do you like a java";
        byte[] contentBytes = content.getBytes();
        System.out.println(contentBytes.length); //40

        byte[] huffmanCodesBytes = zipHuffman(contentBytes);
        System.out.println("压缩后的结果是:" + Arrays.toString(huffmanCodesBytes) + " 长度= " + huffmanCodesBytes.length);


        //测试一把byteToBitString方法
        //System.out.println(byteToBitString((byte)1));
        // byte[] sourceBytes = decode(huffmanCodes, huffmanCodesBytes);
        //
        // System.out.println("原来的字符串=" + new String(sourceBytes));
         // "i like like like java do you like a java"
        byte[] sourceBytes = upzipHuffman(huffmanCodesBytes);
        System.out.println("原来的字符串=" + new String(sourceBytes));
    }


    /**
     * 将数据对应的Byte[]数组转化为Node节点
     */
    public static List<Node1> getNodes(byte[] b){
        //返回对象
        List<Node1> list = new ArrayList<Node1>();
        //统计出现次数
        Map<Byte, Integer> counts = new HashMap();
        for (Byte s : b){
            Integer count = counts.get(s);
            if (count == null){
                counts.put(s, 1);
            } else {
                counts.put(s, count + 1);
            }
        }
        //为每个字符创建一个节点
        for (Map.Entry<Byte, Integer> map : counts.entrySet()){
            list.add(new Node1(map.getKey(), map.getValue()));
        }
        return list;
    }

    /**
     * 通过List节点创建赫夫曼树
     */
    public static Node1 crateHuffmanTree(List<Node1> list){
        while (list.size() > 1){
            Collections.sort(list);
            Node1 left = list.get(0);
            Node1 right = list.get(1);
            Node1 parent = new Node1(null, left.weight + right.weight);
            parent.left = left;
            parent.right = right;
            list.add(parent);
            list.remove(left);
            list.remove(right);
        }
        return list.get(0);
    }

    /**
     * 生成赫夫曼树对应的赫夫曼编码
     * 1.将赫夫曼编码表存放在 Map<Byte,String>
     * 2.生成的赫夫曼编码表32=01, 97=100, 100=11000, 117=11001, 101=1110, 118=11011, 105=101, 121=11010, 106=0010
     * root 节点
     * code  路径类型   左子节点：0   右子节点：1
     * s     拼接路径
     */
    static Map<Byte, String> huffmanCodes = new HashMap<>();//存放赫夫曼编码
    static StringBuilder s = new StringBuilder();//用于拼接编码
    public static void getCodes(Node1 root, String code, StringBuilder s){
        StringBuilder s1 = new StringBuilder(s);
        s1.append(code);
        //判断是否叶子节点
        if (root.data == null){
            //向左递归
            getCodes(root.left, "0", s1);
            //向右递归
            getCodes(root.right, "1", s1);
        } else {
            huffmanCodes.put(root.data, s1.toString());
        }
    }
    //方便调用，重写方法
    public static Map<Byte, String> getCodes(Node1 root){
        if(root == null) {
            return null;
        }
        //处理root的左子树
        getCodes(root.left, "0", s);
        //处理root的右子树
        getCodes(root.right, "1", s);
        return huffmanCodes;
    }

    /**
     * 将Byte[]原始数据，通过赫夫曼编码表，压缩，并转为10进制，Byte[]保存
     * @param b        原始数据
     * @param huffmancode       赫夫曼编码
     * @return          10进制的byte数组
     */
    public static byte[] zip(byte[] b ,Map<Byte, String> huffmancode){
        StringBuilder s = new StringBuilder();//储存转化后的赫夫曼编码
        for (Byte b1 : b){
            s.append(huffmancode.get(b1));
        }
        System.out.println("赫夫曼编码为: " + s);
        //将编码转化为10进制
        int len = s.length();
        if (len % 8 == 0){
            len = len / 8;
        }else {
            len = len / 8 + 1;
        }
        byte[] bytes = new byte[len];
        int count = 0;//计数
        for (int i = 0; i < s.length(); i += 8){
            String str;
            if (i + 8 > s.length()){//不够8位
                str = s.substring(i);
            } else {
                str = s.substring(i, i+8);
            }
            bytes[count] = (byte) Integer.parseInt(str , 2);
            count++;
        }
        return bytes;
    }

    /**
     * 封装方法
     * @param bytes    原始数组
     * @return  压缩后的
     */
    public static byte[] zipHuffman(byte[] bytes){
        List<Node1> node = getNodes(bytes);//1.遍历数据，获取到，相同字符出现的次数，以次数做权值，创建节点
        Node1 root = crateHuffmanTree(node);//2.通过节点，创建赫夫曼树
        Map<Byte, String> map = getCodes(root);//3.通过赫夫曼树，获取赫夫曼编码(左0，右1，路径为，该字符的编码)
        byte[] b = zip(bytes, map);//4.通过赫夫曼编码，将原始数据，转化为以byte类型储存
        return b;
    }

    /**
     * 解码
     * @param huffmanBytes          压缩后的
     * @return
     */
    public static byte[] upzipHuffman(byte[] huffmanBytes){
        //1. 先得到 huffmanBytes 对应的 二进制的字符串 ， 形式 1010100010111...
        StringBuilder stringBuilder = new StringBuilder();
        //将byte数组转成二进制的字符串
        for(int i = 0; i < huffmanBytes.length; i++) {
            byte b = huffmanBytes[i];
            //判断是不是最后一个字节
            boolean flag = (i == huffmanBytes.length - 1);
            stringBuilder.append(byteToBitString(!flag, b));
        }
        //将赫夫曼编码反转，key为编码，value为字符
        Map<String, Byte> map = new HashMap<>();
        for (Map.Entry<Byte, String> m : huffmanCodes.entrySet()){
            map.put(m.getValue(), m.getKey());
        }
        //循环二进制字符串
        List<Byte> list = new ArrayList<>();//用于保存字符
        for (int i = 0; i < stringBuilder.length();){
            Byte b = null;
            boolean flag = true;
            int count = 1;//计数
            while (flag){
                String s = stringBuilder.substring(i, i + count);
                b = map.get(s);
                if (b == null){
                    count++;
                } else {
                    flag = false;
                }
            }
            list.add(b);
            i += count;
        }
        //list转化为byte数组类型返回
        byte[] b = new byte[list.size()];
        for (int i = 0; i < b.length; i++){
            b[i] = list.get(i);
        }
        return b;
    }

    /**
     * 将一个byte 转成一个二进制的字符串,
     * @param b 传入的 byte
     * @param flag 标志是否需要补高位如果是true ，表示需要补高位，如果是false表示不补, 如果是最后一个字节，无需补高位
     * @return 是该b 对应的二进制的字符串，（注意是按补码返回）
     */
    private static String byteToBitString(boolean flag, byte b) {
        //使用变量保存 b
        int temp = b; //将 b 转成 int
        //最后一个字节，无需补高位
        if(flag) {
            //如果是正数我们还存在补高位
            temp |= 256; //按位与 256  1 0000 0000  | 0000 0001 => 1 0000 0001
        }
        String str = Integer.toBinaryString(temp); //返回的是temp对应的二进制的补码
        if(flag) {
            //返回后8位
            return str.substring(str.length() - 8);
        } else {
            return str;
        }
    }

}

class Node1 implements Comparable<Node1>{
    Byte data;//存储数据
    int weight;//权值
    Node1 left;
    Node1 right;

    //中序遍历
    public void preOrder(){
        System.out.println(this);
        if (this.left != null){
            this.left.preOrder();
        }
        if (this.right != null){
            this.right.preOrder();
        }
    }


    public Node1(Byte data, int weight) {
        this.data = data;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Node1{" +
                "weight=" + weight +
                ", data=" + data +
                '}';
    }

    @Override
    public int compareTo(Node1 o) {
        return this.weight - o.weight;
    }
}
