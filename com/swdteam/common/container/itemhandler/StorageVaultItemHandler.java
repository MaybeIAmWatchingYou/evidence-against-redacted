package com.swdteam.common.container.itemhandler;

import com.swdteam.common.block.StorageVaultBlock;
import com.swdteam.common.tileentity.StorageVaultTileEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.state.Property;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.InvWrapper;

public class StorageVaultItemHandler
  extends InvWrapper implements IItemHandlerModifiable {
  public StorageVaultItemHandler(IInventory inv) {
     super(inv);
  }

  
  public ItemStack extractItem(int slot, int amount, boolean simulate) {
     if (getInv() instanceof StorageVaultTileEntity) {
       StorageVaultTileEntity tile = (StorageVaultTileEntity)getInv();
       boolean powered = ((Boolean)tile.func_195044_w().get((Property)StorageVaultBlock.POWERED)).booleanValue();
       if (!powered) return ItemStack.field_190927_a; 
    } 
     return super.extractItem(slot, amount, simulate);
  }
}


