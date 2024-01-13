package com.swdteam.client.data;

import com.swdteam.main.proxy.ClientProxy;
import java.util.function.Predicate;
import net.minecraft.resources.IResourceManager;
import net.minecraftforge.resource.IResourceType;
import net.minecraftforge.resource.ISelectiveResourceReloadListener;



public class ModelReloadListener
  implements ISelectiveResourceReloadListener
{
  public void onResourceManagerReload(IResourceManager resourceManager, Predicate<IResourceType> resourcePredicate) {
     ClientProxy.registerReloadable();
  }
}


