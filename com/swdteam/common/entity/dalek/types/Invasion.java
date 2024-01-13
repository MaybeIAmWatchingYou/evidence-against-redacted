package com.swdteam.common.entity.dalek.types;

import com.swdteam.common.entity.LaserEntity;
import com.swdteam.common.entity.dalek.DalekEntity;
import com.swdteam.common.init.DMProjectiles;
import com.swdteam.common.init.DMSoundEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.SoundEvent;


public class Invasion
  extends Skaro
{
  public Invasion(String dalekName) {
     super(dalekName);
  }
  
  public float getMaxHealth() {
     return 24.0F;
  }


  
  public DMProjectiles.Laser getLaser(DalekEntity dalek) {
     return DMProjectiles.FLASH;
  }


  
  public LaserEntity spawnLaserAttack(DalekEntity dalek, LivingEntity target) {
     double d0 = 0.0D;
     double d1 = 0.0D;
     double d2 = 0.0D;
    
     if (!target.func_70089_S()) return null;
    
     d0 = target.getPosX() - dalek.getPosX();
     d1 = target.func_226283_e_(0.3333333333333333D) - dalek.getPosY() - 0.75D;
     d2 = target.getPosZ() - dalek.getPosZ();
    
     LaserEntity laser = new LaserEntity(dalek.world, (LivingEntity)dalek, 0.2F, 2.0F);
     laser.setLaserType(getLaser(dalek));
    
     laser.func_70186_c(d0, d1, d2, 0.5F, 0.0F);
     dalek.func_184185_a(getShootSound((Entity)dalek), 1.0F, 1.0F);
     dalek.world.addEntity((Entity)laser);
    
     return laser;
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


