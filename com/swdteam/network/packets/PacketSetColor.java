package com.swdteam.network.packets;

import com.swdteam.common.tileentity.MozaiqueTileEntity;
import com.swdteam.util.ChatUtil;
import java.util.function.Supplier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketDirection;
import net.minecraftforge.fml.network.NetworkEvent;



public class PacketSetColor
{
  private MozaiqueTileEntity.Colors color;

  public PacketSetColor(MozaiqueTileEntity.Colors color) {
     this.color = color;
  }

  public static void encode(PacketSetColor msg, PacketBuffer buf) {
     buf.func_179249_a((Enum)msg.color);
  }

  public static PacketSetColor decode(PacketBuffer buf) {
     return new PacketSetColor((MozaiqueTileEntity.Colors)buf.func_179257_a(MozaiqueTileEntity.Colors.class));
  }


  public static void handle(PacketSetColor msg, Supplier<NetworkEvent.Context> ctx) {
     ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> {
          if (((NetworkEvent.Context)ctx.get()).getNetworkManager().getDirection() == PacketDirection.SERVERBOUND) {
            ServerPlayerEntity serverPlayerEntity = ((NetworkEvent.Context)ctx.get()).getSender();

            MozaiqueTileEntity.PLAYER_COLORS.put(serverPlayerEntity, msg.color);
            ChatUtil.sendCompletedMsg((PlayerEntity)serverPlayerEntity, "Set color to: " + msg.color.getName(), ChatUtil.MessageType.STATUS_BAR);
          }
        });
     ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
  }
}


