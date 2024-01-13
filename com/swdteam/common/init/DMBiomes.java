package com.swdteam.common.init;

import com.swdteam.common.RegistryHandler;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeMaker;
import net.minecraftforge.fml.RegistryObject;









public class DMBiomes
{
  @Deprecated
   public static RegistryKey<Biome> DEPRECTATED_1 = makeKey("mc_classic");
  @Deprecated
   public static final RegistryObject<Biome> DEPRECTATED_2 = RegistryHandler.BIOMES.register("mc_classic", () -> BiomeMaker.func_244252_r());


   public static RegistryKey<Biome> CLASSIC = makeKey("minecraft_classic");
   public static RegistryKey<Biome> TARDIS = makeKey("tardis");


   public static RegistryKey<Biome> IRRADIATED_JUNGLE = makeKey("irradiated_jungle");


  private static RegistryKey<Biome> makeKey(String name) {
     return RegistryKey.func_240903_a_(Registry.BIOME_KEY, new ResourceLocation("dalekmod", name));
  }
}


