package com.swdteam.common.entity.dalek.types;

import com.swdteam.common.entity.dalek.DalekBase;
import com.swdteam.common.entity.dalek.DalekEntity;
import com.swdteam.common.init.DMProjectiles;
import com.swdteam.common.init.DMSoundEvents;
import net.minecraft.entity.Entity;
import net.minecraft.util.SoundEvent;




public class Skaro
  extends DalekBase
{
  public Skaro(String dalekName) {
     super(dalekName);
  }

  public float getMaxHealth() {
     return 20.0F;
  }






  public void isOnBlock(DalekEntity dalek) {}






  public void onUpdate(Entity e) {
     isOnBlock((DalekEntity)e);
     super.onUpdate(e);
  }



  public DMProjectiles.Laser getLaser(DalekEntity dalek) {
     return DMProjectiles.FLASH;
  }


  public SoundEvent getShootSound(Entity e) {
     return (SoundEvent)DMSoundEvents.ENTITY_DALEK_SPARK_SHOOT.get();
  }


  public SoundEvent getAttackSound(Entity e) {
     return (SoundEvent)DMSoundEvents.ENTITY_DALEK_SKARO_ATTACK.get();
  }


  public SoundEvent getAmbientSound(Entity e) {
     return (SoundEvent)DMSoundEvents.ENTITY_DALEK_SKARO_AMBIENT.get();
  }

  protected void aiStep() {}
}


