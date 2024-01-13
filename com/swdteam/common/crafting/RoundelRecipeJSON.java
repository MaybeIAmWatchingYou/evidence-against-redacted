package com.swdteam.common.crafting;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.io.Serializable;
import java.util.stream.Stream;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;





@Deprecated
public class RoundelRecipeJSON
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String input_1;
  private String input_2;
  private RoundelOutput output;
  private transient ItemStack stackA;
  private transient ItemStack stackB;
  private transient NonNullList<Ingredient> ingredients;
  
  public NonNullList<Ingredient> getIngredients() {
     if (this.ingredients == null || this.ingredients.size() == 0) {
       this.ingredients = NonNullList.func_191196_a();
      
       this.ingredients.add(Ingredient.func_209357_a(Stream.of(new Ingredient.SingleItemList(getStackA()))));
       this.ingredients.add(Ingredient.func_209357_a(Stream.of(new Ingredient.SingleItemList(getStackB()))));
    } 
     return this.ingredients;
  }
  
  public ItemStack getStackA() {
     if (this.stackA == null) {
       this.stackA = new ItemStack((IItemProvider)getInputItemA());
    }
     return this.stackA;
  }
  
  public ItemStack getStackB() {
     if (this.stackB == null) {
       this.stackB = new ItemStack((IItemProvider)getInputItemB());
    }
     return this.stackB;
  }
  
  public ItemStack getOutput() {
     return this.output.getStack();
  }
  
  private Item getInputItemA() {
     return (Item)ForgeRegistries.ITEMS.getValue(new ResourceLocation(this.input_1));
  }
  
  private Item getInputItemB() {
     return (Item)ForgeRegistries.ITEMS.getValue(new ResourceLocation(this.input_2));
  }
  
  public static class RoundelOutput
    implements Serializable {
    private static final long serialVersionUID = 1L;
    private int amount;
    private String item;
    private String nbt;
    private transient ItemStack stack;
    
    public int getAmount() {
       return this.amount;
    }
    
    private Item getItem() {
       return (Item)ForgeRegistries.ITEMS.getValue(new ResourceLocation(this.item));
    }
    
    public ItemStack getStack() {
       if (this.stack == null) {
         this.stack = new ItemStack((IItemProvider)getItem(), this.amount);
         if (this.nbt != null) {
          try {
             CompoundNBT tag = JsonToNBT.getTagFromJson(this.nbt);
             this.stack.func_77982_d(tag);
           } catch (CommandSyntaxException e) {
             e.printStackTrace();
          } 
        }
      } 
       return this.stack;
    }
  }
}


