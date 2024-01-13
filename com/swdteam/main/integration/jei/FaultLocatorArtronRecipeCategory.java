package com.swdteam.main.integration.jei;

import com.swdteam.common.init.DMBlocks;
import com.swdteam.common.init.DMTags;
import com.swdteam.main.integration.jei.recipes.FaultLocatorInputs;
import java.util.ArrayList;
import java.util.List;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ITag;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

public class FaultLocatorArtronRecipeCategory implements IRecipeCategory<FaultLocatorInputs> {
   public static final List<FaultLocatorInputs> RECIPES = new ArrayList<>();

  static {
     NonNullList<Ingredient> ingredients = NonNullList.func_191196_a();
     ingredients.add(Ingredient.func_199805_a((ITag)DMTags.Items.ARTRON));
     RECIPES.add(FaultLocatorInputs.create("artron", ingredients));
  }

   public static final ResourceLocation UID = new ResourceLocation("dalekmod", "fault_locator_artron");
   public static final ResourceLocation TEXTURE = new ResourceLocation("dalekmod", "textures/gui/fault_locator.png");

  private final IDrawable background;

  private final IDrawable icon;

  public FaultLocatorArtronRecipeCategory(IGuiHelper helper) {
     this.background = (IDrawable)helper.createDrawable(TEXTURE, 45, 17, 39, 67);
     this.icon = helper.createDrawableIngredient(new ItemStack((IItemProvider)DMBlocks.FAULT_LOCATOR.get()));
  }


  public ResourceLocation getUid() {
     return UID;
  }


  public Class<? extends FaultLocatorInputs> getRecipeClass() {
     return FaultLocatorInputs.class;
  }


  public String getTitle() {
     return ((Block)DMBlocks.FAULT_LOCATOR.get()).func_235333_g_().getString();
  }


  public IDrawable getBackground() {
     return this.background;
  }


  public IDrawable getIcon() {
     return this.icon;
  }


  public void setIngredients(FaultLocatorInputs recipe, IIngredients ingredients) {
     ingredients.setInputIngredients((List)recipe.func_192400_c());
  }


  public void setRecipe(IRecipeLayout recipeLayout, FaultLocatorInputs recipe, IIngredients ingredients) {
     recipeLayout.getItemStacks().init(0, true, 10, 40);

     recipeLayout.getItemStacks().set(ingredients);
  }
}


