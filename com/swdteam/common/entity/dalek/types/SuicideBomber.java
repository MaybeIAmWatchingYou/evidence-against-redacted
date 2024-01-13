package com.swdteam.common.entity.dalek.types;

import com.swdteam.common.entity.LaserEntity;
import com.swdteam.common.entity.dalek.DalekBase;
import com.swdteam.common.entity.dalek.DalekEntity;
import com.swdteam.common.init.DMProjectiles;
import com.swdteam.common.init.DMSoundEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.Explosion;
import net.minecraft.world.GameRules;

public class SuicideBomber
  extends DalekBase {
  public SuicideBomber(String dalekName) {
     super(dalekName);
  }

  public float getMaxHealth() {
     return 12.0F;
  }


  public void onDeath(Entity dalek) {
     super.onDeath(dalek);

     if (dalek.world.rand.nextInt(5) == 2 && 
       !dalek.world.isRemote) {
       boolean mobGriefing = dalek.world.getGameRules().getBoolean(GameRules.MOB_GRIEFING);
       dalek.world.func_217398_a(dalek, dalek.getPosX(), dalek.getPosY(), dalek.getPosZ(), 1.5F, mobGriefing, mobGriefing ? Explosion.Mode.DESTROY : Explosion.Mode.NONE);
    }
  }



  public void mobInteract(PlayerEntity player, Hand hand, Entity dalek) {
     ItemStack itemstack = player.getHeldItem(hand);

     if (itemstack.getItem() == Items.field_151033_d) {
       if (!dalek.world.isRemote) {
         boolean mobGriefing = dalek.world.getGameRules().getBoolean(GameRules.MOB_GRIEFING);
         dalek.world.func_217398_a(dalek, dalek.getPosX(), dalek.getPosY(), dalek.getPosZ(), 5.0F, mobGriefing, mobGriefing ? Explosion.Mode.DESTROY : Explosion.Mode.NONE);
      }
       dalek.func_174812_G();
    }
  }


  public LaserEntity spawnLaserAttack(DalekEntity dalek, LivingEntity target) {
     if (dalek.world.rand.nextInt(5) == 2) {
       if (!dalek.world.isRemote) {
         boolean mobGriefing = dalek.world.getGameRules().getBoolean(GameRules.MOB_GRIEFING);
         dalek.world.func_217398_a((Entity)dalek, dalek.getPosX(), dalek.getPosY(), dalek.getPosZ(), 4.0F, true, mobGriefing ? Explosion.Mode.DESTROY : Explosion.Mode.NONE);
      }
       dalek.func_174812_G();
    }
     return super.spawnLaserAttack(dalek, target);
  }


  public void onUpdate(Entity e) {
     super.onUpdate(e);

     if (e.world.isRemote) {
       DalekEntity dalek = (DalekEntity)e;

       if (dalek.getFuse() >= 0) {
         for (int i = 0; i < dalek.getFuse() / 2; i++) {
           if (e.world.rand.nextBoolean()) {
             e.world.addParticle((IParticleData)ParticleTypes.SMOKE, e.func_226282_d_(0.5D), e.getPosY() + 1.0D, e.func_226287_g_(0.5D), (dalek.func_70681_au().nextDouble() - 0.5D) / 8.0D, -dalek.func_70681_au().nextDouble() / 8.0D, (dalek.func_70681_au().nextDouble() - 0.5D) / 8.0D);
          }
        }
      }
    }
  }

  public DMProjectiles.Laser getLaser(DalekEntity dalek) {
     return DMProjectiles.BLUE_LASER;
  }


  public SoundEvent getShootSound(Entity e) {
     return (SoundEvent)DMSoundEvents.ENTITY_DALEK_BEAM_SHOOT.get();
  }


  public SoundEvent getAttackSound(Entity e) {
     return (SoundEvent)DMSoundEvents.ENTITY_DALEK_SUICIDE_BOMBER_ATTACK.get();
  }

  protected void aiStep() {}
}


