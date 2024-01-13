package com.swdteam.common.tileentity.tardis;

import com.swdteam.common.init.DMBlockEntities;
import com.swdteam.common.init.DMNBTKeys;
import com.swdteam.common.tileentity.DMTileEntityBase;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.tileentity.TileEntityType;

public class DataWriterTileEntity extends DMTileEntityBase {
  public DataWriterTileEntity() {
     super((TileEntityType)DMBlockEntities.TILE_DATA_WRITER.get());
  }


  public ItemStack cartridge;

  public void read(BlockState state, CompoundNBT compound) {
     if (compound.func_74764_b(DMNBTKeys.ITEM)) {
       this.cartridge = ItemStack.func_199557_a(compound.func_74775_l(DMNBTKeys.ITEM));
    }
     super.read(state, compound);
  }


  public CompoundNBT func_189515_b(CompoundNBT compound) {
     if (this.cartridge != null) {
       CompoundNBT tag = new CompoundNBT();
       this.cartridge.func_77955_b(tag);
       compound.func_218657_a(DMNBTKeys.ITEM, (INBT)tag);
    }
     return super.func_189515_b(compound);
  }
}


