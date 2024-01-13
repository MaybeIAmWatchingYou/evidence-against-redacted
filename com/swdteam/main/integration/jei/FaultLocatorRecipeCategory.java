package com.swdteam.main.integration.jei;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.swdteam.common.init.DMBlocks;
import com.swdteam.common.init.DMItems;
import com.swdteam.main.integration.jei.recipes.FaultLocatorInputs;
import java.util.ArrayList;
import java.util.List;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

public class FaultLocatorRecipeCategory
  implements IRecipeCategory<FaultLocatorInputs> {
   public static final List<FaultLocatorInputs> RECIPES = new ArrayList<>();
  
  static {
     NonNullList<Ingredient> ingredients = NonNullList.func_191196_a();
     ingredients.add(Ingredient.func_199804_a(new IItemProvider[] { (IItemProvider)DMItems.FLIGHT_FLUID_LINK.get() }));
     ingredients.add(Ingredient.func_199804_a(new IItemProvider[] { (IItemProvider)DMItems.FUEL_FLUID_LINK.get() }));
     ingredients.add(Ingredient.func_199804_a(new IItemProvider[] { (IItemProvider)DMItems.ACCURACY_FLUID_LINK.get() }));
     RECIPES.add(FaultLocatorInputs.create("links", ingredients));
  }
  
   public static final ResourceLocation UID = new ResourceLocation("dalekmod", "fault_locator");
   public static final ResourceLocation TEXTURE = new ResourceLocation("dalekmod", "textures/gui/fault_locator.png");
  
  private final IDrawable background;
  
  private final IDrawable icon;
  private final IDrawable barFlight;
  private final IDrawable barFuel;
  private final IDrawable barAccuracy;
  
  public FaultLocatorRecipeCategory(IGuiHelper helper) {
     this.background = (IDrawable)helper.createDrawable(TEXTURE, 98, 17, 148, 58);
     this.barFlight = (IDrawable)helper.createAnimatedDrawable(helper.createDrawable(TEXTURE, 0, 172, 48, 16), 60, IDrawableAnimated.StartDirection.LEFT, false);
     this.barFuel = (IDrawable)helper.createAnimatedDrawable(helper.createDrawable(TEXTURE, 0, 188, 48, 16), 60, IDrawableAnimated.StartDirection.LEFT, false);
     this.barAccuracy = (IDrawable)helper.createAnimatedDrawable(helper.createDrawable(TEXTURE, 0, 204, 48, 16), 60, IDrawableAnimated.StartDirection.LEFT, false);
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
     recipeLayout.getItemStacks().init(0, true, 56, 0);
     recipeLayout.getItemStacks().init(1, true, 56, 20);
     recipeLayout.getItemStacks().init(2, true, 56, 40);
     recipeLayout.getItemStacks().init(3, true, 130, 0);
     recipeLayout.getItemStacks().init(4, true, 130, 20);
     recipeLayout.getItemStacks().init(5, true, 130, 40);
    
     recipeLayout.getItemStacks().set(ingredients);
  }

  
  public void draw(FaultLocatorInputs recipe, MatrixStack matrixStack, double mouseX, double mouseY) {
     super.draw(recipe, matrixStack, mouseX, mouseY);
    
     this.barFlight.draw(matrixStack, 1, 1);
     this.barFuel.draw(matrixStack, 1, 21);
     this.barAccuracy.draw(matrixStack, 1, 41);
    
     Minecraft mc = Minecraft.func_71410_x();
     if (mc != null) {
       FontRenderer font = mc.field_71466_p;
      
       font.func_238421_b_(matrixStack, "Flight", 4.0F, 5.0F, -1);
       font.func_238421_b_(matrixStack, "Fuel", 4.0F, 25.0F, -1);
       font.func_238421_b_(matrixStack, "Accuracy", 4.0F, 45.0F, -1);
    } 
  }
}


