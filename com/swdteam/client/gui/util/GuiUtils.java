package com.swdteam.client.gui.util;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.swdteam.model.javajson.JSONModel;
import com.swdteam.model.javajson.ModelData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;




public class GuiUtils
{
  public static void drawEntityOnScreen(MatrixStack matrixstack, float posX, float posY, float scale, float rotation, Model model, ResourceLocation rl, float r, float g, float b, float a) {
     RenderSystem.pushMatrix();
     RenderSystem.translatef(posX, posY, 1050.0F);
     RenderSystem.scalef(1.0F, 1.0F, -1.0F);
    
     MatrixStack ms = matrixstack;
     ms.func_227860_a_();
     ms.func_227861_a_(0.0D, 0.0D, 1000.0D);
     ms.func_227861_a_(0.0D, 1.5D, 0.0D);
     ms.func_227862_a_(scale, scale, scale);
     ms.func_227861_a_(0.0D, -1.5D, 0.0D);
     Quaternion quaternion1 = Vector3f.field_229182_e_.func_229187_a_(0.0F);
     Quaternion quaternion2 = Vector3f.field_229178_a_.func_229187_a_(-25.0F);
     Quaternion quaternion = Vector3f.field_229180_c_.func_229187_a_(rotation);
     ms.func_227863_a_(quaternion2);
     ms.func_227863_a_(quaternion);
     ms.func_227863_a_(quaternion1);
    
     EntityRendererManager entityrenderermanager = Minecraft.func_71410_x().func_175598_ae();
     quaternion1.func_195892_e();
     entityrenderermanager.func_229089_a_(quaternion1);
     entityrenderermanager.func_178633_a(false);
     IRenderTypeBuffer.Impl irendertypebuffer$impl = Minecraft.func_71410_x().func_228019_au_().func_228487_b_();
    
     RenderSystem.runAsFancy(() -> {
          IRenderTypeBuffer.Impl renderType = Minecraft.func_71410_x().func_228019_au_().func_228487_b_();
          
          model.func_225598_a_(ms, renderType.getBuffer(RenderType.func_228642_d_(rl)), 15728880, OverlayTexture.field_229196_a_, r, g, b, a);
          renderType.func_228461_a_();
        });
     irendertypebuffer$impl.func_228461_a_();
     entityrenderermanager.func_178633_a(true);
     ms.func_227865_b_();
    
     RenderSystem.popMatrix();
  }


  
  public static void drawEntityOnScreen(MatrixStack matrixstack, float posX, float posY, float scale, float rotation, JSONModel model) {
     if (model != null) {
       RenderSystem.pushMatrix();
       RenderSystem.translatef(posX, posY, 1050.0F);
       RenderSystem.scalef(1.0F, 1.0F, -1.0F);
      
       MatrixStack ms = matrixstack;
       ms.func_227860_a_();
       ms.func_227861_a_(0.0D, 0.0D, 1000.0D);
       ms.func_227861_a_(0.0D, 1.5D, 0.0D);
       ms.func_227862_a_(scale, scale, scale);
       ms.func_227861_a_(0.0D, -1.5D, 0.0D);
       Quaternion quaternion1 = Vector3f.field_229182_e_.func_229187_a_(0.0F);
       Quaternion quaternion2 = Vector3f.field_229178_a_.func_229187_a_(-25.0F);
       Quaternion quaternion = Vector3f.field_229180_c_.func_229187_a_(rotation);
       ms.func_227863_a_(quaternion2);
       ms.func_227863_a_(quaternion);
       ms.func_227863_a_(quaternion1);
      
       EntityRendererManager entityrenderermanager = Minecraft.func_71410_x().func_175598_ae();
       quaternion1.func_195892_e();
       entityrenderermanager.func_229089_a_(quaternion1);
       entityrenderermanager.func_178633_a(false);
       IRenderTypeBuffer.Impl irendertypebuffer$impl = Minecraft.func_71410_x().func_228019_au_().func_228487_b_();
      
       RenderSystem.runAsFancy(() -> {
            IRenderTypeBuffer.Impl renderType = Minecraft.func_71410_x().func_228019_au_().func_228487_b_();
            
            model.getModelData().getModel().func_225598_a_(ms, renderType.getBuffer(RenderType.func_228644_e_(model.getModelData().getTexture())), 15728880, OverlayTexture.field_229196_a_, 1.0F, 1.0F, 1.0F, 1.0F);
            renderType.func_228461_a_();
          });
       irendertypebuffer$impl.func_228461_a_();
       entityrenderermanager.func_178633_a(true);
       if (model.getModelData().getFontData() != null && (model.getModelData().getFontData()).length > 0) {
         FontRenderer font = (Minecraft.func_71410_x()).field_71466_p;
         int col = -1;
         for (int i = 0; i < (model.getModelData().getFontData()).length; i++) {
           ModelData.FontData fontData = model.getModelData().getFontData()[i];
           ms.func_227860_a_();
           ms.func_227863_a_(Vector3f.field_229178_a_.func_229187_a_(fontData.getRotationX()));
           ms.func_227863_a_(Vector3f.field_229180_c_.func_229187_a_(fontData.getRotationY()));
           ms.func_227863_a_(Vector3f.field_229182_e_.func_229187_a_(fontData.getRotationZ()));
           ms.func_227861_a_(fontData.getX(), fontData.getY(), fontData.getZ());
          
           ms.func_227862_a_(0.01F * fontData.getScale(), 0.01F * fontData.getScale(), 0.01F * fontData.getScale());
           String s = fontData.getString();
           font.func_238421_b_(ms, s, (-font.func_78256_a(s) / 2), 0.0F, col);
           ms.func_227865_b_();
        } 
      } 
      
       ms.func_227865_b_();
      
       RenderSystem.popMatrix();
    } 
  }


  
  public static void drawEntityOnScreen(MatrixStack matrixstack, float posX, float posY, float scale, float rotation, JSONModel model, float r, float g, float b, float a) {
     if (model != null)
       drawEntityOnScreen(matrixstack, posX, posY, scale, rotation, (Model)model.getModelData().getModel(), model.getModelData().getTexture(), r, g, b, a); 
  }
}


