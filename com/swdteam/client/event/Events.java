package com.swdteam.client.event;

import com.swdteam.client.gui.GuiTardisViewport;
import com.swdteam.client.gui.viewport.TardisViewportRenderer;
import com.swdteam.client.init.DMKeybinds;
import com.swdteam.common.init.DMDimensions;
import com.swdteam.common.init.DMItems;
import com.swdteam.common.init.DMParticleTypes;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.particles.IParticleData;
import net.minecraft.util.ScreenShotHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = "dalekmod", bus = Mod.EventBusSubscriber.Bus.FORGE, value = {Dist.CLIENT})
public class Events
{
   public static Map<String, NativeImage[]> images = (Map)new HashMap<>();
  public static Vector3d position;
   static String currentName = "";

  static NativeImage[] screenshots;
  static boolean takePanorama = false;
   public static int index = 0;


  @SubscribeEvent(priority = EventPriority.HIGHEST)
  public static void screenshotEvent(TickEvent.ClientTickEvent event) {
     if ((Minecraft.func_71410_x()).field_71441_e != null && Minecraft.func_71410_x().func_175606_aa() != null && DMKeybinds.CLASSIC_INVENTORY.func_151468_f()) {
       if ((Minecraft.func_71410_x()).field_71439_g.world.getDimensionKey() == DMDimensions.TARDIS) {
         Minecraft.func_71410_x().func_147108_a((Screen)new GuiTardisViewport());
      }
       else if (!takePanorama) {

      }
    }
  }









  @SubscribeEvent
  public static void playerRenderEvents(RenderPlayerEvent e) {
     if (e.getEntityLiving() != null && 
       e.getEntityLiving() instanceof PlayerEntity) {

       PlayerEntity ent = (PlayerEntity)e.getEntityLiving();
       if (ent != null)
      {
         if (ent.getItemStackFromSlot(EquipmentSlotType.FEET).getItem().equals(DMItems.ARMOUR_DALEKANIUM_BOOTS.get()))
        {
           for (int i = 0; i < 4; i++) {
             ent.world.addParticle((IParticleData)DMParticleTypes.BLUE_DALEK_HOVER.get(), ent.func_226282_d_(0.5D), ent.getPosY(), ent.func_226287_g_(0.5D), 0.3D, 0.3D, 0.3D);
          }
        }
      }
    }
  }



  public static void takeScreenshot(MainWindow window, int index) {
    int dimension, fromX;
     NativeImage image = ScreenShotHelper.func_198052_a(window.func_198105_m(), window.func_198083_n(), Minecraft.func_71410_x().func_147110_a());


     int imageWidth = image.func_195702_a();
     int imageHeight = image.func_195714_b();
     int fromY = 0;
     if (imageWidth > imageHeight) {
       dimension = imageHeight;
       fromX = imageWidth / 2 - dimension / 2;
    } else {
       dimension = imageWidth;
       fromX = imageWidth / 2 - dimension / 2;
    }
     NativeImage image2 = new NativeImage(dimension, dimension, true);
     image.func_195708_a(fromX, fromY, dimension, dimension, image2);

     screenshots[index] = image2;
  }

  @SubscribeEvent(priority = EventPriority.HIGHEST)
  public static void cameraEvent(EntityViewRenderEvent.CameraSetup event) {
     if (takePanorama && index < 6) {
       Facing f = Facing.getIndex(index);

       event.setYaw(f.yaw);
       event.setPitch(f.pitch);
       event.setRoll(0.0F);
    }
  }


  @SubscribeEvent(priority = EventPriority.HIGHEST)
  public static void fovEvent(EntityViewRenderEvent.FOVModifier event) {
     if (takePanorama) {
       event.setFOV(90.0D);
    }
  }

  @SubscribeEvent(priority = EventPriority.HIGHEST)
  public static void blockOverlayEvent(RenderBlockOverlayEvent event) {
     if (takePanorama) {
       event.setCanceled(true);
    }
  }

   static int timer = 0;


  @SubscribeEvent(priority = EventPriority.HIGHEST)
  public static void renderWorldEvent(RenderWorldLastEvent event) {
     Minecraft mc = Minecraft.func_71410_x();
     if (takePanorama) {
       if (timer >= 2) {
         timer = 0;
         if (index < 6) {
           MainWindow window = mc.func_228018_at_();
           takeScreenshot(window, index);
           index++;
        } else {
           takePanorama = false;
           TardisViewportRenderer.textures = new DynamicTexture[screenshots.length];
           for (int i = 0; i < screenshots.length; i++) {
             TardisViewportRenderer.textures[i] = new DynamicTexture(screenshots[i]);
          }
        }
      } else {
         timer++;
      }
    } else {
       timer = 0;
    }
  }

  @SubscribeEvent(priority = EventPriority.HIGHEST)
  public static void guiEvent(RenderHandEvent event) {
     if (takePanorama) {
       event.setCanceled(true);
    }
  }

  @SubscribeEvent(priority = EventPriority.HIGHEST)
  public static void guiEvent(RenderGameOverlayEvent event) {
     if (takePanorama)
       event.setCanceled(true); 
  }

  public enum Facing
  {
     SOUTH(0.0F, 0.0F),
     WEST(90.0F, 0.0F),
     NORTH(180.0F, 0.0F),
     EAST(-90.0F, 0.0F),

     UP(0.0F, -90.0F),
     DOWN(0.0F, 90.0F);
    float yaw;
    float pitch;

    Facing(float yaw, float pitch) {
       this.yaw = yaw;
       this.pitch = pitch;
    }

    public static Facing getIndex(int index) {
       return values()[index];
    }
  }
}


