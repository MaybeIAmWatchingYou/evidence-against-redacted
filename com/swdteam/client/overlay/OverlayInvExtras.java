package com.swdteam.client.overlay;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;








public class OverlayInvExtras
  extends Overlay
{
  public void render(MatrixStack stack) {
     super.render(stack);
     if (this.currentScreen == null)
    {
       ClientPlayerEntity clientPlayerEntity = (Minecraft.func_71410_x()).field_71439_g;
    }
  }







  public void tick() {
     super.tick();
  }
}


