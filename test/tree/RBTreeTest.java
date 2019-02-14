package tree;

import data.tree.RBTree;

import java.util.*;

public class RBTreeTest implements Runnable{

    @Override
    public void run() {
        int testCount = 100;
        for (int i = 0; i < testCount; i++) {
            List<Integer> list = makeRandomArray(10000);
            Set set = new HashSet(list);
            System.out.println("随机数组生成完毕"+" ==>"+Thread.currentThread().getName());
            try {
                rbtreeTest(list,set);
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
            System.out.println("测试次数："+i+" ==>"+Thread.currentThread().getName());
        }
    }


    public  void rbtreeTest(List<Integer> addNumbers, Set<Integer> remove_array) throws Exception {
        RBTree<Integer> tree = new RBTree<>();
        for (int num : addNumbers) {
            tree.add(num);
            tree.checkAllTree();
        }
//        tree.printSelf(2);
        int remove_count = 0;
        for (int num : remove_array) {
            tree.remove(num);
            remove_count++;
//            tree.printSelf(2);
//            System.out.println("删除的值是  ： " + num+"，索引是："+remove_count+" ==>"+Thread.currentThread().getName());
            tree.checkAllTree();
//            System.out.println("");
//            System.out.println("*****************************************换行**********************************");
        }
    }

    public  List<Integer> makeRandomArray(int length){
        Set tmpSet = null;
        if(length < 1){
            return null;
        }
        final int MAX_NUMBER = length*3;
        int rand = 0;
        ArrayList<Integer> list = new ArrayList<Integer>();
        tmpSet = new HashSet(list);
        int i=0;
        int setLength = tmpSet.size();
        for(i=0; i<length; i++) {
            while (true){
                rand = (int)(Math.random() * MAX_NUMBER);
                tmpSet.add(rand);
                if(setLength<tmpSet.size()){
                    setLength = tmpSet.size();
                    break;
                }
            }
            list.add(rand);
        }
        return list;
    }
}
