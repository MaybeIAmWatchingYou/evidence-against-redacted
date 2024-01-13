package com.swdteam.util;

import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;

public class ItemStackHelper
{
  public static CompoundNBT saveAllItems(CompoundNBT p_191281_0_, List<ItemStack> p_191281_1_, boolean p_191281_2_) {
     ListNBT listnbt = new ListNBT();
    
     for (int i = 0; i < p_191281_1_.size(); i++) {
       ItemStack itemstack = p_191281_1_.get(i);
       if (!itemstack.func_190926_b()) {
         CompoundNBT compoundnbt = new CompoundNBT();
         compoundnbt.func_74774_a("Slot", (byte)i);
         itemstack.func_77955_b(compoundnbt);
         listnbt.add(compoundnbt);
      } 
    } 
    
     if (!listnbt.isEmpty() || p_191281_2_) {
       p_191281_0_.func_218657_a("Items", (INBT)listnbt);
    }
    
     return p_191281_0_;
  }
  
  public static void loadAllItems(CompoundNBT p_191283_0_, List<ItemStack> p_191283_1_) {
     ListNBT listnbt = p_191283_0_.func_150295_c("Items", 10);
     for (int i = 0; i < listnbt.size(); i++) {
       CompoundNBT compoundnbt = listnbt.func_150305_b(i);
       int j = compoundnbt.func_74771_c("Slot") & 0xFF;
       if (j >= 0)
         p_191283_1_.add(ItemStack.func_199557_a(compoundnbt)); 
    } 
  }
}


