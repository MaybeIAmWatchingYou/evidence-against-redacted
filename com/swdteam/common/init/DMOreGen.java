package com.swdteam.common.init;

import java.util.ArrayList;
import net.minecraft.block.Block;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;


@EventBusSubscriber
public class DMOreGen
{
   private static final ArrayList<ConfiguredFeature<?, ?>> overworldOres = new ArrayList<>();
   private static final ArrayList<ConfiguredFeature<?, ?>> netherOres = new ArrayList<>();
   private static final ArrayList<ConfiguredFeature<?, ?>> endOres = new ArrayList<>();






  public static void registerOres() {
     overworldOres.add(register("silicate_ore", (ConfiguredFeature<?, ?>)((ConfiguredFeature)((ConfiguredFeature)Feature.field_202290_aj.func_225566_b_((IFeatureConfig)new OreFeatureConfig(OreFeatureConfig.FillerBlockType.field_241882_a, ((Block)DMBlocks.SILICATE_ORE
               .get()).getDefaultState(), 4))
           .func_242733_d(20)).func_242728_a())
           .func_242731_b(8)));
     overworldOres.add(register("crystaline_ore", (ConfiguredFeature<?, ?>)((ConfiguredFeature)((ConfiguredFeature)Feature.field_202290_aj.func_225566_b_((IFeatureConfig)new OreFeatureConfig(OreFeatureConfig.FillerBlockType.field_241882_a, ((Block)DMBlocks.CRYSTALINE_ORE
               .get()).getDefaultState(), 8))
           .func_242733_d(10)).func_242728_a())
           .func_242731_b(5)));
  }

  @SubscribeEvent(priority = EventPriority.HIGHEST)
  public static void gen(BiomeLoadingEvent event) {
     BiomeGenerationSettingsBuilder generation = event.getGeneration();
     if (event.getCategory().equals(Biome.Category.NETHER))
       for (ConfiguredFeature<?, ?> ore : netherOres) {
         if (ore != null) generation.func_242513_a(GenerationStage.Decoration.UNDERGROUND_ORES, ore);

      }
     if (event.getCategory().equals(Biome.Category.THEEND))
       for (ConfiguredFeature<?, ?> ore : endOres) {
         if (ore != null) generation.func_242513_a(GenerationStage.Decoration.UNDERGROUND_ORES, ore);

      }
     for (ConfiguredFeature<?, ?> ore : overworldOres) {
       if (ore != null) generation.func_242513_a(GenerationStage.Decoration.UNDERGROUND_ORES, ore);
    }
  }

  private static <FC extends IFeatureConfig> ConfiguredFeature<FC, ?> register(String name, ConfiguredFeature<FC, ?> configuredFeature) {
     return (ConfiguredFeature<FC, ?>)Registry.func_218325_a(WorldGenRegistries.field_243653_e, "dalekmod:" + name, configuredFeature);
  }
}


