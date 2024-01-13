package com.swdteam.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.swdteam.common.entity.dalek.DalekEntity;
import com.swdteam.common.entity.dalek.IDalek;
import com.swdteam.common.init.DMBlocks;
import com.swdteam.common.init.DMDalekRegistry;
import com.swdteam.model.javajson.JSONModel;
import com.swdteam.model.javajson.ModelLoader;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;


public class ModelDalekBase
  extends EntityModel<DalekEntity>
  implements IModelPartReloader
{
  private boolean showBomb = false;
  private JSONModel model;
  public ModelRenderer LIGHT_LEFT;
  public ModelRenderer LIGHT_RIGHT;
  public ModelRenderer ANI_HEAD;
  public ModelRenderer ANI_EYE;
  public ModelRenderer ANI_NECK;
  public ModelRenderer ANI_TORSO;
  
  public ModelDalekBase(JSONModel model) {
     super(RenderType::func_228640_c_);
     this.model = model;
     ModelReloaderRegistry.register(this);
  }
  
  public void setPartEye(ModelRenderer aNI_EYE) {
     this.ANI_EYE = aNI_EYE;
  }
  
  public void setPartHead(ModelRenderer aNI_HEAD) {
     this.ANI_HEAD = aNI_HEAD;
  }
  
  public void setPartNeck(ModelRenderer aNI_NECK) {
     this.ANI_NECK = aNI_NECK;
  }
  
  public void setPartTorso(ModelRenderer aNI_TORSO) {
     this.ANI_TORSO = aNI_TORSO;
  }


  
  public void setupAnim(DalekEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
     if (entity.isNitroNined()) {
       this.showBomb = true;
    } else {
       this.showBomb = false;
    } 
    
     double rotation = Math.toRadians(netHeadYaw);
     if (this.ANI_HEAD != null) this.ANI_HEAD.field_78796_g = (float)rotation;
    
     double rotationEye = Math.toRadians(headPitch);
     if (this.ANI_EYE != null) this.ANI_EYE.field_78795_f = (float)rotationEye;
    
     IDalek dalek = entity.getDalekData();
     if (dalek != null) {
       int i; for (i = 0; i < dalek.getLeftArmAttatchments().size(); i++) {
         String arm = dalek.getLeftArmAttatchments().get(i);
         ModelRenderer part = getPart(arm);
        
         if (part != ModelLoader.NULL_PART) {
           if (entity.isLeftArm(arm)) {
             part.field_78806_j = true;
             double rotationLeftArm = Math.toRadians(headPitch) / 1.5D;
            
             if (part != null) {
               part.field_78795_f = (float)rotationLeftArm;
              
               if (entity.getDalekData() == DMDalekRegistry.SPECIAL_WEAPONS_DALEK) {
                 if (entity.getFuse() >= 0) {
                   part.field_78796_g = entity.world.rand.nextFloat() / 16.0F;
                   part.field_78795_f += entity.world.rand.nextFloat() / 16.0F;
                } else {
                   part.field_78796_g = 0.0F;
                } 
              }
            } 
          } else {
             part.field_78806_j = false;
          } 
        }
      } 
      
       for (i = 0; i < dalek.getRightArmAttatchments().size(); i++) {
         String arm = dalek.getRightArmAttatchments().get(i);
         ModelRenderer part = getPart(arm);
        
         if (part != ModelLoader.NULL_PART) {
           if (entity.isRightArm(arm)) {
             part.field_78806_j = true;
             double rotationRightArm = Math.toRadians(headPitch) / -1.5D;
            
             if (part != null) {
               part.field_78795_f = (float)rotationRightArm;
            }
          } else {
             part.field_78806_j = false;
          } 
        }
      } 
    } 
  }

  
  public void func_225598_a_(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
     if (this.model != null) {
       this.model.getModelData().getModel().renderToBuffer(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha, true);
      
       if (this.showBomb) {
         IBakedModel p_191962_4_ = Minecraft.func_71410_x().func_175599_af().func_184393_a(new ItemStack((IItemProvider)DMBlocks.NITRO9.get()), (World)null, (LivingEntity)null);
         IRenderTypeBuffer.Impl buf = Minecraft.func_71410_x().func_228019_au_().func_228487_b_();
        
         matrixStack.func_227861_a_(-0.05000000074505806D, 0.5D, 0.75D);
         matrixStack.func_227863_a_(Vector3f.field_229179_b_.func_229193_c_(10.0F));
        
         Minecraft.func_71410_x().func_175602_ab().func_228791_a_(((Block)DMBlocks.NITRO9.get()).getDefaultState(), matrixStack, (IRenderTypeBuffer)buf, packedLight, packedOverlay);
      } 
    } 
  }

  
  public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
     modelRenderer.field_78795_f = x;
     modelRenderer.field_78796_g = y;
     modelRenderer.field_78808_h = z;
  }
  
  public ModelRenderer getPart(String s) {
     if (this.model != null && this.model.getModelData() != null && this.model.getModelData().getModel() != null) {
       return (ModelRenderer)this.model.getModelData().getModel().getPart(s);
    }
     return (ModelRenderer)ModelLoader.NULL_PART;
  }
  
  public JSONModel getModelData() {
     return this.model;
  }

  
  public void init() {
     setPartEye(getPart("Eye"));
     setPartHead(getPart("Head"));
     setPartNeck(getPart("Neck"));
     setPartTorso(getPart("Torso"));
    
     this.LIGHT_LEFT = getPart("LightLeft");
     this.LIGHT_RIGHT = getPart("LightRight");
  }

  
  public JSONModel getModel() {
     return this.model;
  }
}


