package com.swdteam.model.javajson;

import net.minecraft.util.ResourceLocation;



public class ModelJSONTile
{
  public static ModelWrapper giveWrapper(String modelPath) {
     JSONModel model = ModelLoader.loadModel(new ResourceLocation("dalekmod", "models/tileentity/" + modelPath + ".json"));
     if (model == null || model.getModelData() == null) return null; 
     return model.getModelData().getModel();
  }
}


