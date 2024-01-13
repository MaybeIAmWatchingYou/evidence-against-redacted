package com.swdteam.client.init;

import com.mojang.blaze3d.systems.RenderSystem;
import com.swdteam.client.data.Splashes;
import com.swdteam.client.dimension.sky.SkyRendererCaveGame;
import com.swdteam.client.dimension.sky.SkyRendererMCClassic;
import com.swdteam.client.gui.GuiClassicInventory;
import com.swdteam.client.gui.GuiDMU;
import com.swdteam.client.gui.title.RenderPanorama;
import com.swdteam.client.overlay.Overlay;
import com.swdteam.client.render.RenderFlightMode;
import com.swdteam.client.tardis.data.ClientTardisCache;
import com.swdteam.client.tardis.data.ClientTardisFlightCache;
import com.swdteam.common.init.DMBlocks;
import com.swdteam.common.init.DMDimensions;
import com.swdteam.common.init.DMFlightMode;
import com.swdteam.common.init.DMSonicRegistry;
import com.swdteam.common.init.DMSoundEvents;
import com.swdteam.main.DMConfig;
import com.swdteam.main.DalekMod;
import com.swdteam.model.javajson.JSONModel;
import com.swdteam.network.NetworkHandler;
import com.swdteam.network.packets.PacketClickTardisDoors;
import com.swdteam.network.packets.PacketLandTardisFlightMode;
import com.swdteam.network.packets.PacketSonicInteraction;
import com.swdteam.util.helpers.ReflectionHelper;
import java.util.ArrayList;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.RenderSkybox;
import net.minecraft.client.renderer.RenderSkyboxCube;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldVertexBufferUploader;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.settings.AmbientOcclusionStatus;
import net.minecraft.client.settings.GraphicsFanciness;
import net.minecraft.client.settings.PointOfView;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ISkyRenderHandler;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.client.event.sound.SoundEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;





@EventBusSubscriber(modid = "dalekmod", bus = Mod.EventBusSubscriber.Bus.FORGE, value = {Dist.CLIENT})
public class BusClientEvents
{
  public static boolean snapped = false;
  public static JSONModel CUBE;
   public static AmbientOcclusionStatus OLD_AO = null;
   public static GraphicsFanciness OLD_GF = null;

   public static ArrayList<Overlay> overlays = new ArrayList<>();

   static int flightModeTimer = 0;
  private static ISound sound;

  @SubscribeEvent
  public static void clientTick(LivingEvent event) {
     if (event.getEntity() == (Minecraft.func_71410_x()).field_71439_g && (Minecraft.func_71410_x()).field_71439_g != null) {

       if (DMFlightMode.isInFlight((PlayerEntity)(Minecraft.func_71410_x()).field_71439_g)) {
         (Minecraft.func_71410_x()).field_71474_y.func_243229_a(PointOfView.THIRD_PERSON_BACK);
         if (event.getEntity().func_213453_ef() && event.getEntity().isOnGround()) {
           flightModeTimer++;
           if (flightModeTimer > 5) {
             (Minecraft.func_71410_x()).field_71474_y.func_243229_a(PointOfView.FIRST_PERSON);
             NetworkHandler.sendServerPacket(new PacketLandTardisFlightMode(((Float)RenderFlightMode.ROTATIONS.get((Minecraft.func_71410_x()).field_71439_g.getGameProfile().getId())).floatValue() % 360.0F));

             flightModeTimer = 0;
          }
        }
      }

       BlockPos pos = new BlockPos(event.getEntity().getPosX(), (Minecraft.func_71410_x()).field_71439_g.func_226280_cw_(), event.getEntity().getPosZ());

       if ((Minecraft.func_71410_x()).field_71441_e.getBlockState(pos) == ((Block)DMBlocks.CLASSIC_LAVA.get()).getDefaultState()) {
         ModClientEvents.ClientPlayerData.inCustomLava = true;
      } else {
         ModClientEvents.ClientPlayerData.inCustomLava = false;
      }
    }
  }



  @SubscribeEvent
  public static void playerJoinIWorld(ClientPlayerNetworkEvent.LoggedInEvent e) {
     DMFlightMode.clearClient();
  }


  @SubscribeEvent
  public static void playerJoinIWorld(ClientPlayerNetworkEvent.LoggedOutEvent e) {
     DMFlightMode.clearClient();
  }


  @SubscribeEvent
  public static void playerJoinIWorld(EntityJoinWorldEvent e) {
     if (e.getEntity() instanceof PlayerEntity && 
       e.getWorld() != null) {
       if (e.getWorld().getDimensionKey().equals(DMDimensions.CLASSIC) || e.getWorld().getDimensionKey().equals(DMDimensions.INFDEV)) {
         OLD_AO = (Minecraft.func_71410_x()).field_71474_y.field_74348_k;
         OLD_GF = (Minecraft.func_71410_x()).field_71474_y.field_238330_f_;
      } else {
         if (OLD_AO != null) {
           (Minecraft.func_71410_x()).field_71474_y.field_74348_k = OLD_AO;
        }
         if (OLD_GF != null) {
           (Minecraft.func_71410_x()).field_71474_y.field_238330_f_ = OLD_GF;
        }
      }
    }


     if (e.getEntity() == (Minecraft.func_71410_x()).field_71439_g) {
       FakeWorldHandler.init();
    }
  }









  @SubscribeEvent
  public static void skyRenderer(RenderWorldLastEvent event) {
     if ((Minecraft.func_71410_x()).field_71441_e.getDimensionKey().equals(DMDimensions.CLASSIC)) {
       ClientWorld world = (Minecraft.func_71410_x()).field_71441_e;
       if (world.func_239132_a_().getSkyRenderHandler() == null) {
         world.func_239132_a_().setSkyRenderHandler((ISkyRenderHandler)SkyRendererMCClassic.INSTANCE);
      }
     } else if ((Minecraft.func_71410_x()).field_71441_e.getDimensionKey().equals(DMDimensions.CAVE_GAME)) {
       ClientWorld world = (Minecraft.func_71410_x()).field_71441_e;
       if (world.func_239132_a_().getSkyRenderHandler() == null) {
         world.func_239132_a_().setSkyRenderHandler((ISkyRenderHandler)SkyRendererCaveGame.INSTANCE);
      }
    } else {
       ClientWorld world = (Minecraft.func_71410_x()).field_71441_e;
       if (world.func_239132_a_().getSkyRenderHandler() != null) {
         world.func_239132_a_().setSkyRenderHandler(null);
      }
    }
  }



  @SubscribeEvent
  public static void skyRenderer(RenderPlayerEvent event) {}


  @SubscribeEvent
  public static void blockOverlay(EntityViewRenderEvent.FogColors e) {
     if ((Minecraft.func_71410_x()).field_71441_e.getDimensionKey().equals(DMDimensions.CLASSIC)) {
       e.setBlue(1.0F);
       e.setGreen(1.0F);
       e.setRed(1.0F);
    }

     if (ModClientEvents.ClientPlayerData.inCustomLava) {
       e.setRed(0.9F);
       e.setGreen(0.3F);
       e.setBlue(0.05F);
    }
  }



  @SubscribeEvent
  public static void blockOverlay(EntityViewRenderEvent.FogDensity e) {
     if ((Minecraft.func_71410_x()).field_71441_e.getDimensionKey().equals(DMDimensions.CLASSIC)) {
       e.setDensity(0.025F);
       e.setCanceled(true);
    }

     if ((Minecraft.func_71410_x()).field_71441_e.getDimensionKey().equals(DMDimensions.CAVE_GAME)) {
       e.setDensity(0.0F);
       e.setCanceled(true);
    }

     if (ModClientEvents.ClientPlayerData.inCustomLava) {
       e.setDensity(0.5F);
       e.setCanceled(true);
    }
  }


  @SubscribeEvent
  public static void uiRenderEvent(RenderGameOverlayEvent.Text event) {
     for (Overlay overlay : overlays) {
       overlay.render(event.getMatrixStack());
    }
  }

  @SubscribeEvent
  public static void uiTickEvent(TickEvent.ClientTickEvent event) {
     for (Overlay overlay : overlays) {
       overlay.tick();
    }
  }



  @SubscribeEvent
  public static void musicEvent(PlaySoundEvent event) {
     Minecraft mc = Minecraft.func_71410_x();
     String name = event.getName().toLowerCase();

     if (mc.field_71439_g == null && name.equals("music.menu") && ((Boolean)DMConfig.CLIENT.customTitleScreen.get()).booleanValue()) event.setResultSound(null);

  }


  @SubscribeEvent
  public static void musicEvent(SoundEvent event) {}


  @SubscribeEvent
  public static void clientTick(TickEvent.ClientTickEvent event) {
     Minecraft mc = Minecraft.func_71410_x();

     if (DMKeybinds.CLASSIC_INVENTORY.func_151468_f()) {
       mc.field_71474_y.field_74333_Y = 9.0D;
    }

     if (DMKeybinds.CLASSIC_INVENTORY.func_151468_f() && mc.field_71439_g != null && mc.field_71462_r == null && mc.field_71441_e


       .getDimensionKey().equals(DMDimensions.CLASSIC) && mc.field_71439_g
       .isCreative()) {
       mc.func_147108_a((Screen)new GuiClassicInventory());
    }


     if (DMKeybinds.FINGER_CLICK.func_151468_f() && !snapped) {
       snapped = true;
       NetworkHandler.sendServerPacket(new PacketClickTardisDoors());
    }
  }




  @SubscribeEvent
  public static void playSoundEvent(PlaySoundEvent e) {
     if ((Minecraft.func_71410_x()).field_71441_e != null && (
       (Minecraft.func_71410_x()).field_71441_e.getDimensionKey().equals(DMDimensions.CLASSIC) || (Minecraft.func_71410_x()).field_71441_e.getDimensionKey().equals(DMDimensions.INFDEV))) {
       if (isSound(e, SoundEvents.field_187539_bB)) {
         replaceSound(e, (SoundEvent)DMSoundEvents.MISC_CLASSIC_EXPLOSION.get(), SoundCategory.BLOCKS);
      }

       if (isSound(e, SoundEvents.field_187579_bV)) {
         replaceSound(e, (SoundEvent)DMSoundEvents.BLOCK_CLASSIC_GRASS.get(), SoundCategory.BLOCKS);
      }

       if (isSound(e, SoundEvents.field_187668_ca)) {
         replaceSound(e, (SoundEvent)DMSoundEvents.BLOCK_CLASSIC_GRAVEL.get(), SoundCategory.BLOCKS);
      }

       if (isSound(e, SoundEvents.field_187902_gb)) {
         replaceSound(e, (SoundEvent)DMSoundEvents.BLOCK_CLASSIC_STONE.get(), SoundCategory.BLOCKS);
      }

       if (isSound(e, SoundEvents.field_187897_gY)) {
         replaceSound(e, (SoundEvent)DMSoundEvents.BLOCK_CLASSIC_WOOD.get(), SoundCategory.BLOCKS);
      }

       if (isSound(e, SoundEvents.field_187800_eb)) {
         replaceSound(e, (SoundEvent)DMSoundEvents.PLAYER_CLASSIC_HURT.get(), SoundCategory.PLAYERS);
      }
    }
  }


  public static boolean isSound(PlaySoundEvent e, SoundEvent sound) {
     return e.getName().equals(sound.func_187503_a().getPath());
  }


  public static void replaceSound(PlaySoundEvent e, SoundEvent sound, SoundCategory c) {
     float x = (float)e.getSound().func_147649_g();
     float y = (float)e.getSound().func_147654_h();
     float z = (float)e.getSound().func_147651_i();
     e.setResultSound(null);

     (Minecraft.func_71410_x()).field_71441_e.func_184134_a(x, y, z, sound, c, 1.0F, 1.0F, false);
  }

   public static int sonicTimer = 0;


  public static boolean sonicExecuted = false;


  @SubscribeEvent
  public static void onHarvest(PlayerInteractEvent.RightClickBlock event) {
     if ((event.getWorld()).isRemote && 
       event.getItemStack().getItem() instanceof com.swdteam.common.item.sonics.SonicScrewdriverCustomizedItem) {
       BlockState state = event.getWorld().getBlockState(event.getPos());

       if (DMSonicRegistry.SONIC_LOOKUP.containsKey(state.getBlock())) {
         DMSonicRegistry.ISonicInteraction sonic = (DMSonicRegistry.ISonicInteraction)DMSonicRegistry.SONIC_LOOKUP.get(state.getBlock());
         sonicTimer++;
         if (sonicTimer > sonic.scanTime()) {
           sonicTimer = sonic.scanTime();
           if (!sonicExecuted) {
             NetworkHandler.sendServerPacket(new PacketSonicInteraction(event.getPos(), null, event.getHand()));
             sonicExecuted = true;
          }
        }
      }
    }
  }



  @SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
  public static void onEvent(InputEvent.MouseInputEvent event) {
     if (event.getAction() == 0) {
       sonicTimer = 0;
       sonicExecuted = false;
    }
  }


  @SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
  public static void onEvent(InputEvent.KeyInputEvent event) {
     if (event.getAction() == 0 && event.getKey() == DMKeybinds.FINGER_CLICK.getKey().func_197937_c()) {
       snapped = false;
    }
  }

























  private static boolean guiInit = false;
























   private static ResourceLocation BLANK = new ResourceLocation("dalekmod", "textures/gui/blank.png");
   private static String SPLASH = "";


  @SubscribeEvent(priority = EventPriority.HIGHEST)
  public static void guiEvent(GuiOpenEvent event) {
     if (event.getGui() instanceof MainMenuScreen && (
       (Boolean)DMConfig.CLIENT.customTitleScreen.get()).booleanValue()) {
       (Minecraft.func_71410_x().func_213269_at()).field_215280_c.clear();

       MainMenuScreen gui = (MainMenuScreen)event.getGui();
       MainMenuScreen.field_194400_H = BLANK;
       MainMenuScreen.field_110352_y = BLANK;

       String[] splashes = Splashes.SPLASHES;

       if (splashes != null && splashes.length > 0) {
         SPLASH = splashes[DalekMod.RANDOM.nextInt(splashes.length)];
         if (DalekMod.RANDOM.nextInt(3000) == 0) SPLASH = "Bug secretly did all the work"; 
      } else {
         SPLASH = "Dalek Mod since 2012";
      }

       if (!guiInit) {
         if (!ModList.get().isLoaded("panorama")) {
           MainMenuScreen.field_213098_a = (RenderSkyboxCube)new RenderPanorama();
           gui.field_209101_K = new RenderSkybox(MainMenuScreen.field_213098_a);
        }
         guiInit = true;
      }
    }
  }




  @SubscribeEvent(priority = EventPriority.HIGHEST)
  public static void guiEvent(GuiScreenEvent.InitGuiEvent event) {
     if (event.getGui() instanceof MainMenuScreen && (
       (Boolean)DMConfig.CLIENT.customTitleScreen.get()).booleanValue()) {
       MainMenuScreen gui = (MainMenuScreen)event.getGui();

       ReflectionHelper.setValuePrivateDeclaredField("splash", MainMenuScreen.class, gui, null);
       ReflectionHelper.setValuePrivateDeclaredField("field_73975_c", MainMenuScreen.class, gui, null);

       for (int i = 0; i < gui.field_230710_m_.size(); i++) {
         Widget w = gui.field_230710_m_.get(i);
         if (w.func_230458_i_() instanceof TranslationTextComponent && (
           (TranslationTextComponent)w.func_230458_i_()).func_150268_i().equalsIgnoreCase("menu.multiplayer")) {
           w.func_230991_b_(98);

           Button b = new Button(gui.field_230708_k_ / 2 + 2, gui.field_230709_l_ / 4 + 72, 98, 20, (ITextComponent)new StringTextComponent("Dalek Mod Server"), button -> Minecraft.func_71410_x().func_147108_a((Screen)new GuiDMU((Screen)gui)));



           gui.field_230705_e_.add(b);
           gui.field_230710_m_.add(b);
        }
      }


       if (sound == null) {
         sound = (ISound)SimpleSound.func_184370_a((SoundEvent)DMSoundEvents.MUSIC_TITLE_SCREEN.get());
      }

       if (sound != null && 
         !Minecraft.func_71410_x().func_147118_V().func_215294_c(sound)) {
         Minecraft.func_71410_x().func_147118_V().func_147682_a(sound);
      }
    }
  }



  @SubscribeEvent
  public static void renderEvent(GuiScreenEvent.DrawScreenEvent.Post e) {
     RenderPanorama.time = (float)(RenderPanorama.time + e.getRenderPartialTicks() * 0.1D);
     RenderPanorama.time %= 360.0F;

     if (e.getGui() instanceof MainMenuScreen && ((Boolean)DMConfig.CLIENT.customTitleScreen.get()).booleanValue() && 
       guiInit) {
       Minecraft mc = Minecraft.func_71410_x();
       mc.field_71446_o.func_110577_a(new ResourceLocation("dalekmod", "textures/gui/title_logo.png"));
       drawModalRectWithCustomSizedTexture((e.getGui()).field_230708_k_ / 2 - 161, 28, 0.0F, 0.0F, 322, 44, 322.0F, 44.0F);
       mc.field_71446_o.func_110577_a(new ResourceLocation("dalekmod", "textures/gui/update_name.png"));
       drawModalRectWithCustomSizedTexture((e.getGui()).field_230708_k_ / 2 - 64, 66, 0.0F, 0.0F, 128, 16, 128.0F, 16.0F);

       RenderSystem.pushMatrix();
       RenderSystem.translatef(((e.getGui()).field_230708_k_ / 2), 98.0F, 0.0F);
       float f2 = 1.2F - MathHelper.func_76135_e(MathHelper.func_76126_a((float)(Util.func_211177_b() % 1000L) / 1000.0F * 3.1415927F) * 0.05F);
       RenderSystem.scalef(f2, f2, f2);
       AbstractGui.func_238471_a_(e.getMatrixStack(), mc.field_71466_p, SPLASH, 0, -8, 16776960);
       RenderSystem.popMatrix();
    }
  }




  public static void drawModalRectWithCustomSizedTexture(int x, int y, float u, float v, int width, int height, float textureWidth, float textureHeight) {
     float f = 1.0F / textureWidth;
     float f1 = 1.0F / textureHeight;
     BufferBuilder bufferbuilder = Tessellator.func_178181_a().func_178180_c();
     bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181707_g);
     bufferbuilder.func_225582_a_(x, (y + height), 0.0D).func_225583_a_(u * f, (v + height) * f1).func_181675_d();
     bufferbuilder.func_225582_a_((x + width), (y + height), 0.0D).func_225583_a_((u + width) * f, (v + height) * f1).func_181675_d();
     bufferbuilder.func_225582_a_((x + width), y, 0.0D).func_225583_a_((u + width) * f, v * f1).func_181675_d();
     bufferbuilder.func_225582_a_(x, y, 0.0D).func_225583_a_(u * f, v * f1).func_181675_d();
     bufferbuilder.func_178977_d();
     RenderSystem.enableAlphaTest();
     WorldVertexBufferUploader.func_181679_a(bufferbuilder);
  }

  @SubscribeEvent
  public static void clearCache(ClientPlayerNetworkEvent.LoggedInEvent e) {
     ClientTardisCache.clearTardisData();
     ClientTardisFlightCache.clearTardisFlightData();
     if (sound != null) {
       Minecraft.func_71410_x().func_147118_V().func_147683_b(sound);
       sound = null;
    }
     System.out.println("Cleared client TARDIS cache");
  }
}


