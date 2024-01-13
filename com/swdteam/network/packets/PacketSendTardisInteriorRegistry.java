package com.swdteam.network.packets;

import com.swdteam.common.init.DMTardisRegistry;
import com.swdteam.common.tardis.TardisInterior;
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




public class PacketSendTardisInteriorRegistry
{
  private Map<String, TardisInterior> data;
  
  public PacketSendTardisInteriorRegistry(Map<String, TardisInterior> data) {
     this.data = data;
  }
  
  public static void encode(PacketSendTardisInteriorRegistry msg, PacketBuffer buf) {
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


  
  public static PacketSendTardisInteriorRegistry decode(PacketBuffer buf) {
     Map<String, TardisInterior> data = null;
    
     if (buf.readableBytes() > 0) {
       ByteArrayInputStream ins = new ByteArrayInputStream(buf.func_179251_a());
      try {
         ObjectInputStream ois = new ObjectInputStream(ins);
         Object o = ois.readObject();
         if (o != null && o instanceof Map) data = (Map<String, TardisInterior>)o; 
       } catch (IOException|ClassNotFoundException e) {
         e.printStackTrace();
      } 
    } 
    
     return new PacketSendTardisInteriorRegistry(data);
  }
  
  public static void handle(PacketSendTardisInteriorRegistry msg, Supplier<NetworkEvent.Context> ctx) {
     if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
       ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> {
            Map<ResourceLocation, TardisInterior> newData = new HashMap<>();
            
            for (Map.Entry<String, TardisInterior> entry : msg.data.entrySet()) {
              newData.put(new ResourceLocation(entry.getKey()), entry.getValue());
            }
            
            DMTardisRegistry.getTardisInteriors().clear();
            
            DMTardisRegistry.getTardisInteriors().putAll(newData);
          });
    }
    
     ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
  }
}


