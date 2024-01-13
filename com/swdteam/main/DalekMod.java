package com.swdteam.main;
import com.google.common.collect.UnmodifiableIterator;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.brigadier.CommandDispatcher;
import com.swdteam.common.MCClassicLakesFeature;
import com.swdteam.common.RegistryHandler;
import com.swdteam.common.block.IRust;
import com.swdteam.common.command.CommandDalekModBase;
import com.swdteam.common.command.CommandInteriorDoorPos;
import com.swdteam.common.command.CommandTardisOwnership;
import com.swdteam.common.command.dalekmod.argument.ChameleonArgument;
import com.swdteam.common.command.dalekmod.argument.FacingArgument;
import com.swdteam.common.command.dalekmod.argument.TardisArgument;
import com.swdteam.common.crafting.EngineeringTableReloader;
import com.swdteam.common.crafting.RoundelRecipeReloader;
import com.swdteam.common.entity.AutonEntity;
import com.swdteam.common.entity.CyberdroneEntity;
import com.swdteam.common.entity.CybermanEntity;
import com.swdteam.common.entity.CybervillagerEntity;
import com.swdteam.common.entity.KerblamManEntity;
import com.swdteam.common.entity.OodEntity;
import com.swdteam.common.entity.WeepingAngelEntity;
import com.swdteam.common.entity.classic.ClassicSkeletonEntity;
import com.swdteam.common.entity.classic.ClassicSpiderEntity;
import com.swdteam.common.entity.dalek.DalekEntity;
import com.swdteam.common.event.EventHandlerGeneral;
import com.swdteam.common.init.DMBlocks;
import com.swdteam.common.init.DMContainer;
import com.swdteam.common.init.DMCriteriaTriggers;
import com.swdteam.common.init.DMDalekRegistry;
import com.swdteam.common.init.DMDimensions;
import com.swdteam.common.init.DMEntities;
import com.swdteam.common.init.DMFlightMode;
import com.swdteam.common.init.DMGameRules;
import com.swdteam.common.init.DMItems;
import com.swdteam.common.init.DMOreGen;
import com.swdteam.common.init.DMSonicRegistry;
import com.swdteam.common.init.DMSpawnerRegistry;
import com.swdteam.common.init.DMStructures;
import com.swdteam.common.init.DMTardis;
import com.swdteam.common.kerblam.KerblamItemReloadListener;
import com.swdteam.common.structure.DMConfiguredStructures;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.common.tardis.TardisSaveHandler;
import com.swdteam.common.tardis.data.TardisFlightPool;
import com.swdteam.common.tardis.datapack.InteriorReloadListener;
import com.swdteam.common.tardis.datapack.TardisInteriorReloadListener;
import com.swdteam.common.tardis.datapack.TardisLocationListener;
import com.swdteam.common.tardis.datapack.TardisReloadListener;
import com.swdteam.main.proxy.ServerProxy;
import com.swdteam.network.NetworkHandler;
import com.swdteam.util.SWDMathUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.command.CommandSource;
import net.minecraft.command.arguments.ArgumentSerializer;
import net.minecraft.command.arguments.ArgumentTypes;
import net.minecraft.command.arguments.IArgumentSerializer;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.item.Item;
import net.minecraft.resources.IFutureReloadListener;
import net.minecraft.scoreboard.ScoreCriteria;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.World;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.DimensionSettings;
import net.minecraft.world.gen.feature.BlockStateFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.lifecycle.ParallelDispatchEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistryEntry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("dalekmod")
public class DalekMod {
   public static Random RANDOM = new Random();
  public static DalekMod INSTANCE;
   public static final Logger LOGGER = LogManager.getLogger();
   public static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();
   public static final ServerProxy PROXY = (ServerProxy)DistExecutor.runForDist(() -> com.swdteam.main.proxy.ClientProxy::new, () -> ServerProxy::new);
  
  public static final String MODID = "dalekmod";
  public static SWDMathUtils swdMathUtils;
   public static String VERSION = "68.1.0";
  static ConfiguredFeature<?, ?> LAKE_CF;
  static ConfiguredFeature<?, ?> SAND_PATCH_CF;
  public static Feature<BlockStateFeatureConfig> LAKE;
  public static Feature<BlockStateFeatureConfig> SAND_PATCH;
  
  public DalekMod() {
     INSTANCE = this;
     IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
     swdMathUtils = new SWDMathUtils();
    
     RegistryHandler.init();
     DMStructures.DEFERRED_REGISTRY_STRUCTURE.register(modEventBus);
     LAKE = (Feature<BlockStateFeatureConfig>)new MCClassicLakesFeature(BlockStateFeatureConfig.field_236455_a_);
     LAKE.setRegistryName("dalekmod", "classic_lake");
     ForgeRegistries.FEATURES.register((IForgeRegistryEntry)LAKE);
     SAND_PATCH = (Feature<BlockStateFeatureConfig>)new MCClassicLakesFeature(BlockStateFeatureConfig.field_236455_a_);
     SAND_PATCH.setRegistryName("dalekmod", "classic_sand_patch");
     ForgeRegistries.FEATURES.register((IForgeRegistryEntry)SAND_PATCH);



    
     LAKE_CF = LAKE.func_225566_b_((IFeatureConfig)new BlockStateFeatureConfig(Blocks.WATER.getDefaultState())).func_227228_a_(Placement.field_215006_E.func_227446_a_((IPlacementConfig)new ChanceConfig(4)));
     SAND_PATCH_CF = SAND_PATCH.func_225566_b_((IFeatureConfig)new BlockStateFeatureConfig(Blocks.field_150354_m.getDefaultState())).func_227228_a_(Placement.field_215006_E.func_227446_a_((IPlacementConfig)new ChanceConfig(4)));
    
     modEventBus.addListener(this::setup);
     modEventBus.addListener(this::enqueueIMC);
     modEventBus.addListener(this::processIMC);
     modEventBus.addListener(this::commonSetup);
     modEventBus.addListener(this::doClientStuff);
     modEventBus.addListener(this::runLater);
    
     DMContainer.CONTAINER_TYPE.register(modEventBus);
    
     ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, DMConfig.serverSpec, "dalekmod-server.toml");
     ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, DMConfig.commonSpec, "dalekmod-common.toml");
     ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, DMConfig.clientSpec, "dalekmod-client.toml");
    
     bothSideSetup(modEventBus);
     MinecraftForge.EVENT_BUS.register(this);
     MinecraftForge.EVENT_BUS.register(EventHandlerGeneral.class);
    
     IEventBus vengaBus = MinecraftForge.EVENT_BUS;
     vengaBus.addListener(EventPriority.HIGH, this::biomeModification);
     vengaBus.addListener(EventPriority.NORMAL, this::addDimensionalSpacing);
  }
  
  public void biomeModification(BiomeLoadingEvent event) {
     if (DMSpawnerRegistry.spawns.containsKey(event.getName())) {
       DMSpawnerRegistry.SpawnInfo info = (DMSpawnerRegistry.SpawnInfo)DMSpawnerRegistry.spawns.get(event.getName());
       for (int i = 0; i < info.getSpawners().size(); i++) {
         DMSpawnerRegistry.SpawnInfo.Spawn spawn = info.getSpawners().get(i);
         List<MobSpawnInfo.Spawners> spawns = event.getSpawns().getSpawner(spawn.entityType);
         spawns.add(spawn.spawner);
      } 
    } 
     event.getGeneration().getStructures().add(() -> DMConfiguredStructures.CONFIGURED_METEORITE_1);
     event.getGeneration().getStructures().add(() -> DMConfiguredStructures.CONFIGURED_METEORITE_2);
     event.getGeneration().getStructures().add(() -> DMConfiguredStructures.CONFIGURED_METEORITE_3);
     event.getGeneration().getStructures().add(() -> DMConfiguredStructures.CONFIGURED_AUTON_DUNGEON);
     event.getGeneration().getStructures().add(() -> DMConfiguredStructures.CONFIGURED_CYBERMAN_TOMB);
  }

  
  private void bothSideSetup(IEventBus modEventBus) {}

  
  private void setup(FMLCommonSetupEvent event) {
     NetworkHandler.register();
     IRust.addRustedVariants();
     event.enqueueWork(() -> {
          DMCriteriaTriggers.init();
          DMSpawnerRegistry.init();
          DMGameRules.init();
          DMStructures.setupStructures();
          DMConfiguredStructures.registerConfiguredStructures();
          ((FlowerPotBlock)Blocks.field_150457_bL).addPlant(DMBlocks.CLASSIC_RED_FLOWER.getId(), (Supplier)DMBlocks.POTTED_CLASSIC_RED_FLOWER);
          ((FlowerPotBlock)Blocks.field_150457_bL).addPlant(DMBlocks.CLASSIC_YELLOW_FLOWER.getId(), (Supplier)DMBlocks.POTTED_CLASSIC_YELLOW_FLOWER);
          GlobalEntityTypeAttributes.put((EntityType)DMEntities.AUTON_ENTITY.get(), AutonEntity.setCustomAttributes().func_233813_a_());
          GlobalEntityTypeAttributes.put((EntityType)DMEntities.CYBERMAN_ENTITY.get(), CybermanEntity.setCustomAttributes().func_233813_a_());
          GlobalEntityTypeAttributes.put((EntityType)DMEntities.DALEK_ENTITY.get(), DalekEntity.setCustomAttributes().func_233813_a_());
          GlobalEntityTypeAttributes.put((EntityType)DMEntities.CLASSIC_SPIDER_ENTITY.get(), ClassicSpiderEntity.setCustomAttributes().func_233813_a_());
          GlobalEntityTypeAttributes.put((EntityType)DMEntities.CLASSIC_SKELETON_ENTITY.get(), ClassicSkeletonEntity.setCustomAttributes().func_233813_a_());
          GlobalEntityTypeAttributes.put((EntityType)DMEntities.WEEPING_ANGEL_ENTITY.get(), WeepingAngelEntity.setCustomAttributes().func_233813_a_());
          GlobalEntityTypeAttributes.put((EntityType)DMEntities.KERBLAM_MAN.get(), KerblamManEntity.setCustomAttributes().func_233813_a_());
          GlobalEntityTypeAttributes.put((EntityType)DMEntities.CYBERMANVILLAGER_ENTITY.get(), CybervillagerEntity.setCustomAttributes().func_233813_a_());
          GlobalEntityTypeAttributes.put((EntityType)DMEntities.CYBERMAT_ENTITY.get(), CybervillagerEntity.setCustomAttributes().func_233813_a_());
          GlobalEntityTypeAttributes.put((EntityType)DMEntities.CYBERDRONE.get(), CyberdroneEntity.setCustomAttributes().func_233813_a_());
          GlobalEntityTypeAttributes.put((EntityType)DMEntities.OOD_ENTITY.get(), OodEntity.setCustomAttributes().func_233813_a_());
        });
  }




  
  private void enqueueIMC(InterModEnqueueEvent event) {}



  
  private void processIMC(InterModProcessEvent event) {
     LOGGER.info("Got IMC {}", event.getIMCStream().map(m -> m.getMessageSupplier().get()).collect(Collectors.toList()));
  }
  
  private void runLater(ParallelDispatchEvent event) {
     event.enqueueWork(() -> {
          DMOreGen.registerOres();
          DMSonicRegistry.init();
        });
  }
  
  private void commonSetup(FMLCommonSetupEvent event) {
     ArgumentTypes.func_218136_a("tardis", TardisArgument.class, (IArgumentSerializer)new ArgumentSerializer(TardisArgument::tardis));
     ArgumentTypes.func_218136_a("facing", FacingArgument.class, (IArgumentSerializer)new ArgumentSerializer(FacingArgument::facing));
     ArgumentTypes.func_218136_a("chameleon", ChameleonArgument.class, (IArgumentSerializer)new ArgumentSerializer(ChameleonArgument::skin));
     PROXY.doServerStuff(event);
  }
  
  private void doClientStuff(FMLClientSetupEvent event) {
     PROXY.doClientStuff(event);
  }


  
  @SubscribeEvent
  public void idEvent(RegistryEvent.MissingMappings<Item> event) {
     for (UnmodifiableIterator<RegistryEvent.MissingMappings.Mapping<Item>> unmodifiableIterator = event.getMappings("dalekmod").iterator(); unmodifiableIterator.hasNext(); ) { RegistryEvent.MissingMappings.Mapping<Item> mapping = unmodifiableIterator.next();
       ResourceLocation regName = mapping.key;
       if (regName != null) {
         String path = regName.getPath();
         if (path.equalsIgnoreCase("fez")) {
           mapping.remap(DMItems.RED_FEZ.get());
        }
      }  }
  
  }
  
  @SubscribeEvent
  public void onServerStarting(FMLServerStartingEvent event) {
     DMTardis.clearOwnerLookup();
     DMTardis.getLoadedTardises().clear();
     TardisSaveHandler.loadTardisPool(event.getServer());
     TardisFlightPool.load(event.getServer());
     DMFlightMode.load(event.getServer());
    
     DMDalekRegistry.DALEK_SPAWNS.clear();
     DMSpawnerRegistry.initDalekSpawns();
  }
  
  @SubscribeEvent
  public void onSave(WorldEvent.Save e) {
     if (!e.getWorld().isRemote()) {
       ServerWorld world = (ServerWorld)e.getWorld();
       if (world.getDimensionKey() == DMDimensions.TARDIS) {
         TardisFlightPool.save(world.getServer());
         System.out.println("Saved TARDIS Data Pool");
         for (Map.Entry<Integer, TardisData> entry : (Iterable<Map.Entry<Integer, TardisData>>)DMTardis.getLoadedTardises().entrySet()) {
           TardisData value = entry.getValue();
           value.saveToDisk();
        } 
        
         DMFlightMode.save(world.getServer());
      } 
    } 
  }
  
  @SubscribeEvent
  public void onRegisterCommandEvent(RegisterCommandsEvent event) {
     CommandDispatcher<CommandSource> commandDispatcher = event.getDispatcher();
     registerCommands(commandDispatcher);
  }
  
  public void registerCommands(CommandDispatcher<CommandSource> dispatcher) {
     CommandDalekModBase.register(dispatcher);
     CommandInteriorDoorPos.register(dispatcher);
     CommandTardisOwnership.register(dispatcher);
  }
  
  @SubscribeEvent
  public void addReloadListeners(AddReloadListenerEvent event) {
     event.addListener((IFutureReloadListener)new InteriorReloadListener("tardis_schematics"));
     event.addListener((IFutureReloadListener)new TardisInteriorReloadListener(GSON, "tardis_interiors"));
     event.addListener((IFutureReloadListener)new TardisReloadListener(GSON, "tardis_exteriors"));
     event.addListener((IFutureReloadListener)new TardisLocationListener(GSON, "tardis_destinations"));
     event.addListener((IFutureReloadListener)new RoundelRecipeReloader(GSON, "roundel_recipes"));
     event.addListener((IFutureReloadListener)new EngineeringTableReloader(GSON, "engineering_recipes"));
     event.addListener((IFutureReloadListener)new KerblamItemReloadListener(GSON, "kerblam_listings"));
  }











  
  public void addDimensionalSpacing(WorldEvent.Load event) {
     if (event.getWorld() instanceof ServerWorld) {
       ServerWorld serverWorld = (ServerWorld)event.getWorld();
      
       if (!serverWorld.getDimensionKey().equals(World.field_234918_g_)) {
        return;
      }
      
       if (serverWorld.func_241109_A_()) {
        return;
      }
      
       (serverWorld.func_72863_F()).field_186029_c.func_235957_b_().func_236195_a_().put(DMStructures.METEORITE_1.get(), DimensionStructuresSettings.field_236191_b_.get(DMStructures.METEORITE_1.get()));
       (serverWorld.func_72863_F()).field_186029_c.func_235957_b_().func_236195_a_().put(DMStructures.METEORITE_2.get(), DimensionStructuresSettings.field_236191_b_.get(DMStructures.METEORITE_2.get()));
       (serverWorld.func_72863_F()).field_186029_c.func_235957_b_().func_236195_a_().put(DMStructures.METEORITE_3.get(), DimensionStructuresSettings.field_236191_b_.get(DMStructures.METEORITE_3.get()));
       (serverWorld.func_72863_F()).field_186029_c.func_235957_b_().func_236195_a_().put(DMStructures.AUTON_DUNGEON.get(), DimensionStructuresSettings.field_236191_b_.get(DMStructures.AUTON_DUNGEON.get()));
       (serverWorld.func_72863_F()).field_186029_c.func_235957_b_().func_236195_a_().put(DMStructures.CYBERMAN_TOMB.get(), DimensionStructuresSettings.field_236191_b_.get(DMStructures.CYBERMAN_TOMB.get()));
    } 
  }
  
  public static void registerStructure(RegistryKey<DimensionSettings> dimension, Structure<?> structure, StructureSeparationSettings separationSettings) {
     WorldGenRegistries.field_243658_j.func_243575_c(dimension).ifPresent(dimensionSettings -> {
          DimensionStructuresSettings structuresSettings = dimensionSettings.func_236108_a_();
          structuresSettings.field_236193_d_.put(structure, separationSettings);
        });
  }
  
   public static final ScoreCriteria TARDIS_COUNT = new ScoreCriteria("dalekmod.tardisCount");
   public static final Map<String, ScoreCriteria> ARS_CRITERIA = new HashMap<>();
  public static void addARSInteriorCriteria(String reg) {
     ARS_CRITERIA.put("dalekmod.ars:build." + reg, new ScoreCriteria("dalekmod.ars:build." + reg));
  }
}


