package com.swdteam.client.render.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.swdteam.client.tardis.data.ClientTardisCache;
import com.swdteam.client.tardis.data.ExteriorModels;
import com.swdteam.common.tardis.Tardis;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.common.tileentity.DMTileEntityBase;
import com.swdteam.common.tileentity.TardisTileEntity;
import com.swdteam.model.javajson.JSONModel;
import com.swdteam.model.javajson.ModelData;
import com.swdteam.model.javajson.ModelRendererWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.LightType;


public class RenderBlockTardis
  extends TileEntityRenderer<DMTileEntityBase>
{
  public static JSONModel MODEL_TARDIS;
  
  public RenderBlockTardis(TileEntityRendererDispatcher dispatcher) {
     super(dispatcher);
  }


  
  public void render(DMTileEntityBase dmTileEntityBase, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int combinedLightIn, int combinedOverlayIn) {
    try {
       if (dmTileEntityBase instanceof TardisTileEntity) {
         TardisTileEntity tardis = (TardisTileEntity)dmTileEntityBase;
        
         TardisData data = ClientTardisCache.getTardisData(tardis.globalID);
        
         if (data == null || !Minecraft.func_71410_x().func_195551_G().func_219533_b(data.getTardisExterior().getData().getModel(data.getSkinID()))) {
           data = ClientTardisCache.DEFAULT_DATA;
        }
        
         Tardis tardisData = data.getTardisExterior();
         MODEL_TARDIS = ExteriorModels.getModel(tardisData.getData().getModel(data.getSkinID()));
        
         if (tardis.pulses > 0.0126415478D && tardis.pulses < 1.0F) {
           IVertexBuilder ivertexbuilder2 = iRenderTypeBuffer.getBuffer(RenderType.func_228644_e_(JSONModel.ModelInformation.generateAlphaMap(MODEL_TARDIS.getModelData().getTexture())));
           renderTardis(ivertexbuilder2, tardisData, data, matrixStack, iRenderTypeBuffer, tardis, partialTicks, combinedLightIn, combinedOverlayIn, 1.0F);
          
           if (MODEL_TARDIS.getModelData().getAlphaMap() != null) {
             IVertexBuilder ivertexbuilder3 = iRenderTypeBuffer.getBuffer(RenderType.func_228644_e_(MODEL_TARDIS.getModelData().getAlphaMap()));
             renderTardis(ivertexbuilder3, tardisData, data, matrixStack, iRenderTypeBuffer, tardis, partialTicks, combinedLightIn, combinedOverlayIn, tardis.pulses);
          } 
        } 


        
         IVertexBuilder ivertexbuilder = iRenderTypeBuffer.getBuffer(RenderType.func_228644_e_(MODEL_TARDIS.getModelData().getTexture()));
         renderTardis(ivertexbuilder, tardisData, data, matrixStack, iRenderTypeBuffer, tardis, partialTicks, combinedLightIn, combinedOverlayIn, tardis.pulses, true);
      }
    
     } catch (Exception exception) {}
  }

  
  private void renderTardis(IVertexBuilder ivertexbuilder, Tardis tardisData, TardisData data, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, TardisTileEntity tardis, float partialTicks, int combinedLightIn, int combinedOverlayIn, float tardisDematPulse) {
     renderTardis(ivertexbuilder, tardisData, data, matrixStack, iRenderTypeBuffer, tardis, partialTicks, combinedLightIn, combinedOverlayIn, tardisDematPulse, false);
  }

  
  public static void renderTardis(IVertexBuilder ivertexbuilder, Tardis tardisData, TardisData data, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, TardisTileEntity tardis, float partialTicks, int combinedLightIn, int combinedOverlayIn, float tardisDematPulse, boolean main) {
     matrixStack.func_227860_a_();
    
     matrixStack.func_227861_a_(0.5D, 0.01D, 0.5D);
     matrixStack.func_227861_a_(0.0D, 1.5D, 0.0D);
    
     matrixStack.func_227861_a_(0.0D, -1.5D, 0.0D);
     float scale = (MODEL_TARDIS.getModelData().getModel()).modelScale;
     matrixStack.func_227862_a_(scale, scale, scale);
     matrixStack.func_227861_a_(0.0D, 1.5D, 0.0D);
    
     matrixStack.func_227861_a_(0.0D, -0.01D, 0.0D);
     matrixStack.func_227863_a_(Vector3f.field_229183_f_.func_229187_a_(180.0F));
     matrixStack.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_(tardis.rotation));
    
     ExteriorModels.DoorAnimations doors = ExteriorModels.getDoorAnimation(tardisData.getData().getModel(data.getSkinID()));
     if (doors != null && doors.getDoorAnimations() != null) {
       for (ExteriorModels.DoorAnimations.TardisAnimation animation : doors.getDoorAnimations()) {
        Vector3f initialRotation;
         ModelRendererWrapper renderer = MODEL_TARDIS.getModelData().getModel().getPart(animation.getShapeName());
         float doorRot = animation.getDoorType().equalsIgnoreCase("left") ? tardis.doorLeftRotation : tardis.doorRightRotation;
        
         switch (animation.getMode().toLowerCase()) {
          case "rotate":
             switch (animation.getAxis().toLowerCase()) { case "x":
                 renderer.field_78795_f = (float)Math.toRadians((doorRot * 90.0F * animation.getAmplifier()));
               case "y": renderer.field_78796_g = (float)Math.toRadians((doorRot * 90.0F * animation.getAmplifier()));
               case "z": renderer.field_78808_h = (float)Math.toRadians((doorRot * 90.0F * animation.getAmplifier())); }
          
          
          case "translate":
             initialRotation = renderer.getInitialRotation();
             switch (animation.getAxis().toLowerCase()) { case "x":
                 renderer.field_78800_c = initialRotation.func_195899_a() + doorRot * 20.0F * animation.getAmplifier();
               case "y": renderer.field_78797_d = initialRotation.func_195900_b() + doorRot * 20.0F * animation.getAmplifier();
               case "z": renderer.field_78798_e = initialRotation.func_195902_c() + doorRot * 20.0F * animation.getAmplifier(); }
          
          
        } 
      
      } 
    }
     if (tardis.getWorld().func_175623_d(tardis.getPos().func_177977_b())) {
       tardis.bob = (float)(Math.cos((tardis.bobTime + partialTicks) * 0.05D) * 0.5D + 0.5D);
       tardis.bob *= tardis.bob;
       tardis.boblast = tardis.bob;
    } else {
       tardis.bob = (float)(tardis.bob + tardis.bob - tardis.boblast - 0.001D);
       if (tardis.bob >= 1.0F) tardis.bob = 1.0F;
    
    } 
    
     matrixStack.func_227861_a_(0.0D, (tardis.bob - 1.0F), 0.0D);
    
     if (tardisDematPulse > 0.0126415478D) {
       MODEL_TARDIS.setRenderSnowMap(tardis.isSnowy);
       if (main) { MODEL_TARDIS.getModelData().getModel().renderToBuffer(matrixStack, iRenderTypeBuffer, ivertexbuilder, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, tardisDematPulse); }
       else { MODEL_TARDIS.getModelData().getModel().func_225598_a_(matrixStack, ivertexbuilder, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, tardisDematPulse); }
    
    } 
    
     if (MODEL_TARDIS.getModelData().getFontData() != null && (MODEL_TARDIS.getModelData().getFontData()).length > 0) {
      
       FontRenderer font = (Minecraft.func_71410_x()).field_71466_p;
       int col = changeAlpha(-1, (int)tardis.pulses * 255 + 4);
       for (int i = 0; i < (MODEL_TARDIS.getModelData().getFontData()).length; i++) {
         ModelData.FontData fontData = MODEL_TARDIS.getModelData().getFontData()[i];
         matrixStack.func_227860_a_();
         matrixStack.func_227863_a_(Vector3f.field_229178_a_.func_229187_a_(fontData.getRotationX()));
         matrixStack.func_227863_a_(Vector3f.field_229180_c_.func_229187_a_(fontData.getRotationY()));
         matrixStack.func_227863_a_(Vector3f.field_229182_e_.func_229187_a_(fontData.getRotationZ()));
         matrixStack.func_227861_a_(fontData.getX(), fontData.getY(), fontData.getZ());
        
         matrixStack.func_227862_a_(0.01F * fontData.getScale(), 0.01F * fontData.getScale(), 0.01F * fontData.getScale());
         String s = fontData.getString();
         font.func_238421_b_(matrixStack, s, (-font.func_78256_a(s) / 2), 0.0F, col);
         matrixStack.func_227865_b_();
      } 
    } 
    
     matrixStack.func_227865_b_();
  }

  
  public static void renderTardisModel(JSONModel model, float rotation, IVertexBuilder ivertexbuilder, TardisData data, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int combinedLightIn) {
     matrixStack.func_227860_a_();
    
     matrixStack.func_227861_a_(0.0D, 1.5D, 0.0D);

    
     matrixStack.func_227863_a_(Vector3f.field_229180_c_.func_229187_a_(rotation));
     matrixStack.func_227861_a_(0.0D, -1.5D, 0.0D);
     float scale = (model.getModelData().getModel()).modelScale;
     matrixStack.func_227862_a_(scale, scale, scale);
     matrixStack.func_227861_a_(0.0D, 1.5D, 0.0D);
    
     matrixStack.func_227861_a_(0.0D, -0.01D, 0.0D);
     matrixStack.func_227863_a_(Vector3f.field_229183_f_.func_229187_a_(180.0F));
    
     ExteriorModels.resetDoorAnimation(data.getTardisExterior().getData().getModel(data.getSkinID()));
    
     model.getModelData().getModel().renderToBuffer(matrixStack, iRenderTypeBuffer, ivertexbuilder, combinedLightIn, OverlayTexture.field_229196_a_, 1.0F, 1.0F, 1.0F, 1.0F);
    
     if (model.getModelData().getFontData() != null && (model.getModelData().getFontData()).length > 0) {
      
       FontRenderer font = (Minecraft.func_71410_x()).field_71466_p;
       int col = -1;
       for (int i = 0; i < (model.getModelData().getFontData()).length; i++) {
         ModelData.FontData fontData = model.getModelData().getFontData()[i];
         matrixStack.func_227860_a_();
         matrixStack.func_227863_a_(Vector3f.field_229178_a_.func_229187_a_(fontData.getRotationX()));
         matrixStack.func_227863_a_(Vector3f.field_229180_c_.func_229187_a_(fontData.getRotationY()));
         matrixStack.func_227863_a_(Vector3f.field_229182_e_.func_229187_a_(fontData.getRotationZ()));
         matrixStack.func_227861_a_(fontData.getX(), fontData.getY(), fontData.getZ());
        
         matrixStack.func_227862_a_(0.01F * fontData.getScale(), 0.01F * fontData.getScale(), 0.01F * fontData.getScale());
         String s = fontData.getString();
         font.func_238421_b_(matrixStack, s, (-font.func_78256_a(s) / 2), 0.0F, col);
         matrixStack.func_227865_b_();
      } 
    } 
    
     matrixStack.func_227865_b_();
  }
  
  public static int changeAlpha(int origColor, int userInputedAlpha) {
     origColor &= 0xFFFFFF;
     return userInputedAlpha << 24 | origColor;
  }
  
  public final int getPackedLight(DMTileEntityBase entityIn, float partialTicks) {
     return LightTexture.func_228451_a_(getBlockLight(entityIn, partialTicks), entityIn.getWorld().func_226658_a_(LightType.SKY, entityIn.getPos()));
  }
  
  protected int getBlockLight(DMTileEntityBase entityIn, float partialTicks) {
     return entityIn.getWorld().func_226658_a_(LightType.BLOCK, new BlockPos((Vector3i)entityIn.getPos()));
  }
}


