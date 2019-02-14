package utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Alex
 * @since 19-2-14
 */
public class Utils {


    public static List<Integer> makeRandomArray(int length){
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
