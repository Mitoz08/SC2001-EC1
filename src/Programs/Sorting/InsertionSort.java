package Programs.Sorting;

public class InsertionSort extends BaseClass{

    // Constructors

    public InsertionSort(){
        this.testID = totalTest;
        this.testCases = 0;
        this.keyCompare = new long[50];
        this.timeTaken = new long[50];
        totalTest++;
    }

    public InsertionSort(int maxCases){
        this.testID = totalTest;
        this.testCases = 0;
        this.keyCompare = new long[maxCases];
        this.timeTaken = new long[maxCases];
        totalTest++;
    }

    // Methods

    public int getTestCases() {return this.testCases;}
    public long getKeyAtT(int T){
        return this.keyCompare[T];
    }
    public long getTimeAtT(int T){
        return this.timeTaken[T];
    }

    public void printDetails() {
        System.out.println("Insertion Sort");
        System.out.println("testID: " + this.testID + " testCases: " + this.testCases);
        for (int i = 0; i < this.testCases; i++) {
            System.out.println("ISort Case " + i + ": KeyCompare-" + this.keyCompare[i]+ " timeTaken-" + this.timeTaken[i]);
        }
    }

    public void resetData(){
        testCases = 0;
        this.keyCompare = new long[maxCases];
        this.timeTaken = new long[maxCases];
    }

    public void runTest(int[] array) {
        long startTime = System.currentTimeMillis();

        int[] answer = insertionSort(array);
        for (int i = 0; i < answer.length-1;i++) {
            if (answer[i] > answer[i+1]) {
                System.out.println("Fail");
                return;
            }
        }
        //System.out.println(Arrays.toString(answer));
        this.timeTaken[this.testCases] = System.currentTimeMillis() - startTime;
        //System.out.println(System.currentTimeMillis() - startTime);
        System.out.println("ISort Case " + this.testCases + ": KeyCompare-" + this.keyCompare[this.testCases]+ " timeTaken-" + this.timeTaken[this.testCases]);
        this.testCases++;
    }

    public void printAverage() {
        double keyTotal = 0, timeTotal = 0;
        for (int i = 0; i < this.testCases; i++){
            keyTotal += this.keyCompare[i];
            timeTotal += this.timeTaken[i];
        }
        System.out.println("------------------------------");
        System.out.println("InsertionSort ID: " + this.testID);
        System.out.println("Average Comparison: " + (long)(keyTotal/this.testCases));
        System.out.println("Average Time Taken: " + (long)(timeTotal/this.testCases));
        System.out.println("------------------------------");
    }

    private int[] insertionSort(int[] array) {
        for (int i = 1; i < array.length;i++){
            for (int j = i; j > 0; j--) {
                this.keyCompare[this.testCases]++;
                if (array[j] > array[j-1]) break;
                int temp = array[j];
                array[j] = array[j-1];
                array[j-1] = temp;
            }
        }
        return array;
    }

}
