package com.swdteam.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.swdteam.client.render.tileentity.RenderBlockTardis;
import com.swdteam.client.tardis.data.ClientTardisCache;
import com.swdteam.client.tardis.data.ExteriorModels;
import com.swdteam.common.init.DMFlightMode;
import com.swdteam.common.tardis.Tardis;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.model.javajson.JSONModel;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;



public class RenderFlightMode
{
   public static Map<UUID, Float> ROTATIONS = new HashMap<>();

  public static JSONModel MODEL_TARDIS;


  public static void render(AbstractClientPlayerEntity p_225623_1_, float p_225623_2_, float p_225623_3_, MatrixStack p_225623_4_, IRenderTypeBuffer p_225623_5_, int p_225623_6_) {
     TardisData data = DMFlightMode.getData(p_225623_1_.getGameProfile().getId());

     if (data == null || !Minecraft.func_71410_x().func_195551_G().func_219533_b(data.getTardisExterior().getData().getModel(data.getSkinID()))) {
       data = ClientTardisCache.DEFAULT_DATA;
    }

     Tardis tardisData = data.getTardisExterior();
     MODEL_TARDIS = ExteriorModels.getModel(tardisData.getData().getModel(data.getSkinID()));



     IVertexBuilder ivertexbuilder = p_225623_5_.getBuffer(RenderType.func_228644_e_(MODEL_TARDIS.getModelData().getTexture()));

     float rotation = (p_225623_1_.field_70173_aa * 12);

     if ((Minecraft.func_71410_x()).field_71439_g == p_225623_1_) {
       rotation = (p_225623_1_.field_70173_aa * 12) + (Minecraft.func_71410_x()).field_71460_t.func_215316_n().func_216778_f();
    }

     if (!p_225623_1_.isOnGround()) {
       ROTATIONS.put(p_225623_1_.getGameProfile().getId(), Float.valueOf(rotation));
    } else {
       rotation = ROTATIONS.containsKey(p_225623_1_.getGameProfile().getId()) ? ((Float)ROTATIONS.get(p_225623_1_.getGameProfile().getId())).floatValue() : 0.0F;
    }

     if (p_225623_1_.func_213453_ef()) {
       p_225623_4_.func_227861_a_(0.0D, 0.125D, 0.0D);
    }
     RenderBlockTardis.renderTardisModel(MODEL_TARDIS, rotation, ivertexbuilder, data, p_225623_4_, p_225623_5_, p_225623_6_);
  }
}


