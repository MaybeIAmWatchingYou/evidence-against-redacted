package com.swdteam.common.tileentity.tardis;

import com.swdteam.common.init.DMBlocks;
import com.swdteam.common.init.DMNBTKeys;
import com.swdteam.common.init.DMTardis;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.common.tardis.TardisDoor;
import com.swdteam.common.tardis.actions.TardisActionList;
import com.swdteam.common.tileentity.DMTileEntityBase;
import com.swdteam.common.tileentity.TardisTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;


public abstract class DoubleDoorsTileEntity
  extends DMTileEntityBase
  implements ITickableTileEntity, ITardisDoubleDoors
{
  private boolean leftOpen;
  private boolean rightOpen;
  public float leftRotPcent;
  public float rightRotPcent;
  private boolean main;
  
  public DoubleDoorsTileEntity(TileEntityType<?> type) {
     super(type);
  }

  
  public AxisAlignedBB getRenderBoundingBox() {
     return super.getRenderBoundingBox().func_72314_b(1.5D, 3.0D, 1.5D);
  }
  public boolean isMainDoor() {
     return this.main;
  }
  public boolean isOpen(TardisDoor door) {
     return (door == TardisDoor.BOTH) ? ((this.leftOpen || this.rightOpen)) : ((door == TardisDoor.LEFT) ? this.leftOpen : this.rightOpen);
  }
  
  public void setMain(boolean isMain) {
     this.main = isMain;
  }
  
  public void setOpen(TardisDoor door, boolean isOpen) {
     setOpen(door, isOpen, false);
  }
  
  public void setOpen(TardisDoor door, boolean isOpen, boolean ignoreDoorSync) {
     switch (door) {
      case BOTH:
         this.leftOpen = isOpen;
         this.rightOpen = isOpen; break;
      case LEFT:
         this.leftOpen = isOpen; break;
       case RIGHT: this.rightOpen = isOpen;
        break;
    } 
     if (!this.world.isRemote) {
       this.world.playSound(null, getPos(), getDoorSound(door, isOpen), SoundCategory.BLOCKS, 0.5F, 1.0F);
       sendUpdates();
       if (!ignoreDoorSync) {
         TardisData data = DMTardis.getTardisFromInteriorPos(getPos());
         if (data != null) {
           BlockPos pos = data.getCurrentLocation().getBlockPosition();
           ServerWorld serverWorld = this.world.getServer().getWorld(data.getCurrentLocation().dimensionWorldKey());
           if (serverWorld != null) {
             BlockState state = serverWorld.getBlockState(pos);
             TileEntity te = serverWorld.getTileEntity(pos);
            
             if (state.getBlock() == DMBlocks.TARDIS.get() && te instanceof TardisTileEntity) {
               if (TardisActionList.doAnimation(serverWorld, pos)) {
                 TardisTileEntity tardis = (TardisTileEntity)te;
                 tardis.setDoor((door == TardisDoor.LEFT) ? TardisDoor.RIGHT : ((door == TardisDoor.RIGHT) ? TardisDoor.LEFT : door), isOpen, TardisTileEntity.DoorSource.INTERIOR);
              } else {
                 TardisTileEntity tardis = (TardisTileEntity)te;
                 tardis.forceDoorState((door == TardisDoor.LEFT) ? TardisDoor.RIGHT : ((door == TardisDoor.RIGHT) ? TardisDoor.LEFT : door), isOpen);
              } 
            }
          } 
        } 
      } 
    } 
  }


  
  public CompoundNBT func_189515_b(CompoundNBT compound) {
     compound.func_74757_a(DMNBTKeys.MAIN_DOOR, this.main);
     compound.func_74757_a(DMNBTKeys.DOOR_OPEN_LEFT, this.leftOpen);
     compound.func_74757_a(DMNBTKeys.DOOR_OPEN_RIGHT, this.rightOpen);
     compound.func_74776_a(DMNBTKeys.DOOR_ROTATION_LEFT, this.leftRotPcent);
     compound.func_74776_a(DMNBTKeys.DOOR_ROTATION_RIGHT, this.rightRotPcent);
    
     return super.func_189515_b(compound);
  }

  
  public void read(BlockState state, CompoundNBT compound) {
     if (compound.func_74764_b(DMNBTKeys.MAIN_DOOR)) this.main = compound.func_74767_n(DMNBTKeys.MAIN_DOOR); 
     if (compound.func_74764_b(DMNBTKeys.DOOR_OPEN_LEFT)) this.leftOpen = compound.func_74767_n(DMNBTKeys.DOOR_OPEN_LEFT); 
     if (compound.func_74764_b(DMNBTKeys.DOOR_OPEN_RIGHT)) this.rightOpen = compound.func_74767_n(DMNBTKeys.DOOR_OPEN_RIGHT); 
     if (compound.func_74764_b(DMNBTKeys.DOOR_ROTATION_LEFT)) this.leftRotPcent = compound.func_74760_g(DMNBTKeys.DOOR_ROTATION_LEFT); 
     if (compound.func_74764_b(DMNBTKeys.DOOR_ROTATION_RIGHT)) this.rightRotPcent = compound.func_74760_g(DMNBTKeys.DOOR_ROTATION_RIGHT);
    
     super.read(state, compound);
  }

  
  public void tick() {
     this.leftRotPcent = calculateRotation(this.leftRotPcent, this.leftOpen);
     this.rightRotPcent = calculateRotation(this.rightRotPcent, this.rightOpen);
  }
  
  public float calculateRotation(float currentRotation, boolean opening) {
     if (opening) {
       if (currentRotation < 1.0F) currentRotation = (float)(currentRotation + 0.10000000149011612D - currentRotation * 0.1D); 
       if (currentRotation > 1.0F) currentRotation = 1.0F; 
    } else {
       if (currentRotation > 0.0F) currentRotation -= 0.2F; 
       if (currentRotation < 0.0F) currentRotation = 0.0F;
    
    } 
     return currentRotation;
  }
  
  public void forceDoorState(TardisDoor door, boolean isOpen) {
     switch (door) {
      case BOTH:
         this.leftOpen = isOpen;
         this.rightOpen = isOpen;
         this.leftRotPcent = isOpen ? 1.0F : 0.0F;
         this.rightRotPcent = isOpen ? 1.0F : 0.0F;
        break;
      case LEFT:
         this.leftOpen = isOpen;
         this.leftRotPcent = isOpen ? 1.0F : 0.0F;
        break;
      case RIGHT:
         this.rightOpen = isOpen;
         this.rightRotPcent = isOpen ? 1.0F : 0.0F;
        break;
    } 
    
     if (!this.world.isRemote) sendUpdates(); 
  }
}


