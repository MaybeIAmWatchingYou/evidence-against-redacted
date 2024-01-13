package com.swdteam.model.javajson;

import java.io.IOException;
import java.io.InputStream;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.util.ResourceLocation;





public class JSONModel
{
  private ResourceLocation model_path;
  private ModelInformation model;
  public boolean render_lightmap = true;
  private boolean render_snowmap = false;
  
  public JSONModel(ResourceLocation path) {
     this.model_path = path;
  }
  
  public ModelInformation getModelData() {
     return this.model;
  }
  
  public void load() {
     this.model = ModelLoader.loadModelInfo(this.model_path);
     if (this.model != null && this.model.getModel() != null) {
       (this.model.getModel()).model = this;
    } else {
       this.model = new ModelInformation(new ModelWrapper(0, 0), new ResourceLocation("dalekmod", "missing"), new ResourceLocation("dalekmod", "missing"), null, null);
    } 
  }
  
  public boolean shouldRenderSnowMap() {
     if (this.model.getSnowMap() == null) {
       return false;
    }
     return this.render_snowmap;
  }
  
  public void setRenderSnowMap(boolean render_snowmap) {
     this.render_snowmap = render_snowmap;
  }
  
  public static class ModelInformation {
    private ModelWrapper model;
    private ResourceLocation texture;
    private ResourceLocation light_map;
    private ResourceLocation alpha_map;
    private ResourceLocation snow_map;
    public boolean genAlphaMap = false;
    private ModelData.FontData[] fontData;
    
    public ModelInformation(ModelWrapper m, ResourceLocation tex, ResourceLocation lightMap, String alpha_map, ResourceLocation snowmap) {
       this.model = m;
       this.texture = tex;
       this.light_map = lightMap;
       if (alpha_map != null && !alpha_map.equals("generated")) {
         this.alpha_map = ModelData.getTexture(alpha_map);
      }
       this.snow_map = snowmap;
    }
    
    public void setFontData(ModelData.FontData[] fontData) {
       this.fontData = fontData;
    }
    
    public ModelData.FontData[] getFontData() {
       return this.fontData;
    }
    
    public ModelWrapper getModel() {
       return this.model;
    }
    
    public ResourceLocation getTexture() {
       return (this.texture != null) ? this.texture : new ResourceLocation("dalekmod", "missing");
    }
    
    public ResourceLocation getLightMap() {
       return this.light_map;
    }
    
    public ResourceLocation getSnowMap() {
       return this.snow_map;
    }
    
    public ResourceLocation getAlphaMap() {
       return this.alpha_map;
    }
    
    public static ResourceLocation generateAlphaMap(ResourceLocation tex) {
      try {
         InputStream a = Minecraft.func_71410_x().func_195551_G().func_199002_a(tex).func_199027_b();
        
         NativeImage image_a = NativeImage.func_195713_a(a);
         NativeImage image = new NativeImage(image_a.func_195714_b(), image_a.func_195702_a(), false);
         image.func_195703_a(image_a);
         for (int x = 0; x < image.func_195714_b(); x++) {
           for (int y = 0; y < image.func_195702_a(); y++) {
             int color = image_a.func_195709_a(x, y);
             if (NativeImage.func_227786_a_(color) > 0) {
               image.func_195700_a(x, y, 50331647);
            }
          } 
        } 
        
         DynamicTexture texture = new DynamicTexture(image);
        
         ResourceLocation res = Minecraft.func_71410_x().func_110434_K().func_110578_a("dalekmod/textures/" + tex.getPath().replaceAll(".png", "") + "_alphamap.png", texture);
        
         return res;
       } catch (IOException e) {
         e.printStackTrace();
        
         return tex;
      } 
    }
  }
}


