package com.swdteam.common.particles;

import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SpriteTexturedParticle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BlueHoverParticle extends SpriteTexturedParticle {
  protected BlueHoverParticle(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn) {
     super((ClientWorld)worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);

     float f = this.field_187136_p.nextFloat() * 1.0F;
     this.field_70552_h = f;
     this.field_70553_i = f;
     this.field_70551_j = f;

     func_187115_a(0.02F, 0.02F);
     this.field_70544_f *= this.field_187136_p.nextFloat() * 1.1F;
     this.field_187129_i *= 0.019999999552965164D;
     this.field_187130_j *= 0.019999999552965164D;
     this.field_187131_k *= 0.019999999552965164D;
     this.field_70547_e = (int)(20.0D / Math.random() * 1.0D);
  }



  public IParticleRenderType func_217558_b() {
     return IParticleRenderType.field_217602_b;
  }


  public void func_189213_a() {
     this.field_187123_c = this.field_187126_f;
     this.field_187124_d = this.field_187127_g;
     this.field_187125_e = this.field_187128_h;

     if (this.field_70546_d-- <= 0) {
       func_187112_i();
    } else {
       func_187110_a(this.field_187129_i, this.field_187130_j, this.field_187131_k);
       this.field_187129_i *= 1.0D;
       this.field_187130_j *= 1.0D;
       this.field_187131_k *= 1.0D;
    }
  }

  @OnlyIn(Dist.CLIENT)
  public static abstract class Factory implements IParticleFactory<BasicParticleType> {
    private final IAnimatedSprite spriteSet;

    public Factory(IAnimatedSprite sprite) {
       this.spriteSet = sprite;
    }

    public Particle createParticle(BasicParticleType typeIn, World worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
       BlueHoverParticle blueHoverParticle = new BlueHoverParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed);
       blueHoverParticle.func_70538_b(1.0F, 1.0F, 1.0F);
       blueHoverParticle.func_217568_a(this.spriteSet);
       return (Particle)blueHoverParticle;
    }
  }
}


