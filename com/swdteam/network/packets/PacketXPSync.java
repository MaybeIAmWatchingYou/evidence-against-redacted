package com.swdteam.network.packets;

import com.swdteam.client.overlay.OverlayXPAmount;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketDirection;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkEvent;



public class PacketXPSync
{
  private int xp;
  private boolean showUI;
  
  public PacketXPSync(int xp, boolean showPopup) {
     this.xp = xp;
     this.showUI = showPopup;
  }
  
  public static void encode(PacketXPSync msg, PacketBuffer buf) {
     buf.writeInt(msg.xp);
     buf.writeBoolean(msg.showUI);
  }
  
  public static PacketXPSync decode(PacketBuffer buf) {
     return new PacketXPSync(buf.readInt(), buf.readBoolean());
  }
  
  public static void handle(PacketXPSync msg, Supplier<NetworkEvent.Context> ctx) {
     if (((NetworkEvent.Context)ctx.get()).getNetworkManager().getDirection() == PacketDirection.CLIENTBOUND) {
       clientCode(msg, ctx);
    }
  }

  
  @OnlyIn(Dist.CLIENT)
  public static void clientCode(PacketXPSync msg, Supplier<NetworkEvent.Context> ctx) {
     ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> {
          (Minecraft.func_71410_x()).field_71439_g.experienceTotal = msg.xp;
          
          if (msg.showUI) {
            OverlayXPAmount.show();
          }
        });
     ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
  }
}


