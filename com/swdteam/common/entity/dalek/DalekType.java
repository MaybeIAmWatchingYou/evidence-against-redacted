package com.swdteam.common.entity.dalek;

import java.util.ArrayList;
import java.util.List;

public class DalekType
{
   public static List<DalekType> DALEK_TYPES = new ArrayList<>();

   public static DalekType SKARO = new DalekType("skaro_dalek");
   public static DalekType ADVANCED_SKARO = new DalekType("advanced_skaro_dalek");
   public static DalekType INVASION = new DalekType("invasion_dalek");
   public static DalekType ADVANCED_INVASION = new DalekType("advanced_invasion_dalek");
   public static DalekType SUICIDE_BOMBER = new DalekType("suicide_bomber_dalek");
   public static DalekType SPECIAL_WEAPONS = new DalekType("special_weapons_dalek");
   public static DalekType NETHER_SPECIAL_WEAPONS = new DalekType("nether_special_weapons_dalek");
   public static DalekType ENDER_SPECIAL_WEAPONS = new DalekType("ender_special_weapons_dalek");
   public static DalekType TIME_WAR = new DalekType("time_war_dalek");
   public static DalekType NETHER = new DalekType("nether_dalek");
   public static DalekType MOLTEN = new DalekType("molten_dalek");
   public static DalekType ENDER = new DalekType("ender_dalek");
   public static DalekType IMPERIAL = new DalekType("imperial_dalek");
   public static DalekType RENEGADE = new DalekType("renegade_dalek");
   public static DalekType CHOCOLATE = new DalekType("chocolate_dalek");
   public static DalekType CLASSIC = new DalekType("classic_dalek");
   public static DalekType OTHER = new DalekType("other_dalek");

  public String registryName;

  public DalekType(String registryName) {
     this.registryName = registryName;
     DALEK_TYPES.add(this);
  }

  public String getRegistryName() {
     return this.registryName;
  }
}


