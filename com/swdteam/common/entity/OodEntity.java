package com.swdteam.common.entity;

import com.swdteam.common.entity.dalek.DalekEntity;
import com.swdteam.common.init.DMBlocks;
import com.swdteam.common.init.DMEntities;
import com.swdteam.common.init.DMItems;
import com.swdteam.util.SWDMathUtils;
import java.util.ArrayList;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
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
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.extensions.IForgeEntity;
import net.minecraftforge.event.ForgeEventFactory;

public class OodEntity extends MonsterEntity implements IForgeEntity {
   private ItemStack pickResult = new ItemStack((IItemProvider)DMItems.OOD_SPAWNER.get());
  private Goal meeleAttack;
   public static final DataParameter<Boolean> IS_HOSTILE = EntityDataManager.func_187226_a(OodEntity.class, DataSerializers.field_187198_h);
  
   public static ArrayList<Item> oodGifts = new ArrayList<>();

  
  public OodEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
     super(type, worldIn);
     oodGifts.add(DMItems.CHEESE.get());
     oodGifts.add(DMItems.PEPSI.get());
     oodGifts.add(((Block)DMBlocks.CHEESE_BLOCK.get()).func_199767_j());
     oodGifts.add(DMItems.KRONKMEAT.get());
     oodGifts.add(Items.field_151054_z);
     oodGifts.add(DMItems.KRONKDOG.get());
  }
  
  public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
     return MobEntity.func_233666_p_()
       .func_233815_a_(Attributes.field_233821_d_, 0.3499999940395355D)
       .func_233815_a_(Attributes.field_233818_a_, 30.0D)
       .func_233815_a_(Attributes.field_233823_f_, 2.0D).func_233815_a_(Attributes.field_233819_b_, 20.0D);
  }

  
  protected void func_184651_r() {
     super.func_184651_r();
    
     this.meeleAttack = (Goal)new MeleeAttackGoal((CreatureEntity)this, 1.0D, false);
    
     this.field_70714_bg.func_75776_a(3, (Goal)new AvoidEntityGoal((CreatureEntity)this, PiglinEntity.class, 6.0F, 1.0D, 1.2D));
     this.field_70714_bg.func_75776_a(3, (Goal)new AvoidEntityGoal((CreatureEntity)this, PiglinBruteEntity.class, 6.0F, 1.0D, 1.2D));
    
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

  
  public boolean isHostile() {
     return ((Boolean)func_184212_Q().func_187225_a(IS_HOSTILE)).booleanValue();
  }




  
  public void func_213281_b(CompoundNBT compound) {
     compound.func_74757_a("hostile", ((Boolean)func_184212_Q().func_187225_a(IS_HOSTILE)).booleanValue());
     super.func_213281_b(compound);
  }

  
  public void setHostile(boolean par1) {
     this.field_70180_af.func_187227_b(IS_HOSTILE, Boolean.valueOf(par1));
  }

  
  public ActionResultType func_184199_a(PlayerEntity player, Vector3d p_184199_2_, Hand p_184199_3_) {
     if (player.getHeldItem(p_184199_3_).getItem().equals(Items.field_185165_cW))
    {
       if (!isHostile())
      {
         if (!this.world.isRemote) {
          
           player.getHeldItem(p_184199_3_).func_190918_g(1);
          
           func_184185_a(SoundEvents.field_187537_bA, 1.0F, 1.0F);
          
           ItemStack stack = new ItemStack((IItemProvider)oodGifts.get((int)SWDMathUtils.randomRange(0.0F, (oodGifts.size() - 1))));
          
           func_199701_a_(stack);
        } 
      }
    }
    
     return super.func_184199_a(player, p_184199_2_, p_184199_3_);
  }

  
  public void func_70037_a(CompoundNBT compound) {
     if (compound.func_74764_b("hostile")) {
       func_184212_Q().func_187227_b(IS_HOSTILE, Boolean.valueOf(compound.func_74767_n("hostile")));
       if (((Boolean)func_184212_Q().func_187225_a(IS_HOSTILE)).equals(Boolean.valueOf(true)))
      {
         this.field_70714_bg.func_75776_a(2, (Goal)new MeleeAttackGoal((CreatureEntity)this, 1.0D, false));
      }
    } else {
       setHostile(false);
    } 
     super.func_70037_a(compound);
  }


  
  public void func_70071_h_() {
     super.func_70071_h_();
  }




  
  protected void func_70088_a() {
     func_184212_Q().func_187214_a(IS_HOSTILE, Boolean.valueOf(false));
     super.func_70088_a();
  }

  
  public void setCybermanType(int i) {
     if (i < 0 || i >= 4) {
       i = this.field_70146_Z.nextInt(3);
    }
  }

  
  protected void func_180429_a(BlockPos pos, BlockState blockIn) {
     func_184185_a(SoundEvents.field_187566_ao, 0.5F, SWDMathUtils.randomRange(0.8F, 1.2F));
  }

  
  public void func_70642_aH() {}
  
  protected SoundEvent func_184601_bQ(DamageSource source) {
     return SoundEvents.field_187800_eb;
  }

  
  public boolean func_70097_a(DamageSource source, float f) {
     if (source.func_76346_g() instanceof LivingEntity &&
       !isHostile()) {
      
       this.field_70714_bg.func_75776_a(2, (Goal)new MeleeAttackGoal((CreatureEntity)this, 1.0D, false));
       setHostile(true);
    } 


    
     return super.func_70097_a(source, f);
  }

  
  public void func_241847_a(ServerWorld level, LivingEntity target) {
     super.func_241847_a(level, target);
     if ((level.func_175659_aa() == Difficulty.NORMAL || level.func_175659_aa() == Difficulty.HARD) && target instanceof VillagerEntity && ForgeEventFactory.canLivingConvert(target, (EntityType)DMEntities.CYBERMANVILLAGER_ENTITY.get(), timer -> {
         }) && (level.func_175659_aa() != Difficulty.NORMAL || (level.func_175659_aa() != Difficulty.HARD && this.field_70146_Z.nextBoolean()))) {
      return;
    }
  }


  
  public ItemStack getPickedResult(RayTraceResult target) {
     return this.pickResult;
  }
}


