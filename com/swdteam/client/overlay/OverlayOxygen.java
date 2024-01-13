package com.swdteam.client.overlay;

import com.mojang.blaze3d.matrix.MatrixStack;

public class OverlayOxygen
  extends Overlay
{
  boolean show = false;

  public void tick() {
     this.show = (System.currentTimeMillis() / 500L % 2L == 0L);
     super.tick();
  }


  public void render(MatrixStack stack) {
     super.render(stack);
     if (this.show);
  }
}


