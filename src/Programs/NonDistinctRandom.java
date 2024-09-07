package Programs;

public class NonDistinctRandom {
    public static int[] randomArray(int size,int bound) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++){
            array[i] = (int) Math.round((Math.random() * bound));
        }
        return array;
    }

    public static int[] randomArray(int size,int lowerBound,int upperBound) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++){
            array[i] = (int) Math.round(Math.random()*(upperBound-lowerBound));

        }
        return array;
    }
}
