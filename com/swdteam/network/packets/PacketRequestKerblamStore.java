package com.swdteam.network.packets;

import com.swdteam.common.init.DMKerblamStock;
import com.swdteam.common.kerblam.KerblamItem;
import com.swdteam.network.NetworkHandler;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkEvent;










public class PacketRequestKerblamStore
{
  public static void encode(PacketRequestKerblamStore msg, PacketBuffer buf) {}

  public static PacketRequestKerblamStore decode(PacketBuffer buf) {
     return new PacketRequestKerblamStore();
  }

  public static void handle(PacketRequestKerblamStore msg, Supplier<NetworkEvent.Context> ctx) {
     ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> {
          Map<String, KerblamItem> data = new LinkedHashMap<>();

          for (Map.Entry<ResourceLocation, KerblamItem> entry : (Iterable<Map.Entry<ResourceLocation, KerblamItem>>)DMKerblamStock.getItems().entrySet()) {
            data.put(((ResourceLocation)entry.getKey()).getNamespace() + ":" + ((ResourceLocation)entry.getKey()).getPath(), entry.getValue());
          }

          NetworkHandler.sendTo(((NetworkEvent.Context)ctx.get()).getSender(), new PacketSendKerblamStockSync(data));

          NetworkHandler.sendTo(((NetworkEvent.Context)ctx.get()).getSender(), new PacketXPSync((((NetworkEvent.Context)ctx.get()).getSender()).experienceTotal, false));
        });

     ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
  }
}


