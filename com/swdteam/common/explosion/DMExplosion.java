package com.swdteam.common.explosion;

import com.google.common.collect.Sets;
import java.util.Optional;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EntityExplosionContext;
import net.minecraft.world.Explosion;
import net.minecraft.world.ExplosionContext;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;


public class DMExplosion
  extends Explosion
{
   private static final ExplosionContext DEFAULT_CONTEXT = new ExplosionContext();
   private ExplosionContext context = null;
   public float size = 2.0F;

  public World world;
  public double field_77284_b;

  public DMExplosion(World worldIn, Entity exploderIn, double xIn, double yIn, double zIn, float sizeIn, boolean causesFireIn, Explosion.Mode modeIn) {
     super(worldIn, exploderIn, DamageSource.field_76377_j, (ExplosionContext)null, xIn, yIn, zIn, sizeIn, causesFireIn, modeIn);
     this.context = (this.context == null) ? makeDamageCalculator(exploderIn) : this.context;
     this.size = sizeIn;
     this.world = worldIn;
     this.field_77284_b = xIn;
     this.field_77285_c = yIn;
     this.field_77282_d = zIn;
  }
  public double field_77285_c; public double field_77282_d;
  private ExplosionContext makeDamageCalculator(@Nullable Entity entity) {
     return (entity == null) ? DEFAULT_CONTEXT : (ExplosionContext)new EntityExplosionContext(entity);
  }


  public void doExplosionA() {
     Set<BlockPos> set = Sets.newHashSet();

     int i = 16;

     for (int j = 0; j < 16; j++) {
       for (int k = 0; k < 16; k++) {
         for (int l = 0; l < 16; l++) {
           if (j == 0 || j == 15 || k == 0 || k == 15 || l == 0 || l == 15) {
             double d0 = (j / 15.0F * 2.0F - 1.0F);
             double d1 = (k / 15.0F * 2.0F - 1.0F);
             double d2 = (l / 15.0F * 2.0F - 1.0F);
             double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
             d0 /= d3;
             d1 /= d3;
             d2 /= d3;
             float f = this.size * (0.7F + this.world.rand.nextFloat() * 0.6F);
             double d4 = this.field_77284_b;
             double d6 = this.field_77285_c;
             double d8 = this.field_77282_d;


             for (float f1 = 0.3F; f > 0.0F; f -= 0.22500001F) {
               BlockPos blockpos = new BlockPos(d4, d6, d8);
               BlockState blockstate = this.world.getBlockState(blockpos);
               FluidState fluidstate = this.world.func_204610_c(blockpos);
               Optional<Float> optional = this.context.func_230312_a_(this, (IBlockReader)this.world, blockpos, blockstate, fluidstate);
               if (optional.isPresent()) {
                 f -= (((Float)optional.get()).floatValue() + 0.3F) * 0.3F;
              }

               if (f > 0.0F && this.context.func_230311_a_(this, (IBlockReader)this.world, blockpos, blockstate, f)) {
                 set.add(blockpos);
              }

               d4 += d0 * 0.30000001192092896D;
               d6 += d1 * 0.30000001192092896D;
               d8 += d2 * 0.30000001192092896D;
            }
          }
        }
      }
    }
     func_180343_e().addAll(set);
  }
}


