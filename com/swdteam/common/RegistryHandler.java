package com.swdteam.common;

import com.swdteam.common.init.DMBiomes;
import com.swdteam.common.init.DMBlockEntities;
import com.swdteam.common.init.DMBlocks;
import com.swdteam.common.init.DMCraftingTypes;
import com.swdteam.common.init.DMDalekRegistry;
import com.swdteam.common.init.DMEntities;
import com.swdteam.common.init.DMItems;
import com.swdteam.common.init.DMParticleTypes;
import com.swdteam.common.init.DMProjectiles;
import com.swdteam.common.init.DMSoundEvents;
import com.swdteam.common.init.DMWorldCarvers;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.particles.ParticleType;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;



public class RegistryHandler
{
   public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, "dalekmod");
   public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, "dalekmod");
   public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, "dalekmod");
   public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, "dalekmod");
   public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, "dalekmod");
   public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, "dalekmod");
   public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, "dalekmod");
   public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, "dalekmod");
   public static final DeferredRegister<WorldCarver<?>> WORLD_CARVERS = DeferredRegister.create(ForgeRegistries.WORLD_CARVERS, "dalekmod");
  
  public static DMItems dmItems;
  
  public static DMBlocks dmBlocks;
  
  public static DMParticleTypes dmParticles;
  
  public static DMBiomes dmBiomes;
  
  public static DMEntities dmEntities;
  
  public static DMSoundEvents dmSounds;
  
  public static DMBlockEntities dmTiles;
  
  public static DMCraftingTypes dmCrafting;
  
  public static DMWorldCarvers dmCarvers;

  
  public static void init() {
     DMProjectiles.init();
     DMDalekRegistry.init();
    
     dmItems = new DMItems();
     dmBlocks = new DMBlocks();
     dmParticles = new DMParticleTypes();
     dmBiomes = new DMBiomes();
     dmEntities = new DMEntities();
     dmSounds = new DMSoundEvents();
     dmTiles = new DMBlockEntities();
     dmCrafting = new DMCraftingTypes();
     dmCarvers = new DMWorldCarvers();
    
     ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
     BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
     PARTICLE_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
     BIOMES.register(FMLJavaModLoadingContext.get().getModEventBus());
     ENTITY_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
     SOUNDS.register(FMLJavaModLoadingContext.get().getModEventBus());
     TILE_ENTITY_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
     RECIPE_SERIALIZERS.register(FMLJavaModLoadingContext.get().getModEventBus());
     WORLD_CARVERS.register(FMLJavaModLoadingContext.get().getModEventBus());
  }
}


