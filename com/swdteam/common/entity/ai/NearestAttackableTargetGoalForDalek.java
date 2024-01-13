package com.swdteam.common.entity.ai;

import com.swdteam.common.init.DMItems;
import java.util.EnumSet;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.math.AxisAlignedBB;


public class NearestAttackableTargetGoalForDalek<T extends LivingEntity>
  extends TargetGoal
{
  public final Class<T> targetClass;
  private final int targetChance;
  protected LivingEntity nearestTarget;
  protected EntityPredicate targetEntitySelector;

  public NearestAttackableTargetGoalForDalek(MobEntity mobIn, Class<T> targetClassIn, boolean checkSight) {
     this(mobIn, targetClassIn, checkSight, false);
  }

  public NearestAttackableTargetGoalForDalek(MobEntity mobIn, Class<T> targetClassIn, boolean checkSight, boolean nearbyOnlyIn) {
     this(mobIn, targetClassIn, 10, checkSight, nearbyOnlyIn, (Predicate<LivingEntity>)null);
  }

  public NearestAttackableTargetGoalForDalek(MobEntity mobIn, Class<T> targetClassIn, int targetChanceIn, boolean checkSight, boolean nearbyOnlyIn, @Nullable Predicate<LivingEntity> targetPredicate) {
     super(mobIn, checkSight, nearbyOnlyIn);
     this.targetClass = targetClassIn;
     this.targetChance = targetChanceIn;
     func_220684_a(EnumSet.of(Goal.Flag.TARGET));
     this.targetEntitySelector = (new EntityPredicate()).func_221013_a(func_111175_f()).func_221012_a(targetPredicate);
  }






  public boolean func_75250_a() {
     if (this.targetChance > 0 && this.field_75299_d.func_70681_au().nextInt(this.targetChance) != 0) {
       return false;
    }
     findNearestTarget();
     return (this.nearestTarget != null);
  }


  protected AxisAlignedBB getTargetSearchArea(double targetDistance) {
     return this.field_75299_d.getBoundingBox().func_72314_b(targetDistance, 4.0D, targetDistance);
  }

  protected void findNearestTarget() {
     if (this.targetClass != PlayerEntity.class && this.targetClass != ServerPlayerEntity.class) {
       this.nearestTarget = this.field_75299_d.world.func_225318_b(this.targetClass, this.targetEntitySelector, (LivingEntity)this.field_75299_d, this.field_75299_d.getPosX(), this.field_75299_d.func_226280_cw_(), this.field_75299_d.getPosZ(), getTargetSearchArea(func_111175_f()));
    } else {
       this.nearestTarget = (LivingEntity)this.field_75299_d.world.func_217372_a(this.targetEntitySelector, (LivingEntity)this.field_75299_d, this.field_75299_d.getPosX(), this.field_75299_d.func_226280_cw_(), this.field_75299_d.getPosZ());
    }

     if (this.nearestTarget != null && this.nearestTarget.getItemStackFromSlot(EquipmentSlotType.HEAD) != null && this.nearestTarget.getItemStackFromSlot(EquipmentSlotType.HEAD).getItem() == DMItems.EYESTALK.get()) {
       this.nearestTarget = null;
    }
  }





  public void func_75249_e() {
     this.field_75299_d.func_70624_b(this.nearestTarget);
     super.func_75249_e();
  }
}


