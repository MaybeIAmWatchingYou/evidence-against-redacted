package com.swdteam.client.render.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.swdteam.client.model.IModelPartReloader;
import com.swdteam.client.model.ModelReloaderRegistry;
import com.swdteam.client.render.ScannerPages;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.common.tileentity.ScannerTileEntity;
import com.swdteam.model.javajson.JSONModel;
import com.swdteam.model.javajson.ModelLoader;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.state.Property;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;


public class RenderScanner
  extends TileEntityRenderer<ScannerTileEntity>
  implements IModelPartReloader
{
  public static JSONModel MODEL_SCANNER;

  public RenderScanner(TileEntityRendererDispatcher dispatcher) {
     super(dispatcher);
     ModelReloaderRegistry.register(this);
  }


  public void render(ScannerTileEntity te, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int combinedLightIn, int combinedOverlayIn) {
     if (MODEL_SCANNER != null) {
       matrixStack.func_227860_a_();
       matrixStack.func_227861_a_(0.5D, 1.5D, 0.5D);
       matrixStack.func_227863_a_(new Quaternion(0.0F, 0.0F, 180.0F, true));
       matrixStack.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_(180.0F + ((Direction)te.func_195044_w().get((Property)HorizontalBlock.field_185512_D)).func_185119_l()));

       MODEL_SCANNER.getModelData().getModel().renderToBuffer(matrixStack, iRenderTypeBuffer, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
       matrixStack.func_227861_a_(0.0D, 0.6000000238418579D, -0.5099999904632568D);
       matrixStack.func_227860_a_();
       FontRenderer font = this.field_228858_b_.func_147548_a();
       matrixStack.func_227862_a_(0.0065F, 0.0065F, 0.0065F);
       te.renderCallUpdate();

       if (te.getTardisData() != null) {
         TardisData data = te.getTardisData();

         ScannerPages.PAGES[te.getScreen()].render(matrixStack, font, data);
      }
       matrixStack.func_227865_b_();
       matrixStack.func_227865_b_();
    }
  }


  public void init() {
     MODEL_SCANNER = ModelLoader.loadModel(new ResourceLocation("dalekmod", "models/tileentity/scanner.json"));
  }


  public JSONModel getModel() {
     return MODEL_SCANNER;
  }
}


