package com.swdteam.common.init;

import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;




public class DMDimensions
{
   public static RegistryKey<World> TARDIS = RegistryKey.func_240903_a_(Registry.field_239699_ae_, new ResourceLocation("dalekmod", "tardis"));


   public static RegistryKey<World> SKARO = RegistryKey.func_240903_a_(Registry.field_239699_ae_, new ResourceLocation("dalekmod", "skaro"));


   public static RegistryKey<World> CAVE_GAME = RegistryKey.func_240903_a_(Registry.field_239699_ae_, new ResourceLocation("dalekmod", "cave_game"));
   public static RegistryKey<World> CLASSIC = RegistryKey.func_240903_a_(Registry.field_239699_ae_, new ResourceLocation("dalekmod", "minecraft_classic"));
   public static RegistryKey<World> INFDEV = RegistryKey.func_240903_a_(Registry.field_239699_ae_, new ResourceLocation("dalekmod", "minecraft_infdev"));
}


