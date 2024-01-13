package com.swdteam.client.render.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.swdteam.client.tardis.data.ClientTardisCache;
import com.swdteam.client.tardis.data.ClientTardisFlightCache;
import com.swdteam.common.block.tardis.CoordPanelBlock;
import com.swdteam.common.init.DMDimensions;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.common.tardis.TardisFlightData;
import com.swdteam.common.tileentity.tardis.CoordPanelTileEntity;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.state.Property;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Vector3f;

public class RenderCoordPanel extends TileEntityRenderer<CoordPanelTileEntity> {
  public RenderCoordPanel(TileEntityRendererDispatcher dispatcher) {
     super(dispatcher);
  }



  public void render(CoordPanelTileEntity te, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int combinedLightIn, int combinedOverlayIn) {
     float blockRot = ((Direction)te.func_195044_w().get((Property)CoordPanelBlock.FACING)).func_185119_l();

     matrixStack.func_227860_a_();
     matrixStack.func_227861_a_(0.5D, 0.127D, 0.5D);

     matrixStack.func_227863_a_(Vector3f.field_229179_b_.func_229187_a_(180.0F));
     matrixStack.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_(blockRot));

     FontRenderer font = this.field_228858_b_.field_147557_n;

     matrixStack.func_227863_a_(Vector3f.field_229179_b_.func_229187_a_(-90.0F));
     matrixStack.func_227862_a_(0.005F, 0.005F, 0.005F);

     if (te.getWorld().getDimensionKey() == DMDimensions.TARDIS) {
       TardisData data = ClientTardisCache.getTardisData(te.getPos());
       if (data != null && data.getCurrentLocation() != null) {
         String x = "X: " + data.getCurrentLocation().getBlockPosition().getX();
         String y = "Y: " + data.getCurrentLocation().getBlockPosition().getY();
         String z = "Z: " + data.getCurrentLocation().getBlockPosition().getZ();

         if (ClientTardisFlightCache.hasTardisFlightData(data.getGlobalID())) {
           TardisFlightData flight = ClientTardisFlightCache.getTardisFlightData(data.getGlobalID());
           x = "X: " + (int)flight.getPos(Direction.Axis.X);
           y = "Y: " + (int)flight.getPos(Direction.Axis.Y);
           z = "Z: " + (int)flight.getPos(Direction.Axis.Z);
        }

         font.func_238421_b_(matrixStack, x, (-font.func_78256_a(x) / 2), -4.0F, -1);
         font.func_238421_b_(matrixStack, y, (-font.func_78256_a(y) / 2), 34.0F, -1);
         font.func_238421_b_(matrixStack, z, (-font.func_78256_a(z) / 2), 72.0F, -1);
      }
    }
     matrixStack.func_227865_b_();
  }
}


