package com.swdteam.common.tardis.datapack;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.swdteam.common.tardis.data.TardisLocationRegistry;
import com.swdteam.main.DalekMod;
import java.util.Map;
import net.minecraft.client.resources.JsonReloadListener;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;




public class TardisLocationListener
  extends JsonReloadListener
{
  public TardisLocationListener(Gson gson, String path) {
     super(gson, path);
  }

  
  protected void apply(Map<ResourceLocation, JsonElement> objectIn, IResourceManager resourceManagerIn, IProfiler profilerIn) {
     TardisLocationRegistry.getLocationRegistry().clear();
     TardisLocationRegistry.getLocationRegistryAsList().clear();
    
     for (Map.Entry<ResourceLocation, JsonElement> entry : objectIn.entrySet()) {
       ResourceLocation resourcelocation = entry.getKey();
       TardisLocationRegistry.TardisLocation location = (TardisLocationRegistry.TardisLocation)DalekMod.GSON.fromJson(entry.getValue(), TardisLocationRegistry.TardisLocation.class);
       location.filePath = resourcelocation;
       if (location != null) {
         TardisLocationRegistry.getLocationRegistry().put(location.getDimension(), location);
         TardisLocationRegistry.getLocationRegistryAsList().add(location);
      } 
    } 
  }
}


