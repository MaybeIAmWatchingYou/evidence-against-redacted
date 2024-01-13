package com.swdteam.client.gui.title;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.RenderSkyboxCube;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;



@OnlyIn(Dist.CLIENT)
public class RenderPanorama
  extends RenderSkyboxCube
{
   private final ResourceLocation[] images = new ResourceLocation[6];
   public static float time = 180.0F;
  
  public RenderPanorama() {
     super(new ResourceLocation("textures/gui/title/background/panorama"));
     for (int i = 0; i < 6; i++) {
       this.images[i] = new ResourceLocation("dalekmod", "textures/gui/panorama/panorama_" + i + ".png");
    }
  }


  
  protected void init() {}

  
  public void func_217616_a(Minecraft p_217616_1_, float p_217616_2_, float p_217616_3_, float p_217616_4_) {
     p_217616_2_ = 15.0F;
     p_217616_3_ = -time;
     Tessellator tessellator = Tessellator.func_178181_a();
     BufferBuilder bufferbuilder = tessellator.func_178180_c();
     RenderSystem.matrixMode(5889);
     RenderSystem.pushMatrix();
     RenderSystem.loadIdentity();
     RenderSystem.multMatrix(Matrix4f.func_195876_a(85.0D, p_217616_1_.func_228018_at_().func_198109_k() / p_217616_1_.func_228018_at_().func_198091_l(), 0.05F, 10.0F));
     RenderSystem.matrixMode(5888);
     RenderSystem.pushMatrix();
     RenderSystem.loadIdentity();
     RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
     RenderSystem.rotatef(180.0F, 1.0F, 0.0F, 0.0F);
     RenderSystem.enableBlend();
     RenderSystem.disableAlphaTest();
     RenderSystem.disableCull();
     RenderSystem.depthMask(false);
     RenderSystem.defaultBlendFunc();
    
     int i = 2;
    
     for (int j = 0; j < 4; j++) {
       RenderSystem.pushMatrix();
       float f = ((j % 2) / 2.0F - 0.5F) / 256.0F;
       float f1 = ((j / 2) / 2.0F - 0.5F) / 256.0F;
      
       float f2 = 0.0F;
       RenderSystem.translatef(f, f1, 0.0F);
       RenderSystem.rotatef(p_217616_2_, 1.0F, 0.0F, 0.0F);
       RenderSystem.rotatef(p_217616_3_, 0.0F, 1.0F, 0.0F);
      
       for (int k = 0; k < 6; k++) {
         p_217616_1_.func_110434_K().func_110577_a(this.images[k]);
         bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181709_i);
         int l = Math.round(255.0F * p_217616_4_) / (j + 1);
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
  }
  
  public CompletableFuture<Void> func_217617_a(TextureManager manager, Executor executor) {
     CompletableFuture[] arrayOfCompletableFuture = new CompletableFuture[6];
    
     for (int i = 0; i < arrayOfCompletableFuture.length; i++) {
       arrayOfCompletableFuture[i] = manager.func_215268_a(this.images[i], executor);
    }
    
     return CompletableFuture.allOf((CompletableFuture<?>[])arrayOfCompletableFuture);
  }
}


