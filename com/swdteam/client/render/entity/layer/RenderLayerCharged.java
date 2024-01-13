package com.swdteam.client.render.entity.layer;

import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderLayerCharged
  extends ChargedLayer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>>
{
   private static final ResourceLocation TEXTURE = new ResourceLocation("dalekmod", "textures/entity/player/skin/bones_skin.png");
   private final PlayerModel<AbstractClientPlayerEntity> model = new PlayerModel(0.01F, false);
   private final PlayerModel<AbstractClientPlayerEntity> modelGlow = new PlayerModel(0.1F, false);

  public RenderLayerCharged(IEntityRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>> p_i50947_1_) {
     super(p_i50947_1_);
     this.model.field_217114_e = false;
     this.modelGlow.field_217114_e = false;
  }

  protected float func_225634_a_(float p_225634_1_) {
     return p_225634_1_ * 0.01F;
  }

  protected ResourceLocation getTexture() {
     return TEXTURE;
  }

  protected EntityModel<AbstractClientPlayerEntity> getModel() {
     return (EntityModel<AbstractClientPlayerEntity>)this.model;
  }

  protected EntityModel<AbstractClientPlayerEntity> getGlowModel() {
     return (EntityModel<AbstractClientPlayerEntity>)this.modelGlow;
  }
}


