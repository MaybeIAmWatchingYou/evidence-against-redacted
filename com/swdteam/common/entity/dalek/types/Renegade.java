package com.swdteam.common.entity.dalek.types;

import com.swdteam.common.entity.dalek.DalekEntity;
import com.swdteam.common.init.DMProjectiles;
import com.swdteam.common.init.DMSoundEvents;
import net.minecraft.entity.Entity;
import net.minecraft.util.SoundEvent;


public class Renegade
  extends Skaro
{
  public Renegade(String dalekName) {
     super(dalekName);
  }



  public DMProjectiles.Laser getLaser(DalekEntity dalek) {
     return DMProjectiles.BLUE_LASER;
  }


  public SoundEvent getAttackSound(Entity e) {
     return (SoundEvent)DMSoundEvents.ENTITY_DALEK_IMPERIAL_ATTACK.get();
  }


  public SoundEvent getAmbientSound(Entity e) {
     return (SoundEvent)DMSoundEvents.ENTITY_DALEK_IMPERIAL_STAY.get();
  }
}


