package com.swdteam.mixins;

import com.swdteam.client.tardis.data.ClientTardisCache;
import com.swdteam.common.init.DMDimensions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin({LightTexture.class})
public abstract class LightMapMixin
{
   private static double GAMMA_BACKUP = 0.0D;

  @Inject(at = {@At("HEAD")}, method = {"Lnet/minecraft/client/renderer/LightTexture;updateLightTexture(F)V"})
  private void updateLightTexturePre(float f, CallbackInfo info) {
     GAMMA_BACKUP = (Minecraft.func_71410_x()).field_71474_y.field_74333_Y;
     if ((Minecraft.func_71410_x()).field_71439_g.world.getDimensionKey().equals(DMDimensions.TARDIS)) {
       (Minecraft.func_71410_x()).field_71474_y.field_74333_Y = GAMMA_BACKUP + ClientTardisCache.GLOBAL_TARDIS_LIGHTING;
    }
  }

  @Inject(at = {@At("TAIL")}, method = {"Lnet/minecraft/client/renderer/LightTexture;updateLightTexture(F)V"})
  private void updateLightTexturePost(float f, CallbackInfo info) {
     (Minecraft.func_71410_x()).field_71474_y.field_74333_Y = GAMMA_BACKUP;
  }
}


