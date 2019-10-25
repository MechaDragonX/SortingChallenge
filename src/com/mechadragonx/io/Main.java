package com.mechadragonx.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main
{
    private static ArrayList<Integer> result = new ArrayList<>();
    private static final int[] POWERS_OF_10 = { 1, 10, 100, 1000, 10000, 100000 };

    public static void main(String[] args) throws IOException
    {
        long start = System.currentTimeMillis();
        int[] input = read("./data/random.txt");
        long end = System.currentTimeMillis();
        System.out.println("Read: "+ (end - start));

        start = System.currentTimeMillis();
        mergeSort(input);
        end = System.currentTimeMillis();
        System.out.println("Sort: " + (end - start));
    }
    private static int[] read(String path) throws IOException
    {
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        ArrayList<Integer> list = new ArrayList<>();
        int start = 0;
        int end;
        int num = 0;
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
                list.add(num);
                num = 0;
                start = i + 2;
                i++;
            }
        }
        int[] array = new int[list.size()];
        for(int i = 0; i < array.length; i++)
        {
            array[i] = list.get(i);
        }
        return array;
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
//    private static void mergeSort(List<Integer> list)
//    {
//        if(list.size() <= 1) return;
//        List<Integer> one = list.subList(0, list.size() / 2);
//        List<Integer> two = list.subList(list.size() / 2, list.size() - 1);
//        mergeSort(one);
//        mergeSort(two);
//        result.addAll(merge(one, two));
//    }
//    private static ArrayList<Integer> merge(List<Integer> one, List<Integer> two)
//    {
//        ArrayList<Integer> result = new ArrayList<>();
//        int size = one.size() + two.size();
//        int i1 = 0;
//        int i2 = 0;
//        for(int i = 0; i < size; i++)
//        {
//            if(i2 >= two.size()  ||  (i1 < one.size() && one.get(i1) <= two.get(i2)))
//            {
//                result.add(one.get(i1));
//                i1++;
//            }
//            else
//            {
//                result.add(two.get(i2));
//                i2++;
//            }
//        }
//        return result;
//    }

    private static int powerOfTen(int pow)
    {
        return POWERS_OF_10[pow];
    }
    private static void printCollection(ArrayList<Integer> list)
    {
        for(int num : list)
        {
            System.out.print(num + "\t");
        }
    }
    private static void printCollection(int[] array)
    {
        for(int num : array)
        {
            System.out.print(num + "\t");
        }
    }
}
