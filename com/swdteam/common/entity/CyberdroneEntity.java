package com.swdteam.common.entity;

import com.swdteam.common.entity.dalek.DalekEntity;
import com.swdteam.common.init.DMProjectiles;
import com.swdteam.common.init.DMSoundEvents;
import java.util.EnumSet;
import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FlyingEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.monster.HuskEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.SkeletonEntity;
import net.minecraft.entity.monster.StrayEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.monster.ZombieVillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeEntity;

public class CyberdroneEntity
  extends FlyingEntity
  implements IForgeEntity {
  public CyberdroneEntity(EntityType<? extends CyberdroneEntity> p_i50206_1_, World p_i50206_2_) {
     super(p_i50206_1_, p_i50206_2_);
     this.field_70728_aV = 5;
     this.field_70765_h = new MoveHelperController(this);
  }

  protected void func_184651_r() {
     this.field_70714_bg.func_75776_a(5, new RandomFlyGoal(this));
     this.field_70714_bg.func_75776_a(7, new LookAroundGoal(this));
     this.field_70714_bg.func_75776_a(7, new LaserAttackGoal(this));

     this.field_70715_bh.func_75776_a(2, (Goal)new NearestAttackableTargetGoal((MobEntity)this, PlayerEntity.class, true));
     this.field_70715_bh.func_75776_a(3, (Goal)new NearestAttackableTargetGoal((MobEntity)this, DalekEntity.class, true));
     this.field_70715_bh.func_75776_a(4, (Goal)new NearestAttackableTargetGoal((MobEntity)this, VillagerEntity.class, true));
     this.field_70715_bh.func_75776_a(5, (Goal)new NearestAttackableTargetGoal((MobEntity)this, ZombieVillagerEntity.class, true));
     this.field_70715_bh.func_75776_a(5, (Goal)new NearestAttackableTargetGoal((MobEntity)this, ZombieEntity.class, true));
     this.field_70715_bh.func_75776_a(5, (Goal)new NearestAttackableTargetGoal((MobEntity)this, HuskEntity.class, true));
     this.field_70715_bh.func_75776_a(6, (Goal)new NearestAttackableTargetGoal((MobEntity)this, SkeletonEntity.class, true));
     this.field_70715_bh.func_75776_a(6, (Goal)new NearestAttackableTargetGoal((MobEntity)this, StrayEntity.class, true));
  }

  protected boolean func_225511_J_() {
     System.out.println(getPosition());
     return true;
  }

  public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
     return MobEntity.func_233666_p_().func_233815_a_(Attributes.field_233818_a_, 10.0D).func_233815_a_(Attributes.field_233819_b_, 100.0D);
  }

  public SoundCategory func_184176_by() {
     return SoundCategory.HOSTILE;
  }

  protected SoundEvent func_184601_bQ(DamageSource p_184601_1_) {
     return SoundEvents.field_187602_cF;
  }

  public int func_70641_bl() {
     return 3;
  }

  public static boolean checkAnyLightMonsterSpawnRules(EntityType<? extends MonsterEntity> p_223324_0_, IWorld p_223324_1_, SpawnReason p_223324_2_, BlockPos p_223324_3_, Random p_223324_4_) {
     return (p_223324_1_.func_175659_aa() != Difficulty.PEACEFUL && func_223315_a(p_223324_0_, p_223324_1_, p_223324_2_, p_223324_3_, p_223324_4_));
  }

  static class LaserAttackGoal extends Goal {
    private final CyberdroneEntity drone;
    public int chargeTime;

    public LaserAttackGoal(CyberdroneEntity p_i45837_1_) {
       this.drone = p_i45837_1_;
    }

    public boolean func_75250_a() {
       return (this.drone.getAttackTarget() != null);
    }

    public void func_75249_e() {
       this.chargeTime = 0;
    }

    public void func_75246_d() {
       LivingEntity livingentity = this.drone.getAttackTarget();
       if (livingentity.func_70068_e((Entity)this.drone) < 512.0D && this.drone.func_70685_l((Entity)livingentity)) {
         World world = this.drone.world;
         this.chargeTime++;

         if (this.chargeTime == 20) {

           double d0 = 0.0D;
           double d1 = 0.0D;
           double d2 = 0.0D;

           if (!livingentity.func_70089_S())
            return;
           d0 = livingentity.getPosX() - this.drone.getPosX();
           d1 = livingentity.func_226283_e_(0.3333333333333333D) - this.drone.getPosY() - 0.75D;
           d2 = livingentity.getPosZ() - this.drone.getPosZ();

           LaserEntity laser = new LaserEntity(world, (LivingEntity)this.drone, 0.2F, 2.5F);
           laser.setExplosionSize(2.0F);
           laser.setLaserType(DMProjectiles.EXPLOSIVE_LASER);
           laser.setCausesFireExplosion(this.drone.world.rand.nextBoolean());
           laser.func_70186_c(d0, d1, d2, 2.5F, 0.0F);
           this.drone.func_184185_a((SoundEvent)DMSoundEvents.ENTITY_CYBERMAN_SHOOT.get(), 1.0F, 1.0F);
           world.addEntity((Entity)laser);

           this.chargeTime = 0;
        }
       } else if (this.chargeTime > 0) {
         this.chargeTime--;
      }
    }
  }

  static class LookAroundGoal extends Goal {
    private final CyberdroneEntity drone;

    public LookAroundGoal(CyberdroneEntity p_i45839_1_) {
       this.drone = p_i45839_1_;
       func_220684_a(EnumSet.of(Goal.Flag.LOOK));
    }

    public boolean func_75250_a() {
       return true;
    }

    public void func_75246_d() {
       if (this.drone.getAttackTarget() == null) {
         Vector3d vector3d = this.drone.getMotion();
         this.drone.field_70177_z = -((float)MathHelper.func_181159_b(vector3d.x, vector3d.z)) * 57.295776F;
         this.drone.field_70761_aq = this.drone.field_70177_z;
      } else {
         LivingEntity livingentity = this.drone.getAttackTarget();
         double d0 = 64.0D;
         if (livingentity.func_70068_e((Entity)this.drone) < 512.0D) {
           double d1 = livingentity.getPosX() - this.drone.getPosX();
           double d2 = livingentity.getPosZ() - this.drone.getPosZ();
           this.drone.field_70177_z = -((float)MathHelper.func_181159_b(d1, d2)) * 57.295776F;
           this.drone.field_70761_aq = this.drone.field_70177_z;
        }
      }
    }
  }

  static class MoveHelperController
    extends MovementController {
    private final CyberdroneEntity drone;
    private int floatDuration;

    public MoveHelperController(CyberdroneEntity p_i45838_1_) {
       super((MobEntity)p_i45838_1_);
       this.drone = p_i45838_1_;
    }

    public void func_75641_c() {
       if (this.field_188491_h == MovementController.Action.MOVE_TO && 
         this.floatDuration-- <= 0) {
         this.floatDuration += this.drone.func_70681_au().nextInt(2) + 2;
         Vector3d vector3d = new Vector3d(this.field_75646_b - this.drone.getPosX(), this.field_75647_c - this.drone.getPosY(), this.field_75644_d - this.drone.getPosZ());
         double d0 = vector3d.func_72433_c();
         vector3d = vector3d.func_72432_b();
         if (canReach(vector3d, MathHelper.func_76143_f(d0))) {
           this.drone.setMotion(this.drone.getMotion().func_178787_e(vector3d.func_186678_a(0.1D)));
        } else {
           this.field_188491_h = MovementController.Action.WAIT;
        }
      }
    }



    private boolean canReach(Vector3d p_220673_1_, int p_220673_2_) {
       AxisAlignedBB axisalignedbb = this.drone.getBoundingBox();

       for (int i = 1; i < p_220673_2_; i++) {
         axisalignedbb = axisalignedbb.func_191194_a(p_220673_1_);
         if (!this.drone.world.func_226665_a__((Entity)this.drone, axisalignedbb)) {
           return false;
        }
      }

       return true;
    }
  }

  static class RandomFlyGoal extends Goal {
    private final CyberdroneEntity drone;

    public RandomFlyGoal(CyberdroneEntity p_i45836_1_) {
       this.drone = p_i45836_1_;
       func_220684_a(EnumSet.of(Goal.Flag.MOVE));
    }

    public boolean func_75250_a() {
       MovementController movementcontroller = this.drone.func_70605_aq();
       if (!movementcontroller.func_75640_a()) {
         return true;
      }
       double d0 = movementcontroller.func_179917_d() - this.drone.getPosX();
       double d1 = movementcontroller.func_179919_e() - this.drone.getPosY();
       double d2 = movementcontroller.func_179918_f() - this.drone.getPosZ();
       double d3 = d0 * d0 + d1 * d1 + d2 * d2;
       return (d3 < 1.0D || d3 > 3600.0D);
    }


    public boolean func_75253_b() {
       return false;
    }

    public void func_75249_e() {
       Random random = this.drone.func_70681_au();
       double d0 = this.drone.getPosX() + ((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
       double d1 = this.drone.getPosY() + ((random.nextFloat() * 1.5F - 1.0F) * 16.0F);
       double d2 = this.drone.getPosZ() + ((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
       this.drone.func_70605_aq().func_75642_a(d0, d1, d2, 1.0D);
    }
  }
}


