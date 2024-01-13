package com.swdteam.client.overlay;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;







public class Overlay
{
  public Screen currentScreen;
  public Minecraft game;
  public int screenWidth;
  public int screenHeight;

  public void render(MatrixStack stack) {}

  public void tick() {
     this.currentScreen = (Minecraft.func_71410_x()).field_71462_r;
     this.game = Minecraft.func_71410_x();
     this.screenWidth = Minecraft.func_71410_x().func_228018_at_().func_198107_o();
     this.screenHeight = Minecraft.func_71410_x().func_228018_at_().func_198087_p();
  }
}


