package com.data.graph;

import java.util.*;

/**
 * 图（邻结矩阵）
 */
public class Graph {
    public static void main(String[] args) {
        //测试一把图是否创建ok
        int n = 8;  //结点的个数
        //String Vertexs[] = {"A", "B", "C", "D", "E"};
        String Vertexs[] = {"1", "2", "3", "4", "5", "6", "7", "8"};

        //创建图对象
        Graph graph = new Graph(n);
        //循环的添加顶点
        for(String vertex: Vertexs) {
            graph.addVertex(vertex);
        }

        //添加边
        //A-B A-C B-C B-D B-E
//		graph.insertEdge(0, 1, 1); // A-B
//		graph.insertEdge(0, 2, 1); //
//		graph.insertEdge(1, 2, 1); //
//		graph.insertEdge(1, 3, 1); //
//		graph.insertEdge(1, 4, 1); //

        //更新边的关系
        graph.addEdge(0, 1, 1);
        graph.addEdge(0, 2, 1);
        graph.addEdge(1, 3, 1);
        graph.addEdge(1, 4, 1);
        graph.addEdge(3, 7, 1);
        graph.addEdge(4, 7, 1);
        graph.addEdge(2, 5, 1);
        graph.addEdge(2, 6, 1);
        graph.addEdge(5, 6, 1);



        //显示一把邻结矩阵
        graph.showGraph();

        //测试一把，我们的dfs遍历是否ok
        System.out.println("深度遍历");
        graph.dfs(); // A->B->C->D->E [1->2->4->8->5->3->6->7]
		System.out.println();
        System.out.println("广度优先!");
        graph.bfs(); // A->B->C->D-E [1->2->3->4->5->6->7->8]

    }


    private List<String> vertexList;//顶点集合
    private int numOfEdges;//总边数
    private int[][] edges;//边对应的矩阵
    private boolean[] isVisited;//是否被访问

    Graph(int n) {
        vertexList = new ArrayList<String>();
        edges = new int[n][n];
        numOfEdges = 0;
    }

    //添加顶点
    public void addVertex(String vertex){
        vertexList.add(vertex);
    }

    /**
     * 添加边
     * @param v1    顶点1
     * @param v2    顶点2
     * @param weight    权值(1为有连接,0为无连接)
     */
    public void addEdge(int v1, int v2, int weight){
        edges[v1][v2] = weight;
        edges[v2][v1] = weight;//双向
        numOfEdges++;
    }
    //显示邻结矩阵
    public void showGraph(){
        for (int[] i : edges){
            System.out.println(Arrays.toString(i));
        }
    }
    //获取顶点
    public String getVertex(int i) {
        return vertexList.get(i);
    }
    //返回对应顶点是否被访问
    public boolean isVisited(int i) {
        return isVisited[i];
    }
    //获取第一个节点
    private int getFirstVertex(int i) {
        for (int j = 0; j < vertexList.size(); j++){
            if (edges[i][j] > 0){
                return j;
            }
        }
        return -1;
    }

    private int getNextVertex(int v1, int v2) {
        for (int j = v2 + 1; j < vertexList.size(); j++){
            if (edges[v1][j] > 0){
                return j;
            }
        }
        return -1;
    }

    /**
     * 深度优化遍历(从每个顶点的第一个邻接结点访问)
     * @param isVisited     是否被访问
     * @param i             顶点索引
     */
    public void dfs(boolean[] isVisited, int i){
        System.out.print(getVertex(i) + "-->");
        isVisited[i] = true;//顶点已被访问
        int w = getFirstVertex(i);//获取此顶点的第一个邻接结点
        while (w != -1){//有
            if (!isVisited[w]){//邻接结点没有被访问
                dfs(isVisited, w);
            }
            w = getNextVertex(i, w);//被访问获取下一个邻接结点
        }
    }
    //重载
    public void dfs(){
        isVisited = new boolean[vertexList.size()];
        for (int j = 0; j < vertexList.size(); j++){
            if (!isVisited[j]){
                dfs(isVisited, j);
            }
        }
    }
    /**
     * 广度优化遍历(先把一个顶点的邻接结点访问完毕，在从第一个邻接结点开始访问)
     * @param isVisited     顶点是否被访问
     * @param i             顶点索引
     */
    public void bfs(boolean[] isVisited, int i){
        int U;//头节点下标
        int W;//邻接结点下标
        LinkedList queue = new LinkedList();
        System.out.print(getVertex(i) + "-->");
        isVisited[i] = true;
        queue.add(i);
        while (!queue.isEmpty()){
            U = (Integer) queue.removeFirst();
            W = getFirstVertex(U);
            if (!isVisited[W]){
                System.out.print(getVertex(W) + "-->");
                isVisited[W] = true;
                queue.add(W);
            }
            W = getNextVertex(U, W);//体现广度优化遍历
        }
    }
    //重载
    public void bfs(){
        isVisited = new boolean[vertexList.size()];
        for (int j = 0; j < vertexList.size(); j++){
            if (!isVisited[j]){
                bfs(isVisited, j);
            }
        }
    }
}
