package com.swdteam.client.render.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.swdteam.client.model.IModelPartReloader;
import com.swdteam.client.model.ModelReloaderRegistry;
import com.swdteam.common.tileentity.tardis.TardisPlantTileEntity;
import com.swdteam.model.javajson.JSONModel;
import com.swdteam.model.javajson.ModelLoader;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;

public class RenderTardisPlant
  extends TileEntityRenderer<TardisPlantTileEntity>
  implements IModelPartReloader {
  public static JSONModel[] PLANT_STATE;

  public RenderTardisPlant(TileEntityRendererDispatcher dispatcher) {
     super(dispatcher);

     ModelReloaderRegistry.register(this);
  }


  public void render(TardisPlantTileEntity tile, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int combinedLightIn, int combinedOverlayIn) {
     JSONModel model = PLANT_STATE[tile.getAge()];

     if (model != null) {
       matrixStack.func_227860_a_();

       matrixStack.func_227861_a_(0.5D, 0.0D, 0.5D);
       matrixStack.func_227861_a_(0.0D, 1.5D, 0.0D);
       matrixStack.func_227863_a_(Vector3f.field_229179_b_.func_229187_a_(180.0F));

       matrixStack.func_227861_a_(0.0D, 0.0D, 0.0D);
       model.getModelData().getModel().renderToBuffer(matrixStack, iRenderTypeBuffer, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
       matrixStack.func_227865_b_();
    }
  }


  public void init() {
     PLANT_STATE = new JSONModel[7];
     PLANT_STATE[0] = ModelLoader.loadModel(new ResourceLocation("dalekmod", "models/tileentity/tardis_plant/tardis_plant_stage0.json"));
     PLANT_STATE[1] = ModelLoader.loadModel(new ResourceLocation("dalekmod", "models/tileentity/tardis_plant/tardis_plant_stage1.json"));
     PLANT_STATE[2] = ModelLoader.loadModel(new ResourceLocation("dalekmod", "models/tileentity/tardis_plant/tardis_plant_stage2.json"));
     PLANT_STATE[3] = ModelLoader.loadModel(new ResourceLocation("dalekmod", "models/tileentity/tardis_plant/tardis_plant_stage3.json"));
     PLANT_STATE[4] = ModelLoader.loadModel(new ResourceLocation("dalekmod", "models/tileentity/tardis_plant/tardis_plant_stage4.json"));
     PLANT_STATE[5] = ModelLoader.loadModel(new ResourceLocation("dalekmod", "models/tileentity/tardis_plant/tardis_plant_stage5.json"));
     PLANT_STATE[6] = ModelLoader.loadModel(new ResourceLocation("dalekmod", "models/tileentity/tardis_plant/tardis_plant_stage6.json"));
  }


  public JSONModel getModel() {
     return PLANT_STATE[0];
  }
}


