package com.swdteam.common.entity.dalek.types;

import com.swdteam.common.entity.dalek.DalekBase;
import com.swdteam.common.entity.dalek.DalekEntity;
import com.swdteam.common.init.DMProjectiles;
import com.swdteam.common.init.DMSoundEvents;
import net.minecraft.entity.Entity;
import net.minecraft.util.SoundEvent;

public class Molten
  extends DalekBase
{
  public Molten(String dalekName) {
     super(dalekName);
  }

  public float getMaxHealth() {
     return 28.0F;
  }



  public DMProjectiles.Laser getLaser(DalekEntity dalek) {
     return DMProjectiles.FIRE;
  }


  public SoundEvent getShootSound(Entity e) {
     return (SoundEvent)DMSoundEvents.ENTITY_DALEK_FLAME_THROWER_SHOOT.get();
  }


  public SoundEvent getAttackSound(Entity e) {
     return (SoundEvent)DMSoundEvents.ENTITY_DALEK_MOLTEN_ATTACK.get();
  }

  protected void aiStep() {}
}


