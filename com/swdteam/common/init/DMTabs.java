package com.swdteam.common.init;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.NonNullList;

public class DMTabs {
   public static final ItemGroup DM_TARDIS = new ItemGroup("DM_Tardis")
    {
      public ItemStack func_78016_d()
      {
         if (DMBlocks.YELLOW_TERRACOTTA_ROUNDELS.isPresent()) {
           return new ItemStack((IItemProvider)DMBlocks.YELLOW_TERRACOTTA_ROUNDELS.get());
        }
         return ItemStack.field_190927_a;
      }

      
      public void func_78018_a(NonNullList<ItemStack> p_78018_1_) {
         super.func_78018_a(p_78018_1_);
      }
    };
  
   public static final ItemGroup DM_CLOTHES = new ItemGroup("DM_Clothes")
    {
      public ItemStack func_78016_d()
      {
         if (DMItems.RED_FEZ.isPresent()) {
           return new ItemStack((IItemProvider)DMItems.RED_FEZ.get());
        }
         return ItemStack.field_190927_a;
      }
    };
}


