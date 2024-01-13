package com.swdteam.common.entity.classic;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.RangedAttackGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class ClassicSkeletonEntity extends MonsterEntity implements IRangedAttackMob {
  public ClassicSkeletonEntity(EntityType<? extends ClassicSkeletonEntity> type, World worldIn) {
     super(type, worldIn);
  }

  protected void func_184651_r() {
     this.field_70714_bg.func_75776_a(1, (Goal)new RangedAttackGoal(this, 1.25D, 20, 2.0F));
     this.field_70714_bg.func_75776_a(2, (Goal)new WaterAvoidingRandomWalkingGoal((CreatureEntity)this, 1.0D, 1.0000001E-5F));
     this.field_70714_bg.func_75776_a(3, (Goal)new LookAtGoal((MobEntity)this, PlayerEntity.class, 6.0F));
     this.field_70714_bg.func_75776_a(4, (Goal)new LookRandomlyGoal((MobEntity)this));
     this.field_70715_bh.func_75776_a(2, (Goal)new NearestAttackableTargetGoal((MobEntity)this, PlayerEntity.class, true));
  }

  public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
     return MobEntity.func_233666_p_().func_233815_a_(Attributes.field_233818_a_, 30.0D).func_233815_a_(Attributes.field_233821_d_, 0.25D);
  }





  public void func_82196_d(LivingEntity target, float distanceFactor) {
     AbstractArrowEntity abstractarrowentity = fireArrow(new ItemStack((IItemProvider)Items.field_151032_g), distanceFactor);


     double d0 = target.getPosX() - getPosX();
     double d1 = target.func_226283_e_(0.3333333333333333D) - abstractarrowentity.getPosY();
     double d2 = target.getPosZ() - getPosZ();
     double d3 = MathHelper.func_76133_a(d0 * d0 + d2 * d2);
     abstractarrowentity.func_70186_c(d0, d1 + d3 * 0.20000000298023224D, d2, 1.6F, (14 - this.world.func_175659_aa().func_151525_a() * 4));
     func_184185_a(SoundEvents.field_187866_fi, 1.0F, 1.0F / (func_70681_au().nextFloat() * 0.4F + 0.8F));
     this.world.addEntity((Entity)abstractarrowentity);
  }




  protected AbstractArrowEntity fireArrow(ItemStack arrowStack, float distanceFactor) {
     return ProjectileHelper.func_221272_a((LivingEntity)this, arrowStack, distanceFactor);
  }

  protected float func_213348_b(Pose poseIn, EntitySize sizeIn) {
     return 1.74F;
  }




  public double getYOffset() {
     return -0.6D;
  }

  protected SoundEvent func_184639_G() {
     return SoundEvents.field_187854_fc;
  }

  protected SoundEvent func_184601_bQ(DamageSource damageSourceIn) {
     return SoundEvents.field_187864_fh;
  }

  protected SoundEvent func_184615_bR() {
     return SoundEvents.field_187856_fd;
  }

  protected SoundEvent getStepSound() {
     return SoundEvents.field_187868_fj;
  }


  public boolean func_213380_a(IWorld worldIn, SpawnReason spawnReasonIn) {
     return true;
  }
}


