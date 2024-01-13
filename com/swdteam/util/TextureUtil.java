package com.swdteam.util;

import java.io.File;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DownloadingTexture;
import net.minecraft.client.renderer.texture.Texture;
import net.minecraft.util.ResourceLocation;





public class TextureUtil
{
  public static ResourceLocation loadSkin(String username) {
     ResourceLocation resourcelocation = new ResourceLocation("dalekmod", "holograms/" + username.toLowerCase().replaceAll("(\\W|^_)*", ""));
     Texture texture = (Minecraft.func_71410_x()).field_71446_o.func_229267_b_(resourcelocation);
     if (texture != null) {
       return resourcelocation;
    }
     File folder = new File("tmp/dalekmod/hologram/cache/");
     File file = new File("tmp/dalekmod/hologram/cache/" + username + ".png");
     if (!folder.exists()) {
       folder.mkdirs();
    }

     DownloadingTexture downloadingtexture = new DownloadingTexture(file, "https://minotar.net/skin/" + username + ".png", resourcelocation, false, new Runnable()
        {
          public void run() {}
        });



     (Minecraft.func_71410_x()).field_71446_o.func_229263_a_(resourcelocation, (Texture)downloadingtexture);


     return resourcelocation;
  }


  public static ResourceLocation loadImageLoader(String img_name) {
     return Graphics.getTextureFromURL("https://i.swdteam.com/dm/" + img_name + ".png", img_name, "img_loader");
  }
}


