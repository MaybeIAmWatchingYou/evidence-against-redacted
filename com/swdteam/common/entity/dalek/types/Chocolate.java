package com.swdteam.common.entity.dalek.types;

import com.swdteam.common.entity.dalek.DalekBase;
import com.swdteam.common.entity.dalek.DalekEntity;
import com.swdteam.common.init.DMProjectiles;
import com.swdteam.util.SWDMathUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class Chocolate
  extends DalekBase
{
  public Chocolate(String dalekName) {
     super(dalekName);
  }
  
  public float getMaxHealth() {
     return 14.0F;
  }

  
  public ActionResultType interactAt(PlayerEntity player, Vector3d vec, Hand hand) {
     player.func_184185_a(SoundEvents.field_187537_bA, 1.0F, 1.0F);
     player.func_184185_a(SoundEvents.field_187739_dZ, 1.0F, SWDMathUtils.randomRange(0.9F, 1.0F));
    
     player.getFoodStats().func_75122_a(6, 10.0F);
     if (!player.world.isRemote) {
       player.func_145747_a((ITextComponent)new TranslationTextComponent("msg.dalekmod.dalek.eat"), player.getUniqueID());
    }
     remove();
     return interactAt(player, vec, hand);
  }


  
  public DMProjectiles.Laser getLaser(DalekEntity dalek) {
     return DMProjectiles.GREEN_LASER;
  }
  
  protected void aiStep() {}
}


