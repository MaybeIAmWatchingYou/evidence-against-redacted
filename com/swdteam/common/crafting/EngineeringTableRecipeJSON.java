package com.swdteam.common.crafting;

import java.util.stream.Stream;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;



@Deprecated
public class EngineeringTableRecipeJSON
{
  private String input_1;
  private String input_2;
  private String input_3;
  private String output;
  private transient ItemStack stackA;
  private transient ItemStack stackB;
  private transient ItemStack stackC;
  private transient ItemStack stackOutput;
  private transient ResourceLocation[] items;
  private transient NonNullList<Ingredient> ingredients;

  public NonNullList<Ingredient> getIngredients() {
     if (this.ingredients == null || this.ingredients.size() == 0) {
       this.ingredients = NonNullList.func_191196_a();

       this.ingredients.add(Ingredient.func_209357_a(Stream.of(new Ingredient.SingleItemList(getStackA()))));
       this.ingredients.add(Ingredient.func_209357_a(Stream.of(new Ingredient.SingleItemList(getStackB()))));
       this.ingredients.add(Ingredient.func_209357_a(Stream.of(new Ingredient.SingleItemList(getStackC()))));
    }
     return this.ingredients;
  }

  public ResourceLocation[] getItems() {
     if (this.items == null) {
       this.items = new ResourceLocation[] { getStackA().getItem().getRegistryName(), getStackB().getItem().getRegistryName(), getStackC().getItem().getRegistryName() };
    }
     return this.items;
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

  public ItemStack getStackC() {
     if (this.stackC == null) {
       this.stackC = new ItemStack((IItemProvider)getInputItemC());
    }
     return this.stackC;
  }

  public ItemStack getOutput() {
     if (this.stackOutput == null) {
       this.stackOutput = new ItemStack((IItemProvider)getOutputItem());
    }
     return this.stackOutput;
  }

  private Item getInputItemA() {
     return (Item)ForgeRegistries.ITEMS.getValue(new ResourceLocation(this.input_1));
  }

  private Item getInputItemB() {
     return (Item)ForgeRegistries.ITEMS.getValue(new ResourceLocation(this.input_2));
  }

  private Item getInputItemC() {
     return (Item)ForgeRegistries.ITEMS.getValue(new ResourceLocation(this.input_3));
  }

  private Item getOutputItem() {
     return (Item)ForgeRegistries.ITEMS.getValue(new ResourceLocation(this.output));
  }
}


