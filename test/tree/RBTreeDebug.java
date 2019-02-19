package tree;

import data.tree.RBTree;

import java.io.*;
import java.util.*;

public class RBTreeDebug {
    public static void main(String[] args) {
//        try {
//            test();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        int count = 5;
        for (int i = 0; i < count; i++) {
            Thread thread = new Thread(new RBTreeTest());
            thread.start();
        }
    }

    public static void test() throws Exception {
        String file = "filePath";
        String array=readToString(file);
        System.out.println("arrayString ===>");
        System.out.println(array);
        System.out.println(" ");
        String[] numArray = array.replace("[","").replace("]","").split(",");
        List list = new ArrayList(numArray.length);
        for (String s : numArray) {
            list.add(Integer.valueOf(s.trim()));
        }
        Set set = new HashSet(list);
        rbtreeTest(list,set);
    }


    public  Set tmpSet = null;
    public  List<Integer> makeRandomArray(int length){
        if(length < 1){
            return null;
        }
        final int MAX_NUMBER = length*3;
        int rand = 0;
        ArrayList <Integer> list = new ArrayList<Integer>();
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

    public static void rbtreeTest(List<Integer> addNumbers, Set<Integer> remove_array) throws Exception {
        RBTree<Integer> tree = new RBTree<>();
        TreeMap treeMap = new TreeMap();
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
            System.out.println("删除的值是  ： " + num+"，索引是："+remove_count);
            if(remove_count == 10){
                System.out.println("debugger!");
            }
            tree.checkAllTree();
//            System.out.println("");
//            System.out.println("*****************************************换行**********************************");
        }
    }

    public static String readToString(String fileName) {
        String encoding = "UTF-8";
        File file = new File(fileName);
        Long filelength = file.length();
        byte[] filecontent = new byte[filelength.intValue()];
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(filecontent);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return new String(filecontent, encoding);
        } catch (UnsupportedEncodingException e) {
            System.err.println("The OS does not support " + encoding);
            e.printStackTrace();
            return null;
        }
    }





}
