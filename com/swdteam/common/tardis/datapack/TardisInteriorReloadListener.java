package com.swdteam.common.tardis.datapack;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.swdteam.common.init.DMTardisRegistry;
import com.swdteam.common.tardis.TardisInterior;
import com.swdteam.main.DalekMod;
import java.util.Map;
import net.minecraft.client.resources.JsonReloadListener;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;



public class TardisInteriorReloadListener
  extends JsonReloadListener
{
  public TardisInteriorReloadListener(Gson p_i51536_1_, String p_i51536_2_) {
     super(p_i51536_1_, p_i51536_2_);
  }







  private static void setupInterior(ResourceLocation rl, String json) {
     TardisInterior interior = (TardisInterior)DalekMod.GSON.fromJson(json, TardisInterior.class);
     interior.filePath = rl;
     interior.setRegKey(rl.toString());

     interior.getInteriorName();
     interior.getInterior();

     DMTardisRegistry.getTardisInteriors().put(rl, interior);
  }



  protected void apply(Map<ResourceLocation, JsonElement> objectIn, IResourceManager resourceManagerIn, IProfiler profilerIn) {
     DMTardisRegistry.getTardisInteriors().clear();

     for (Map.Entry<ResourceLocation, JsonElement> entry : objectIn.entrySet()) {
       ResourceLocation resourcelocation = entry.getKey();
       setupInterior(resourcelocation, ((JsonElement)entry.getValue()).toString());
    }
  }
}


