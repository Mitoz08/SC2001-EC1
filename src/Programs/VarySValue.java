package Programs;

import Programs.Sorting.BaseClass;
import Programs.Sorting.MergeInsertSort;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class VarySValue {
    /*
        Keeping Array size fixed and varying the size of the data
    */
    static final int DATABOUND = 1000;
    static final Scanner sc = new Scanner(System.in);

    // Private attributes
    private static int FileCount = 0;
    private static boolean SetUp;

    private static int AverageOf, OutputOption;
    private static int[] SValueInfo = new int[3];
    private static boolean DistinctValues;
    private static int noOfSortObjects;
    private static int[] ArraySizes;
    private static int noOfArraySize;
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
        System.out.print("Start value of S: ");
        SValueInfo[0] = sc.nextInt();
        System.out.print("End value of S: ");
        SValueInfo[1] = sc.nextInt();
        System.out.print("Increment size of S: ");
        SValueInfo[2] = sc.nextInt();

        noOfSortObjects = (int) Math.ceil(((double)(SValueInfo[1]-SValueInfo[0]))/SValueInfo[2]) + 1;
        System.out.println(noOfSortObjects);
        SortObjects = new BaseClass[noOfSortObjects];
        for (int i = 0; i < noOfSortObjects; i++) {
            int j = SValueInfo[0] + i*SValueInfo[2];
            SortObjects[i] = new MergeInsertSort(AverageOf,j);
        }

        System.out.print("Number of Array Sizes: ");
        noOfArraySize = sc.nextInt();
        ArraySizes = new int[noOfArraySize];
        for (int i = 0; i< noOfArraySize; i++){
            System.out.print("Enter Size: ");
            ArraySizes[i] = sc.nextInt();
        }

        Results = OutputOption == 2? new long[noOfSortObjects][1+noOfArraySize*2]:new long[noOfSortObjects][1+noOfArraySize];
    }

    public static void run() throws IOException {
        if (!SetUp) {
            setUp();
        }
        for (BaseClass object: SortObjects) {
            object.resetData();
        }
//        int k = 0;
//        for (BaseClass object: SortObjects) {
//            Results[k][0] = object.getS();
//            System.out.println(Results[k][0]);
//            for (int i = 0; i <noOfArraySize; i++){
//                for (int j = 0; j < AverageOf; j++) {
//                    int[] Data = DistinctValues? DistinctRandom.randomArray(ArraySizes[i]): NonDistinctRandom.randomArray(ArraySizes[i],DATABOUND);
//                    object.runTest(Data);
//                }
//                long totalKey = 0;
//                long totalTime = 0;
//                for (long number: object.getKeyCompare()) totalKey += number;
//                for (long number: object.getTimeTaken()) totalTime += number;
//                totalKey /= AverageOf;
//                totalTime /= AverageOf;
//                if (OutputOption == 2) {
//                    Results[k][1+i*2] = totalKey;
//                    Results[k][2+i*2] = totalTime;
//                } else if (OutputOption == 1) {
//                    Results[k][1+i] = totalTime;
//                } else {
//                    Results[k][1+i] = totalKey;
//                }
//                object.resetData();
//            }
//            k++;
//        }
        for (int i = 0; i <noOfArraySize; i++){
            System.out.println(i);
            for (int j = 0; j < AverageOf; j++) {
                System.out.print(j);
                int[] Data = DistinctValues? DistinctRandom.randomArray(ArraySizes[i]): NonDistinctRandom.randomArray(ArraySizes[i],DATABOUND);
                for (BaseClass object: SortObjects) {
                    object.runTest(Data);
                }
            }
            System.out.println();
            int k = 0;
            for (BaseClass object: SortObjects) {
                long totalKey = 0;
                long totalTime = 0;
                for (long number: object.getKeyCompare()) totalKey += number;
                for (long number: object.getTimeTaken()) totalTime += number;
                Results[k][0] = object.getS();
                totalKey /= AverageOf;
                totalTime /= AverageOf;
                if (OutputOption == 2) {
                    Results[k][1+i*2] = totalKey;
                    Results[k][2+i*2] = totalTime;
                } else if (OutputOption == 1) {
                    Results[k][1+i] = totalTime;
                } else {
                    Results[k][1+i] = totalKey;
                }
                object.resetData();
                k++;
            }
        }
        saveFile();
    }
    private static void saveFile() throws IOException {
        String FileName = "VarySValue" + FileCount + ".csv";
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
        for (int i = 0; i < noOfArraySize; i++){
            if (OutputOption == 2) {
                File.write(",KeyOfSize" + ArraySizes[i] + ",TimeOfSize" + ArraySizes[i]);
            } else if (OutputOption == 1) {
                File.write(",TimeOfSize" + ArraySizes[i]);
            } else {
                File.write(",KeyOfSize" + ArraySizes[i]);
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
