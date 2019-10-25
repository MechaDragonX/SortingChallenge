package com.mechadragonx.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main
{
    private static final int[] POWERS_OF_10 = { 1, 10, 100, 1000, 10000, 100000 };

    public static void main(String[] args) throws IOException
    {
        String path = "./data/random.txt";
        long start = System.currentTimeMillis();
        readAsBytes(path);
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
    private static ArrayList<Integer> readAsBytes(String path) throws IOException
    {
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        ArrayList<Integer> numbers = new ArrayList<>();
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
                numbers.add(num);
                num = 0;
                start = i + 2;
                i++;
            }
        }
        return numbers;
    }
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
}
