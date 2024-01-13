package com.swdteam.common.init;

import com.swdteam.common.entity.dalek.DalekType;
import com.swdteam.common.entity.dalek.IDalek;
import com.swdteam.common.entity.dalek.types.AdvancedInvasion;
import com.swdteam.common.entity.dalek.types.AdvancedSkaro;
import com.swdteam.common.entity.dalek.types.Chocolate;
import com.swdteam.common.entity.dalek.types.Classic;
import com.swdteam.common.entity.dalek.types.Ender;
import com.swdteam.common.entity.dalek.types.EnderSpecialWeapons;
import com.swdteam.common.entity.dalek.types.Imperial;
import com.swdteam.common.entity.dalek.types.Invasion;
import com.swdteam.common.entity.dalek.types.Molten;
import com.swdteam.common.entity.dalek.types.Nether;
import com.swdteam.common.entity.dalek.types.Renegade;
import com.swdteam.common.entity.dalek.types.Skaro;
import com.swdteam.common.entity.dalek.types.SpecialWeapons;
import com.swdteam.common.entity.dalek.types.SuicideBomber;
import com.swdteam.common.entity.dalek.types.TimeWar;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.annotation.CheckForNull;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;




public class DMDalekRegistry
{
   public static Map<DalekType, List<String>> DALEK_TYPES = new HashMap<>();
  
   public static Map<ResourceLocation, List<IDalek>> DALEK_SPAWNS = new HashMap<>();
  
   private static List<String> dalekList = new ArrayList<>();
   private static Map<String, IDalek> daleks = new HashMap<>();
  
  public static IDalek SKARO_DALEK;
  
  public static IDalek ADVANCED_SKARO_DALEK;
  
  public static IDalek INVASION_DALEK;
  
  public static IDalek ADVANCED_INVASION_DALEK;
  public static IDalek SUICIDE_BOMBER_DALEK;
  public static IDalek SPECIAL_WEAPONS_DALEK;
  public static IDalek NETHER_SPECIAL_WEAPONS_DALEK;
  public static IDalek ENDER_SPECIAL_WEAPONS_DALEK;
  public static IDalek ENDER_DALEK;
  public static IDalek TIME_WAR_DALEK;
  public static IDalek NETHER_DALEK;
  public static IDalek MOLTEN_DALEK;
  public static IDalek CHOCOLATE_DALEK;
  public static IDalek CLASSIC_DALEK;
  public static IDalek IMPERIAL_DALEK;
  public static IDalek RENEGADE_DALEK;
  
  public static void init() {
     SKARO_DALEK = addDalek(DalekType.SKARO, (IDalek)new Skaro("Skaro Dalek"), "skaro_dalek");
     SKARO_DALEK.addChild("skaro_dalek_alt");
     SKARO_DALEK.addChild("skaro_dalek_blue");
     SKARO_DALEK.addChild("skaro_dalek_blue_alt");
     SKARO_DALEK.addChild("skaro_dalek_black");
     SKARO_DALEK.addChild("skaro_dalek_black_alt");
     SKARO_DALEK.addChild("skaro_dalek_guard");
     SKARO_DALEK.addChild("skaro_dalek_supreme");
    
     ADVANCED_SKARO_DALEK = addDalek(DalekType.ADVANCED_SKARO, (IDalek)new AdvancedSkaro("Advanced Skaro Dalek"), "advanced_skaro_dalek");
     ADVANCED_SKARO_DALEK.addChild("advanced_skaro_dalek_red");
     ADVANCED_SKARO_DALEK.addChild("advanced_skaro_dalek_black");
    
     INVASION_DALEK = addDalek(DalekType.INVASION, (IDalek)new Invasion("Invasion Dalek"), "invasion_dalek");
     INVASION_DALEK.addChild("invasion_dalek_alt");
     INVASION_DALEK.addChild("invasion_dalek_gold");
     INVASION_DALEK.addChild("invasion_dalek_gold_alt");
     INVASION_DALEK.addChild("invasion_dalek_commander");
     INVASION_DALEK.addChild("invasion_dalek_supreme");
    
     ADVANCED_INVASION_DALEK = addDalek(DalekType.ADVANCED_INVASION, (IDalek)new AdvancedInvasion("Advanced Invasion Dalek"), "advanced_invasion_dalek");
     ADVANCED_INVASION_DALEK.addChild("advanced_invasion_dalek_red");
     ADVANCED_INVASION_DALEK.addChild("advanced_invasion_dalek_gold");
     ADVANCED_INVASION_DALEK.addChild("advanced_invasion_dalek_black");
    
     SUICIDE_BOMBER_DALEK = addDalek(DalekType.SUICIDE_BOMBER, (IDalek)new SuicideBomber("Suicide Bomber Dalek"), "suicide_bomber_dalek");
     SUICIDE_BOMBER_DALEK.addChild("suicide_bomber_dalek_alt");
     SUICIDE_BOMBER_DALEK.addChild("suicide_bomber_dalek_alt_2");
    
     SPECIAL_WEAPONS_DALEK = addDalek(DalekType.SPECIAL_WEAPONS, (IDalek)new SpecialWeapons("Special Weapons Dalek"), "special_weapons_dalek");
     NETHER_SPECIAL_WEAPONS_DALEK = addDalek(DalekType.NETHER_SPECIAL_WEAPONS, (IDalek)new SpecialWeapons("Nether Special Weapons Dalek"), "nether_special_weapons_dalek");
     ENDER_SPECIAL_WEAPONS_DALEK = addDalek(DalekType.ENDER_SPECIAL_WEAPONS, (IDalek)new EnderSpecialWeapons("Ender Special Weapons Dalek"), "ender_special_weapons_dalek");

    
     TIME_WAR_DALEK = addDalek(DalekType.TIME_WAR, (IDalek)new TimeWar("Time War Dalek"), "time_war_dalek");
     TIME_WAR_DALEK.addChild("time_war_dalek_alt");
     TIME_WAR_DALEK.addChild("time_war_dalek_black");
     TIME_WAR_DALEK.addChild("time_war_dalek_black_alt");
    
     NETHER_DALEK = addDalek(DalekType.NETHER, (IDalek)new Nether("Nether Dalek"), "nether_dalek");
     NETHER_DALEK.addChild("nether_dalek_alt");
    
     MOLTEN_DALEK = addDalek(DalekType.MOLTEN, (IDalek)new Molten("Molten Dalek"), "molten_dalek");
    
     ENDER_DALEK = addDalek(DalekType.ENDER, (IDalek)new Ender("Ender Dalek"), "ender_dalek");
    
     CHOCOLATE_DALEK = addDalek(DalekType.CHOCOLATE, (IDalek)new Chocolate("Chocolate Dalek"), "chocolate_dalek");
     CHOCOLATE_DALEK.addChild("chocolate_dalek_dark");
     CHOCOLATE_DALEK.addChild("chocolate_dalek_white");
     CHOCOLATE_DALEK.addChild("chocolate_dalek_strawberry");
     CHOCOLATE_DALEK.addChild("chocolate_dalek_matcha");
    
     CLASSIC_DALEK = addDalek(DalekType.CLASSIC, (IDalek)new Classic("Classic Dalek"), "classic_dalek");
     CLASSIC_DALEK.addChild("classic_dalek_alt");
    
     IMPERIAL_DALEK = addDalek(DalekType.IMPERIAL, (IDalek)new Imperial("Imperial Dalek"), "imperial_dalek");
     RENEGADE_DALEK = addDalek(DalekType.RENEGADE, (IDalek)new Renegade("Renegade Dalek"), "renegade_dalek");
  }
  
  public static class ArmTypes {
    public static final String DEFAULT_GUN = "GunArm";
    public static final String DEFAULT_PLUNGER = "SuctionArm";
    public static final String CLAW_ARM = "ClawArm";
    public static final String FLAME_THROWER = "FlameThrowerArm";
  }
  
  public static void addDalek(IDalek dalek) {
     if (dalek.getID() != null) {
       register(dalek.getID(), dalek);
    }
  }

  
  public static IDalek addDalek(IDalek dalek, String registryKey) {
     return addDalek(DalekType.OTHER, dalek, registryKey);
  }
  
  public static IDalek addDalek(DalekType type, IDalek dalek, String registryKey) {
    try {
       dalek.setRegistryName(type, registryKey);
     } catch (Exception e) {
       e.printStackTrace();
    } 
    
     register(registryKey, dalek);
    
     return dalek;
  }
  
  public static void register(String registryKey, IDalek dalek) {
     daleks.put(registryKey, dalek);
     dalekList.add(registryKey);
    
     if (!DALEK_TYPES.containsKey(dalek.getType())) {
       DALEK_TYPES.put(dalek.getType(), new ArrayList<>());
    }
    
     ((List<String>)DALEK_TYPES.get(dalek.getType())).add(dalek.getID());
  }
  
  public static IDalek getRandomDalek(Random rand, DalekType type) {
     List<String> dalekList = DALEK_TYPES.get(type);
     if (rand.nextInt(9) == 0) {
       return getDalek(dalekList.get(rand.nextInt(dalekList.size())));
    }
    
     return getDalek(dalekList.get(0));
  }
  
  public static Map<String, IDalek> getDaleks() {
     return daleks;
  }
  
  @CheckForNull
  public static IDalek getDalekForBiome(World world, Biome biomeIn) {
     if (DALEK_SPAWNS.containsKey(biomeIn.getRegistryName())) {
       List<IDalek> daleks = DALEK_SPAWNS.get(biomeIn.getRegistryName());
      
       IDalek dalek = daleks.get(world.rand.nextInt(daleks.size()));
       if (dalek.getChildren() != null && dalek.getChildren().size() > 0) {
         return world.rand.nextBoolean() ? dalek : dalek.getChildren().get(world.rand.nextInt(dalek.getChildren().size()));
      }
       return dalek;
    } 
     return null;
  }
  
  public static String getRandomDalek(Random rand) {
     return dalekList.get(rand.nextInt(dalekList.size()));
  }
  
  public static IDalek getDalek(String identifier) {
     if (daleks.containsKey(identifier)) {
       return daleks.get(identifier);
    }
     return SKARO_DALEK;
  }

  
  public static List<String> getDalekList() {
     return dalekList;
  }
  
  @SafeVarargs
  public static void addSpawn(IDalek dalek, RegistryKey<Biome>... biomes) {
     for (RegistryKey<Biome> biome : biomes) {
      
       if (DALEK_SPAWNS.containsKey(biome.getRegistryLocation())) {
         ((List<IDalek>)DALEK_SPAWNS.get(biome.getRegistryLocation())).add(dalek);
      } else {
         List<IDalek> l = new ArrayList<>();
         l.add(dalek);
         DALEK_SPAWNS.put(biome.getRegistryLocation(), l);
      } 
    } 
  }
  
  public static void addSpawn(RegistryKey<Biome> biome, IDalek... daleks) {
     Biome b = (Biome)DynamicRegistries.func_239770_b_().func_243612_b(Registry.BIOME_KEY).func_243576_d(biome);
    
     for (IDalek dalek : daleks) {
       if (DALEK_SPAWNS.containsKey(b.getRegistryName())) {
         ((List<IDalek>)DALEK_SPAWNS.get(b.getRegistryName())).add(dalek);
      } else {
         List<IDalek> l = new ArrayList<>();
         l.add(dalek);
         DALEK_SPAWNS.put(b.getRegistryName(), l);
      } 
    } 
  }
}


