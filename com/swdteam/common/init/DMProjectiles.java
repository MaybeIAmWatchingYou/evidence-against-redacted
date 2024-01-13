package com.swdteam.common.init;

import com.swdteam.common.entity.LaserEntity;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.state.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

public class DMProjectiles
{
   private static List<Laser> LASERS = new ArrayList<>();
  
  public static Laser BLUE_LASER;
  
  public static Laser RED_LASER;
  
  public static Laser GREEN_LASER;
  
  public static Laser ORANGE_LASER;
  
  public static Laser POISON;
  
  public static Laser FLASH;
  public static Laser SMOKE;
  public static Laser FIRE;
  public static Laser BULLET;
  public static Laser NAUSEA_LASER;
  public static Laser EXPLOSIVE_LASER;
  
  public static void init() {
     BLUE_LASER = addLaser(90, 200, 255);
     RED_LASER = addLaser(255, 60, 50);
     GREEN_LASER = addLaser(60, 210, 30);
     ORANGE_LASER = addLaser(255, 140, 60);
    
     POISON = addLaser(false);
     FLASH = addLaser(false);
     SMOKE = addLaser(false);
     FIRE = addLaser(false);
    
     BULLET = addLaser(1, 1, 1);
     NAUSEA_LASER = addLaser(130, 200, 255);
     EXPLOSIVE_LASER = addLaser(255, 170, 10);

    
     NAUSEA_LASER.setLaserInterface(new ILaser()
        {
          public void onImpact(World world, RayTraceResult result, LaserEntity laser) {
             if (result.func_216346_c() == RayTraceResult.Type.ENTITY) {
               Entity entity = ((EntityRayTraceResult)result).func_216348_a();
               if (entity != null && entity instanceof LivingEntity) {
                 if (entity instanceof PlayerEntity) {
                   PlayerEntity player = (PlayerEntity)entity;
                   if (player.func_184585_cz())
                    return; 
                 }  ((LivingEntity)entity).func_195064_c(new EffectInstance(Effects.field_76431_k, 200, 2));
              } 
            } 
          }



          
          public void tick(LaserEntity laser) {}
        });
     FLASH.setLaserInterface(new ILaser()
        {
          public void tick(LaserEntity laser) {
             if (laser.world.isRemote) {
               for (int i = 0; i < 5; i++) {
                 float randomX = laser.world.rand.nextFloat() * (laser.world.rand.nextBoolean() ? -1 : true) / 2.0F;
                 float randomY = laser.world.rand.nextFloat() * (laser.world.rand.nextBoolean() ? -1 : true) / 2.0F;
                 float randomZ = laser.world.rand.nextFloat() * (laser.world.rand.nextBoolean() ? -1 : true) / 2.0F;
                 laser.world.addParticle((IParticleData)ParticleTypes.field_197598_I, laser.getPosX() + randomX, laser.getPosY() + randomY, laser.getPosZ() + randomZ, 0.0D, 0.0D, 0.0D);
              } 
            }
            
             if (laser.field_70173_aa > 10) {
               laser.remove();
            }
          }

          
          public void onImpact(World world, RayTraceResult result, LaserEntity laser) {
             if (result.func_216346_c() == RayTraceResult.Type.ENTITY) {
               Entity entity = ((EntityRayTraceResult)result).func_216348_a();
               if (entity != null && entity instanceof LivingEntity) {
                 if (entity instanceof PlayerEntity) {
                   PlayerEntity player = (PlayerEntity)entity;
                   if (player.func_184585_cz())
                    return; 
                 }  ((LivingEntity)entity).func_195064_c(new EffectInstance(Effects.field_76421_d, 100, 2));
              } 
            } 
          }
        });
    
     SMOKE.setLaserInterface(new ILaser()
        {
          public void tick(LaserEntity laser) {
             if (laser.world.isRemote) {
               for (int i = 0; i < 5; i++) {
                 float randomX = laser.world.rand.nextFloat() * (laser.world.rand.nextBoolean() ? -1 : true) / 2.0F;
                 float randomY = laser.world.rand.nextFloat() * (laser.world.rand.nextBoolean() ? -1 : true) / 2.0F;
                 float randomZ = laser.world.rand.nextFloat() * (laser.world.rand.nextBoolean() ? -1 : true) / 2.0F;
                 laser.world.addParticle((IParticleData)ParticleTypes.field_197613_f, laser.getPosX() + randomX, laser.getPosY() + randomY, laser.getPosZ() + randomZ, 0.0D, 0.0D, 0.0D);
              } 
            }
            
             if (laser.field_70173_aa > 10) {
               laser.remove();
            }
          }



          
          public void onImpact(World world, RayTraceResult result, LaserEntity laser) {}
        });
     POISON.setLaserInterface(new ILaser()
        {
          public void tick(LaserEntity laser) {
             if (laser.world.isRemote) {
               for (int i = 0; i < 5; i++) {
                 float ff = (15 - laser.field_70173_aa);
                 float randomX = laser.world.rand.nextFloat() * (laser.world.rand.nextBoolean() ? -1 : true) / ff / 10.0F;
                 float randomY = laser.world.rand.nextFloat() * (laser.world.rand.nextBoolean() ? -1 : true) / ff / 10.0F;
                 float randomZ = laser.world.rand.nextFloat() * (laser.world.rand.nextBoolean() ? -1 : true) / ff / 10.0F;
                 laser.world.addParticle((IParticleData)DMParticleTypes.POISON_GAS.get(), laser.getPosX() + randomX, laser.getPosY() + randomY, laser.getPosZ() + randomZ, 0.0D, 0.0D, 0.0D);
              } 
            }
            
             if (laser.field_70173_aa > 15) {
               laser.remove();
            }
          }

          
          public void onImpact(World world, RayTraceResult result, LaserEntity laser) {
             if (result.func_216346_c() == RayTraceResult.Type.ENTITY) {
               Entity entity = ((EntityRayTraceResult)result).func_216348_a();
               if (entity != null && entity instanceof LivingEntity) {
                 if (entity instanceof PlayerEntity) {
                   PlayerEntity player = (PlayerEntity)entity;
                   if (player.func_184585_cz())
                    return; 
                 }  ((LivingEntity)entity).func_195064_c(new EffectInstance(Effects.field_76436_u, 200, 2));
              } 
            } 
          }
        });
    
     EXPLOSIVE_LASER.setLaserInterface(new ILaser()
        {
          public void onCreate(LaserEntity laser) {
             laser.setExplosive(true);
             laser.setCausesFireExplosion(true);
          }
        });
    
     FIRE.setLaserInterface(new ILaser()
        {
          public void tick(LaserEntity laser) {
             if (laser.world.isRemote) {
               for (int i = 0; i < 25; i++) {
                 float ff = (10 - laser.field_70173_aa);
                 float randomX = laser.world.rand.nextFloat() * (laser.world.rand.nextBoolean() ? -1 : true) / ff / 1.0F;
                 float randomY = laser.world.rand.nextFloat() * (laser.world.rand.nextBoolean() ? -1 : true) / ff / 1.0F;
                 float randomZ = laser.world.rand.nextFloat() * (laser.world.rand.nextBoolean() ? -1 : true) / ff / 1.0F;
                 laser.world.addParticle((IParticleData)DMParticleTypes.FIRE_BALL.get(), laser.getPosX() + randomX, laser.getPosY() + randomY - 0.10000000149011612D, laser.getPosZ() + randomZ, 0.0D, 0.0D, 0.0D);
              } 
            }
            
             if (laser.field_70173_aa > 10) {
               laser.remove();
            }
          }

          
          public void onImpact(World world, RayTraceResult result, LaserEntity laser) {
             if (result.func_216346_c() == RayTraceResult.Type.ENTITY) {
               Entity entity = ((EntityRayTraceResult)result).func_216348_a();
               if (entity != null) entity.func_70015_d(3); 
             } else if (result
               .func_216346_c() == RayTraceResult.Type.BLOCK && 
               ForgeEventFactory.getMobGriefingEvent(world, laser.func_234616_v_())) {
              
               BlockRayTraceResult block = (BlockRayTraceResult)result;
               BlockPos blockpos = block.func_216350_a();
               BlockState blockstate = world.getBlockState(blockpos);
              
               if (CampfireBlock.func_241470_h_(blockstate)) {
                 world.setBlockState(blockpos, (BlockState)blockstate.func_206870_a((Property)CampfireBlock.field_220101_b, Boolean.valueOf(true)));
              } else {
                 blockpos = blockpos.func_177972_a(block.func_216354_b());
                 if (AbstractFireBlock.func_241465_a_(world, blockpos, laser.getHorizontalFacing()))
                   world.setBlockState(blockpos, AbstractFireBlock.func_235326_a_((IBlockReader)world, blockpos)); 
              } 
            } 
          }
        });
  }
  
  public static interface ILaser {
    default void onImpact(World world, RayTraceResult result, LaserEntity laser) {}
    
    default void tick(LaserEntity laser) {}
    
    default void onCreate(LaserEntity laser) {} }
  
  public static class Laser { private int id;
    private DMProjectiles.ILaser laserInterface;
    private float[] color;
    private boolean renders = true;
    
    public Laser(int id, float r, float g, float b) {
       this.id = id;
       this.color = new float[] { r, g, b };
    }
    
    public Laser(int id, boolean renders) {
       this.id = id;
       this.color = new float[] { 0.0F, 0.0F, 0.0F };
       this.renders = renders;
    }
    
    public float[] getColor() {
       return this.color;
    }


    
    public boolean equals(Object obj) {
       if (obj instanceof Laser && (
         (Laser)obj).getId() == getId()) {
         return true;
      }
      
       return super.equals(obj);
    }
    
    public int getId() {
       return this.id;
    }
    
    public boolean doesRender() {
       return this.renders;
    }
    
    public void setLaserInterface(DMProjectiles.ILaser laserInterface) {
       this.laserInterface = laserInterface;
    }
    
    public DMProjectiles.ILaser getLaserInterface() {
       return this.laserInterface;
    } }

  
  private static Laser addLaser(int r, int g, int b) {
     Laser l = new Laser(LASERS.size(), r / 255.0F, g / 255.0F, b / 255.0F);
     LASERS.add(l);
     return l;
  }
  
  private static Laser addLaser(boolean renders) {
     Laser l = new Laser(LASERS.size(), renders);
     LASERS.add(l);
     return l;
  }
  
  public static Laser getLaser(int id) {
     if (id < 0) {
       id = 0;
    }
     if (id >= LASERS.size()) {
       return LASERS.get(0);
    }
     return LASERS.get(id);
  }
  
  public static int getLaserID(Laser laser) {
     if (LASERS.contains(laser)) {
       return LASERS.indexOf(laser);
    }
     return 1;
  }
}


