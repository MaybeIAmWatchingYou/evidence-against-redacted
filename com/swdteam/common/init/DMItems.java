package com.swdteam.common.init;

import com.swdteam.common.RegistryHandler;
import com.swdteam.common.entity.dalek.DalekType;
import com.swdteam.common.item.ArtronItem;
import com.swdteam.common.item.ChameleonCartridgeItem;
import com.swdteam.common.item.ClothesItem;
import com.swdteam.common.item.DataModuleItem;
import com.swdteam.common.item.DiscItem;
import com.swdteam.common.item.FluidLinkItem;
import com.swdteam.common.item.FoodItem;
import com.swdteam.common.item.FullArtronItem;
import com.swdteam.common.item.GasMask;
import com.swdteam.common.item.GunItem;
import com.swdteam.common.item.KnockbackWeaponItem;
import com.swdteam.common.item.PhoneItem;
import com.swdteam.common.item.SpawnerItem;
import com.swdteam.common.item.StattenheimRemoteItem;
import com.swdteam.common.item.TardisKeyItem;
import com.swdteam.common.item.TardisSpawnerItem;
import com.swdteam.common.item.sonics.SonicScrewdriverCustomizedItem;
import com.swdteam.common.item.sonics.SonicScrewdriverItem;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.block.Block;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.AxeItem;
import net.minecraft.item.BlockNamedItem;
import net.minecraft.item.Food;
import net.minecraft.item.HoeItem;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemTier;
import net.minecraft.item.Items;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.Rarity;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.SwordItem;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.fml.RegistryObject;





public class DMItems
{
   public static RegistryObject<Item> TARDIS_SPAWNER = RegistryHandler.ITEMS.register("tardis_spawner", () -> new TardisSpawnerItem((new Item.Properties()).func_200916_a(DMTabs.DM_TARDIS).func_208103_a(Rarity.EPIC)));
   public static RegistryObject<Item> TARDIS_KEY = RegistryHandler.ITEMS.register("tardis_key", () -> new TardisKeyItem((new Item.Properties()).func_200916_a(DMTabs.DM_TARDIS), ""));
   public static RegistryObject<Item> TARDIS_SPADE_KEY = RegistryHandler.ITEMS.register("tardis_spade_key", () -> new TardisKeyItem((new Item.Properties()).func_200916_a(DMTabs.DM_TARDIS), ""));
   public static RegistryObject<Item> TARDIS_FAN_KEY = RegistryHandler.ITEMS.register("tardis_fan_key", () -> new TardisKeyItem((new Item.Properties()).func_200916_a(DMTabs.DM_TARDIS), ""));
   public static RegistryObject<Item> TARDIS_LOCK_PICK_KEY = RegistryHandler.ITEMS.register("tardis_lock_pick_key", () -> new TardisKeyItem((new Item.Properties()).func_200916_a(DMTabs.DM_TARDIS), ""));
   public static RegistryObject<Item> TARDIS_CORE = RegistryHandler.ITEMS.register("tardis_core", () -> new Item((new Item.Properties()).func_200916_a(DMTabs.DM_TARDIS).func_208103_a(Rarity.RARE)));
   public static RegistryObject<Item> TARDIS_PANELS = RegistryHandler.ITEMS.register("tardis_panels", () -> new Item((new Item.Properties()).func_200916_a(DMTabs.DM_TARDIS).func_208103_a(Rarity.RARE)));
   public static RegistryObject<Item> TARDIS_BASE = RegistryHandler.ITEMS.register("tardis_base", () -> new Item((new Item.Properties()).func_200916_a(DMTabs.DM_TARDIS).func_208103_a(Rarity.RARE)));
   public static RegistryObject<Item> FLIGHT_FLUID_LINK = RegistryHandler.ITEMS.register("flight_fluid_link", () -> new FluidLinkItem((new Item.Properties()).func_200916_a(DMTabs.DM_TARDIS), DMTranslationKeys.ITEM_FLIGHT_FLUID_LINK_DESC));
   public static RegistryObject<Item> FUEL_FLUID_LINK = RegistryHandler.ITEMS.register("fuel_fluid_link", () -> new FluidLinkItem((new Item.Properties()).func_200916_a(DMTabs.DM_TARDIS), DMTranslationKeys.ITEM_FUEL_FLUID_LINK_DESC));
   public static RegistryObject<Item> ACCURACY_FLUID_LINK = RegistryHandler.ITEMS.register("accuracy_fluid_link", () -> new FluidLinkItem((new Item.Properties()).func_200916_a(DMTabs.DM_TARDIS), DMTranslationKeys.ITEM_ACCURACY_FLUID_LINK_DESC));
   public static RegistryObject<Item> CHAMELEON_CATRTIDGE = RegistryHandler.ITEMS.register("chameleon_cartridge", () -> new ChameleonCartridgeItem((new Item.Properties()).func_200916_a(DMTabs.DM_TARDIS)));
   public static RegistryObject<Item> STATTENHEIM_REMOTE = RegistryHandler.ITEMS.register("stattenheim_remote", () -> new StattenheimRemoteItem((new Item.Properties()).func_200918_c(24).func_200916_a(DMTabs.DM_TARDIS)));
   public static RegistryObject<Item> DATA_MODULE = RegistryHandler.ITEMS.register("data_module", () -> new DataModuleItem((new Item.Properties()).func_200916_a(ItemGroup.field_78026_f)));
   public static RegistryObject<Item> DATA_MODULE_GOLD = RegistryHandler.ITEMS.register("data_module_gold", () -> new DataModuleItem((new Item.Properties()).func_200916_a(ItemGroup.field_78026_f).func_208103_a(Rarity.EPIC)));


   public static RegistryObject<Item> ZEITON_CRYSTAL = RegistryHandler.ITEMS.register("zeiton_crystal", () -> new Item((new Item.Properties()).func_200916_a(ItemGroup.field_78026_f)));
   public static RegistryObject<Item> SONIC_CRYSTAL = RegistryHandler.ITEMS.register("sonic_crystal", () -> new Item((new Item.Properties()).func_200916_a(ItemGroup.field_78026_f)));
   public static RegistryObject<Item> REFINED_SONIC_CRYSTAL = RegistryHandler.ITEMS.register("refined_sonic_crystal", () -> new Item((new Item.Properties()).func_200916_a(ItemGroup.field_78026_f)));
   public static RegistryObject<Item> CRYSTALINE = RegistryHandler.ITEMS.register("crystaline", () -> new Item((new Item.Properties()).func_200916_a(ItemGroup.field_78026_f)));
   public static RegistryObject<Item> SILICON = RegistryHandler.ITEMS.register("silicon", () -> new Item((new Item.Properties()).func_200916_a(ItemGroup.field_78026_f)));
   public static RegistryObject<Item> DALEKANIUM_INGOT = RegistryHandler.ITEMS.register("dalekanium_ingot", () -> new Item((new Item.Properties()).func_200916_a(ItemGroup.field_78026_f)));
   public static RegistryObject<Item> REFINED_DALEKANIUM_INGOT = RegistryHandler.ITEMS.register("refined_dalekanium_ingot", () -> new Item((new Item.Properties()).func_200916_a(ItemGroup.field_78026_f)));
   public static RegistryObject<Item> DALEKANIUM_CONCENTRATE = RegistryHandler.ITEMS.register("dalekanium_concentrate", () -> new FoodItem((new Item.Properties()).func_221540_a((new Food.Builder()).func_221456_a(1).func_221454_a(0.0F).func_221452_a(new EffectInstance(Effects.field_76436_u, 100, 60), 3.0F).func_221453_d()).func_200919_a(Items.field_151069_bo).func_200916_a(ItemGroup.field_78026_f), true));
   public static RegistryObject<Item> METALERT = RegistryHandler.ITEMS.register("metalert", () -> new FoodItem((new Item.Properties()).func_221540_a((new Food.Builder()).func_221456_a(1).func_221454_a(0.0F).func_221452_a(new EffectInstance(Effects.field_76421_d, 300, 70), 5.0F).func_221452_a(new EffectInstance(Effects.field_76436_u, 300, 70), 5.0F).func_221453_d()).func_200919_a(Items.field_151069_bo).func_200916_a(ItemGroup.field_78026_f).func_208103_a(Rarity.RARE), true));
   public static RegistryObject<Item> METALERT_SCRAP = RegistryHandler.ITEMS.register("metalert_scrap", () -> new Item((new Item.Properties()).func_200916_a(ItemGroup.field_78026_f).func_208103_a(Rarity.RARE)));
   public static RegistryObject<Item> STEEL_INGOT = RegistryHandler.ITEMS.register("steel_ingot", () -> new Item((new Item.Properties()).func_200916_a(ItemGroup.field_78026_f)));
   public static RegistryObject<Item> STAINLESS_STEEL_INGOT = RegistryHandler.ITEMS.register("stainless_steel_ingot", () -> new Item((new Item.Properties()).func_200916_a(ItemGroup.field_78026_f)));
   public static RegistryObject<Item> BRASS_INGOT = RegistryHandler.ITEMS.register("brass_ingot", () -> new Item((new Item.Properties()).func_200916_a(ItemGroup.field_78026_f)));
   public static RegistryObject<Item> CINNABAR = RegistryHandler.ITEMS.register("cinnabar", () -> new Item((new Item.Properties()).func_200916_a(ItemGroup.field_78026_f)));
   public static RegistryObject<Item> MERCURY = RegistryHandler.ITEMS.register("mercury", () -> new FoodItem((new Item.Properties()).func_221540_a((new Food.Builder()).func_221456_a(1).func_221454_a(0.0F).func_221452_a(new EffectInstance(Effects.field_76436_u, 800, 45), 7.0F).func_221453_d()).func_200919_a(Items.field_151069_bo).func_200916_a(ItemGroup.field_78026_f), true));
   public static RegistryObject<Item> ARTRON = RegistryHandler.ITEMS.register("artron", () -> new ArtronItem((new Item.Properties()).func_221540_a((new Food.Builder()).func_221456_a(1).func_221454_a(0.0F).func_221452_a(new EffectInstance(Effects.field_76429_m, 500, 70), 8.0F).func_221452_a(new EffectInstance(Effects.field_180152_w, 10, 20), 4.0F).func_221453_d()).func_200919_a(Items.field_151069_bo)));
   public static RegistryObject<Item> FULL_ARTRON = RegistryHandler.ITEMS.register("artron_full", () -> new FullArtronItem((new Item.Properties()).func_221540_a((new Food.Builder()).func_221456_a(1).func_221454_a(0.0F).func_221452_a(new EffectInstance(Effects.field_76429_m, 500, 70), 8.0F).func_221452_a(new EffectInstance(Effects.field_180152_w, 10, 20), 4.0F).func_221453_d()).func_200919_a(Items.field_151069_bo).func_200916_a(ItemGroup.field_78026_f)));
   public static RegistryObject<Item> SONIC_EMITTER = RegistryHandler.ITEMS.register("sonic_emitter", () -> new Item((new Item.Properties()).func_200916_a(ItemGroup.field_78026_f)));
   public static RegistryObject<Item> CIRCUIT = RegistryHandler.ITEMS.register("circuit", () -> new Item((new Item.Properties()).func_200916_a(ItemGroup.field_78026_f)));
   public static RegistryObject<Item> PLASTIC_CHUNK = RegistryHandler.ITEMS.register("plastic_chunk", () -> new Item((new Item.Properties()).func_200916_a(ItemGroup.field_78026_f)));
   public static RegistryObject<Item> ROUNDEL_MOLD = RegistryHandler.ITEMS.register("roundel_mold", () -> new Item((new Item.Properties()).func_200916_a(ItemGroup.field_78026_f)));
   public static RegistryObject<Item> ANTI_RADIATION_DRUGS = RegistryHandler.ITEMS.register("anti_radiation_drugs", () -> new Item((new Item.Properties()).func_200916_a(ItemGroup.field_78026_f)));
   public static RegistryObject<Item> BRASS_COG = RegistryHandler.ITEMS.register("brass_cog", () -> new Item((new Item.Properties()).func_200916_a(ItemGroup.field_78026_f)));
   public static RegistryObject<Item> STEEL_ROD = RegistryHandler.ITEMS.register("steel_rod", () -> new Item((new Item.Properties()).func_200916_a(ItemGroup.field_78026_f)));
   public static RegistryObject<Item> DARK_STAR_DUST = RegistryHandler.ITEMS.register("dark_star_dust", () -> new Item((new Item.Properties()).func_200916_a(ItemGroup.field_78026_f)));
   public static RegistryObject<Item> DARK_STAR_INGOT = RegistryHandler.ITEMS.register("dark_star_ingot", () -> new Item((new Item.Properties()).func_200916_a(ItemGroup.field_78026_f).func_208103_a(Rarity.RARE)));
   public static RegistryObject<Item> TEA_CUP = RegistryHandler.ITEMS.register("tea_cup", () -> new Item((new Item.Properties()).func_200916_a(ItemGroup.field_78026_f)));
   public static RegistryObject<Item> TEA_LEAF = RegistryHandler.ITEMS.register("tea_leaf", () -> new Item((new Item.Properties()).func_200916_a(ItemGroup.field_78026_f)));
   public static RegistryObject<Item> BULLET = RegistryHandler.ITEMS.register("bullet", () -> new Item((new Item.Properties()).func_200916_a(ItemGroup.field_78026_f)));
   public static RegistryObject<Item> CREDIT = RegistryHandler.ITEMS.register("credit", () -> new Item((new Item.Properties()).func_200916_a(ItemGroup.field_78026_f).func_200917_a(16)));


   public static RegistryObject<Item> STEEL_SWORD = RegistryHandler.ITEMS.register("steel_sword", () -> new SwordItem(DMItemTiers.STEEL, 3, -2.4F, (new Item.Properties()).func_200916_a(ItemGroup.field_78037_j)));
   public static RegistryObject<Item> STEEL_SHOVEL = RegistryHandler.ITEMS.register("steel_shovel", () -> new ShovelItem(DMItemTiers.STEEL, 1.5F, -3.0F, (new Item.Properties()).func_200916_a(ItemGroup.field_78040_i)));
   public static RegistryObject<Item> STEEL_PICKAXE = RegistryHandler.ITEMS.register("steel_pickaxe", () -> new PickaxeItem(DMItemTiers.STEEL, 1, -2.8F, (new Item.Properties()).func_200916_a(ItemGroup.field_78040_i)));
   public static RegistryObject<Item> STEEL_BATTLEAXE = RegistryHandler.ITEMS.register("steel_battleaxe", () -> new AxeItem(DMItemTiers.STEEL, 6.0F, -2.5F, (new Item.Properties()).func_200916_a(ItemGroup.field_78040_i)));
   public static RegistryObject<Item> STEEL_HOE = RegistryHandler.ITEMS.register("steel_hoe", () -> new HoeItem(DMItemTiers.STEEL, -1, -1.0F, (new Item.Properties()).func_200916_a(ItemGroup.field_78040_i)));
   public static RegistryObject<Item> STEEL_MACE = RegistryHandler.ITEMS.register("steel_mace", () -> new SwordItem(DMItemTiers.STEEL, 7, -3.2F, (new Item.Properties()).func_200916_a(ItemGroup.field_78037_j)));
   public static RegistryObject<Item> STAINLESS_STEEL_SWORD = RegistryHandler.ITEMS.register("stainless_steel_sword", () -> new SwordItem(DMItemTiers.STAINLESS_STEEL, 3, -2.4F, (new Item.Properties()).func_200916_a(ItemGroup.field_78037_j)));
   public static RegistryObject<Item> STAINLESS_STEEL_SHOVEL = RegistryHandler.ITEMS.register("stainless_steel_shovel", () -> new ShovelItem(DMItemTiers.STAINLESS_STEEL, 1.5F, -3.0F, (new Item.Properties()).func_200916_a(ItemGroup.field_78040_i)));
   public static RegistryObject<Item> STAINLESS_STEEL_PICKAXE = RegistryHandler.ITEMS.register("stainless_steel_pickaxe", () -> new PickaxeItem(DMItemTiers.STAINLESS_STEEL, 1, -2.8F, (new Item.Properties()).func_200916_a(ItemGroup.field_78040_i)));
   public static RegistryObject<Item> STAINLESS_STEEL_BATTLEAXE = RegistryHandler.ITEMS.register("stainless_steel_battleaxe", () -> new AxeItem(DMItemTiers.STAINLESS_STEEL, 6.0F, -2.5F, (new Item.Properties()).func_200916_a(ItemGroup.field_78040_i)));
   public static RegistryObject<Item> STAINLESS_STEEL_HOE = RegistryHandler.ITEMS.register("stainless_steel_hoe", () -> new HoeItem(DMItemTiers.STAINLESS_STEEL, -2, -1.0F, (new Item.Properties()).func_200916_a(ItemGroup.field_78040_i)));
   public static RegistryObject<Item> STAINLESS_STEEL_MACE = RegistryHandler.ITEMS.register("stainless_steel_mace", () -> new SwordItem(DMItemTiers.STAINLESS_STEEL, 7, -3.2F, (new Item.Properties()).func_200916_a(ItemGroup.field_78037_j)));
   public static RegistryObject<Item> DALEKANIUM_SWORD = RegistryHandler.ITEMS.register("dalekanium_sword", () -> new SwordItem(DMItemTiers.DALEKANIUM, 3, -2.4F, (new Item.Properties()).func_200916_a(ItemGroup.field_78037_j)));
   public static RegistryObject<Item> DALEKANIUM_SHOVEL = RegistryHandler.ITEMS.register("dalekanium_shovel", () -> new ShovelItem(DMItemTiers.DALEKANIUM, 1.5F, -3.0F, (new Item.Properties()).func_200916_a(ItemGroup.field_78040_i)));
   public static RegistryObject<Item> DALEKANIUM_PICKAXE = RegistryHandler.ITEMS.register("dalekanium_pickaxe", () -> new PickaxeItem(DMItemTiers.DALEKANIUM, 1, -2.8F, (new Item.Properties()).func_200916_a(ItemGroup.field_78040_i)));
   public static RegistryObject<Item> DALEKANIUM_BATTLEAXE = RegistryHandler.ITEMS.register("dalekanium_battleaxe", () -> new AxeItem(DMItemTiers.DALEKANIUM, 2.0F, -2.5F, (new Item.Properties()).func_200916_a(ItemGroup.field_78040_i)));
   public static RegistryObject<Item> DALEKANIUM_HOE = RegistryHandler.ITEMS.register("dalekanium_hoe", () -> new HoeItem(DMItemTiers.DALEKANIUM, -2, -1.0F, (new Item.Properties()).func_200916_a(ItemGroup.field_78040_i)));
   public static RegistryObject<Item> DALEKANIUM_MACE = RegistryHandler.ITEMS.register("dalekanium_mace", () -> new SwordItem(DMItemTiers.DALEKANIUM, 7, -3.2F, (new Item.Properties()).func_200916_a(ItemGroup.field_78037_j)));
   public static RegistryObject<Item> REFINED_DALEKANIUM_SWORD = RegistryHandler.ITEMS.register("refined_dalekanium_sword", () -> new SwordItem(DMItemTiers.REFINED_DALEKANIUM, 3, -2.4F, (new Item.Properties()).func_200916_a(ItemGroup.field_78037_j)));
   public static RegistryObject<Item> REFINED_DALEKANIUM_SHOVEL = RegistryHandler.ITEMS.register("refined_dalekanium_shovel", () -> new ShovelItem(DMItemTiers.REFINED_DALEKANIUM, 1.5F, -3.0F, (new Item.Properties()).func_200916_a(ItemGroup.field_78040_i)));
   public static RegistryObject<Item> REFINED_DALEKANIUM_PICKAXE = RegistryHandler.ITEMS.register("refined_dalekanium_pickaxe", () -> new PickaxeItem(DMItemTiers.REFINED_DALEKANIUM, 1, -2.8F, (new Item.Properties()).func_200916_a(ItemGroup.field_78040_i)));
   public static RegistryObject<Item> REFINED_DALEKANIUM_BATTLEAXE = RegistryHandler.ITEMS.register("refined_dalekanium_battleaxe", () -> new AxeItem(DMItemTiers.REFINED_DALEKANIUM, 7.0F, -2.5F, (new Item.Properties()).func_200916_a(ItemGroup.field_78040_i)));
   public static RegistryObject<Item> REFINED_DALEKANIUM_HOE = RegistryHandler.ITEMS.register("refined_dalekanium_hoe", () -> new HoeItem(DMItemTiers.REFINED_DALEKANIUM, -2, 0.0F, (new Item.Properties()).func_200916_a(ItemGroup.field_78040_i)));
   public static RegistryObject<Item> REFINED_DALEKANIUM_MACE = RegistryHandler.ITEMS.register("refined_dalekanium_mace", () -> new SwordItem(DMItemTiers.REFINED_DALEKANIUM, 7, -3.2F, (new Item.Properties()).func_200916_a(ItemGroup.field_78037_j)));
   public static RegistryObject<Item> PURE_DALEKANIUM_SWORD = RegistryHandler.ITEMS.register("pure_dalekanium_sword", () -> new SwordItem(DMItemTiers.PURE_DALEKANIUM, 3, -2.4F, (new Item.Properties()).func_200916_a(ItemGroup.field_78037_j)));
   public static RegistryObject<Item> PURE_DALEKANIUM_SHOVEL = RegistryHandler.ITEMS.register("pure_dalekanium_shovel", () -> new ShovelItem(DMItemTiers.PURE_DALEKANIUM, 1.5F, -3.0F, (new Item.Properties()).func_200916_a(ItemGroup.field_78040_i)));
   public static RegistryObject<Item> PURE_DALEKANIUM_PICKAXE = RegistryHandler.ITEMS.register("pure_dalekanium_pickaxe", () -> new PickaxeItem(DMItemTiers.PURE_DALEKANIUM, 1, -2.8F, (new Item.Properties()).func_200916_a(ItemGroup.field_78040_i)));
   public static RegistryObject<Item> PURE_DALEKANIUM_BATTLEAXE = RegistryHandler.ITEMS.register("pure_dalekanium_battleaxe", () -> new AxeItem(DMItemTiers.PURE_DALEKANIUM, 5.0F, -2.5F, (new Item.Properties()).func_200916_a(ItemGroup.field_78040_i)));
   public static RegistryObject<Item> PURE_DALEKANIUM_HOE = RegistryHandler.ITEMS.register("pure_dalekanium_hoe", () -> new HoeItem(DMItemTiers.PURE_DALEKANIUM, -3, 0.0F, (new Item.Properties()).func_200916_a(ItemGroup.field_78040_i)));
   public static RegistryObject<Item> PURE_DALEKANIUM_MACE = RegistryHandler.ITEMS.register("pure_dalekanium_mace", () -> new SwordItem(DMItemTiers.PURE_DALEKANIUM, 7, -3.2F, (new Item.Properties()).func_200916_a(ItemGroup.field_78037_j)));
   public static RegistryObject<Item> METALERT_SWORD = RegistryHandler.ITEMS.register("metalert_sword", () -> new SwordItem(DMItemTiers.METALERT, 3, -2.4F, (new Item.Properties()).func_200916_a(ItemGroup.field_78037_j)));
   public static RegistryObject<Item> METALERT_SHOVEL = RegistryHandler.ITEMS.register("metalert_shovel", () -> new ShovelItem(DMItemTiers.METALERT, 1.5F, -3.0F, (new Item.Properties()).func_200916_a(ItemGroup.field_78040_i)));
   public static RegistryObject<Item> METALERT_PICKAXE = RegistryHandler.ITEMS.register("metalert_pickaxe", () -> new PickaxeItem(DMItemTiers.METALERT, 1, -2.8F, (new Item.Properties()).func_200916_a(ItemGroup.field_78040_i)));
   public static RegistryObject<Item> METALERT_BATTLEAXE = RegistryHandler.ITEMS.register("metalert_battleaxe", () -> new AxeItem(DMItemTiers.METALERT, 5.0F, -2.5F, (new Item.Properties()).func_200916_a(ItemGroup.field_78040_i)));
   public static RegistryObject<Item> METALERT_HOE = RegistryHandler.ITEMS.register("metalert_hoe", () -> new HoeItem(DMItemTiers.METALERT, -4, 0.0F, (new Item.Properties()).func_200916_a(ItemGroup.field_78040_i)));
   public static RegistryObject<Item> METALERT_MACE = RegistryHandler.ITEMS.register("metalert_mace", () -> new SwordItem(DMItemTiers.METALERT, 7, -3.2F, (new Item.Properties()).func_200916_a(ItemGroup.field_78037_j)));
   public static RegistryObject<Item> WOODEN_CANE = RegistryHandler.ITEMS.register("wooden_cane", () -> new KnockbackWeaponItem((IItemTier)ItemTier.WOOD, 0, 0.0F, (new Item.Properties()).func_200916_a(ItemGroup.field_78037_j)));
   public static RegistryObject<Item> BASEBALL_BAT = RegistryHandler.ITEMS.register("baseball_bat", () -> new KnockbackWeaponItem((IItemTier)ItemTier.IRON, 3, 0.0F, (new Item.Properties()).func_200916_a(ItemGroup.field_78037_j)));
   public static RegistryObject<Item> WOODEN_BASEBALL_BAT = RegistryHandler.ITEMS.register("wooden_baseball_bat", () -> new KnockbackWeaponItem((IItemTier)ItemTier.WOOD, 2, 0.0F, (new Item.Properties()).func_200916_a(ItemGroup.field_78037_j)));
   public static RegistryObject<Item> PLUNGER = RegistryHandler.ITEMS.register("plunger", () -> new SwordItem(DMItemTiers.DALEK_PLUNGER, 1, 10.0F, (new Item.Properties()).func_200916_a(ItemGroup.field_78037_j)));
   public static RegistryObject<Item> GUNSTICK = RegistryHandler.ITEMS.register("gunstick", () -> new GunItem(DMItemTiers.DALEK_GUNSTICK, 0.5F, DMProjectiles.BLUE_LASER, DMSoundEvents.ENTITY_DALEK_GUNSTICK_CHARGE, DMSoundEvents.ENTITY_DALEK_GUNSTICK_SHOOT, (new Item.Properties()).func_200916_a(ItemGroup.field_78037_j)));
   public static RegistryObject<Item> CANNON = RegistryHandler.ITEMS.register("cannon", () -> new GunItem(DMItemTiers.DALEK_CANNON, 2.0F, DMProjectiles.EXPLOSIVE_LASER, DMSoundEvents.ENTITY_DALEK_CANNON_CHARGE, DMSoundEvents.ENTITY_DALEK_CANNON_SHOOT, (new Item.Properties()).func_200916_a(ItemGroup.field_78037_j)));
   public static RegistryObject<Item> DESERT_EAGLE = RegistryHandler.ITEMS.register("desert_eagle", () -> new GunItem(DMItemTiers.DEAGLE, 0.5F, DMProjectiles.BULLET, DMSoundEvents.ITEM_GUN_CLICK, DMSoundEvents.ITEM_GUN_SHOOT, (new Item.Properties()).func_200916_a(ItemGroup.field_78037_j)));


   public static RegistryObject<Item> SONIC_SCREWDRIVER = RegistryHandler.ITEMS.register("sonic_screwdriver", () -> new SonicScrewdriverItem(ItemGroup.field_78040_i, 250));
   public static RegistryObject<Item> SONIC_SCREWDRIVER_BASIC = RegistryHandler.ITEMS.register("sonic_screwdriver_basic", () -> new SonicScrewdriverCustomizedItem(DMSoundEvents.ENTITY_SONIC_SECOND, 250));
   public static RegistryObject<Item> SONIC_SCREWDRIVER_1 = RegistryHandler.ITEMS.register("sonic_screwdriver_1", () -> new SonicScrewdriverCustomizedItem(DMSoundEvents.ENTITY_SONIC_SECOND, 250));
   public static RegistryObject<Item> SONIC_SCREWDRIVER_2 = RegistryHandler.ITEMS.register("sonic_screwdriver_2", () -> new SonicScrewdriverCustomizedItem(DMSoundEvents.ENTITY_SONIC_SECOND, 250));
   public static RegistryObject<Item> SONIC_SCREWDRIVER_3 = RegistryHandler.ITEMS.register("sonic_screwdriver_3", () -> new SonicScrewdriverCustomizedItem(DMSoundEvents.ENTITY_SONIC_THIRD, 250));
   public static RegistryObject<Item> SONIC_SCREWDRIVER_4 = RegistryHandler.ITEMS.register("sonic_screwdriver_4", () -> new SonicScrewdriverCustomizedItem(DMSoundEvents.ENTITY_SONIC_FOURTH, 250));
   public static RegistryObject<Item> SONIC_SCREWDRIVER_5 = RegistryHandler.ITEMS.register("sonic_screwdriver_5", () -> new SonicScrewdriverCustomizedItem(DMSoundEvents.ENTITY_SONIC_FIFTH, 250));
   public static RegistryObject<Item> SONIC_SCREWDRIVER_6 = RegistryHandler.ITEMS.register("sonic_screwdriver_6", () -> new SonicScrewdriverCustomizedItem(DMSoundEvents.ENTITY_SONIC_SIXTH, 250));
   public static RegistryObject<Item> SONIC_SCREWDRIVER_7 = RegistryHandler.ITEMS.register("sonic_screwdriver_7", () -> new SonicScrewdriverCustomizedItem(DMSoundEvents.ENTITY_SONIC_NINTH, 250));
   public static RegistryObject<Item> SONIC_SCREWDRIVER_8 = RegistryHandler.ITEMS.register("sonic_screwdriver_8", () -> new SonicScrewdriverCustomizedItem(DMSoundEvents.ENTITY_SONIC_NINTH, 250));
   public static RegistryObject<Item> SONIC_SCREWDRIVER_WAR = RegistryHandler.ITEMS.register("sonic_screwdriver_war", () -> new SonicScrewdriverCustomizedItem(DMSoundEvents.ENTITY_SONIC_NINTH, 250));
   public static RegistryObject<Item> SONIC_SCREWDRIVER_9 = RegistryHandler.ITEMS.register("sonic_screwdriver_9", () -> new SonicScrewdriverCustomizedItem(DMSoundEvents.ENTITY_SONIC_NINTH, 250));
   public static RegistryObject<Item> SONIC_SCREWDRIVER_RIVER = RegistryHandler.ITEMS.register("sonic_screwdriver_river", () -> new SonicScrewdriverCustomizedItem(DMSoundEvents.ENTITY_SONIC_NINTH, 250));
   public static RegistryObject<Item> SONIC_SCREWDRIVER_11 = RegistryHandler.ITEMS.register("sonic_screwdriver_11", () -> new SonicScrewdriverCustomizedItem(DMSoundEvents.ENTITY_SONIC_ELEVENTH, 250));
   public static RegistryObject<Item> SONIC_SCREWDRIVER_12 = RegistryHandler.ITEMS.register("sonic_screwdriver_12", () -> new SonicScrewdriverCustomizedItem(DMSoundEvents.ENTITY_SONIC_NINTH, 250));
   public static RegistryObject<Item> SONIC_SCREWDRIVER_13 = RegistryHandler.ITEMS.register("sonic_screwdriver_13", () -> new SonicScrewdriverCustomizedItem(DMSoundEvents.ENTITY_SONIC_NINTH, 250));
   public static RegistryObject<Item> SONIC_SCREWDRIVER_PY_GREEN = RegistryHandler.ITEMS.register("sonic_screwdriver_py_green", () -> new SonicScrewdriverCustomizedItem(DMSoundEvents.ENTITY_SONIC_PY_GREEN, 250));
   public static RegistryObject<Item> SONIC_SCREWDRIVER_PY_BLUE = RegistryHandler.ITEMS.register("sonic_screwdriver_py_blue", () -> new SonicScrewdriverCustomizedItem(DMSoundEvents.ENTITY_SONIC_PY_BLUE, 250));
   public static RegistryObject<Item> SONIC_SCREWDRIVER_TEMPORAL = RegistryHandler.ITEMS.register("sonic_screwdriver_temporal", () -> new SonicScrewdriverCustomizedItem(DMSoundEvents.ENTITY_SONIC_TEMPORAL, 250));
   public static RegistryObject<Item> SONIC_SCREWDRIVER_PEN = RegistryHandler.ITEMS.register("sonic_screwdriver_pen", () -> new SonicScrewdriverCustomizedItem(DMSoundEvents.ENTITY_SONIC_NINTH, 250));
   public static RegistryObject<Item> SONIC_SCREWDRIVER_EPSILON = RegistryHandler.ITEMS.register("sonic_screwdriver_epsilon", () -> new SonicScrewdriverCustomizedItem(DMSoundEvents.ENTITY_SONIC_EPSILON, 250));
   public static RegistryObject<Item> SONIC_SCREWDRIVER_CANDY = RegistryHandler.ITEMS.register("sonic_screwdriver_candy", () -> new SonicScrewdriverCustomizedItem(DMSoundEvents.ENTITY_SONIC_NINTH, 250));
   public static RegistryObject<Item> SONIC_SCREWDRIVER_LIPSTICK = RegistryHandler.ITEMS.register("sonic_screwdriver_lipstick", () -> new SonicScrewdriverCustomizedItem(DMSoundEvents.ENTITY_SONIC_LIPSTICK, 250));
   public static RegistryObject<Item> SONIC_SCREWDRIVER_LAKE = RegistryHandler.ITEMS.register("sonic_screwdriver_lake", () -> new SonicScrewdriverCustomizedItem(DMSoundEvents.ENTITY_SONIC_LAKE, 250));
   public static RegistryObject<Item> MOBILE_PHONE = RegistryHandler.ITEMS.register("mobile_phone", () -> new PhoneItem(new Item.Properties()));


   public static RegistryObject<Item> CELERY = RegistryHandler.ITEMS.register("celery", () -> new BlockNamedItem((Block)DMBlocks.CELERY.get(), (new Item.Properties()).func_221540_a(DMFoods.CELERY).func_200916_a(ItemGroup.field_78039_h)));
   public static RegistryObject<Item> DALEK_COOKIE = RegistryHandler.ITEMS.register("dalek_cookie", () -> new FoodItem((new Item.Properties()).func_221540_a(DMFoods.DALEK_COOKIE).func_200916_a(ItemGroup.field_78039_h)));
   public static RegistryObject<Item> DALEK_BREAD = RegistryHandler.ITEMS.register("dalek_bread", () -> new FoodItem((new Item.Properties()).func_221540_a(DMFoods.DALEK_BREAD).func_200916_a(ItemGroup.field_78039_h)));
   public static RegistryObject<Item> DALEK_CUPCAKE = RegistryHandler.ITEMS.register("dalek_cupcake", () -> new FoodItem((new Item.Properties()).func_221540_a(DMFoods.DALEK_CUPCAKE).func_200916_a(ItemGroup.field_78039_h)));
   public static RegistryObject<Item> DALEK_DONUT = RegistryHandler.ITEMS.register("dalek_donut", () -> new FoodItem((new Item.Properties()).func_221540_a(DMFoods.DALEK_DONUT).func_200916_a(ItemGroup.field_78039_h)));
   public static RegistryObject<Item> RAW_DALEK_WAFFLE = RegistryHandler.ITEMS.register("raw_dalek_waffle", () -> new FoodItem((new Item.Properties()).func_221540_a(DMFoods.RAW_DALEK_WAFFLE).func_200916_a(ItemGroup.field_78039_h)));
   public static RegistryObject<Item> TOASTED_DALEK_WAFFLE = RegistryHandler.ITEMS.register("toasted_dalek_waffle", () -> new FoodItem((new Item.Properties()).func_221540_a(DMFoods.TOASTED_DALEK_WAFFLE).func_200916_a(ItemGroup.field_78039_h)));
   public static RegistryObject<Item> RAW_DALEK_MUTANT = RegistryHandler.ITEMS.register("raw_dalek_mutant", () -> new FoodItem((new Item.Properties()).func_221540_a(DMFoods.RAW_DALEK_MUTANT).func_200916_a(ItemGroup.field_78039_h)));
   public static RegistryObject<Item> FRIED_DALEK_MUTANT = RegistryHandler.ITEMS.register("fried_dalek_mutant", () -> new FoodItem((new Item.Properties()).func_221540_a(DMFoods.FRIED_DALEK_MUTANT).func_200916_a(ItemGroup.field_78039_h)));
   public static RegistryObject<Item> CHEESE = RegistryHandler.ITEMS.register("cheese", () -> new FoodItem((new Item.Properties()).func_221540_a(DMFoods.CHEESE).func_200916_a(ItemGroup.field_78039_h)));
   public static RegistryObject<Item> FISH_FINGER_AND_CUSTARD = RegistryHandler.ITEMS.register("fish_finger_and_custard", () -> new FoodItem((new Item.Properties()).func_221540_a(DMFoods.FISH_FINGER_AND_CUSTARD).func_200916_a(ItemGroup.field_78039_h)));
   public static RegistryObject<Item> BOWL_OF_CUSTARD = RegistryHandler.ITEMS.register("bowl_of_custard", () -> new FoodItem((new Item.Properties()).func_221540_a(DMFoods.BOWL_OF_CUSTARD).func_200916_a(ItemGroup.field_78039_h)));
   public static RegistryObject<Item> JELLY_BABIES = RegistryHandler.ITEMS.register("jelly_babies", () -> new FoodItem((new Item.Properties()).func_221540_a(DMFoods.JELLY_BABIES).func_200916_a(ItemGroup.field_78039_h)));
   public static RegistryObject<Item> JAMMY_DODGER = RegistryHandler.ITEMS.register("jammy_dodger", () -> new FoodItem((new Item.Properties()).func_221540_a(DMFoods.JAMMY_DODGER).func_200916_a(ItemGroup.field_78039_h)));
   public static RegistryObject<Item> KRONKMEAT = RegistryHandler.ITEMS.register("kronkmeat", () -> new FoodItem((new Item.Properties()).func_221540_a(DMFoods.KRONKMEAT).func_200916_a(ItemGroup.field_78039_h)));
   public static RegistryObject<Item> KRONKBURGER = RegistryHandler.ITEMS.register("kronkburger", () -> new FoodItem((new Item.Properties()).func_221540_a(DMFoods.KRONKBURGER).func_200916_a(ItemGroup.field_78039_h)));
   public static RegistryObject<Item> KRONKFRIES = RegistryHandler.ITEMS.register("kronkfries", () -> new FoodItem((new Item.Properties()).func_221540_a(DMFoods.KRONKFRIES).func_200916_a(ItemGroup.field_78039_h)));
   public static RegistryObject<Item> KRONKDOG = RegistryHandler.ITEMS.register("kronkdog", () -> new FoodItem((new Item.Properties()).func_221540_a(DMFoods.KRONKDOG).func_200916_a(ItemGroup.field_78039_h)));
   public static RegistryObject<Item> KRONKSHAKE = RegistryHandler.ITEMS.register("kronkshake", () -> new FoodItem((new Item.Properties()).func_221540_a(DMFoods.KRONKSHAKE).func_200916_a(ItemGroup.field_78039_h), true));
   public static RegistryObject<Item> BUBBLE_SHOCK = RegistryHandler.ITEMS.register("bubble_shock", () -> new FoodItem((new Item.Properties()).func_200919_a(Items.field_151069_bo).func_221540_a(DMFoods.BUBBLE_SHOCK).func_200916_a(ItemGroup.field_78039_h), true));
   public static RegistryObject<Item> TEA = RegistryHandler.ITEMS.register("tea", () -> new FoodItem((new Item.Properties()).func_200919_a((Item)TEA_CUP.get()).func_221540_a(DMFoods.TEA).func_200916_a(ItemGroup.field_78039_h), true));
   public static RegistryObject<Item> PEPSI = RegistryHandler.ITEMS.register("pepsi", () -> new FoodItem((new Item.Properties()).func_221540_a(DMFoods.PEPSI).func_200916_a(ItemGroup.field_78039_h), true));


   public static RegistryObject<Item> MUSIC_DISC_CYBERWAVE = RegistryHandler.ITEMS.register("music_disc_cyberwave", () -> new DiscItem(5, (Supplier)DMSoundEvents.MUSIC_DISC_CYBERWAVE, (new Item.Properties()).func_208103_a(Rarity.RARE).func_200916_a(ItemGroup.field_78026_f)));
   public static RegistryObject<Item> MUSIC_DISC_SONG = RegistryHandler.ITEMS.register("music_disc_song", () -> new DiscItem(5, (Supplier)DMSoundEvents.MUSIC_DISC_SONG, (new Item.Properties()).func_208103_a(Rarity.EPIC)));


   public static RegistryObject<Item> WHITE_FEZ = RegistryHandler.ITEMS.register("white_fez", () -> new ClothesItem(EquipmentSlotType.HEAD));
   public static RegistryObject<Item> ORANGE_FEZ = RegistryHandler.ITEMS.register("orange_fez", () -> new ClothesItem(EquipmentSlotType.HEAD));
   public static RegistryObject<Item> MAGENTA_FEZ = RegistryHandler.ITEMS.register("magenta_fez", () -> new ClothesItem(EquipmentSlotType.HEAD));
   public static RegistryObject<Item> LIGHT_BLUE_FEZ = RegistryHandler.ITEMS.register("light_blue_fez", () -> new ClothesItem(EquipmentSlotType.HEAD));
   public static RegistryObject<Item> YELLOW_FEZ = RegistryHandler.ITEMS.register("yellow_fez", () -> new ClothesItem(EquipmentSlotType.HEAD));
   public static RegistryObject<Item> LIME_FEZ = RegistryHandler.ITEMS.register("lime_fez", () -> new ClothesItem(EquipmentSlotType.HEAD));
   public static RegistryObject<Item> PINK_FEZ = RegistryHandler.ITEMS.register("pink_fez", () -> new ClothesItem(EquipmentSlotType.HEAD));
   public static RegistryObject<Item> GRAY_FEZ = RegistryHandler.ITEMS.register("gray_fez", () -> new ClothesItem(EquipmentSlotType.HEAD));
   public static RegistryObject<Item> LIGHT_GRAY_FEZ = RegistryHandler.ITEMS.register("light_gray_fez", () -> new ClothesItem(EquipmentSlotType.HEAD));
   public static RegistryObject<Item> CYAN_FEZ = RegistryHandler.ITEMS.register("cyan_fez", () -> new ClothesItem(EquipmentSlotType.HEAD));
   public static RegistryObject<Item> PURPLE_FEZ = RegistryHandler.ITEMS.register("purple_fez", () -> new ClothesItem(EquipmentSlotType.HEAD));
   public static RegistryObject<Item> BLUE_FEZ = RegistryHandler.ITEMS.register("blue_fez", () -> new ClothesItem(EquipmentSlotType.HEAD));
   public static RegistryObject<Item> BROWN_FEZ = RegistryHandler.ITEMS.register("brown_fez", () -> new ClothesItem(EquipmentSlotType.HEAD));
   public static RegistryObject<Item> GREEN_FEZ = RegistryHandler.ITEMS.register("green_fez", () -> new ClothesItem(EquipmentSlotType.HEAD));
   public static RegistryObject<Item> RED_FEZ = RegistryHandler.ITEMS.register("red_fez", () -> new ClothesItem(EquipmentSlotType.HEAD));
   public static RegistryObject<Item> BLACK_FEZ = RegistryHandler.ITEMS.register("black_fez", () -> new ClothesItem(EquipmentSlotType.HEAD));
   public static RegistryObject<Item> GAS_MASK = RegistryHandler.ITEMS.register("gas_mask", () -> new GasMask(EquipmentSlotType.HEAD));
   public static RegistryObject<Item> THREE_DIMENSIONAL_GLASSES = RegistryHandler.ITEMS.register("3d_glasses", () -> new ClothesItem(EquipmentSlotType.HEAD));
   public static RegistryObject<Item> GLASSES = RegistryHandler.ITEMS.register("glasses", () -> new ClothesItem(EquipmentSlotType.HEAD));
   public static RegistryObject<Item> EYESTALK = RegistryHandler.ITEMS.register("eyestalk", () -> new ClothesItem(EquipmentSlotType.HEAD));

   public static RegistryObject<Item> ARMOUR_DALEKANIUM_HELMET = RegistryHandler.ITEMS.register("dalekanium_helmet", () -> new ArmorItem(DMArmorMaterial.ArmorMaterial.DALEKANIUM, EquipmentSlotType.HEAD, (new Item.Properties()).func_200916_a(ItemGroup.field_78037_j)));
   public static RegistryObject<Item> ARMOUR_DALEKANIUM_CHESTPLATE = RegistryHandler.ITEMS.register("dalekanium_chestplate", () -> new ArmorItem(DMArmorMaterial.ArmorMaterial.DALEKANIUM, EquipmentSlotType.CHEST, (new Item.Properties()).func_200916_a(ItemGroup.field_78037_j)));
   public static RegistryObject<Item> ARMOUR_DALEKANIUM_LEGGINGS = RegistryHandler.ITEMS.register("dalekanium_leggings", () -> new ArmorItem(DMArmorMaterial.ArmorMaterial.DALEKANIUM, EquipmentSlotType.LEGS, (new Item.Properties()).func_200916_a(ItemGroup.field_78037_j)));
   public static RegistryObject<Item> ARMOUR_DALEKANIUM_BOOTS = RegistryHandler.ITEMS.register("dalekanium_boots", () -> new ArmorItem(DMArmorMaterial.ArmorMaterial.DALEKANIUM, EquipmentSlotType.FEET, (new Item.Properties()).func_200916_a(ItemGroup.field_78037_j)));

   public static RegistryObject<Item> ARMOUR_STEEL_HELMET = RegistryHandler.ITEMS.register("steel_helmet", () -> new ArmorItem(DMArmorMaterial.ArmorMaterial.STEEL, EquipmentSlotType.HEAD, (new Item.Properties()).func_200916_a(ItemGroup.field_78037_j)));
   public static RegistryObject<Item> ARMOUR_STEEL_CHESTPLATE = RegistryHandler.ITEMS.register("steel_chestplate", () -> new ArmorItem(DMArmorMaterial.ArmorMaterial.STEEL, EquipmentSlotType.CHEST, (new Item.Properties()).func_200916_a(ItemGroup.field_78037_j)));
   public static RegistryObject<Item> ARMOUR_STEEL_LEGGINGS = RegistryHandler.ITEMS.register("steel_leggings", () -> new ArmorItem(DMArmorMaterial.ArmorMaterial.STEEL, EquipmentSlotType.LEGS, (new Item.Properties()).func_200916_a(ItemGroup.field_78037_j)));
   public static RegistryObject<Item> ARMOUR_STEEL_BOOTS = RegistryHandler.ITEMS.register("steel_boots", () -> new ArmorItem(DMArmorMaterial.ArmorMaterial.STEEL, EquipmentSlotType.FEET, (new Item.Properties()).func_200916_a(ItemGroup.field_78037_j)));

   public static RegistryObject<Item> ARMOUR_METALERT_HELMET = RegistryHandler.ITEMS.register("metalert_helmet", () -> new ArmorItem(DMArmorMaterial.ArmorMaterial.METALERT, EquipmentSlotType.HEAD, (new Item.Properties()).func_200916_a(ItemGroup.field_78037_j)));
   public static RegistryObject<Item> ARMOUR_METALERT_CHESTPLATE = RegistryHandler.ITEMS.register("metalert_chestplate", () -> new ArmorItem(DMArmorMaterial.ArmorMaterial.METALERT, EquipmentSlotType.CHEST, (new Item.Properties()).func_200916_a(ItemGroup.field_78037_j)));
   public static RegistryObject<Item> ARMOUR_METALERT_LEGGINGS = RegistryHandler.ITEMS.register("metalert_leggings", () -> new ArmorItem(DMArmorMaterial.ArmorMaterial.METALERT, EquipmentSlotType.LEGS, (new Item.Properties()).func_200916_a(ItemGroup.field_78037_j)));
   public static RegistryObject<Item> ARMOUR_METALERT_BOOTS = RegistryHandler.ITEMS.register("metalert_boots", () -> new ArmorItem(DMArmorMaterial.ArmorMaterial.METALERT, EquipmentSlotType.FEET, (new Item.Properties()).func_200916_a(ItemGroup.field_78037_j)));



   public static RegistryObject<Item>[] DALEK_SPAWNER = intDalekSpawnItems();


  public static RegistryObject<Item>[] intDalekSpawnItems() {
     DALEK_SPAWNER = (RegistryObject<Item>[])new RegistryObject[DalekType.DALEK_TYPES.size()];
     int i = 0;
     for (DalekType type : DalekType.DALEK_TYPES) {
       List<String> dalekTypes = DMDalekRegistry.DALEK_TYPES.get(type);
       if (dalekTypes != null && dalekTypes.size() > 0) {
         DALEK_SPAWNER[i] = addSpawnItem("dalek", DMDalekRegistry.DALEK_TYPES.get(type), type.getRegistryName());
         i++;
      }
    }
     return DALEK_SPAWNER;
  }


   public static RegistryObject<Item> CYBERMAN_SPAWNER = addSpawnItem("cyberman");
   public static RegistryObject<Item> AUTON_SPAWNER = addSpawnItem("auton");
   public static RegistryObject<Item> WEEPING_ANGEL_SPAWNER = addSpawnItem("weeping_angel");
   public static RegistryObject<Item> CYBERVILLAGER_SPAWNER = addSpawnItem("cybervillager");
   public static RegistryObject<Item> CYBERMAT_SPAWNER = addSpawnItem("cybermat");
   public static RegistryObject<Item> CYBERDRONE_SPAWNER = addSpawnItem("cyberdrone");
   public static RegistryObject<Item> OOD_SPAWNER = addSpawnItem("ood");


  public static <T extends net.minecraft.entity.Entity> RegistryObject<Item> addSpawnItem(String key, List<String> types, String itemKey) {
     RegistryObject<Item> item = RegistryHandler.ITEMS.register(itemKey + "_spawner", () -> new SpawnerItem(key, types));
     return item;
  }

  public static <T extends net.minecraft.entity.Entity> RegistryObject<Item> addSpawnItem(String key, List<String> types) {
     return addSpawnItem(key, types, types.get(0));
  }

  public static <T extends net.minecraft.entity.Entity> RegistryObject<Item> addSpawnItem(String key, String type) {
     RegistryObject<Item> item = RegistryHandler.ITEMS.register(type + "_spawner", () -> new SpawnerItem(key, type));
     return item;
  }

  public static <T extends net.minecraft.entity.Entity> RegistryObject<Item> addSpawnItem(String key) {
     return addSpawnItem(key, key);
  }
}


