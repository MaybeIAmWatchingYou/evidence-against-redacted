package com.swdteam.common.container.slot;

import com.swdteam.common.tileentity.KerblamBoxTileEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class KerblamBoxSlot
  extends Slot
{
  public KerblamBoxSlot(IInventory inv, int slot, int x, int y) {
     super(inv, slot, x, y);
  }

  public boolean func_75214_a(ItemStack stack) {
     return KerblamBoxTileEntity.validItem(stack);
  }
}


