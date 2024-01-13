package com.swdteam.client.init;

import com.swdteam.client.render.tileentity.RenderTardisDoorDouble;
import com.swdteam.client.render.tileentity.RenderTileEntityBase;
import com.swdteam.common.init.DMBlockEntities;
import com.swdteam.main.DalekMod;
import com.swdteam.model.javajson.JSONModel;
import com.swdteam.model.javajson.ModelJSONTile;
import com.swdteam.model.javajson.ModelLoader;
import java.util.function.Function;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.ClientRegistry;















public class DMTileEntityRenderRegistry
{
  public static void init() {
     DalekMod.LOGGER.info("Registering TileEntity Renders");
     registerModel((TileEntityType<TileEntity>)DMBlockEntities.TILE_LAMP_POST.get(), "lamp_post");
     registerModel((TileEntityType<TileEntity>)DMBlockEntities.TILE_LIGHT_BOX.get(), "light_box");
     registerModel((TileEntityType<TileEntity>)DMBlockEntities.TILE_POLICE_BOX_DOOR.get(), s -> new RenderTardisDoorDouble(s, "tardis_door"));
     registerModel((TileEntityType<TileEntity>)DMBlockEntities.TILE_SWD_STATUE.get(), "swd_statue");
     registerModel((TileEntityType<TileEntity>)DMBlockEntities.TILE_ANGEL_STATUE.get(), "angel_statue");
     registerModel((TileEntityType<TileEntity>)DMBlockEntities.TILE_ARTRON_FUEL_TANK.get(), com.swdteam.client.render.tileentity.RenderArtronTank::new);
     registerModel((TileEntityType<TileEntity>)DMBlockEntities.TILE_COORD_PANEL.get(), com.swdteam.client.render.tileentity.RenderCoordPanel::new);
     registerModel((TileEntityType<TileEntity>)DMBlockEntities.TILE_DIMENSION_SELECTOR.get(), com.swdteam.client.render.tileentity.RenderDimensionSelectorPanel::new);
     registerModel((TileEntityType<TileEntity>)DMBlockEntities.TILE_HOLOGRAM.get(), com.swdteam.client.render.tileentity.RenderHologram::new);
     registerModel((TileEntityType<TileEntity>)DMBlockEntities.TILE_SCANNER.get(), com.swdteam.client.render.tileentity.RenderScanner::new);
     registerModel((TileEntityType<TileEntity>)DMBlockEntities.TILE_WOODEN_SCANNER.get(), com.swdteam.client.render.tileentity.RenderWoodenScanner::new);
     registerModel((TileEntityType<TileEntity>)DMBlockEntities.TILE_TARDIS.get(), com.swdteam.client.render.tileentity.RenderBlockTardis::new);
     registerModel((TileEntityType<TileEntity>)DMBlockEntities.TILE_ROUNDEL_DOOR.get(), com.swdteam.client.render.tileentity.RenderRoundelDoor::new);
     registerModel((TileEntityType<TileEntity>)DMBlockEntities.TILE_TARDIS_PLANT.get(), com.swdteam.client.render.tileentity.RenderTardisPlant::new);
     registerModel((TileEntityType<TileEntity>)DMBlockEntities.TILE_MOZAIQUE.get(), com.swdteam.client.render.tileentity.RenderMozaique::new);
     registerModel((TileEntityType<TileEntity>)DMBlockEntities.TILE_IMAGE_LOADER.get(), com.swdteam.client.render.tileentity.RenderImgLoader::new);
     registerModel((TileEntityType<TileEntity>)DMBlockEntities.TILE_LIGHT_ALTERNATOR.get(), com.swdteam.client.render.tileentity.RenderLightAlternator::new);
  }


  
  public static <T extends TileEntity> void registerModel(TileEntityType<T> tileEntityType, Function<? super TileEntityRendererDispatcher, ? extends TileEntityRenderer<? super T>> rendererFactory) {
     ClientRegistry.bindTileEntityRenderer(tileEntityType, rendererFactory);
  }
  
  public static <T extends TileEntity> void registerModel(TileEntityType<T> tileEntityType, String location) {
     JSONModel model = ModelLoader.loadModel(new ResourceLocation("dalekmod", "models/tileentity/" + location + ".json"));
     if (model != null) registerModel(tileEntityType, s -> new RenderTileEntityBase(s, ModelJSONTile.giveWrapper(location)));
  }
}


