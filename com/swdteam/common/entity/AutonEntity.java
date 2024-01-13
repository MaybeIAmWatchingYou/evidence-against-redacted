package com.swdteam.common.entity;
import com.swdteam.common.entity.dalek.DalekEntity;
import com.swdteam.common.entity.goal.RangedLaserAttack;
import com.swdteam.common.init.DMItems;
import com.swdteam.common.init.DMNBTKeys;
import com.swdteam.common.init.DMProjectiles;
import com.swdteam.common.init.DMSoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MoveTowardsTargetGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AutonEntity extends MonsterEntity implements IRangedAttackMob, IForgeEntity {
   public static final DataParameter<Integer> AUTON_TYPE = EntityDataManager.func_187226_a(AutonEntity.class, DataSerializers.field_187192_b);
   public static final DataParameter<Boolean> HAS_TARGET = EntityDataManager.func_187226_a(AutonEntity.class, DataSerializers.field_187198_h);
  
  private Goal meeleAttack;
   private ItemStack pickResult = new ItemStack((IItemProvider)DMItems.AUTON_SPAWNER.get());
  
  public AutonEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
     super(type, worldIn);
  }

  
  protected void func_184651_r() {
     super.func_184651_r();
     this.field_70715_bh.func_75776_a(1, (Goal)new NearestAttackableTargetGoal((MobEntity)this, PlayerEntity.class, true));
     this.field_70715_bh.func_75776_a(2, (Goal)new NearestAttackableTargetGoal((MobEntity)this, VillagerEntity.class, true));
     this.field_70715_bh.func_75776_a(3, (Goal)new NearestAttackableTargetGoal((MobEntity)this, DalekEntity.class, true));
     this.field_70715_bh.func_75776_a(4, (Goal)new HurtByTargetGoal((CreatureEntity)this, new Class[0]));
     this.field_70714_bg.func_75776_a(4, new LookAtGoalBetter((MobEntity)this, (Class)PlayerEntity.class, 8.0F));
     this.field_70715_bh.func_75776_a(5, (Goal)new MoveTowardsTargetGoal((CreatureEntity)this, 0.4D, 0.5F));
     this.field_70714_bg.func_75776_a(4, (Goal)new RangedLaserAttack(this, 0.5D, 20, 15.0F));
     this.field_70714_bg.func_75776_a(3, (Goal)new LookRandomlyGoal((MobEntity)this));
  }

  
  public void func_213281_b(CompoundNBT compound) {
     compound.func_74757_a(DMNBTKeys.GUN_ARMED, ((Boolean)func_184212_Q().func_187225_a(HAS_TARGET)).booleanValue());
     if (this.field_70180_af != null) {
       compound.putInt(DMNBTKeys.TYPE_AUTON, ((Integer)this.field_70180_af.func_187225_a(AUTON_TYPE)).intValue());
    }
     super.func_213281_b(compound);
  }

  
  public void func_70037_a(CompoundNBT compound) {
     if (compound.func_74764_b(DMNBTKeys.GUN_ARMED)) {
       func_184212_Q().func_187227_b(HAS_TARGET, Boolean.valueOf(compound.func_74767_n(DMNBTKeys.GUN_ARMED)));
    }
     if (compound.func_74764_b(DMNBTKeys.TYPE_AUTON)) {
       setAutonType(compound.getInt(DMNBTKeys.TYPE_AUTON));
    } else {
       setAutonType(this.field_70146_Z.nextInt(8));
    } 
     super.func_70037_a(compound);
  }

  
  public void func_70071_h_() {
     super.func_70071_h_();
     if (!this.world.isRemote) {
       if (getAttackTarget() != null) {
         if (!((Boolean)func_184212_Q().func_187225_a(HAS_TARGET)).booleanValue()) {
           func_184212_Q().func_187227_b(HAS_TARGET, Boolean.valueOf(true));
        }
       } else if (((Boolean)func_184212_Q().func_187225_a(HAS_TARGET)).booleanValue()) {
         func_184212_Q().func_187227_b(HAS_TARGET, Boolean.valueOf(false));
      } 
    }
  }

  
  protected void func_70088_a() {
     func_184212_Q().func_187214_a(HAS_TARGET, Boolean.valueOf(false));
     func_184212_Q().func_187214_a(AUTON_TYPE, Integer.valueOf(this.field_70146_Z.nextInt(7)));
     super.func_70088_a();
  }
  
  public int getAutonType() {
     return ((Integer)this.field_70180_af.func_187225_a(AUTON_TYPE)).intValue();
  }
  
  public void setAutonType(int i) {
     if (i < 0 || i >= 8) {
       i = this.field_70146_Z.nextInt(7);
    }
     if (this.field_70180_af != null) {
       this.field_70180_af.func_187227_b(AUTON_TYPE, Integer.valueOf(i));
    }
  }

  
  public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
     return MobEntity.func_233666_p_()
       .func_233815_a_(Attributes.field_233821_d_, 0.41999998688697815D)
       .func_233815_a_(Attributes.field_233818_a_, 16.0D)
       .func_233815_a_(Attributes.field_233823_f_, 3.0D)
       .func_233815_a_(Attributes.field_233819_b_, 15.0D);
  }

  
  protected void func_180429_a(BlockPos pos, BlockState blockIn) {
     super.func_180429_a(pos, blockIn);
  }
  
  public boolean hasTarget() {
     return ((Boolean)func_184212_Q().func_187225_a(HAS_TARGET)).booleanValue();
  }

  
  public void func_82196_d(LivingEntity target, float distanceFactor) {
     double d0 = 0.0D;
     double d1 = 0.0D;
     double d2 = 0.0D;
    
     if (!target.func_70089_S())
      return; 
     d0 = target.getPosX() - getPosX();
     d1 = target.func_226283_e_(0.3333333333333333D) - getPosY() - 0.75D;
     d2 = target.getPosZ() - getPosZ();
    
     LaserEntity laser = new LaserEntity(this.world, (LivingEntity)this, 0.2F, (float)func_110148_a(Attributes.field_233823_f_).func_111126_e());
     laser.setLaserType(DMProjectiles.BULLET);
    
     laser.func_70186_c(d0, d1, d2, 2.5F, 0.0F);
     func_184185_a((SoundEvent)DMSoundEvents.ENTITY_AUTON_SHOOT.get(), 1.0F, 1.0F);
     this.world.addEntity((Entity)laser);
  }

  
  public ItemStack getPickedResult(RayTraceResult target) {
     return this.pickResult;
  }
}


