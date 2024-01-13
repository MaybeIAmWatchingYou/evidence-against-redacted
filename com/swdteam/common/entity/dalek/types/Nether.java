package com.swdteam.common.entity.dalek.types;

import com.swdteam.common.entity.dalek.DalekBase;
import com.swdteam.common.entity.dalek.DalekEntity;
import com.swdteam.common.init.DMParticleTypes;
import com.swdteam.common.init.DMProjectiles;
import com.swdteam.common.init.DMSoundEvents;
import net.minecraft.entity.Entity;
import net.minecraft.particles.IParticleData;
import net.minecraft.util.SoundEvent;


public class Nether
  extends DalekBase
{
  public Nether(String dalekName) {
     super(dalekName);
     addRightArmAttatchment("ClawArm");
     addLeftArmAttatchment("FlameThrowerArm");
  }

  public float getMaxHealth() {
     return 26.0F;
  }


  public boolean canFly() {
     return true;
  }

  public void onUpdate(Entity e) {
     super.onUpdate(e);

     if (e.world.isRemote) {
       DalekEntity dalek = (DalekEntity)e;

       if (dalek.isFlying() == true &&
         dalek.world.isRemote) {
         if (dalek.world.rand.nextInt(24) != 0 || !dalek.func_174814_R());


         for (int i = 0; i < 2; i++) {
           dalek.world.addParticle((IParticleData)DMParticleTypes.RED_DALEK_HOVER.get(), dalek.func_226282_d_(0.5D), dalek.func_226283_e_(-0.01D), dalek.func_226287_g_(0.5D), 0.0D, 0.0D, 0.0D);
        }
      }
    }
  }


  public boolean causeFallDamage(float f1, float f2) {
     return false;
  }



  public DMProjectiles.Laser getLaser(DalekEntity dalek) {
     if (dalek.isLeftArm("FlameThrowerArm")) {
       return DMProjectiles.POISON;
    }
     return DMProjectiles.RED_LASER;
  }


  public SoundEvent getShootSound(Entity e) {
     if (((DalekEntity)e).isLeftArm("FlameThrowerArm")) {
       return (SoundEvent)DMSoundEvents.ENTITY_DALEK_FLAME_THROWER_SHOOT.get();
    }
     return (SoundEvent)DMSoundEvents.ENTITY_DALEK_LASER_SHOOT.get();
  }


  public SoundEvent getAttackSound(Entity e) {
     return (SoundEvent)DMSoundEvents.ENTITY_DALEK_NETHER_ATTACK.get();
  }

  protected void aiStep() {}
}


