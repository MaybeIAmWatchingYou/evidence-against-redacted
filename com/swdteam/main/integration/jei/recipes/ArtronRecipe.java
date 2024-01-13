package com.swdteam.main.integration.jei.recipes;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ArtronRecipe
  implements IRecipe<IInventory>
{
  private final ResourceLocation id;
  private final ItemStack result;

  public ArtronRecipe(ResourceLocation id, ItemStack result) {
     this.id = id;
     this.result = result;
  }

  public static ArtronRecipe create(ItemStack item) {
     ResourceLocation recipeId = new ResourceLocation("dalekmod", "generated_artron_" + item.getItem().getRegistryName().getPath());
     return new ArtronRecipe(recipeId, item);
  }

   public boolean func_192399_d() { return true; }
   public NonNullList<Ingredient> func_192400_c() { return null; }
   public boolean func_77569_a(IInventory inventory, World world) { return false; }
   public boolean func_194133_a(int xSlots, int ySlots) { return false; }
   public ItemStack func_77572_b(IInventory inventory) { return this.result.func_77946_l(); }
   public ItemStack func_77571_b() { return this.result; }
   public ResourceLocation func_199560_c() { return this.id; }
   public IRecipeSerializer<?> func_199559_b() { return null; } public IRecipeType<?> func_222127_g() {
     return null;
  }
}


