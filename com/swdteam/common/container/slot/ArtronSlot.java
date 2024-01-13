package com.swdteam.common.container.slot;

import com.swdteam.common.init.DMTags;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tags.ITag;

public class ArtronSlot
  extends Slot {
  public ArtronSlot(IInventory inv, int id, int x, int y) {
     super(inv, id, x, y);
  }


  public boolean func_75214_a(ItemStack stack) {
     return isValid(stack);
  }

  public static boolean isValid(ItemStack stack) {
     return (stack.getItem().func_206844_a((ITag)DMTags.Items.ARTRON) || stack.getItem() == Items.field_151069_bo);
  }


  public int func_75219_a() {
     return 1;
  }
}


