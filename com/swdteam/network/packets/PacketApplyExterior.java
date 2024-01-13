package com.swdteam.network.packets;

import com.swdteam.common.init.DMTardis;
import com.swdteam.common.init.DMTardisRegistry;
import com.swdteam.common.tardis.Tardis;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.util.ChatUtil;
import java.util.function.Supplier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketDirection;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkEvent;



public class PacketApplyExterior
{
  private ResourceLocation resourceLocation;
  private int tardisID;
  private int subID;

  public PacketApplyExterior(ResourceLocation resourceLocation, int tardisID, int subID) {
     this.resourceLocation = resourceLocation;
     this.tardisID = tardisID;
     this.subID = subID;
  }

  public static void encode(PacketApplyExterior msg, PacketBuffer buf) {
     buf.writeInt(msg.tardisID);
     buf.writeInt(msg.subID);
     buf.func_192572_a(msg.resourceLocation);
  }

  public static PacketApplyExterior decode(PacketBuffer buf) {
     int i = buf.readInt();
     int j = buf.readInt();
     ResourceLocation rl = buf.func_192575_l();

     return new PacketApplyExterior(rl, i, j);
  }


  public static void handle(PacketApplyExterior msg, Supplier<NetworkEvent.Context> ctx) {
     ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> {
          if (((NetworkEvent.Context)ctx.get()).getNetworkManager().getDirection() == PacketDirection.SERVERBOUND) {
            ServerPlayerEntity serverPlayerEntity = ((NetworkEvent.Context)ctx.get()).getSender();

            TardisData data = DMTardis.getTardis(msg.tardisID);

            if (data != null) {
              if (data.hasPermission((PlayerEntity)serverPlayerEntity, TardisData.PermissionType.CONTROLS) && data.isUnlocked(msg.resourceLocation)) {
                Tardis tardis = DMTardisRegistry.getExterior(msg.resourceLocation);

                if (msg.subID >= tardis.getData().getSkinCount()) {
                  msg.subID = tardis.getData().getSkinCount() - 1;
                }

                if (msg.subID < 0) {
                  msg.subID = 0;
                }

                data.setExterior(msg.resourceLocation.toString());

                data.setSkinID(msg.subID);

                data.save();
                ChatUtil.sendCompletedMsg((PlayerEntity)serverPlayerEntity, "Chameleon circuit has been updated", ChatUtil.MessageType.STATUS_BAR);
              } else {
                data.noPermission((PlayerEntity)serverPlayerEntity);
              }
            }
          }
        });
     ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
  }
}


