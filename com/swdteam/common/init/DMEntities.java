package com.swdteam.common.init;

import com.swdteam.common.RegistryHandler;
import com.swdteam.common.entity.AutonEntity;
import com.swdteam.common.entity.ClassicItemEntity;
import com.swdteam.common.entity.CyberdroneEntity;
import com.swdteam.common.entity.CybermanEntity;
import com.swdteam.common.entity.CybermatEntity;
import com.swdteam.common.entity.CybervillagerEntity;
import com.swdteam.common.entity.KerblamManEntity;
import com.swdteam.common.entity.LaserEntity;
import com.swdteam.common.entity.OodEntity;
import com.swdteam.common.entity.WeepingAngelEntity;
import com.swdteam.common.entity.classic.ClassicSkeletonEntity;
import com.swdteam.common.entity.classic.ClassicSpiderEntity;
import com.swdteam.common.entity.dalek.DalekEntity;
import java.util.Optional;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.RegistryObject;





public class DMEntities
{
  public static void init() {}

   public static final RegistryObject<EntityType<LaserEntity>> LASER_ENTITY = RegistryHandler.ENTITY_TYPES.register("laser", () -> EntityType.Builder.func_220322_a(LaserEntity::new, EntityClassification.MISC).func_220321_a(0.5F, 0.5F).func_206830_a((new ResourceLocation("dalekmod", "laser")).toString()));


   public static final RegistryObject<EntityType<DalekEntity>> DALEK_ENTITY = RegistryHandler.ENTITY_TYPES.register("dalek", () -> EntityType.Builder.func_220322_a(DalekEntity::new, EntityClassification.MONSTER).func_220321_a(0.7F, 1.65F).func_220320_c().func_206830_a((new ResourceLocation("dalekmod", "dalek")).toString()));


   public static final RegistryObject<EntityType<CybermanEntity>> CYBERMAN_ENTITY = RegistryHandler.ENTITY_TYPES.register("cyberman", () -> EntityType.Builder.func_220322_a(CybermanEntity::new, EntityClassification.MONSTER).func_220321_a(0.6F, 1.9F).func_206830_a((new ResourceLocation("dalekmod", "cyberman")).toString()));


   public static final RegistryObject<EntityType<CybervillagerEntity>> CYBERMANVILLAGER_ENTITY = RegistryHandler.ENTITY_TYPES.register("cybervillager", () -> EntityType.Builder.func_220322_a(CybervillagerEntity::new, EntityClassification.MONSTER).func_220321_a(0.6F, 1.9F).func_206830_a((new ResourceLocation("dalekmod", "cybervillager")).toString()));


   public static final RegistryObject<EntityType<OodEntity>> OOD_ENTITY = RegistryHandler.ENTITY_TYPES.register("ood", () -> EntityType.Builder.func_220322_a(OodEntity::new, EntityClassification.MONSTER).func_220321_a(0.6F, 1.9F).func_206830_a((new ResourceLocation("dalekmod", "ood")).toString()));



   public static final RegistryObject<EntityType<CybermatEntity>> CYBERMAT_ENTITY = RegistryHandler.ENTITY_TYPES.register("cybermat", () -> EntityType.Builder.func_220322_a(CybermatEntity::new, EntityClassification.MONSTER).func_220321_a(0.6F, 0.2F).func_206830_a((new ResourceLocation("dalekmod", "cybermat")).toString()));


   public static final RegistryObject<EntityType<AutonEntity>> AUTON_ENTITY = RegistryHandler.ENTITY_TYPES.register("auton", () -> EntityType.Builder.func_220322_a(AutonEntity::new, EntityClassification.MONSTER).func_220321_a(0.6F, 1.9F).func_206830_a((new ResourceLocation("dalekmod", "auton")).toString()));


   public static final RegistryObject<EntityType<WeepingAngelEntity>> WEEPING_ANGEL_ENTITY = RegistryHandler.ENTITY_TYPES.register("weeping_angel", () -> EntityType.Builder.func_220322_a(WeepingAngelEntity::new, EntityClassification.MONSTER).func_220321_a(0.6F, 1.9F).func_206830_a((new ResourceLocation("dalekmod", "weeping_angel")).toString()));


   public static final RegistryObject<EntityType<ClassicSpiderEntity>> CLASSIC_SPIDER_ENTITY = RegistryHandler.ENTITY_TYPES.register("classic_spider", () -> EntityType.Builder.func_220322_a(ClassicSpiderEntity::new, EntityClassification.MONSTER).func_220321_a(1.5F, 1.0F).func_206830_a((new ResourceLocation("dalekmod", "classic_spider")).toString()));


   public static final RegistryObject<EntityType<ClassicSkeletonEntity>> CLASSIC_SKELETON_ENTITY = RegistryHandler.ENTITY_TYPES.register("classic_skeleton", () -> EntityType.Builder.func_220322_a(ClassicSkeletonEntity::new, EntityClassification.MONSTER).func_220321_a(0.6F, 1.9F).func_206830_a((new ResourceLocation("dalekmod", "classic_skeleton")).toString()));


   public static final RegistryObject<EntityType<KerblamManEntity>> KERBLAM_MAN = RegistryHandler.ENTITY_TYPES.register("kerblam_man", () -> EntityType.Builder.func_220322_a(KerblamManEntity::new, EntityClassification.AMBIENT).func_220321_a(0.6F, 1.9F).setTrackingRange(15).func_206830_a((new ResourceLocation("dalekmod", "kerblam_man")).toString()));


   public static final RegistryObject<EntityType<ClassicItemEntity>> CLASSIC_ITEM = RegistryHandler.ENTITY_TYPES.register("classic_item", () -> EntityType.Builder.func_220322_a(ClassicItemEntity::new, EntityClassification.MISC).func_220321_a(0.4F, 0.4F).func_206830_a((new ResourceLocation("dalekmod", "classic_item")).toString()));


   public static final RegistryObject<EntityType<CyberdroneEntity>> CYBERDRONE = RegistryHandler.ENTITY_TYPES.register("cyberdrone", () -> EntityType.Builder.func_220322_a(CyberdroneEntity::new, EntityClassification.MONSTER).func_220321_a(0.5F, 0.5F).setTrackingRange(15).func_206830_a((new ResourceLocation("dalekmod", "cyberdrone")).toString()));


  public static void registerEntityWorldSpawn(EntityType<?> entity, EntityClassification classification, int weight, int groupMin, int groupMax, Biome... biomes) {
     for (Biome biome : biomes) {
       if (biome == null || biome.func_201856_r() != Biome.Category.OCEAN);
    }
  }



  public static EntityType<?> getEntityTypeFromString(String s) {
     Optional<EntityType<?>> ty = EntityType.func_220327_a("dalekmod:" + s);
     return ty.get();
  }
}


