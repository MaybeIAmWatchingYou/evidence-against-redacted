package com.swdteam.network.packets;

import com.swdteam.common.init.DMTardisRegistry;
import com.swdteam.common.tardis.Tardis;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;




public class PacketSendTardisRegistry
{
  private Map<String, Tardis> data;

  public PacketSendTardisRegistry(Map<String, Tardis> data) {
     this.data = data;
  }

  public static void encode(PacketSendTardisRegistry msg, PacketBuffer buf) {
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



  public static PacketSendTardisRegistry decode(PacketBuffer buf) {
     Map<String, Tardis> data = null;

     if (buf.readableBytes() > 0) {
       ByteArrayInputStream ins = new ByteArrayInputStream(buf.func_179251_a());
      try {
         ObjectInputStream ois = new ObjectInputStream(ins);
         Object o = ois.readObject();
         if (o != null && o instanceof Map) data = (Map<String, Tardis>)o; 
       } catch (IOException|ClassNotFoundException e) {
         e.printStackTrace();
      }
    }

     return new PacketSendTardisRegistry(data);
  }

  public static void handle(PacketSendTardisRegistry msg, Supplier<NetworkEvent.Context> ctx) {
     if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
       ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> {
            Map<ResourceLocation, Tardis> newData = new HashMap<>();

            for (Map.Entry<String, Tardis> entry : msg.data.entrySet()) {
              newData.put(new ResourceLocation(entry.getKey()), entry.getValue());

              if (((Tardis)entry.getValue()).getData() != null) {
                ((Tardis)entry.getValue()).getData().setup();
              }
            }

            DMTardisRegistry.getRegistry().clear();
            DMTardisRegistry.getRegistry().putAll(newData);
          });
    }
     ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
  }
}


