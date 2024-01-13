package com.swdteam.common.init;

import com.swdteam.common.tardis.Tardis;
import com.swdteam.common.tardis.TardisInterior;
import com.swdteam.util.world.Schematic;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;



@EventBusSubscriber(modid = "dalekmod", bus = Mod.EventBusSubscriber.Bus.MOD)
public class DMTardisRegistry
{
   private static Map<ResourceLocation, Tardis> TARDISES = new HashMap<>();
   private static Map<ResourceLocation, Schematic> TARDIS_INTERIOR_BUILDS = new HashMap<>();
   private static Map<ResourceLocation, TardisInterior> TARDIS_INTERIORS = new HashMap<>();

   public static ResourceLocation TARDIS_CAPSULE = new ResourceLocation("dalekmod", "tardis_capsule");
   public static ResourceLocation SIDRAT_CAPSULE = new ResourceLocation("dalekmod", "sidrat_capsule");


  public static Map<ResourceLocation, Tardis> getRegistry() {
     return TARDISES;
  }

  public static List<Tardis> getRegistryAsList() {
     return new ArrayList<>(TARDISES.values());
  }

  public static List<TardisInterior> getInteriorRegistryAsList() {
     return new ArrayList<>(TARDIS_INTERIORS.values());
  }

  public static Tardis getExterior(ResourceLocation rl) {
     if (TARDISES.containsKey(rl)) {
       return TARDISES.get(rl);
    }
     return TARDISES.get(TARDIS_CAPSULE);
  }


  public static Map<ResourceLocation, Schematic> getInteriorBuildRegistry() {
     return TARDIS_INTERIOR_BUILDS;
  }

  public static Map<ResourceLocation, TardisInterior> getTardisInteriors() {
     return TARDIS_INTERIORS;
  }
}


