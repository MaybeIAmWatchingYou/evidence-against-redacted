package com.swdteam.common.tileentity.tardis;

import com.swdteam.common.container.FaultLocatorContainer;
import com.swdteam.common.init.DMBlockEntities;
import com.swdteam.common.init.DMNBTKeys;
import com.swdteam.common.init.DMTranslationKeys;
import com.swdteam.common.tileentity.DMTileEntityBase;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.text.ITextComponent;

public class FaultLocatorTileEntity extends DMTileEntityBase implements INamedContainerProvider {
   private ItemStack fuelSlot = ItemStack.field_190927_a;

  public FaultLocatorTileEntity() {
     super((TileEntityType)DMBlockEntities.TILE_FAULT_LOCATOR.get());
  }


  public void read(BlockState state, CompoundNBT compound) {
     if (compound.func_74764_b(DMNBTKeys.ITEM)) {
       this.fuelSlot = ItemStack.func_199557_a(compound.func_74775_l(DMNBTKeys.ITEM));
    }
     super.read(state, compound);
  }


  public CompoundNBT func_189515_b(CompoundNBT compound) {
     if (this.fuelSlot != null) {
       CompoundNBT tag = new CompoundNBT();
       this.fuelSlot.func_77955_b(tag);
       compound.func_218657_a(DMNBTKeys.ITEM, (INBT)tag);
    }
     return super.func_189515_b(compound);
  }

  public ItemStack getFuelSlot() {
     if (this.fuelSlot == null) {
       return ItemStack.field_190927_a;
    }
     return this.fuelSlot;
  }

  public void setFuelSlot(ItemStack fuelSlot) {
     this.fuelSlot = fuelSlot;
  }


  public Container createMenu(int id, PlayerInventory inv, PlayerEntity player) {
     return (Container)new FaultLocatorContainer(id, inv, this);
  }


  public ITextComponent func_145748_c_() {
     return (ITextComponent)DMTranslationKeys.GUI_FAULT_LOCATOR_NAME;
  }
}


