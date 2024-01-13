package com.swdteam.main.integration.jei;
import com.swdteam.common.crafting.EngineeringTableRecipe;
import com.swdteam.common.init.DMBlocks;
import java.util.List;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;

public class EngineeringTableRecipeCategory implements IRecipeCategory<EngineeringTableRecipe> {
   public static final ResourceLocation UID = new ResourceLocation("dalekmod", "engineering_table");
   public static final ResourceLocation TEXTURE = new ResourceLocation("dalekmod", "textures/gui/engineering_table.png");
  
  private final IDrawable background;
  private final IDrawable icon;
  
  public EngineeringTableRecipeCategory(IGuiHelper helper) {
     this.background = (IDrawable)helper.createDrawable(TEXTURE, 21, 30, 134, 26);
     this.icon = helper.createDrawableIngredient(new ItemStack((IItemProvider)DMBlocks.ENGINEERING_TABLE.get()));
  }

  
  public ResourceLocation getUid() {
     return UID;
  }

  
  public Class<? extends EngineeringTableRecipe> getRecipeClass() {
     return EngineeringTableRecipe.class;
  }

  
  public String getTitle() {
     return ((Block)DMBlocks.ENGINEERING_TABLE.get()).func_235333_g_().getString();
  }

  
  public IDrawable getBackground() {
     return this.background;
  }

  
  public IDrawable getIcon() {
     return this.icon;
  }

  
  public void setIngredients(EngineeringTableRecipe recipe, IIngredients ingredients) {
     ingredients.setInputIngredients((List)recipe.func_192400_c());
     ingredients.setOutput(VanillaTypes.ITEM, recipe.func_77571_b());
  }


  
  public void setRecipe(IRecipeLayout recipeLayout, EngineeringTableRecipe recipe, IIngredients ingredients) {
     recipeLayout.getItemStacks().init(0, true, 0, 4);
     recipeLayout.getItemStacks().init(1, true, 27, 4);
     recipeLayout.getItemStacks().init(2, true, 54, 4);
    
     recipeLayout.getItemStacks().init(3, false, 112, 4);
     recipeLayout.getItemStacks().set(ingredients);
  }
}


