package com.swdteam.network.packets;

import com.swdteam.client.gui.GuiFaultLocator;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketDirection;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkEvent;


public class PacketFaultLocatorSlotUpdate
{
  public int slot;
  public ItemStack stack;

  public PacketFaultLocatorSlotUpdate(int slotID, ItemStack stack) {
     this.slot = slotID;
     this.stack = stack;
  }

  public static void encode(PacketFaultLocatorSlotUpdate msg, PacketBuffer buf) {
     buf.writeInt(msg.slot);
     buf.writeItemStack(msg.stack, true);
  }

  public static PacketFaultLocatorSlotUpdate decode(PacketBuffer buf) {
     return new PacketFaultLocatorSlotUpdate(buf.readInt(), buf.func_150791_c());
  }

  public static void handle(PacketFaultLocatorSlotUpdate msg, Supplier<NetworkEvent.Context> ctx) {
     if (((NetworkEvent.Context)ctx.get()).getNetworkManager().getDirection() == PacketDirection.CLIENTBOUND) {
       clientCode(msg, ctx);
    }
  }


  @OnlyIn(Dist.CLIENT)
  public static void clientCode(PacketFaultLocatorSlotUpdate msg, Supplier<NetworkEvent.Context> ctx) {
     ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> {
          if ((Minecraft.func_71410_x()).field_71462_r instanceof GuiFaultLocator) {
            GuiFaultLocator gui = (GuiFaultLocator)(Minecraft.func_71410_x()).field_71462_r;

            gui.getContainer().func_75139_a(msg.slot).func_75215_d(msg.stack);
          }
        });
     ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
  }
}


