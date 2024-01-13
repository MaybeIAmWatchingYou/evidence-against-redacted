package com.swdteam.network.packets;

import com.swdteam.common.init.DMTardis;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.network.NetworkHandler;
import java.util.function.Supplier;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;



public class PacketRequestTardis
{
  private int data;

  public PacketRequestTardis(int id) {
     this.data = id;
  }

  public static void encode(PacketRequestTardis msg, PacketBuffer buf) {
     buf.writeInt(msg.data);
  }

  public static PacketRequestTardis decode(PacketBuffer buf) {
     return new PacketRequestTardis(buf.readInt());
  }

  public static void handle(PacketRequestTardis msg, Supplier<NetworkEvent.Context> ctx) {
     ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> {
          TardisData data = DMTardis.getTardis(msg.data);

          if (data != null) {
            NetworkHandler.sendTo(((NetworkEvent.Context)ctx.get()).getSender(), new PacketSendTardisData(data));
          }
        });

     ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
  }
}


