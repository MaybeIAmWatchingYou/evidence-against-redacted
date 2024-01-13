package com.swdteam.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.swdteam.client.gui.viewport.TardisViewportRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;



public class GuiTardisViewport
  extends Screen
{
  public static float rotation;
   public static ResourceLocation TEXTURE = new ResourceLocation("dalekmod", "textures/gui/tardis_viewport.png");
   public static TardisViewportRenderer viewport = new TardisViewportRenderer();
  
  public GuiTardisViewport() {
     super((ITextComponent)new StringTextComponent("TARDIS Viewport"));
  }


  
  public void func_230430_a_(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
     if (TardisViewportRenderer.textures == null) {
       func_230446_a_(p_230430_1_);
    } else {
       RenderSystem.enableScissor((int)((this.field_230708_k_ / 2 - 128 + 4) * Minecraft.func_71410_x().func_228018_at_().func_198100_s()), (int)((this.field_230709_l_ / 2 - 99 + 4) * Minecraft.func_71410_x().func_228018_at_().func_198100_s()), (int)(248.0D * Minecraft.func_71410_x().func_228018_at_().func_198100_s()), (int)(190.0D * Minecraft.func_71410_x().func_228018_at_().func_198100_s()));
       viewport.func_217616_a(this.field_230706_i_, 0.0F, rotation, 1.0F);
       RenderSystem.disableScissor();
      
       this.field_230706_i_.func_110434_K().func_110577_a(TEXTURE);
       func_238474_b_(p_230430_1_, this.field_230708_k_ / 2 - 128 + 1, this.field_230709_l_ / 2 - 99, 0, 0, 256, 199);
    } 
    
     super.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
  }

  
  public void func_231023_e_() {
     super.func_231023_e_();
     rotation += 0.2F;
     if (rotation > 360.0F)
       rotation = 0.0F;
  }
}


