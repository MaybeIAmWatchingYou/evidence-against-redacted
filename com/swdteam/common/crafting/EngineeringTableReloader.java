package com.swdteam.common.crafting;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.swdteam.common.init.DMEngineeringTableRegistry;
import com.swdteam.main.DalekMod;
import java.util.Map;
import net.minecraft.client.resources.JsonReloadListener;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;



@Deprecated
public class EngineeringTableReloader
  extends JsonReloadListener
{
  public EngineeringTableReloader(Gson g, String s) {
     super(g, s);
  }

  
  protected void apply(Map<ResourceLocation, JsonElement> objectIn, IResourceManager resourceManagerIn, IProfiler profilerIn) {
     DMEngineeringTableRegistry.DEPRECRATED_RECIPES.clear();
    
     for (Map.Entry<ResourceLocation, JsonElement> entry : objectIn.entrySet()) {
       EngineeringTableRecipeJSON recipe = (EngineeringTableRecipeJSON)DalekMod.GSON.fromJson(entry.getValue(), EngineeringTableRecipeJSON.class);
      
       if (recipe != null) {
         EngineeringTableRecipe converted = new EngineeringTableRecipe(entry.getKey(), recipe.getIngredients(), recipe.getOutput());
        
         DMEngineeringTableRegistry.DEPRECRATED_RECIPES.add(converted);
      } 
    } 
  }
}


