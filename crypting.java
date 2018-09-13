package com.company.cryptor;

import java.io.FileReader;
import java.util.Arrays;
import java.util.Base64;
import java.util.Scanner;

import static com.company.cryptor.files.*;

public class crypting {
    private  static String base64alph = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890+/=";
    private static String path = "C://secrets//";
    private static String key1 = "";
    private static String key2 = "";
    private static String key3 = "";

    private static void init() throws Exception {
        key1 = read("key1.key");
        key2 = read("key2.key");
        key3 = read("key3.key");
    }

    public static String encode(String str) throws Exception {
        if (key1.equals("")) {
            init();
        }
        char[] encoded = (new String(Base64.getEncoder().encode(str.getBytes()))).toCharArray();


        for (int a = 0; a < encoded.length; a++) {
            encoded[a] = key1.charAt(base64alph.indexOf(encoded[a]));
        }

        for (int ac = 0; ac < encoded.length; ac++) {
            encoded[ac] = key2.charAt(base64alph.indexOf(encoded[ac]));
        }

        for (int ad = 0; ad < encoded.length; ad++) {
            encoded[ad] = key3.charAt(base64alph.indexOf(encoded[ad]));
        }
        return new String(encoded);
    }

    public static String decode(String str) throws Exception {
        char[] decoded = str.toCharArray();
        if (key1.equals("")) {
            init();
        }

        for (int ad = 0; ad < decoded.length; ad++) {
            decoded[ad] = base64alph.charAt(key3.indexOf(decoded[ad]));
        }
        for (int ac = 0; ac < decoded.length; ac++) {
            decoded[ac] = base64alph.charAt(key2.indexOf(decoded[ac]));
        }


        for (int a = 0; a < decoded.length; a++) {
            decoded[a] = base64alph.charAt(key1.indexOf(decoded[a]));
        }



        String res = new String(decoded);
        return new String(Base64.getDecoder().decode(res.getBytes()));
    }

    private static String read(String fname) throws Exception {
        FileReader fr = new FileReader(path + fname);
        Scanner sc = new Scanner(fr);
        String res = sc.nextLine();
        while (sc.hasNextLine()) {
            res += "\n" + sc.nextLine();
        }
        fr.close();
        return res;
    }
}

