package com.mechadragonx.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class Main
{
    private static final int[] POWERS_OF_10 = { 1, 10, 100, 1000, 10000, 100000 };

    public static void main(String[] args) throws IOException
    {
        test();
    }
    private static void test() throws IOException
    {
        String inPath = "./data/random.txt";
        String outPath = "./data/output.txt";

        long one = execute(inPath, outPath);
        long two = execute(inPath, outPath);
        long three = execute(inPath, outPath);
        long four = execute(inPath, outPath);
        long five = execute(inPath, outPath);
        long six = execute(inPath, outPath);
        long seven = execute(inPath, outPath);
        long eight = execute(inPath, outPath);
        long nine = execute(inPath, outPath);
        long ten = execute(inPath, outPath);
        long oneone = execute(inPath, outPath);
        long onetwo = execute(inPath, outPath);
        long onethree = execute(inPath, outPath);
        long onefour = execute(inPath, outPath);
        long onefive = execute(inPath, outPath);
        long onesix = execute(inPath, outPath);
        long oneseven = execute(inPath, outPath);
        long oneeight = execute(inPath, outPath);
        long onenine = execute(inPath, outPath);
        long twenty = execute(inPath, outPath);

        long average = (one + two + three + four + five + six + seven + eight + nine + ten + oneone + onetwo + onethree + onefour + onefive + onesix + oneseven + oneeight + onenine + twenty) / 20;
        System.out.println("Average Time: " + average + " ms");
    }
    private static long execute(String inPath, String outPath) throws IOException
    {
        File out = new File(outPath);
        if(out.exists())
        {
            if(out.delete()) System.out.println("The output file was deleted!");
            else
            {
                System.out.println("The output file wan't deleted somehow!\nABORT!!");
                return -1;
            }
        }

        // Read data as bytes
        long start = System.currentTimeMillis();
        int[] input = read(inPath, 10000000);
        long end = System.currentTimeMillis();
        long read = end - start;
        System.out.println("Read: " + read);

        // Sort data
        int[] output = Arrays.copyOf(input, input.length);
        start = System.currentTimeMillis();
        mergeSort(output);
        end = System.currentTimeMillis();
        long sort = end - start;
        System.out.println("Sort: " + sort );

        // Write data
        start = System.currentTimeMillis();
        write(output, outPath);
        end = System.currentTimeMillis();
        long write = end - start;
        System.out.println("Write: " + write);

        System.out.println("Total Time: " + (read + sort + write) + "\n");

        return (read + sort + write);
    }

    private static int[] read(String path, int count) throws IOException
    {
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        int[] numbers = new int[count];
        int start = 0;
        int end;
        int num = 0;
        int finalIndex = 0;
        for(int i = 0; i < bytes.length; i++)
        {
            if((bytes[i] == 13 && bytes[i + 1] == 10) || (i == bytes.length - 1))
            {
                if(i != bytes.length - 1)  end = i - 1;
                else end = i;
                for(int j = end; j >= start; j--)
                {
                    num += ((bytes[j] - 48) * powerOfTen(end - j));
                }
                numbers[finalIndex] = num;
                finalIndex++;
                num = 0;
                start = i + 2;
                i++;
            }
        }
        return numbers;
    }
    private static void mergeSort(int[] array)
    {
        if(array.length <= 1) return;
        int[] one = Arrays.copyOfRange(array, 0, array.length / 2);
        int[] two = Arrays.copyOfRange(array, array.length / 2, array.length);
        mergeSort(one);
        mergeSort(two);
        merge(array, one, two);
    }
    private static void merge(int[] result, int[] one, int[] two)
    {
        int i1 = 0;
        int i2 = 0;
        for(int i = 0; i < result.length; i++)
        {
            if(i2 >= two.length  ||  (i1 < one.length && one[i1] <= two[i2]))
            {
                result[i] = one[i1];
                i1++;
            }
            else
            {
                result[i] = two[i2];
                i2++;
            }
        }
    }
    private static void write(int[] array, String path) throws IOException
    {
        BufferedWriter writer = Files.newBufferedWriter(Paths.get(path), StandardCharsets.US_ASCII);
        String line;
        for(int num : array)
        {
            line = Integer.toString(num) + "\r\n";
            try
            {
                writer.write(line, 0, line.length());
            }
            catch(IOException e)
            {
                System.err.format("IOException: %s%n", e);
            }
        }
        writer.close();
    }

    private static int powerOfTen(int pow) { return POWERS_OF_10[pow]; }
    private static void printCollection(int[] array)
    {
        for(int num : array)
        {
            if(num != array[array.length - 1]) System.out.print(num + ", ");
            else System.out.println(num);
        }
    }
}
