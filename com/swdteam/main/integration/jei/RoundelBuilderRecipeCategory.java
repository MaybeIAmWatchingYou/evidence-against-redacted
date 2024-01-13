package com.swdteam.main.integration.jei;
import com.swdteam.common.crafting.RoundelBuilderRecipe;
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

public class RoundelBuilderRecipeCategory implements IRecipeCategory<RoundelBuilderRecipe> {
   public static final ResourceLocation UID = new ResourceLocation("dalekmod", "roundel_builder");
   public static final ResourceLocation TEXTURE = new ResourceLocation("dalekmod", "textures/gui/roundel_builder.png");

  private final IDrawable background;
  private final IDrawable icon;

  public RoundelBuilderRecipeCategory(IGuiHelper helper) {
     this.background = (IDrawable)helper.createDrawable(TEXTURE, 16, 166, 81, 50);
     this.icon = helper.createDrawableIngredient(new ItemStack((IItemProvider)DMBlocks.ROUNDEL_BUILDER.get()));
  }


  public ResourceLocation getUid() {
     return UID;
  }


  public Class<? extends RoundelBuilderRecipe> getRecipeClass() {
     return RoundelBuilderRecipe.class;
  }


  public String getTitle() {
     return ((Block)DMBlocks.ROUNDEL_BUILDER.get()).func_235333_g_().getString();
  }


  public IDrawable getBackground() {
     return this.background;
  }


  public IDrawable getIcon() {
     return this.icon;
  }


  public void setIngredients(RoundelBuilderRecipe recipe, IIngredients ingredients) {
     ingredients.setInputIngredients((List)recipe.func_192400_c());
     ingredients.setOutput(VanillaTypes.ITEM, recipe.func_77571_b());
  }


  public void setRecipe(IRecipeLayout recipeLayout, RoundelBuilderRecipe recipe, IIngredients ingredients) {
     recipeLayout.getItemStacks().init(0, true, 0, 0);
     recipeLayout.getItemStacks().init(1, true, 0, 32);

     recipeLayout.getItemStacks().init(2, false, 59, 16);
     recipeLayout.getItemStacks().set(ingredients);
  }
}


