package com.swdteam.common.tileentity;

import com.swdteam.common.init.DMNBTKeys;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;

public class ExtraRotationTileEntityBase
  extends DMTileEntityBase {
   public float rotation = 0.0F;
  
  public ExtraRotationTileEntityBase(TileEntityType<?> tileEntityTypeIn) {
     super(tileEntityTypeIn);
  }
  
  public void setRotation(float rotation) {
     this.rotation = rotation;
  }
  
  public float getRotation() {
     return this.rotation;
  }

  
  public CompoundNBT func_189515_b(CompoundNBT compound) {
     super.func_189515_b(compound);
     compound.func_74776_a(DMNBTKeys.ROTATION, this.rotation);
    
     return compound;
  }

  
  public void read(BlockState state, CompoundNBT compound) {
     this.rotation = compound.func_74764_b(DMNBTKeys.ROTATION) ? compound.func_74760_g(DMNBTKeys.ROTATION) : 0.0F;
    
     super.read(state, compound);
  }
}


