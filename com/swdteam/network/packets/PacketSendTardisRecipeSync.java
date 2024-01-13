package com.swdteam.network.packets;

import com.swdteam.client.gui.GuiTardisInteriorBuilder;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkEvent;



public class PacketSendTardisRecipeSync
{
  private int quantity;
  private ItemStack stack;
  private String item;

  public PacketSendTardisRecipeSync(String item, ItemStack stack, int quantity) {
     this.stack = stack;
     this.quantity = quantity;
     this.item = item;
  }

  public static void encode(PacketSendTardisRecipeSync msg, PacketBuffer buf) {
     buf.func_180714_a(msg.item);
     buf.writeItemStack(msg.stack, true);
     buf.writeInt(msg.quantity);
  }

  public static PacketSendTardisRecipeSync decode(PacketBuffer buf) {
     return new PacketSendTardisRecipeSync(buf.func_218666_n(), buf.func_150791_c(), buf.readInt());
  }

  public static void handle(PacketSendTardisRecipeSync msg, Supplier<NetworkEvent.Context> ctx) {
     ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> clientCode(msg, ctx));
  }




  @OnlyIn(Dist.CLIENT)
  public static void clientCode(PacketSendTardisRecipeSync msg, Supplier<NetworkEvent.Context> ctx) {
     ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> {
          if ((Minecraft.func_71410_x()).field_71462_r instanceof GuiTardisInteriorBuilder) {
            GuiTardisInteriorBuilder gui = (GuiTardisInteriorBuilder)(Minecraft.func_71410_x()).field_71462_r;

            gui.getContainer().func_75139_a(0).func_75215_d(msg.stack);

            gui.updateList(msg.item, msg.quantity);
            (Minecraft.func_71410_x()).field_71439_g.func_184185_a(SoundEvents.field_187604_bf, 1.0F, (Minecraft.func_71410_x()).field_71441_e.rand.nextFloat() / 2.0F + 0.6F);
          }
        });
     ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
  }
}


