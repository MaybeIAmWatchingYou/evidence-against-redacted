package com.swdteam.common.tardis;

import com.google.gson.JsonParseException;
import com.google.gson.annotations.SerializedName;
import com.swdteam.common.init.DMSoundEvents;
import com.swdteam.common.init.DMTardisRegistry;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.ITextProperties;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.registries.ForgeRegistries;















public class Data
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public transient ResourceLocation filePath;
  private transient ResourceLocation[] modelArray;
  private transient TardisInterior tardisInterior;
  private transient ITextProperties exteriorNameComponent;
  private transient ITextProperties descText;
  private transient int xpCost;
  private transient boolean isDefault;
  private transient SoundEvent closeSound;
  private transient SoundEvent openSound;
  private transient SoundEvent takeoffSound;
  private transient SoundEvent landingSound;
  private transient ExteriorHitbox shape;
  private transient ExteriorHitbox visualShape;
  @Deprecated
  private String exteriorName;
  private String name;
  @Deprecated
  private String interiorPath;
  private String interior;
  @Deprecated
  private String[] modelPath;
   private String[] models = new String[0];
  
  @Deprecated
  private int xpValue;
  
  private int cost;
  
  @Deprecated
  private boolean isDefaultSelection = false;
  private boolean default_exterior = false;
  private boolean unlockable = true;
   private String desc = "";
  @Deprecated
  private String doorOpenSound;
  @Deprecated
  private String doorCloseSound;
  private ExteriorSounds sounds;
  private Object hitbox;
  private Object hitbox_visual;
  
  public ResourceLocation getModel(int i) {
     if (this.modelArray == null) {
       this.modelArray = new ResourceLocation[getSkinCount()];
       if (this.models.length > 0) { for (int j = 0; j < getSkinCount(); ) { this.modelArray[j] = new ResourceLocation(this.models[j]); j++; }  }
       else { for (int j = 0; j < getSkinCount(); ) { this.modelArray[j] = new ResourceLocation(this.modelPath[j]); j++; }
         }
    
     }  if (i >= this.modelArray.length) i = this.modelArray.length - 1;
     if (i < 0) i = 0;
    
     return this.modelArray[i];
  }

  
  public ExteriorHitbox getHitbox() {
     if (this.shape == null && this.hitbox == null) this.shape = new ExteriorHitbox();
    
     if (this.shape == null) {
       if (this.hitbox.getClass().getSimpleName().equals("ArrayList")) {
        
         this.shape = new ExteriorHitbox((ArrayList<ArrayList<Double>>)this.hitbox);
       } else if (this.hitbox.getClass().getSimpleName().equals("LinkedTreeMap")) {
        
         this.shape = new ExteriorHitbox((Map<String, ArrayList<ArrayList<Double>>>)this.hitbox);
      } else {
        
         this.shape = new ExteriorHitbox((LinkedHashMap)this.hitbox);
      } 
    }
     return this.shape;
  }


  
  public ExteriorHitbox getVisualHitbox() {
     if (this.visualShape == null && this.hitbox_visual == null) this.visualShape = getHitbox();
    
     if (this.visualShape == null) {
       if (this.hitbox_visual.getClass().getSimpleName().equals("ArrayList")) {
        
         this.visualShape = new ExteriorHitbox((ArrayList<ArrayList<Double>>)this.hitbox_visual);
       } else if (this.hitbox_visual.getClass().getSimpleName().equals("LinkedTreeMap")) {
        
         this.visualShape = new ExteriorHitbox((Map<String, ArrayList<ArrayList<Double>>>)this.hitbox_visual);
      } else {
        
         this.visualShape = new ExteriorHitbox((LinkedHashMap)this.hitbox_visual);
      } 
    }
     return this.visualShape;
  }

  
  public boolean hasSkins() {
     return (getSkinCount() > 1);
  }
  
  public int getSkinCount() {
     if (this.models.length > 0) return this.models.length;
     return this.modelPath.length;
  }
  
  public TardisInterior getInterior() {
     return this.tardisInterior;
  }
  
  public void setup() {
    ResourceLocation rl;
     if (this.interior == null && this.interiorPath != null) { rl = new ResourceLocation(this.interiorPath); }
     else if (this.interior != null) { rl = new ResourceLocation(this.interior); }
     else { if (isDefaultSelection()) throw new JsonParseException("No interior provided when required for " + this.filePath);
      return; }
    
     getExteriorName();
    
     if (DMTardisRegistry.getTardisInteriors().containsKey(rl)) this.tardisInterior = (TardisInterior)DMTardisRegistry.getTardisInteriors().get(rl);
  }
  
  public ITextProperties getExteriorName() {
     if (this.exteriorNameComponent == null)
       if (this.name == null && this.exteriorName != null) { this.exteriorNameComponent = (ITextProperties)new TranslationTextComponent(this.exteriorName); }
       else if (this.name != null) { this.exteriorNameComponent = (ITextProperties)new TranslationTextComponent(this.name); }
       
     if (this.exteriorNameComponent == null) throw new JsonParseException("No name provided for " + this.filePath);
    
     return this.exteriorNameComponent;
  }
  
  public int getXpValue() {
     if (this.xpCost != this.cost || this.xpCost != this.xpValue)
       if (this.cost == 0) { this.xpCost = this.xpValue; }
       else { this.xpCost = this.cost; }
       
     return this.xpCost;
  }
  
  public boolean isUnlockable() {
     return this.unlockable;
  }
  
  public ITextProperties getDescription() {
     if (this.descText == null) this.descText = (ITextProperties)new TranslationTextComponent(this.desc);
     return this.descText;
  }
  
  public SoundEvent getDoorCloseSound() {
     SoundEvent def = (SoundEvent)DMSoundEvents.TARDIS_POLICE_BOX_DOOR_CLOSE.get();
     if (this.doorCloseSound == null && (this.sounds == null || this.sounds.close == null)) return def;
    
     if (this.closeSound == null) {
      ResourceLocation rl;
       if (this.doorCloseSound != null) { rl = new ResourceLocation(this.doorCloseSound); }
       else { rl = new ResourceLocation(this.sounds.close); }
      
       if (ForgeRegistries.SOUND_EVENTS.containsKey(rl)) { this.closeSound = (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(rl); }
       else { this.closeSound = def; }
    
    } 
     return this.closeSound;
  }
  
  public SoundEvent getDoorOpenSound() {
     SoundEvent def = (SoundEvent)DMSoundEvents.TARDIS_POLICE_BOX_DOOR_OPEN.get();
     if (this.doorOpenSound == null && (this.sounds == null || this.sounds.open == null)) return def;
    
     if (this.openSound == null) {
      ResourceLocation rl;
       if (this.doorOpenSound != null) { rl = new ResourceLocation(this.doorOpenSound); }
       else { rl = new ResourceLocation(this.sounds.open); }
      
       if (ForgeRegistries.SOUND_EVENTS.containsKey(rl)) { this.openSound = (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(rl); }
       else { this.openSound = def; }
    
    } 
     return this.openSound;
  }
  
  public SoundEvent getTardisTakeoffSound() {
     SoundEvent def = (SoundEvent)DMSoundEvents.TARDIS_DEMAT.get();
     if (this.sounds == null || this.sounds.demat == null) return def;
    
     if (this.takeoffSound == null) {
       ResourceLocation rl = new ResourceLocation(this.sounds.demat);
      
       if (ForgeRegistries.SOUND_EVENTS.containsKey(rl)) { this.takeoffSound = (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(rl); }
       else { this.takeoffSound = def; }
    
    } 
     return this.takeoffSound;
  }
  
  public SoundEvent getTardisLandingSound() {
     SoundEvent def = (SoundEvent)DMSoundEvents.TARDIS_REMAT.get();
     if (this.sounds == null || this.sounds.remat == null) return def;
    
     if (this.landingSound == null) {
       ResourceLocation rl = new ResourceLocation(this.sounds.remat);
      
       if (ForgeRegistries.SOUND_EVENTS.containsKey(rl)) { this.landingSound = (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(rl); }
       else { this.landingSound = def; }
    
    } 
     return this.landingSound;
  }
  
  public boolean isDefaultSelection() {
     if (this.isDefault != ((this.isDefaultSelection || this.default_exterior))) this.isDefault = (this.isDefaultSelection || this.default_exterior);
     return this.isDefault;
  }
  
  public static class ExteriorSounds
    implements Serializable
  {
    private static final long serialVersionUID = -6155353656098693278L;
    public String open;
    public String close;
    public String remat;
    public String demat;
  }
  
  public static class ExteriorHitbox
    implements Serializable {
    private static final long serialVersionUID = -6155353614618693278L;
    @SerializedName("default")
    private ArrayList<ArrayList<Double>> all;
    private ArrayList<ArrayList<Double>> north;
    private ArrayList<ArrayList<Double>> northeast;
    private ArrayList<ArrayList<Double>> east;
    private ArrayList<ArrayList<Double>> southeast;
    private ArrayList<ArrayList<Double>> south;
    private ArrayList<ArrayList<Double>> southwest;
    private ArrayList<ArrayList<Double>> west;
    private ArrayList<ArrayList<Double>> northwest;
    
    public ExteriorHitbox(ArrayList<ArrayList<Double>> all) {
       this.all = all;
    }
    
    public ExteriorHitbox() {
       ArrayList<Double> single = new ArrayList<>(Arrays.asList(new Double[] { Double.valueOf(0.0D), Double.valueOf(0.0D), Double.valueOf(0.0D), Double.valueOf(16.0D), Double.valueOf(32.0D), Double.valueOf(16.0D) }));
       ArrayList<ArrayList<Double>> _all = new ArrayList<>(Arrays.asList((ArrayList<Double>[])new ArrayList[] { single }));
       this.all = _all;
    }
    
    public ExteriorHitbox(Map<String, ArrayList<ArrayList<Double>>> map) {
       this.all = map.get("default");
       this.north = map.get("north");
       this.northeast = map.get("northeast");
       this.east = map.get("east");
       this.southeast = map.get("southeast");
       this.south = map.get("south");
       this.southwest = map.get("southwest");
       this.west = map.get("west");
       this.northwest = map.get("northwest");
    }
    
     public enum RotationEnum { ALL, NORTH, NORTH_EAST, EAST, SOUTH_EAST, SOUTH, SOUTH_WEST, WEST, NORTH_WEST; }
    
    public VoxelShape getRotationShape(RotationEnum rotation) {
       switch (rotation) { default:
           return makeShape(this.all);
         case NORTH: return makeShape((this.north == null) ? this.all : this.north);
         case NORTH_EAST: return makeShape((this.northeast == null) ? this.all : this.northeast);
         case EAST: return makeShape((this.east == null) ? this.all : this.east);
         case SOUTH_EAST: return makeShape((this.southeast == null) ? this.all : this.southeast);
         case SOUTH: return makeShape((this.south == null) ? this.all : this.south);
         case SOUTH_WEST: return makeShape((this.southwest == null) ? this.all : this.southwest);
         case WEST: return makeShape((this.west == null) ? this.all : this.west);
         case NORTH_WEST: break; }  return makeShape((this.northwest == null) ? this.all : this.northwest);
    }

    
    public VoxelShape makeShape(ArrayList<ArrayList<Double>> array) {
       VoxelShape tempShape = VoxelShapes.func_197880_a();
       for (ArrayList<Double> shapeArray : array) {
         tempShape = VoxelShapes.func_197872_a(tempShape, VoxelShapes.func_197873_a(((Double)shapeArray.get(0)).doubleValue() / 16.0D, ((Double)shapeArray.get(1)).doubleValue() / 16.0D, ((Double)shapeArray.get(2)).doubleValue() / 16.0D, ((Double)shapeArray.get(3)).doubleValue() / 16.0D, ((Double)shapeArray.get(4)).doubleValue() / 16.0D, ((Double)shapeArray.get(5)).doubleValue() / 16.0D));
      }
       return tempShape;
    }
  }
}


