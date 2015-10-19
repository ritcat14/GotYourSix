package com.ritcat14.GotYourSix.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;


public class FileHandler {

    /*
    String currentUsersHomeDir = System.getProperty("user.home");
    and to append path separator

    String otherFolder = currentUsersHomeDir + File.separator + "other";
     */

    private static final String file    = "";
    private static final String netDir  = "";
    private static final String homeDir = System.getProperty("user.home") + File.separator + "GotYourSix";
    private static final String userDir = homeDir + File.separator + "User";

    public static void openFile(String group, String file) {
        /*try {
          // Write to file      
          FileWriter fw = new FileWriter("C:\\Users\\Nicolas\\Desktop\\save.txt");
          BufferedWriter save = new BufferedWriter(fw);

          save.write("example");
          save.flush();

          // read as stream
          BufferedReader r = new BufferedReader(new FileReader("C:\\Users\\Nicolas\\Desktop\\save.txt"));
          System.out.println(r.readLine());

          save.close();
        } catch (Exception e) {}*/
    }

    public static boolean UserExists() {
        File file = new File(userDir);
        return file.exists();
    }

    public static void createUser(String name) {
        System.out.println(userDir + File.separator + "user.txt");
        Writer writer = null;
        createFile(userDir);

        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(userDir + File.separator + "user.txt"), "utf-8"));
            writer.write(name);
        } catch (IOException ex) {
            // report
        } finally {
            try {
                writer.close();
            } catch (Exception ex) {/*ignore*/
            }
        }
    }

    private static void createFile(String dir) {
        File theDir = new File(dir);

        // if the directory does not exist, create it
        if (!theDir.exists()) {
            System.out.println("creating directory: " + theDir.getPath());
            boolean result = false;

            try {
                theDir.mkdir();
                result = true;
            } catch (SecurityException se) {
                //handle it
            }
            if (result) {
                System.out.println("DIR created");
            }
        }
    }

    public static void setupGameFiles() {
        File theDir = new File(homeDir);

        // if the directory does not exist, create it
        if (!theDir.exists()) {
            System.out.println("creating directory: " + theDir.getPath());
            boolean result = false;

            try {
                theDir.mkdir();
                result = true;
            } catch (SecurityException se) {
                //handle it
            }
            if (result) {
                System.out.println("DIR created");
            }
        }
    }

    public static String getPlayerName() {
        String name  = "";
        try {
            // read as stream
            BufferedReader r = new BufferedReader(new FileReader(userDir + File.separator + "user.txt"));
            name = r.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
      return name;
    }

}
