package Programs.Sorting;

public abstract class BaseClass {
    protected long[] keyCompare;
    protected long[] timeTaken;
    protected int testCases;
    protected int testID;
    protected int maxCases;

    protected static int totalTest = 0;

    public int getS() {return 0;}
    public abstract int getTestCases();
    public abstract long getKeyAtT(int T);
    public abstract long getTimeAtT(int T);

    public long[] getKeyCompare() {
        return this.keyCompare;
    }
    public long[] getTimeTaken() {
        return this.timeTaken;
    }

    public abstract void resetData();
    public abstract void printDetails();
    public abstract void runTest(int[] array);
    public abstract void printAverage();
}
