package com.swdteam.common.tileentity.tardis;
import com.swdteam.common.block.tardis.RoundelDoorBlock;
import com.swdteam.common.init.DMBlockEntities;
import com.swdteam.common.init.DMBlocks;
import com.swdteam.common.init.DMNBTKeys;
import com.swdteam.common.init.DMTardis;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.common.tardis.TardisDoor;
import com.swdteam.common.tardis.actions.TardisActionList;
import com.swdteam.common.tileentity.DMTileEntityBase;
import com.swdteam.common.tileentity.TardisTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.state.Property;
import net.minecraft.state.properties.DoorHingeSide;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

public class RoundelDoorTileEntity extends DMTileEntityBase implements ITickableTileEntity {
   private BlockState blockSidesTop = Blocks.field_196581_bI.getDefaultState();
   private BlockState block = ((Block)DMBlocks.BLACK_QUARTZ_ROUNDEL.get()).getDefaultState();
  private boolean isOpen;
  private float doorRot;
  private boolean main;
  
  public RoundelDoorTileEntity() {
     super((TileEntityType)DMBlockEntities.TILE_ROUNDEL_DOOR.get());
  }
  
  public AxisAlignedBB getRenderBoundingBox() {
     return super.getRenderBoundingBox().grow(2.0D);
  }
   public boolean isMainDoor() { return this.main; }
   public boolean isOpen() { return this.isOpen; }
   public float getDoorRotation() { return this.doorRot; }
   public BlockState getStateToRender() { return this.block; } public BlockState getStateToRenderTop() {
     return this.blockSidesTop;
  }
  public void setMain(boolean isMain) {
     setMain(isMain, true);
  }
  
  public void setMain(boolean isMain, boolean updateOthers) {
     BlockPos tilePos = getPos();
     int doorPart = ((Integer)this.world.getBlockState(tilePos).get((Property)RoundelDoorBlock.DOOR_PART)).intValue();
    
     this.main = isMain;
    
     if (updateOthers) {
       if (doorPart == 0) {
         setOtherMain(tilePos.func_177984_a(), isMain);
         setOtherMain(tilePos.func_177984_a().func_177984_a(), isMain);
       } else if (doorPart == 1) {
         setOtherMain(tilePos.func_177984_a(), isMain);
         setOtherMain(tilePos.func_177977_b(), isMain);
      } else {
         setOtherMain(tilePos.func_177977_b(), isMain);
         setOtherMain(tilePos.func_177977_b().func_177977_b(), isMain);
      } 
    }
  }
  
  public void setOpen(boolean isOpen) {
     setOpen(isOpen, false);
  }
  
  public void forceDoorState(boolean isOpen) {
     this.isOpen = isOpen;
     this.doorRot = isOpen ? 1.0F : 0.0F;
    
     if (!this.world.isRemote) sendUpdates();
  }
  
  public void setBlock(BlockState block) {
     this.block = block;
     sendUpdates();
  }
  
  public void setBlockSidesTop(BlockState blockSidesTop) {
     this.blockSidesTop = blockSidesTop;
     sendUpdates();
  }
  
  public void setOpen(boolean isOpen, boolean ignoreDoorSync) {
     this.isOpen = isOpen;
     TardisDoor door = (func_195044_w().get((Property)RoundelDoorBlock.HINGE) == DoorHingeSide.LEFT) ? TardisDoor.LEFT : TardisDoor.RIGHT;
    
     if (!this.world.isRemote) {
       sendUpdates();
      
       if (!ignoreDoorSync) {
         TardisData data = DMTardis.getTardisFromInteriorPos(getPos());
         if (data != null) {
           BlockPos pos = data.getCurrentLocation().getBlockPosition();
           ServerWorld serverWorld = this.world.getServer().getWorld(data.getCurrentLocation().dimensionWorldKey());
           if (serverWorld != null) {
             TileEntity te = serverWorld.getTileEntity(pos);
            
             if (te instanceof TardisTileEntity) {
               TardisTileEntity tardis = (TardisTileEntity)te;
              
               if (TardisActionList.doAnimation(serverWorld, pos)) {
                 tardis.setDoor((door == TardisDoor.LEFT) ? TardisDoor.RIGHT : ((door == TardisDoor.RIGHT) ? TardisDoor.LEFT : door), isOpen, TardisTileEntity.DoorSource.INTERIOR);
              } else {
                 tardis.forceDoorState((door == TardisDoor.LEFT) ? TardisDoor.RIGHT : ((door == TardisDoor.RIGHT) ? TardisDoor.LEFT : door), isOpen);
              } 
            } 
          } 
        } 
      } 
    } 
  }

  
  public void tick() {
     if (this.isOpen) {
       if (this.doorRot < 1.0F) {
         double calcRotation = 0.05000000074505806D - this.doorRot * 0.05D;
         if (calcRotation < 0.009999999776482582D) calcRotation = 0.009999999776482582D;
         this.doorRot = (float)(this.doorRot + calcRotation);
      } 
       if (this.doorRot > 0.995D) this.doorRot = 1.0F;
    } else {
       if (this.doorRot > 0.0F) this.doorRot -= 0.05F;
       if (this.doorRot < 0.0F) this.doorRot = 0.0F;
    
    } 
  }
  
  public CompoundNBT func_189515_b(CompoundNBT compound) {
     compound.func_74757_a(DMNBTKeys.MAIN_DOOR, this.main);
     compound.func_74757_a(DMNBTKeys.DOOR_OPEN, this.isOpen);
     compound.func_74776_a(DMNBTKeys.DOOR_ROTATION, this.doorRot);
     compound.func_218657_a(DMNBTKeys.ROUNDEL_DOOR_BLOCK, (INBT)NBTUtil.func_190009_a(this.block));
     compound.func_218657_a(DMNBTKeys.ROUNDEL_DOOR_TOP, (INBT)NBTUtil.func_190009_a(this.blockSidesTop));
    
     return super.func_189515_b(compound);
  }

  
  public void read(BlockState state, CompoundNBT compound) {
     if (compound.func_74764_b(DMNBTKeys.MAIN_DOOR)) this.main = compound.func_74767_n(DMNBTKeys.MAIN_DOOR);
     if (compound.func_74764_b(DMNBTKeys.DOOR_OPEN)) this.isOpen = compound.func_74767_n(DMNBTKeys.DOOR_OPEN);
     if (compound.func_74764_b(DMNBTKeys.DOOR_ROTATION)) this.doorRot = compound.func_74760_g(DMNBTKeys.DOOR_ROTATION);
     if (compound.func_74764_b(DMNBTKeys.ROUNDEL_DOOR_BLOCK)) this.block = NBTUtil.func_190008_d(compound.func_74775_l(DMNBTKeys.ROUNDEL_DOOR_BLOCK));
     if (compound.func_74764_b(DMNBTKeys.ROUNDEL_DOOR_TOP)) this.blockSidesTop = NBTUtil.func_190008_d(compound.func_74775_l(DMNBTKeys.ROUNDEL_DOOR_TOP));
    
     super.read(state, compound);
  }
  
  private void setOtherMain(BlockPos pos, boolean isMain) {
     TileEntity tile = this.world.getTileEntity(pos);
     if (tile != null && tile instanceof RoundelDoorTileEntity)
       ((RoundelDoorTileEntity)this.world.getTileEntity(pos)).setMain(isMain, false);
  }
}


