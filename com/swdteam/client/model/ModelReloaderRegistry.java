package com.swdteam.client.model;

import java.util.ArrayList;
import java.util.List;

public class ModelReloaderRegistry
{
   private static List<IModelPartReloader> models = new ArrayList<>();

  public static void reloadModels() {
     for (int i = 0; i < models.size(); i++) {
       IModelPartReloader model = models.get(i);
       model.init();
    }
  }

  public static void register(IModelPartReloader p) {
     models.add(p);
     p.init();
  }
}


