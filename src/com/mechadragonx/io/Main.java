package com.mechadragonx.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main
{
    private static ArrayList<Integer> result = new ArrayList<>();
    private static final int[] POWERS_OF_10 = { 1, 10, 100, 1000, 10000, 100000 };

    public static void main(String[] args) throws Exception
    {
        execute();
    }
    private static long execute() throws IOException
    {
        String inPath = "./data/random.txt";
        String outPath = "./data/output.txt";
        File out = new File(outPath);
        if(out.exists())
        {
            if(out.delete()) System.out.println("The output file was deleted!\n");
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
        for(int num : array)
        {
            String line = Integer.toString(num) + "\r\n";
            try
            {
                writer.write(line, 0, line.length());
            }
            catch (IOException e)
            {
                System.err.format("IOException: %s%n", e);
            }
        }
        writer.close();
    }

    private static int[] readScanner(String path, int count) throws Exception
    {
        Scanner fileScan = new Scanner(new File(path));
        String[] lines = new String[count];
        int i = 0;
        while(fileScan.hasNextLine())
        {
            lines[i] = fileScan.nextLine();
            i++;
        }
        i = 0;
        int[] numbers = new int[count];
        for(String line : lines)
        {
            numbers[i] = Integer.parseInt(line);
        }
        return numbers;
    }
    private static void compare(int[] custom, int[] scanner)
    {
        if(custom.length != scanner.length) System.out.println("o_0 The arrays are not the same length!!");
        int differences = 0;
        for(int i = 0; i < custom.length; i++)
        {
            if(custom[i] != scanner[i])
            {
                differences++;
            }
        }
        if(differences == 0) System.out.println("Test successful!!");
    }

    private static int powerOfTen(int pow) { return POWERS_OF_10[pow]; }
    private static void printCollection(ArrayList<Integer> list)
    {
        for(int num : list)
        {
            if(num != list.get(list.size() - 1)) System.out.print(num + ", ");
            else System.out.println(num);
        }
    }
    private static void printCollection(int[] array)
    {
        for(int num : array)
        {
            if(num != array[array.length - 1]) System.out.print(num + ", ");
            else System.out.println(num);
        }
    }
}
