package com.swdteam.common.tileentity;

import com.swdteam.common.block.tardis.PoliceBoxDoorBlock;
import com.swdteam.common.block.tardis.RoundelDoorBlock;
import com.swdteam.common.init.DMBlockEntities;
import com.swdteam.common.init.DMBlocks;
import com.swdteam.common.init.DMDimensions;
import com.swdteam.common.init.DMFlightMode;
import com.swdteam.common.init.DMNBTKeys;
import com.swdteam.common.init.DMSoundEvents;
import com.swdteam.common.init.DMTardis;
import com.swdteam.common.tardis.Location;
import com.swdteam.common.tardis.Tardis;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.common.tardis.TardisDoor;
import com.swdteam.common.tardis.TardisState;
import com.swdteam.common.tardis.actions.TardisActionList;
import com.swdteam.common.teleport.TeleportRequest;
import com.swdteam.common.tileentity.tardis.DoubleDoorsTileEntity;
import com.swdteam.common.tileentity.tardis.RoundelDoorTileEntity;
import com.swdteam.util.SWDMathUtils;
import com.swdteam.util.TeleportUtil;
import com.swdteam.util.WorldUtils;
import com.swdteam.util.math.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.Property;
import net.minecraft.state.properties.DoorHingeSide;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class TardisTileEntity
  extends ExtraRotationTileEntityBase implements ITickableTileEntity {
  boolean demat;
   public float pulses = 0.0F;
   public float bob = 1.0F;
   public float boblast = 1.0F;
   public int bobTime = 0;
   public static AxisAlignedBB defaultAABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 2.0D, 1.0D);
  public boolean doorOpenLeft = false;
  public boolean doorOpenRight = false;
  public float doorLeftRotation;
  public float doorRightRotation;
   public float dematTime = 1.0F;
  
  public boolean isSnowy = false;
   public TardisState state = TardisState.REMAT;
  
   public int globalID = 0;
  public TardisData tardisData; public long animStartTime; public long lastTickTime; public CompoundNBT func_189515_b(CompoundNBT compound) { compound.putInt(DMNBTKeys.TARDIS_ID, this.globalID); compound.func_74757_a(DMNBTKeys.DOOR_OPEN_LEFT, this.doorOpenLeft); compound.func_74757_a(DMNBTKeys.DOOR_OPEN_RIGHT, this.doorOpenRight); compound.func_74776_a(DMNBTKeys.DOOR_ROTATION_LEFT, this.doorLeftRotation); compound.func_74776_a(DMNBTKeys.DOOR_ROTATION_RIGHT, this.doorRightRotation); compound.func_74776_a(DMNBTKeys.TARDIS_DEMAT_TIME, this.dematTime);
    compound.func_74778_a(DMNBTKeys.TARDIS_STATE, this.state.toString());
    compound.func_74776_a(DMNBTKeys.TARDIS_PULSES, this.pulses);
    compound.func_74757_a(DMNBTKeys.TARDIS_DEMAT, this.demat);
     return super.func_189515_b(compound); } public TardisTileEntity() { super((TileEntityType)DMBlockEntities.TILE_TARDIS.get());























































































    
     this.animStartTime = 0L;
    
     this.lastTickTime = System.currentTimeMillis(); this.dematTime = 0.0F; }
  public void read(BlockState blockstate, CompoundNBT compound) { if (compound.func_74764_b(DMNBTKeys.TARDIS_ID)) { this.globalID = compound.getInt(DMNBTKeys.TARDIS_ID); if (this.world != null && !this.world.isRemote) this.tardisData = DMTardis.getTardis(this.globalID);  if (this.tardisData == null || !this.tardisData.getCurrentLocation().getPosition().equals(getPos())); } else { this.world.setBlockState(getPos(), Blocks.AIR.getDefaultState()); }  if (compound.func_74764_b(DMNBTKeys.DOOR_OPEN_LEFT)) this.doorOpenLeft = compound.func_74767_n(DMNBTKeys.DOOR_OPEN_LEFT);  if (compound.func_74764_b(DMNBTKeys.DOOR_OPEN_RIGHT)) this.doorOpenRight = compound.func_74767_n(DMNBTKeys.DOOR_OPEN_RIGHT);  if (compound.func_74764_b(DMNBTKeys.DOOR_ROTATION_LEFT)) this.doorLeftRotation = compound.func_74760_g(DMNBTKeys.DOOR_ROTATION_LEFT);  if (compound.func_74764_b(DMNBTKeys.DOOR_ROTATION_RIGHT)) this.doorRightRotation = compound.func_74760_g(DMNBTKeys.DOOR_ROTATION_RIGHT);  if (compound.func_74764_b(DMNBTKeys.TARDIS_DEMAT_TIME)) this.dematTime = compound.func_74760_g(DMNBTKeys.TARDIS_DEMAT_TIME);  if (compound.func_74764_b(DMNBTKeys.TARDIS_PULSES)) this.pulses = compound.func_74760_g(DMNBTKeys.TARDIS_PULSES);  if (compound.func_74764_b(DMNBTKeys.TARDIS_DEMAT))
      this.demat = compound.func_74767_n(DMNBTKeys.TARDIS_DEMAT);  if (compound.func_74764_b(DMNBTKeys.TARDIS_STATE))
       try { this.state = TardisState.valueOf(compound.func_74779_i(DMNBTKeys.TARDIS_STATE)); } catch (Exception e) { this.state = TardisState.NEUTRAL; }   super.read(blockstate, compound); } private boolean isSnowyBlock(BlockPos pos, boolean layer) { if (layer) {
       return (this.world.getBlockState(pos).func_185904_a() == Material.field_151597_y);
    }
     return (this.world.getBlockState(pos.func_177977_b()).func_185904_a() == Material.field_151596_z); } public void onLoad() { super.onLoad();
    if (this.world.isRemote)
      snowCheck();  }
  public AxisAlignedBB getRenderBoundingBox() { return super.getRenderBoundingBox().func_72314_b(2.0D, 5.0D, 2.0D); }
  public void snowCheck() {
     if (isSnowyBlock(getPos(), false) || ((
       isSnowyBlock(getPos().func_177978_c(), true) || isSnowyBlock(getPos().func_177978_c(), false)) && (
       isSnowyBlock(getPos().func_177968_d(), true) || isSnowyBlock(getPos().func_177968_d(), false)) && (
       isSnowyBlock(getPos().func_177974_f(), true) || isSnowyBlock(getPos().func_177974_f(), false)) && (
       isSnowyBlock(getPos().func_177976_e(), true) || isSnowyBlock(getPos().func_177976_e(), false))))
    {
       this.isSnowy = true;
    }
  }


  
  public void tick() {
     if (this.world.isRemote && 
       this.world.rand.nextInt(100) == 50) {
       snowCheck();
    }

    
     doorAnimation();
     long tickTime = System.currentTimeMillis() - this.lastTickTime;
     this.lastTickTime = System.currentTimeMillis();

    
     if (this.state == TardisState.DEMAT) {
       this.demat = true;
       if (this.animStartTime == 0L) {
         this.animStartTime = System.currentTimeMillis();
      }
       if (tickTime > 100L) {
         this.animStartTime += tickTime;
      }
       this.dematTime = (float)((System.currentTimeMillis() - this.animStartTime) / 10000.0D);
      
       if (this.dematTime >= 1.0F) {
         this.dematTime = 1.0F;
      }
       if (this.dematTime == 1.0F) {
        
         this.world.setBlockState(getPos(), Blocks.AIR.getDefaultState());
         this.animStartTime = 0L;
      } 
     } else if (this.state == TardisState.REMAT) {
       this.demat = false;
      
       if (this.animStartTime == 0L) {
         this.animStartTime = System.currentTimeMillis();
      }
       if (tickTime > 100L) {
         this.animStartTime += tickTime;
      }
       if (System.currentTimeMillis() - this.animStartTime > 9000L) {
         this.dematTime = 1.0F - (float)((System.currentTimeMillis() - this.animStartTime + 9000L) / 10000.0D);
      }
       if (this.dematTime <= 0.0F) {
         this.dematTime = 0.0F;
      }
       if (this.dematTime == 0.0F) {
        
         setState(TardisState.NEUTRAL);
         this.animStartTime = 0L;
      } 
    } 



    
     this.pulses = 1.0F - this.dematTime + MathHelper.func_76134_b(this.dematTime * 3.141592F * 10.0F) * 0.25F * MathHelper.func_76126_a(this.dematTime * 3.141592F);
    
     if (getWorld().getBlockState(getPos().func_177982_a(0, -1, 0)).func_185904_a() == Material.field_151579_a) {
       this.bobTime++;
       this.rotation++;
    } else {
       this.bobTime = 0;
       this.rotation = SWDMathUtils.SnapRotationToCardinal(this.rotation);
    } 
    
     if (!this.world.isRemote) {



      
       this.tardisData = DMTardis.getTardis(this.globalID);
       if (this.tardisData != null)
      {
        
         if (this.doorOpenLeft || this.doorOpenRight) {
           defaultAABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 2.0D, 1.0D);
           AxisAlignedBB bounds = defaultAABB.func_186670_a(getPos()).func_72314_b(-0.30000001192092896D, 0.0D, -0.30000001192092896D);
           bounds = bounds.func_72317_d(Math.sin(Math.toRadians(this.rotation)) * 0.5D, 0.0D, -Math.cos(Math.toRadians(this.rotation)) * 0.5D);
           List<Entity> entities = this.world.getEntitiesWithinAABB(Entity.class, bounds);
          
           Predicate<Entity> inFlight = entity -> 
             (entity instanceof PlayerEntity && DMFlightMode.isInFlight((PlayerEntity)entity));
          
           Predicate<Entity> isRiding = entity -> entity.func_184218_aH();


          
           entities.removeIf(inFlight);
           entities.removeIf(isRiding);
          
           if (!entities.isEmpty()) {
             Entity e = entities.get(0);
             Position vec = this.tardisData.getInteriorSpawnPosition();
            
             if (!TeleportUtil.TELEPORT_REQUESTS.containsKey(e) && vec != null) {
               Location loc = new Location(new Vector3d(vec.func_82615_a(), vec.func_82617_b(), vec.func_82616_c()), DMDimensions.TARDIS);
               loc.setFacing(this.tardisData.getInteriorSpawnRotation() + e.getRotationYawHead() - this.rotation);
               TeleportUtil.TELEPORT_REQUESTS.put(e, new TeleportRequest(loc));
            } 
          } 
        } 
      }
    } 
  }



  
  private void doorAnimation() {
     if (this.doorOpenLeft) {
       if (this.doorLeftRotation < 1.0F) {
         this.doorLeftRotation = (float)(this.doorLeftRotation + 0.10000000149011612D - this.doorLeftRotation * 0.08D);
      } else {
         this.doorLeftRotation = 1.0F;
      }
    
     } else if (this.doorLeftRotation > 0.0F) {
       if (this.doorLeftRotation - 0.3F < 0.0F) {
         this.doorLeftRotation = 0.0F;
      } else {
         this.doorLeftRotation -= 0.3F;
      } 
    } else {
       this.doorLeftRotation = 0.0F;
    } 

    
     if (this.doorOpenRight) {
       if (this.doorRightRotation < 1.0F) {
         this.doorRightRotation = (float)(this.doorRightRotation + 0.10000000149011612D - this.doorRightRotation * 0.08D);
      } else {
         this.doorRightRotation = 1.0F;
      }
    
     } else if (this.doorRightRotation > 0.0F) {
       if (this.doorRightRotation - 0.3F < 0.0F) {
         this.doorRightRotation = 0.0F;
      } else {
         this.doorRightRotation -= 0.3F;
      } 
    } else {
       this.doorRightRotation = 0.0F;
    } 
  }

  
  public void toggleDoor(TardisDoor door, DoorSource source) {
     if (door == TardisDoor.LEFT) {
       this.doorOpenLeft = !this.doorOpenLeft;
       toggleInteriorDoor(door, this.doorOpenLeft, source);
       if (this.doorOpenLeft) {
         this.world.playSound((PlayerEntity)null, getPos().getX(), getPos().getY(), getPos().getZ(), getOpenSound(), SoundCategory.BLOCKS, 0.5F, 1.0F);
      } else {
         this.world.playSound((PlayerEntity)null, getPos().getX(), getPos().getY(), getPos().getZ(), getCloseSound(), SoundCategory.BLOCKS, 0.5F, 1.0F);
      } 
     } else if (door == TardisDoor.RIGHT) {
       this.doorOpenRight = !this.doorOpenRight;
       toggleInteriorDoor(door, this.doorOpenRight, source);
       if (this.doorOpenRight) {
         this.world.playSound((PlayerEntity)null, getPos().getX(), getPos().getY(), getPos().getZ(), getOpenSound(), SoundCategory.BLOCKS, 0.5F, 1.0F);
      } else {
         this.world.playSound((PlayerEntity)null, getPos().getX(), getPos().getY(), getPos().getZ(), getCloseSound(), SoundCategory.BLOCKS, 0.5F, 1.0F);
      } 
    } else {
       this.doorOpenRight = !this.doorOpenRight;
       this.doorOpenLeft = !this.doorOpenLeft;
      
       if (this.doorOpenRight) {
         toggleInteriorDoor(TardisDoor.RIGHT, true, source);
         this.world.playSound((PlayerEntity)null, getPos().getX(), getPos().getY(), getPos().getZ(), getOpenSound(), SoundCategory.BLOCKS, 0.5F, 1.0F);
      } else {
         toggleInteriorDoor(TardisDoor.RIGHT, false, source);
         this.world.playSound((PlayerEntity)null, getPos().getX(), getPos().getY(), getPos().getZ(), getCloseSound(), SoundCategory.BLOCKS, 0.5F, 1.0F);
      } 
      
       if (this.doorOpenLeft) {
         toggleInteriorDoor(TardisDoor.LEFT, true, source);
         this.world.playSound((PlayerEntity)null, getPos().getX(), getPos().getY(), getPos().getZ(), getOpenSound(), SoundCategory.BLOCKS, 0.5F, 1.0F);
      } else {
         toggleInteriorDoor(TardisDoor.LEFT, false, source);
         this.world.playSound((PlayerEntity)null, getPos().getX(), getPos().getY(), getPos().getZ(), getCloseSound(), SoundCategory.BLOCKS, 0.5F, 1.0F);
      } 
    } 
    
     sendUpdates();
  }
  
  public void closeDoor(TardisDoor door, DoorSource source) {
     if (door == TardisDoor.LEFT) {
       this.doorOpenLeft = false;
     } else if (door == TardisDoor.RIGHT) {
       this.doorOpenRight = false;
    } else {
       this.doorOpenLeft = false;
       this.doorOpenRight = false;
    } 
    
     toggleInteriorDoor(door, false, source);
    
     this.world.playSound((PlayerEntity)null, getPos().getX(), getPos().getY(), getPos().getZ(), getCloseSound(), SoundCategory.BLOCKS, 0.5F, 1.0F);
     sendUpdates();
  }
  
  public void toggleInteriorDoor(TardisDoor door, boolean state, DoorSource source) {
     if (!this.world.isRemote && source == DoorSource.TARDIS) {
       TardisData data = DMTardis.getTardis(this.globalID);
       if (data != null && 
         data.hasGenerated()) {
         Position p = data.getInteriorSpawnPosition();
         BlockPos pos = new BlockPos(p.func_82615_a(), p.func_82617_b(), p.func_82616_c());
         ServerWorld world = this.world.getServer().getWorld(DMDimensions.TARDIS);
         if (world != null) {
           Vector3d spawnPos = new Vector3d(pos.getX(), pos.getY(), pos.getZ());
           List<WorldUtils.BlockWithPos> doors = new ArrayList<>();
           List<WorldUtils.BlockWithPos> blocks = WorldUtils.getBlocksWithinBounds((World)world, defaultAABB.func_186670_a(pos).grow(4.0D));
           for (int i = 0; i < blocks.size(); i++) {
             WorldUtils.BlockWithPos bwp = blocks.get(i);
             if (bwp.getBlock() instanceof PoliceBoxDoorBlock) {
               BlockState blockState = world.getBlockState(bwp.getBlockPos());
               if (blockState.get((Property)PoliceBoxDoorBlock.HALF) == DoubleBlockHalf.LOWER) {
                 doors.add(bwp);
              }
             } else if (bwp.getBlock() instanceof RoundelDoorBlock) {
               doors.add(bwp);
            } 
          } 
           double distance = 0.0D;
           WorldUtils.BlockWithPos targetDoor = null;
           for (int j = 0; j < doors.size(); j++) {
             WorldUtils.BlockWithPos bwp = doors.get(j);
            
             if (bwp.getBlock() == DMBlocks.ROUNDEL_DOOR.get()) {
              
               TileEntity tile = world.getTileEntity(bwp.getBlockPos());
               if (tile != null && tile instanceof RoundelDoorTileEntity) {
                 DoorHingeSide hinge = (DoorHingeSide)tile.func_195044_w().get((Property)RoundelDoorBlock.HINGE);
                 TardisDoor doo = (door == TardisDoor.LEFT) ? TardisDoor.RIGHT : ((door == TardisDoor.RIGHT) ? TardisDoor.LEFT : door);
                 if (TardisActionList.doAnimation(world, bwp.getBlockPos())) {
                   if (doo == TardisDoor.RIGHT && hinge == DoorHingeSide.RIGHT) {
                     ((RoundelDoorTileEntity)tile).setOpen(state);
                   } else if (doo == TardisDoor.LEFT && hinge == DoorHingeSide.LEFT) {
                     ((RoundelDoorTileEntity)tile).setOpen(state);
                   } else if (doo == TardisDoor.BOTH) {
                     ((RoundelDoorTileEntity)tile).setOpen(state);
                  }
                
                 } else if (doo == TardisDoor.RIGHT && hinge == DoorHingeSide.RIGHT) {
                   ((RoundelDoorTileEntity)tile).forceDoorState(state);
                 } else if (doo == TardisDoor.LEFT && hinge == DoorHingeSide.LEFT) {
                   ((RoundelDoorTileEntity)tile).forceDoorState(state);
                 } else if (doo == TardisDoor.BOTH) {
                   ((RoundelDoorTileEntity)tile).forceDoorState(state);
                }
              
              } 
            } else {
              
               Vector3d blockPos = new Vector3d(bwp.getBlockPos().getX(), bwp.getBlockPos().getY(), bwp.getBlockPos().getZ());
              
               if (targetDoor == null) {
                 targetDoor = bwp;
              }
               double doorDistance = blockPos.func_72438_d(spawnPos);
               if (doorDistance < distance) {
                 distance = doorDistance;
                 targetDoor = bwp;
              } 
            } 
          } 
          
           if (targetDoor != null) {
             TileEntity tile = world.getTileEntity(targetDoor.getBlockPos());
             if (tile != null && tile instanceof DoubleDoorsTileEntity) {
               if (TardisActionList.doAnimation(world, targetDoor.getBlockPos())) {
                 TardisDoor doo = (door == TardisDoor.LEFT) ? TardisDoor.RIGHT : ((door == TardisDoor.RIGHT) ? TardisDoor.LEFT : door);
                 ((DoubleDoorsTileEntity)tile).setOpen(doo, state);
              } else {
                 ((DoubleDoorsTileEntity)tile).forceDoorState((door == TardisDoor.LEFT) ? TardisDoor.RIGHT : ((door == TardisDoor.RIGHT) ? TardisDoor.LEFT : door), state);
              } 
            }
          } 
        } 
      } 
    } 
  }

  
  public void setDoor(TardisDoor door, boolean state, DoorSource source) {
     if (door == TardisDoor.LEFT) {
       this.doorOpenLeft = state;
     } else if (door == TardisDoor.RIGHT) {
       this.doorOpenRight = state;
    } else {
       this.doorOpenLeft = state;
       this.doorOpenRight = state;
    } 
    
     toggleInteriorDoor(door, state, source);
     this.world.playSound((PlayerEntity)null, getPos().getX(), getPos().getY(), getPos().getZ(), getCloseSound(), SoundCategory.BLOCKS, 0.5F, 1.0F);
     sendUpdates();
  }
  
  public SoundEvent getCloseSound() {
     Tardis tardis = DMTardis.getTardis(this.globalID).getTardisExterior();
     if (tardis != null) {
       return tardis.getData().getDoorCloseSound();
    }
    
     return (SoundEvent)DMSoundEvents.TARDIS_POLICE_BOX_DOOR_CLOSE.get();
  }
  
  public SoundEvent getOpenSound() {
     Tardis tardis = DMTardis.getTardis(this.globalID).getTardisExterior();
     if (tardis != null) return tardis.getData().getDoorOpenSound();
    
     return (SoundEvent)DMSoundEvents.TARDIS_POLICE_BOX_DOOR_OPEN.get();
  }
  
  public SoundEvent getTakeOffSound() {
     Tardis tardis = DMTardis.getTardis(this.globalID).getTardisExterior();
     if (tardis != null) return tardis.getData().getTardisTakeoffSound();
    
     return (SoundEvent)DMSoundEvents.TARDIS_DEMAT.get();
  }
  
  public SoundEvent getLandingSound() {
     Tardis tardis = DMTardis.getTardis(this.globalID).getTardisExterior();
     if (tardis != null) return tardis.getData().getTardisLandingSound();
    
     return (SoundEvent)DMSoundEvents.TARDIS_REMAT.get();
  }
  
  public void sendUpdates() {
     markDirty();
     BlockState blockstate = getWorld().getBlockState(getPos());
     getWorld().func_184138_a(getPos(), blockstate, blockstate, 3);
  }
  
  public void forceDoorState(TardisDoor door, boolean isOpen) {
     switch (door) {
      default:
         this.doorOpenLeft = isOpen;
         this.doorOpenRight = isOpen;
         this.doorLeftRotation = isOpen ? 1.0F : 0.0F;
         this.doorRightRotation = isOpen ? 1.0F : 0.0F;
        break;
      case NEUTRAL:
         this.doorOpenLeft = isOpen;
         this.doorLeftRotation = isOpen ? 1.0F : 0.0F;
        break;
      case REMAT:
         this.doorOpenRight = isOpen;
         this.doorRightRotation = isOpen ? 1.0F : 0.0F;
        break;
    } 
    
     if (!this.world.isRemote) sendUpdates(); 
  }
  
  public void setState(TardisState state) {
     this.state = state;
     this.demat = true;
     this.pulses = 1.0F;
    
     switch (state) {
      case DEMAT:
         this.dematTime = 0.0F;
         closeDoor(TardisDoor.BOTH, DoorSource.TARDIS);
         this.world.playSound((PlayerEntity)null, getPos(), getTakeOffSound(), SoundCategory.BLOCKS, 1.0F, 1.0F);
        break;
      case NEUTRAL:
         this.dematTime = 0.0F;
        break;
      case REMAT:
         this.dematTime = 1.0F;
         this.world.playSound((PlayerEntity)null, getPos(), getLandingSound(), SoundCategory.BLOCKS, 1.0F, 1.0F);
        break;
    } 
    
     if (!this.world.isRemote) sendUpdates(); 
  }
  
  public enum DoorSource {
     TARDIS, INTERIOR;
  }
}


