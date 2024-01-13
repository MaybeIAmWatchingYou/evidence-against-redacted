package com.swdteam.network.packets;

import com.swdteam.common.entity.WeepingAngelEntity;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketDirection;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;



public class PacketWeepingAngel
{
  private boolean seen;
  private String uuid;

  public PacketWeepingAngel(boolean seen, String uuid) {
     this.seen = seen;
     this.uuid = uuid;
  }

  public static void encode(PacketWeepingAngel msg, PacketBuffer buf) {
     buf.writeBoolean(msg.seen);
     buf.func_180714_a(msg.uuid);
  }

  public static PacketWeepingAngel decode(PacketBuffer buf) {
     return new PacketWeepingAngel(buf.readBoolean(), buf.func_218666_n());
  }

  public static void handle(PacketWeepingAngel msg, Supplier<NetworkEvent.Context> ctx) {
     ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> {
          if (((NetworkEvent.Context)ctx.get()).getNetworkManager().getDirection() == PacketDirection.SERVERBOUND) {
            ServerPlayerEntity player = ((NetworkEvent.Context)ctx.get()).getSender();

            if (player != null) {
              int r = 100;
              AxisAlignedBB bb = new AxisAlignedBB(new BlockPos(0, 0, 0));
              bb = bb.func_191194_a(player.getPositionVec());
              bb = bb.func_191194_a(player.func_70040_Z().func_216372_d(r, r, r));
              bb = bb.grow(r);
              List<WeepingAngelEntity> entities = player.world.getEntitiesWithinAABB(WeepingAngelEntity.class, bb);
              for (WeepingAngelEntity e : entities) {
                if (e.getUniqueID().equals(UUID.fromString(msg.uuid))) {
                  e.handleSeenUpdate(player.func_195047_I_(), msg.seen);
                  break;
                }
              }
            }
          }
        });
     ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
  }
}


