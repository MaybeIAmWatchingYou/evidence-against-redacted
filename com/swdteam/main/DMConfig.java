package com.swdteam.main;


public class DMConfig {
  static final ForgeConfigSpec clientSpec;
  public static final Client CLIENT;
  static final ForgeConfigSpec commonSpec;
  public static final Common COMMON;
  static final ForgeConfigSpec serverSpec;
  public static final Server SERVER;

  public static class Client { public final ForgeConfigSpec.ConfigValue<Boolean> customTitleScreen;

    Client(ForgeConfigSpec.Builder builder) {
       builder.comment("Dalek Mod Client configuration settings").push("client");
       this
         .customTitleScreen = (ForgeConfigSpec.ConfigValue<Boolean>)builder.comment("Use the custom Dalek Mod titlescreen").define("customTitleScreen", true);
       this
         .getSplashes = (ForgeConfigSpec.ConfigValue<Boolean>)builder.comment(new String[] { "Connect the the SWDTeam website to get the latest title screen splashes", "If customTitleScreen is disabled, this will be too" }).define("getSplashes", true);
       this
         .renderLightmaps = (ForgeConfigSpec.ConfigValue<Boolean>)builder.comment("Toggle lightmaps").define("renderLightmaps", true);
    }
    public final ForgeConfigSpec.ConfigValue<Boolean> renderLightmaps;
    public final ForgeConfigSpec.ConfigValue<Boolean> getSplashes; }

  public static class Common { public final ForgeConfigSpec.ConfigValue<Integer> tardisYMin;

    Common(ForgeConfigSpec.Builder builder) {
       builder.comment("Dalek Mod Common Configuration Setting").push("common");
       this
         .tardisYMin = builder.comment("Minimum Y value TARDIS can fly to").define("tardisMinY", Integer.valueOf(0));
    } }


  public static class Server {
    Server(ForgeConfigSpec.Builder builder) {
       builder.comment("Dalek Mod Server Configuration Settings").push("server");

       builder.pop();
    }
  }



  static {
     Pair<Client, ForgeConfigSpec> pair1 = (new ForgeConfigSpec.Builder()).configure(Client::new);
     clientSpec = (ForgeConfigSpec)pair1.getRight();
     CLIENT = (Client)pair1.getLeft();





     Pair<Common, ForgeConfigSpec> pair = (new ForgeConfigSpec.Builder()).configure(Common::new);
     commonSpec = (ForgeConfigSpec)pair.getRight();
     COMMON = (Common)pair.getLeft();





     Pair<Server, ForgeConfigSpec> specPair = (new ForgeConfigSpec.Builder()).configure(Server::new);
     serverSpec = (ForgeConfigSpec)specPair.getRight();
     SERVER = (Server)specPair.getLeft();
  }
}


