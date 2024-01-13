package com.swdteam.network.packets;

import com.swdteam.common.init.DMBlocks;
import com.swdteam.common.init.DMSoundEvents;
import java.util.function.Supplier;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketDirection;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkEvent;

public class PacketCreeperExplosion {
  private Vector3d pos;

  public PacketCreeperExplosion(double d, double e, double f) {
     this.pos = new Vector3d(d, e, f);
  }

  public static void encode(PacketCreeperExplosion msg, PacketBuffer buf) {
     buf.writeDouble(msg.pos.x);
     buf.writeDouble(msg.pos.y);
     buf.writeDouble(msg.pos.z);
  }

  public static PacketCreeperExplosion decode(PacketBuffer buf) {
     return new PacketCreeperExplosion(buf.readDouble(), buf.readDouble(), buf.readDouble());
  }

  public static void handle(PacketCreeperExplosion msg, Supplier<NetworkEvent.Context> ctx) {
     if (((NetworkEvent.Context)ctx.get()).getNetworkManager().getDirection() == PacketDirection.CLIENTBOUND) {
       clientCode(msg, ctx);
    }
  }

  @OnlyIn(Dist.CLIENT)
  public static void clientCode(PacketCreeperExplosion msg, Supplier<NetworkEvent.Context> ctx) {
     ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> {
          ClientWorld clientWorld = (Minecraft.func_71410_x()).field_71441_e;

          float f1 = 4.0F;

          for (int i = 0; i < 500; i++) {
            float f2 = (float)clientWorld.func_201674_k().nextGaussian() * f1 / 4.0F;

            float f3 = (float)clientWorld.func_201674_k().nextGaussian() * f1 / 4.0F;
            float f4 = (float)clientWorld.func_201674_k().nextGaussian() * f1 / 4.0F;
            float f5 = (float)Math.sqrt((f2 * f2 + f3 * f3 + f4 * f4));
            float f6 = f2 / f5 / f5;
            float f7 = f3 / f5 / f5;
            f5 = f4 / f5 / f5;
            clientWorld.addParticle((IParticleData)new BlockParticleData(ParticleTypes.field_197611_d, ((Block)DMBlocks.CLASSIC_LEAVES.get()).getDefaultState()), msg.pos.x + f2, msg.pos.y + f3, msg.pos.z + f4, f6, f7, f5);
          }
          clientWorld.func_184134_a(msg.pos.x, msg.pos.y, msg.pos.z, (SoundEvent)DMSoundEvents.MISC_CLASSIC_EXPLOSION.get(), SoundCategory.HOSTILE, 1.0F, 1.0F, false);
        });
     ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
  }
}


