package com.swdteam.network.packets;

import com.swdteam.common.init.DMTardis;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.common.tardis.TardisFlightData;
import com.swdteam.common.tardis.data.TardisFlightPool;
import com.swdteam.network.NetworkHandler;
import java.util.function.Supplier;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;



public class PacketRequestTardisFlightData
{
  private int data;

  public PacketRequestTardisFlightData(int id) {
     this.data = id;
  }

  public static void encode(PacketRequestTardisFlightData msg, PacketBuffer buf) {
     buf.writeInt(msg.data);
  }

  public static PacketRequestTardisFlightData decode(PacketBuffer buf) {
     return new PacketRequestTardisFlightData(buf.readInt());
  }

  public static void handle(PacketRequestTardisFlightData msg, Supplier<NetworkEvent.Context> ctx) {
     ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> {
          TardisData data = DMTardis.getTardis(msg.data);

          if (data != null) {
            TardisFlightData flightData = TardisFlightPool.getFlightData(data);
            if (flightData != null) {
              NetworkHandler.sendTo(((NetworkEvent.Context)ctx.get()).getSender(), new PacketSendFlightData(msg.data, flightData));
            }
          }
        });
     ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
  }
}


