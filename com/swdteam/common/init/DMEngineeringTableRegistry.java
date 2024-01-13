package com.swdteam.common.init;

import com.swdteam.common.crafting.EngineeringTableRecipe;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.Ingredient;


@Deprecated
public class DMEngineeringTableRegistry
{
   public static List<EngineeringTableRecipe> DEPRECRATED_RECIPES = new ArrayList<>();

  public static List<EngineeringTableRecipe> getAllRecipes() {
     Minecraft mc = Minecraft.func_71410_x();
     if (mc != null && mc.field_71439_g != null) {
       List<EngineeringTableRecipe> list = mc.field_71439_g.world.func_199532_z().func_241447_a_(DMCraftingTypes.ENGINEERING_RECIPE);
       list.addAll(DEPRECRATED_RECIPES);
       return list;
    }

     return DEPRECRATED_RECIPES;
  }

  public static List<EngineeringTableRecipe> getRecipesFor(IInventory inventory) {
     List<Item> ingredients = new ArrayList<>();
     List<EngineeringTableRecipe> matches = new ArrayList<>();

     if (inventory.func_70301_a(0) == null || inventory.func_70301_a(1) == null || inventory.func_70301_a(2) == null) return matches;

     ingredients.add(inventory.func_70301_a(0).getItem());
     ingredients.add(inventory.func_70301_a(1).getItem());
     ingredients.add(inventory.func_70301_a(2).getItem());

     DEPRECRATED_RECIPES.forEach(recipe -> {
          List<Item> neededItems = new ArrayList<>();

          neededItems.add(((Ingredient)recipe.func_192400_c().get(0)).func_193365_a()[0].getItem());
          neededItems.add(((Ingredient)recipe.func_192400_c().get(1)).func_193365_a()[0].getItem());
          neededItems.add(((Ingredient)recipe.func_192400_c().get(2)).func_193365_a()[0].getItem());
          if (neededItems.containsAll(ingredients) && ingredients.containsAll(neededItems)) {
            matches.add(recipe);
          }
        });
     return matches;
  }
}


