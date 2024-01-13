package com.swdteam.mixins;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.swdteam.util.RenderUtil;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.util.math.vector.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;



@Mixin({GameRenderer.class})
public abstract class GameRendererMixin
{
  @Redirect(method = {"Lnet/minecraft/client/renderer/GameRenderer;renderLevel(FJLcom/mojang/blaze3d/matrix/MatrixStack;)V"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/WorldRenderer;renderLevel(Lcom/mojang/blaze3d/matrix/MatrixStack;FJZLnet/minecraft/client/renderer/ActiveRenderInfo;Lnet/minecraft/client/renderer/GameRenderer;Lnet/minecraft/client/renderer/LightTexture;Lnet/minecraft/util/math/vector/Matrix4f;)V"))
  private void renderLevel(WorldRenderer instance, MatrixStack matrices, float p_228426_2_, long p_228426_3_, boolean p_228426_5_, ActiveRenderInfo p_228426_6_, GameRenderer p_228426_7_, LightTexture p_228426_8_, Matrix4f p_228426_9_) {
     RenderUtil.renderLevel(instance, matrices, p_228426_2_, p_228426_3_, p_228426_5_, p_228426_6_, p_228426_7_, p_228426_8_, p_228426_9_);
  }
}


