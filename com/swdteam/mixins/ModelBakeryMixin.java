package com.swdteam.mixins;

import com.swdteam.client.init.ItemRenderingInit;
import com.swdteam.client.init.ItemRenderingRegistry;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.IUnbakedModel;
import net.minecraft.client.renderer.model.ModelBakery;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.data.ModelTextures;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ModelBakery.class})
public abstract class ModelBakeryMixin
{
  @Shadow
  private Map<ResourceLocation, IUnbakedModel> field_217849_F;
  @Shadow
  private Map<ResourceLocation, IUnbakedModel> field_217851_H;

  @Shadow
  public abstract IUnbakedModel func_209597_a(ResourceLocation paramResourceLocation);

  @Inject(at = {@At("HEAD")}, remap = false, require = 1, allow = 1, method = {"Lnet/minecraft/client/renderer/model/ModelBakery;processLoading(Lnet/minecraft/profiler/IProfiler;I)V"})
  private void processLoading(CallbackInfo info) {
     ItemRenderingInit.addRegistries();
     for (ItemRenderingRegistry.ItemRenderInfo renderInfo : ItemRenderingRegistry.getGuiRenders()) {
       ModelResourceLocation model = ModelLoader.getInventoryVariant(ModelTextures.func_240344_a_(renderInfo.getItem(), "_gui").toString().replaceFirst("item/", ""));
       ResourceLocation fullPath = new ResourceLocation(model.getNamespace(), "models/item/" + model.getPath() + ".json");
       if (Minecraft.func_71410_x().func_195551_G().func_219533_b(fullPath)) {
         IUnbakedModel iunbakedmodel = func_209597_a((ResourceLocation)model);
         this.field_217849_F.put(model, iunbakedmodel);
         this.field_217851_H.put(model, iunbakedmodel);
      }
    }
  }
}


