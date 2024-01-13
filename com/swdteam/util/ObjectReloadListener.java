package com.swdteam.util;

import com.google.common.collect.Maps;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.Map;
import net.minecraft.client.resources.ReloadListener;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



public abstract class ObjectReloadListener
  extends ReloadListener<Map<ResourceLocation, Object>>
{
   private static final Logger LOGGER = LogManager.getLogger();
  private static int EXTENSION_LENGTH;
  private final String folder;
  private final String fileType;

  public ObjectReloadListener(String folder, String fileType) {
     this.folder = folder;
     this.fileType = fileType;
     EXTENSION_LENGTH = fileType.length();
  }




  protected Map<ResourceLocation, Object> prepare(IResourceManager resourceManagerIn, IProfiler profilerIn) {
     Map<ResourceLocation, Object> map = Maps.newHashMap();
     int i = this.folder.length() + 1;

     for (ResourceLocation resourcelocation : resourceManagerIn.func_199003_a(this.folder, p_223379_0_ -> p_223379_0_.endsWith(this.fileType))) {


       String s = resourcelocation.getPath();
       ResourceLocation resourcelocation1 = new ResourceLocation(resourcelocation.getNamespace(), s.substring(i, s.length() - EXTENSION_LENGTH));


       try(IResource iresource = resourceManagerIn.func_199002_a(resourcelocation);
           InputStream inputstream = iresource.func_199027_b()) {

         ObjectInputStream ois = new ObjectInputStream(inputstream);
         Object o = ois.readObject();
         ois.close();
         map.put(resourcelocation1, o);
       } catch (IllegalArgumentException|java.io.IOException|ClassNotFoundException e) {
         LOGGER.error("Couldn't parse data file {} from {}", resourcelocation1, resourcelocation, e);
      }
    }

     return map;
  }

  protected ResourceLocation getPreparedPath(ResourceLocation rl) {
     return new ResourceLocation(rl.getNamespace(), this.folder + "/" + rl.getPath() + ".json");
  }
}


