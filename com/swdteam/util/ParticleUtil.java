package com.swdteam.util;

import com.swdteam.common.init.DMParticleTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;





@EventBusSubscriber(modid = "dalekmod", bus = Mod.EventBusSubscriber.Bus.MOD)
public class ParticleUtil
{
  @SubscribeEvent(priority = EventPriority.LOWEST)
  public static void registerParticles(ParticleFactoryRegisterEvent event) {
     (Minecraft.func_71410_x()).field_71452_i.func_215234_a((ParticleType)DMParticleTypes.BLUE_DALEK_HOVER.get(), net.minecraft.client.particle.BubbleParticle.Factory::new);
     (Minecraft.func_71410_x()).field_71452_i.func_215234_a((ParticleType)DMParticleTypes.RED_DALEK_HOVER.get(), net.minecraft.client.particle.BubbleParticle.Factory::new);
     (Minecraft.func_71410_x()).field_71452_i.func_215234_a((ParticleType)DMParticleTypes.POISON_GAS.get(), net.minecraft.client.particle.CloudParticle.Factory::new);
     (Minecraft.func_71410_x()).field_71452_i.func_215234_a((ParticleType)DMParticleTypes.FIRE_BALL.get(), com.swdteam.client.particle.FlameThrowerParticle.Factory::new);
     (Minecraft.func_71410_x()).field_71452_i.func_215234_a((ParticleType)DMParticleTypes.GOLD_DUST.get(), net.minecraft.client.particle.FireworkParticle.SparkFactory::new);
  }
}


