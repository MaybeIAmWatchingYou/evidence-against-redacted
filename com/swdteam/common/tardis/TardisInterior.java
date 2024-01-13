package com.swdteam.common.tardis;

import com.google.gson.JsonParseException;
import com.swdteam.common.init.DMTardisRegistry;
import com.swdteam.util.world.Schematic;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextProperties;
import net.minecraft.util.text.TranslationTextComponent;








public class TardisInterior
  implements Serializable
{
  private static final long serialVersionUID = 3020517538431332390L;
  public transient ResourceLocation filePath;
  private transient float spawnRotation;
  private transient float[] door;
  private transient Schematic interior;
  private transient ResourceLocation previewTexture;
  private transient ResourceLocation registryKey;
  private transient BuildingRecipe parsedRecipe;
  private transient ITextProperties interiorNameComponent;
  private String regKey;
  @Deprecated
  private float defaultSpawnRotation;
  private float default_rotation;
  @Deprecated
  private float[] doorOffset;
  private float[] door_offset;
  @Deprecated
   private int xOffset = 0; @Deprecated
   private int yOffset = 0; @Deprecated
   private int zOffset = 0;
   private int[] block_offset = new int[] { 0, 0, 0 };
  
  @Deprecated
  private String interiorName;
  
  private String name;
  @Deprecated
  private String interiorFile;
  private String schematic;
  @Deprecated
   private String previewImage = "dalekmod:textures/gui/interior_selection/missing.png";
  
  private String preview;
  
  private Object recipe;
  
   private final TardisData.Lighting lighting = new TardisData.Lighting();
  
  public Schematic getInterior() {
     if (this.interior == null)
       if (this.schematic == null && this.interiorFile != null) { this.interior = (Schematic)DMTardisRegistry.getInteriorBuildRegistry().get(new ResourceLocation(this.interiorFile)); }
       else if (this.schematic != null) { this.interior = (Schematic)DMTardisRegistry.getInteriorBuildRegistry().get(new ResourceLocation(this.schematic)); }
       
     if (this.interior == null) throw new JsonParseException("No interior schematic provided for " + this.filePath);
    
     return this.interior;
  }
  
  public ResourceLocation getRegKey() {
     if (this.registryKey == null) this.registryKey = new ResourceLocation(this.regKey);
    
     return this.registryKey;
  }
  
  public void setRegKey(String regKey) {
     this.regKey = regKey;
  }
  
  public ITextProperties getInteriorName() {
     if (this.interiorNameComponent == null)
       if (this.name == null && this.interiorName != null) { this.interiorNameComponent = (ITextProperties)new TranslationTextComponent(this.interiorName); }
       else if (this.name != null) { this.interiorNameComponent = (ITextProperties)new TranslationTextComponent(this.name); }
       
     if (this.interiorNameComponent == null) throw new JsonParseException("No name provided for " + this.filePath);
    
     return this.interiorNameComponent;
  }
  
  public float getDefaultSpawnRotation() {
     if (this.spawnRotation != this.default_rotation || this.spawnRotation != this.defaultSpawnRotation)
       if (this.default_rotation == 0.0F) { this.spawnRotation = this.defaultSpawnRotation; }
       else { this.spawnRotation = this.default_rotation; }
       
     return this.spawnRotation;
  }
  
  public float[] getDoorOffset() {
     if (this.door == null)
       if (this.door_offset == null) { this.door = this.doorOffset; }
       else { this.door = this.door_offset; }
       
     return this.door;
  }
  
  public ResourceLocation getPreviewImage() {
     if (this.previewTexture == null)
       if (this.preview == null) { this.previewTexture = new ResourceLocation(this.previewImage); }
       else { this.previewTexture = new ResourceLocation(this.preview); }
       
     return this.previewTexture;
  }

  
  public BuildingRecipe getRecipe() {
     if (this.parsedRecipe == null) {
       if (this.recipe.getClass().getSimpleName().equals("ArrayList")) {
         if (((ArrayList)this.recipe).size() > 0 && ((ArrayList)this.recipe).get(0) instanceof Map)
        {
           ArrayList<Map<String, ?>> newArray = new ArrayList<>();
          
           ((ArrayList)this.recipe).stream().forEach(map -> {
                Map<String, Object> newMap = new LinkedHashMap<>();
                
                map.keySet().stream().forEach(());
                newArray.add(newMap);
              });
           this.parsedRecipe = new BuildingRecipe(newArray);
        }
        else
        {
           this.parsedRecipe = new BuildingRecipe((ArrayList<Map<String, ?>>)this.recipe);
        }
      
       } else if (this.recipe instanceof Map) {
        
         this.parsedRecipe = new BuildingRecipe((Map<String, ArrayList<Map<String, ?>>>)this.recipe);
      } else {
        
         this.parsedRecipe = new BuildingRecipe(new ArrayList<>());
      } 
    }
     return this.parsedRecipe;
  }
  
  public BlockPos getBlockOffset() {
     if (this.xOffset != 0 && this.block_offset[0] == 0) this.block_offset[0] = this.xOffset; 
     if (this.yOffset != 0 && this.block_offset[1] == 0) this.block_offset[1] = this.yOffset; 
     if (this.zOffset != 0 && this.block_offset[2] == 0) this.block_offset[2] = this.zOffset;
    
     return new BlockPos(this.block_offset[0], this.block_offset[1], this.block_offset[2]);
  }
  
  public TardisData.Lighting getLighting() {
     return this.lighting;
  }
  
  public static class BuildingRecipe
    implements Serializable {
    private static final long serialVersionUID = -6155353656098693278L;
    private BuildingRecipePart[] parts;
    
    BuildingRecipe(BuildingRecipePart[] parts) {
       this.parts = parts;
    }
    
    BuildingRecipe(ArrayList<Map<String, ?>> parts) {
       ArrayList<BuildingRecipePart> tempParts = new ArrayList<>();
      
       for (Map<String, ?> part : parts) {
         tempParts.add(new BuildingRecipePart((String)part.get("item"), ((Double)part.get("quantity")).intValue()));
      }
       this.parts = tempParts.<BuildingRecipePart>toArray(new BuildingRecipePart[parts.size()]);
    }
    
    @Deprecated
    BuildingRecipe(Map<String, ArrayList<Map<String, ?>>> map) {
       this(map.get("parts"));
    }
    
    public BuildingRecipePart[] getParts() {
       return this.parts;
    }
    
    public static class BuildingRecipePart
      implements Serializable {
      private static final long serialVersionUID = -2739603425900014322L;
      private String item;
      private int quantity;
      
      public BuildingRecipePart(String item, int quantity) {
         this.item = item;
         this.quantity = quantity;
      }
      
      public String getItem() {
         return this.item;
      }
      
      public int getQuantity() {
         return this.quantity;
      }
      
      public void addQuantity(int amount) {
         this.quantity += amount;
      }
    }
  }
}


