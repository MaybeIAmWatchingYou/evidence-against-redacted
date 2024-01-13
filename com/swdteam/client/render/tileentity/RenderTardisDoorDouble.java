package com.swdteam.client.render.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.swdteam.client.model.IModelPartReloader;
import com.swdteam.client.model.ModelReloaderRegistry;
import com.swdteam.common.block.tardis.PoliceBoxDoorBlock;
import com.swdteam.common.tileentity.tardis.DoubleDoorsTileEntity;
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

public class RenderTardisDoorDouble extends TileEntityRenderer<DoubleDoorsTileEntity> implements IModelPartReloader {
  private String doorName;
  public static JSONModel MODEL_TARDIS;

  public RenderTardisDoorDouble(TileEntityRendererDispatcher dispatcher, String doorName) {
     super(dispatcher);
     this.doorName = doorName;
     ModelReloaderRegistry.register(this);
  }


  public void render(DoubleDoorsTileEntity doors, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int combinedLightIn, int combinedOverlayIn) {
     matrixStack.func_227860_a_();
     float doorOffset = ((Integer)doors.func_195044_w().get((Property)PoliceBoxDoorBlock.OFFSET)).intValue();
     float rotation = ((Direction)doors.func_195044_w().get((Property)PoliceBoxDoorBlock.FACING)).func_185119_l();
     matrixStack.func_227861_a_(0.5D, 0.0D, 0.5D);
     matrixStack.func_227861_a_(0.0D, 1.5D, 0.0D);
     matrixStack.func_227863_a_(Vector3f.field_229179_b_.func_229187_a_(180.0F));
     matrixStack.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_(rotation + 90.0F));

     if (doorOffset == 2.0F) {
       matrixStack.func_227861_a_(0.0D, 0.0D, 0.875D);
     } else if (doorOffset == 1.0F) {
       matrixStack.func_227861_a_(0.0D, 0.0D, 0.4375D);
    }

     float maxRot = 90.0F;

     (MODEL_TARDIS.getModelData().getModel().getPart("LeftDoor")).field_78796_g = (float)Math.toRadians((maxRot * doors.leftRotPcent));
     (MODEL_TARDIS.getModelData().getModel().getPart("RightDoor")).field_78796_g = (float)-Math.toRadians((maxRot * doors.rightRotPcent));
     MODEL_TARDIS.getModelData().getModel().renderToBuffer(matrixStack, iRenderTypeBuffer, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
     matrixStack.func_227865_b_();
  }


  public void init() {
     MODEL_TARDIS = ModelLoader.loadModel(new ResourceLocation("dalekmod", "models/tileentity/" + this.doorName + ".json"));
  }


  public JSONModel getModel() {
     return MODEL_TARDIS;
  }
}


