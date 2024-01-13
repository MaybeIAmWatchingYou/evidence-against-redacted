package com.swdteam.util;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;


public class IOUtil
{
  public static void writeObjectToFile(Object object, String filePath, Gson gson) {
     File f = new File(filePath);

     if (!f.exists()) {
       f.getParentFile().mkdirs();
    }

    try {
       BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
       writer.write(gson.toJson(object));
       writer.close();
     } catch (IOException e) {
       e.printStackTrace();
    }
  }

  public static Object loadObjectFromFile(String filePath, Class c, Gson gson, boolean create) {
     File f = new File(filePath);

     if (!f.exists() && create) {
      try {
         f.createNewFile();
       } catch (IOException e) {
         e.printStackTrace();
      }
    }

     if (f.exists()) {
      try {
         BufferedReader reader = new BufferedReader(new FileReader(f));
         StringBuilder sb = new StringBuilder();
         String line = "";
         while ((line = reader.readLine()) != null) {
           sb.append(line);
        }
         reader.close();

         Object o = gson.fromJson(sb.toString(), c);
         return o;
       } catch (Exception exception) {}
    }



     return null;
  }

  public static String readFileURL(String s) {
    try {
       URL url = new URL(s);
       BufferedReader read = new BufferedReader(new InputStreamReader(url.openStream()));
       StringBuffer sb = new StringBuffer();
      String i;
       while ((i = read.readLine()) != null) {
         sb.append(i);
      }
       read.close();
       return sb.toString();
     } catch (Exception e) {
       return null;
    }
  }
}


