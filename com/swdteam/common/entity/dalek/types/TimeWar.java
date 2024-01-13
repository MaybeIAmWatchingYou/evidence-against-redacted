package com.swdteam.common.entity.dalek.types;

import com.swdteam.common.entity.LaserEntity;
import com.swdteam.common.entity.dalek.DalekBase;
import com.swdteam.common.entity.dalek.DalekEntity;
import com.swdteam.common.init.DMParticleTypes;
import com.swdteam.common.init.DMProjectiles;
import com.swdteam.common.init.DMSoundEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particles.IParticleData;
import net.minecraft.util.SoundEvent;


public class TimeWar
  extends DalekBase
{
  public TimeWar(String dalekName) {
     super(dalekName);
     addRightArmAttatchment("ClawArm");
     addLeftArmAttatchment("FlameThrowerArm");
  }

  public float getMaxHealth() {
     return 24.0F;
  }


  public boolean canFly() {
     return true;
  }

  public void onUpdate(Entity e) {
     if (e.world.isRemote) {
       DalekEntity dalek = (DalekEntity)e;
       if (dalek.isFlying() == true &&
         dalek.world.isRemote) {
         if (dalek.world.rand.nextInt(24) != 0 || !dalek.func_174814_R());

         for (int i = 0; i < 2; i++) {
           dalek.world.addParticle((IParticleData)DMParticleTypes.BLUE_DALEK_HOVER.get(), dalek.func_226282_d_(0.5D), dalek.func_226283_e_(-0.01D), dalek.func_226287_g_(0.5D), 0.0D, 0.0D, 0.0D);
        }
      }
    }

     super.onUpdate(e);
  }


  public LaserEntity spawnLaserAttack(DalekEntity dalek, LivingEntity target) {
     double d0 = 0.0D;
     double d1 = 0.0D;
     double d2 = 0.0D;

     if (!target.func_70089_S()) {
       return null;
    }
     d0 = target.getPosX() - dalek.getPosX();
     d1 = target.func_226283_e_(0.3333333333333333D) - dalek.getPosY() - 0.75D;
     d2 = target.getPosZ() - dalek.getPosZ();

     dalek.func_184185_a(getShootSound((Entity)dalek), 1.0F, 1.0F);

     LaserEntity laser = new LaserEntity(dalek.world, (LivingEntity)dalek, 0.2F, 2.0F);
     laser.setLaserType(getLaser(dalek));
     float speed = dalek.isLeftArm("FlameThrowerArm") ? 0.5F : 2.5F;
     laser.func_70186_c(d0, d1, d2, speed, 0.0F);
     dalek.world.addEntity((Entity)laser);

     return laser;
  }

  public boolean causeFallDamage(float f1, float f2) {
     return false;
  }


  public DMProjectiles.Laser getLaser(DalekEntity dalek) {
     if (dalek.isLeftArm("FlameThrowerArm")) {
       return DMProjectiles.FIRE;
    }
     return DMProjectiles.BLUE_LASER;
  }


  public SoundEvent getShootSound(Entity e) {
     if (((DalekEntity)e).isLeftArm("FlameThrowerArm")) {
       return (SoundEvent)DMSoundEvents.ENTITY_DALEK_FLAME_THROWER_SHOOT.get();
    }
     return (SoundEvent)DMSoundEvents.ENTITY_DALEK_LASER_SHOOT.get();
  }


  public SoundEvent getAttackSound(Entity e) {
     return (SoundEvent)DMSoundEvents.ENTITY_DALEK_TIME_WAR_ATTACK.get();
  }

  protected void aiStep() {}
}


