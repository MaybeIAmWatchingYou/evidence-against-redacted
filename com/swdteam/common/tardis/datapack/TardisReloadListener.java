package com.swdteam.common.tardis.datapack;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.swdteam.common.init.DMTardis;
import com.swdteam.common.init.DMTardisRegistry;
import com.swdteam.common.tardis.Data;
import com.swdteam.common.tardis.Tardis;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.main.DalekMod;
import java.util.Map;
import net.minecraft.client.resources.JsonReloadListener;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;



public class TardisReloadListener
  extends JsonReloadListener
{
  public TardisReloadListener(Gson p_i51536_1_, String p_i51536_2_) {
     super(p_i51536_1_, p_i51536_2_);
  }


  private static void setupTardis(ResourceLocation rl, String json) {
     Tardis tardis = new Tardis(rl.getNamespace() + ":" + rl.getPath());
     Data data = (Data)DalekMod.GSON.fromJson(json, Data.class);
     if (data != null) {
       data.filePath = rl;
       data.setup();
       tardis.setData(data);
       DMTardisRegistry.getRegistry().put(rl, tardis);
    }
  }


  protected void apply(Map<ResourceLocation, JsonElement> objectIn, IResourceManager resourceManagerIn, IProfiler profilerIn) {
     for (Map.Entry<ResourceLocation, JsonElement> entry : objectIn.entrySet()) {
       ResourceLocation resourcelocation = entry.getKey();
       setupTardis(resourcelocation, ((JsonElement)entry.getValue()).toString());
    }

     for (Map.Entry<Integer, TardisData> entry : (Iterable<Map.Entry<Integer, TardisData>>)DMTardis.getLoadedTardises().entrySet()) {

       Integer key = entry.getKey();
       TardisData value = entry.getValue();
       value.reload();
    }
  }
}


