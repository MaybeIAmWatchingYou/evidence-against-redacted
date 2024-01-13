package com.swdteam.model.javajson;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.util.ResourceLocation;


public class ModelData
{
  public String parent;
  public String texture;
  public String lightmap;
  public String alphamap;
  public String snowmap;
  public boolean gen_alpha = false;
   public List<ModelRenderer> groups = new ArrayList<>();
  public int texture_width;
  public int texture_height;
   public float scale = 1.0F;
  public String font_data;
  
  public static ResourceLocation getTexture(String tex) {
     if (tex == null) return null;
    
     int colonPos = tex.indexOf(':') + 1;
     String modId = tex.substring(0, colonPos);
     String path = tex.substring(colonPos);
     return new ResourceLocation(modId + "textures/" + path + ".png");
  }
  
  public ResourceLocation getParent() {
     if (this.parent != null && this.parent.length() > 0) return new ResourceLocation(this.parent + ".json"); 
     return null;
  }
  
  public static class FontData
  {
    private float x;
    private float y;
    private float z;
    private float scale;
    
    public float getRotationX() {
       return this.rot_x;
    } private String string; private float rot_x; private float rot_y; private float rot_z;
    public float getRotationY() {
       return this.rot_y;
    }
    public float getRotationZ() {
       return this.rot_z;
    }
    
    public float getScale() {
       return this.scale;
    }
    
    public String getString() {
       return this.string;
    }
    
    public double getX() {
       return this.x;
    }
    public double getY() {
       return this.y;
    }
    public double getZ() {
       return this.z;
    }
  }
}


