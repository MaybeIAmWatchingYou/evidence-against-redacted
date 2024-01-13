package com.swdteam.util;

import com.mojang.blaze3d.systems.RenderSystem;
import com.swdteam.main.DalekMod;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.client.renderer.texture.Texture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;


public class Graphics
{
  private static float zLevel;
   public static float RENDER_NUM = 0.0625F;

  public static void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height) {
     Tessellator tessellator = Tessellator.func_178181_a();
     BufferBuilder bufferbuilder = tessellator.func_178180_c();
     bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181707_g);
     bufferbuilder.func_225582_a_(x, (y + height), zLevel).func_225583_a_(textureX * 0.00390625F, (textureY + height) * 0.00390625F).func_181675_d();
     bufferbuilder.func_225582_a_((x + width), (y + height), zLevel).func_225583_a_((textureX + width) * 0.00390625F, (textureY + height) * 0.00390625F).func_181675_d();
     bufferbuilder.func_225582_a_((x + width), y, zLevel).func_225583_a_((textureX + width) * 0.00390625F, textureY * 0.00390625F).func_181675_d();
     bufferbuilder.func_225582_a_(x, y, zLevel).func_225583_a_((textureX + 0) * 0.00390625F, textureY * 0.00390625F).func_181675_d();
     tessellator.func_78381_a();
  }


  public static void drawTexturedModalRectScaled(int x, int y, int textureX, int textureY, int width, int height, double scale) {
     RenderSystem.scaled(scale, scale, scale);
     Tessellator tessellator = Tessellator.func_178181_a();
     BufferBuilder bufferbuilder = tessellator.func_178180_c();
     bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181707_g);
     bufferbuilder.func_225582_a_(x, (y + height), zLevel).func_225583_a_(textureX * 0.00390625F, (textureY + height) * 0.00390625F).func_181675_d();
     bufferbuilder.func_225582_a_((x + width), (y + height), zLevel).func_225583_a_((textureX + width) * 0.00390625F, (textureY + height) * 0.00390625F).func_181675_d();
     bufferbuilder.func_225582_a_((x + width), y, zLevel).func_225583_a_((textureX + width) * 0.00390625F, textureY * 0.00390625F).func_181675_d();
     bufferbuilder.func_225582_a_(x, y, zLevel).func_225583_a_((textureX + 0) * 0.00390625F, textureY * 0.00390625F).func_181675_d();
     tessellator.func_78381_a();
  }

  public static void bind(ResourceLocation location) {
     Minecraft.func_71410_x().func_110434_K().func_110577_a(location);
  }

   private static Map<String, ResourceLocation> TEXTURES = new HashMap<>();

  public static ResourceLocation getTextureFromURL(final String url, String regName, String domain) {
     if (TEXTURES.containsKey(url)) {
       return TEXTURES.get(url);
    }
     final ResourceLocation rl = new ResourceLocation(domain.toLowerCase(), regName.toLowerCase());
     TEXTURES.put(url, rl);
     (new Thread(new Runnable()
        {


          public void run()
          {
            try {
               InputStream input = (new URL(url)).openStream();

               NativeImage ni = NativeImage.func_195713_a(input);
               DynamicTexture texture = new DynamicTexture(ni);
               input.close();

               (Minecraft.func_71410_x()).field_71446_o.func_229263_a_(rl, (Texture)texture);
             } catch (Exception e) {
               e.printStackTrace();
            }
          }
         })).start();
     return rl;
  }


   private static ResourceLocation[] DEFAULT_SKINS = new ResourceLocation[] { new ResourceLocation("dalekmod", "textures/gui/dmu/def_head_0.png"), new ResourceLocation("dalekmod", "textures/gui/dmu/def_head_1.png"), new ResourceLocation("dalekmod", "textures/gui/dmu/def_head_2.png"), new ResourceLocation("dalekmod", "textures/gui/dmu/def_head_3.png") };






  public static ResourceLocation getPlayerHead(final String name) {
     final String url = "https://minotar.net/helm/" + name + "/16.png";

     if (TEXTURES.containsKey(url)) {
       return TEXTURES.get(url);
    }
     if (name.startsWith("dmu_placeholder_def_skin")) {
       if (TEXTURES.containsKey(name)) {
         return TEXTURES.get(name);
      }
       TEXTURES.put(name, DEFAULT_SKINS[DalekMod.RANDOM.nextInt(DEFAULT_SKINS.length)]);
       return TEXTURES.get(name);
    }
     TEXTURES.put(url, DEFAULT_SKINS[DalekMod.RANDOM.nextInt(DEFAULT_SKINS.length)]);
     (new Thread(new Runnable()
        {


          public void run()
          {
            try {
               InputStream input = (new URL(url)).openStream();

               NativeImage ni = NativeImage.func_195713_a(input);
               DynamicTexture texture = new DynamicTexture(ni);
               input.close();

               ResourceLocation rl = new ResourceLocation("downloads", "skins/" + name.toLowerCase());
               (Minecraft.func_71410_x()).field_71446_o.func_229263_a_(rl, (Texture)texture);
               Graphics.TEXTURES.put(url, rl);
             } catch (Exception e) {
               e.printStackTrace();
            }
          }
         })).start();

     return TEXTURES.get(url);
  }
}


