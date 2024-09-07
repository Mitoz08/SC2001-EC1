package Programs;

import Programs.Sorting.BaseClass;
import Programs.Sorting.MergeInsertSort;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Scanner;

public class VaryBoth {
    /*
     Keeping Array size fixed and varying the size of the data
 */
    static final int DATABOUND = 1000;
    static final Scanner sc = new Scanner(System.in);

    // Private attributes
    private static int FileCount = 0;
    private static boolean SetUp;

    private static int AverageOf, OutputOption;
    private static boolean DistinctValues;
    private static int[] SValueInfo = new int[3];
    private static int[] ArrayInfo = new int[3];
    private static int noOfSortObjects;
    private static int noOfArraySize;
    private static long[][][] Results;


    public static void print() {
        System.out.println("Varying Array Size");
    }

    public static void setUp() {
        SetUp = true;
        System.out.println("Enter the parameters for VaryBoth test.");
        System.out.print("Distinct values (T/F): ");
        DistinctValues = sc.nextLine().charAt(0) == 'T';
        System.out.print("Average of: ");
        AverageOf = sc.nextInt();
        System.out.print("Start value of S: ");
        SValueInfo[0] = sc.nextInt();
        System.out.print("End value of S: ");
        SValueInfo[1] = sc.nextInt();
        System.out.print("Increment size of S: ");
        SValueInfo[2] = sc.nextInt();
        System.out.print("Start size of array: ");
        ArrayInfo[0] = sc.nextInt();
        System.out.print("End size of array: ");
        ArrayInfo[1] = sc.nextInt();
        System.out.print("Increment size of array: ");
        ArrayInfo[2] = sc.nextInt();
        noOfArraySize = (int) Math.ceil(((double)(ArrayInfo[1]-ArrayInfo[0]))/ArrayInfo[2]) + 1;
        noOfSortObjects = (int) Math.ceil(((double)(SValueInfo[1]-SValueInfo[0]))/SValueInfo[2]) + 1;

        Results = new long[noOfSortObjects][noOfArraySize][2];
    }

    public static void run() throws IOException {
        if (!SetUp) {
            setUp();
        }
        for (int S = 0; S < noOfSortObjects; S++) {
            int SValue = SValueInfo[0] + S*SValueInfo[2];
            MergeInsertSort object = new MergeInsertSort(AverageOf,SValue);
            for (int J = 0; J < noOfArraySize; J++){
                int ArraySize = ArrayInfo[0] + J*ArrayInfo[2];
                for (int Case = 0; Case < AverageOf; Case++){
                    int[] Data = DistinctValues? DistinctRandom.randomArray(ArraySize): NonDistinctRandom.randomArray(ArraySize,DATABOUND);
                    object.runTest(Data);
                }
                long totalKey = 0;
                long totalTime = 0;
                for (long number: object.getKeyCompare()) totalKey += number;
                for (long number: object.getTimeTaken()) totalTime += number;
                totalKey /= AverageOf;
                totalTime /= AverageOf;
                Results[S][J][0] = totalKey;
                Results[S][J][1] = totalTime;
                object.resetData();
            }
        }
        saveFile();
    }
    private static void saveFile() throws IOException {
        String FileName = "VaryBoth" + FileCount + ".csv";
        try {
            File csvOutputFile = new File(FileName);
            if (csvOutputFile.createNewFile()){
                System.out.println("File created.");
            } else {
                System.out.println("File already exist.");
            }
        } catch (IOException e) {
            System.out.println("Error creating file.");
            e.printStackTrace();
            return;
        }
        try {
            FileWriter csvWriterFile = new FileWriter(FileName);
            writeFile(csvWriterFile);
            csvWriterFile.close();
        } catch (IOException e) {
            System.out.println("Error writing file.");
            e.printStackTrace();
            return;
        }

        FileCount++;
    }

    private static void writeFile(FileWriter File) throws IOException {
        // Write heading
        File.write("SValue,ArraySize,KeyComparison,TimeTaken\n");

        // Write Data
        for (int i = 0; i < noOfSortObjects; i++){
            int SValue = SValueInfo[0] + i*SValueInfo[2];
            for (int j = 0; j < noOfArraySize; j++){
                int ArraySize = ArrayInfo[0] + j*ArrayInfo[2];
                File.write(SValue + "," + ArraySize + "," + Results[i][j][0] + "," + Results[i][j][1] + "\n");
            }
        }
    }

}
