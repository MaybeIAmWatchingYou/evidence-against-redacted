package com.swdteam.common.structure;

import com.swdteam.common.init.DMStructures;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.structure.Structure;

public class DMConfiguredStructures
{
   public static StructureFeature<?, ?> CONFIGURED_METEORITE_1 = ((Structure)DMStructures.METEORITE_1.get()).func_236391_a_((IFeatureConfig)IFeatureConfig.field_202429_e);
   public static StructureFeature<?, ?> CONFIGURED_METEORITE_2 = ((Structure)DMStructures.METEORITE_2.get()).func_236391_a_((IFeatureConfig)IFeatureConfig.field_202429_e);
   public static StructureFeature<?, ?> CONFIGURED_METEORITE_3 = ((Structure)DMStructures.METEORITE_3.get()).func_236391_a_((IFeatureConfig)IFeatureConfig.field_202429_e);
   public static StructureFeature<?, ?> CONFIGURED_AUTON_DUNGEON = ((Structure)DMStructures.AUTON_DUNGEON.get()).func_236391_a_((IFeatureConfig)IFeatureConfig.field_202429_e);
   public static StructureFeature<?, ?> CONFIGURED_CYBERMAN_TOMB = ((Structure)DMStructures.CYBERMAN_TOMB.get()).func_236391_a_((IFeatureConfig)IFeatureConfig.field_202429_e);

  public static void registerConfiguredStructures() {
     Registry<StructureFeature<?, ?>> registry = WorldGenRegistries.field_243654_f;
     Registry.func_218322_a(registry, new ResourceLocation("dalekmod", "configured_meteorite_1"), CONFIGURED_METEORITE_1);
     Registry.func_218322_a(registry, new ResourceLocation("dalekmod", "configured_meteorite_2"), CONFIGURED_METEORITE_2);
     Registry.func_218322_a(registry, new ResourceLocation("dalekmod", "configured_meteorite_3"), CONFIGURED_METEORITE_3);
     Registry.func_218322_a(registry, new ResourceLocation("dalekmod", "configured_auton_dungeon"), CONFIGURED_AUTON_DUNGEON);
     Registry.func_218322_a(registry, new ResourceLocation("dalekmod", "configured_cyberman_tomb"), CONFIGURED_CYBERMAN_TOMB);
  }
}


