package com.swdteam.common.tileentity;

import com.swdteam.common.init.DMBlockEntities;
import com.swdteam.common.init.DMNBTKeys;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.AxisAlignedBB;

public class ImageLoaderTileEntity
  extends ExtraRotationTileEntityBase
{
   private float scaleX = 1.0F;
   private float scaleY = 1.0F;
   private float offsetX = 0.0F;
   private float offsetY = 0.0F;
   private float offsetZ = 0.0F;
   private String imgName = "";
  private boolean doom;
  
  public ImageLoaderTileEntity() {
     super((TileEntityType)DMBlockEntities.TILE_IMAGE_LOADER.get());
  }

  
  public AxisAlignedBB getRenderBoundingBox() {
     return super.getRenderBoundingBox().func_72314_b(5.0D, 5.0D, 5.0D);
  }

  
  public CompoundNBT func_189515_b(CompoundNBT compound) {
     super.func_189515_b(compound);
     compound.func_74776_a(DMNBTKeys.IMG_LOADER_SCALE_X, this.scaleX);
     compound.func_74776_a(DMNBTKeys.IMG_LOADER_SCALE_Y, this.scaleY);
     compound.func_74776_a(DMNBTKeys.IMG_LOADER_OFFSET_X, this.offsetX);
     compound.func_74776_a(DMNBTKeys.IMG_LOADER_OFFSET_Y, this.offsetY);
     compound.func_74776_a(DMNBTKeys.IMG_LOADER_OFFSET_Z, this.offsetZ);
     compound.func_74778_a(DMNBTKeys.IMG_LOADER_IMAGE, this.imgName);
     compound.func_74757_a(DMNBTKeys.IMG_LOADER_DOOM, this.doom);
     return compound;
  }

  
  public void read(BlockState state, CompoundNBT compound) {
     if (compound.func_74764_b(DMNBTKeys.IMG_LOADER_SCALE_X)) {
       this.scaleX = compound.func_74760_g(DMNBTKeys.IMG_LOADER_SCALE_X);
    }
     if (compound.func_74764_b(DMNBTKeys.IMG_LOADER_SCALE_Y)) {
       this.scaleY = compound.func_74760_g(DMNBTKeys.IMG_LOADER_SCALE_Y);
    }
     if (compound.func_74764_b(DMNBTKeys.IMG_LOADER_OFFSET_X)) {
       this.offsetX = compound.func_74760_g(DMNBTKeys.IMG_LOADER_OFFSET_X);
    }
     if (compound.func_74764_b(DMNBTKeys.IMG_LOADER_OFFSET_Y)) {
       this.offsetY = compound.func_74760_g(DMNBTKeys.IMG_LOADER_OFFSET_Y);
    }
     if (compound.func_74764_b(DMNBTKeys.IMG_LOADER_OFFSET_Z)) {
       this.offsetZ = compound.func_74760_g(DMNBTKeys.IMG_LOADER_OFFSET_Z);
    }
     if (compound.func_74764_b(DMNBTKeys.IMG_LOADER_IMAGE)) {
       this.imgName = compound.func_74779_i(DMNBTKeys.IMG_LOADER_IMAGE);
    }
     if (compound.func_74764_b(DMNBTKeys.IMG_LOADER_DOOM)) {
       this.doom = compound.func_74767_n(DMNBTKeys.IMG_LOADER_DOOM);
    }
     super.read(state, compound);
  }




  
  @Nullable
  public SUpdateTileEntityPacket func_189518_D_() {
     return new SUpdateTileEntityPacket(getPos(), 4, func_189517_E_());
  }




  
  public CompoundNBT func_189517_E_() {
     return func_189515_b(new CompoundNBT());
  }
  
  public float getScaleX() {
     return this.scaleX;
  }
  
  public float getScaleY() {
     return this.scaleY;
  }
  
  public float getOffsetX() {
     return this.offsetX;
  }
  
  public float getOffsetY() {
     return this.offsetY;
  }
  
  public float getOffsetZ() {
     return this.offsetZ;
  }
  
  public String getImgName() {
     return this.imgName;
  }
  
  public void setImgName(String imgName) {
     this.imgName = imgName;
     sendUpdates();
  }
  
  public boolean isDoom() {
     return this.doom;
  }
}


