package com.swdteam.client.render.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.swdteam.client.model.IModelPartReloader;
import com.swdteam.client.model.ModelReloaderRegistry;
import com.swdteam.common.tileentity.ArtronFuelTankTileEntity;
import com.swdteam.model.javajson.JSONModel;
import com.swdteam.model.javajson.ModelLoader;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;

public class RenderArtronTank
  extends TileEntityRenderer<ArtronFuelTankTileEntity>
  implements IModelPartReloader {
  public static JSONModel[] TANK_STATES;

  public RenderArtronTank(TileEntityRendererDispatcher dispatcher) {
     super(dispatcher);

     ModelReloaderRegistry.register(this);
  }


  public void render(ArtronFuelTankTileEntity dmTileEntityBase, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int combinedLightIn, int combinedOverlayIn) {
     JSONModel model = TANK_STATES[0];

     int fuelLevel = (int)dmTileEntityBase.charge / 10;

     if (fuelLevel >= 10) {
       fuelLevel = 9;
    }
     if (fuelLevel < 0) {
       fuelLevel = 0;
    }

     if (fuelLevel < TANK_STATES.length) {
       model = TANK_STATES[fuelLevel];
    }


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
     TANK_STATES = new JSONModel[10];
     TANK_STATES[0] = ModelLoader.loadModel(new ResourceLocation("dalekmod", "models/tileentity/artron_fuel_tank/artron_fuel_tank.json"));
     TANK_STATES[1] = ModelLoader.loadModel(new ResourceLocation("dalekmod", "models/tileentity/artron_fuel_tank/artron_fuel_tank_1.json"));
     TANK_STATES[2] = ModelLoader.loadModel(new ResourceLocation("dalekmod", "models/tileentity/artron_fuel_tank/artron_fuel_tank_2.json"));
     TANK_STATES[3] = ModelLoader.loadModel(new ResourceLocation("dalekmod", "models/tileentity/artron_fuel_tank/artron_fuel_tank_3.json"));
     TANK_STATES[4] = ModelLoader.loadModel(new ResourceLocation("dalekmod", "models/tileentity/artron_fuel_tank/artron_fuel_tank_4.json"));
     TANK_STATES[5] = ModelLoader.loadModel(new ResourceLocation("dalekmod", "models/tileentity/artron_fuel_tank/artron_fuel_tank_5.json"));
     TANK_STATES[6] = ModelLoader.loadModel(new ResourceLocation("dalekmod", "models/tileentity/artron_fuel_tank/artron_fuel_tank_6.json"));
     TANK_STATES[7] = ModelLoader.loadModel(new ResourceLocation("dalekmod", "models/tileentity/artron_fuel_tank/artron_fuel_tank_7.json"));
     TANK_STATES[8] = ModelLoader.loadModel(new ResourceLocation("dalekmod", "models/tileentity/artron_fuel_tank/artron_fuel_tank_8.json"));
     TANK_STATES[9] = ModelLoader.loadModel(new ResourceLocation("dalekmod", "models/tileentity/artron_fuel_tank/artron_fuel_tank_full.json"));
  }


  public JSONModel getModel() {
     return TANK_STATES[0];
  }
}


