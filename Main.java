package com.company.cryptor;



import java.io.*;
import java.util.Arrays;
import java.util.Base64;
import java.util.Scanner;
import static com.company.cryptor.files.*;
import static com.company.cryptor.crypting.*;

public class Main {



    static Scanner in = new Scanner(System.in);
    static String[] ad;
    static String lineSeparator = System.getProperty("line.separator");
    static String[] manifest;
    static String path = "C://secrets//folder//";
    static String dir = "/";

    public static void main(String[] args) throws Exception {

        System.out.print(decode(encode("welcome\n")));

        update_manifest();

        ad = new String[7];
        ad[0] = "help";
        ad[1] = "exit";
        ad[2] = "encrypt";
        ad[3] = "decrypt";
        ad[4] = "mkdir";
        ad[5] = "ls";
        ad[6] = "cd";

        while (true) {
            String[] cmd = waitforcommand(ad);
            if (cmd[0].equals("help")) {
                help();
            }
            if (cmd[0].equals("exit")) {
                return;
            }
            if (cmd[0].equals("encrypt")) {
                encrypt(cmd);
            }
            if (cmd[0].equals("decrypt")) {
                decrypt(cmd);
            }
            if (cmd[0].equals("mkdir")) {
                mkdir(cmd);
            }
            if (cmd[0].equals("ls")) {
                ls();
            }
            if (cmd[0].equals("cd")) {
                cd(cmd);
            }
        }
    }

    static String[] waitforcommand(String[] commands) {
        String val = in.nextLine();
        String[] res = val.split(" ");
        for (int i = 0; i < commands.length; i++) {
            if (commands[i].equals(res[0])) {
                return res;
            }
        }
        System.out.println("Command \"" + res[0] + "\" not found");
        waitforcommand(commands);
        return res;
    }
    static void update_manifest() throws Exception {
        if (!read("manifest.txt").equals("manifest")) {
            manifest = read("manifest.txt").split(lineSeparator);
        } else {
            manifest = new String[0];
        }

    }

    static void help() {
        for (int i = 0; i < ad.length; i++) {
            System.out.println(ad[i]);
        }
    }
    static void encrypt(String[] cmd) throws Exception {
        System.out.println(crypting.encode(cmd[1]));
    }
    static void decrypt(String[] cmd) throws Exception {
        System.out.println(crypting.decode(cmd[1]));
    }
    static void mkdir(String[] cmd) throws Exception {
        if (dir.equals("/")) {
            write("manifest.txt", lineSeparator + encode(cmd[1]));
        } else {
            String route = encode(dir.substring(1, dir.length() - 1).split("/")[dir.substring(1, dir.length() - 1).split("/").length - 1]);
            write(route + "." + encode("dir"), lineSeparator + encode(cmd[1]));
        }
        File file = new File(path + encode(cmd[1]) + "." + encode("dir"));
        System.out.print((file.createNewFile()) ? "" : "dir " + cmd[1] + " is already exist\n");
        update_manifest();
    }
    static void ls() throws Exception {
        if (dir.equals("/")) {
            for (int i = 1; i < manifest.length; i++) {
                System.out.println(decode(manifest[i]));
            }
            return;
        }
        String route = encode(dir.substring(1, dir.length() - 1).split("/")[dir.substring(1, dir.length() - 1).split("/").length - 1]);
        String[] output = read(route + "." + encode("dir")).split(lineSeparator);
        for (int i = 0; i < output.length; i++) {
            System.out.println(decode(output[i]));
        }
    }
    static void cd(String[] cmd) throws Exception {
        String arg = cmd[1];
        if (arg.substring(0,1).equals("/")) {
            boolean correct = true;
            String[] folders = arg.substring(1, ((arg.substring(arg.length() - 1).equals("/")) ? arg.length()-1 : arg.length())).split("/");
            if (Arrays.asList(manifest).indexOf(encode(folders[0])) != -1) {
                for (int i = 1; i < folders.length; i++) {
                    if (Arrays.asList(read(encode(folders[i-1]) + "." + encode("dir")).split(lineSeparator)).indexOf(folders[i]) == -1) {
                        correct = false;
                    }
                }
            } else {
                correct = false;
            }
            if (correct) {
                dir = arg + ((arg.substring(arg.length() - 1).equals("/")) ? "" : "/");
            } else {
                System.out.println("Not found");
            }
        } else {
            boolean correct = true;
            String[] folders = arg.substring(0, ((arg.substring(arg.length() - 1).equals("/")) ? arg.length()-1 : arg.length())).split("/");
            if (dir.equals("/") ? Arrays.asList(manifest).indexOf(folders[0]) != -1 : Arrays.asList(read(encode(dir.substring(dir.lastIndexOf("/") + 1)) + "." + encode("dir")).split(lineSeparator)).indexOf(encode(folders[0])) != -1) {
                for (int i = 1; i < folders.length; i++) {
                    if (Arrays.asList(read(encode(folders[i-1]) + "." + encode("dir")).split(lineSeparator)).indexOf(folders[i]) == -1) {
                        correct = false;
                    }
                }
            } else {
                correct = false;
            }
            if (correct) {
                dir += arg + ((arg.substring(arg.length() - 1).equals("/")) ? "" : "/");
            } else {
                System.out.println("Not found");
            }
        }
    }
}
