package com.swdteam.network.packets;

import com.swdteam.client.tardis.data.ClientTardisCache;
import com.swdteam.common.init.DMTardis;
import com.swdteam.common.tardis.TardisData;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketDirection;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkEvent;



public class PacketTardisLightingSync
{
  private int tardisID;
  private boolean side;
  private int left;
  private int right;
  private boolean force;

  public PacketTardisLightingSync(int tardisID, boolean lighting, int left, int right, boolean force) {
     this.tardisID = tardisID;
     this.side = lighting;
     this.left = left;
     this.right = right;
     this.force = force;
  }

  public static void encode(PacketTardisLightingSync msg, PacketBuffer buf) {
     buf.writeInt(msg.tardisID);
     buf.writeBoolean(msg.side);
     buf.writeInt(msg.left);
     buf.writeInt(msg.right);
     buf.writeBoolean(msg.force);
  }

  public static PacketTardisLightingSync decode(PacketBuffer buf) {
     return new PacketTardisLightingSync(buf.readInt(), buf.readBoolean(), buf.readInt(), buf.readInt(), buf.readBoolean());
  }

  public static void handle(PacketTardisLightingSync msg, Supplier<NetworkEvent.Context> ctx) {
     if (((NetworkEvent.Context)ctx.get()).getNetworkManager().getDirection() == PacketDirection.CLIENTBOUND) {
       clientCode(msg, ctx);
    }
  }

  @OnlyIn(Dist.CLIENT)
  public static void clientCode(PacketTardisLightingSync msg, Supplier<NetworkEvent.Context> ctx) {
     ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> {
          Minecraft mc = Minecraft.func_71410_x();

          ClientPlayerEntity player = mc.field_71439_g;

          if (msg.force || msg.tardisID == DMTardis.getIDForXZ(player.getPosition().getX(), player.getPosition().getZ())) {
            ClientTardisCache.GLOBAL_TARDIS_LIGHTING_LEFT = TardisData.Lighting.toReal(msg.left);

            ClientTardisCache.GLOBAL_TARDIS_LIGHTING_RIGHT = TardisData.Lighting.toReal(msg.right);

            ClientTardisCache.GLOBAL_TARDIS_LIGHTING = msg.side ? ClientTardisCache.GLOBAL_TARDIS_LIGHTING_LEFT : ClientTardisCache.GLOBAL_TARDIS_LIGHTING_RIGHT;
          }
        });

     ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
  }
}


