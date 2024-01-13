package com.swdteam.common.kerblam;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.swdteam.common.init.DMKerblamStock;
import com.swdteam.main.DalekMod;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.resources.JsonReloadListener;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;



public class KerblamItemReloadListener
  extends JsonReloadListener
{
  public KerblamItemReloadListener(Gson p_i51536_1_, String p_i51536_2_) {
     super(p_i51536_1_, p_i51536_2_);
  }





  
  protected void apply(Map<ResourceLocation, JsonElement> objectIn, IResourceManager resourceManagerIn, IProfiler profilerIn) {
     Map<IRecipeType<?>, ImmutableMap.Builder<ResourceLocation, IRecipe<?>>> map = Maps.newHashMap();
     DMKerblamStock.getItems().clear();
     List<KerblamItem> items = new ArrayList<>();
    
     for (Map.Entry<ResourceLocation, JsonElement> entry : objectIn.entrySet()) {
       ResourceLocation resourcelocation = entry.getKey();
       KerblamItem item = (KerblamItem)DalekMod.GSON.fromJson(entry.getValue(), KerblamItem.class);
      
       item.setResourceKey(resourcelocation);
       items.add(item);
    } 
    
     Collections.sort(items, (o1, o2) -> o1.getCategory().compareTo(o2.getCategory()));

    
     System.out.println(items);
    
     Map<ResourceLocation, KerblamItem> itemMap = new LinkedHashMap<>();
     for (KerblamItem item : items) {
       itemMap.put(item.getResourceKey(), item);
    }
    
     DMKerblamStock.getItems().clear();
     DMKerblamStock.getItems().putAll(itemMap);
  }
}


