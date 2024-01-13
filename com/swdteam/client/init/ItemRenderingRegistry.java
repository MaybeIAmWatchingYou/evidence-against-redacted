package com.swdteam.client.init;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.RegistryObject;


public class ItemRenderingRegistry
{
   private static List<ItemRenderInfo> guiRenders = new ArrayList<>();

  public static ItemRenderInfo addItemRenderer(RegistryObject<Item> item) {
     if (item.isPresent()) {
       ItemRenderInfo ir = new ItemRenderInfo((Item)item.get());
       guiRenders.add(ir);
       return ir;
    }

     return new ItemRenderInfo(null);
  }

  public static List<ItemRenderInfo> getGuiRenders() {
     return guiRenders;
  }

  public static class ItemRenderInfo
  {
     private Map<ItemCameraTransforms.TransformType, ItemModelMatch> transforms = new HashMap<>();

    private ResourceLocation defaultModelLocation;
    private Item item;

    public ItemRenderInfo(Item i) {
       if (i == null)
        return;
       this.item = i;
       ResourceLocation rlPath = i.getRegistryName();
       this.defaultModelLocation = (ResourceLocation)new ModelResourceLocation(rlPath, "inventory");
    }

    public void addTransformType(String s, ItemCameraTransforms.TransformType type) {
       if (this.item == null)
        return;
       ModelLoader.addSpecialModel((ResourceLocation)new ModelResourceLocation(this.item.getRegistryName() + "_" + s, "inventory"));
       this.transforms.put(type, new ItemModelMatch(this, s, type));
    }

    public Map<ItemCameraTransforms.TransformType, ItemModelMatch> getTransforms() {
       return this.transforms;
    }

    public Item getItem() {
       return this.item;
    }

    public ResourceLocation getDefaultModelLocation() {
       return this.defaultModelLocation;
    }

    public static class ItemModelMatch {
      private ItemCameraTransforms.TransformType type;
      private ResourceLocation modelLocation;
      private IBakedModel model;

      public ItemModelMatch(ItemRenderingRegistry.ItemRenderInfo iri, String s, ItemCameraTransforms.TransformType type) {
         this.modelLocation = (ResourceLocation)new ModelResourceLocation(iri.getItem().getRegistryName() + "_" + s, "inventory");
         this.type = type;
      }

      public ResourceLocation getModelLocation() {
         return this.modelLocation;
      }

      public ItemCameraTransforms.TransformType getType() {
         return this.type;
      }

      public void setModel(IBakedModel model) {
         this.model = model;
      }

      public IBakedModel getModel() {
         return this.model;
      }
    }
  }
}


