package com.swdteam.common.init;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.registries.ForgeRegistries;





public class DMSpawnerRegistry
{
   public static Map<ResourceLocation, SpawnInfo> spawns = new HashMap<>();

  
  public static void init() {
     EntitySpawnPlacementRegistry.func_209343_a((EntityType)DMEntities.AUTON_ENTITY.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MonsterEntity::func_223324_d);
     EntitySpawnPlacementRegistry.func_209343_a((EntityType)DMEntities.CLASSIC_SKELETON_ENTITY.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MonsterEntity::func_223324_d);
     EntitySpawnPlacementRegistry.func_209343_a((EntityType)DMEntities.CLASSIC_SPIDER_ENTITY.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MonsterEntity::func_223324_d);
     EntitySpawnPlacementRegistry.func_209343_a((EntityType)DMEntities.CYBERMAN_ENTITY.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MonsterEntity::func_223324_d);
     EntitySpawnPlacementRegistry.func_209343_a((EntityType)DMEntities.DALEK_ENTITY.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MonsterEntity::func_223324_d);
     EntitySpawnPlacementRegistry.func_209343_a((EntityType)DMEntities.CYBERDRONE.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MobEntity::func_223315_a);
     EntitySpawnPlacementRegistry.func_209343_a((EntityType)DMEntities.OOD_ENTITY.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MonsterEntity::func_223324_d);



    
     addSpawn(Biomes.field_150588_X, (EntityType)DMEntities.AUTON_ENTITY.get(), 5, 1, 3, EntityClassification.MONSTER);
     addSpawn(Biomes.field_150587_Y, (EntityType)DMEntities.AUTON_ENTITY.get(), 4, 1, 4, EntityClassification.MONSTER);
     addSpawn(Biomes.field_185436_ah, (EntityType)DMEntities.AUTON_ENTITY.get(), 2, 1, 2, EntityClassification.MONSTER);
    
     addSpawn(Biomes.field_185443_S, (EntityType)DMEntities.DALEK_ENTITY.get(), 3, 1, 1, EntityClassification.MONSTER);
     addSpawn(Biomes.field_235251_aB_, (EntityType)DMEntities.DALEK_ENTITY.get(), 9, 1, 1, EntityClassification.MONSTER);
     addSpawn(Biomes.field_235254_j_, (EntityType)DMEntities.DALEK_ENTITY.get(), 5, 1, 3, EntityClassification.MONSTER);
     addSpawn(Biomes.field_235252_ay_, (EntityType)DMEntities.DALEK_ENTITY.get(), 6, 1, 2, EntityClassification.MONSTER);
     addSpawn(Biomes.field_150583_P, (EntityType)DMEntities.DALEK_ENTITY.get(), 3, 1, 2, EntityClassification.MONSTER);
     addSpawn(Biomes.field_76772_c, (EntityType)DMEntities.DALEK_ENTITY.get(), 4, 1, 3, EntityClassification.MONSTER);
     addSpawn(Biomes.field_185441_Q, (EntityType)DMEntities.DALEK_ENTITY.get(), 3, 1, 4, EntityClassification.MONSTER);
     addSpawn(Biomes.field_76770_e, (EntityType)DMEntities.DALEK_ENTITY.get(), 2, 1, 3, EntityClassification.MONSTER);
     addSpawn(Biomes.field_76775_o, (EntityType)DMEntities.DALEK_ENTITY.get(), 2, 1, 2, EntityClassification.MONSTER);
     addSpawn(Biomes.field_150590_f, (EntityType)DMEntities.DALEK_ENTITY.get(), 3, 1, 2, EntityClassification.MONSTER);
     addSpawn(Biomes.field_76767_f, (EntityType)DMEntities.DALEK_ENTITY.get(), 3, 1, 2, EntityClassification.MONSTER);
     addSpawn(Biomes.field_185444_T, (EntityType)DMEntities.DALEK_ENTITY.get(), 2, 1, 3, EntityClassification.MONSTER);
     addSpawn(Biomes.field_76787_r, (EntityType)DMEntities.DALEK_ENTITY.get(), 3, 1, 4, EntityClassification.MONSTER);
     addSpawn(Biomes.field_76781_i, (EntityType)DMEntities.DALEK_ENTITY.get(), 4, 1, 2, EntityClassification.MONSTER);
     addSpawn(Biomes.field_150578_U, (EntityType)DMEntities.DALEK_ENTITY.get(), 2, 1, 1, EntityClassification.MONSTER);
     addSpawn(Biomes.field_150581_V, (EntityType)DMEntities.DALEK_ENTITY.get(), 4, 1, 2, EntityClassification.MONSTER);
     addSpawn(Biomes.field_201939_S, (EntityType)DMEntities.DALEK_ENTITY.get(), 4, 1, 2, EntityClassification.MONSTER);
     addSpawn(Biomes.field_201938_R, (EntityType)DMEntities.DALEK_ENTITY.get(), 4, 1, 2, EntityClassification.MONSTER);
     addSpawn(Biomes.field_201937_Q, (EntityType)DMEntities.DALEK_ENTITY.get(), 4, 1, 2, EntityClassification.MONSTER);
     addSpawn(Biomes.field_201936_P, (EntityType)DMEntities.DALEK_ENTITY.get(), 4, 1, 2, EntityClassification.MONSTER);
     addSpawn(Biomes.field_76779_k, (EntityType)DMEntities.DALEK_ENTITY.get(), 4, 1, 2, EntityClassification.MONSTER);
     addSpawn(Biomes.field_150589_Z, (EntityType)DMEntities.DALEK_ENTITY.get(), 4, 1, 1, EntityClassification.MONSTER);
     addSpawn(Biomes.field_76769_d, (EntityType)DMEntities.DALEK_ENTITY.get(), 4, 1, 1, EntityClassification.MONSTER);
    
     addSpawn(Biomes.field_76772_c, (EntityType)DMEntities.CYBERDRONE.get(), 4, 1, 1, EntityClassification.MONSTER);
     addSpawn(Biomes.field_185441_Q, (EntityType)DMEntities.CYBERMAN_ENTITY.get(), 4, 1, 1, EntityClassification.MONSTER);
     addSpawn(Biomes.field_150582_Q, (EntityType)DMEntities.CYBERMAN_ENTITY.get(), 4, 1, 1, EntityClassification.MONSTER);
     addSpawn(Biomes.field_150582_Q, (EntityType)DMEntities.OOD_ENTITY.get(), 4, 1, 1, EntityClassification.MONSTER);
     addSpawn(Biomes.field_150583_P, (EntityType)DMEntities.OOD_ENTITY.get(), 4, 1, 1, EntityClassification.MONSTER);
     addSpawn(Biomes.field_76767_f, (EntityType)DMEntities.OOD_ENTITY.get(), 4, 1, 1, EntityClassification.MONSTER);




    
     addSpawn(DMBiomes.IRRADIATED_JUNGLE, (EntityType)DMEntities.DALEK_ENTITY.get(), 1, 1, 4, EntityClassification.MONSTER);
     addSpawn(DMBiomes.CLASSIC, (EntityType)DMEntities.DALEK_ENTITY.get(), 2, 1, 2, EntityClassification.CREATURE);
     addSpawn(DMBiomes.CLASSIC, (EntityType)DMEntities.CLASSIC_SKELETON_ENTITY.get(), 5, 1, 2, EntityClassification.CREATURE);
     addSpawn(DMBiomes.CLASSIC, (EntityType)DMEntities.CLASSIC_SPIDER_ENTITY.get(), 5, 1, 2, EntityClassification.CREATURE);
  }

  
  public static void initDalekSpawns() {
     DMDalekRegistry.addSpawn(DMDalekRegistry.CHOCOLATE_DALEK, (RegistryKey<Biome>[])new RegistryKey[] { Biomes.field_185443_S });
     DMDalekRegistry.addSpawn(DMDalekRegistry.MOLTEN_DALEK, (RegistryKey<Biome>[])new RegistryKey[] { Biomes.field_235251_aB_ });
     DMDalekRegistry.addSpawn(DMDalekRegistry.NETHER_DALEK, (RegistryKey<Biome>[])new RegistryKey[] { Biomes.field_235254_j_, Biomes.field_235252_ay_ });
     DMDalekRegistry.addSpawn(DMDalekRegistry.NETHER_SPECIAL_WEAPONS_DALEK, (RegistryKey<Biome>[])new RegistryKey[] { Biomes.field_235254_j_, Biomes.field_235252_ay_ });
     for (int i = 0; i < 3; i++) {
      
       DMDalekRegistry.addSpawn(DMDalekRegistry.TIME_WAR_DALEK, (RegistryKey<Biome>[])new RegistryKey[] { Biomes.field_76772_c, Biomes.field_76767_f, Biomes.field_185441_Q, Biomes.field_150583_P, Biomes.field_76770_e, Biomes.field_76775_o, Biomes.field_185431_ac });
       DMDalekRegistry.addSpawn(DMDalekRegistry.INVASION_DALEK, (RegistryKey<Biome>[])new RegistryKey[] { Biomes.field_76772_c, Biomes.field_185441_Q, Biomes.field_76767_f, Biomes.field_185444_T, Biomes.field_76787_r });
       DMDalekRegistry.addSpawn(DMDalekRegistry.ADVANCED_INVASION_DALEK, (RegistryKey<Biome>[])new RegistryKey[] { Biomes.field_185441_Q, Biomes.field_76781_i, Biomes.field_150578_U, Biomes.field_185444_T });
       DMDalekRegistry.addSpawn(DMDalekRegistry.IMPERIAL_DALEK, (RegistryKey<Biome>[])new RegistryKey[] { Biomes.field_150589_Z, Biomes.field_76769_d, Biomes.field_76786_s, Biomes.field_150608_ab });
    } 
     DMDalekRegistry.addSpawn(DMDalekRegistry.SPECIAL_WEAPONS_DALEK, (RegistryKey<Biome>[])new RegistryKey[] { Biomes.field_150583_P, Biomes.field_76772_c, Biomes.field_76767_f, Biomes.field_76769_d, Biomes.field_185441_Q });
     DMDalekRegistry.addSpawn(DMDalekRegistry.SUICIDE_BOMBER_DALEK, (RegistryKey<Biome>[])new RegistryKey[] { Biomes.field_150578_U });

    
     DMDalekRegistry.addSpawn(DMDalekRegistry.SKARO_DALEK, (RegistryKey<Biome>[])new RegistryKey[] { DMBiomes.IRRADIATED_JUNGLE });
     DMDalekRegistry.addSpawn(DMDalekRegistry.ADVANCED_SKARO_DALEK, (RegistryKey<Biome>[])new RegistryKey[] { DMBiomes.IRRADIATED_JUNGLE });
     DMDalekRegistry.addSpawn(DMDalekRegistry.CLASSIC_DALEK, (RegistryKey<Biome>[])new RegistryKey[] { DMBiomes.CLASSIC });
    
     DMDalekRegistry.addSpawn(DMDalekRegistry.ENDER_DALEK, (RegistryKey<Biome>[])new RegistryKey[] { Biomes.field_76779_k, Biomes.field_201939_S, Biomes.field_201938_R, Biomes.field_201937_Q, Biomes.field_201936_P });
     DMDalekRegistry.addSpawn(DMDalekRegistry.ENDER_SPECIAL_WEAPONS_DALEK, (RegistryKey<Biome>[])new RegistryKey[] { Biomes.field_76779_k, Biomes.field_201939_S, Biomes.field_201938_R, Biomes.field_201937_Q, Biomes.field_201936_P });
  }
  
  public static boolean canSpawnAmbient(EntityType<?> type, IWorld worldIn, SpawnReason reason, BlockPos pos, Random randomIn) {
     return true;
  }

  
  private static void addSpawn(RegistryKey<Biome> biome, EntityType<?> type, int weight, int min, int max, EntityClassification entityType) {
     if (!spawns.containsKey(biome.getRegistryLocation())) {
       spawns.put(biome.getRegistryLocation(), new SpawnInfo());
    }
     SpawnInfo info = spawns.get(biome.getRegistryLocation());
     info.addSpawn(type, weight, min, max, entityType);
  }

  
  private static void addSpawnToAllBiomes(EntityType<?> type, int weight, int min, int max, EntityClassification entityType) {
     for (Map.Entry<RegistryKey<Biome>, Biome> rl : (Iterable<Map.Entry<RegistryKey<Biome>, Biome>>)ForgeRegistries.BIOMES.getEntries()) {
       addSpawn(rl.getKey(), type, weight, min, max, entityType);
    }
  }

  
  private static void removeSpawn(EntityType<?> type, RegistryKey<Biome>... biome) {
     for (int i = 0; i < biome.length; i++) {
       RegistryKey<Biome> bi = biome[i];
      
       if (spawns.containsKey(bi.getRegistryLocation())) {
         SpawnInfo info = spawns.get(bi.getRegistryLocation());
         info.removeSpawn(type);
      } 
    } 
  }
  
  public static class SpawnInfo
  {
     private List<Spawn> spawners = new ArrayList<>();



    
    public void addSpawn(EntityType<?> type, int weight, int min, int max, EntityClassification entType) {
       this.spawners.add(new Spawn(new MobSpawnInfo.Spawners(type, weight, min, max), entType));
    }
    
    public void removeSpawn(EntityType<?> type) {
       for (int i = 0; i < this.spawners.size(); i++) {
         Spawn spawn = this.spawners.get(i);
        
         if (spawn.spawner.field_242588_c == type) {
           this.spawners.remove(i);
          break;
        } 
      } 
    }
    
    public List<Spawn> getSpawners() {
       return this.spawners;
    }
    
    public static class Spawn {
      public MobSpawnInfo.Spawners spawner;
      
      public Spawn(MobSpawnInfo.Spawners spawner, EntityClassification type) {
         this.spawner = spawner;
         this.entityType = type;
      }
      
      public EntityClassification entityType;
    }
  }
}


