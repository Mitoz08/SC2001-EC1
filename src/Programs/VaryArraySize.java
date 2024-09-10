package Programs;

import Programs.Sorting.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class VaryArraySize  {

/*
    Keeping S fixed and varying the size of the data
*/
    static final int DATABOUND = 1000;
    static final Scanner sc = new Scanner(System.in);

    // Private attributes
    private static int FileCount = 0;
    private static boolean SetUp;

    private static int AverageOf, OutputOption;
    private static int[] ArrayInfo = new int[3];
    private static boolean DistinctValues;
    private static int noOfSortObjects;
    private static BaseClass[] SortObjects;
    private static long[][] Results;


    public static void print() {
        System.out.println("Varying Array Size");
    }

    public static void setUp() {
        SetUp = true;
        System.out.println("Enter the parameters for VaryArraySize test.");
        System.out.print("Distinct values (T/F): ");
        DistinctValues = sc.nextLine().charAt(0) == 'T';
        System.out.print("Average of: ");
        AverageOf = sc.nextInt();
        System.out.print("Data output 0(KeyValue),1(TimeTaken),2(Both): ");
        OutputOption = sc.nextInt();
        System.out.print("Start size of array: ");
        ArrayInfo[0] = sc.nextInt();
        System.out.print("End size of array: ");
        ArrayInfo[1] = sc.nextInt();
        System.out.print("Increment size of array: ");
        ArrayInfo[2] = sc.nextInt();
        System.out.print("Number of Sorting programs: ");
        noOfSortObjects = sc.nextInt();
        SortObjects = new BaseClass[noOfSortObjects];
        int MaxCases = AverageOf;
        for (int i = 0; i < noOfSortObjects; i++) {
            System.out.print("Enter S Value: ");
            int j = sc.nextInt();
            SortObjects[i] = new MergeInsertSort(MaxCases,j);
        }

        int Size = (int) Math.ceil(((double)(ArrayInfo[1]-ArrayInfo[0]))/ArrayInfo[2]) + 1;
        if (OutputOption == 2) {
            Results = new long[Size][1 + noOfSortObjects*2];
        } else Results = new long[Size][1 + noOfSortObjects];

    }

    public static void run() throws IOException {
        if (!SetUp) {
            setUp();
        }
        for (BaseClass object: SortObjects) {
            object.resetData();
        }
        for (int j = 0; j < Results.length; j++) {
            // If J is 0 put start ArraySize
            // If start ArraySize is smaller than increment, the next ArraySize is just increment
            // To eliminate the issues of stopping at 9951000 when bound is 1k - 10mil and increments of 50k
            int Size = j == 0? ArrayInfo[0] :
                    ArrayInfo[0] < ArrayInfo[2]? j*ArrayInfo[2] : ArrayInfo[0] + j*ArrayInfo[2];
            Results[j][0] = Size;
            System.out.println(Size);
            for (BaseClass object: SortObjects) {
                object.resetData();
            }
            for (int i = 0; i < AverageOf; i++){
                int[] Data = DistinctValues? DistinctRandom.randomArray(Size): NonDistinctRandom.randomArray(Size,DATABOUND);
                for (BaseClass o: SortObjects) {
                    o.runTest(Data);
                }
            }
            for (int i = 0; i < noOfSortObjects; i++) {
                long totalKey = 0;
                long totalTime = 0;
                for (long number: SortObjects[i].getKeyCompare()) totalKey += number;
                for (long number: SortObjects[i].getTimeTaken()) totalTime += number;
                totalKey /= AverageOf;
                totalTime /= AverageOf;
                if (OutputOption == 2) {
                    Results[j][1+i*2] = totalKey;
                    Results[j][2+i*2] = totalTime;
                } else if (OutputOption == 1) {
                    Results[j][1+i] = totalTime;
                } else {
                    Results[j][1+i] = totalKey;
                }
            }
        }
        saveFile();
    }
    private static void saveFile() throws IOException {
        String FileName = "VaryArraySize" + FileCount + ".csv";
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
        File.write("ArraySize");
        for (int i = 0; i < noOfSortObjects; i++){
            if (OutputOption == 2) {
                File.write(",KeyCompare" + i + ",TimeTaken" + i);
            } else if (OutputOption == 1) {
                File.write(",TimeTaken" + i);
            } else {
                File.write(",KeyCompare" + i);
            }
        }
        File.write("\n");

        // Write Data
        for (long[] list: Results){
            for (int i = 0; i <list.length; i++) {
                File.write(Long.toString(list[i]));
                if (i == list.length-1) break;
                File.write(",");
            }
            File.write("\n");
        }

    }

}
