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
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.client.ISkyRenderHandler;


public class SkyRendererMCClassic
  implements ISkyRenderHandler
{
   public static SkyRendererMCClassic INSTANCE = new SkyRendererMCClassic();
  
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
    
     matrixStackIn.func_227860_a_();
     RenderSystem.enableDepthTest();
     RenderSystem.depthMask(true);
     RenderSystem.enableAlphaTest();
    
     double pX = MathHelper.func_219803_d(partialTicks, mc.field_71439_g.field_70169_q, mc.field_71439_g.getPosX());
     double pZ = MathHelper.func_219803_d(partialTicks, mc.field_71439_g.field_70166_s, mc.field_71439_g.getPosZ());
    
     double x2 = 128.0D * Math.floor(pX / 128.0D) - pX;
     double y2 = MathHelper.func_219803_d(partialTicks, mc.field_71439_g.field_70167_r, mc.field_71439_g.getPosY()) - 88.0D;
     double z2 = 128.0D * Math.floor(pZ / 128.0D) - pZ;

    
     matrixStackIn.func_227861_a_(-x2, 1.0D - y2, -z2);
    
     matrixStackIn.func_227863_a_(Vector3f.field_229183_f_.func_229187_a_(-180.0F));
     mc.field_71446_o.func_110577_a(CLOUDS);
    
     float f = (float)(mc.field_71441_e.func_241851_ab() % 89478L) / 89478.0F;
    
     for (int x = -4; x < 4; x++) {
       for (int y = -4; y < 4; y++) {
         Matrix4f matrix4f = matrixStackIn.func_227866_c_().func_227870_a_();
        
         bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181709_i);
         bufferbuilder.func_227888_a_(matrix4f, (x * 128), -1.0F, (y * 128)).func_225583_a_(f, 0.0F).func_225586_a_(255, 255, 255, 255).func_181675_d();
         bufferbuilder.func_227888_a_(matrix4f, (x * 128), -1.0F, (y * 128 + 128)).func_225583_a_(f, 0.2F).func_225586_a_(255, 255, 255, 255).func_181675_d();
         bufferbuilder.func_227888_a_(matrix4f, (x * 128) + 128.0F, -1.0F, (y * 128 + 128)).func_225583_a_(f + 0.2F, 0.2F).func_225586_a_(255, 255, 255, 255).func_181675_d();
         bufferbuilder.func_227888_a_(matrix4f, (x * 128) + 128.0F, -1.0F, (y * 128)).func_225583_a_(f + 0.2F, 0.0F).func_225586_a_(255, 255, 255, 255).func_181675_d();
         tessellator.func_78381_a();
      } 
    } 
    
     matrixStackIn.func_227865_b_();
    
     matrixStackIn.func_227865_b_();
    
     RenderSystem.depthMask(true);
     RenderSystem.enableTexture();
     RenderSystem.disableBlend();
     RenderSystem.enableAlphaTest();
  }
}


