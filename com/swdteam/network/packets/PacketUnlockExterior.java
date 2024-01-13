package com.swdteam.network.packets;

import com.swdteam.common.init.DMCriteriaTriggers;
import com.swdteam.common.init.DMTardis;
import com.swdteam.common.init.DMTardisRegistry;
import com.swdteam.common.tardis.Tardis;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.network.NetworkHandler;
import java.util.function.Supplier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketDirection;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkEvent;


public class PacketUnlockExterior
{
  private ResourceLocation resourceLocation;
  private int tardisID;
  
  public PacketUnlockExterior(ResourceLocation resourceLocation, int tardisID) {
     this.resourceLocation = resourceLocation;
     this.tardisID = tardisID;
  }
  
  public static void encode(PacketUnlockExterior msg, PacketBuffer buf) {
     buf.writeInt(msg.tardisID);
     buf.func_192572_a(msg.resourceLocation);
  }
  
  public static PacketUnlockExterior decode(PacketBuffer buf) {
     int i = buf.readInt();
     ResourceLocation rl = buf.func_192575_l();
    
     return new PacketUnlockExterior(rl, i);
  }

  
  public static void handle(PacketUnlockExterior msg, Supplier<NetworkEvent.Context> ctx) {
     ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> {
          if (((NetworkEvent.Context)ctx.get()).getNetworkManager().getDirection() == PacketDirection.SERVERBOUND) {
            ServerPlayerEntity player = ((NetworkEvent.Context)ctx.get()).getSender();
            
            TardisData data = DMTardis.getTardis(msg.tardisID);
            
            if (data != null) {
              if (!data.isUnlocked(msg.resourceLocation)) {
                Tardis tardis = DMTardisRegistry.getExterior(msg.resourceLocation);
                
                if (tardis != null && tardis.getData().isUnlockable()) {
                  if (tardis.getData().getXpValue() <= player.experienceTotal || player.isCreative()) {
                    if (!player.isCreative()) {
                      player.func_195068_e(-tardis.getData().getXpValue());
                    }
                    
                    data.unlockExterior(msg.resourceLocation);
                    
                    DMCriteriaTriggers.UNLOCK_EXT.trigger(player, msg.resourceLocation);
                    
                    data.save();
                    
                    NetworkHandler.sendTo(player, new PacketUnlockExteriorResponse(true));
                  } 
                }
              } else {
                data.noPermission((PlayerEntity)player);
              } 
            }
          } 
        });
     ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
  }
}


