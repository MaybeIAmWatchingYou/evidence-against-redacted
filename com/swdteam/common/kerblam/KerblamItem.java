package com.swdteam.common.kerblam;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.io.Serializable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;




public class KerblamItem
  implements Serializable
{
  private static final long serialVersionUID = 3694531711678010673L;
  private transient ResourceLocation resourceKey;
  private String item;
  private int restock_interval;
  private int price;
  private String nbt;
  private String category;
  private transient ItemStack itemStack;

  public String getItem() {
     return this.item;
  }

  public int getPrice() {
     return this.price;
  }

  public int getRestockInterval() {
     return this.restock_interval;
  }

  public ItemStack getItemStack() {
     if (this.itemStack == null) {
       this.itemStack = new ItemStack((IItemProvider)ForgeRegistries.ITEMS.getValue(new ResourceLocation(this.item)));
       if (this.nbt != null) {
        try {
           CompoundNBT tag = JsonToNBT.getTagFromJson(this.nbt);
           this.itemStack.func_77982_d(tag);
         } catch (CommandSyntaxException e) {
           e.printStackTrace();
        }
      }
    }
     return this.itemStack;
  }

  public String getCategory() {
     return this.category;
  }

  public ResourceLocation getResourceKey() {
     return this.resourceKey;
  }

  public void setResourceKey(ResourceLocation resourceKey) {
     this.resourceKey = resourceKey;
  }
}


