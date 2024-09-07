package Programs.Sorting;

import java.util.Arrays;

public class MergeInsertSort extends BaseClass{

    private int N;

    public void setN (int N){
        this.N = N;
    }

    // Constructors

    public MergeInsertSort(int N) {
        this.testID = totalTest;
        this.testCases = 0;
        this.maxCases = 50;
        this.keyCompare = new long[50];
        this.timeTaken = new long[50];
        this.N = N;
        totalTest++;
    }

    public MergeInsertSort(int maxCases, int N) {
        this.testID = totalTest;
        this.testCases = 0;
        this.maxCases = maxCases;
        this.keyCompare = new long[maxCases];
        this.timeTaken = new long[maxCases];
        this.N = N;
        totalTest++;
    }

    // Methods
    public int getN() {return this.N;}
    public int getTestCases() {return this.testCases;}
    public long getKeyAtT(int T){
        return this.keyCompare[T];
    }
    public long getTimeAtT(int T){
        return this.timeTaken[T];
    }

    public void printDetails() {
        System.out.println("Merge-Insertion Sort");
        System.out.println("testID: " + this.testID + " testCases: " + this.testCases);
        for (int i = 0; i < this.testCases; i++) {
            System.out.println("MISort Case " + i + ": KeyCompare-" + this.keyCompare[i] + " timeTaken-" + (this.timeTaken[i]/1000000));
        }
    }

    public void resetData(){
        testCases = 0;
        this.keyCompare = new long[maxCases];
        this.timeTaken = new long[maxCases];
    }

    public void runTest(int[] array) {
        //long startTime = System.currentTimeMillis();
        long startTime = System.nanoTime();
        int[] answer = mergeInsertSort(array,0,array.length);
        this.timeTaken[this.testCases] = (System.nanoTime() - startTime)/ 1000000;

//        for (int i = 0; i < answer.length-1;i++) {
//            if (answer[i] > answer[i+1]) {
//                System.out.println("Fail");
//                return;
//            }
//        }
        //System.out.println(Arrays.toString(answer));
        //System.out.println(System.currentTimeMillis() - startTime);
        //System.out.println("MISort Case " + this.testCases + ": KeyCompare-" + this.keyCompare[this.testCases]+ " timeTaken-" + this.timeTaken[this.testCases]/1000000);
        //System.out.println(array.length + "," + this.timeTaken[this.testCases]/1000000 );
        this.testCases++;
    }

    public void printAverage() {
        double keyTotal = 0, timeTotal = 0;
        for (int i = 0; i < this.testCases; i++){
            keyTotal += this.keyCompare[i];
            timeTotal += this.timeTaken[i];
        }
        System.out.println("------------------------------");
        System.out.println("MergeInsertSort ID: " + this.testID);
        System.out.println("Average Comparison: " + (long)keyTotal/this.testCases);
        System.out.println("Average Time Taken: " + (long)timeTotal/this.testCases);
        System.out.println("------------------------------");
    }


    private int[] mergeInsertSort(int[] array, int start, int end) {
        int returnSize = end-start;
        int midPoint = start + returnSize/2;

        // Insertion Sort
        if (returnSize <= this.N) {
            int[] returnArray = Arrays.copyOfRange(array,start,end);
            for (int i = 1; i < returnArray.length;i++){
                for (int j = i; j > 0; j--) {
                    this.keyCompare[this.testCases]++;
                    if (returnArray[j] > returnArray[j-1]) break;
                    int temp = returnArray[j];
                    returnArray[j] = returnArray[j-1];
                    returnArray[j-1] = temp;
                }
            }
            return returnArray;
        }

        // Merge Sort
        // Base Case
        if (returnSize == 1) return new int[] {array[start]} ;

        // Divide and Conquer
        int[] leftRecursion = mergeInsertSort(array,start,midPoint);
        int[] rightRecursion = mergeInsertSort(array,midPoint,end);
        int[] returnArray = new int[returnSize];

        // Merging
        int left = 0, right = 0, index = 0;
        while (left < leftRecursion.length && right<rightRecursion.length) {
            if (leftRecursion[left] == rightRecursion[right]) {
                returnArray[index++] = leftRecursion[left++];
                returnArray[index++] = rightRecursion[right++];
                this.keyCompare[this.testCases]++; // Considered 1 Key comparison?
            }
            else if (leftRecursion[left] < rightRecursion[right]) {
                returnArray[index++] = leftRecursion[left++];
                this.keyCompare[this.testCases]++; // Incrementing the key compare count
            } else {
                returnArray[index++] = rightRecursion[right++];
                this.keyCompare[this.testCases]++; // Incrementing the key compare count
            }
        }
        // Merging when 1 array is emptied
        if (right < rightRecursion.length){
            while (right < rightRecursion.length){
                returnArray[index] = rightRecursion[right];
                index++;
                right++;
            }
        } else {
            while (left < leftRecursion.length){
                returnArray[index] = leftRecursion[left];
                index++;
                left++;
            }
        }
        return returnArray;
    }

}


