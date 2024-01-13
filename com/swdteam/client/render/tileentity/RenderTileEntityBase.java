package com.swdteam.client.render.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.swdteam.model.javajson.ModelWrapper;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.state.Property;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Quaternion;

public class RenderTileEntityBase
  extends TileEntityRenderer<TileEntity>
{
  private ModelWrapper model;

  public RenderTileEntityBase(TileEntityRendererDispatcher rendererDispatcherIn) {
     super(rendererDispatcherIn);
  }

  public RenderTileEntityBase(TileEntityRendererDispatcher rendererDispatcherIn, ModelWrapper model) {
     super(rendererDispatcherIn);
     this.model = model;
  }



  public void func_225616_a_(TileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer iRenderTypeBuffer, int combinedLightIn, int combinedOverlayIn) {
     float rot = 0.0F;

     if (tileEntityIn instanceof com.swdteam.common.tileentity.DMTileEntityBase && tileEntityIn.func_195044_w().getBlock() instanceof com.swdteam.common.block.RotatableTileEntityBase) {
       switch ((Direction)tileEntityIn.func_195044_w().get((Property)HorizontalBlock.field_185512_D)) { default:
           rot = 0.0F; break;
         case EAST: rot = 90.0F; break;
         case SOUTH: rot = 180.0F; break;
         case WEST: rot = 270.0F;
          break; }

    }
     matrixStackIn.func_227860_a_();
     matrixStackIn.func_227861_a_(0.5D, 0.0D, 0.5D);
     matrixStackIn.func_227862_a_(getScale(), getScale(), getScale());
     matrixStackIn.func_227861_a_(0.0D, 1.5D, 0.0D);
     matrixStackIn.func_227863_a_(new Quaternion(180.0F, rot, 0.0F, true));
     this.model.renderToBuffer(matrixStackIn, iRenderTypeBuffer, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
     matrixStackIn.func_227865_b_();
  }

  public float getScale() {
     return 1.0F;
  }

  public void setModel(ModelWrapper modelBase) {
     this.model = modelBase;
  }
}


