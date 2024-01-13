package com.swdteam.client.overlay;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;

public class OverlayXPAmount
  extends Overlay
{
  private static boolean show = false;
   private static int timer = 0;



  public void render(MatrixStack stack) {
     super.render(stack);
     if (show && (Minecraft.func_71410_x()).field_71439_g != null) {

       String xp = "XP: " + (Minecraft.func_71410_x()).field_71439_g.experienceTotal;


       int opacity = (int)(3.642857F * timer);
       if (opacity <= 0) {
         opacity = 1;
      }

       if (opacity > 255) {
         opacity = 255;
      }

       int col = 0x7EFC20 | opacity << 24;
       int col2 = 0x0 | opacity << 24;
       float height = (timer > 80) ? 80.0F : timer;
       float hOffset = 0.0F;
       if ((Minecraft.func_71410_x()).field_71439_g.isCreative() || (Minecraft.func_71410_x()).field_71439_g.func_175149_v()) {
         hOffset = 14.0F;
      }

       (Minecraft.func_71410_x()).field_71466_p.func_238421_b_(stack, xp, (this.screenWidth / 2 - (Minecraft.func_71410_x()).field_71466_p.func_78256_a(xp) / 2 + 1), this.screenHeight - 49.0F + height / 25.0F + hOffset, col2);
       (Minecraft.func_71410_x()).field_71466_p.func_238421_b_(stack, xp, (this.screenWidth / 2 - (Minecraft.func_71410_x()).field_71466_p.func_78256_a(xp) / 2), this.screenHeight - 50.0F + height / 25.0F + hOffset, col);
    }
  }


  public void tick() {
     super.tick();
     if (show) {
       timer++;
       if (timer > 250) {
         show = false;
         timer = 0;
      }
    }
  }

  public static void show() {
     if (!show) {
       show = true;
       timer = 0;
    }
  }
}


