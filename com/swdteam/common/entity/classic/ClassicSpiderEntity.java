package com.swdteam.common.entity.classic;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class ClassicSpiderEntity extends MonsterEntity {
   private static final DataParameter<Byte> CLIMBING = EntityDataManager.func_187226_a(ClassicSpiderEntity.class, DataSerializers.field_187191_a);
  
  public ClassicSpiderEntity(EntityType<? extends ClassicSpiderEntity> type, World worldIn) {
     super(type, worldIn);
  }

  
  protected void func_184651_r() {
     this.field_70714_bg.func_75776_a(1, (Goal)new SwimGoal((MobEntity)this));
     this.field_70714_bg.func_75776_a(3, (Goal)new LeapAtTargetGoal((MobEntity)this, 0.4F));
     this.field_70714_bg.func_75776_a(4, (Goal)new AttackGoal(this));
     this.field_70714_bg.func_75776_a(5, (Goal)new WaterAvoidingRandomWalkingGoal((CreatureEntity)this, 0.8D));
     this.field_70714_bg.func_75776_a(6, (Goal)new LookAtGoal((MobEntity)this, PlayerEntity.class, 8.0F));
     this.field_70714_bg.func_75776_a(6, (Goal)new LookRandomlyGoal((MobEntity)this));
     this.field_70715_bh.func_75776_a(1, (Goal)new HurtByTargetGoal((CreatureEntity)this, new Class[0]));
     this.field_70715_bh.func_75776_a(2, (Goal)new TargetGoal<>(this, PlayerEntity.class));
  }




  
  public double func_70042_X() {
     return (func_213302_cg() * 0.5F);
  }




  
  protected PathNavigator func_175447_b(World worldIn) {
     return (PathNavigator)new ClimberPathNavigator((MobEntity)this, worldIn);
  }

  
  protected void func_70088_a() {
     super.func_70088_a();
     this.field_70180_af.func_187214_a(CLIMBING, Byte.valueOf((byte)0));
  }




  
  public void func_70071_h_() {
     super.func_70071_h_();
     if (!this.world.isRemote) {
       setClimbing(this.field_70123_F);
    }
  }

  
  public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
     return MonsterEntity.func_234295_eP_().func_233815_a_(Attributes.field_233818_a_, 16.0D).func_233815_a_(Attributes.field_233821_d_, 0.30000001192092896D);
  }
  
  protected SoundEvent func_184639_G() {
     return SoundEvents.field_187817_fK;
  }
  
  protected SoundEvent func_184601_bQ(DamageSource damageSourceIn) {
     return SoundEvents.field_187821_fM;
  }
  
  protected SoundEvent func_184615_bR() {
     return SoundEvents.field_187819_fL;
  }
  
  protected void func_180429_a(BlockPos pos, BlockState blockIn) {
     func_184185_a(SoundEvents.field_187823_fN, 0.15F, 1.0F);
  }




  
  public boolean func_70617_f_() {
     return isCliming();
  }

  
  public void func_213295_a(BlockState state, Vector3d motionMultiplierIn) {
     if (!state.func_203425_a(Blocks.field_196553_aF)) {
       super.func_213295_a(state, motionMultiplierIn);
    }
  }


  
  public CreatureAttribute func_70668_bt() {
     return CreatureAttribute.field_223224_c_;
  }




  
  public boolean isCliming() {
     return ((((Byte)this.field_70180_af.func_187225_a(CLIMBING)).byteValue() & 0x1) != 0);
  }




  
  public void setClimbing(boolean climbing) {
     byte b0 = ((Byte)this.field_70180_af.func_187225_a(CLIMBING)).byteValue();
     if (climbing) {
       b0 = (byte)(b0 | 0x1);
    } else {
       b0 = (byte)(b0 & 0xFFFFFFFE);
    } 
    
     this.field_70180_af.func_187227_b(CLIMBING, Byte.valueOf(b0));
  }

  
  protected float func_213348_b(Pose poseIn, EntitySize sizeIn) {
     return 0.65F;
  }
  
  static class AttackGoal extends MeleeAttackGoal {
    public AttackGoal(ClassicSpiderEntity spider) {
       super((CreatureEntity)spider, 1.0D, true);
    }





    
    public boolean func_75250_a() {
       return (super.func_75250_a() && !this.field_75441_b.func_184207_aI());
    }




    
    public boolean func_75253_b() {
       float f = this.field_75441_b.func_70013_c();
       if (f >= 0.5F && this.field_75441_b.func_70681_au().nextInt(100) == 0) {
         this.field_75441_b.func_70624_b((LivingEntity)null);
         return false;
      } 
       return super.func_75253_b();
    }


    
    protected double func_179512_a(LivingEntity attackTarget) {
       return (4.0F + attackTarget.func_213311_cf());
    }
  }
  
  static class TargetGoal<T extends LivingEntity>
    extends NearestAttackableTargetGoal<T> {
    public TargetGoal(ClassicSpiderEntity spider, Class<T> classTarget) {
       super((MobEntity)spider, classTarget, true);
    }




    
    public boolean func_75250_a() {
       float f = this.field_75299_d.func_70013_c();
       return (f >= 0.5F) ? false : super.func_75250_a();
    }
  }

  
  public boolean func_213380_a(IWorld worldIn, SpawnReason spawnReasonIn) {
     return true;
  }
}


