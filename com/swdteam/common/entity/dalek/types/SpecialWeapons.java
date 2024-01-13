package com.swdteam.common.entity.dalek.types;

import com.swdteam.common.entity.dalek.DalekBase;
import com.swdteam.common.entity.dalek.DalekEntity;
import com.swdteam.common.init.DMProjectiles;
import com.swdteam.common.init.DMSoundEvents;
import net.minecraft.entity.Entity;
import net.minecraft.util.SoundEvent;


public class SpecialWeapons
  extends DalekBase
{
  public SpecialWeapons(String s) {
     super(s);
  }

  public float getMaxHealth() {
     return 24.0F;
  }

  public SoundEvent getLivingSound(Entity e) {
     return null;
  }


  public DMProjectiles.Laser getLaser(DalekEntity dalek) {
     return DMProjectiles.EXPLOSIVE_LASER;
  }


  public SoundEvent getSpawnSound(Entity e) {
     return null;
  }


  public SoundEvent getAttackSound(Entity e) {
     return (SoundEvent)DMSoundEvents.ENTITY_DALEK_SWD_CHARGE.get();
  }


  public SoundEvent getShootSound(Entity e) {
     return (SoundEvent)DMSoundEvents.ENTITY_DALEK_SWD_SHOOT.get();
  }

  protected void aiStep() {}
}


