package com.swdteam.common.tardis.datapack;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.swdteam.common.init.DMTardisRegistry;
import com.swdteam.util.ObjectReloadListener;
import com.swdteam.util.world.Schematic;
import java.util.Map;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;



public class InteriorReloadListener
  extends ObjectReloadListener
{
  public InteriorReloadListener(String folderName) {
     super(folderName, ".schm");
  }






  protected void apply(Map<ResourceLocation, Object> objectIn, IResourceManager resourceManagerIn, IProfiler profilerIn) {
     Map<IRecipeType<?>, ImmutableMap.Builder<ResourceLocation, IRecipe<?>>> map = Maps.newHashMap();

     DMTardisRegistry.getInteriorBuildRegistry().clear();

     for (Map.Entry<ResourceLocation, Object> entry : objectIn.entrySet()) {
       ResourceLocation resourcelocation = entry.getKey();
       System.out.println(entry.getKey());
       if (entry.getValue() instanceof Schematic)
         DMTardisRegistry.getInteriorBuildRegistry().put(resourcelocation, (Schematic)entry.getValue());
    }
  }
}


