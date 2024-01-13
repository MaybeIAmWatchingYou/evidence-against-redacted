package com.swdteam.common.structure;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Random;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.jigsaw.JigsawManager;
import net.minecraft.world.gen.feature.jigsaw.JigsawPattern;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.structure.VillageConfig;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class AutonDungeonStructure extends Structure<NoFeatureConfig> {
  public AutonDungeonStructure(Codec<NoFeatureConfig> codec) {
     super(codec);
  }


  public Structure.IStartFactory<NoFeatureConfig> func_214557_a() {
     return Start::new;
  }


  public GenerationStage.Decoration func_236396_f_() {
     return GenerationStage.Decoration.UNDERGROUND_STRUCTURES;
  }

   private static final List<MobSpawnInfo.Spawners> STRUCTURE_MONSTERS = (List<MobSpawnInfo.Spawners>)ImmutableList.of();



  public List<MobSpawnInfo.Spawners> getDefaultSpawnList() {
     return STRUCTURE_MONSTERS;
  }

   private static final List<MobSpawnInfo.Spawners> STRUCTURE_CREATURES = (List<MobSpawnInfo.Spawners>)ImmutableList.of();



  public List<MobSpawnInfo.Spawners> getDefaultCreatureSpawnList() {
     return STRUCTURE_CREATURES;
  }

  public static class Start extends StructureStart<NoFeatureConfig> {
    public Start(Structure<NoFeatureConfig> structureIn, int chunkX, int chunkZ, MutableBoundingBox mutableBoundingBox, int referenceIn, long seedIn) {
       super(structureIn, chunkX, chunkZ, mutableBoundingBox, referenceIn, seedIn);
    }



    public void generatePieces(DynamicRegistries dynamicRegistryManager, ChunkGenerator chunkGenerator, TemplateManager templateManagerIn, int chunkX, int chunkZ, Biome biomeIn, NoFeatureConfig config) {
       int x = (chunkX << 4) + 7;
       int z = (chunkZ << 4) + 7;

       BlockPos blockpos = new BlockPos(x, 0, z);

       JigsawManager.func_242837_a(dynamicRegistryManager, new VillageConfig(() -> (JigsawPattern)dynamicRegistryManager.func_243612_b(Registry.field_243555_ax).getOrDefault(new ResourceLocation("dalekmod", "auton_dungeon/start_pool")), 10), net.minecraft.world.gen.feature.structure.AbstractVillagePiece::new, chunkGenerator, templateManagerIn, blockpos, this.field_75075_a, (Random)this.field_214631_d, false, true);




       this.field_75075_a.forEach(piece -> piece.func_181138_a(0, -40, 0));
       this.field_75075_a.forEach(piece -> (piece.func_74874_b()).field_78895_b--);
       func_202500_a();
    }
  }
}


