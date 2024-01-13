package com.swdteam.client.render.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.swdteam.common.tileentity.tardis.TardisVisualizerTileEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tileentity.TileEntity;







public class RenderTardisVisualizer
  extends TileEntityRenderer<TardisVisualizerTileEntity>
{
  public RenderTardisVisualizer(TileEntityRendererDispatcher dispatcher) {
     super(dispatcher);
  }

  public void render(TardisVisualizerTileEntity dmTileEntityBase, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int combinedLightIn, int combinedOverlayIn) {}
}


