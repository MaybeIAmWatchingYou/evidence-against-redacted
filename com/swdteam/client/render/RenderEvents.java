package com.swdteam.client.render;

import com.swdteam.client.render.entity.layer.RenderLayerCharged;
import com.swdteam.common.init.DMDimensions;
import com.swdteam.common.init.DMFlightMode;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber({Dist.CLIENT})
public class RenderEvents {
  private static boolean skinLayersSetup = false;

  @SubscribeEvent
  public static void RenderPlayerEvent(RenderPlayerEvent.Pre e) {
     if (!skinLayersSetup) {
       e.getRenderer().func_177094_a((LayerRenderer)new RenderLayerCharged((IEntityRenderer)e.getRenderer()));
       skinLayersSetup = true;
    }
  }


  @SubscribeEvent
  public static void renderGameOverlay(RenderGameOverlayEvent e) {
     if ((Minecraft.func_71410_x()).field_71439_g != null && (Minecraft.func_71410_x()).field_71439_g.world.getDimensionKey().equals(DMDimensions.CLASSIC) && (
       e.getType() == RenderGameOverlayEvent.ElementType.JUMPBAR || e.getType() == RenderGameOverlayEvent.ElementType.HEALTHMOUNT || e.getType() == RenderGameOverlayEvent.ElementType.AIR || e.getType() == RenderGameOverlayEvent.ElementType.HEALTH || e.getType() == RenderGameOverlayEvent.ElementType.ARMOR || e.getType() == RenderGameOverlayEvent.ElementType.FOOD || e.getType() == RenderGameOverlayEvent.ElementType.EXPERIENCE)) {
       e.setCanceled(true);
    }


     if (DMFlightMode.isInFlight((PlayerEntity)(Minecraft.func_71410_x()).field_71439_g) && (
       e.getType() == RenderGameOverlayEvent.ElementType.HOTBAR || e.getType() == RenderGameOverlayEvent.ElementType.JUMPBAR || e.getType() == RenderGameOverlayEvent.ElementType.HEALTHMOUNT || e.getType() == RenderGameOverlayEvent.ElementType.AIR || e.getType() == RenderGameOverlayEvent.ElementType.HEALTH || e.getType() == RenderGameOverlayEvent.ElementType.ARMOR || e.getType() == RenderGameOverlayEvent.ElementType.FOOD || e.getType() == RenderGameOverlayEvent.ElementType.EXPERIENCE)) {
       e.setCanceled(true);
    }
  }





  @SubscribeEvent
  public static void renderGUIEvent(RenderGameOverlayEvent.Pre e) {
     if ((Minecraft.func_71410_x()).field_71439_g.getEntityWorld().getDimensionKey().equals(DMDimensions.INFDEV)) {

       if (e.getType().equals(RenderGameOverlayEvent.ElementType.FOOD)) {
         e.setCanceled(true);
      }
       if (e.getType().equals(RenderGameOverlayEvent.ElementType.EXPERIENCE)) {
         e.setCanceled(true);
      }
       if (e.getType().equals(RenderGameOverlayEvent.ElementType.HEALTH)) {
         e.setCanceled(true);
      }
       if (e.getType().equals(RenderGameOverlayEvent.ElementType.ARMOR))
         e.setCanceled(true);
    }
  }
}


