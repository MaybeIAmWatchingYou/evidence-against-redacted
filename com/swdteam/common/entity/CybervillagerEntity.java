package com.swdteam.common.entity;
import com.swdteam.common.entity.dalek.DalekEntity;
import com.swdteam.common.entity.goal.RangedLaserAttack;
import com.swdteam.common.init.DMEntities;
import com.swdteam.common.init.DMItems;
import com.swdteam.common.init.DMNBTKeys;
import com.swdteam.common.init.DMParticleTypes;
import com.swdteam.common.init.DMProjectiles;
import com.swdteam.common.init.DMSoundEvents;
import com.swdteam.common.init.DMTags;
import com.swdteam.util.SWDMathUtils;
import net.minecraft.block.BlockState;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
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
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTier;
import net.minecraft.item.TieredItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.IParticleData;
import net.minecraft.tags.ITag;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.ForgeEventFactory;

public class CybervillagerEntity extends MonsterEntity implements IRangedAttackMob, IForgeEntity {
   public static final DataParameter<Integer> CYBERMAN_TYPE = EntityDataManager.func_187226_a(AutonEntity.class, DataSerializers.field_187192_b);
   public static final DataParameter<Boolean> HAS_GUN = EntityDataManager.func_187226_a(CybervillagerEntity.class, DataSerializers.field_187198_h);
  
  private Goal meeleAttack;
   private ItemStack pickResult = new ItemStack((IItemProvider)DMItems.CYBERMAN_SPAWNER.get());
  public CybervillagerEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
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
     if (hasGun()) {
       this.field_70714_bg.func_75776_a(4, (Goal)new RangedLaserAttack(this, 1.0D, 20, 15.0F));
    } else {
       this.field_70714_bg.func_75776_a(2, this.meeleAttack = (Goal)new MeleeAttackGoal((CreatureEntity)this, 1.0D, false));
    } 
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

  
  public void func_213281_b(CompoundNBT compound) {
     compound.func_74757_a(DMNBTKeys.GUN_ARMED, ((Boolean)func_184212_Q().func_187225_a(HAS_GUN)).booleanValue());
     if (this.field_70180_af != null) {
       compound.putInt(DMNBTKeys.TYPE_CYBER, ((Integer)this.field_70180_af.func_187225_a(CYBERMAN_TYPE)).intValue());
    }
     super.func_213281_b(compound);
  }

  
  public void func_70037_a(CompoundNBT compound) {
     if (compound.func_74764_b(DMNBTKeys.GUN_ARMED)) {
       func_184212_Q().func_187227_b(HAS_GUN, Boolean.valueOf(compound.func_74767_n(DMNBTKeys.GUN_ARMED)));
    }
     if (compound.func_74764_b(DMNBTKeys.TYPE_CYBER)) {
       setCybermanType(compound.getInt(DMNBTKeys.TYPE_CYBER));
    } else {
       setCybermanType(this.field_70146_Z.nextInt(4));
    } 
     super.func_70037_a(compound);
  }
  
  public boolean hasGun() {
     return ((Boolean)func_184212_Q().func_187225_a(HAS_GUN)).booleanValue();
  }

  
  protected void func_70088_a() {
     func_184212_Q().func_187214_a(HAS_GUN, Boolean.valueOf((this.world.rand.nextInt(5) == 3)));
     func_184212_Q().func_187214_a(CYBERMAN_TYPE, Integer.valueOf(this.field_70146_Z.nextInt(4)));
     super.func_70088_a();
  }
  
  public int getCybermanType() {
     return ((Integer)this.field_70180_af.func_187225_a(CYBERMAN_TYPE)).intValue();
  }
  
  public void setCybermanType(int i) {
     if (i < 0 || i >= 4) {
       i = this.field_70146_Z.nextInt(3);
    }
     if (this.field_70180_af != null) {
       this.field_70180_af.func_187227_b(CYBERMAN_TYPE, Integer.valueOf(i));
    }
  }

  
  public void func_82196_d(LivingEntity target, float distanceFactor) {
     double d0 = 0.0D;
     double d1 = 0.0D;
     double d2 = 0.0D;
    
     if (!target.func_70089_S())
       return;  d0 = target.getPosX() - getPosX();
     d1 = target.func_226283_e_(0.3333333333333333D) - getPosY() - 0.75D;
     d2 = target.getPosZ() - getPosZ();
    
     if (this.world.rand.nextInt(5) == 2) {
       func_184185_a((SoundEvent)DMSoundEvents.ENTITY_CYBERMAN_LIVING.get(), 1.0F, 1.0F);
    }
     LaserEntity laser = new LaserEntity(this.world, (LivingEntity)this, 0.2F, 2.0F);
     laser.setLaserType(DMProjectiles.ORANGE_LASER);
     laser.func_70186_c(d0, d1, d2, 2.5F, 0.0F);
     func_184185_a((SoundEvent)DMSoundEvents.ENTITY_CYBERMAN_SHOOT.get(), 1.0F, 1.0F);
     this.world.addEntity((Entity)laser);
  }
  
  protected void func_180429_a(BlockPos pos, BlockState blockIn) {
     func_184185_a((SoundEvent)DMSoundEvents.ENTITY_CYBERMAN_STEP.get(), 0.5F, SWDMathUtils.randomRange(0.8F, 1.2F));
  }
  
  public void func_70642_aH() {
     if (this.field_70173_aa >= 200) {
       func_184185_a((SoundEvent)DMSoundEvents.ENTITY_CYBERVILLAGER_AMBIENT.get(), 0.5F, 1.0F);
    }
  }
  
  protected SoundEvent func_184601_bQ(DamageSource source) {
     return (SoundEvent)DMSoundEvents.ENTITY_CYBERMAN_HURT.get();
  }
  
  public boolean func_70097_a(DamageSource source, float f) {
     if (source.func_76346_g() instanceof LivingEntity)
     { Item item = ((LivingEntity)source.func_76346_g()).func_184614_ca().getItem();








      
       boolean isGold = (item.func_206844_a((ITag)Tags.Items.INGOTS_GOLD) || item.func_206844_a((ITag)Tags.Items.NUGGETS_GOLD) || item.func_206844_a((ITag)Tags.Items.ORES_GOLD) || item.func_206844_a((ITag)Tags.Items.STORAGE_BLOCKS_GOLD) || item.func_206844_a((ITag)DMTags.Items.MOSTLY_GOLD_ITEMS) || (item.getItem() instanceof TieredItem && ((TieredItem)item.getItem()).func_200891_e().equals(ItemTier.GOLD)) || (item.getItem() instanceof ArmorItem && ((ArmorItem)item.getItem()).func_200880_d().equals(ArmorMaterial.GOLD)));
       if (isGold) {
         goldHurtEffects(f);
      } }
     else if (source.func_76346_g() != null && source.func_76346_g().getType().func_220341_a((ITag)DMTags.EntityTypes.GOLD)) { goldHurtEffects(f); }
    
     return super.func_70097_a(source, f);
  }
  
  private void goldHurtEffects(float attackDamage) {
     if (func_110143_aJ() - 3.0F <= 0.0F) { setHealth(func_110143_aJ() - func_110143_aJ() - attackDamage); }
     else { setHealth(func_110143_aJ() - 3.0F); }
    
     if (this.world.isRemote) {
       this.world.addParticle((IParticleData)DMParticleTypes.GOLD_DUST.get(), func_226282_d_(-0.5D), func_226283_e_(0.5D), func_226287_g_(0.5D), 0.0D, 0.0D, 0.0D);
    }
  }















  
  public void func_241847_a(ServerWorld level, LivingEntity target) {
     super.func_241847_a(level, target);
     if ((level.func_175659_aa() == Difficulty.NORMAL || level.func_175659_aa() == Difficulty.HARD) && target instanceof VillagerEntity && ForgeEventFactory.canLivingConvert(target, (EntityType)DMEntities.CYBERMANVILLAGER_ENTITY.get(), timer -> { 
         })) { if (level.func_175659_aa() != Difficulty.NORMAL || (level.func_175659_aa() != Difficulty.HARD && this.field_70146_Z.nextBoolean())) {
        return;
      }
       VillagerEntity villagerentity = (VillagerEntity)target;
       CybervillagerEntity cybermanentity = (CybervillagerEntity)villagerentity.func_233656_b_((EntityType)DMEntities.CYBERMANVILLAGER_ENTITY.get(), false);
       cybermanentity.func_213386_a((IServerWorld)level, level.func_175649_E(cybermanentity.getPosition()), SpawnReason.CONVERSION, (ILivingEntityData)new ZombieEntity.GroupData(false, true), (CompoundNBT)null);
       ForgeEventFactory.onLivingConvert(target, (LivingEntity)cybermanentity);
       if (!func_174814_R()) {
         level.func_217378_a((PlayerEntity)null, 1026, getPosition(), 0);
      } }
  
  }

  
  public ItemStack getPickedResult(RayTraceResult target) {
     return this.pickResult;
  }
}


