package com.swdteam.common.entity.dalek.types;

import com.swdteam.common.entity.dalek.DalekEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ITag;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

public class EnderSpecialWeapons extends SpecialWeapons {
  public EnderSpecialWeapons(String s) {
     super(s);
  }

  public void onUpdate(Entity e) {
     super.onUpdate(e);

     if (e.world.isRemote) {
       DalekEntity dalek = (DalekEntity)e;
       for (int i = 0; i < 2; i++) {
         float f = -(dalek.world.rand.nextFloat() - 0.5F) * 2.2F;
         float g = -(dalek.world.rand.nextFloat() - 0.5F) * 2.2F;
         float h = -(dalek.world.rand.nextFloat() - 0.5F) * 2.2F;
         dalek.world.addParticle((IParticleData)ParticleTypes.field_197599_J, dalek.func_226282_d_(0.5D), dalek.func_226283_e_(-0.01D), dalek.func_226287_g_(0.5D), f, g, h);
      }
    }
  }


  public void onAttacked(Entity dalek, Entity attacker, DamageSource damage) {
     if (dalek.world.rand.nextBoolean()) {
       teleportRandomly((LivingEntity)dalek);
       dalek.world.playSound(null, dalek.getPosition(), SoundEvents.field_187534_aX, SoundCategory.HOSTILE, 1.0F, 1.0F);
    }

     super.onAttacked(dalek, attacker, damage);
  }

  protected boolean teleportRandomly(LivingEntity dalek) {
     if (!dalek.world.isRemote() && dalek.func_70089_S()) {
       double d = dalek.getPosX() + (dalek.world.rand.nextDouble() - 0.5D) * 64.0D;
       double e = dalek.getPosY() + (dalek.world.rand.nextInt(64) - 32);
       double f = dalek.getPosZ() + (dalek.world.rand.nextDouble() - 0.5D) * 64.0D;
       return teleportTo(dalek, d, e, f);
    }
     return false;
  }


  private boolean teleportTo(LivingEntity dalek, Entity entity) {
     Vector3d vec3d = new Vector3d(dalek.getPosX() - entity.getPosX(), dalek.func_226283_e_(0.5D) - entity.func_226280_cw_(), dalek.getPosZ() - entity.getPosZ());
     vec3d = vec3d.func_72432_b();
     double d = 16.0D;
     double e = dalek.getPosX() + (dalek.world.rand.nextDouble() - 0.5D) * 8.0D - vec3d.x * 16.0D;
     double f = dalek.getPosY() + (dalek.world.rand.nextInt(16) - 8) - vec3d.y * 16.0D;
     double g = dalek.getPosZ() + (dalek.world.rand.nextDouble() - 0.5D) * 8.0D - vec3d.z * 16.0D;
     return teleportTo(dalek, e, f, g);
  }

  private boolean teleportTo(LivingEntity dalek, double x, double y, double z) {
     BlockPos.Mutable mutable = new BlockPos.Mutable(x, y, z);

     while (mutable.getY() > 0 && !dalek.world.getBlockState((BlockPos)mutable).func_185904_a().func_76230_c()) {
       mutable.func_189536_c(Direction.DOWN);
    }

     BlockState blockState = dalek.world.getBlockState((BlockPos)mutable);
     boolean bl = blockState.func_185904_a().func_76230_c();
     boolean bl2 = blockState.func_204520_s().func_206884_a((ITag)FluidTags.field_206959_a);
     if (bl && !bl2) {
       boolean bl3 = dalek.func_213373_a(x, y, z, true);
       if (bl3 && !dalek.func_174814_R()) {
         dalek.world.playSound((PlayerEntity)null, dalek.field_70142_S, dalek.field_70137_T, dalek.field_70136_U, SoundEvents.field_187534_aX, dalek.func_184176_by(), 1.0F, 1.0F);
         dalek.func_184185_a(SoundEvents.field_187534_aX, 1.0F, 1.0F);
      }

       return bl3;
    }
     return false;
  }
}


