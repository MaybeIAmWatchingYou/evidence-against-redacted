package com.swdteam.common.init;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.swdteam.common.structure.AutonDungeonStructure;
import com.swdteam.common.structure.CybermanTombStructure;
import com.swdteam.common.structure.Meteorite1Structure;
import com.swdteam.common.structure.Meteorite2Structure;
import com.swdteam.common.structure.Meteorite3Structure;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;


public class DMStructures
{
   public static final DeferredRegister<Structure<?>> DEFERRED_REGISTRY_STRUCTURE = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, "dalekmod");

   public static final RegistryObject<Structure<NoFeatureConfig>> METEORITE_1 = registerStructure("meteorite_1", () -> new Meteorite1Structure(NoFeatureConfig.field_236558_a_));
   public static final RegistryObject<Structure<NoFeatureConfig>> METEORITE_2 = registerStructure("meteorite_2", () -> new Meteorite2Structure(NoFeatureConfig.field_236558_a_));
   public static final RegistryObject<Structure<NoFeatureConfig>> METEORITE_3 = registerStructure("meteorite_3", () -> new Meteorite3Structure(NoFeatureConfig.field_236558_a_));

   public static final RegistryObject<Structure<NoFeatureConfig>> AUTON_DUNGEON = registerStructure("auton_dungeon", () -> new AutonDungeonStructure(NoFeatureConfig.field_236558_a_));
   public static final RegistryObject<Structure<NoFeatureConfig>> CYBERMAN_TOMB = registerStructure("cyberman_tomb", () -> new CybermanTombStructure(NoFeatureConfig.field_236558_a_));

  private static <T extends Structure<?>> RegistryObject<T> registerStructure(String name, Supplier<T> structure) {
     return DEFERRED_REGISTRY_STRUCTURE.register(name, structure);
  }

  public static void setupStructures() {
     setupMapSpacingAndLand((Structure)METEORITE_1
         .get(), new StructureSeparationSettings(50, 10, 42069), false);
     setupMapSpacingAndLand((Structure)METEORITE_2
         .get(), new StructureSeparationSettings(40, 30, 72039), false);
     setupMapSpacingAndLand((Structure)METEORITE_3
         .get(), new StructureSeparationSettings(70, 20, 89249), false);
     setupMapSpacingAndLand((Structure)AUTON_DUNGEON
         .get(), new StructureSeparationSettings(30, 10, 51223), false);
     setupMapSpacingAndLand((Structure)CYBERMAN_TOMB
         .get(), new StructureSeparationSettings(30, 10, 13321), false);
  }





  public static <F extends Structure<?>> void setupMapSpacingAndLand(F structure, StructureSeparationSettings structureSeparationSettings, boolean transformSurroundingLand) {
     Structure.field_236365_a_.put(structure.getRegistryName().toString(), structure);

     if (transformSurroundingLand)
    {



       Structure.field_236384_t_ = (List)ImmutableList.builder().addAll(Structure.field_236384_t_).add(structure).build();
    }





     DimensionStructuresSettings.field_236191_b_ = ImmutableMap.builder().putAll((Map)DimensionStructuresSettings.field_236191_b_).put(structure, structureSeparationSettings).build();
  }
}


