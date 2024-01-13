package com.swdteam.main.integration.jei;

import com.swdteam.common.init.DMBlocks;
import com.swdteam.common.init.DMItems;
import com.swdteam.main.integration.jei.recipes.ArtronRecipe;
import java.util.ArrayList;
import java.util.List;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;

public class ArtronRecipeCategory
  implements IRecipeCategory<ArtronRecipe> {
   public static final List<ArtronRecipe> RECIPES = new ArrayList<>();

  static {
     RECIPES.add(ArtronRecipe.create(((Item)DMItems.FULL_ARTRON.get()).func_190903_i()));
     RECIPES.add(ArtronRecipe.create(((Item)DMItems.ARTRON.get()).func_190903_i()));
  }

   public static final ResourceLocation UID = new ResourceLocation("dalekmod", "artron_fuel_tank");
   public static final ResourceLocation TEXTURE = new ResourceLocation("dalekmod", "textures/gui/artron_fuel_tank.png");

  private final IDrawable background;
  private final IDrawable icon;

  public ArtronRecipeCategory(IGuiHelper helper) {
     this.background = (IDrawable)helper.createDrawable(TEXTURE, 77, 32, 68, 41);
     this.icon = helper.createDrawableIngredient(new ItemStack((IItemProvider)DMBlocks.ARTRON_FUEL_TANK.get()));
  }


  public ResourceLocation getUid() {
     return UID;
  }


  public Class<? extends ArtronRecipe> getRecipeClass() {
     return ArtronRecipe.class;
  }


  public String getTitle() {
     return ((Item)DMItems.ARTRON.get()).func_200295_i(new ItemStack((IItemProvider)DMItems.ARTRON.get())).getString();
  }


  public IDrawable getBackground() {
     return this.background;
  }


  public IDrawable getIcon() {
     return this.icon;
  }


  public void setIngredients(ArtronRecipe recipe, IIngredients ingredients) {
     ingredients.setOutput(VanillaTypes.ITEM, recipe.func_77571_b());
  }



  public void setRecipe(IRecipeLayout recipeLayout, ArtronRecipe recipe, IIngredients ingredients) {
     recipeLayout.getItemStacks().init(0, false, 47, 20);
     recipeLayout.getItemStacks().set(ingredients);
  }
}


