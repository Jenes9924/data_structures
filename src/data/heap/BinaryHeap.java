package data.heap;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author alex
 * @Description TODO 二叉堆
 * @Date 19-2-3  下午4:45
 * @Version 1.0
 **/
public class BinaryHeap<T> {

    private boolean isBig;
    private ArrayList array;



    /**
     *
     * @param isBig 判断是最大堆还是最小堆
     */
    public BinaryHeap(boolean isBig){
        this.isBig = isBig;
        this.array = new ArrayList();
    }
    public BinaryHeap(boolean isBig,int capactiy){
        this.isBig = isBig;
        this.array = new ArrayList(capactiy);
    }

    public List getArray(){
        return this.array;
    }
    public void add(T value){
        int idx = array.size();
        this.array.add(idx,value);
        //检查堆的性质
        checkHeapProperty(idx);
    }

    public void checkHeapProperty(int index){
        check(index);
    }

    private void check(int idx){
        if(idx == 0){
            return;
        }
        int f_idx = idx%2 ==0?idx/2 -1:(idx-1)/2;

        if ( ((array.get(f_idx).hashCode() > array.get(idx).hashCode()) ^ isBig )){
            //最大堆
                Object tmp = array.get(f_idx);
                array.set(f_idx,array.get(idx));
                array.set(idx,tmp);
                check(f_idx);
        }


    }


    public static void main(String[] args) {
//        double a = Math.log(17)/Math.log(2);
//        Integer b = Integer.valueOf(String.valueOf(a).split("\\.")[0]);
//        int depth = b-1;
//        System.out.println("b=="+b);
//        double res = Math.pow(2,b);
//        System.out.println("res ==>"+res);
        test(32);
        System.out.println(true ^false);
    }

    public static int test(int a){
        double a1= Math.log(a)/ Math.log(2);
        Integer b = Integer.valueOf(String.valueOf(a1).split("\\.")[0]);
        int depth = 0;
        System.out.println("b=="+b);
        double res = Math.pow(2,b);
        int diff = a - (int) res;
        System.out.println("diff ===>>"+diff);
        int all = (int) res*2-1;
        int c = all -a ;
        int f_idx = a%2 == 0?a/2-1:(a-1)/2;
        System.out.println("f_idx==>"+f_idx);


        return 0;
    }

    public static int calc(int a){
        int res = 0;
        for (int i = 0; i < a; i++) {
            res = (int) Math.pow(2,i)+i;
        }
        return res;
    }

}
