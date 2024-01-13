package com.swdteam.common.tileentity;

import com.mojang.authlib.GameProfile;
import com.swdteam.common.block.HologramBlock;
import com.swdteam.common.init.DMBlockEntities;
import com.swdteam.common.init.DMBlocks;
import com.swdteam.common.init.DMNBTKeys;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.state.Property;
import net.minecraft.tileentity.SkullTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;





public class HologramTileEntity
  extends ExtraRotationTileEntityBase
{
  @Nullable
  private GameProfile owner;
  private UUID lockedBy;
  public boolean isSolid = false;
  public boolean isAnimated = true;
   public float scale = 1.0F;
  public boolean smallArms = false;
  
  public HologramTileEntity() {
     super((TileEntityType)DMBlockEntities.TILE_HOLOGRAM.get());
  }

  
  public AxisAlignedBB getRenderBoundingBox() {
     return super.getRenderBoundingBox().grow(3.0D);
  }
  
  public float getScale() {
     return this.scale;
  }
  
  public void setScale(float scale) {
     this.scale = scale;
  }
  
  public void setSmallArms(boolean has_small_arms) {
     this.smallArms = has_small_arms;
  }
  
  public boolean hasSmallArms() {
     return this.smallArms;
  }
  
  public boolean hasBase() {
     BlockState state = func_195044_w();
     if (state.getBlock() == DMBlocks.HOLOGRAM.get()) {
       return ((Boolean)state.get((Property)HologramBlock.HAS_BASE)).booleanValue();
    }
     return false;
  }


  
  public void onLoad() {}


  
  public CompoundNBT func_189515_b(CompoundNBT compound) {
     super.func_189515_b(compound);
     compound.func_74776_a(DMNBTKeys.SCALE, this.scale);
     compound.func_74757_a(DMNBTKeys.HOLO_SOLID, this.isSolid);
     compound.func_74757_a(DMNBTKeys.HOLO_ANIMATED, this.isAnimated);
     compound.func_74757_a(DMNBTKeys.HOLO_SMALL_ARMS, this.smallArms);
     compound.func_74776_a(DMNBTKeys.ROTATION, this.rotation);
    
     if (this.lockedBy != null) {
       compound.func_186854_a(DMNBTKeys.HOLO_LOCKED_BY, this.lockedBy);
     } else if (compound.func_74764_b(DMNBTKeys.HOLO_LOCKED_BY)) {
       compound.func_82580_o(DMNBTKeys.HOLO_LOCKED_BY);
    } 
    
     if (this.owner != null) {
       CompoundNBT compoundnbt = new CompoundNBT();
       NBTUtil.func_180708_a(compoundnbt, this.owner);
       compound.func_218657_a(DMNBTKeys.HOLO_OWNER, (INBT)compoundnbt);
    } 
    
     return compound;
  }

  
  public void read(BlockState state, CompoundNBT compound) {
     if (compound.func_74764_b(DMNBTKeys.SCALE)) this.scale = compound.func_74760_g(DMNBTKeys.SCALE);
     if (compound.func_74764_b(DMNBTKeys.HOLO_SOLID)) this.isSolid = compound.func_74767_n(DMNBTKeys.HOLO_SOLID);
     if (compound.func_74764_b(DMNBTKeys.HOLO_ANIMATED)) this.isAnimated = compound.func_74767_n(DMNBTKeys.HOLO_ANIMATED);
     if (compound.func_74764_b(DMNBTKeys.HOLO_SMALL_ARMS)) this.smallArms = compound.func_74767_n(DMNBTKeys.HOLO_SMALL_ARMS);
     if (compound.func_74764_b(DMNBTKeys.HOLO_LOCKED_BY)) { this.lockedBy = compound.func_186857_a(DMNBTKeys.HOLO_LOCKED_BY); }
     else { this.lockedBy = null; }
      if (compound.func_74764_b(DMNBTKeys.ROTATION)) this.rotation = compound.func_74760_g(DMNBTKeys.ROTATION);
     if (compound.func_150297_b(DMNBTKeys.HOLO_OWNER, 10)) setOwner(NBTUtil.func_152459_a(compound.func_74775_l(DMNBTKeys.HOLO_OWNER)));
    
     super.read(state, compound);
  }
  
  @Nullable
  @OnlyIn(Dist.CLIENT)
  public GameProfile getOwnerProfile() {
     return this.owner;
  }
  
  public UUID getLockedBy() {
     return this.lockedBy;
  }
  
  public boolean isLocked() {
     return (this.lockedBy != null);
  }
  
  public void setLockedBy(UUID lockedBy) {
     this.lockedBy = lockedBy;
  }
  
  public void setOwner(@Nullable GameProfile p_195485_1_) {
     this.owner = p_195485_1_;
     updateOwnerProfile();
  }
  
  private void updateOwnerProfile() {
     this.owner = SkullTileEntity.func_174884_b(this.owner);
     markDirty();
  }






  
  @Nullable
  public SUpdateTileEntityPacket func_189518_D_() {
     return new SUpdateTileEntityPacket(getPos(), 4, func_189517_E_());
  }






  
  public CompoundNBT func_189517_E_() {
     return func_189515_b(new CompoundNBT());
  }
}


