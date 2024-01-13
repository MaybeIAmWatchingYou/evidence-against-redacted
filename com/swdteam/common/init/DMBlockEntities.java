package com.swdteam.common.init;
import com.swdteam.common.RegistryHandler;
import com.swdteam.common.tileentity.AngelStatueTileEntity;
import com.swdteam.common.tileentity.ArtronFuelTankTileEntity;
import com.swdteam.common.tileentity.BetaChestTileEntity;
import com.swdteam.common.tileentity.ExplosiveDeviceTileEntity;
import com.swdteam.common.tileentity.ForceFieldFloorTileEntity;
import com.swdteam.common.tileentity.HologramTileEntity;
import com.swdteam.common.tileentity.ImageLoaderTileEntity;
import com.swdteam.common.tileentity.KerblamBoxTileEntity;
import com.swdteam.common.tileentity.LampPostTileEntity;
import com.swdteam.common.tileentity.LightBoxTileEntity;
import com.swdteam.common.tileentity.MozaiqueTileEntity;
import com.swdteam.common.tileentity.Nitro9TileEntity;
import com.swdteam.common.tileentity.SWDStatueTileEntity;
import com.swdteam.common.tileentity.ScannerTileEntity;
import com.swdteam.common.tileentity.StorageVaultTileEntity;
import com.swdteam.common.tileentity.TardisTileEntity;
import com.swdteam.common.tileentity.TeleportPadTileEntity;
import com.swdteam.common.tileentity.WoodenScannerTileEntity;
import com.swdteam.common.tileentity.tardis.CoordPanelTileEntity;
import com.swdteam.common.tileentity.tardis.DataWriterTileEntity;
import com.swdteam.common.tileentity.tardis.DimensionSelectorTileEntity;
import com.swdteam.common.tileentity.tardis.FaultLocatorTileEntity;
import com.swdteam.common.tileentity.tardis.LightAlternatorTileEntity;
import com.swdteam.common.tileentity.tardis.PoliceBoxDoorsTileEntity;
import com.swdteam.common.tileentity.tardis.RoundelDoorTileEntity;
import com.swdteam.common.tileentity.tardis.SonicInterfaceTileEntity;
import com.swdteam.common.tileentity.tardis.TardisDoorHitboxTileEntity;
import com.swdteam.common.tileentity.tardis.TardisPlantTileEntity;
import com.swdteam.common.tileentity.tardis.WaypointPanelTileEntity;
import java.util.function.Supplier;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = "dalekmod", bus = Mod.EventBusSubscriber.Bus.MOD)
public class DMBlockEntities {
   public static final RegistryObject<TileEntityType<AngelStatueTileEntity>> TILE_ANGEL_STATUE = RegistryHandler.TILE_ENTITY_TYPES.register("angel_statue", () -> TileEntityType.Builder.func_223042_a(AngelStatueTileEntity::new, new Block[] { (Block)DMBlocks.ANGEL_STATUE.get() }).func_206865_a(null));
   public static final RegistryObject<TileEntityType<ArtronFuelTankTileEntity>> TILE_ARTRON_FUEL_TANK = RegistryHandler.TILE_ENTITY_TYPES.register("artron_fuel_tank", () -> TileEntityType.Builder.func_223042_a(ArtronFuelTankTileEntity::new, new Block[] { (Block)DMBlocks.ARTRON_FUEL_TANK.get() }).func_206865_a(null));
   public static final RegistryObject<TileEntityType<BetaChestTileEntity>> TILE_BETA_CHEST = RegistryHandler.TILE_ENTITY_TYPES.register("beta_chest", () -> TileEntityType.Builder.func_223042_a(BetaChestTileEntity::new, new Block[] { (Block)DMBlocks.BETA_CHEST.get() }).func_206865_a(null));
   public static final RegistryObject<TileEntityType<CoordPanelTileEntity>> TILE_COORD_PANEL = RegistryHandler.TILE_ENTITY_TYPES.register("coord_panel", () -> TileEntityType.Builder.func_223042_a(CoordPanelTileEntity::new, new Block[] { (Block)DMBlocks.COORD_PANEL.get() }).func_206865_a(null));
   public static final RegistryObject<TileEntityType<DimensionSelectorTileEntity>> TILE_DIMENSION_SELECTOR = RegistryHandler.TILE_ENTITY_TYPES.register("dimension_selector", () -> TileEntityType.Builder.func_223042_a(DimensionSelectorTileEntity::new, new Block[] { (Block)DMBlocks.DIMENSION_SELECTOR_PANEL.get() }).func_206865_a(null));
   public static final RegistryObject<TileEntityType<ExplosiveDeviceTileEntity>> TILE_EXPLOSIVE_DEVICE = RegistryHandler.TILE_ENTITY_TYPES.register("explosive_device", () -> TileEntityType.Builder.func_223042_a(ExplosiveDeviceTileEntity::new, new Block[] { (Block)DMBlocks.EXPLOSIVE_DEVICE.get() }).func_206865_a(null));
   public static final RegistryObject<TileEntityType<FaultLocatorTileEntity>> TILE_FAULT_LOCATOR = RegistryHandler.TILE_ENTITY_TYPES.register("fault_locator", () -> TileEntityType.Builder.func_223042_a(FaultLocatorTileEntity::new, new Block[] { (Block)DMBlocks.FAULT_LOCATOR.get() }).func_206865_a(null));
   public static final RegistryObject<TileEntityType<HologramTileEntity>> TILE_HOLOGRAM = RegistryHandler.TILE_ENTITY_TYPES.register("hologram", () -> TileEntityType.Builder.func_223042_a(HologramTileEntity::new, new Block[] { (Block)DMBlocks.HOLOGRAM.get() }).func_206865_a(null));
   public static final RegistryObject<TileEntityType<LampPostTileEntity>> TILE_LAMP_POST = RegistryHandler.TILE_ENTITY_TYPES.register("lamp_post", () -> TileEntityType.Builder.func_223042_a(LampPostTileEntity::new, new Block[] { (Block)DMBlocks.LAMP_POST.get() }).func_206865_a(null));
   public static final RegistryObject<TileEntityType<LightBoxTileEntity>> TILE_LIGHT_BOX = RegistryHandler.TILE_ENTITY_TYPES.register("light_box", () -> TileEntityType.Builder.func_223042_a(LightBoxTileEntity::new, new Block[] { (Block)DMBlocks.LIGHT_BOX.get() }).func_206865_a(null));
   public static final RegistryObject<TileEntityType<Nitro9TileEntity>> TILE_NITRO_9 = RegistryHandler.TILE_ENTITY_TYPES.register("nitro_9", () -> TileEntityType.Builder.func_223042_a(Nitro9TileEntity::new, new Block[] { (Block)DMBlocks.NITRO9.get() }).func_206865_a(null));
   public static final RegistryObject<TileEntityType<PoliceBoxDoorsTileEntity>> TILE_POLICE_BOX_DOOR = RegistryHandler.TILE_ENTITY_TYPES.register("police_box_door", () -> TileEntityType.Builder.func_223042_a(PoliceBoxDoorsTileEntity::new, new Block[] { (Block)DMBlocks.POLICE_BOX_DOOR.get() }).func_206865_a(null));
   public static final RegistryObject<TileEntityType<ScannerTileEntity>> TILE_SCANNER = RegistryHandler.TILE_ENTITY_TYPES.register("scanner", () -> TileEntityType.Builder.func_223042_a(ScannerTileEntity::new, new Block[] { (Block)DMBlocks.SCANNER.get() }).func_206865_a(null));
   public static final RegistryObject<TileEntityType<WoodenScannerTileEntity>> TILE_WOODEN_SCANNER = RegistryHandler.TILE_ENTITY_TYPES.register("wooden_scanner", () -> TileEntityType.Builder.func_223042_a(WoodenScannerTileEntity::new, new Block[] { (Block)DMBlocks.WOODEN_SCANNER.get() }).func_206865_a(null));
   public static final RegistryObject<TileEntityType<SonicInterfaceTileEntity>> TILE_SONIC_INTERFACE = RegistryHandler.TILE_ENTITY_TYPES.register("sonic_interface", () -> TileEntityType.Builder.func_223042_a(SonicInterfaceTileEntity::new, new Block[] { (Block)DMBlocks.SONIC_INTERFACE.get() }).func_206865_a(null));
   public static final RegistryObject<TileEntityType<StorageVaultTileEntity>> TILE_STORAGE_VAULT = RegistryHandler.TILE_ENTITY_TYPES.register("storage_vault", () -> TileEntityType.Builder.func_223042_a(StorageVaultTileEntity::new, new Block[] { (Block)DMBlocks.STORAGE_VAULT.get() }).func_206865_a(null));
   public static final RegistryObject<TileEntityType<SWDStatueTileEntity>> TILE_SWD_STATUE = RegistryHandler.TILE_ENTITY_TYPES.register("swd_statue", () -> TileEntityType.Builder.func_223042_a(SWDStatueTileEntity::new, new Block[] { (Block)DMBlocks.SWD_STATUE.get() }).func_206865_a(null));
   public static final RegistryObject<TileEntityType<DataWriterTileEntity>> TILE_DATA_WRITER = RegistryHandler.TILE_ENTITY_TYPES.register("data_writer", () -> TileEntityType.Builder.func_223042_a(DataWriterTileEntity::new, new Block[] { (Block)DMBlocks.DATA_WRITER.get() }).func_206865_a(null));
   public static final RegistryObject<TileEntityType<WaypointPanelTileEntity>> TILE_WAYPOINT_PANEL = RegistryHandler.TILE_ENTITY_TYPES.register("waypoint_panel", () -> TileEntityType.Builder.func_223042_a(WaypointPanelTileEntity::new, new Block[] { (Block)DMBlocks.WAYPOINT_PANEL.get() }).func_206865_a(null));
   public static final RegistryObject<TileEntityType<TardisTileEntity>> TILE_TARDIS = RegistryHandler.TILE_ENTITY_TYPES.register("tardis", () -> TileEntityType.Builder.func_223042_a(TardisTileEntity::new, new Block[] { (Block)DMBlocks.TARDIS.get() }).func_206865_a(null));
   public static final RegistryObject<TileEntityType<TardisDoorHitboxTileEntity>> TILE_TARDIS_DOOR_HITBOX = RegistryHandler.TILE_ENTITY_TYPES.register("tardis_door_hitbox", () -> TileEntityType.Builder.func_223042_a(TardisDoorHitboxTileEntity::new, new Block[] { (Block)DMBlocks.TARDIS_DOOR_HITBOX.get() }).func_206865_a(null));
   public static final RegistryObject<TileEntityType<RoundelDoorTileEntity>> TILE_ROUNDEL_DOOR = RegistryHandler.TILE_ENTITY_TYPES.register("roundel_door", () -> TileEntityType.Builder.func_223042_a(RoundelDoorTileEntity::new, new Block[] { (Block)DMBlocks.ROUNDEL_DOOR.get() }).func_206865_a(null));
   public static final RegistryObject<TileEntityType<TardisPlantTileEntity>> TILE_TARDIS_PLANT = RegistryHandler.TILE_ENTITY_TYPES.register("tardis_plant", () -> TileEntityType.Builder.func_223042_a(TardisPlantTileEntity::new, new Block[] { (Block)DMBlocks.TARDIS_PLANT.get() }).func_206865_a(null));
   public static final RegistryObject<TileEntityType<MozaiqueTileEntity>> TILE_MOZAIQUE = RegistryHandler.TILE_ENTITY_TYPES.register("mozaique", () -> TileEntityType.Builder.func_223042_a(MozaiqueTileEntity::new, new Block[] { (Block)DMBlocks.MOZAIQUE.get() }).func_206865_a(null));
   public static final RegistryObject<TileEntityType<TeleportPadTileEntity>> TILE_TELEPORT_PAD = RegistryHandler.TILE_ENTITY_TYPES.register("teleport_pad", () -> TileEntityType.Builder.func_223042_a(TeleportPadTileEntity::new, new Block[] { (Block)DMBlocks.TELEPORT_PAD.get() }).func_206865_a(null));
   public static final RegistryObject<TileEntityType<KerblamBoxTileEntity>> TILE_KERBLAM_BOX = RegistryHandler.TILE_ENTITY_TYPES.register("kerblam_box", () -> TileEntityType.Builder.func_223042_a(KerblamBoxTileEntity::new, new Block[] { (Block)DMBlocks.KERBLAM_BOX.get() }).func_206865_a(null));
   public static final RegistryObject<TileEntityType<ForceFieldFloorTileEntity>> TILE_FORCE_FIELD = RegistryHandler.TILE_ENTITY_TYPES.register("force_field_floor", () -> TileEntityType.Builder.func_223042_a(ForceFieldFloorTileEntity::new, new Block[] { (Block)DMBlocks.FORCE_FIELD.get() }).func_206865_a(null));
   public static final RegistryObject<TileEntityType<LightAlternatorTileEntity>> TILE_LIGHT_ALTERNATOR = RegistryHandler.TILE_ENTITY_TYPES.register("light_alternator", () -> TileEntityType.Builder.func_223042_a(LightAlternatorTileEntity::new, new Block[] { (Block)DMBlocks.LIGHT_ALTERNATOR.get() }).func_206865_a(null));











   public static final RegistryObject<TileEntityType<ImageLoaderTileEntity>> TILE_IMAGE_LOADER = RegistryHandler.TILE_ENTITY_TYPES.register("image_loader", () -> TileEntityType.Builder.func_223042_a(ImageLoaderTileEntity::new, new Block[] { (Block)DMBlocks.IMAGE_LOADER.get() }).func_206865_a(null));
}


