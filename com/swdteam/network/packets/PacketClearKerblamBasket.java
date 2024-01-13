package com.swdteam.network.packets;

import com.swdteam.client.gui.GuiKerblam;
import java.util.function.Supplier;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketDirection;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkEvent;








public class PacketClearKerblamBasket
{
  public static void encode(PacketClearKerblamBasket msg, PacketBuffer buf) {}

  public static PacketClearKerblamBasket decode(PacketBuffer buf) {
     return new PacketClearKerblamBasket();
  }

  public static void handle(PacketClearKerblamBasket msg, Supplier<NetworkEvent.Context> ctx) {
     if (((NetworkEvent.Context)ctx.get()).getNetworkManager().getDirection() == PacketDirection.CLIENTBOUND) {
       clientCode(msg, ctx);
    }
  }


  @OnlyIn(Dist.CLIENT)
  public static void clientCode(PacketClearKerblamBasket msg, Supplier<NetworkEvent.Context> ctx) {
     ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> GuiKerblam.BASKET.clear());



     ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
  }
}


