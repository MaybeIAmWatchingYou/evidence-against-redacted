package com.swdteam.network.packets;

import com.swdteam.common.init.DMCriteriaTriggers;
import com.swdteam.common.init.DMTardis;
import com.swdteam.common.init.DMTardisRegistry;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.common.tardis.TardisDoor;
import com.swdteam.common.tardis.TardisInterior;
import com.swdteam.common.tileentity.TardisTileEntity;
import com.swdteam.util.ChatUtil;
import com.swdteam.util.TeleportUtil;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketDirection;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkEvent;

public class PacketConfirmDesktopChange
{
  private boolean cancel;
  private int tardisID;

  public PacketConfirmDesktopChange(int tardisID, boolean cancel) {
     this.tardisID = tardisID;
     this.cancel = cancel;
  }

  public static void encode(PacketConfirmDesktopChange msg, PacketBuffer buf) {
     buf.writeInt(msg.tardisID);
     buf.writeBoolean(msg.cancel);
  }

  public static PacketConfirmDesktopChange decode(PacketBuffer buf) {
     return new PacketConfirmDesktopChange(buf.readInt(), buf.readBoolean());
  }


  public static void handle(PacketConfirmDesktopChange msg, Supplier<NetworkEvent.Context> ctx) {
     ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> {
          if (((NetworkEvent.Context)ctx.get()).getNetworkManager().getDirection() == PacketDirection.SERVERBOUND) {
            ServerPlayerEntity player = ((NetworkEvent.Context)ctx.get()).getSender();

            TardisData data = DMTardis.getTardis(msg.tardisID);

            if (data != null) {
              if (msg.cancel) {
                if (data.isOwner((PlayerEntity)player)) {
                  data.setCurrentlyBuilding(null);

                  data.save();
                } else {
                  ChatUtil.sendError((PlayerEntity)player, "You do not have permission", ChatUtil.MessageType.STATUS_BAR);
                }
              } else if (data.isOwner((PlayerEntity)player) && (data.isRecipeComplete() || player.isCreative())) {
                if (!data.isInFlight()) {
                  ServerWorld serverWorld = player.getServer().getWorld(data.getCurrentLocation().dimensionWorldKey());

                  TileEntity te = serverWorld.getTileEntity(data.getCurrentLocation().getBlockPosition());

                  if (te != null && te instanceof TardisTileEntity) {
                    TardisTileEntity tard = (TardisTileEntity)te;

                    BlockPos tardisPosition = data.getCurrentLocation().getBlockPosition();

                    tard.setDoor(TardisDoor.BOTH, false, TardisTileEntity.DoorSource.TARDIS);

                    Vector3d look = Vector3d.fromPitchYaw(new Vector2f(45.0F, tard.rotation + 180.0F));

                    float distance = 3.0F;

                    double dx = tardisPosition.getX() + look.x * distance;

                    double dy = (tardisPosition.getY() > 0) ? tardisPosition.getY() : 128.0D;

                    double dz = tardisPosition.getZ() + look.z * distance;

                    BlockPos interiorPos = DMTardis.getXZForMap(tard.globalID);
                    BlockPos pos2 = new BlockPos(interiorPos.getX() * DMTardis.INTERIOR_BOUNDS, 0, interiorPos.getZ() * DMTardis.INTERIOR_BOUNDS);
                    AxisAlignedBB bounds = new AxisAlignedBB(pos2, pos2.func_177982_a(DMTardis.INTERIOR_BOUNDS, 256, DMTardis.INTERIOR_BOUNDS));
                    List<ServerPlayerEntity> players = player.world.getEntitiesWithinAABB(ServerPlayerEntity.class, bounds);
                    if (players != null && players.size() > 0) {
                      for (int i = 0; i < players.size(); i++) {
                        ServerPlayerEntity pla = players.get(i);
                        TeleportUtil.teleportPlayer((Entity)pla, serverWorld.getDimensionKey(), (IPosition)new Vector3d(dx + 0.5D, dy, dz + 0.5D), tard.rotation);
                      }
                    }
                  }
                  DMCriteriaTriggers.ARS_TRIGGER.trigger(player, new ResourceLocation(data.getCurrentlyBuilding()));
                  DMTardis.generateInterior((TardisInterior)DMTardisRegistry.getTardisInteriors().get(new ResourceLocation(data.getCurrentlyBuilding())), player.getServer(), data);
                  data.setCurrentlyBuilding(null);
                  data.save();
                  ChatUtil.sendCompletedMsg((PlayerEntity)player, "Your TARDIS is re-growing, please wait... ", ChatUtil.MessageType.STATUS_BAR);
                } else {
                  ChatUtil.sendError((PlayerEntity)player, "Please land your TARDIS", ChatUtil.MessageType.STATUS_BAR);
                }
              } else {
                ChatUtil.sendError((PlayerEntity)player, "You do not have permission", ChatUtil.MessageType.STATUS_BAR);
              }
            }
          }
        });
     ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
  }
}


