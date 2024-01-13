package com.swdteam.client.particle;
import net.minecraft.client.particle.DeceleratingParticle;
import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.IParticleData;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FlameThrowerParticle extends DeceleratingParticle {
  private FlameThrowerParticle(ClientWorld p_i232392_1_, double p_i232392_2_, double p_i232392_4_, double p_i232392_6_, double p_i232392_8_, double p_i232392_10_, double p_i232392_12_) {
     super(p_i232392_1_, p_i232392_2_, p_i232392_4_, p_i232392_6_, p_i232392_8_, p_i232392_10_, p_i232392_12_);
  }

  public IParticleRenderType func_217558_b() {
     return IParticleRenderType.field_217602_b;
  }

  public void func_187110_a(double p_187110_1_, double p_187110_3_, double p_187110_5_) {
     func_187108_a(func_187116_l().func_72317_d(p_187110_1_, p_187110_3_, p_187110_5_));
     func_187118_j();
  }

  public float func_217561_b(float p_217561_1_) {
     if (this.field_70546_d > 10) {
       return 0.0F;
    }
     float f = (this.field_70546_d + p_217561_1_) / this.field_70547_e;
     return this.field_70544_f * (1.0F - f * f * 0.5F);
  }

  public int func_189214_a(float p_189214_1_) {
     float f = (this.field_70546_d + p_189214_1_) / this.field_70547_e;
     f = MathHelper.func_76131_a(f, 0.0F, 1.0F);
     int i = super.func_189214_a(p_189214_1_);
     int j = i & 0xFF;
     int k = i >> 16 & 0xFF;
     j += (int)(f * 15.0F * 16.0F);
     if (j > 240) {
       j = 240;
    }

     return j | k << 16;
  }

  @OnlyIn(Dist.CLIENT)
  public static class Factory implements IParticleFactory<BasicParticleType> {
    private final IAnimatedSprite sprite;

    public Factory(IAnimatedSprite p_i50823_1_) {
       this.sprite = p_i50823_1_;
    }

    public Particle createParticle(BasicParticleType p_199234_1_, ClientWorld p_199234_2_, double p_199234_3_, double p_199234_5_, double p_199234_7_, double p_199234_9_, double p_199234_11_, double p_199234_13_) {
       FlameThrowerParticle flameparticle = new FlameThrowerParticle(p_199234_2_, p_199234_3_, p_199234_5_, p_199234_7_, p_199234_9_, p_199234_11_, p_199234_13_);
       flameparticle.func_217568_a(this.sprite);
       return (Particle)flameparticle;
    }
  }
}


