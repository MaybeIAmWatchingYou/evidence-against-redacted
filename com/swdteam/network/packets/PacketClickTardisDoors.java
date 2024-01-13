package com.swdteam.network.packets;

import com.swdteam.common.init.DMBlockEntities;
import com.swdteam.common.init.DMSoundEvents;
import com.swdteam.common.init.DMTardis;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.common.tardis.TardisDoor;
import com.swdteam.common.tardis.TardisState;
import com.swdteam.common.tileentity.TardisTileEntity;
import com.swdteam.common.tileentity.tardis.DoubleDoorsTileEntity;
import com.swdteam.common.tileentity.tardis.RoundelDoorTileEntity;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketDirection;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.fml.network.NetworkEvent;







public class PacketClickTardisDoors
{
  public static void encode(PacketClickTardisDoors msg, PacketBuffer buf) {}
  
  public static PacketClickTardisDoors decode(PacketBuffer buf) {
     return new PacketClickTardisDoors();
  }

  
  public static void handle(PacketClickTardisDoors msg, Supplier<NetworkEvent.Context> ctx) {
     ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> {
          if (((NetworkEvent.Context)ctx.get()).getNetworkManager().getDirection() == PacketDirection.SERVERBOUND) {
            ServerPlayerEntity serverPlayerEntity = ((NetworkEvent.Context)ctx.get()).getSender();
            
            if (serverPlayerEntity.getHeldItem(Hand.MAIN_HAND).func_190926_b() || serverPlayerEntity.getHeldItem(Hand.OFF_HAND).func_190926_b()) {
              ((PlayerEntity)serverPlayerEntity).world.playSound(null, serverPlayerEntity.getPosition(), (SoundEvent)DMSoundEvents.PLAYER_SNAP.get(), SoundCategory.PLAYERS, 0.4F + ((PlayerEntity)serverPlayerEntity).world.rand.nextFloat() / 3.0F, 0.9F + ((PlayerEntity)serverPlayerEntity).world.rand.nextFloat() / 10.0F);
              
              List<TileEntity> tiles = ((PlayerEntity)serverPlayerEntity).world.field_175730_i;
              
              for (int i = 0; i < tiles.size(); i++) {
                TileEntity tile = tiles.get(i);
                
                if (tile.func_200662_C() == DMBlockEntities.TILE_TARDIS.get()) {
                  Vector3d playerPos = serverPlayerEntity.getPositionVec();
                  
                  Vector3d tardisPos = new Vector3d(tile.getPos().getX(), tile.getPos().getY(), tile.getPos().getZ());
                  
                  double distance = playerPos.func_72438_d(tardisPos);
                  
                  if (distance <= 15.0D) {
                    TardisData data = ((TardisTileEntity)tile).tardisData;
                    
                    if (data != null && !data.isLocked()) {
                      TardisTileEntity tardis = (TardisTileEntity)tile;
                      
                      if (tardis.state == TardisState.NEUTRAL) {
                        if (tardis.doorOpenLeft || tardis.doorOpenRight) {
                          tardis.closeDoor(TardisDoor.BOTH, TardisTileEntity.DoorSource.TARDIS);
                        } else {
                          tardis.toggleDoor(TardisDoor.BOTH, TardisTileEntity.DoorSource.TARDIS);
                        } 
                      }
                    } 
                  } 
                } else if (tile instanceof DoubleDoorsTileEntity) {
                  Vector3d playerPos = serverPlayerEntity.getPositionVec();
                  
                  Vector3d tardisPos = new Vector3d(tile.getPos().getX(), tile.getPos().getY(), tile.getPos().getZ());
                  
                  double distance = playerPos.func_72438_d(tardisPos);
                  
                  if (distance <= 15.0D) {
                    DoubleDoorsTileEntity tardis = (DoubleDoorsTileEntity)tile;
                    
                    TardisData data = DMTardis.getTardisFromInteriorPos(tile.getPos());
                    
                    if (tardis.isMainDoor() && data != null && !data.isLocked() && !data.isInFlight()) {
                      if (tardis.isOpen(TardisDoor.LEFT) || tardis.isOpen(TardisDoor.RIGHT)) {
                        tardis.setOpen(TardisDoor.BOTH, false);
                      } else {
                        tardis.setOpen(TardisDoor.BOTH, true);
                      } 
                    }
                  } 
                } else if (tile instanceof RoundelDoorTileEntity) {
                  Vector3d playerPos = serverPlayerEntity.getPositionVec();
                  
                  Vector3d tardisPos = new Vector3d(tile.getPos().getX(), tile.getPos().getY(), tile.getPos().getZ());
                  
                  double distance = playerPos.func_72438_d(tardisPos);
                  
                  if (distance <= 15.0D) {
                    RoundelDoorTileEntity tardis = (RoundelDoorTileEntity)tile;
                    TardisData data = DMTardis.getTardisFromInteriorPos(tile.getPos());
                    if (tardis.isMainDoor() && data != null && !data.isLocked() && !data.isInFlight()) {
                      if (tardis.isOpen()) {
                        tardis.setOpen(false);
                      } else {
                        tardis.setOpen(true);
                      } 
                    }
                  } 
                } 
              } 
            } 
          } 
        });
     ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
  }
}


