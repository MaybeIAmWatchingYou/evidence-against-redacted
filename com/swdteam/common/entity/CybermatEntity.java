package com.swdteam.common.entity;

import com.swdteam.common.entity.dalek.DalekEntity;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.monster.HuskEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.SkeletonEntity;
import net.minecraft.entity.monster.StrayEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.monster.ZombieVillagerEntity;
import net.minecraft.entity.monster.piglin.PiglinBruteEntity;
import net.minecraft.entity.monster.piglin.PiglinEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class CybermatEntity
  extends MonsterEntity {
  public CybermatEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
     super(type, worldIn);
  }


  public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
     return MobEntity.func_233666_p_()
       .func_233815_a_(Attributes.field_233821_d_, 0.3499999940395355D)
       .func_233815_a_(Attributes.field_233818_a_, 20.0D)
       .func_233815_a_(Attributes.field_233823_f_, 2.0D).func_233815_a_(Attributes.field_233819_b_, 20.0D);
  }


  protected void func_184651_r() {
     super.func_184651_r();
     this.field_70714_bg.func_75776_a(3, (Goal)new AvoidEntityGoal((CreatureEntity)this, PiglinEntity.class, 6.0F, 1.0D, 1.2D));
     this.field_70714_bg.func_75776_a(3, (Goal)new AvoidEntityGoal((CreatureEntity)this, PiglinBruteEntity.class, 6.0F, 1.0D, 1.2D));

     this.field_70714_bg.func_75776_a(2, (Goal)new MeleeAttackGoal((CreatureEntity)this, 1.0D, false));
     this.field_70714_bg.func_75776_a(5, new LookAtGoalBetter((MobEntity)this, (Class)PlayerEntity.class, 8.0F));
     this.field_70714_bg.func_75776_a(6, (Goal)new WaterAvoidingRandomWalkingGoal((CreatureEntity)this, 0.8D));
     this.field_70714_bg.func_75776_a(7, (Goal)new LookRandomlyGoal((MobEntity)this));

     this.field_70715_bh.func_75776_a(2, (Goal)new NearestAttackableTargetGoal((MobEntity)this, PlayerEntity.class, true));
     this.field_70715_bh.func_75776_a(3, (Goal)new NearestAttackableTargetGoal((MobEntity)this, DalekEntity.class, true));
     this.field_70715_bh.func_75776_a(4, (Goal)new NearestAttackableTargetGoal((MobEntity)this, VillagerEntity.class, true));
     this.field_70715_bh.func_75776_a(5, (Goal)new NearestAttackableTargetGoal((MobEntity)this, ZombieVillagerEntity.class, true));
     this.field_70715_bh.func_75776_a(5, (Goal)new NearestAttackableTargetGoal((MobEntity)this, ZombieEntity.class, true));
     this.field_70715_bh.func_75776_a(5, (Goal)new NearestAttackableTargetGoal((MobEntity)this, HuskEntity.class, true));
     this.field_70715_bh.func_75776_a(6, (Goal)new NearestAttackableTargetGoal((MobEntity)this, SkeletonEntity.class, true));
     this.field_70715_bh.func_75776_a(6, (Goal)new NearestAttackableTargetGoal((MobEntity)this, StrayEntity.class, true));
     this.field_70715_bh.func_75776_a(1, (Goal)new HurtByTargetGoal((CreatureEntity)this, new Class[0]));
  }
}


