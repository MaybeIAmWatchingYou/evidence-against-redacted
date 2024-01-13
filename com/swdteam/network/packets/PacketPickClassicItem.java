package com.swdteam.network.packets;

import java.util.function.Supplier;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketDirection;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.network.NetworkEvent;

public class PacketPickClassicItem
{
  private ItemStack stack;

  public PacketPickClassicItem(ItemStack stack) {
     this.stack = stack;
  }

  public static void encode(PacketPickClassicItem msg, PacketBuffer buf) {
     buf.func_150788_a(msg.stack);
  }

  public static PacketPickClassicItem decode(PacketBuffer buf) {
     return new PacketPickClassicItem(buf.func_150791_c());
  }


  public static void handle(PacketPickClassicItem msg, Supplier<NetworkEvent.Context> ctx) {
     ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> {
          if (((NetworkEvent.Context)ctx.get()).getNetworkManager().getDirection() == PacketDirection.SERVERBOUND) {
            ServerPlayerEntity serverPlayerEntity = ((NetworkEvent.Context)ctx.get()).getSender();

            if (serverPlayerEntity != null && serverPlayerEntity.isCreative()) {
              serverPlayerEntity.func_184611_a(serverPlayerEntity.getActiveHand(), msg.stack);
            } else {
              serverPlayerEntity.func_145747_a((ITextComponent)new StringTextComponent(TextFormatting.RED + "You do not have permission"), serverPlayerEntity.getUniqueID());
            }
          }
        });

     ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
  }
}


