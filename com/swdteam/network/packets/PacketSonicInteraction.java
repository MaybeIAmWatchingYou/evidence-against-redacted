package com.swdteam.network.packets;

import com.swdteam.common.init.DMSonicRegistry;
import com.swdteam.common.sonic.SonicCategory;
import java.util.UUID;
import java.util.function.Supplier;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketDirection;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.network.NetworkEvent;

public class PacketSonicInteraction
{
  private BlockPos pos;
  private UUID uuid;
  private Hand hand;

  public PacketSonicInteraction(BlockPos pos, UUID uuid, Hand slot) {
     this.pos = pos;
     this.uuid = uuid;
     this.hand = slot;
  }

  public static void encode(PacketSonicInteraction msg, PacketBuffer buf) {
     if (msg.uuid != null) {
       buf.writeInt(0);
       buf.func_179252_a(msg.uuid);
    } else {
       buf.writeInt(1);
       buf.func_179255_a(msg.pos);
    }

     buf.writeInt((msg.hand == Hand.MAIN_HAND) ? 0 : 1);
  }

  public static PacketSonicInteraction decode(PacketBuffer buf) {
     int type = buf.readInt();
     UUID uuid = null;
     BlockPos pos = null;

     if (type == 0) {

       uuid = buf.func_179253_g();
    } else {

       pos = buf.func_179259_c();
    }

     return new PacketSonicInteraction(pos, uuid, (buf.readInt() == 0) ? Hand.MAIN_HAND : Hand.OFF_HAND);
  }


  public static void handle(PacketSonicInteraction msg, Supplier<NetworkEvent.Context> ctx) {
     ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> {
          if (((NetworkEvent.Context)ctx.get()).getNetworkManager().getDirection() == PacketDirection.SERVERBOUND && msg.uuid == null) {
            if (msg.pos.func_218141_a((Vector3i)((NetworkEvent.Context)ctx.get()).getSender().getPosition(), 10.0D)) {
              BlockState state = ((NetworkEvent.Context)ctx.get()).getSender().getEntityWorld().getBlockState(msg.pos);


              if (DMSonicRegistry.SONIC_LOOKUP.containsKey(state.getBlock())) {
                DMSonicRegistry.ISonicInteraction son = (DMSonicRegistry.ISonicInteraction)DMSonicRegistry.SONIC_LOOKUP.get(state.getBlock());


                ItemStack stack = ((NetworkEvent.Context)ctx.get()).getSender().getHeldItem(msg.hand);


                if (stack.func_77942_o() && son != null) {
                  if (SonicCategory.canExecute(stack, son.getCategory()) || ((NetworkEvent.Context)ctx.get()).getSender().isCreative()) {
                    stack.func_77978_p().putInt("xp", stack.func_77978_p().getInt("xp") + 1);


                    son.interact(((NetworkEvent.Context)ctx.get()).getSender().getEntityWorld(), (PlayerEntity)((NetworkEvent.Context)ctx.get()).getSender(), ((NetworkEvent.Context)ctx.get()).getSender().func_184607_cu(), msg.pos);


                    SonicCategory.checkUnlock((PlayerEntity)((NetworkEvent.Context)ctx.get()).getSender(), stack);
                  } else {
                    ((NetworkEvent.Context)ctx.get()).getSender().func_146105_b((ITextComponent)(new StringTextComponent("This ability is still locked")).func_240699_a_(TextFormatting.RED), false);
                  }
                }
              }
            }
          }
        });

     ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
  }
}


