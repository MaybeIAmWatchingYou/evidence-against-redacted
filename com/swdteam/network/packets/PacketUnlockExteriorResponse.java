package com.swdteam.network.packets;

import com.swdteam.client.gui.GuiChameleonCircuit;
import com.swdteam.common.init.DMSoundEvents;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketDirection;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkEvent;


public class PacketUnlockExteriorResponse
{
  public boolean unlocked;

  public PacketUnlockExteriorResponse(boolean b) {
     this.unlocked = b;
  }

  public static void encode(PacketUnlockExteriorResponse msg, PacketBuffer buf) {
     buf.writeBoolean(msg.unlocked);
  }

  public static PacketUnlockExteriorResponse decode(PacketBuffer buf) {
     return new PacketUnlockExteriorResponse(buf.readBoolean());
  }

  public static void handle(PacketUnlockExteriorResponse msg, Supplier<NetworkEvent.Context> ctx) {
     if (((NetworkEvent.Context)ctx.get()).getNetworkManager().getDirection() == PacketDirection.CLIENTBOUND) {
       clientCode(msg, ctx);
    }
  }


  @OnlyIn(Dist.CLIENT)
  public static void clientCode(PacketUnlockExteriorResponse msg, Supplier<NetworkEvent.Context> ctx) {
     ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> {
          if ((Minecraft.func_71410_x()).field_71462_r instanceof GuiChameleonCircuit) {
            ((GuiChameleonCircuit)(Minecraft.func_71410_x()).field_71462_r).checkUnlocked();

            if (msg.unlocked) {
              (Minecraft.func_71410_x()).field_71439_g.func_184185_a((SoundEvent)DMSoundEvents.TARDIS_CHAMELEON_PANEL_UNLOCK.get(), 1.0F, 1.0F);
            }
          }
        });
     ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
  }
}


