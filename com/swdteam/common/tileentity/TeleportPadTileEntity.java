package com.swdteam.common.tileentity;
import com.swdteam.common.init.DMBlockEntities;
import com.swdteam.common.init.DMBlocks;
import com.swdteam.common.init.DMFlightMode;
import com.swdteam.common.init.DMSoundEvents;
import com.swdteam.common.tardis.Location;
import com.swdteam.util.TeleportUtil;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class TeleportPadTileEntity extends DMTileEntityBase implements ITickableTileEntity {
   private static HashMap<PlayerEntity, Location> PADS = new HashMap<>();
  private BlockPos exitPosition;
  private ResourceLocation exitDimension;
  private boolean ignoreExitPad = false;
  
  public TeleportPadTileEntity() {
     super((TileEntityType)DMBlockEntities.TILE_TELEPORT_PAD.get());
  }
  
  public void setExitDimension(ResourceLocation exitDimension) {
     this.exitDimension = exitDimension;
  }
  
  public void setExitPosition(BlockPos exitPosition) {
     this.exitPosition = exitPosition;
  }

  
  public CompoundNBT func_189515_b(CompoundNBT tag) {
     if (this.exitPosition != null) {
       tag.func_218657_a("ExitPosition", (INBT)NBTUtil.func_186859_a(this.exitPosition));
    }
    
     if (this.exitDimension != null) {
       tag.func_74778_a("ExitDimension", this.exitDimension.toString());
    }
    
     tag.func_74757_a("IgnoreExitPad", this.ignoreExitPad);
     return super.func_189515_b(tag);
  }

  
  public void read(BlockState state, CompoundNBT tag) {
     if (tag.func_74764_b("ExitPosition")) {
       this.exitPosition = NBTUtil.func_186861_c(tag.func_74775_l("ExitPosition"));
    }
    
     if (tag.func_74764_b("ExitDimension")) {
       this.exitDimension = new ResourceLocation(tag.func_74779_i("ExitDimension"));
    }
    
     if (tag.func_74764_b("IgnoreExitPad")) {
       this.ignoreExitPad = tag.func_74767_n("IgnoreExitPad");
    }
     super.read(state, tag);
  }
  
  public ResourceLocation getExitDimension() {
     return this.exitDimension;
  }
  
  public BlockPos getExitPosition() {
     return this.exitPosition;
  }
  
  public boolean canIgnoreExitPad() {
     return this.ignoreExitPad;
  }
  
  public static HashMap<PlayerEntity, Location> getPads() {
     return PADS;
  }
  
  public RegistryKey<World> dimension() {
     return RegistryKey.func_240903_a_(Registry.field_239699_ae_, this.exitDimension);
  }
  
   public static AxisAlignedBB bounds = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.30000001192092896D, 1.0D);

  
  public void tick() {
     if (!this.world.isRemote) {
       List<Entity> entities = this.world.getEntitiesWithinAABB(Entity.class, bounds.func_186670_a(getPos()));
      
       Predicate<Entity> isRiding = entity -> entity.func_184218_aH();

      
       entities.removeIf(isRiding);
       if (!entities.isEmpty()) {
         Entity e = entities.get(0);
         if (getExitDimension() != null && getExitPosition() != null) {
           ServerWorld serverWorld = e.getServer().getWorld(dimension());
           BlockState state = serverWorld.getBlockState(this.exitPosition.func_177977_b());
           if ((state.getBlock() == DMBlocks.TELEPORT_RECEIVER.get() || this.ignoreExitPad) && ((
             e instanceof PlayerEntity && ((PlayerEntity)e).func_225608_bj_()) || !(e instanceof PlayerEntity))) {
             if (e instanceof PlayerEntity && 
               DMFlightMode.isInFlight((PlayerEntity)e)) {
              return;
            }
            
             if (!TeleportUtil.TELEPORT_REQUESTS.containsKey(e))
               TeleportUtil.TELEPORT_REQUESTS.put(e, new TeleportRequest(new Location(new Vector3d(this.exitPosition.getX() + 0.5D, this.exitPosition.getY(), this.exitPosition.getZ() + 0.5D), dimension()), (entity, loc) -> {
                      ServerWorld serverWorld = e.getServer().getWorld(loc.dimensionWorldKey());
                      entity.world.playSound(null, loc.getBlockPosition(), (SoundEvent)DMSoundEvents.BLOCK_TELEPORT_PAD.get(), SoundCategory.BLOCKS, 0.5F, 1.0F);
                      serverWorld.playSound(null, loc.getBlockPosition(), (SoundEvent)DMSoundEvents.BLOCK_TELEPORT_PAD.get(), SoundCategory.BLOCKS, 0.5F, 1.0F);
                    })); 
          } 
        } 
      } 
    } 
  }
}


