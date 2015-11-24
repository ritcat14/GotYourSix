package com.ritcat14.GotYourSix.util;

import com.ritcat14.GotYourSix.entity.mob.Player;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;


public class FileHandler {
  
    public static final String netDir  = "Q:" + File.separator + "Teaching and Learning" + File.separator + "Kris Rice" + File.separator;
    public static final String netDataDir = netDir + "GameData" + File.separator;
    public static final String netGroupDir = netDataDir + "Groups" + File.separator;
    public static final String netGroupPassDir = netGroupDir + "Passwords" + File.separator;
    public static final String netUserDir = netDataDir + "Users" + File.separator;
    public static final String localDir = "N:" + File.separator + "GotYourSix" + File.separator + "UserData" + File.separator;
    public static final String localUserFile = localDir + "User.txt";
    public static final String startDir = System.getenv("userprofile") + "/AppData/Roaming/Microsoft/Windows/Start Menu/Programs/GotYourSix";
    public static String playerName = "";
  
    public static void setupGame(){
      createTree(startDir);
      createTree(netGroupDir);
      createTree(netGroupPassDir);
      createTree(netUserDir);
      createTree(localDir);
    }
  
    public static boolean fileExists(String file){
      File f = new File(file);
      return f.exists();
    }
  
    public static void createGroup(String group, String pass){
        try {
            PrintWriter pw = new PrintWriter(netGroupDir + group + ".txt"); // Create the file
            pw.close();
            pw = new PrintWriter(netGroupPassDir + group + ".txt"); // Create the file
            pw.write(pass);
            pw.close();
        } catch (IOException ex) {}
    }
  
    public static void addPlayerToGroup(String group, String player){
      try{
          PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(netGroupDir + group + ".txt", true)));
          out.println(player);
          out.close();
      } catch (IOException e) {}
    }
  
    public static boolean playerInGroup(String group, String player){
        boolean exists = false;
        String groupNames = "";
        try {
            groupNames = readFile(netGroupDir + group + ".txt", StandardCharsets.UTF_8);
            String[] parts = groupNames.split("\n");
            for (int i = 0; i < parts.length; i++){
              if (parts[i].equals(player)){
                exists = true;
                break;
              }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return exists;
    }
  
    public static boolean password(String group, String groupPass){
        boolean correct = false;
        String password = "";
        try {
            password = readFile(netGroupPassDir + group + ".txt", StandardCharsets.UTF_8);
            String[] parts = password.split("\n");
            for (int i = 0; i < parts.length; i++){
              if (parts[i].equals(groupPass)){
                correct = true;
                break;
              }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return correct;
    }

    private static void createTree(String dir) {
        File theDir = new File(dir);
        if (!theDir.exists()) {
            System.out.println("creating directory: " + theDir.getPath());
            boolean result = false;
            try {
                theDir.mkdirs();
                result = true;
            } catch (SecurityException se) {}
            if (result) {
                System.out.println("DIR created");
            }
        }
    }
  
  private static String readFile(String path, Charset encoding) throws IOException {
      byte[] encoded = Files.readAllBytes(Paths.get(path));
      return new String(encoded, encoding);
}
  
    public static String getStats(){
        try {
            return readFile(netUserDir + getPlayerName() + ".txt", StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
  
    public static String getPlayerName(){
      String name = "";
      BufferedReader reader = null;
      try {
        reader = new BufferedReader(new InputStreamReader(new FileInputStream(localUserFile), "utf-8"));
        try {
            name = reader.readLine();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    }
      return name;
    }
  
    public static void createPlayer(String player){
        try {
            PrintWriter pw = new PrintWriter(localUserFile); // Recreate the file
            pw.println(player); // Save player name
            pw.close();
        } catch (IOException ex) {}
    }

    public static void save(Player p) {
        System.out.println("Saving...");
        //1. Save users name locally
        try {
            PrintWriter pw = new PrintWriter(localUserFile); // Recreate the file
            pw.println(Player.getName()); // Save player name
            pw.close();
        } catch (IOException ex) {}
        
        //2. Save player on network
        try {
          PrintWriter pw = new PrintWriter(netUserDir + getPlayerName() + ".txt"); // Recreate the file
          // Save player data
          pw.write(p.getStats());
          pw.close();
        } catch (IOException ex) {}
        System.out.println("Saved");
      }
    }