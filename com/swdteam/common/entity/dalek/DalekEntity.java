package com.swdteam.common.entity.dalek;
import com.swdteam.common.entity.CybermanEntity;
import com.swdteam.common.entity.IEntityVariant;
import com.swdteam.common.entity.ai.DestroyTargetBlock;
import com.swdteam.common.entity.ai.NearestAttackableTargetGoalForDalek;
import com.swdteam.common.entity.ai.NearestAttackableTargetGoalForDalekRenegadeImperial;
import com.swdteam.common.entity.goal.RangedLaserAttack;
import com.swdteam.common.init.DMBlocks;
import com.swdteam.common.init.DMDalekRegistry;
import com.swdteam.common.init.DMItems;
import com.swdteam.common.init.DMNBTKeys;
import com.swdteam.common.init.DMSoundEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DoorBlock;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.FlyingMovementController;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.IFlyingAnimal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.Dimension;
import net.minecraft.world.Explosion;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeEntity;
import net.minecraftforge.registries.ForgeRegistries;

public class DalekEntity extends MonsterEntity implements IRangedAttackMob, IEntityVariant, IForgeEntity, IFlyingAnimal {
   private int timer = 0;
  private boolean isSetup = false;
   private final RangedLaserAttack<DalekEntity> aiLaserAttack = new RangedLaserAttack(this, 1.0D, 20, 15.0F);
  
  private DestroyTargetBlock destroyTargetBlock;
   public static final DataParameter<String> DALEK_TYPE = EntityDataManager.func_187226_a(DalekEntity.class, DataSerializers.field_187194_d);
   public static final DataParameter<String> DALEK_ARM_LEFT = EntityDataManager.func_187226_a(DalekEntity.class, DataSerializers.field_187194_d);
   public static final DataParameter<String> DALEK_ARM_RIGHT = EntityDataManager.func_187226_a(DalekEntity.class, DataSerializers.field_187194_d);
   public static final DataParameter<Integer> DALEK_FUSE = EntityDataManager.func_187226_a(DalekEntity.class, DataSerializers.field_187192_b);
   public static final DataParameter<Boolean> DALEK_BREAK_IN = EntityDataManager.func_187226_a(DalekEntity.class, DataSerializers.field_187198_h);
   public static final DataParameter<Boolean> DALEK_NITRO_NINED = EntityDataManager.func_187226_a(DalekEntity.class, DataSerializers.field_187198_h);
   private IDalek cachedData = null; public float lift; public float liftSpeed;
  public float oLiftSpeed;
  public float oLift;
  private Goal waterOne;
  private Goal laserRange;
   private float lifting = 1.0F; private Goal lookAt;
  
  public DalekEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
     super(type, worldIn);
  }
  private Goal randomGoal; private Goal hurtGoal;
  private Goal nearestPlayerGoal;
  
  protected void func_184651_r() {
     this.field_70714_bg.func_75776_a(5, this.waterOne = (Goal)new WaterAvoidingRandomWalkingGoal((CreatureEntity)this, 1.0D));
     this.field_70714_bg.func_75776_a(1, this.laserRange = (Goal)new RangedLaserAttack(this, 1.0D, 20, 2.0F));
     this.field_70714_bg.func_75776_a(5, this.lookAt = (Goal)new LookAtGoal((MobEntity)this, PlayerEntity.class, 8.0F));
     this.field_70714_bg.func_75776_a(6, this.randomGoal = (Goal)new LookRandomlyGoal((MobEntity)this));
     this.field_70714_bg.func_75776_a(7, (Goal)(this.destroyTargetBlock = new DestroyTargetBlock(DoorBlock.class, (CreatureEntity)this, 6.0D)));
     this.field_70714_bg.func_75776_a(7, (Goal)new DestroyTargetBlock(Blocks.TNT.getDefaultState(), (CreatureEntity)this, 6.0D));
     this.field_70715_bh.func_75776_a(1, this.hurtGoal = (Goal)new HurtByTargetGoal((CreatureEntity)this, new Class[0]));
     this.field_70715_bh.func_75776_a(2, this.nearestPlayerGoal = (Goal)new NearestAttackableTargetGoalForDalek((MobEntity)this, PlayerEntity.class, true));
     this.field_70715_bh.func_75776_a(3, (Goal)new NearestAttackableTargetGoalForDalek((MobEntity)this, VillagerEntity.class, true));
     this.field_70715_bh.func_75776_a(4, (Goal)new NearestAttackableTargetGoalForDalek((MobEntity)this, CybermanEntity.class, true));
     this.field_70715_bh.func_75776_a(5, (Goal)new NearestAttackableTargetGoalForDalekRenegadeImperial((MobEntity)this, DalekEntity.class, true));
  }


  
  public void setID(String id) {
     func_184212_Q().func_187227_b(DALEK_TYPE, id);
     func_184212_Q().func_187227_b(DALEK_ARM_LEFT, getDalekData().getRandomLeftArm(this));
     func_184212_Q().func_187227_b(DALEK_ARM_RIGHT, getDalekData().getRandomRightArm(this));
  }
  
  public String getDalekArmLeft() {
     if (!getDalekData().getLeftArmAttatchments().contains(func_184212_Q().func_187225_a(DALEK_ARM_LEFT))) {
       func_184212_Q().func_187227_b(DALEK_ARM_LEFT, "GunArm");
    }
     return (String)func_184212_Q().func_187225_a(DALEK_ARM_LEFT);
  }
  
  public String getDalekArmRight() {
     if (!getDalekData().getRightArmAttatchments().contains(func_184212_Q().func_187225_a(DALEK_ARM_RIGHT))) {
       func_184212_Q().func_187227_b(DALEK_ARM_RIGHT, "SuctionArm");
    }
     return (String)func_184212_Q().func_187225_a(DALEK_ARM_RIGHT);
  }
  
  public void setupDalek() {
     getDalekData().setupDalek((Entity)this);
     func_110148_a(Attributes.field_233818_a_).func_111128_a(getDalekData().getMaxHealth());
     func_110148_a(Attributes.field_233821_d_).func_111128_a(getDalekData().getMoveSpeed());
     func_110148_a(Attributes.field_233819_b_).func_111128_a(15.0D);
    
     if (getDalekData() instanceof com.swdteam.common.entity.dalek.types.Nether) {
       this.field_70714_bg.func_75776_a(5, (Goal)new WaterAvoidingRandomWalkingGoal((CreatureEntity)this, 1.0D));
    }
    
     if (getDalekData().canFly()) {
       this.field_70765_h = (MovementController)new FlyingMovementController((MobEntity)this, 10, false);
       FlyingPathNavigator flyingpathnavigator = new FlyingPathNavigator((MobEntity)this, this.world);
       flyingpathnavigator.func_192879_a(false);
       flyingpathnavigator.func_212239_d(true);
       flyingpathnavigator.func_192878_b(true);
       this.field_70699_by = (PathNavigator)flyingpathnavigator;
       func_110148_a(Attributes.field_233822_e_).func_111128_a(0.4D);
    } 
  }
  
  public void set() {
     func_110148_a(Attributes.field_233821_d_).func_111128_a(0.5D);
     func_110148_a(Attributes.field_233823_f_).func_111128_a(1.2000000476837158D);
     func_110148_a(Attributes.field_233819_b_).func_111128_a(15.0D);
  }
  
  public int getFuse() {
     return ((Integer)func_184212_Q().func_187225_a(DALEK_FUSE)).intValue();
  }
  
  public boolean canBreakIn() {
     return ((Boolean)func_184212_Q().func_187225_a(DALEK_BREAK_IN)).booleanValue();
  }

  
  public void func_233629_a_(LivingEntity entity, boolean b) {
     if (getDalekData().canFly()) {
       super.func_233629_a_(entity, b);
    }
  }
  
  public void getBreakIn(boolean state) {
     func_184212_Q().func_187227_b(DALEK_BREAK_IN, Boolean.valueOf(state));
     if (state) {
       this.field_70714_bg.func_85156_a((Goal)this.destroyTargetBlock);
    } else {
       this.field_70714_bg.func_75776_a(7, (Goal)this.destroyTargetBlock);
    } 
  }

  
  public ITextComponent getName() {
     if (func_145818_k_()) {
       return func_200201_e();
    }
     return super.getName();
  }

  
  public void func_70645_a(DamageSource cause) {
     getDalekData().onDeath((Entity)this);
     getDalekData().setDead(true);
     super.func_70645_a(cause);
  }

  
  public void func_174812_G() {
     getDalekData().onDeath((Entity)this);
     getDalekData().setDead(true);
     super.func_174812_G();
  }
  
  public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
     return MobEntity.func_233666_p_()
       .func_233815_a_(Attributes.field_233821_d_, 0.30000001192092896D)
       .func_233815_a_(Attributes.field_233818_a_, 40.0D)
       .func_233815_a_(Attributes.field_233823_f_, 4.0D)
       .func_233815_a_(Attributes.field_233819_b_, 15.0D)
       .func_233815_a_(Attributes.field_233822_e_, 0.0D);
  }
  
  public void goCrazy() {
     this.field_70714_bg.func_85156_a(this.waterOne);
     this.field_70714_bg.func_85156_a(this.laserRange);
     this.field_70714_bg.func_85156_a(this.lookAt);
     this.field_70714_bg.func_85156_a(this.randomGoal);
     this.field_70715_bh.func_85156_a(this.hurtGoal);
     this.field_70715_bh.func_85156_a(this.nearestPlayerGoal);
  }

  
  public boolean func_70097_a(DamageSource damageSource, float amount) {
     if (damageSource != null && damageSource.func_76346_g() != this) {
       getDalekData().onAttacked((Entity)this, damageSource.func_76346_g(), damageSource);
       if (damageSource.func_76346_g() != null) func_70625_a(damageSource.func_76346_g(), 1.0F, 1.0F); 
       this.world.func_184134_a(getPosX(), getPosY(), getPosZ(), (SoundEvent)DMSoundEvents.ENTITY_DALEK_HURT.get(), func_184176_by(), 1.0F, 1.0F, false);
    } 
     return super.func_70097_a(damageSource, amount);
  }

  
  public CreatureAttribute func_70668_bt() {
     return CreatureAttribute.field_223223_b_;
  }
  
  protected PathNavigator func_175447_b(World world) {
     return super.func_175447_b(world);
  }

  
  protected float func_191954_d(float f) {
     return 0.0F;
  }

  
  protected void func_180429_a(BlockPos pos, BlockState state) {
     SoundEvent sound = getDalekData().getMovementSound((Entity)this);
    
     if (sound != null) {
       func_184185_a(sound, 0.2F, 1.0F);
    }
  }
  
  private void calculateLifting() {
     this.oLift = this.lift;
     this.oLiftSpeed = this.liftSpeed;
     this.liftSpeed = (float)(this.liftSpeed + (!this.field_70122_E ? 4 : -1) * 0.3D);
     this.liftSpeed = MathHelper.func_76131_a(this.liftSpeed, 0.0F, 1.0F);
     if (!this.field_70122_E && this.lifting < 1.0F) {
       if (this.field_70173_aa % 15 == 0) {
         func_184185_a((SoundEvent)DMSoundEvents.ENTITY_DALEK_HOVER.get(), 1.0F, 1.0F);
      }
       this.lifting = 1.0F;
    } 
    
     this.lifting = (float)(this.lifting * 0.9D);
     Vector3d vector3d = getMotion();
     if (!this.field_70122_E && vector3d.y < 0.0D) {
       setMotion(vector3d.func_216372_d(1.0D, 0.6D, 1.0D));
    }
     this.lift += this.lifting * 2.0F;
  }

  
  public boolean func_225503_b_(float distance, float damageMultiplier) {
     if (getDalekData().canFly()) return false; 
     return super.func_225503_b_(distance, damageMultiplier);
  }
  
  public boolean isFlying() {
     return !this.field_70122_E;
  }

  
  public void func_70636_d() {
     super.func_70636_d();
     if (getDalekData().canFly()) {
       calculateLifting();
    }
    
     if (!this.isSetup) {
       setupDalek();
       this.isSetup = true;
    } 
     getDalekData().onUpdate((Entity)this);
    
     if (!this.world.isRemote && 
       getFuse() >= 0) {
       func_184212_Q().func_187227_b(DALEK_FUSE, Integer.valueOf(getFuse() + 1));
      
       if (getFuse() > 25) {
         if (getAttackTarget() != null) {
           func_70625_a((Entity)getAttackTarget(), 10.0F, 10.0F);
           getDalekData().spawnLaserAttack(this, getAttackTarget());
        } 
         func_184212_Q().func_187227_b(DALEK_FUSE, Integer.valueOf(-1));
      } 
    } 

    
     if (!this.world.isRemote && 
       isNitroNined()) {
       this.timer++;
       if (this.timer > 45) {
         this.world.func_217398_a((Entity)this, (getPositionVec()).x, (getPositionVec()).y, (getPositionVec()).z, 4.0F, true, Explosion.Mode.BREAK);
         this.timer = 0;
         func_174812_G();
      } 
    } 
  }


  
  public float func_213307_e(Pose pose) {
     return 0.5F;
  }

  
  protected float func_213348_b(Pose poseIn, EntitySize sizeIn) {
     return 1.5F;
  }
  
  public double func_70033_W() {
     return -0.6D;
  }
  
  public ActionResultType func_230254_b_(PlayerEntity player, Hand hand) {
     getDalekData().mobInteract(player, hand, (Entity)this);
     return super.func_230254_b_(player, hand);
  }


  
  public void func_70071_h_() {
     super.func_70071_h_();
  }


  
  public boolean func_213380_a(IWorld worldIn, SpawnReason spawnReasonIn) {
     if (this.world.func_175659_aa().func_151525_a() == 0) {
       remove();
       return false;
    } 
     return true;
  }


  
  public ILivingEntityData func_213386_a(IServerWorld world, DifficultyInstance difficulty, SpawnReason spawnReason, ILivingEntityData spawnDataIn, CompoundNBT entityTag) {
     String idToUse = null;
     if (spawnReason != SpawnReason.SPAWN_EGG && spawnReason != SpawnReason.DISPENSER) {
       IDalek dalekSpawn = DMDalekRegistry.getDalekForBiome((World)world.func_201672_e(), world.func_226691_t_(getPosition()));
      
       if (dalekSpawn != null) {
         if (spawnReason == SpawnReason.NATURAL || spawnReason == SpawnReason.CHUNK_GENERATION) {
           idToUse = DMDalekRegistry.getRandomDalek(this.field_70146_Z, dalekSpawn.getType()).getID();
        } else {
           idToUse = DMDalekRegistry.getRandomDalek(this.field_70146_Z);
        } 
      } else {
         idToUse = DMDalekRegistry.getRandomDalek(this.field_70146_Z);
      } 
    } 
     if (idToUse != null) setID(idToUse); 
     return super.func_213386_a(world, difficulty, spawnReason, spawnDataIn, entityTag);
  }

  
  public IDalek getDalekData() {
     if (this.cachedData == null) {
       this.cachedData = DMDalekRegistry.getDalek((String)func_184212_Q().func_187225_a(DALEK_TYPE));
    }
     return this.cachedData;
  }

  
  public ItemStack getPickedResult(RayTraceResult target) {
     Item i = (Item)ForgeRegistries.ITEMS.getValue(new ResourceLocation("dalekmod", getDalekData().getType().getRegistryName() + "_spawner"));
     if (i != null) {
       return new ItemStack((IItemProvider)i);
    }
     return new ItemStack((IItemProvider)DMItems.DALEK_SPAWNER[0].get());
  }

  
  protected void func_70088_a() {
     super.func_70088_a();
    
     if (this.world.getDimensionKey().getRegistryLocation().equals(Dimension.field_236054_c_.getRegistryLocation())) {
       this.field_70180_af.func_187214_a(DALEK_TYPE, DMDalekRegistry.NETHER_DALEK.getID());
    } else {
       this.field_70180_af.func_187214_a(DALEK_TYPE, DMDalekRegistry.getRandomDalek(this.field_70146_Z));
    } 
    
     this.field_70180_af.func_187214_a(DALEK_FUSE, Integer.valueOf(-1));
     this.field_70180_af.func_187214_a(DALEK_ARM_LEFT, getDalekData().getRandomLeftArm(this));
     this.field_70180_af.func_187214_a(DALEK_ARM_RIGHT, getDalekData().getRandomRightArm(this));
     this.field_70180_af.func_187214_a(DALEK_NITRO_NINED, Boolean.valueOf(false));
  }

  
  public void func_213281_b(CompoundNBT compound) {
     super.func_213281_b(compound);
     compound.func_74778_a(DMNBTKeys.TYPE_DALEK, (String)func_184212_Q().func_187225_a(DALEK_TYPE));
     compound.func_74778_a(DMNBTKeys.ARM_ATTACHMENT_LEFT, getDalekArmLeft());
     compound.func_74778_a(DMNBTKeys.ARM_ATTACHMENT_RIGHT, getDalekArmRight());
     compound.putInt(DMNBTKeys.DALEK_FUSE, ((Integer)func_184212_Q().func_187225_a(DALEK_FUSE)).intValue());
     compound.func_74757_a(DMNBTKeys.DALEK_NITRO_NINED, ((Boolean)func_184212_Q().func_187225_a(DALEK_NITRO_NINED)).booleanValue());
  }

  
  public void func_70037_a(CompoundNBT compound) {
     super.func_70037_a(compound);
     this.field_70180_af.func_187227_b(DALEK_TYPE, compound.func_74779_i(DMNBTKeys.TYPE_DALEK));
     this.field_70180_af.func_187227_b(DALEK_ARM_LEFT, compound.func_74779_i(DMNBTKeys.ARM_ATTACHMENT_LEFT));
     this.field_70180_af.func_187227_b(DALEK_ARM_RIGHT, compound.func_74779_i(DMNBTKeys.ARM_ATTACHMENT_RIGHT));
     this.field_70180_af.func_187227_b(DALEK_FUSE, Integer.valueOf(compound.getInt(DMNBTKeys.DALEK_FUSE)));
     this.field_70180_af.func_187227_b(DALEK_NITRO_NINED, Boolean.valueOf(compound.func_74767_n(DMNBTKeys.DALEK_NITRO_NINED)));
    
     this.cachedData = null;
  }
  
  public boolean isNitroNined() {
     return ((Boolean)func_184212_Q().func_187225_a(DALEK_NITRO_NINED)).booleanValue();
  }


  
  public ActionResultType func_184199_a(PlayerEntity player, Vector3d p_184199_2_, Hand hand) {
     if (player.getHeldItem(hand).getItem() == Item.func_150898_a((Block)DMBlocks.NITRO9.get()) && 
       !isNitroNined()) {
       func_184212_Q().func_187227_b(DALEK_NITRO_NINED, Boolean.valueOf(true));
       this.world.playSound(null, getPosition(), SoundEvents.field_187602_cF, func_184176_by(), 1.0F, 1.0F);
       this.world.playSound(null, getPosition(), SoundEvents.field_187904_gd, func_184176_by(), 1.0F, 1.0F);
       if (!player.isCreative()) {
         player.getHeldItem(hand).func_190918_g(1);
      }
       return ActionResultType.CONSUME;
    } 

    
     return super.func_184199_a(player, p_184199_2_, hand);
  }

  
  public void func_184206_a(DataParameter<?> key) {
     if (DALEK_TYPE.equals(key)) {
       this.cachedData = DMDalekRegistry.getDalek((String)func_184212_Q().func_187225_a(DALEK_TYPE));
    }
     super.func_184206_a(key);
  }

  
  public void func_82196_d(LivingEntity target, float distanceFactor) {
     getDalekData().onPreLaserAttack(this, target, distanceFactor);
  }


  
  public void fireLaserAt(BlockPos target, float distanceFactor) {}

  
  public boolean canPlayAmbientSound() {
     return true;
  }

  
  protected SoundEvent func_184639_G() {
     if (getAttackTarget() == null && 
       this.field_70173_aa >= 200) {
       return getDalekData().getAmbientSound((Entity)this);
    }

    
     return null;
  }

  
  protected SoundEvent func_184601_bQ(DamageSource p_184601_1_) {
     return getDalekData().getHurtSound((Entity)this);
  }

  
  protected SoundEvent func_184615_bR() {
     return getDalekData().getDeathSound((Entity)this);
  }
  
  public boolean isLeftArm(String s) {
     return getDalekArmLeft().equalsIgnoreCase(s);
  }
  
  public boolean isRightArm(String s) {
     return getDalekArmRight().equalsIgnoreCase(s);
  }
}


