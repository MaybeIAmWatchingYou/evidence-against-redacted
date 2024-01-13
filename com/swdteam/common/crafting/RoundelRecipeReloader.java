package com.swdteam.common.crafting;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.swdteam.common.init.DMRoundelRegistry;
import com.swdteam.main.DalekMod;
import java.util.Map;
import net.minecraft.client.resources.JsonReloadListener;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;



@Deprecated
public class RoundelRecipeReloader
  extends JsonReloadListener
{
  public RoundelRecipeReloader(Gson g, String s) {
     super(g, s);
  }

  
  protected void apply(Map<ResourceLocation, JsonElement> objectIn, IResourceManager resourceManagerIn, IProfiler profilerIn) {
     DMRoundelRegistry.DEPRECRATED_RECIPES.clear();
    
     for (Map.Entry<ResourceLocation, JsonElement> entry : objectIn.entrySet()) {
       RoundelRecipeJSON recipe = (RoundelRecipeJSON)DalekMod.GSON.fromJson(entry.getValue(), RoundelRecipeJSON.class);
      
       if (recipe != null) {
         RoundelBuilderRecipe converted = new RoundelBuilderRecipe(entry.getKey(), recipe.getIngredients(), recipe.getOutput());
        
         DMRoundelRegistry.DEPRECRATED_RECIPES.add(converted);
      } 
    } 
  }
}


