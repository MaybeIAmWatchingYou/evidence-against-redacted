package com.swdteam.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketThreadUtil;
import net.minecraft.network.play.server.SChunkDataPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IObjectIntIterable;
import net.minecraft.util.concurrent.ThreadTaskExecutor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeContainer;
import net.minecraft.world.chunk.Chunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ClientPlayNetHandler.class})
public class ClientPlayBiomeMixin {
  @Shadow
  private Minecraft field_147299_f;

  @Inject(at = {@At("HEAD")}, method = {"handleLevelChunk(Lnet/minecraft/network/play/server/SChunkDataPacket;)V"}, cancellable = true)
  public void onChunkData(SChunkDataPacket p_147263_1_, CallbackInfo info) {
     PacketThreadUtil.func_218797_a((IPacket)p_147263_1_, (INetHandler)this, (ThreadTaskExecutor)this.field_147299_f);
     int i = p_147263_1_.func_149273_e();
     int j = p_147263_1_.func_149271_f();

     int[] packetArray = p_147263_1_.func_244296_i();

     MutableRegistry mutableRegistry = this.field_239163_t_.func_243612_b(Registry.BIOME_KEY);

     Biome[] data = new Biome[BiomeContainer.field_227049_a_];
     for (int ii = 0; ii < data.length; ii++) {
       int jj = packetArray[ii];
       Biome biome = (Biome)mutableRegistry.func_148745_a(jj);
       if (biome == null) {
         data[ii] = (Biome)mutableRegistry.func_148745_a(0);
      } else {
         data[ii] = biome;
      }
    }

     BiomeContainer biomecontainer = (p_147263_1_.func_244296_i() == null) ? null : new BiomeContainer((IObjectIntIterable)mutableRegistry, data);
     Chunk chunk = this.field_147300_g.func_72863_F().func_228313_a_(i, j, biomecontainer, p_147263_1_.func_186946_a(), p_147263_1_.func_218710_g(), p_147263_1_.func_149276_g(), p_147263_1_.func_149274_i());
     if (chunk != null && p_147263_1_.func_149274_i()) {
       this.field_147300_g.func_217417_b(chunk);
    }

     for (int k = 0; k < 16; k++) {
       this.field_147300_g.func_217427_b(i, k, j);
    }

     for (CompoundNBT compoundnbt : p_147263_1_.func_189554_f()) {
       BlockPos blockpos = new BlockPos(compoundnbt.getInt("x"), compoundnbt.getInt("y"), compoundnbt.getInt("z"));
       TileEntity tileentity = this.field_147300_g.getTileEntity(blockpos);
       if (tileentity != null) {
         tileentity.handleUpdateTag(this.field_147300_g.getBlockState(blockpos), compoundnbt);
      }
    }

     info.cancel();
  }

  @Shadow
  private DynamicRegistries field_239163_t_;
  @Shadow
  private ClientWorld field_147300_g;
}


