package com.swdteam.main.integration.jei;
import com.swdteam.client.gui.GuiEngineeringTable;
import com.swdteam.client.gui.GuiFaultLocator;
import com.swdteam.client.gui.GuiTardisInteriorBuilder;
import com.swdteam.common.init.DMBlocks;
import com.swdteam.common.init.DMEngineeringTableRegistry;
import com.swdteam.common.init.DMRoundelRegistry;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.gui.handlers.IGuiContainerHandler;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.ISubtypeRegistration;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;

@JeiPlugin
public class DMJei implements IModPlugin {
  public ResourceLocation getPluginUid() {
     return new ResourceLocation("dalekmod", "jei_plugin");
  }


  public void registerCategories(IRecipeCategoryRegistration registration) {
     registration.addRecipeCategories(new IRecipeCategory[] { new EngineeringTableRecipeCategory(registration.getJeiHelpers().getGuiHelper()) });
     registration.addRecipeCategories(new IRecipeCategory[] { new RoundelBuilderRecipeCategory(registration.getJeiHelpers().getGuiHelper()) });
     registration.addRecipeCategories(new IRecipeCategory[] { new ArtronRecipeCategory(registration.getJeiHelpers().getGuiHelper()) });
     registration.addRecipeCategories(new IRecipeCategory[] { new FaultLocatorRecipeCategory(registration.getJeiHelpers().getGuiHelper()) });
     registration.addRecipeCategories(new IRecipeCategory[] { new FaultLocatorArtronRecipeCategory(registration.getJeiHelpers().getGuiHelper()) });

     super.registerCategories(registration);
  }


  public void registerRecipes(IRecipeRegistration registration) {
     registration.addRecipes(DMEngineeringTableRegistry.getAllRecipes(), EngineeringTableRecipeCategory.UID);
     registration.addRecipes(DMRoundelRegistry.getAllRecipes(), RoundelBuilderRecipeCategory.UID);

     registration.addRecipes(ArtronRecipeCategory.RECIPES, ArtronRecipeCategory.UID);
     registration.addRecipes(FaultLocatorRecipeCategory.RECIPES, FaultLocatorRecipeCategory.UID);
     registration.addRecipes(FaultLocatorArtronRecipeCategory.RECIPES, FaultLocatorArtronRecipeCategory.UID);

     super.registerRecipes(registration);
  }


  public void registerItemSubtypes(ISubtypeRegistration registration) {
     registration.useNbtForSubtypes(new Item[] { ((Block)DMBlocks.ROUNDEL_DOOR.get()).func_199767_j() });

     super.registerItemSubtypes(registration);
  }


  public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
     registration.addRecipeCatalyst(new ItemStack((IItemProvider)DMBlocks.ENGINEERING_TABLE.get()), new ResourceLocation[] { EngineeringTableRecipeCategory.UID });
     registration.addRecipeCatalyst(new ItemStack((IItemProvider)DMBlocks.ROUNDEL_BUILDER.get()), new ResourceLocation[] { RoundelBuilderRecipeCategory.UID });

     registration.addRecipeCatalyst(new ItemStack((IItemProvider)DMBlocks.ARTRON_FUEL_TANK.get()), new ResourceLocation[] { ArtronRecipeCategory.UID });
     registration.addRecipeCatalyst(new ItemStack((IItemProvider)DMBlocks.FAULT_LOCATOR.get()), new ResourceLocation[] { FaultLocatorRecipeCategory.UID });
     registration.addRecipeCatalyst(new ItemStack((IItemProvider)DMBlocks.FAULT_LOCATOR.get()), new ResourceLocation[] { FaultLocatorArtronRecipeCategory.UID });

     super.registerRecipeCatalysts(registration);
  }


  public void registerGuiHandlers(IGuiHandlerRegistration registration) {
     registration.addRecipeClickArea(GuiEngineeringTable.class, 100, 32, 22, 21, new ResourceLocation[] { EngineeringTableRecipeCategory.UID });

     registration.addGenericGuiContainerHandler(GuiFaultLocator.class, (IGuiContainerHandler)new GuiFaultLocator.Handler());
     registration.addGenericGuiContainerHandler(GuiTardisInteriorBuilder.class, (IGuiContainerHandler)new GuiTardisInteriorBuilder.Handler());

     super.registerGuiHandlers(registration);
  }
}


