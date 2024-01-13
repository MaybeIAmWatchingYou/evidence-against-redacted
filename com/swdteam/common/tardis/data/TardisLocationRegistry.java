package com.swdteam.common.tardis.data;

import com.google.gson.JsonParseException;
import com.swdteam.common.init.DMTranslationKeys;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextProperties;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;



public class TardisLocationRegistry
{
   private static Map<RegistryKey<World>, TardisLocation> locationRegistry = new HashMap<>();
   private static List<TardisLocation> locationRegistryList = new ArrayList<>();

  public static Map<RegistryKey<World>, TardisLocation> getLocationRegistry() {
     return locationRegistry;
  }

  public static List<TardisLocation> getLocationRegistryAsList() {
     return locationRegistryList;
  }

  public static TardisLocation getLocationForKey(RegistryKey<World> key) {
     if (locationRegistry.containsKey(key)) {
       return locationRegistry.get(key);
    }
     return null;
  }


  public static class TardisLocation
  {
    public transient ResourceLocation filePath;

    private transient ITextProperties dimName;

    private transient RegistryKey<World> dimensionKey;

    private transient ResourceLocation dimensionImage;
    @Deprecated
    private String dimension_name;
    private String name;
    @Deprecated
    private String dimension_key;
    private String dimension;
    @Deprecated
    private String dimension_icon;
    private String icon;
     private int[] coordinate = new int[] { 0, 0 };

    public RegistryKey<World> getDimension() {
       if (this.dimensionKey == null)
         if (this.dimension == null && this.dimension_key != null) { this.dimensionKey = RegistryKey.func_240903_a_(Registry.field_239699_ae_, new ResourceLocation(this.dimension_key)); }
         else if (this.dimension != null) { this.dimensionKey = RegistryKey.func_240903_a_(Registry.field_239699_ae_, new ResourceLocation(this.dimension)); }

       if (this.dimensionKey == null) throw new JsonParseException("No dimension provided when required for " + this.filePath);

       return this.dimensionKey;
    }

    public ResourceLocation getDimensionImage() {
       if (this.dimensionImage == null)
         if (this.icon == null) { this.dimensionImage = new ResourceLocation(this.dimension_icon); }
         else { this.dimensionImage = new ResourceLocation(this.icon); }

       return this.dimensionImage;
    }

    public ITextProperties getDimensionName() {
       if (this.dimName == null)
         if (this.name == null && this.dimension_name != null) { this.dimensionImage = new ResourceLocation(this.dimension_name); }
         else if (this.name != null) { this.dimName = (ITextProperties)new TranslationTextComponent(this.name); }
         else { this.dimName = (ITextProperties)new TranslationTextComponent(DMTranslationKeys.getDimensionTranslation(getDimension().getRegistryLocation().toString(), false).getString()); }

       return this.dimName;
    }

    public int[] getCoordinate() {
       return this.coordinate;
    }
  }
}


