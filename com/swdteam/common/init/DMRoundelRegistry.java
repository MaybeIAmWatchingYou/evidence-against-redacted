package com.swdteam.common.init;

import com.swdteam.common.crafting.RoundelBuilderRecipe;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.Ingredient;


@Deprecated
public class DMRoundelRegistry
{
   public static List<RoundelBuilderRecipe> DEPRECRATED_RECIPES = new ArrayList<>();
  
  public static List<RoundelBuilderRecipe> getAllRecipes() {
     Minecraft mc = Minecraft.func_71410_x();
     if (mc != null && mc.field_71439_g != null) {
       List<RoundelBuilderRecipe> list = mc.field_71439_g.world.func_199532_z().func_241447_a_(DMCraftingTypes.ROUNDEL_RECIPE);
       list.addAll(DEPRECRATED_RECIPES);
       return list;
    } 
    
     return DEPRECRATED_RECIPES;
  }
  
  public static List<RoundelBuilderRecipe> getRecipesFor(IInventory inventory) {
     List<Item> ingredients = new ArrayList<>();
     List<RoundelBuilderRecipe> matches = new ArrayList<>();
    
     if (inventory.func_70301_a(0) == null || inventory.func_70301_a(1) == null) return matches;
    
     ingredients.add(inventory.func_70301_a(0).getItem());
     ingredients.add(inventory.func_70301_a(1).getItem());
    
     DEPRECRATED_RECIPES.forEach(recipe -> {
          List<Item> neededItems = new ArrayList<>();
          
          neededItems.add(((Ingredient)recipe.func_192400_c().get(0)).func_193365_a()[0].getItem());
          neededItems.add(((Ingredient)recipe.func_192400_c().get(1)).func_193365_a()[0].getItem());
          if (neededItems.containsAll(ingredients) && ingredients.containsAll(neededItems)) {
            matches.add(recipe);
          }
        });
     return matches;
  }
}


