package data.graph;

import data.LinkedList;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Alex
 * @since 18-12-28
 * @desc 有向图
 */
public class DirectedGraph {

    Vertex[] vertexArray;
    //根据hashcode进而查询数组索引，省得遍历
    Map<Integer,Integer> map =new HashMap<>();

    /**
     * 采用邻接表的方式表示
     */
    class Vertex{

        Object value;
        //链表,表示边
        LinkedList<Vertex> edgeLinkedList;

        public Vertex(){

        }
        public Vertex(Object object){
            this.value = object;
        }

    }



    public DirectedGraph(Object[] c) {
        this.vertexArray = new Vertex[c.length];
        for (int idx = 0; idx < c.length; idx++) {
            vertexArray[idx] = new Vertex(c[idx]);
            map.put(c[idx].hashCode(),idx);
        }
    }

    public DirectedGraph addEdge(Object from, Object to){
        Integer idx = map.get(from.hashCode());
        Integer idx_to = map.get(to.hashCode());
        if(idx == null || idx_to == null){
            throw new RuntimeException("object is not in this graph!");
        }
        vertexArray[idx].edgeLinkedList.add(vertexArray[idx_to]);
        return this;
    }


}
