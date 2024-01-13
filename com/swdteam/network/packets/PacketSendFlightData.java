package com.swdteam.network.packets;

import com.swdteam.client.tardis.data.ClientTardisFlightCache;
import com.swdteam.common.tardis.TardisFlightData;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.function.Supplier;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;



public class PacketSendFlightData
{
  private TardisFlightData data;
  private int id;

  public PacketSendFlightData(int id, TardisFlightData data) {
     this.data = data;
     this.id = id;
  }

  public static void encode(PacketSendFlightData msg, PacketBuffer buf) {
     buf.writeInt(msg.id);
    try {
       ByteArrayOutputStream bos = new ByteArrayOutputStream();
       ObjectOutputStream oos = new ObjectOutputStream(bos);
       oos.writeObject(msg.data);
       oos.close();

       buf.func_179250_a(bos.toByteArray());
     } catch (IOException e) {
       e.printStackTrace();
    }
  }

  public static PacketSendFlightData decode(PacketBuffer buf) {
     TardisFlightData data = null;
     int tardisID = buf.readInt();
     if (buf.readableBytes() > 0) {
       ByteArrayInputStream ins = new ByteArrayInputStream(buf.func_179251_a());
      try {
         ObjectInputStream ois = new ObjectInputStream(ins);
         Object o = ois.readObject();
         if (o != null && o instanceof TardisFlightData) data = (TardisFlightData)o;
       } catch (IOException|ClassNotFoundException e) {
         e.printStackTrace();
      }
    }

     return new PacketSendFlightData(tardisID, data);
  }

  public static void handle(PacketSendFlightData msg, Supplier<NetworkEvent.Context> ctx) {
     ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> ClientTardisFlightCache.addTardisFlightData(msg.id, msg.data));



     ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
  }
}


