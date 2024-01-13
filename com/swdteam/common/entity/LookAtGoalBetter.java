package com.swdteam.common.entity;

import java.util.EnumSet;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.EntityPredicates;

public class LookAtGoalBetter extends Goal {
  protected final MobEntity entity;
  protected Entity closestEntity;
  protected final float maxDistance;
  private int lookTime;
  protected final float chance;
  protected final Class<? extends LivingEntity> watchedClass;
  protected final EntityPredicate field_220716_e;

  public LookAtGoalBetter(MobEntity entityIn, Class<? extends LivingEntity> watchTargetClass, float maxDistance) {
     this(entityIn, watchTargetClass, maxDistance, 0.02F);
  }

  public LookAtGoalBetter(MobEntity entityIn, Class<? extends LivingEntity> watchTargetClass, float maxDistance, float chanceIn) {
     this.entity = entityIn;
     this.watchedClass = watchTargetClass;
     this.maxDistance = maxDistance;
     this.chance = chanceIn;
     func_220684_a(EnumSet.of(Goal.Flag.LOOK));
     if (watchTargetClass == PlayerEntity.class) {
       this.field_220716_e = (new EntityPredicate()).func_221013_a(maxDistance).func_221011_b().func_221008_a().func_221009_d().func_221012_a(p_220715_1_ -> EntityPredicates.func_200820_b((Entity)entityIn).test(p_220715_1_));
    }
    else {

       this.field_220716_e = (new EntityPredicate()).func_221013_a(maxDistance).func_221011_b().func_221008_a().func_221009_d();
    }
  }







  public boolean func_75250_a() {
     if (this.entity.func_70681_au().nextFloat() >= this.chance) {
       return false;
    }
     if (this.entity.getAttackTarget() != null) {
       this.closestEntity = (Entity)this.entity.getAttackTarget();
    }

     if (this.watchedClass == PlayerEntity.class) {
       this.closestEntity = (Entity)this.entity.world.func_217372_a(this.field_220716_e, (LivingEntity)this.entity, this.entity.getPosX(), this.entity.func_226280_cw_(), this.entity.getPosZ());
       if (this.closestEntity instanceof PlayerEntity) {

         PlayerEntity player = (PlayerEntity)this.closestEntity;
         if (player.isCreative() || player.func_175149_v())
        {
           return false;
        }
      }
    } else {
       this.closestEntity = (Entity)this.entity.world.func_225318_b(this.watchedClass, this.field_220716_e, (LivingEntity)this.entity, this.entity.getPosX(), this.entity.func_226280_cw_(), this.entity.getPosZ(), this.entity.getBoundingBox().func_72314_b(this.maxDistance, 3.0D, this.maxDistance));
    }

     return (this.closestEntity != null);
  }







  public boolean func_75253_b() {
     if (!this.closestEntity.func_70089_S())
       return false; 
     if (this.entity.func_70068_e(this.closestEntity) > (this.maxDistance * this.maxDistance)) {
       return false;
    }
     return (this.lookTime > 0);
  }






  public void func_75249_e() {
     this.lookTime = 40 + this.entity.func_70681_au().nextInt(40);
  }





  public void func_75251_c() {
     this.closestEntity = null;
  }





  public void func_75246_d() {
     this.entity.func_70671_ap().func_220679_a(this.closestEntity.getPosX(), this.closestEntity.func_226280_cw_(), this.closestEntity.getPosZ());
     this.lookTime--;
  }
}


