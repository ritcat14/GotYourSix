package com.ritcat14.GotYourSix.util;

import com.ritcat14.GotYourSix.Game;
import com.ritcat14.GotYourSix.entity.mob.Player;
import com.ritcat14.GotYourSix.graphics.UI.menus.UserSetup;

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
    private static final String netDir  = "Q:" + File.separator + "Teaching and Learning" + File.separator + "Kris Rice" + File.separator;
    private static final String groupDir = netDir + File.separator + "Groups" + File.separator;
    private static final String homeDir = "N:" + File.separator + "GotYourSix" + File.separator;
    private static final String userDir = homeDir + File.separator + "User" + File.separator;
  
    private static boolean fileExists(String file){
      File f = new File(file);
      return f.exists();
    }

    public static void save() {
        if (!fileExists(netDir)) createFile(netDir);
        if (!fileExists(homeDir)) createFile(homeDir);
        Writer writer = null;
        //1. Save users name locally
        try {
            if (!fileExists(userDir)) createFile(userDir);
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(userDir + "user.txt"), "utf-8"));
            if (userExists()) writer.write(getPlayerName());
            else writer.write(UserSetup.getName());
        } catch (IOException ex) {
        } finally {
            try {
                writer.close();
            } catch (Exception ex) {ignore
            }
        }
      
        if (Game.playerCreated){
        //2. Save player on network
        try {
            String userfile = groupDir + File.separator + Player.getGroup() + File.separator + "Users";
            if (!fileExists(userfile)) createFile(userfile);
            userfile = userfile + File.separator + Player.getName() + ".txt";
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(userfile), "utf-8"));
            writer.write(Player.getStats());
        } catch (IOException ex) {
        } finally {
            try {
                writer.close();
            } catch (Exception ex) {ignore
            }
        }
        }      
    }

    private static void createFile(String dir) {
        File theDir = new File(dir);
        if (!theDir.exists()) {
            System.out.println("creating directory: " + theDir.getPath());
            boolean result = false;
            try {
                theDir.mkdir();
                result = true;
            } catch (SecurityException se) {}
            if (result) {
                System.out.println("DIR created");
            }
        }
    }
  
  
  

    public static String getPlayerName() {
        String name  = "";
        try {
            BufferedReader r = new BufferedReader(new FileReader(userDir + "user.txt"));
            name = r.readLine();
        } catch (IOException e) {}
      return name;
    }

    public static boolean userExists() {
        File file = new File(userDir);
        return file.exists();
    }
*/
}