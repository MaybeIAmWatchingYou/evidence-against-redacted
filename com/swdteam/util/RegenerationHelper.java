package com.swdteam.util;

































public class RegenerationHelper
{
  public static String getSkinType(SkinChoice choice) {
     return (choice == SkinChoice.ALEX) ? "slim" : "default";
  }

  public enum SkinChoice {
     NONE("none"), STEVE("steve"), ALEX("alex"), RANDOM("random");

    String directory;
    String choice;

    SkinChoice(String s) {
       this.choice = s;
    }

    public String getChoice() {
       return this.choice;
    }

    public static SkinChoice getSkinChoice(String s) {
       for (SkinChoice choice : values()) {
         if (choice.getChoice().equalsIgnoreCase(s)) {
           return choice;
        }
      }
       return NONE;
    }
  }
}


