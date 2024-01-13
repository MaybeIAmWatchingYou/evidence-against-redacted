package com.swdteam.model.javajson;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;





public class ModelLoader
{
   private static Gson GSON = new Gson();
   public static ModelRendererWrapper NULL_PART = new ModelRendererWrapper(new BlankModel());
   private static Map<ResourceLocation, JSONModel> cache = new HashMap<>();
  
  public static JSONModel loadModel(ResourceLocation rl) {
     if (cache.containsKey(rl)) {
       return cache.get(rl);
    }
    
     JSONModel m = new JSONModel(rl);
     m.load();
     cache.put(rl, m);
    
     return m;
  }
  
  private static ModelData.FontData[] loadFontData(String s) {
    try {
       InputStream stream = Minecraft.func_71410_x().func_195551_G().func_199002_a(new ResourceLocation(s)).func_199027_b();
      
       BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
       StringBuilder b = new StringBuilder();
       String s1 = null;
      
       while ((s1 = reader.readLine()) != null) {
         b.append(s1);
      }
      
       ModelData.FontData[] data = (ModelData.FontData[])GSON.fromJson(b.toString(), ModelData.FontData[].class);
       return data;
     } catch (Exception e) {
       e.printStackTrace();

      
       return null;
    } 
  }
  public static JSONModel.ModelInformation loadModelInfo(ResourceLocation rl) {
    try {
       InputStream stream = Minecraft.func_71410_x().func_195551_G().func_199002_a(rl).func_199027_b();
      try {
         BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
         StringBuilder b = new StringBuilder();
         String s = null;
        
         while ((s = reader.readLine()) != null) {
           b.append(s);
        }
        
         ModelData model = (ModelData)GSON.fromJson(b.toString(), ModelData.class);
         ModelData.FontData[] data = null;
        
         if (model != null && model.font_data != null) {
           data = loadFontData(model.font_data);
        }
        
         if (model.getParent() != null) {
           JSONModel.ModelInformation modelInformation = new JSONModel.ModelInformation(loadModelInfo(model.getParent()).getModel(), ModelData.getTexture(model.texture), ModelData.getTexture(model.lightmap), model.alphamap, ModelData.getTexture(model.snowmap));
           modelInformation.setFontData(data);
           return modelInformation;
        } 
        
         ModelWrapper mod = new ModelWrapper(model.texture_width, model.texture_height);
        
         mod.modelScale = model.scale;
        
         for (ModelRenderer renderer : model.groups) {
           ModelRendererWrapper group = new ModelRendererWrapper(mod);
           group.func_78793_a(renderer.getPivot()[0], renderer.getPivot()[1], renderer.getPivot()[2]);
           group.field_78795_f = renderer.getxRot();
           group.field_78796_g = -renderer.getyRot();
           group.field_78808_h = renderer.getzRot();
           if (renderer.cubes != null) {
             for (Cube cube : renderer.cubes) {
               group.func_78784_a(cube.getUv()[0], cube.getUv()[1]).func_228303_a_(cube.getOrigin()[0], cube.getOrigin()[1], cube.getOrigin()[2], cube.getSize()[0], cube.getSize()[1], cube.getSize()[2], cube.getInflate(), cube.isMirrored());
            }
          }
           addChildren(mod, renderer, group);
          
           mod.renderList.add(group);
           mod.partsList.put(renderer.group_name, group);
        } 
         JSONModel.ModelInformation m = new JSONModel.ModelInformation(mod, ModelData.getTexture(model.texture), ModelData.getTexture(model.lightmap), model.alphamap, ModelData.getTexture(model.snowmap));
         m.setFontData(data);
         return m;
       } catch (Exception e) {
         e.printStackTrace();
      } 
     } catch (Exception e) {
       e.printStackTrace();
    } 
    
     return null;
  }
  
  private static void addChildren(ModelWrapper wrapper, ModelRenderer mv2, ModelRendererWrapper model) {
     if (mv2.getChildren() != null && mv2.getChildren().size() > 0) {
       for (ModelRenderer m : mv2.getChildren()) {
         ModelRendererWrapper renderer = new ModelRendererWrapper(wrapper);
         renderer.func_78793_a(m.getPivot()[0], m.getPivot()[1], m.getPivot()[2]);
         renderer.field_78795_f = m.getxRot();
         renderer.field_78796_g = m.getyRot();
         renderer.field_78808_h = m.getzRot();
        
         if (m.cubes != null) {
           for (Cube cube : m.cubes) {
             renderer.func_78784_a(cube.getUv()[0], cube.getUv()[1]).func_228303_a_(cube.getOrigin()[0], cube.getOrigin()[1], cube.getOrigin()[2], cube.getSize()[0], cube.getSize()[1], cube.getSize()[2], cube.getInflate(), cube.isMirrored());
          }
        }
         addChildren(wrapper, m, renderer);
        
         model.func_78792_a(renderer);
         wrapper.partsList.put(m.group_name, renderer);
      } 
    }
  }
  
  public static Map<ResourceLocation, JSONModel> getCache() {
     return cache;
  }
}


