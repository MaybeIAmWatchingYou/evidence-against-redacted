package com.swdteam.common.entity;

import com.swdteam.common.init.DMEntities;
import com.swdteam.common.init.DMGameRules;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.network.IPacket;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.network.NetworkHooks;




public class ProjectileBaseEntity
  extends ThrowableEntity
{
  public LivingEntity shooter;
   public float explosionSize = 8.0F;
  
  private boolean isExplosive;
  private DamageSource damageSource;
   private float damage = 1.0F;
   private int laser_id = 0;
  
   public float red = 1.0F;
   public float blue = 1.0F;
   public float green = 1.0F;
   public float alpha = 0.75F;
  public boolean emitsSmoke = true;
  
  public ProjectileBaseEntity(EntityType<? extends ThrowableEntity> type, World worldIn) {
     super(type, worldIn);
  }
  
  public ProjectileBaseEntity(World worldIn, LivingEntity shooter, float yOffset, float damage) {
     super((EntityType)DMEntities.LASER_ENTITY.get(), worldIn);
     setShooter(shooter);
     setPosition(shooter.getPosX(), shooter.getPosY() + shooter.func_70047_e() - (0.15F + yOffset), shooter.getPosZ());
     this.damage = damage;
     this.shooter = shooter;
     this.damageSource = (DamageSource)new EntityDamageSource(shooter.func_70022_Q(), (Entity)shooter);
  }


  
  protected void func_70088_a() {}


  
  public LivingEntity getShooter() {
     return this.shooter;
  }
  
  public void setShooter(LivingEntity shooter) {
     this.shooter = shooter;
  }
  
  public void shoot(Entity shooter, float pitch, float yaw, float p_184547_4_, float velocity, float inaccuracy) {
     float f = -MathHelper.func_76126_a(yaw * 0.017453292F) * MathHelper.func_76134_b(pitch * 0.017453292F);
     float f1 = -MathHelper.func_76126_a(pitch * 0.017453292F);
     float f2 = MathHelper.func_76134_b(yaw * 0.017453292F) * MathHelper.func_76134_b(pitch * 0.017453292F);
    
     Vector3d vec3d = (new Vector3d(f, f1, f2)).func_72432_b().func_72441_c(this.field_70146_Z.nextGaussian() * 0.007499999832361937D * inaccuracy, this.field_70146_Z.nextGaussian() * 0.007499999832361937D * inaccuracy, this.field_70146_Z.nextGaussian() * 0.007499999832361937D * inaccuracy).func_186678_a(velocity);
     setMotion(vec3d);
     float fd = MathHelper.func_76133_a(func_213296_b(vec3d));
     this.field_70177_z = (float)(MathHelper.func_181159_b(vec3d.x, vec3d.z) * 57.2957763671875D);
     this.field_70125_A = (float)(MathHelper.func_181159_b(vec3d.y, fd) * 57.2957763671875D);
     this.field_70126_B = this.field_70177_z;
     this.field_70127_C = this.field_70125_A;
    
     setMotion(getMotion().func_72441_c((shooter.getMotion()).x, shooter.isOnGround() ? 0.0D : (shooter.getMotion()).y, (shooter.getMotion()).z));
  }


  
  protected float func_70185_h() {
     return 0.0F;
  }


  
  protected void func_70227_a(RayTraceResult result) {
     if (!this.world.isRemote) {
       if (result.func_216346_c() == RayTraceResult.Type.ENTITY) {
         EntityRayTraceResult result1 = (EntityRayTraceResult)result;
         Entity entity = result1.func_216348_a();
        
         if (entity != null && this.damageSource != null && entity != this.shooter) {
           entity.func_70097_a(this.damageSource, this.damage);
        }
      }
       else if (result.func_216346_c() == RayTraceResult.Type.BLOCK) {
         BlockRayTraceResult rayTraceResult = (BlockRayTraceResult)result;
         BlockState state = this.world.getBlockState(rayTraceResult.func_216350_a());
         boolean isAllowed = canGriefWithProjectile(this.world, (Entity)this.shooter);
        
         if ((state.getBlock() instanceof net.minecraft.block.GlassBlock || state.getBlock() instanceof net.minecraft.block.PaneBlock) &&
           isAllowed) {
           this.world.setBlockState(rayTraceResult.func_216350_a(), Blocks.AIR.getDefaultState());
           this.world.playSound(null, rayTraceResult.func_216350_a(), state.getBlock().func_220072_p(state).func_185845_c(), SoundCategory.BLOCKS, 1.0F, 1.0F);
        } 
        
         if (state.func_185904_a().func_76220_a()) {
           func_174812_G();
        }
      } 
    }
  }

  
  public static boolean canGriefWithProjectile(World level, Entity shooter) {
     if (level.isRemote) return false;
     if (!level.getGameRules().getBoolean(DMGameRules.LASER_GRIEFING)) return false;
     if (shooter instanceof net.minecraft.entity.player.PlayerEntity) return true;
     return ForgeEventFactory.getMobGriefingEvent(level, shooter);
  }

  
  public void func_70071_h_() {
     super.func_70071_h_();
    
     if (this.field_70173_aa % 80 == 0) {
       func_174812_G();
    }
  }
  
  public void setDamage(float damage) {
     this.damage = damage;
  }
  
  public void setDamageSource(DamageSource damageSource) {
     this.damageSource = damageSource;
  }

  
  protected void func_213868_a(EntityRayTraceResult result) {
     Entity entity = result.func_216348_a();
    
     if (entity != null) {
       entity.func_70097_a(this.damageSource, this.damage);
    }
  }

  
  public IPacket<?> func_213297_N() {
     return NetworkHooks.getEntitySpawningPacket((Entity)this);
  }
}


