package com.swdteam.common.init;

import com.swdteam.common.RegistryHandler;
import net.minecraft.particles.BasicParticleType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = "dalekmod", bus = Mod.EventBusSubscriber.Bus.MOD)
public class DMParticleTypes
{
   public static final RegistryObject<BasicParticleType> BLUE_DALEK_HOVER = register("blue_dalek_hover", true);
   public static final RegistryObject<BasicParticleType> RED_DALEK_HOVER = register("red_dalek_hover", true);
   public static final RegistryObject<BasicParticleType> POISON_GAS = register("poison_gas", true);
   public static final RegistryObject<BasicParticleType> FIRE_BALL = register("fire_ball", true);
   public static final RegistryObject<BasicParticleType> GOLD_DUST = register("gold_dust", true);

  private static RegistryObject<BasicParticleType> register(String string, boolean b) {
     return RegistryHandler.PARTICLE_TYPES.register(string, () -> new BasicParticleType(b));
  }
}


