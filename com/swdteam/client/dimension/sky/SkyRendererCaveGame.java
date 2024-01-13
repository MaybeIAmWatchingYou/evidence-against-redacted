package com.swdteam.client.dimension.sky;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.swdteam.client.init.ModClientEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.client.ISkyRenderHandler;


public class SkyRendererCaveGame
  implements ISkyRenderHandler
{
   public static SkyRendererCaveGame INSTANCE = new SkyRendererCaveGame();
  
   public static MatrixStack matrixStackIn = new MatrixStack();
   public static ResourceLocation SKY = new ResourceLocation("dalekmod", "textures/sky/classic.png");
   public static ResourceLocation CLOUDS = new ResourceLocation("dalekmod", "textures/sky/clouds.png");



  
  public void render(int ticks, float partialTicks, MatrixStack matrixStack, ClientWorld world, Minecraft mc) {
     if (ModClientEvents.ClientPlayerData.inCustomLava) {
       RenderSystem.enableFog();
    }
    
     RenderSystem.disableAlphaTest();
    
     RenderSystem.defaultBlendFunc();
     RenderSystem.depthMask(false);
    
     mc.field_71446_o.func_110577_a(SKY);
     Tessellator tessellator = Tessellator.func_178181_a();
     BufferBuilder bufferbuilder = tessellator.func_178180_c();
    
     ActiveRenderInfo renderInfo = (Minecraft.func_71410_x()).field_71460_t.func_215316_n();
     Vector2f angle = new Vector2f(renderInfo.func_216777_e(), renderInfo.func_216778_f());

    
     matrixStackIn.func_227860_a_();
     RenderSystem.enableDepthTest();
     matrixStackIn.func_227863_a_(new Quaternion(angle.field_189982_i, angle.field_189983_j, 0.0F, true));
    
     for (int i = 0; i < 6; i++) {
       matrixStackIn.func_227860_a_();
       if (i == 1) {
         matrixStackIn.func_227863_a_(Vector3f.field_229179_b_.func_229187_a_(90.0F));
      }
      
       if (i == 2) {
         matrixStackIn.func_227863_a_(Vector3f.field_229179_b_.func_229187_a_(-90.0F));
      }
      
       if (i == 3) {
         matrixStackIn.func_227863_a_(Vector3f.field_229179_b_.func_229187_a_(180.0F));
      }
      
       if (i == 4) {
         matrixStackIn.func_227863_a_(Vector3f.field_229183_f_.func_229187_a_(90.0F));
      }
      
       if (i == 5) {
         matrixStackIn.func_227863_a_(Vector3f.field_229183_f_.func_229187_a_(-90.0F));
      }
      
       float skyDepth = 10.0F;
       skyDepth = skyDepth * (Minecraft.func_71410_x()).field_71474_y.field_151451_c / 4.0F;
      
       Matrix4f matrix4f = matrixStackIn.func_227866_c_().func_227870_a_();
       bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181709_i);
       bufferbuilder.func_227888_a_(matrix4f, -skyDepth, -skyDepth, -skyDepth).func_225583_a_(0.0F, 0.0F).func_225586_a_(255, 255, 255, 255).func_181675_d();
       bufferbuilder.func_227888_a_(matrix4f, -skyDepth, -skyDepth, skyDepth).func_225583_a_(0.0F, 2.0F).func_225586_a_(255, 255, 255, 255).func_181675_d();
       bufferbuilder.func_227888_a_(matrix4f, skyDepth, -skyDepth, skyDepth).func_225583_a_(2.0F, 2.0F).func_225586_a_(255, 255, 255, 255).func_181675_d();
       bufferbuilder.func_227888_a_(matrix4f, skyDepth, -skyDepth, -skyDepth).func_225583_a_(2.0F, 0.0F).func_225586_a_(255, 255, 255, 255).func_181675_d();
       tessellator.func_78381_a();
      
       matrixStackIn.func_227865_b_();
    } 
     RenderSystem.disableDepthTest();
    
     matrixStackIn.func_227865_b_();
    
     RenderSystem.depthMask(true);
     RenderSystem.enableTexture();
     RenderSystem.disableBlend();
     RenderSystem.enableAlphaTest();
  }
}


