package com.swdteam.network.packets;

import com.swdteam.client.tardis.data.ClientTardisCache;
import com.swdteam.common.tardis.TardisData;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.function.Supplier;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;



public class PacketSendTardisData
{
  private TardisData data;

  public PacketSendTardisData(TardisData data) {
     this.data = data;
  }

  public static void encode(PacketSendTardisData msg, PacketBuffer buf) {
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


  public static PacketSendTardisData decode(PacketBuffer buf) {
     TardisData data = null;

     if (buf.readableBytes() > 0) {
       ByteArrayInputStream ins = new ByteArrayInputStream(buf.func_179251_a());
      try {
         ObjectInputStream ois = new ObjectInputStream(ins);
         Object o = ois.readObject();
         if (o != null && o instanceof TardisData) data = (TardisData)o; 
       } catch (IOException|ClassNotFoundException e) {
         e.printStackTrace();
      }
    }

     return new PacketSendTardisData(data);
  }

  public static void handle(PacketSendTardisData msg, Supplier<NetworkEvent.Context> ctx) {
     ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> ClientTardisCache.addTardisData(msg.data));



     ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
  }
}


