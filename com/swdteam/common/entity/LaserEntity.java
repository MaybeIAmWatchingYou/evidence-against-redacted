package com.swdteam.common.entity;

import com.swdteam.common.entity.dalek.DalekEntity;
import com.swdteam.common.entity.dalek.DalekType;
import com.swdteam.common.init.DMNBTKeys;
import com.swdteam.common.init.DMProjectiles;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;








public class LaserEntity
  extends ProjectileBaseEntity
{
  private boolean isExplosive = false;
  private boolean causesFireExplosion = false;
  public boolean emitsSmoke = false;
   private float explosionSize = 3.0F;
   public DMProjectiles.Laser laserType = DMProjectiles.BLUE_LASER;
  
  private boolean firstTick = true;
   public static final DataParameter<Integer> LASER_TYPE = EntityDataManager.func_187226_a(DalekEntity.class, DataSerializers.field_187192_b);
  
  public LaserEntity(World worldIn, LivingEntity shooter, float yOffset, float damage) {
     super(worldIn, shooter, yOffset, damage);
  }
  
  public LaserEntity(EntityType<? extends ThrowableEntity> type, World worldIn) {
     super(type, worldIn);
  }
  
  public DMProjectiles.Laser getLaserType() {
     if (this.laserType == null) {
       this.laserType = DMProjectiles.getLaser(((Integer)func_184212_Q().func_187225_a(LASER_TYPE)).intValue());
    }
    
     return this.laserType;
  }
  
  public void setExplosive(boolean isExplosive) {
     this.isExplosive = isExplosive;
  }
  
  public void setExplosionSize(float explosionSize) {
     this.explosionSize = explosionSize;
  }
  
  public float getExplosionSize() {
     return this.explosionSize;
  }

  
  public void setCausesFireExplosion(boolean causesFireExplosion) {
     this.causesFireExplosion = causesFireExplosion;
  }


  
  public void setLaserType(DMProjectiles.Laser laserType) {
     this.laserType = laserType;
     func_184212_Q().func_187227_b(LASER_TYPE, Integer.valueOf(laserType.getId()));
  }
  
  protected void func_70088_a() {
     super.func_70088_a();
     func_184212_Q().func_187214_a(LASER_TYPE, Integer.valueOf(0));
  }
  
  public void func_213281_b(CompoundNBT compound) {
     super.func_213281_b(compound);
     compound.putInt(DMNBTKeys.TYPE_LASER, ((Integer)func_184212_Q().func_187225_a(LASER_TYPE)).intValue());
  }
  
  public void func_70037_a(CompoundNBT compound) {
     super.func_70037_a(compound);
     func_184212_Q().func_187227_b(LASER_TYPE, Integer.valueOf(compound.getInt(DMNBTKeys.TYPE_LASER)));
  }

  
  public void func_184206_a(DataParameter<?> key) {
     if (LASER_TYPE.equals(key)) {
       this.laserType = DMProjectiles.getLaser(((Integer)this.field_70180_af.func_187225_a(LASER_TYPE)).intValue());
    }
    
     super.func_184206_a(key);
  }


  
  protected void func_70227_a(RayTraceResult result) {
     if (result != null) {
       if (result.func_216346_c() == RayTraceResult.Type.ENTITY) {
         if (result instanceof EntityRayTraceResult) {
           Entity hit = ((EntityRayTraceResult)result).func_216348_a();
           if (hit != null && this.shooter != null) {
             if (!(hit instanceof net.minecraft.entity.player.PlayerEntity) && 
               hit.getClass().equals(this.shooter.getClass())) {
              
               if (hit instanceof DalekEntity && this.shooter instanceof DalekEntity) {
                 DalekEntity d1 = (DalekEntity)hit;
                 DalekEntity d2 = (DalekEntity)this.shooter;
                
                 if ((d1.getDalekData().getType() == DalekType.RENEGADE && (d2.getDalekData().getType() == DalekType.IMPERIAL || d2.getDalekData().getType() == DalekType.SPECIAL_WEAPONS)) || ((d1.getDalekData().getType() == DalekType.IMPERIAL || d1.getDalekData().getType() == DalekType.SPECIAL_WEAPONS) && d2.getDalekData().getType() == DalekType.RENEGADE)) {
                   super.func_70227_a(result);
                  
                  return;
                } 
              } 
              
              return;
            } 
             if (hit.equals(this.shooter)) {
              return;
            }
          } 
        } 
       } else if (result.func_216346_c() == RayTraceResult.Type.BLOCK) {
         func_230299_a_((BlockRayTraceResult)result);
      } 
    }
    
     if (getLaserType().getLaserInterface() != null)
       getLaserType().getLaserInterface().onImpact(this.world, result, this); 
     if (this.isExplosive) {
       boolean isAllowed = ProjectileBaseEntity.canGriefWithProjectile(this.world, (Entity)this.shooter);
       if (result != null && result.func_216347_e() != null) {
         this.world.func_217398_a((Entity)this, (result.func_216347_e()).x, (result.func_216347_e()).y, (result.func_216347_e()).z, this.explosionSize, (isAllowed && this.causesFireExplosion), isAllowed ? Explosion.Mode.DESTROY : Explosion.Mode.NONE);
         func_174812_G();
      } 
    } 
     super.func_70227_a(result);
  }
  
  public void setEmitsSmoke(boolean emitsSmoke) {
     this.emitsSmoke = emitsSmoke;
  }


  
  public void func_70030_z() {
     if (this.emitsSmoke);


    
     if (this.firstTick) {
       if (getLaserType().getLaserInterface() != null) getLaserType().getLaserInterface().onCreate(this); 
       this.firstTick = false;
    } 
     if (getLaserType().getLaserInterface() != null) getLaserType().getLaserInterface().tick(this); 
     super.func_70030_z();
  }
}


