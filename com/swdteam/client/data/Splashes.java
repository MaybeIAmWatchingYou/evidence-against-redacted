package com.swdteam.client.data;

import com.swdteam.main.DMConfig;
import com.swdteam.main.DalekMod;
import com.swdteam.util.IOUtil;
import net.minecraft.client.Minecraft;


public class Splashes
{
   public static String[] SPLASHES = new String[0];
  
  public static void load() {
     Boolean getSplashes = Boolean.valueOf((((Boolean)DMConfig.CLIENT.getSplashes.get()).booleanValue() && ((Boolean)DMConfig.CLIENT.customTitleScreen.get()).booleanValue()));
     String splashesFromURL = getSplashes.booleanValue() ? IOUtil.readFileURL("http://api.swdteam.com/dm/splashes.json") : null;
    
     if (splashesFromURL != null) {
      try {
         IOUtil.writeObjectToFile(DalekMod.GSON.fromJson(splashesFromURL, String[].class), "config/dalekmod/splash.json", DalekMod.GSON);
       } catch (Exception exception) {}
    }
    
     Object obj = IOUtil.loadObjectFromFile("config/dalekmod/splash.json", String[].class, DalekMod.GSON, true);
     if (obj != null && obj instanceof String[]) SPLASHES = (String[])obj;
    
     if (SPLASHES.length < 1) SPLASHES = new String[] { "Dalek Mod since 2012" };
    
     if (Minecraft.func_71410_x() != null && Minecraft.func_71410_x().func_110432_I() != null)
       for (int i = 0; i < SPLASHES.length; i++)
         SPLASHES[i] = SPLASHES[i].replace("%USERNAME%", Minecraft.func_71410_x().func_110432_I().func_111285_a());
  }
}


