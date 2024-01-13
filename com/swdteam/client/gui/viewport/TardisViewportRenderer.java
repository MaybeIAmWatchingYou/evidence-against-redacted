package com.swdteam.client.gui.viewport;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.RenderSkyboxCube;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TardisViewportRenderer
  extends RenderSkyboxCube
{
  public static DynamicTexture[] textures;

  public TardisViewportRenderer() {
     super(new ResourceLocation("textures/gui/title/background/panorama"));
  }

  public void func_217616_a(Minecraft mc, float pitch, float yaw, float alpha) {
     if (textures != null && textures.length >= 6) {
       Tessellator tessellator = Tessellator.func_178181_a();
       BufferBuilder bufferbuilder = tessellator.func_178180_c();
       RenderSystem.matrixMode(5889);
       RenderSystem.pushMatrix();
       RenderSystem.loadIdentity();
       RenderSystem.multMatrix(Matrix4f.func_195876_a(120.0D, mc.func_228018_at_().func_198105_m() / mc.func_228018_at_().func_198083_n(), 0.05F, 10.0F));
       RenderSystem.matrixMode(5888);
       RenderSystem.pushMatrix();
       RenderSystem.loadIdentity();
       RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
       RenderSystem.rotatef(180.0F, 1.0F, 0.0F, 0.0F);
       RenderSystem.enableBlend();
       RenderSystem.disableAlphaTest();

       RenderSystem.depthMask(false);
       RenderSystem.defaultBlendFunc();
       int i = 2;
       for (int j = 0; j < 4; j++) {
         RenderSystem.pushMatrix();
         float f = ((j % 2) / 2.0F - 0.5F) / 256.0F;
         float f1 = ((j / 2) / 2.0F - 0.5F) / 256.0F;

         RenderSystem.translatef(f, f1, 0.0F);
         RenderSystem.rotatef(pitch, 1.0F, 0.0F, 0.0F);
         RenderSystem.rotatef(yaw, 0.0F, 1.0F, 0.0F);
         for (int k = 0; k < 6; k++) {
           textures[k].func_229148_d_();
           bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181709_i);
           int l = Math.round(255.0F * alpha) / (j + 1);
           if (k == 0) {
             bufferbuilder.func_225582_a_(-1.0D, -1.0D, 1.0D).func_225583_a_(0.0F, 0.0F).func_225586_a_(255, 255, 255, l).func_181675_d();
             bufferbuilder.func_225582_a_(-1.0D, 1.0D, 1.0D).func_225583_a_(0.0F, 1.0F).func_225586_a_(255, 255, 255, l).func_181675_d();
             bufferbuilder.func_225582_a_(1.0D, 1.0D, 1.0D).func_225583_a_(1.0F, 1.0F).func_225586_a_(255, 255, 255, l).func_181675_d();
             bufferbuilder.func_225582_a_(1.0D, -1.0D, 1.0D).func_225583_a_(1.0F, 0.0F).func_225586_a_(255, 255, 255, l).func_181675_d();
          }

           if (k == 1) {
             bufferbuilder.func_225582_a_(1.0D, -1.0D, 1.0D).func_225583_a_(0.0F, 0.0F).func_225586_a_(255, 255, 255, l).func_181675_d();
             bufferbuilder.func_225582_a_(1.0D, 1.0D, 1.0D).func_225583_a_(0.0F, 1.0F).func_225586_a_(255, 255, 255, l).func_181675_d();
             bufferbuilder.func_225582_a_(1.0D, 1.0D, -1.0D).func_225583_a_(1.0F, 1.0F).func_225586_a_(255, 255, 255, l).func_181675_d();
             bufferbuilder.func_225582_a_(1.0D, -1.0D, -1.0D).func_225583_a_(1.0F, 0.0F).func_225586_a_(255, 255, 255, l).func_181675_d();
          }

           if (k == 2) {
             bufferbuilder.func_225582_a_(1.0D, -1.0D, -1.0D).func_225583_a_(0.0F, 0.0F).func_225586_a_(255, 255, 255, l).func_181675_d();
             bufferbuilder.func_225582_a_(1.0D, 1.0D, -1.0D).func_225583_a_(0.0F, 1.0F).func_225586_a_(255, 255, 255, l).func_181675_d();
             bufferbuilder.func_225582_a_(-1.0D, 1.0D, -1.0D).func_225583_a_(1.0F, 1.0F).func_225586_a_(255, 255, 255, l).func_181675_d();
             bufferbuilder.func_225582_a_(-1.0D, -1.0D, -1.0D).func_225583_a_(1.0F, 0.0F).func_225586_a_(255, 255, 255, l).func_181675_d();
          }

           if (k == 3) {
             bufferbuilder.func_225582_a_(-1.0D, -1.0D, -1.0D).func_225583_a_(0.0F, 0.0F).func_225586_a_(255, 255, 255, l).func_181675_d();
             bufferbuilder.func_225582_a_(-1.0D, 1.0D, -1.0D).func_225583_a_(0.0F, 1.0F).func_225586_a_(255, 255, 255, l).func_181675_d();
             bufferbuilder.func_225582_a_(-1.0D, 1.0D, 1.0D).func_225583_a_(1.0F, 1.0F).func_225586_a_(255, 255, 255, l).func_181675_d();
             bufferbuilder.func_225582_a_(-1.0D, -1.0D, 1.0D).func_225583_a_(1.0F, 0.0F).func_225586_a_(255, 255, 255, l).func_181675_d();
          }

           if (k == 4) {
             bufferbuilder.func_225582_a_(-1.0D, -1.0D, -1.0D).func_225583_a_(0.0F, 0.0F).func_225586_a_(255, 255, 255, l).func_181675_d();
             bufferbuilder.func_225582_a_(-1.0D, -1.0D, 1.0D).func_225583_a_(0.0F, 1.0F).func_225586_a_(255, 255, 255, l).func_181675_d();
             bufferbuilder.func_225582_a_(1.0D, -1.0D, 1.0D).func_225583_a_(1.0F, 1.0F).func_225586_a_(255, 255, 255, l).func_181675_d();
             bufferbuilder.func_225582_a_(1.0D, -1.0D, -1.0D).func_225583_a_(1.0F, 0.0F).func_225586_a_(255, 255, 255, l).func_181675_d();
          }

           if (k == 5) {
             bufferbuilder.func_225582_a_(-1.0D, 1.0D, 1.0D).func_225583_a_(0.0F, 0.0F).func_225586_a_(255, 255, 255, l).func_181675_d();
             bufferbuilder.func_225582_a_(-1.0D, 1.0D, -1.0D).func_225583_a_(0.0F, 1.0F).func_225586_a_(255, 255, 255, l).func_181675_d();
             bufferbuilder.func_225582_a_(1.0D, 1.0D, -1.0D).func_225583_a_(1.0F, 1.0F).func_225586_a_(255, 255, 255, l).func_181675_d();
             bufferbuilder.func_225582_a_(1.0D, 1.0D, 1.0D).func_225583_a_(1.0F, 0.0F).func_225586_a_(255, 255, 255, l).func_181675_d();
          }


           tessellator.func_78381_a();
        }

         RenderSystem.popMatrix();
         RenderSystem.colorMask(true, true, true, false);
      }

       RenderSystem.colorMask(true, true, true, true);
       RenderSystem.matrixMode(5889);
       RenderSystem.popMatrix();
       RenderSystem.matrixMode(5888);
       RenderSystem.popMatrix();
       RenderSystem.depthMask(true);
       RenderSystem.enableCull();
       RenderSystem.enableDepthTest();
    } else {
       super.func_217616_a(mc, pitch, yaw, alpha);
    }
  }
}


