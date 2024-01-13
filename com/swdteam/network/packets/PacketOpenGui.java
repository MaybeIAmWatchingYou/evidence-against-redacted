package com.swdteam.network.packets;

import com.swdteam.client.init.DMGuiHandler;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketDirection;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkEvent;


public class PacketOpenGui
{
  private BlockPos pos;
  private int guiID;
  
  public PacketOpenGui(BlockPos pos, int guiID) {
     this.pos = pos;
     this.guiID = guiID;
  }
  
  public static void encode(PacketOpenGui msg, PacketBuffer buf) {
     buf.func_179255_a(msg.pos);
     buf.writeInt(msg.guiID);
  }
  
  public static PacketOpenGui decode(PacketBuffer buf) {
     return new PacketOpenGui(buf.func_179259_c(), buf.readInt());
  }
  
  public static void handle(PacketOpenGui msg, Supplier<NetworkEvent.Context> ctx) {
     if (((NetworkEvent.Context)ctx.get()).getNetworkManager().getDirection() == PacketDirection.CLIENTBOUND) {
       clientCode(msg, ctx);
    }
  }

  
  @OnlyIn(Dist.CLIENT)
  public static void clientCode(PacketOpenGui msg, Supplier<NetworkEvent.Context> ctx) {
     ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> {
          if (msg.guiID == -1) {
            Minecraft.func_71410_x().func_147108_a(null);
          } else {
            DMGuiHandler.openGui(msg.guiID, msg.pos, (PlayerEntity)(Minecraft.func_71410_x()).field_71439_g);
          } 
        });
    
     ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
  }
}


