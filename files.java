package com.company.cryptor;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

public class files {
    static String path = "C://secrets//folder//";

    public static void write(String fname, String val) throws Exception {
        String pre = read(fname);
        FileWriter fw = new FileWriter(path + fname);
        fw.write(pre + val);
        fw.close();
    }

    public static String read(String fname) throws Exception {
        FileReader fr = new FileReader(path + fname);
        Scanner sc = new Scanner(fr);
        String res = sc.nextLine();
        while (sc.hasNextLine()) {
            res += System.getProperty("line.separator") + sc.nextLine();
        }
        fr.close();
        return res;
    }
}
