package com.swdteam.client.init;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.swdteam.common.init.DMBlocks;
import java.util.List;
import java.util.Map;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;




@EventBusSubscriber(modid = "dalekmod", bus = Mod.EventBusSubscriber.Bus.MOD, value = {Dist.CLIENT})
public class ModClientEvents
{
  @SubscribeEvent
  public static void clientSetup(FMLClientSetupEvent event) {
     RenderTypeLookup.setRenderLayer((Block)DMBlocks.STEEL_DOOR.get(), RenderType.func_228641_d_());
     RenderTypeLookup.setRenderLayer((Block)DMBlocks.GEAR.get(), RenderType.func_228643_e_());
     RenderTypeLookup.setRenderLayer((Block)DMBlocks.TARDIS_PLANT.get(), RenderType.func_228643_e_());
     RenderTypeLookup.setRenderLayer((Block)DMBlocks.ARTRON_CUBE.get(), RenderType.func_228645_f_());
     RenderTypeLookup.setRenderLayer((Block)DMBlocks.STEEL_BEAMS.get(), RenderType.func_228643_e_());
     RenderTypeLookup.setRenderLayer((Block)DMBlocks.STEEL_GRATE.get(), RenderType.func_228643_e_());
     RenderTypeLookup.setRenderLayer((Block)DMBlocks.STEEL_GRATE_SLAB.get(), RenderType.func_228643_e_());
     RenderTypeLookup.setRenderLayer((Block)DMBlocks.RUSTED_STEEL_BEAMS.get(), RenderType.func_228643_e_());
     RenderTypeLookup.setRenderLayer((Block)DMBlocks.RUSTED_STEEL_GRATE.get(), RenderType.func_228643_e_());
     RenderTypeLookup.setRenderLayer((Block)DMBlocks.RUSTED_STEEL_GRATE_SLAB.get(), RenderType.func_228643_e_());
     RenderTypeLookup.setRenderLayer((Block)DMBlocks.STAINLESS_STEEL_BEAMS.get(), RenderType.func_228643_e_());
     RenderTypeLookup.setRenderLayer((Block)DMBlocks.STAINLESS_STEEL_GRATE.get(), RenderType.func_228643_e_());
     RenderTypeLookup.setRenderLayer((Block)DMBlocks.STAINLESS_STEEL_GRATE_SLAB.get(), RenderType.func_228643_e_());
     RenderTypeLookup.setRenderLayer((Block)DMBlocks.DIMENSION_SELECTOR_PANEL.get(), RenderType.func_228643_e_());
     RenderTypeLookup.setRenderLayer((Block)DMBlocks.COORD_PANEL.get(), RenderType.func_228643_e_());
     RenderTypeLookup.setRenderLayer((Block)DMBlocks.DATA_WRITER.get(), RenderType.func_228643_e_());
     RenderTypeLookup.setRenderLayer((Block)DMBlocks.GLASS_ROTOR.get(), RenderType.func_228643_e_());
     RenderTypeLookup.setRenderLayer((Block)DMBlocks.STEEL_ROTOR.get(), RenderType.func_228643_e_());
     RenderTypeLookup.setRenderLayer((Block)DMBlocks.RUSTED_STEEL_ROTOR.get(), RenderType.func_228643_e_());
     RenderTypeLookup.setRenderLayer((Block)DMBlocks.STAINLESS_STEEL_ROTOR.get(), RenderType.func_228643_e_());
     RenderTypeLookup.setRenderLayer((Block)DMBlocks.WHITE_GLASS_ROTOR.get(), RenderType.func_228643_e_());
     RenderTypeLookup.setRenderLayer((Block)DMBlocks.ORANGE_GLASS_ROTOR.get(), RenderType.func_228643_e_());
     RenderTypeLookup.setRenderLayer((Block)DMBlocks.MAGENTA_GLASS_ROTOR.get(), RenderType.func_228643_e_());
     RenderTypeLookup.setRenderLayer((Block)DMBlocks.LIGHT_BLUE_GLASS_ROTOR.get(), RenderType.func_228643_e_());
     RenderTypeLookup.setRenderLayer((Block)DMBlocks.LIME_GLASS_ROTOR.get(), RenderType.func_228643_e_());
     RenderTypeLookup.setRenderLayer((Block)DMBlocks.YELLOW_GLASS_ROTOR.get(), RenderType.func_228643_e_());
     RenderTypeLookup.setRenderLayer((Block)DMBlocks.PINK_GLASS_ROTOR.get(), RenderType.func_228643_e_());
     RenderTypeLookup.setRenderLayer((Block)DMBlocks.GRAY_GLASS_ROTOR.get(), RenderType.func_228643_e_());
     RenderTypeLookup.setRenderLayer((Block)DMBlocks.CYAN_GLASS_ROTOR.get(), RenderType.func_228643_e_());
     RenderTypeLookup.setRenderLayer((Block)DMBlocks.LIGHT_GRAY_GLASS_ROTOR.get(), RenderType.func_228643_e_());
     RenderTypeLookup.setRenderLayer((Block)DMBlocks.PURPLE_GLASS_ROTOR.get(), RenderType.func_228643_e_());
     RenderTypeLookup.setRenderLayer((Block)DMBlocks.BLUE_GLASS_ROTOR.get(), RenderType.func_228643_e_());
     RenderTypeLookup.setRenderLayer((Block)DMBlocks.BROWN_GLASS_ROTOR.get(), RenderType.func_228643_e_());
     RenderTypeLookup.setRenderLayer((Block)DMBlocks.GREEN_GLASS_ROTOR.get(), RenderType.func_228643_e_());
     RenderTypeLookup.setRenderLayer((Block)DMBlocks.RED_GLASS_ROTOR.get(), RenderType.func_228643_e_());
     RenderTypeLookup.setRenderLayer((Block)DMBlocks.BLACK_GLASS_ROTOR.get(), RenderType.func_228643_e_());
     RenderTypeLookup.setRenderLayer((Block)DMBlocks.WHITE_TUBE_ROTOR.get(), RenderType.func_228643_e_());
     RenderTypeLookup.setRenderLayer((Block)DMBlocks.ORANGE_TUBE_ROTOR.get(), RenderType.func_228643_e_());
     RenderTypeLookup.setRenderLayer((Block)DMBlocks.MAGENTA_TUBE_ROTOR.get(), RenderType.func_228643_e_());
     RenderTypeLookup.setRenderLayer((Block)DMBlocks.LIGHT_BLUE_TUBE_ROTOR.get(), RenderType.func_228643_e_());
     RenderTypeLookup.setRenderLayer((Block)DMBlocks.LIME_TUBE_ROTOR.get(), RenderType.func_228643_e_());
     RenderTypeLookup.setRenderLayer((Block)DMBlocks.YELLOW_TUBE_ROTOR.get(), RenderType.func_228643_e_());
     RenderTypeLookup.setRenderLayer((Block)DMBlocks.PINK_TUBE_ROTOR.get(), RenderType.func_228643_e_());
     RenderTypeLookup.setRenderLayer((Block)DMBlocks.GRAY_TUBE_ROTOR.get(), RenderType.func_228643_e_());
     RenderTypeLookup.setRenderLayer((Block)DMBlocks.CYAN_TUBE_ROTOR.get(), RenderType.func_228643_e_());
     RenderTypeLookup.setRenderLayer((Block)DMBlocks.LIGHT_GRAY_TUBE_ROTOR.get(), RenderType.func_228643_e_());
     RenderTypeLookup.setRenderLayer((Block)DMBlocks.PURPLE_TUBE_ROTOR.get(), RenderType.func_228643_e_());
     RenderTypeLookup.setRenderLayer((Block)DMBlocks.BLUE_TUBE_ROTOR.get(), RenderType.func_228643_e_());
     RenderTypeLookup.setRenderLayer((Block)DMBlocks.BROWN_TUBE_ROTOR.get(), RenderType.func_228643_e_());
     RenderTypeLookup.setRenderLayer((Block)DMBlocks.GREEN_TUBE_ROTOR.get(), RenderType.func_228643_e_());
     RenderTypeLookup.setRenderLayer((Block)DMBlocks.RED_TUBE_ROTOR.get(), RenderType.func_228643_e_());
     RenderTypeLookup.setRenderLayer((Block)DMBlocks.BLACK_TUBE_ROTOR.get(), RenderType.func_228643_e_());
     RenderTypeLookup.setRenderLayer((Block)DMBlocks.ENGINEERING_TABLE.get(), RenderType.func_228643_e_());
     RenderTypeLookup.setRenderLayer((Block)DMBlocks.DALEK_DOOR.get(), RenderType.func_228643_e_());
     RenderTypeLookup.setRenderLayer((Block)DMBlocks.ROUNDEL_BUILDER.get(), RenderType.func_228643_e_());
     RenderTypeLookup.setRenderLayer((Block)DMBlocks.STEEL_BEAMS_ROUNDEL.get(), RenderType.func_228643_e_());
     RenderTypeLookup.setRenderLayer((Block)DMBlocks.STAINLESS_STEEL_BEAMS_ROUNDEL.get(), RenderType.func_228643_e_());
     RenderTypeLookup.setRenderLayer((Block)DMBlocks.RUSTED_STEEL_BEAMS_ROUNDEL.get(), RenderType.func_228643_e_());
     RenderTypeLookup.setRenderLayer((Block)DMBlocks.CLASSIC_LEAVES.get(), RenderType.func_228643_e_());
     RenderTypeLookup.setRenderLayer((Block)DMBlocks.CLASSIC_RED_FLOWER.get(), RenderType.func_228643_e_());
     RenderTypeLookup.setRenderLayer((Block)DMBlocks.CLASSIC_YELLOW_FLOWER.get(), RenderType.func_228643_e_());
     RenderTypeLookup.setRenderLayer((Block)DMBlocks.POTTED_CLASSIC_RED_FLOWER.get(), RenderType.func_228643_e_());
     RenderTypeLookup.setRenderLayer((Block)DMBlocks.POTTED_CLASSIC_YELLOW_FLOWER.get(), RenderType.func_228643_e_()); RenderTypeLookup.setRenderLayer((Block)DMBlocks.CLASSIC_GLASS.get(), RenderType.func_228645_f_());
     RenderTypeLookup.setRenderLayer((Block)DMBlocks.BUBBLE_WRAP.get(), RenderType.func_228645_f_());
     RenderTypeLookup.setRenderLayer((Block)DMBlocks.EXPLOSIVE_BUBBLE_WRAP.get(), RenderType.func_228645_f_());
     RenderTypeLookup.setRenderLayer((Block)DMBlocks.FORCE_FIELD.get(), RenderType.func_228643_e_());
     RenderTypeLookup.setRenderLayer((Block)DMBlocks.CELERY.get(), RenderType.func_228643_e_());
     RenderTypeLookup.setRenderLayer((Block)DMBlocks.FLIGHT_PANEL.get(), RenderType.func_228643_e_());
  }
  
  @SubscribeEvent
  public static void modelBakeEvent(ModelBakeEvent event) {
     Map<ResourceLocation, IBakedModel> map = event.getModelRegistry();
     for (ItemRenderingRegistry.ItemRenderInfo ir : ItemRenderingRegistry.getGuiRenders()) {
       final IBakedModel defaultModel = map.get(ir.getDefaultModelLocation());
       for (ItemRenderingRegistry.ItemRenderInfo.ItemModelMatch imm : ir.getTransforms().values()) {
         imm.setModel(map.get(imm.getModelLocation()));
         map.put(imm.getModelLocation(), imm.getModel());
      } 
      
       IBakedModel modelWrapper = new IBakedModel()
        {
          public List<BakedQuad> func_200117_a(BlockState state, Direction side, Random rand)
          {
             return defaultModel.func_200117_a(state, side, rand);
          }

          
          public boolean func_177555_b() {
             return defaultModel.func_177555_b();
          }

          
          public boolean func_177556_c() {
             return defaultModel.func_177556_c();
          }

          
          public boolean func_188618_c() {
             return defaultModel.func_188618_c();
          }


          
          public TextureAtlasSprite func_177554_e() {
             return defaultModel.func_177554_e();
          }

          
          public ItemOverrideList func_188617_f() {
             return defaultModel.func_188617_f();
          }

          
          public IBakedModel handlePerspective(ItemCameraTransforms.TransformType transformType, MatrixStack mat) {
             IBakedModel modelToUse = defaultModel;
             if (ir.getTransforms().containsKey(transformType)) {
               modelToUse = ((ItemRenderingRegistry.ItemRenderInfo.ItemModelMatch)ir.getTransforms().get(transformType)).getModel();
            }
            
             for (ItemRenderingRegistry.ItemRenderInfo.ItemModelMatch itemModelMatch : ir.getTransforms().values());



            
             if (modelToUse == null) {
               modelToUse = defaultModel;
            }
             return ForgeHooksClient.handlePerspective(modelToUse, transformType, mat);
          }


          
          public boolean func_230044_c_() {
             return false;
          }
        };
       map.put(ir.getDefaultModelLocation(), modelWrapper);
    } 
  }
  
  public static class ClientPlayerData {
    public static boolean inCustomLava = false;
  }
}


