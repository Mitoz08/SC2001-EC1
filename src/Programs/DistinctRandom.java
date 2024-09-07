package Programs;

import java.util.*;

public class DistinctRandom {
    public static int[] randomArray(int size){
        List<Integer> tempArray = new ArrayList<>();
        for (int i = 0; i < size; i++){
            tempArray.add(i);
        }
        Collections.shuffle(tempArray);
        int[] array = new int[size];
        for (int i = 0; i < size; i++){
            array[i] = tempArray.get(i);
        }
        return array;
    }

    public static int[] randomArray(int size, int start){
        List<Integer> tempArray = new ArrayList<>();
        for (int i = start; i < size+start; i++){
            tempArray.add(i);
        }
        Collections.shuffle(tempArray);
        int[] array = new int[size];
        for (int i = start; i < size+start; i++){
            array[i] = tempArray.get(i);
        }
        return array;
    }
}
