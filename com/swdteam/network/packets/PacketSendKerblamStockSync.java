package com.swdteam.network.packets;

import com.swdteam.client.gui.GuiKerblam;
import com.swdteam.common.init.DMKerblamStock;
import com.swdteam.common.kerblam.KerblamItem;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;




public class PacketSendKerblamStockSync
{
  private Map<String, KerblamItem> data;

  public PacketSendKerblamStockSync(Map<String, KerblamItem> data) {
     this.data = data;
  }

  public static void encode(PacketSendKerblamStockSync msg, PacketBuffer buf) {
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



  public static PacketSendKerblamStockSync decode(PacketBuffer buf) {
     Map<String, KerblamItem> kerblamData = null;

     if (buf.readableBytes() > 0) {
       ByteArrayInputStream ins = new ByteArrayInputStream(buf.func_179251_a());
      try {
         ObjectInputStream ois = new ObjectInputStream(ins);
         Object o = ois.readObject();
         if (o != null && o instanceof Map) kerblamData = (Map<String, KerblamItem>)o;
       } catch (IOException|ClassNotFoundException e) {
         e.printStackTrace();
      }
    }

     return new PacketSendKerblamStockSync(kerblamData);
  }

  public static void handle(PacketSendKerblamStockSync msg, Supplier<NetworkEvent.Context> ctx) {
     if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
       ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> {
            List<KerblamItem> items = new ArrayList<>();

            for (Map.Entry<String, KerblamItem> entry : msg.data.entrySet()) {
              ((KerblamItem)entry.getValue()).setResourceKey(new ResourceLocation(entry.getKey()));

              items.add(entry.getValue());
            }

            Collections.sort(items, ());

            Map<ResourceLocation, KerblamItem> itemMap = new LinkedHashMap<>();

            for (KerblamItem item : items) {
              itemMap.put(item.getResourceKey(), item);
            }

            DMKerblamStock.getItems().clear();

            DMKerblamStock.getItems().putAll(itemMap);

            Minecraft mc = Minecraft.func_71410_x();

            if (mc != null && mc.field_71462_r instanceof GuiKerblam) {
              ((GuiKerblam)mc.field_71462_r).setupStoreData();
            }
          });
    }
     ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
  }
}


