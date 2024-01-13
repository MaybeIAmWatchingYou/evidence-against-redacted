package com.swdteam.util;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;

public class NBTUtil {
  public static CompoundNBT saveAllItems(CompoundNBT tag, Inventory inventory) {
     ListNBT listnbt = new ListNBT();

     for (int i = 0; i < inventory.func_70302_i_(); i++) {
       ItemStack itemstack = inventory.func_70301_a(i);
       if (!itemstack.func_190926_b()) {
         CompoundNBT compoundnbt = new CompoundNBT();
         compoundnbt.func_74774_a("Slot", (byte)i);
         itemstack.func_77955_b(compoundnbt);
         listnbt.add(compoundnbt);
      }
    }

     if (!listnbt.isEmpty()) {
       tag.func_218657_a("Items", (INBT)listnbt);
    }

     return tag;
  }
}


