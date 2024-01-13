package com.swdteam.common.crafting;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.swdteam.common.init.DMCraftingTypes;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraftforge.common.util.RecipeMatcher;
import net.minecraftforge.registries.ForgeRegistryEntry;


public class RoundelBuilderRecipe
  implements IRecipe<IInventory>
{
   public static final ResourceLocation TYPE_ID = new ResourceLocation("dalekmod", "roundel_builder");
  
  private final NonNullList<Ingredient> ingredients;
  private final ItemStack result;
  private final ResourceLocation id;
  
  public RoundelBuilderRecipe(ResourceLocation id, NonNullList<Ingredient> ingredients, ItemStack result) {
     this.id = id;
     this.ingredients = ingredients;
     this.result = result;
  }
  public boolean func_192399_d() {
     return true;
  }
  public NonNullList<Ingredient> func_192400_c() {
     return this.ingredients;
  }
  
  public boolean func_77569_a(IInventory inventory, World world) {
     List<ItemStack> inputs = new ArrayList<>();
     for (int i = 0; i < 2; i++) {
       ItemStack itemstack = inventory.func_70301_a(i);
       if (itemstack.func_190926_b()) return false; 
       inputs.add(itemstack);
    } 
    
     return (RecipeMatcher.findMatches(inputs, (List)this.ingredients) != null);
  }

  
  public ItemStack func_77572_b(IInventory inventory) {
     return this.result.func_77946_l();
  }

  
  public boolean func_194133_a(int xSlots, int ySlots) {
     return (xSlots * ySlots == 2);
  }
  
  public ItemStack func_77571_b() {
     return this.result;
  }
  public ResourceLocation func_199560_c() {
     return this.id;
  }
  
  public IRecipeSerializer<?> func_199559_b() {
     return (IRecipeSerializer)DMCraftingTypes.ROUNDEL_SERIALIZER.get();
  }

  
  public IRecipeType<?> func_222127_g() {
     return Registry.field_218367_H.func_241873_b(TYPE_ID).get();
  }
  
  public static class RoundelBuilderRecipeType implements IRecipeType<RoundelBuilderRecipe> {
    public String toString() {
       return RoundelBuilderRecipe.TYPE_ID.toString();
    } }
  
  public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<RoundelBuilderRecipe> {
    public RoundelBuilderRecipe fromJson(ResourceLocation location, JsonObject json) {
       NonNullList<Ingredient> nonnulllist = itemsFromJson(JSONUtils.func_151214_t(json, "ingredients"));
      
       if (nonnulllist.isEmpty())
         throw new JsonParseException("No ingredients for engineering table recipe"); 
       if (nonnulllist.size() > 2) {
         throw new JsonParseException("Too many ingredients for roundel builder recipe the max is 2");
      }
       ItemStack itemstack = ShapedRecipe.func_199798_a(JSONUtils.func_152754_s(json, "result"));
       return new RoundelBuilderRecipe(location, nonnulllist, itemstack);
    }

    
    private static NonNullList<Ingredient> itemsFromJson(JsonArray p_199568_0_) {
       NonNullList<Ingredient> nonnulllist = NonNullList.func_191196_a();
      
       for (int i = 0; i < p_199568_0_.size(); i++) {
         Ingredient ingredient = Ingredient.func_199802_a(p_199568_0_.get(i));
         if (!ingredient.func_203189_d()) {
           nonnulllist.add(ingredient);
        }
      } 
      
       return nonnulllist;
    }
    
    public RoundelBuilderRecipe fromNetwork(ResourceLocation location, PacketBuffer buffer) {
       int i = buffer.func_150792_a();
       NonNullList<Ingredient> nonnulllist = NonNullList.func_191197_a(i, Ingredient.field_193370_a);
      
       for (int j = 0; j < nonnulllist.size(); j++) {
         nonnulllist.set(j, Ingredient.func_199566_b(buffer));
      }
      
       ItemStack itemstack = buffer.func_150791_c();
       return new RoundelBuilderRecipe(location, nonnulllist, itemstack);
    }
    
    public void toNetwork(PacketBuffer buffer, RoundelBuilderRecipe recipe) {
       buffer.func_150787_b(recipe.ingredients.size());
      
       for (Ingredient ingredient : recipe.ingredients) {
         ingredient.func_199564_a(buffer);
      }
      
       buffer.func_150788_a(recipe.result);
    }
  }
}


