package com.swdteam.client.render.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.swdteam.client.model.IModelPartReloader;
import com.swdteam.client.model.ModelReloaderRegistry;
import com.swdteam.common.block.tardis.DimensionSelectorPanelBlock;
import com.swdteam.common.tileentity.tardis.DimensionSelectorTileEntity;
import com.swdteam.model.javajson.JSONModel;
import com.swdteam.model.javajson.ModelLoader;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.state.Property;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;

public class RenderDimensionSelectorPanel extends TileEntityRenderer<DimensionSelectorTileEntity> implements IModelPartReloader {
  public static JSONModel SCREEN_MODEL;
  
  public RenderDimensionSelectorPanel(TileEntityRendererDispatcher dispatcher) {
     super(dispatcher);
     ModelReloaderRegistry.register(this);
  }

  
  public void render(DimensionSelectorTileEntity dmTileEntityBase, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int combinedLightIn, int combinedOverlayIn) {
     if (SCREEN_MODEL != null) {
       ResourceLocation rl = dmTileEntityBase.getTexturePath();
       if (rl == null) {
         rl = SCREEN_MODEL.getModelData().getTexture();
      }
       float rotation = ((Direction)dmTileEntityBase.func_195044_w().get((Property)DimensionSelectorPanelBlock.FACING)).func_185119_l();
      
       IVertexBuilder ivertexbuilder = iRenderTypeBuffer.getBuffer(RenderType.func_228638_b_(rl));
       matrixStack.func_227860_a_();
       matrixStack.func_227861_a_(0.5D, 0.0D, 0.5D);
       matrixStack.func_227863_a_(Vector3f.field_229180_c_.func_229187_a_(rotation));
       matrixStack.func_227861_a_(0.0D, 0.12600000202655792D, -0.3100000023841858D);
       matrixStack.func_227862_a_(0.4F, 0.5F, 0.4F);
       matrixStack.func_227863_a_(Vector3f.field_229178_a_.func_229187_a_(90.0F));
       matrixStack.func_227863_a_(Vector3f.field_229182_e_.func_229187_a_(180.0F));
       SCREEN_MODEL.getModelData().getModel().func_225598_a_(matrixStack, ivertexbuilder, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
      
       matrixStack.func_227865_b_();
    } 
  }

  
  public void init() {
     SCREEN_MODEL = ModelLoader.loadModel(new ResourceLocation("dalekmod", "models/mesh/plane_16.json"));
  }

  
  public JSONModel getModel() {
     return SCREEN_MODEL;
  }
}


