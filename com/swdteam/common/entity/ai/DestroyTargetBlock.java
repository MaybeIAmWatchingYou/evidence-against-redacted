package com.swdteam.common.entity.ai;

import com.swdteam.common.entity.LaserEntity;
import com.swdteam.common.entity.dalek.DalekEntity;
import com.swdteam.common.entity.dalek.IDalek;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.event.ForgeEventFactory;

public class DestroyTargetBlock
  extends Goal {
  private final Class<? extends Block> block;
  private final BlockState blockState;
  private final MobEntity entity;
   private double distance = 20.0D;

  
  public DestroyTargetBlock(Class<? extends Block> blockIn, CreatureEntity creature, double distance) {
     this.block = blockIn;
     this.entity = (MobEntity)creature;
     this.distance = distance;
     this.blockState = null;
  }

  
  public DestroyTargetBlock(BlockState blockState, CreatureEntity creature, double distance) {
     this.blockState = blockState;
     this.entity = (MobEntity)creature;
     this.distance = distance;
     this.block = null;
  }





  
  public boolean func_75250_a() {
     return (ForgeEventFactory.getMobGriefingEvent(this.entity.world, (Entity)this.entity) && this.entity.world.rand.nextInt(2) == 1);
  }

  
  public void func_75246_d() {
     super.func_75246_d();
    
     RayTraceResult ray = pick((Entity)this.entity, this.distance, 0.0F, this.entity.world.rand.nextBoolean() ? 0.0F : -this.entity.func_70047_e(), false);
     if (ray != null && 
       ray.func_216346_c() == RayTraceResult.Type.BLOCK && 
       ray instanceof BlockRayTraceResult) {
       BlockRayTraceResult blockRay = (BlockRayTraceResult)ray;
       if (blockRay.func_216350_a() != null) {
         BlockState state = this.entity.world.getBlockState(blockRay.func_216350_a());
         if (isBlock(state))
        {
           fireLaser(blockRay.func_216350_a());
        }
      } 
    } 
  }


  
  public boolean isBlock(BlockState stateIn) {
     return (this.block != null) ? stateIn.getBlock().getClass().equals(this.block) : ((this.blockState == stateIn));
  }
  
  public RayTraceResult pick(Entity ent, double p_213324_1_, float p_213324_3_, float eyeOffset, boolean p_213324_4_) {
     Vector3d vec3d = ent.func_174824_e(1.0F).func_72441_c(0.0D, eyeOffset, 0.0D);
    
     Vector3d vec3d1 = ent.func_70676_i(p_213324_3_);
     Vector3d vec3d2 = vec3d.func_72441_c(vec3d1.x * p_213324_1_, vec3d1.y * p_213324_1_, vec3d1.z * p_213324_1_);
     return (RayTraceResult)ent.world.func_217299_a(new RayTraceContext(vec3d, vec3d2, RayTraceContext.BlockMode.OUTLINE, p_213324_4_ ? RayTraceContext.FluidMode.ANY : RayTraceContext.FluidMode.NONE, ent));
  }

  
  public void fireLaser(BlockPos target) {
     if (!(this.entity instanceof DalekEntity)) {
      return;
    }
    
     DalekEntity dalek = (DalekEntity)this.entity;
     IDalek dData = dalek.getDalekData();
    
     double d0 = 0.0D;
     double d1 = 0.0D;
     double d2 = 0.0D;
    
     d0 = (target.getX() + 0.5F) - this.entity.getPosX();
     d1 = (target.getY() + 0.5F) - this.entity.getPosY() - 1.0D;
     d2 = (target.getZ() + 0.5F) - this.entity.getPosZ();
    
     LaserEntity laser = new LaserEntity(dalek.world, (LivingEntity)dalek, 0.2F, 2.0F);
     laser.setLaserType(dData.getLaser(dalek));
     laser.setExplosive(true);
     laser.setExplosionSize(1.0F);
    
     laser.func_70186_c(d0, d1, d2, 2.5F, 0.0F);
     dalek.func_184185_a(dData.getShootSound((Entity)dalek), 1.0F, 1.0F);
     dalek.world.addEntity((Entity)laser);
  }
}


