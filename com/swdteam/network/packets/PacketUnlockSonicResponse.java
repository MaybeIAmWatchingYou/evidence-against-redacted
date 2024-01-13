package com.swdteam.network.packets;

import com.swdteam.client.gui.GuiSonicWorkbench;
import com.swdteam.common.init.DMSoundEvents;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketDirection;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkEvent;

public class PacketUnlockSonicResponse
{
  public boolean unlocked;

  public PacketUnlockSonicResponse(boolean b) {
     this.unlocked = b;
  }

  public static void encode(PacketUnlockSonicResponse msg, PacketBuffer buf) {
     buf.writeBoolean(msg.unlocked);
  }

  public static PacketUnlockSonicResponse decode(PacketBuffer buf) {
     return new PacketUnlockSonicResponse(buf.readBoolean());
  }

  public static void handle(PacketUnlockSonicResponse msg, Supplier<NetworkEvent.Context> ctx) {
     if (((NetworkEvent.Context)ctx.get()).getNetworkManager().getDirection() == PacketDirection.CLIENTBOUND) {
       clientCode(msg, ctx);
    }
  }


  @OnlyIn(Dist.CLIENT)
  public static void clientCode(PacketUnlockSonicResponse msg, Supplier<NetworkEvent.Context> ctx) {
     ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> {
          if ((Minecraft.func_71410_x()).field_71462_r instanceof GuiSonicWorkbench) {
            if (msg.unlocked) {
              (Minecraft.func_71410_x()).field_71439_g.func_184185_a((SoundEvent)DMSoundEvents.TARDIS_SONIC_WORKBENCH_UNLOCK.get(), 1.0F, 1.0F);

              ((GuiSonicWorkbench)(Minecraft.func_71410_x()).field_71462_r).applyBtn.func_238482_a_((ITextComponent)new StringTextComponent("Apply"));
              ((GuiSonicWorkbench)(Minecraft.func_71410_x()).field_71462_r).isUnlocked = true;
              ((GuiSonicWorkbench)(Minecraft.func_71410_x()).field_71462_r).msg = "";
            } else {
              ((GuiSonicWorkbench)(Minecraft.func_71410_x()).field_71462_r).msg = "Short on XP";
            }
          }
        });
     ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
  }
}


