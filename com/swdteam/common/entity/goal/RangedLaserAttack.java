package com.swdteam.common.entity.goal;

import java.util.EnumSet;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.item.BowItem;

public class RangedLaserAttack<T extends MonsterEntity & IRangedAttackMob> extends Goal {
  private final T mob;
  private final double speedModifier;
  private int attackIntervalMin;
  private final float attackRadiusSqr;
   private int attackTime = -1;
  private int seeTime;
  private boolean strafingClockwise;
  private boolean strafingBackwards;
   private int strafingTime = -1;

  public RangedLaserAttack(T p_i47515_1_, double p_i47515_2_, int p_i47515_4_, float p_i47515_5_) {
     this.mob = p_i47515_1_;
     this.speedModifier = p_i47515_2_;
     this.attackIntervalMin = p_i47515_4_;
     this.attackRadiusSqr = p_i47515_5_ * p_i47515_5_;
     func_220684_a(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
  }

  public void setMinAttackInterval(int p_189428_1_) {
     this.attackIntervalMin = p_189428_1_;
  }

  public boolean func_75250_a() {
     return !(this.mob.getAttackTarget() == null);
  }

  public boolean func_75253_b() {
     return (func_75250_a() || !this.mob.func_70661_as().func_75500_f());
  }

  public void func_75249_e() {
     super.func_75249_e();
     this.mob.func_213395_q(true);
  }

  public void func_75251_c() {
     super.func_75251_c();
     this.mob.func_213395_q(false);
     this.seeTime = 0;
     this.attackTime = -1;
     this.mob.func_184602_cy();
  }

  public void func_75246_d() {
     LivingEntity livingentity = this.mob.getAttackTarget();
     if (livingentity != null) {
       double d0 = this.mob.func_70092_e(livingentity.getPosX(), livingentity.getPosY(), livingentity.getPosZ());
       boolean flag = this.mob.func_70635_at().func_75522_a((Entity)livingentity);
       boolean flag1 = (this.seeTime > 0);
       if (flag != flag1) {
         this.seeTime = 0;
      }

       if (flag) {
         this.seeTime++;
      } else {
         this.seeTime--;
      }

       if (d0 <= this.attackRadiusSqr && this.seeTime >= 20) {
         this.mob.func_70661_as().func_75499_g();
         this.strafingTime++;
      } else {
         this.mob.func_70661_as().func_75497_a((Entity)livingentity, this.speedModifier);
         this.strafingTime = -1;
      }

       if (this.strafingTime >= 20) {
         if (this.mob.func_70681_au().nextFloat() < 0.3D) {
           this.strafingClockwise = !this.strafingClockwise;
        }

         if (this.mob.func_70681_au().nextFloat() < 0.3D) {
           this.strafingBackwards = !this.strafingBackwards;
        }

         this.strafingTime = 0;
      }

       if (this.strafingTime > -1) {
         if (d0 > (this.attackRadiusSqr * 0.75F)) {
           this.strafingBackwards = false;
         } else if (d0 < (this.attackRadiusSqr * 0.25F)) {
           this.strafingBackwards = true;
        }

         this.mob.func_70605_aq().func_188488_a(this.strafingBackwards ? -0.5F : 0.5F, this.strafingClockwise ? 0.5F : -0.5F);
         this.mob.func_70625_a((Entity)livingentity, 30.0F, 30.0F);
      } else {
         this.mob.func_70671_ap().func_75651_a((Entity)livingentity, 30.0F, 30.0F);
      }

       int i = this.mob.func_184612_cw();
       if (i >= 20) {
         this.mob.func_184602_cy();
         this.attackTime = this.attackIntervalMin;
      }

       if (this.attackTime <= 0) {
         ((IRangedAttackMob)this.mob).func_82196_d(livingentity, BowItem.func_185059_b(i));
         this.attackTime = this.attackIntervalMin;
      }

       this.attackTime--;
    }
  }
}


