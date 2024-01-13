package com.swdteam.mixins;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.swdteam.client.render.RenderFlightMode;
import com.swdteam.common.init.DMFlightMode;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;



@Mixin({PlayerRenderer.class})
public class RenderPlayerMixin
{
  @Inject(at = {@At("HEAD")}, method = {"render"}, cancellable = true)
  public void render(AbstractClientPlayerEntity p_225623_1_, float p_225623_2_, float p_225623_3_, MatrixStack p_225623_4_, IRenderTypeBuffer p_225623_5_, int p_225623_6_, CallbackInfo info) {
     if (DMFlightMode.isInFlight(p_225623_1_.getGameProfile().getId())) {
       info.cancel();
       RenderFlightMode.render(p_225623_1_, p_225623_2_, p_225623_3_, p_225623_4_, p_225623_5_, p_225623_6_);
    }
  }
}


