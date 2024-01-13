package com.swdteam.network.packets;

import com.swdteam.client.data.DamageMap;
import java.util.UUID;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketDirection;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkEvent;



public class PacketDisplayDalekDamage
{
  private UUID player;
  
  public PacketDisplayDalekDamage(UUID player) {
     this.player = player;
  }
  
  public static void encode(PacketDisplayDalekDamage msg, PacketBuffer buf) {
     buf.func_179252_a(msg.player);
  }
  
  public static PacketDisplayDalekDamage decode(PacketBuffer buf) {
     return new PacketDisplayDalekDamage(buf.func_179253_g());
  }
  
  public static void handle(PacketDisplayDalekDamage msg, Supplier<NetworkEvent.Context> ctx) {
     if (((NetworkEvent.Context)ctx.get()).getNetworkManager().getDirection() == PacketDirection.CLIENTBOUND) {
       clientCode(msg, ctx);
    }
  }
  
  @OnlyIn(Dist.CLIENT)
  public static void clientCode(PacketDisplayDalekDamage msg, Supplier<NetworkEvent.Context> ctx) {
     ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> {
          PlayerEntity e = (Minecraft.func_71410_x()).field_71441_e.func_217371_b(msg.player);

          
          DamageMap.addDamagedPlayer(e);
        });

    
     ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
  }
}


