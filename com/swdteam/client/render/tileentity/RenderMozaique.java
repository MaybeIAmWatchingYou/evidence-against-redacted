package com.swdteam.client.render.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.swdteam.client.model.IModelPartReloader;
import com.swdteam.client.model.ModelReloaderRegistry;
import com.swdteam.common.block.MozaiqueBlock;
import com.swdteam.common.tileentity.MozaiqueTileEntity;
import com.swdteam.model.javajson.JSONModel;
import com.swdteam.model.javajson.ModelLoader;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.state.Property;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;

public class RenderMozaique extends TileEntityRenderer<MozaiqueTileEntity> implements IModelPartReloader {
  public static JSONModel CUBE;
  
  public RenderMozaique(TileEntityRendererDispatcher dispatcher) {
     super(dispatcher);
    
     ModelReloaderRegistry.register(this);
  }


  
  public void render(MozaiqueTileEntity tile, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int combinedLightIn, int combinedOverlayIn) {
     if (CUBE != null) {
       matrixStack.func_227860_a_();
       matrixStack.func_227861_a_(0.5D, 0.0D, 0.5D);
       matrixStack.func_227861_a_(0.0D, 1.5D, 0.0D);
       matrixStack.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_(180.0F + ((Direction)tile.func_195044_w().get((Property)MozaiqueBlock.FACING)).func_185119_l()));
       matrixStack.func_227863_a_(Vector3f.field_229179_b_.func_229187_a_(180.0F));
      
       int col = tile.colBotLeft.getHex();
       float r = ((col & 0xFF0000) >> 16) / 255.0F;
       float g = ((col & 0xFF00) >> 8) / 255.0F;
       float b = (col & 0xFF) / 255.0F;
      
       matrixStack.func_227861_a_(0.25D, 0.0D, 0.25D);
       CUBE.getModelData().getModel().renderToBuffer(matrixStack, iRenderTypeBuffer, combinedLightIn, combinedOverlayIn, r, g, b, 1.0F);

      
       col = tile.colBotRight.getHex();
       r = ((col & 0xFF0000) >> 16) / 255.0F;
       g = ((col & 0xFF00) >> 8) / 255.0F;
       b = (col & 0xFF) / 255.0F;
       matrixStack.func_227861_a_(-0.5D, 0.0D, 0.0D);
       CUBE.getModelData().getModel().renderToBuffer(matrixStack, iRenderTypeBuffer, combinedLightIn, combinedOverlayIn, r, g, b, 1.0F);

      
       col = tile.colTopRight.getHex();
       r = ((col & 0xFF0000) >> 16) / 255.0F;
       g = ((col & 0xFF00) >> 8) / 255.0F;
       b = (col & 0xFF) / 255.0F;
       matrixStack.func_227861_a_(0.0D, -0.5D, 0.0D);
       CUBE.getModelData().getModel().renderToBuffer(matrixStack, iRenderTypeBuffer, combinedLightIn, combinedOverlayIn, r, g, b, 1.0F);

      
       col = tile.colTopLeft.getHex();
       r = ((col & 0xFF0000) >> 16) / 255.0F;
       g = ((col & 0xFF00) >> 8) / 255.0F;
       b = (col & 0xFF) / 255.0F;
       matrixStack.func_227861_a_(0.5D, 0.0D, 0.0D);
       CUBE.getModelData().getModel().renderToBuffer(matrixStack, iRenderTypeBuffer, combinedLightIn, combinedOverlayIn, r, g, b, 1.0F);
       matrixStack.func_227865_b_();
    } 
  }

  
  public void init() {
     CUBE = ModelLoader.loadModel(new ResourceLocation("dalekmod", "models/tileentity/cube.json"));
  }

  
  public JSONModel getModel() {
     return CUBE;
  }
}


