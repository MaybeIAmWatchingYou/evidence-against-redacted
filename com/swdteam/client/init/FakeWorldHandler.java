package com.swdteam.client.init;

import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DimensionType;

public class FakeWorldHandler {
  public static ClientWorld world;

  public static void init() {
     ClientWorld.ClientWorldInfo cwi = new ClientWorld.ClientWorldInfo(Difficulty.NORMAL, false, false);
     MutableRegistry mutableRegistry = (Minecraft.func_71410_x()).field_71441_e.getDynamicRegistries().func_243612_b(Registry.field_239698_ad_);

     world = new ClientWorld(Minecraft.func_71410_x().func_147114_u(), cwi, ClientWorld.field_234918_g_, (DimensionType)mutableRegistry.func_230516_a_(DimensionType.field_235999_c_), 4, Minecraft.func_71410_x()::func_213239_aq, (Minecraft.func_71410_x()).field_71438_f, false, 0L);
  }
}


