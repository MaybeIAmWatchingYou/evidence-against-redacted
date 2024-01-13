package com.swdteam.main.proxy;

import com.swdteam.client.data.ModelReloadListener;
import com.swdteam.client.data.Splashes;
import com.swdteam.client.event.ModelEvent;
import com.swdteam.client.init.BusClientEvents;
import com.swdteam.client.init.DMEntityRenderRegistry;
import com.swdteam.client.init.DMKeybinds;
import com.swdteam.client.init.DMTileEntityRenderRegistry;
import com.swdteam.client.init.ModClientEvents;
import com.swdteam.client.model.ModelReloaderRegistry;
import com.swdteam.client.overlay.OverlayDalekEyestalk;
import com.swdteam.client.overlay.OverlayDevelopment;
import com.swdteam.client.overlay.OverlayFlightMode;
import com.swdteam.client.overlay.OverlayGunChargeup;
import com.swdteam.client.overlay.OverlayInfdev;
import com.swdteam.client.overlay.OverlayInvExtras;
import com.swdteam.client.overlay.OverlayMCClassic;
import com.swdteam.client.overlay.OverlayOxygen;
import com.swdteam.client.overlay.OverlaySonicScrewdriver;
import com.swdteam.client.overlay.OverlayTooltip;
import com.swdteam.client.overlay.OverlayXPAmount;
import com.swdteam.client.render.RenderEvents;
import com.swdteam.client.tardis.data.ExteriorModels;
import com.swdteam.common.entity.dalek.IDalek;
import com.swdteam.common.init.DMBlocks;
import com.swdteam.common.init.DMContainer;
import com.swdteam.common.init.DMDalekRegistry;
import com.swdteam.model.javajson.JSONModel;
import com.swdteam.model.javajson.ModelLoader;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.resources.IFutureReloadListener;
import net.minecraft.resources.IReloadableResourceManager;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;







public class ClientProxy
  extends ServerProxy
{
  public void doClientStuff(FMLClientSetupEvent event) {
     Splashes.load();



     ScreenManager.func_216911_a((ContainerType)DMContainer.FAULT_LOCATOR_CONTAINER.get(), com.swdteam.client.gui.GuiFaultLocator::new);
     ScreenManager.func_216911_a((ContainerType)DMContainer.CRAFTING_CONTAINER.get(), com.swdteam.client.gui.GuiRoundelBuilder::new);
     ScreenManager.func_216911_a((ContainerType)DMContainer.ARS_CONTAINER.get(), com.swdteam.client.gui.GuiTardisInteriorBuilder::new);
     ScreenManager.func_216911_a((ContainerType)DMContainer.ARTRON_FUEL_CONTAINER.get(), com.swdteam.client.gui.GuiArtronFuelTank::new);
     ScreenManager.func_216911_a((ContainerType)DMContainer.ENGINEERING_TABLE_CONTAINER.get(), com.swdteam.client.gui.GuiEngineeringTable::new);
     ScreenManager.func_216911_a((ContainerType)DMContainer.KERBLAM_BOX_CONTAINER.get(), com.swdteam.client.gui.GuiKerblamBox::new);

     MinecraftForge.EVENT_BUS.register(new ModelEvent());
     DMKeybinds.init();
     MinecraftForge.EVENT_BUS.register(new RenderEvents());
     MinecraftForge.EVENT_BUS.register(BusClientEvents.class);
     FMLJavaModLoadingContext.get().getModEventBus().register(ModClientEvents.class);

     BusClientEvents.overlays.add(new OverlayInvExtras());
     BusClientEvents.overlays.add(new OverlayMCClassic());
     BusClientEvents.overlays.add(new OverlayDalekEyestalk());
     BusClientEvents.overlays.add(new OverlayFlightMode());
     BusClientEvents.overlays.add(new OverlayOxygen());
     BusClientEvents.overlays.add(new OverlayTooltip());
     BusClientEvents.overlays.add(new OverlayInfdev());
     BusClientEvents.overlays.add(new OverlaySonicScrewdriver());
     BusClientEvents.overlays.add(new OverlayXPAmount());
     BusClientEvents.overlays.add(new OverlayGunChargeup());
     BusClientEvents.overlays.add(new OverlayDevelopment());



     DMBlocks.registerRenderTypes();

     IResourceManager manager = Minecraft.func_71410_x().func_195551_G();
     if (manager instanceof IReloadableResourceManager) {
       ((IReloadableResourceManager)manager).func_219534_a((IFutureReloadListener)new ModelReloadListener());
    }

     BusClientEvents.CUBE = ModelLoader.loadModel(new ResourceLocation("dalekmod", "models/misc/cube.json"));

     registerReloadable();
  }



  public void postLoad() {}


  public static void registerReloadable() {
     ModelLoader.getCache().clear();
     ExteriorModels.ANIMATIONS.clear();
     DMTileEntityRenderRegistry.init();
     DMEntityRenderRegistry.registryEntityRenders();

     for (Map.Entry<String, IDalek> entry : (Iterable<Map.Entry<String, IDalek>>)DMDalekRegistry.getDaleks().entrySet()) {
       ((IDalek)entry.getValue()).setModel(ModelLoader.loadModel(new ResourceLocation("dalekmod", "models/entity/dalek/" + ((IDalek)entry.getValue()).getID().toLowerCase() + ".json")));
    }

     for (Map.Entry<ResourceLocation, JSONModel> entry : (Iterable<Map.Entry<ResourceLocation, JSONModel>>)ModelLoader.getCache().entrySet()) {
       ((JSONModel)entry.getValue()).load();
    }
     ModelReloaderRegistry.reloadModels();
  }

  @EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
  public static class ClientRegistryEvents {}

  @EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
  public static class ClientEventHandler {
    @SubscribeEvent
    public static void worldTickEvent(TickEvent.WorldTickEvent e) {}
  }
}


